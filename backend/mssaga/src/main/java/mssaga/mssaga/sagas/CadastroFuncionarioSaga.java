package mssaga.mssaga.sagas;

import com.fasterxml.jackson.databind.ObjectMapper;
import mssaga.mssaga.DTO.FuncionarioDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastroFuncionarioSaga {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public FuncionarioDTO criarFuncionario(FuncionarioDTO funcionarioDTO) {
        try {
            String funcionarioJson = objectMapper.writeValueAsString(funcionarioDTO);

            String respostaJson = (String) rabbitTemplate.convertSendAndReceive("funcionario.exchange", "funcionario.create", funcionarioJson);

            if (respostaJson != null && respostaJson.contains("\"erro\"")) {
                String mensagemErro = objectMapper.readTree(respostaJson).get("erro").asText();

                throw new IllegalArgumentException(mensagemErro);
            }

            return objectMapper.readValue(respostaJson, FuncionarioDTO.class);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar funcionário via saga: " + e.getMessage(), e);
        }
    }

}
