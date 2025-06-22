package mssaga.mssaga.controllers;

import mssaga.mssaga.DTO.ReservaInputDTO;
import mssaga.mssaga.DTO.ReservaOutputDTO;
import mssaga.mssaga.sagas.CancelarReservaSaga;
import mssaga.mssaga.sagas.CriarReservaSaga;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final CriarReservaSaga sagaCriar;
    private final CancelarReservaSaga sagaCancelar;
    // private final CancelarReservaSaga sagaCancelar;

    // public ReservaController(CriarReservaSaga sagaCriar, CancelarReservaSaga
    // sagaCancelar) {
    // this.sagaCriar = sagaCriar;
    // this.sagaCancelar = sagaCancelar;
    // }

    public ReservaController(CriarReservaSaga sagaCriar, CancelarReservaSaga sagaCancelar) {
        this.sagaCriar = sagaCriar;
        this.sagaCancelar = sagaCancelar;
    }

    @PostMapping
    public ResponseEntity<?> efetuarReserva(@RequestBody ReservaInputDTO payload) {
        try {
            System.out.println("Saga da reserva iniciando");
            System.out.println(
                    "Cliente: " + payload.getCodigo_cliente() +
                            ", Voo: " + payload.getCodigo_voo() +
                            ", Milhas Utilizadas: " + payload.getMilhas_utilizadas() +
                            ", Valor: " + payload.getValor());
            ReservaOutputDTO reserva = sagaCriar.executeSaga(payload);
            return ResponseEntity.created(URI.create("/reserva/" + reserva.getCodigo())).body(reserva);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("erro", e.getReason()));
        }

    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> cancelarReserva(@PathVariable String codigo) {
        try {
            System.out.println("Iniciando cancelamento da reserva");
            ReservaOutputDTO reserva = sagaCancelar.executeSaga(codigo);
            return ResponseEntity.ok(reserva);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("erro", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro interno ao cancelar reserva"));
        }
    }

}