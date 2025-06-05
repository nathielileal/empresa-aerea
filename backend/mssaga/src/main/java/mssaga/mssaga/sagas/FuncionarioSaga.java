package mssaga.mssaga.sagas;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioSaga {

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
            cadastroDTO.setEmail("CLIENTE");

            String cadastroJson = objectMapper.writeValueAsString(cadastroDTO);
            rabbitTemplate.convertAndSend("autocadastro", "auth", cadastroJson);
            return clienteCriado;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar autocadastro: " + e.getMessage(), e);
        }
    }

        @Bean
    public DirectExchange sagaAutocadastro() {
        return new DirectExchange("autocadastro");
    }

    @Bean
    public DirectExchange sagaCriarReserva() {
        return new DirectExchange("criareserva");
    }

    @Bean
    public DirectExchange sagaCancelarReserva() {
        return new DirectExchange("cancelareserva");
    }

    @Bean
    public DirectExchange sagaCancelarVoo() {
        return new DirectExchange("cancelavoo");
    }

    @Bean
    public DirectExchange sagaRealizarVoo() {
        return new DirectExchange("realizavoo");
    }

}
