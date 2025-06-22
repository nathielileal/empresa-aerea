package com.dac.msreserva.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dac.msreserva.DTO.AlterarEstadoDTO;
import com.dac.msreserva.DTO.ReservaConsultaDTO;
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
    public ResponseEntity<List<ReservaConsultaDTO>> getReservasByCliente(@PathVariable Long codigo_cliente) {
        try {
            List<ReservaConsultaDTO> reservas = service.listReservasByCliente(codigo_cliente);
            return ResponseEntity.ok(reservas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/reservas/{codigo}")
    public ResponseEntity<ReservaConsultaDTO> buscaReserva(@PathVariable String codigo) {
        try {
            ReservaConsultaDTO reserva = service.buscaReserva(codigo);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PatchMapping("/reservas/{codigo}/estado")
    public ResponseEntity<ReservaDTO> alterarEstado(
            @PathVariable String codigo,
            @RequestBody AlterarEstadoDTO payload) {
        ReservaDTO reserva = service.alterarEstado(codigo, payload.getEstado());
        return ResponseEntity.ok(reserva);
    }

}
