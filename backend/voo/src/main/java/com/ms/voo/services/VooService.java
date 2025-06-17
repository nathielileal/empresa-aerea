package com.ms.voo.services;

import com.ms.voo.dto.VooDTO;
import com.ms.voo.model.Voo;
import com.ms.voo.model.VooEstado;
import com.ms.voo.repository.AeroportoRepository;
import com.ms.voo.repository.VooEstadoRepository;
import com.ms.voo.repository.VooRepository;
import java.util.List;
import java.util.Random;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VooService {

    @Autowired
    private VooRepository repository;

    @Autowired
    private AeroportoRepository aeroportoRepository;

    @Autowired
    private VooEstadoRepository vooEstadoRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "voo.exchange";

    @Transactional(readOnly = true)
    public VooDTO buscarPorCodigo(String codigo) {
        Voo voo = repository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Voo não encontrado com código: " + codigo));
        System.out.println("Voo encontrado");
        System.out.println(voo);
        return mapper.map(voo, VooDTO.class);
    }

    @Transactional(readOnly = true)
    public List<VooDTO> listarVoos() {
        List<Voo> voos = repository.findAll();

        return voos.stream().map(voo -> mapper.map(voo, VooDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public VooDTO cadastrarVoo(VooDTO dto) {
        String codigo = "TADS" + String.format("%04d", new Random().nextInt(10000));

        Voo voo = new Voo();

        voo.setCodigo(codigo);
        voo.setData(dto.getData());
        voo.setAeroportoOrigem(aeroportoRepository.findById(dto.getAeroportoOrigem().getCodigo())
                .orElseThrow(() -> new RuntimeException("Aeroporto origem não encontrado")));
        voo.setAeroportoDestino(aeroportoRepository.findById(dto.getAeroportoDestino().getCodigo())
                .orElseThrow(() -> new RuntimeException("Aeroporto destino não encontrado")));
        voo.setValorPassagem(dto.getValorPassagem());
        voo.setQuantidadeOcupadas(dto.getQuantidadeOcupadas());

        VooEstado estadoConfirmado = vooEstadoRepository.findBySigla("CONFIRMADO")
                .orElseThrow(() -> new RuntimeException("Estado CONFIRMADO não encontrado"));

        voo.setEstado(estadoConfirmado);

        Voo vooSalvo = repository.save(voo);

        return mapper.map(vooSalvo, VooDTO.class);
    }

    @Transactional
    public VooDTO cancelarVoo(String codigoVoo) {
        Voo voo = repository.findById(codigoVoo).orElseThrow(() -> new RuntimeException("Voo não encontrado"));

        if (!voo.getEstado().getSigla().equals("CONFIRMADO")) {
            throw new IllegalStateException("Só é possível cancelar voo no estado CONFIRMADO");
        }

        VooEstado estadoCancelado = vooEstadoRepository.findBySigla("CANCELADO")
                .orElseThrow(() -> new RuntimeException("Estado CANCELADO não encontrado"));

        voo.setEstado(estadoCancelado);

        repository.save(voo);

        try {
            rabbitTemplate.convertAndSend(EXCHANGE, "voo.cancel", codigoVoo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem de cancelamento para RabbitMQ", e);
        }

        return mapper.map(voo, VooDTO.class);
    }

    @Transactional
    public VooDTO realizarVoo(String codigoVoo) {
        Voo voo = repository.findById(codigoVoo).orElseThrow(() -> new RuntimeException("Voo não encontrado"));

        if (!voo.getEstado().getSigla().equals("CONFIRMADO")) {
            throw new IllegalStateException("Só é possível realizar voo no estado CONFIRMADO");
        }

        VooEstado estadoRealizado = vooEstadoRepository.findBySigla("REALIZADO")
                .orElseThrow(() -> new RuntimeException("Estado REALIZADO não encontrado"));

        voo.setEstado(estadoRealizado);

        repository.save(voo);

        try {
            rabbitTemplate.convertAndSend(EXCHANGE, "voo.realize", codigoVoo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem de realização para RabbitMQ", e);
        }

        return mapper.map(voo, VooDTO.class);
    }

}
