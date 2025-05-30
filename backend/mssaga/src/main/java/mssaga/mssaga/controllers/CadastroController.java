package mssaga.mssaga.controllers;

import mssaga.mssaga.DTO.ClienteDTO;
import mssaga.mssaga.sagas.AutocadastroSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class CadastroController {

    @Autowired
    private AutocadastroSaga autocadastroSaga;

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            System.out.println("Iniciando a saga");
            autocadastroSaga.autoCadastro(clienteDTO);
            return ResponseEntity.ok("Cliente e usuário criados com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar autocadastro: " + e.getMessage());
        }
    }
}
