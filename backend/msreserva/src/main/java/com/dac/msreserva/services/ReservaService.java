package com.dac.msreserva.services;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

// import com.dac.msreserva.DTO.AlternaEstadoDTO;
import com.dac.msreserva.DTO.EstadoReservaDTO;
import com.dac.msreserva.DTO.ReservaCreationResponseDTO;
import com.dac.msreserva.DTO.ReservaDTO;
import com.dac.msreserva.DTO.ReservaTransactionDTO;
import com.dac.msreserva.DTO.VooDTO;
import com.dac.msreserva.model.EstadoReserva;
import com.dac.msreserva.model.EstadoReservaEnum;
import com.dac.msreserva.model.HistoricoReserva;
import com.dac.msreserva.model.Reserva;
import com.dac.msreserva.model.ReservaConsulta;
import com.dac.msreserva.repository.ConsultaRepository;
import com.dac.msreserva.repository.EstadoReservaRepository;
import com.dac.msreserva.repository.HistoricoRepository;
import com.dac.msreserva.repository.TransactionRepository;

@Service
public class ReservaService {

    private final RabbitTemplate template;
    private final TransactionRepository repository;
    private final ConsultaRepository consultaReposity;
    private final HistoricoRepository historicoRepository;
    private final EstadoReservaRepository estadoReservaRepository;
    private final DirectExchange exchange;

    @Autowired
    private ModelMapper mapper;

    public ReservaService(
            RabbitTemplate template,
            TransactionRepository repository,
            ConsultaRepository consultaReposity,
            HistoricoRepository historicoRepository,
            EstadoReservaRepository estadoReservaRepository,
            @Qualifier("reservasCQRSExchange") DirectExchange exchange) {
        this.template = template;
        this.repository = repository;
        this.consultaReposity = consultaReposity;
        this.historicoRepository = historicoRepository;
        this.estadoReservaRepository = estadoReservaRepository;
        this.exchange = exchange;
    }

