package mssaga.mssaga.sagas;

import com.fasterxml.jackson.databind.ObjectMapper;

import mssaga.mssaga.DTO.ClienteDTO;
import mssaga.mssaga.DTO.CadastroDTO;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutocadastroSaga {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public void autoCadastro(ClienteDTO clienteDTO) {
        try {
            String clienteJson = objectMapper.writeValueAsString(clienteDTO);

            // Envia a mensagem e espera a resposta
            String respostaJson = (String) rabbitTemplate.convertSendAndReceive(
                    "autocadastro", // exchange
                    "cliente", // routing key (ajuste conforme necessário)
                    clienteJson // mensagem
            );

            if (respostaJson != null) {
                ClienteDTO clienteCriado = objectMapper.readValue(respostaJson, ClienteDTO.class);

                // Cria DTO para autenticação
                CadastroDTO cadastroDTO = new CadastroDTO();
                cadastroDTO.setNome(clienteCriado.getNome());
                cadastroDTO.setEmail(clienteCriado.getEmail());
                cadastroDTO.setPerfil("CLIENTE");

                String cadastroJson = objectMapper.writeValueAsString(cadastroDTO);

                // Envia para auth
                rabbitTemplate.convertAndSend("autocadastro", "auth", cadastroJson);
                System.out.println("Usuário criado com sucesso.");
            } else {
                System.err.println("Erro: não houve resposta do mscliente");
            }
        } catch (Exception e) {
            System.err.println("Erro ao realizar autocadastro: " + e.getMessage());
        }
    }
}
