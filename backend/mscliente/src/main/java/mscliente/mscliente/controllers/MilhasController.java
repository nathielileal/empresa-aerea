package mscliente.mscliente.controllers;

import mscliente.mscliente.DTO.MilhasDTO;
import mscliente.mscliente.DTO.TransacaoDTO;
import mscliente.mscliente.DTO.ClienteDTO;
import mscliente.mscliente.DTO.ExtratoDTO;
import mscliente.mscliente.services.MilhasService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{id}")
public class MilhasController {

    @Autowired
    private MilhasService service;

    @PutMapping("/milhas")
    public ResponseEntity<ClienteDTO> comprarMilhas(@PathVariable Long id, @RequestBody MilhasDTO milhas) {
        System.out.println("Comprar milhas iniciado");
        ClienteDTO response = service.comprarMilhas(id, milhas);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/milhas")
    public ResponseEntity<ExtratoDTO> listarTransacoes(@PathVariable Long id) {
        ExtratoDTO extrato = service.listarTransacoesCliente(id);
        return ResponseEntity.ok(extrato);
    }

    // @GetMapping
    // public ResponseEntity<ExtratoDTO> emitirExtrato(@PathVariable Long id) {
    // ExtratoDTO extrato = service.emitirExtrato(id);
    // return ResponseEntity.ok(extrato);
    // }
}
