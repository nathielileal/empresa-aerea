package mssaga.mssaga.controllers;

import java.util.HashMap;
import java.util.Map;
import mssaga.mssaga.DTO.FuncionarioDTO;
import mssaga.mssaga.sagas.AtualizarFuncionarioSaga;
import mssaga.mssaga.sagas.CadastroFuncionarioSaga;
import mssaga.mssaga.sagas.ExcluirFuncionarioSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private CadastroFuncionarioSaga cadastroFuncionarioSaga;

    @Autowired
    private AtualizarFuncionarioSaga atualizarFuncionarioSaga;

    @Autowired
    private ExcluirFuncionarioSaga excluirFuncionarioSaga;

    @PostMapping
    public ResponseEntity<Map<String, String>> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            FuncionarioDTO criado = cadastroFuncionarioSaga.criarFuncionario(funcionarioDTO);

            Map<String, String> response = new HashMap<>();

            response.put("codigo", String.valueOf(criado.getCodigo()));
            response.put("cpf", criado.getCpf());
            response.put("nome", criado.getNome());
            response.put("email", criado.getEmail());
            response.put("telefone", criado.getTelefone());
            response.put("ativo", String.valueOf(criado.getAtivo()));

            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao criar funcionário"));
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Map<String, String>> atualizarFuncionario(@PathVariable Long codigo, @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            funcionarioDTO.setCodigo(codigo);
            FuncionarioDTO atualizado = atualizarFuncionarioSaga.atualizarFuncionario(funcionarioDTO);

            Map<String, String> response = new HashMap<>();
            response.put("codigo", String.valueOf(atualizado.getCodigo()));
            response.put("cpf", atualizado.getCpf());
            response.put("nome", atualizado.getNome());
            response.put("email", atualizado.getEmail());
            response.put("telefone", atualizado.getTelefone());
            response.put("ativo", String.valueOf(atualizado.getAtivo()));

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao atualizar funcionário"));
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Map<String, String>> deletarFuncionario(@PathVariable Long codigo) {
        try {
            excluirFuncionarioSaga.deletarFuncionario(codigo);

            return ResponseEntity.ok(Map.of("mensagem", "Funcionário deletado com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao deletar funcionário"));
        }
    }
}
