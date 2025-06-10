// package com.dac.msreserva.listeners;

// import java.time.ZonedDateTime;

// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.stereotype.Service;

// import com.dac.msreserva.DTO.VooDTO;
// import com.dac.msreserva.services.ReservaService;

// @Service
// public class TransactionListener {

//     private final ReservaService service;
//     private final Gson gson;

//     public TransactionListener(ReservaService service) {
//         this.service = service;
//         this.gson = new GsonBuilder()
//                 .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
//                 .create();
//     }

//     @RabbitListener(queues = "criareserva.reserva", errorHandler = "customErrorHandler")
//     public String efetuarReserva(String payload) {
//         ReservaTransactionDTO reservaTransaction = gson.fromJson(payload, ReservaTransactionDTO.class);
        
//         Object milhasTransaction = service.efetuarReserva(reservaTransaction);
//          response = new (true, milhasTransaction);
//         return gson.toJson(response);
//     }

//     @RabbitListener(queues = "cancelareserva.reserva", errorHandler = "customErrorHandler")
//     public String cancelarReserva(String codigo) {
//         Object reserva = service.cancelarReserva(codigo);
//          response = new (true, reserva);
        
//         return gson.toJson(response);
//     }

//     @RabbitListener(queues = "cancelavoo.reserva", errorHandler = "customErrorHandler")
//     public String canceladoVoo(String payload) {
//         VooDTO voo = gson.fromJson(payload, VooDTO.class);
//         Object reserva = service.canceladoVoo(voo);
//          response = new (true, reserva);
        
//         return gson.toJson(response);
//     }

//     @RabbitListener(queues = "realizavoo.reserva", errorHandler = "customErrorHandler")
//     public String realizaVoo(String payload) {
//         VooDTO voo = gson.fromJson(payload, VooDTO.class);
//         Object reserva = service.realizaVoo(voo);
//          response = new (true, reserva);
        
//         return gson.toJson(response);
//     }
// }