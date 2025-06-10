package mscliente.mscliente.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.DTO.ReservaCreationResponseDTO;
import mscliente.mscliente.DTO.ReservaInputDTO;
import mscliente.mscliente.DTO.ReservaOutputDTO;
import mscliente.mscliente.services.ClienteService;
import mscliente.mscliente.services.MilhasService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteListener {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MilhasService milhasService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "autocadastro.cliente")
    public String receberAutocadastro(String clienteJson) {
        try {
            ClienteDTO clienteDTO = objectMapper.readValue(clienteJson, ClienteDTO.class);
            ClienteDTO createdClient = clienteService.saveCliente(clienteDTO);
            return objectMapper.writeValueAsString(createdClient);
        } catch (IllegalArgumentException e) {
            return "{\"erro\":\"CPF ou e-mail já cadastrados.\"}";
        } catch (Exception e) {
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
            throw new AmqpRejectAndDontRequeueException("Erro no autocadastro", e);
        }
    }

    @RabbitListener(queues = "login.cliente")
    public String dadosLoginCliente(String email) {
        try {
            Object dadosCliente = clienteService.findByEmail(email);
            return objectMapper.writeValueAsString(dadosCliente);
        } catch (Exception e) {
            System.err.println("Erro ao buscar: " + e.getMessage());
            throw new AmqpRejectAndDontRequeueException("Erro no login", e);
        }
    }

    @RabbitListener(queues = "criareserva.saldo")
    public String consultaSaldo(String payload) {
        try {
            ReservaInputDTO dadosReserva = objectMapper.readValue(payload, ReservaInputDTO.class);
            ClienteDTO dadosCliente = clienteService.findById(dadosReserva.getCodigo_cliente());

            if (dadosReserva.getMilhas_utilizadas() > dadosCliente.getSaldo_milhas()) {
                throw new IllegalArgumentException("Saldo de milhas insuficiente");
            }

            return objectMapper.writeValueAsString(dadosCliente);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro na verificação de saldo", e);
        }
    }

    @RabbitListener(queues = "criareserva.cliente")
    public String efetuarReserva(String payload) {
        try {
            ReservaCreationResponseDTO transaction = objectMapper.readValue(payload, ReservaCreationResponseDTO.class);
            Object result = milhasService.registrarReserva(transaction);
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro ao efetuar reserva", e);
        }
    }

    @RabbitListener(queues = "cancelareserva.cliente")
    public String cancelarReserva(String payload) {
        try {
            ReservaOutputDTO reserva = objectMapper.readValue(payload, ReservaOutputDTO.class);
            Object result = milhasService.reembolsarReserva(reserva);
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro ao cancelar reserva", e);
        }
    }

    // @RabbitListener(queues = "cancelavoo.cliente")
    // public String canceladoVoo(String payload) {
    //     try {
    //         List<ReservaOutputDTO> reservas = objectMapper.readValue(
    //                 payload,
    //                 new TypeReference<List<ReservaOutputDTO>>() {
    //                 }
    //         );
    //         Object result = milhasService.reembolsarVoo(reservas);
    //         return objectMapper.writeValueAsString(new RabbitMessageDTO(true, result));
    //     } catch (Exception e) {
    //         throw new AmqpRejectAndDontRequeueException("Erro ao cancelar voo", e);
    //     }
    // }
}