    public List<ReservaDTO> listReservasByCliente(Long codigo) {
        List<ReservaConsulta> reservas = consultaReposity.findReservasByCodigoCliente(codigo);

        return reservas.stream()
                .map(reserva -> mapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    public ReservaDTO detailReserva(String codigo) {
        ReservaConsulta reserva = consultaReposity.findReservaByCodigo(codigo);

        if (reserva == null) {
            throw new RuntimeException("Reserva com código " + codigo + " não encontrada.");
        }

        return mapper.map(reserva, ReservaDTO.class);
    }

    public ReservaCreationResponseDTO efetuarReserva(ReservaTransactionDTO reserva) {
        // Geração de código aleatório no formato ABC123
        long proximoNumero = repository.count() + 1;
        String codigo = "RES" + String.format("%04d", proximoNumero);

        Double milhas_utilizadas = (reserva.getMilhas_utilizadas() != null ? reserva.getMilhas_utilizadas() : 0.0)
                + reserva.getValor();
        // EstadoReserva estadoReserva =
        // estadoReservaRepository.findById(EstadoReservaEnum.CRIADA.getCodigo()).get();
        EstadoReserva estadoReserva = estadoReservaRepository.findById(EstadoReservaEnum.CRIADA.getCodigo()).get();
        ZonedDateTime data = ZonedDateTime.now(ZoneOffset.of("-03:00"));

        Reserva input = new Reserva(codigo, reserva.getCodigo_cliente(), reserva.getVoo().getCodigo(), estadoReserva,
                milhas_utilizadas);

        repository.save(input);
        historicoRepository.save(new HistoricoReserva(0L, data, input, null, estadoReserva));

        // Envia para sistema de consulta (CQRS)
        template.convertAndSend(exchange.getName(), "gravacao", mapper.map(reserva, ReservaDTO.class));

        return new ReservaCreationResponseDTO(
                data,
                codigo,
                estadoReserva.getDescricao(),
                reserva.getCodigo_cliente(),
                milhas_utilizadas,
                reserva.getVoo().getCodigo(),
                milhas_utilizadas);
    }

    private String gerarCodigoReservaUnico() {
        String letras = UUID.randomUUID().toString().replaceAll("[^A-Z]", "").substring(0, 3).toUpperCase();
        int numeros = new Random().nextInt(900) + 100;
        return letras + numeros;
    }

    // public ReservaDTO cancelarReserva(String codigo) {
    // Reserva reserva = repository.findById(codigo)
    // .orElseThrow(() -> new ("Reserva não encontrada com o código fornecido."));

    // if (!Arrays.asList(EstadoReservaEnum.CRIADA.getCodigo(),
    // EstadoReservaEnum.CHECK_IN.getCodigo()).contains(reserva.getEstado().getCodigo()))
    // {
    // throw new IllegalArgumentException("Uma reserva só pode ser cancelada nos
    // estados CRIADA ou CHECK-IN");
    // }

    // return atualizarEstadoReserva(reserva,
    // EstadoReservaEnum.CANCELADA.getCodigo());
    // }

    // public ReservaDTO alterarEstado(String codigo, AlternaEstadoDTO payload) {
    // Reserva reserva = repository.findById(codigo)
    // .orElseThrow(() -> new ("Reserva não encontrada com o código fornecido."));

    // long novoEstado = validaAlteracaoEstado(payload.getEstado(),
    // reserva.getEstado()).getCodigo();

    // return atualizarEstadoReserva(reserva, novoEstado);
    // }

    // public List<ReservaDTO> canceladoVoo(VooDTO voo) {
    // List<Reserva> reservas = repository.findByCodigoVoo(voo.getCodigo());
    // return reservas.stream()
    // .map(r -> atualizarEstadoReserva(r,
    // EstadoReservaEnum.CANCELADA_VOO.getCodigo()))
    // .collect(Collectors.toList());
    // }

    // public void realizaVoo(VooDTO voo) {
    // List<Reserva> reservas = repository.findByCodigoVoo(voo.getCodigo());
    // reservas.forEach(res -> {
    // EstadoReservaEnum estado;
    // if (res.getEstado().getCodigo() == EstadoReservaEnum.EMBARCADA.getCodigo()) {
    // estado = EstadoReservaEnum.REALIZADA;
    // } else if (res.getEstado().getCodigo() ==
    // EstadoReservaEnum.CANCELADA.getCodigo()) {
    // estado = EstadoReservaEnum.CANCELADA;
    // } else {
    // estado = EstadoReservaEnum.NAO_REALIZADA;
    // }
    // atualizarEstadoReserva(res, estado.getCodigo());
    // });
    // }

    // private ReservaDTO atualizarEstadoReserva(Reserva reserva, long
    // codigo_estado) {
    // ZonedDateTime data = ZonedDateTime.now(ZoneOffset.of("-03:00"));
    // EstadoReservaDTO estadoAntigo = reserva.getEstado();
    // EstadoReservaDTO estadoNovo =
    // estadoReservaRepository.findById(codigo_estado).get();
    // reserva.setEstado(estadoNovo);
    // Reserva reservaAtualizada = repository.save(reserva);

    // historicoRepository.save(new HistoricoReserva(0L, data, reservaAtualizada,
    // estadoAntigo, estadoNovo));

    // template.convertAndSend(
    // exchange.getName(),
    // "edicao",
    // gson.toJson(new ReservaUpdateEstadoDTO(reservaAtualizada.getCodigo(),
    // reservaAtualizada.getEstado().getDescricao(), data)));

    // return new ReservaDTO(
    // reservaAtualizada.getCodigo(),
    // data,
    // reservaAtualizada.getEstado().getDescricao(),
    // (Double) reservaAtualizada.getQuantidade_milhas(),
    // reservaAtualizada.getCodigo_cliente(),
    // null,
    // null,
    // reservaAtualizada.getCodigo_voo());
    // }

    // private EstadoReservaEnum validaAlteracaoEstado(String estadoDesejado,
    // EstadoReservaDTO estadoAtual) {
    // Map<String, EstadoReservaEnum> transicoesValidas = new HashMap<>();
    // transicoesValidas.put("CHECK-IN", EstadoReservaEnum.CRIADA);
    // transicoesValidas.put("EMBARCADA", EstadoReservaEnum.CHECK_IN);

    // String estadoNormalizado = estadoDesejado.toUpperCase().replace("-", "_");
    // EstadoReservaEnum estadoDesejadoEnum;
    // try {
    // estadoDesejadoEnum = EstadoReservaEnum.valueOf(estadoNormalizado);
    // } catch (IllegalArgumentException e) {
    // throw new IllegalArgumentException("Estado desejado inválido: " +
    // estadoDesejado);
    // }

    // EstadoReservaEnum estadoAtualEsperado =
    // transicoesValidas.get(estadoDesejado.toUpperCase());
    // if (estadoAtualEsperado == null) {
    // throw new IllegalArgumentException("Estado desejado inválido: " +
    // estadoDesejado);
    // }

    // if (estadoAtual.getCodigo() != estadoAtualEsperado.getCodigo()) {
    // throw new IllegalArgumentException("Condições para troca de estado não
    // atendidas");
    // }

    // return estadoDesejadoEnum;
    // }
}