package mscliente.mscliente.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.model.Cliente;
import mscliente.mscliente.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO cliente) {
        ClienteDTO saved = service.saveCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getById(@PathVariable Long id) {
        try {
            ClienteDTO clienteDTO = service.findById(id);
            return ResponseEntity.ok(clienteDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listarClientes();
    }
}
