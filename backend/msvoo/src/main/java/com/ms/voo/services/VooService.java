package com.ms.voo.services;

import com.ms.voo.dto.BuscaVoosResponseDTO;
import com.ms.voo.dto.VooDTO;
import com.ms.voo.model.Voo;
import com.ms.voo.model.VooEstado;
import com.ms.voo.repository.AeroportoRepository;
import com.ms.voo.repository.VooEstadoRepository;
import com.ms.voo.repository.VooRepository;
import java.time.ZonedDateTime;
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

    @Transactional(readOnly = true)
    public BuscaVoosResponseDTO listarVoosFiltrados(String origem, String destino, ZonedDateTime data) {
        List<Voo> todosVoos = repository.findAll();

        List<Voo> voosFiltrados = todosVoos.stream()
                .filter(voo -> (origem == null || voo.getAeroportoOrigem().getCodigo().equalsIgnoreCase(origem)) &&
                        (destino == null || voo.getAeroportoDestino().getCodigo().equalsIgnoreCase(destino)) &&
                        (data == null || !voo.getData().isBefore(data)))
                .collect(Collectors.toList());

        List<VooDTO> voosDto = voosFiltrados.stream()
                .map(voo -> mapper.map(voo, VooDTO.class))
                .collect(Collectors.toList());

        return new BuscaVoosResponseDTO(origem, destino, data, voosDto);
    }

    private String gerarCodigoUnico() {
        String codigo;

        do {
            codigo = "TADS" + String.format("%04d", new Random().nextInt(10000));
        } while (repository.existsById(codigo));

        return codigo;
    }

    @Transactional
    public VooDTO cadastrarVoo(VooDTO dto) {
        String codigo = gerarCodigoUnico();

        if (dto.getAeroporto_origem().getCodigo().equals(dto.getAeroporto_destino().getCodigo())) {
            throw new IllegalArgumentException("Aeroporto de origem e destino não podem ser iguais");
        }

        if (dto.getData().isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Não é possível cadastrar voos com data no passado");
        }

        Voo voo = new Voo();

        voo.setCodigo(codigo);
        voo.setData(dto.getData());
        voo.setAeroportoOrigem(aeroportoRepository.findById(dto.getAeroporto_origem().getCodigo())
                .orElseThrow(() -> new RuntimeException("Aeroporto origem não encontrado")));
        voo.setAeroportoDestino(aeroportoRepository.findById(dto.getAeroporto_destino().getCodigo())
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
