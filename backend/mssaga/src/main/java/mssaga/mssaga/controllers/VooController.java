package mssaga.mssaga.controllers;

import java.util.HashMap;
import mssaga.mssaga.DTO.VooDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import mssaga.mssaga.sagas.CadastroVooSaga;
import mssaga.mssaga.sagas.CancelarVooSaga;
import mssaga.mssaga.sagas.RealizarVooSaga;

@RestController
@RequestMapping("/voos")
public class VooController {

    @Autowired
    private CadastroVooSaga cadastroVooSaga;

    @Autowired
    private CancelarVooSaga cancelarVooSaga;

    @Autowired
    private RealizarVooSaga realizarVooSaga;

    @PostMapping
    public ResponseEntity<Map<String, Object>> cadastrarVoo(@RequestBody VooDTO vooDTO) {
        try {
            VooDTO vooCriado = cadastroVooSaga.iniciarCadastroVoo(vooDTO);

            Map<String, Object> response = new HashMap<>();

            response.put("codigo", vooCriado.getCodigo());
            response.put("aeroporto_origem", vooCriado.getAeroporto_origem());
            response.put("aeroporto_destino", vooCriado.getAeroporto_destino());
            response.put("data", vooCriado.getData());
            response.put("estado", vooCriado.getEstado());

            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao processar cadastro de voo"));
        }
    }

    @PatchMapping("/{codigo}/estado")
    public ResponseEntity<?> alterarEstado(@PathVariable String codigo, @RequestBody Map<String, String> payload) {
        try {
            String novoEstado = payload.get("estado").toUpperCase().trim();

            switch (novoEstado) {
                case "CANCELADO":
                    cancelarVooSaga.cancelarVoo(codigo);
                    break;

                case "REALIZADO":
                    realizarVooSaga.realizarVoo(codigo);
                    break;

                default:
                    return ResponseEntity.badRequest().body(Map.of("erro", "Estado inválido: " + novoEstado));
            }

            return ResponseEntity.ok(Map.of("mensagem", "Solicitação de alteração de estado enviada"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao alterar estado do voo via saga"));
        }
    }
}
