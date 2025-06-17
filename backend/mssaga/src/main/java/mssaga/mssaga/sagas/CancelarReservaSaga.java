package mssaga.mssaga.sagas;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mssaga.mssaga.DTO.ClienteDTO;
import mssaga.mssaga.DTO.ReservaOutputDTO;
import mssaga.mssaga.DTO.VooDTO;

@Service
public class CancelarReservaSaga {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;
    private final ObjectMapper objectMapper;

    @Autowired
    public CancelarReservaSaga(RabbitTemplate rabbitTemplate,
                               @Qualifier("sagaCancelarReserva") DirectExchange exchange,
                               ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.objectMapper = objectMapper;
    }

    public ReservaOutputDTO executeSaga(String codigo) throws Exception {
        // Atualiza a reserva
        String reservaJson = objectMapper.writeValueAsString(codigo);
        Object reservaResponse = rabbitTemplate.convertSendAndReceive(exchange.getName(), "reserva", reservaJson);
        ReservaOutputDTO dadosReserva = objectMapper.readValue(reservaResponse.toString(), ReservaOutputDTO.class);

        // Reembolsa o cliente
        String clienteJson = objectMapper.writeValueAsString(dadosReserva);
        Object clienteResponse = rabbitTemplate.convertSendAndReceive(exchange.getName(), "cliente", clienteJson);
        ClienteDTO dadosCliente = objectMapper.readValue(clienteResponse.toString(), ClienteDTO.class);

        // // Libera assentos do voo
        // String vooJson = objectMapper.writeValueAsString(dadosReserva);
        // Object vooResponse = rabbitTemplate.convertSendAndReceive(exchange.getName(), "voo", vooJson);
        // VooDTO dadosVoo = objectMapper.readValue(vooResponse.toString(), VooDTO.class);

        return processResponse(dadosReserva, dadosCliente);
    }

    private ReservaOutputDTO processResponse(ReservaOutputDTO dadosReserva, ClienteDTO dadosCliente) {
        return new ReservaOutputDTO(
                dadosReserva.getCodigo(),
                dadosReserva.getData(),
                dadosReserva.getEstado(),
                dadosReserva.getQuantidade_milhas(),
                dadosCliente.getCodigo(),
                dadosCliente.getSaldo_milhas()
        );
    }
}
