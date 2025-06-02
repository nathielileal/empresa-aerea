package mssaga.mssaga.controllers;

import mssaga.mssaga.DTO.ClienteDTO;
import mssaga.mssaga.sagas.AutocadastroSaga;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class CadastroController {

    @Autowired
    private AutocadastroSaga autocadastroSaga;

    @PostMapping
    public ResponseEntity<Map<String, Object>> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteCriado = autocadastroSaga.autoCadastro(clienteDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("cpf", clienteCriado.getCpf());
            response.put("email", clienteCriado.getEmail());
            response.put("codigo", clienteCriado.getCodigo());
            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao processar autocadastro"));
        }
    }

}
