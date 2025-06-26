package com.ms.voo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.voo.dto.AeroportoDTO;
import com.ms.voo.dto.BuscaVoosResponseDTO;
import com.ms.voo.dto.BuscarVooResponseDTO;
import com.ms.voo.dto.VooDTO;
import com.ms.voo.model.Aeroporto;
import com.ms.voo.model.Voo;
import com.ms.voo.model.VooEstado;
import com.ms.voo.repository.AeroportoRepository;
import com.ms.voo.repository.VooEstadoRepository;
import com.ms.voo.repository.VooRepository;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ObjectMapper objectMapper;

    private static final String EXCHANGE = "voo.exchange";

    @Transactional(readOnly = true)
    public VooDTO buscarPorCodigo(String codigo) {
        Voo voo = repository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Voo não encontrado com código: " + codigo));

        VooDTO dto = mapper.map(voo, VooDTO.class);

        dto.setEstado(voo.getEstado().getSigla());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<VooDTO> listarVoos() {
        List<Voo> voos = repository.findAll();

        return voos.stream().map(voo -> {
            VooDTO dto = mapper.map(voo, VooDTO.class);
            dto.setEstado(voo.getEstado().getSigla());

            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BuscaVoosResponseDTO listarVoosFiltrados(String origem, String destino, ZonedDateTime data) {
        List<Voo> todosVoos = repository.findAll();

        List<Voo> voosFiltrados = todosVoos.stream()
                .filter(voo -> (origem == null || voo.getAeroportoOrigem().getCodigo().equalsIgnoreCase(origem))
                        && (destino == null || voo.getAeroportoDestino().getCodigo().equalsIgnoreCase(destino))
                        && (data == null || !voo.getData().isBefore(data)))
                .collect(Collectors.toList());

        List<VooDTO> voosDto = voosFiltrados.stream()
                .map(voo -> {
                    VooDTO dto = mapper.map(voo, VooDTO.class);
                    dto.setEstado(voo.getEstado().getSigla());
                    return dto;
                })
                .collect(Collectors.toList());

        return new BuscaVoosResponseDTO(origem, destino, data, voosDto);
    }

    @Transactional(readOnly = true)
    public BuscarVooResponseDTO listarVoosPorHora(String inicioStr, String fimStr) {
        ZonedDateTime inicio = ZonedDateTime.parse(inicioStr + "T00:00:00-03:00");
        ZonedDateTime fim = ZonedDateTime.parse(fimStr + "T23:59:59-03:00");

        List<Voo> todos = repository.findAll();

        List<VooDTO> voosFiltrados = todos.stream()
                .filter(voo -> {
                    ZonedDateTime data = voo.getData();
                    return data != null && !data.isBefore(inicio) && !data.isAfter(fim);
                })
                .map(voo -> {
                    VooDTO dto = mapper.map(voo, VooDTO.class);
                    dto.setEstado(voo.getEstado() != null ? voo.getEstado().getSigla() : "DESCONHECIDO");
                    return dto;
                })
                .sorted(Comparator.comparing(VooDTO::getData))
                .collect(Collectors.toList());

        return new BuscarVooResponseDTO(inicioStr, fimStr, voosFiltrados);
    }

    public List<AeroportoDTO> listarAeroportos() {
        List<Aeroporto> aeroportos = aeroportoRepository.findAll();

        return aeroportos.stream().map(a -> new AeroportoDTO(
                a.getCodigo(),
                a.getNome(),
                a.getCidade(),
                a.getUf())).collect(Collectors.toList());
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
        if (dto.getAeroportoOrigem() == null || dto.getAeroportoDestino() == null) {
            throw new IllegalArgumentException("Aeroporto origem e destino devem ser informados");
        }

        String codigo = gerarCodigoUnico();

        if (dto.getAeroportoOrigem().getCodigo().equalsIgnoreCase(dto.getAeroportoDestino().getCodigo())) {
            throw new IllegalArgumentException("Aeroporto de origem e destino não podem ser iguais");
        }

        if (dto.getData().isBefore(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Não é possível cadastrar voos com data no passado");
        }

        Aeroporto aeroportoOrigem = aeroportoRepository.findById(dto.getAeroportoOrigem().getCodigo().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Aeroporto origem não encontrado"));

        Aeroporto aeroportoDestino = aeroportoRepository.findById(dto.getAeroportoDestino().getCodigo().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Aeroporto destino não encontrado"));

        Voo voo = new Voo();

        voo.setCodigo(codigo);
        voo.setData(dto.getData());
        voo.setAeroportoOrigem(aeroportoOrigem);
        voo.setAeroportoDestino(aeroportoDestino);
        voo.setValorPassagem(dto.getValorPassagem());
        voo.setQuantidadePoltronas(dto.getQuantidadePoltronasTotal());
        voo.setQuantidadeOcupadas(dto.getQuantidadePoltronasOcupadas());

        VooEstado estadoConfirmado = vooEstadoRepository.findBySigla("CONFIRMADO")
                .orElseThrow(() -> new RuntimeException("Estado CONFIRMADO não encontrado"));

        voo.setEstado(estadoConfirmado);

        Voo vooSalvo = repository.save(voo);

        VooDTO retorno = mapper.map(vooSalvo, VooDTO.class);
        retorno.setEstado(vooSalvo.getEstado().getSigla());

        return retorno;
    }

    @Transactional
    public void cancelarVoo(String codigoVoo) {
        alterarEstado(codigoVoo, "CANCELADO");
    }

    @Transactional
    public void realizarVoo(String codigoVoo) {
        alterarEstado(codigoVoo, "REALIZADO");
    }

    @Transactional
    public VooDTO alterarEstado(String codigoVoo, String estadoStr) {
        Voo voo = repository.findById(codigoVoo)
                .orElseThrow(() -> new RuntimeException("Voo não encontrado"));

        String estadoAtual = voo.getEstado().getSigla();

        String estadoDesejado = estadoStr.trim().toUpperCase();

        if (!estadoDesejado.equals("CANCELADO") && !estadoDesejado.equals("REALIZADO")) {
            throw new IllegalArgumentException("Estado inválido: " + estadoStr);
        }

        if (estadoAtual.equals(estadoDesejado)) {
            VooDTO dto = mapper.map(voo, VooDTO.class);
           
            dto.setEstado(estadoAtual);
           
            return dto;
        }

        if (!estadoAtual.equals("CONFIRMADO")) {
            throw new IllegalStateException("Só é possível alterar estado de voos CONFIRMADOS");
        }

        VooEstado novoEstado = vooEstadoRepository.findBySigla(estadoDesejado)
                .orElseThrow(() -> new RuntimeException("Estado não encontrado: " + estadoDesejado));

        voo.setEstado(novoEstado);
       
        repository.save(voo);

        VooDTO dto = mapper.map(voo, VooDTO.class);

        try {
            String payload = objectMapper.writeValueAsString(dto);
            
            String exc = estadoAtual.equals("CANCELADO") ? "cancelavoo" : "realizavoo";

            rabbitTemplate.convertAndSend(exc, "reserva", payload);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem de voo para RabbitMQ", e);
        }

        dto.setEstado(novoEstado.getSigla());

        return dto;
    }
}
