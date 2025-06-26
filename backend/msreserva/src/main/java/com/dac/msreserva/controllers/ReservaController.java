package com.dac.msreserva.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// import com.dac.msreserva.DTO.AlternaEstadoDTO;
import com.dac.msreserva.DTO.ReservaDTO;
import com.dac.msreserva.services.ReservaService;

@RestController
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping("/clientes/{codigo_cliente}/reservas")
    public ResponseEntity<List<ReservaDTO>> getReservasByCliente(@PathVariable Long codigo_cliente) {
        try {
            List<ReservaDTO> reservas = service.listReservasByCliente(codigo_cliente);
            return ResponseEntity.ok(reservas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ReservaDTO> detailReserva(@PathVariable String codigo) {
        try {
            ReservaDTO reserva = service.detailReserva(codigo);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @PatchMapping("/{codigo}/estado")
     public ResponseEntity<ReservaDTO> alterarEstado(
             @PathVariable String codigo,
             @RequestBody AlternaEstadoDTO payload) {
       ReservaDTO reserva = service.alterarEstado(codigo, payload);
         return ResponseEntity.ok(reserva);
     }
}
