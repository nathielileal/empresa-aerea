package mssaga.mssaga.sagas;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mssaga.mssaga.DTO.ClienteDTO;
import mssaga.mssaga.DTO.ExtratoDTO;
import mssaga.mssaga.DTO.ReservaCreationResponseDTO;
import mssaga.mssaga.DTO.ReservaInputDTO;
import mssaga.mssaga.DTO.ReservaOutputDTO;
import mssaga.mssaga.DTO.ReservaTransactionDTO;
import mssaga.mssaga.DTO.VooDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class CriarReservaSaga {

    private final DirectExchange exchangeCriar;
    private final DirectExchange exchangeCancelar;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public CriarReservaSaga(
            @Qualifier("sagaCriarReserva") DirectExchange exchangeCriar,
            @Qualifier("sagaCancelarReserva") DirectExchange exchangeCancelar) {
        this.exchangeCriar = exchangeCriar;
        this.exchangeCancelar = exchangeCancelar;
    }

    public ReservaOutputDTO executeSaga(ReservaInputDTO payload) {
        try {
            // Verificar saldo do cliente
            String responseConsultaSaldo = (String) rabbitTemplate.convertSendAndReceive(
                    exchangeCriar.getName(), "saldo", objectMapper.writeValueAsString(payload));

            ClienteDTO clienteOutput = objectMapper.readValue(responseConsultaSaldo, ClienteDTO.class);

            // Buscar dados do voo
            String vooPayload = (String) rabbitTemplate.convertSendAndReceive(
                    exchangeCriar.getName(), "voo", objectMapper.writeValueAsString(payload));

            VooDTO dadosVoo = objectMapper.readValue(vooPayload, VooDTO.class);

            // Criar transação de reserva
            ReservaTransactionDTO reservaTransaction = new ReservaTransactionDTO(
                    payload.getValor(),
                    payload.getMilhas_utilizadas(),
                    payload.getQuantidade_poltronas(),
                    payload.getCodigo_cliente(),
                    dadosVoo);

            String reservaResponse = (String) rabbitTemplate.convertSendAndReceive(
                    exchangeCriar.getName(), "reserva", objectMapper.writeValueAsString(reservaTransaction));

            ReservaCreationResponseDTO dadosReserva = objectMapper.readValue(reservaResponse,
                    ReservaCreationResponseDTO.class);

            // Descontar milhas
            String savedTransaction = (String) rabbitTemplate.convertSendAndReceive(
                    exchangeCriar.getName(), "cliente", objectMapper.writeValueAsString(dadosReserva));

            ExtratoDTO transaction = objectMapper.readValue(savedTransaction, ExtratoDTO.class);

            return processResults(dadosReserva, dadosVoo, transaction);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao executar saga de criação de reserva", e);
        }
    }

    // public void rollbackVoo(String codigoVoo, int poltronas) {
    //     try {
    //         List<Integer> poltronasList = new ArrayList<>();
    //         for (int i = 1; i <= poltronas; i++) {
    //             poltronasList.add(i);
    //         }

    //         ReservaOutputDTO reserva = new ReservaOutputDTO(
    //                 "",
    //                 ZonedDateTime.now(),
    //                 "",
    //                 0f,
    //                 0L,
    //                 null,
    //                 poltronasList,
    //                 null,
    //                 codigoVoo);

    //         String jsonReserva = objectMapper.writeValueAsString(reserva);
    //         rabbitTemplate.convertAndSend(exchangeCancelar.getName(), "voo", jsonReserva);
    //     } catch (Exception e) {
    //         throw new RuntimeException("Erro ao executar rollback de voo", e);
    //     }
    // }

    private ReservaOutputDTO processResults(
            ReservaCreationResponseDTO dadosReserva,
            VooDTO dadosVoo,
            ExtratoDTO transaction) {
        return new ReservaOutputDTO(
                dadosReserva.getCodigo_reserva(),
                dadosReserva.getData(),
                dadosReserva.getEstado_reserva(),
                transaction.getTransacoes().get(0).getQuantidade_milhas(),
                transaction.getCodigo(),
                transaction.getSaldo_milhas(),
                dadosVoo,
                null);
    }
}
