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

    public ClienteDTO autoCadastro(ClienteDTO clienteDTO) {
        try {
            String clienteJson = objectMapper.writeValueAsString(clienteDTO);

            String respostaJson = (String) rabbitTemplate.convertSendAndReceive(
                    "autocadastro",
                    "cliente",
                    clienteJson);
            System.out.println(respostaJson);

            if (respostaJson != null && respostaJson.contains("\"erro\"")) {
                String mensagemErro = objectMapper.readTree(respostaJson).get("erro").asText();
                throw new IllegalArgumentException(mensagemErro);
            }

            ClienteDTO clienteCriado = objectMapper.readValue(respostaJson, ClienteDTO.class);

            CadastroDTO cadastroDTO = new CadastroDTO();
            cadastroDTO.setNome(clienteCriado.getNome());
            cadastroDTO.setEmail(clienteCriado.getEmail());
            cadastroDTO.setPerfil("CLIENTE");

            String cadastroJson = objectMapper.writeValueAsString(cadastroDTO);
            rabbitTemplate.convertAndSend("autocadastro", "auth", cadastroJson);
            return clienteCriado;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar autocadastro: " + e.getMessage(), e);
        }
    }

}
