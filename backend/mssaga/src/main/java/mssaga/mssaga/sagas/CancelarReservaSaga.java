package mssaga.mssaga.sagas;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public ReservaOutputDTO executeSaga(String codigo) {
        try {
            // Etapa 1 - Cancelar Reserva
            String reservaJson = objectMapper.writeValueAsString(codigo);
            Object reservaResponse = rabbitTemplate.convertSendAndReceive(exchange.getName(), "reserva", reservaJson);
            System.out.println("Reserva cancelada");
            if (reservaResponse == null) {
                throw new IllegalStateException("Erro ao cancelar reserva. Resposta nula.");
            }

            ReservaOutputDTO dadosReserva = objectMapper.readValue(reservaResponse.toString(), ReservaOutputDTO.class);

            // Etapa 2 - Reembolsar Cliente
            String clienteJson = objectMapper.writeValueAsString(dadosReserva);
            Object clienteResponse = rabbitTemplate.convertSendAndReceive(exchange.getName(), "cliente", clienteJson);

            if (clienteResponse == null) {
                throw new IllegalStateException("Erro ao reembolsar cliente. Resposta nula.");
            }

            ClienteDTO dadosCliente = objectMapper.readValue(clienteResponse.toString(), ClienteDTO.class);

            return processResponse(dadosReserva, dadosCliente);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao executar a saga de cancelamento: " + e.getMessage());
        }
    }

    private ReservaOutputDTO processResponse(ReservaOutputDTO dadosReserva, ClienteDTO dadosCliente) {
        return new ReservaOutputDTO(
                dadosReserva.getCodigo(),
                dadosReserva.getData(),
                dadosReserva.getEstado(),
                dadosReserva.getQuantidade_milhas(),
                dadosCliente.getCodigo(),
                dadosCliente.getSaldo_milhas());
    }
}
