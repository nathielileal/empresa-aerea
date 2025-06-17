package mssaga.mssaga.sagas;

import com.fasterxml.jackson.databind.ObjectMapper;
import mssaga.mssaga.DTO.VooDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class CadastroVooSaga {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public VooDTO iniciarCadastroVoo(VooDTO vooDTO) {
        try {
            String vooJson = objectMapper.writeValueAsString(vooDTO);

            String respostaJson = (String) rabbitTemplate.convertSendAndReceive(
                    "cadastravoo",
                    "voo",
                    vooJson);

            if (respostaJson != null && respostaJson.contains("\"erro\"")) {
                String mensagemErro = objectMapper.readTree(respostaJson).get("erro").asText();
                throw new IllegalArgumentException(mensagemErro);
            }

            return objectMapper.readValue(respostaJson, VooDTO.class);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao iniciar cadastro de voo: " + e.getMessage(), e);
        }
    }

}
