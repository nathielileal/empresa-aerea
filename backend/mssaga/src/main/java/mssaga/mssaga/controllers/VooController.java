package mssaga.mssaga.controllers;

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
    public ResponseEntity<?> cadastrarVoo(@RequestBody VooDTO vooDTO) {
        try {
            VooDTO criado = cadastroVooSaga.iniciarCadastroVoo(vooDTO);
            return ResponseEntity.status(201).body(criado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao cadastrar voo via saga"));
        }
    }

    @PostMapping("/{codigo}/cancelar")
    public ResponseEntity<?> cancelarVoo(@PathVariable String codigo) {
        try {
            cancelarVooSaga.cancelarVoo(codigo);
            return ResponseEntity.ok(Map.of("mensagem", "Solicitação de cancelamento enviada"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao cancelar voo via saga"));
        }
    }

    @PostMapping("/{codigo}/realizar")
    public ResponseEntity<?> realizarVoo(@PathVariable String codigo) {
        try {
            realizarVooSaga.realizarVoo(codigo);
            return ResponseEntity.ok(Map.of("mensagem", "Solicitação de realização enviada"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao realizar voo via saga"));
        }
    }

}
