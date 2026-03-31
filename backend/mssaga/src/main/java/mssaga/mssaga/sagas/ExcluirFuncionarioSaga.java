package mssaga.mssaga.sagas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcluirFuncionarioSaga {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void deletarFuncionario(Long codigo) {
        try {
            String respostaJson = (String) rabbitTemplate.convertSendAndReceive("funcionario.exchange", "funcionario.delete", codigo.toString());

            if (respostaJson != null && respostaJson.contains("\"erro\"")) {
                String mensagemErro = objectMapper.readTree(respostaJson).get("erro").asText();

                throw new IllegalArgumentException(mensagemErro);
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar funcionário via saga: " + e.getMessage(), e);
        }
    }

}
