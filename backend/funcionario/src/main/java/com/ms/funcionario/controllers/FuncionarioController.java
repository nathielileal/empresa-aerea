package com.ms.funcionario.controllers;

import com.ms.funcionario.dto.FuncionarioDTO;
import com.ms.funcionario.services.FuncionarioService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private FuncionarioService service;

    @GetMapping
    public List<FuncionarioDTO> listAll() {
        return service.listarFuncionarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> getById(@PathVariable Long id) {
        try {
            FuncionarioDTO dto = service.findById(id);
            
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> createCliente(@RequestBody FuncionarioDTO funcionarioDTO) {
        FuncionarioDTO saved = service.saveFuncionario(funcionarioDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> updateFuncionario(@PathVariable Long id, @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            FuncionarioDTO updated = service.updateFuncionario(id, funcionarioDTO);

            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        try {
            service.deleteFuncionario(id);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
