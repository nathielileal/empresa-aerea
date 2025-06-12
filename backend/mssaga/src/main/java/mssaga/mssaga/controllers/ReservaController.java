package mssaga.mssaga.controllers;

import mssaga.mssaga.DTO.ReservaInputDTO;
import mssaga.mssaga.DTO.ReservaOutputDTO;
import mssaga.mssaga.sagas.CancelarReservaSaga;
import mssaga.mssaga.sagas.CriarReservaSaga;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final CriarReservaSaga sagaCriar;
    // private final CancelarReservaSaga sagaCancelar;

    // public ReservaController(CriarReservaSaga sagaCriar, CancelarReservaSaga
    // sagaCancelar) {
    // this.sagaCriar = sagaCriar;
    // this.sagaCancelar = sagaCancelar;
    // }

    public ReservaController(CriarReservaSaga sagaCriar) {
        this.sagaCriar = sagaCriar;
        // this.sagaCancelar = sagaCancelar;
    }

    @PostMapping
    public ResponseEntity<ReservaOutputDTO> efetuarReserva(@RequestBody ReservaInputDTO payload) {
        System.out.println("Saga da reserva iniciando");
        System.out.println(
                "Cliente: " + payload.getCodigo_cliente() +
                        ", Voo: " + payload.getCodigo_voo() +
                        ", Milhas Utilizadas: " + payload.getMilhas_utilizadas() +
                        ", Valor: " + payload.getValor());
        ReservaOutputDTO reserva = sagaCriar.executeSaga(payload);
        return ResponseEntity.created(URI.create("/reserva/" + reserva.getCodigo())).body(reserva);
    }

    // @DeleteMapping("/{codigo}")
    // public ResponseEntity<ReservaOutputDTO> cancelarReserva(@PathVariable String
    // codigo) {
    // ReservaOutputDTO reserva = sagaCancelar.executeSaga(codigo);
    // return ResponseEntity.ok(reserva);
    // }
}