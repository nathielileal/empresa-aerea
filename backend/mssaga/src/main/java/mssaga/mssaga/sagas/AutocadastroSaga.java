package br.ufpr.dac.saga_orchestration_service.sagas;

import br.ufpr.dac.saga_orchestration_service.utils.RabbitUtils;
import com.google.gson.Gson;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import utils.gson.GsonProcessor;
import utils.dto.ClienteInputDTO;
import utils.dto.ClienteOutputDTO;
import utils.dto.UsuarioInputDTO;
import utils.dto.UsuarioRole;
import java.util.concurrent.CompletableFuture;

@Service
public class AutocadastroSaga {

    private final RabbitUtils rabbit;
    private final DirectExchange exchange;
    private final Gson gson;

    public AutocadastroSaga(RabbitUtils rabbit, @Qualifier("sagaAutocadastro") DirectExchange exchange) {
        this.rabbit = rabbit;
        this.exchange = exchange;
        this.gson = new Gson();
    }

    public CompletableFuture<ClienteOutputDTO> executeSaga(ClienteInputDTO clienteCadastro) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String requestCliente = gson.toJson(clienteCadastro);
                String responseCliente = rabbit.asyncSendAndReceive(exchange.getName(), "cliente", requestCliente).get();

                ClienteOutputDTO cliente = GsonProcessor.parseJson(responseCliente, ClienteOutputDTO.class);
                UsuarioInputDTO inputCadastro = new UsuarioInputDTO(cliente.getCodigo(), cliente.getEmail(), null, UsuarioRole.CLIENTE);

                String requestAuth = gson.toJson(inputCadastro);
                String responseAuth = rabbit.asyncSendAndReceive(exchange.getName(), "auth", requestAuth).get();

                return processResponses(cliente, responseAuth);
            } catch (Exception e) {
                throw new RuntimeException("Error executing saga", e);
            }
        });
    }

    private ClienteOutputDTO processResponses(ClienteOutputDTO responseCliente, String responseAuth) {
        if ("Sucesso".equals(responseAuth)) {
            return responseCliente;
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }
}