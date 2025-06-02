package mscliente.mscliente.listeners;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.services.ClienteService;

@Component
public class ClienteListener {

    @Autowired
    private ClienteService clienteService;

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
            String emailCliente = email;
            Object dadosCliente = clienteService.findByEmail(emailCliente);

            return objectMapper.writeValueAsString(dadosCliente);
        } catch (Exception e) {
            System.err.println("Erro ao buscar " + e.getMessage());
            throw new AmqpRejectAndDontRequeueException("Erro no login", e);
        }

    }
}