package com.ms.voo.controllers;

import com.ms.voo.dto.AeroportoDTO;
import com.ms.voo.dto.CriarVooDTO;
import com.ms.voo.dto.VooDTO;
import com.ms.voo.services.VooService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voos")
public class VooController {

    @Autowired
    private VooService service;

    @GetMapping
    public ResponseEntity<?> listarVoos(
            @RequestParam(required = false) String origem,
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime data,
            @RequestParam(required = false) String inicio,
            @RequestParam(required = false) String fim) {

        if (origem != null && destino != null && data != null) {
            return ResponseEntity.ok(service.listarVoosFiltrados(origem, destino, data));
        } else if (inicio != null && fim != null) {
            return ResponseEntity.ok(service.listarVoosPorHora(inicio, fim));
        } else {
            return ResponseEntity.ok(service.listarVoos());
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<VooDTO> buscarPorCodigo(@PathVariable String codigo) {
        VooDTO voo = service.buscarPorCodigo(codigo);
        return ResponseEntity.ok(voo);
    }

    @GetMapping("/aeroportos")
    public ResponseEntity<List<AeroportoDTO>> listarAeroportos() {
        return ResponseEntity.ok(service.listarAeroportos());
    }

    @PostMapping
    public ResponseEntity<VooDTO> cadastrar(@RequestBody CriarVooDTO request) {
        AeroportoDTO origem = new AeroportoDTO();
        origem.setCodigo(request.getCodigoAeroportoOrigem());

        AeroportoDTO destino = new AeroportoDTO();
        destino.setCodigo(request.getCodigoAeroportoDestino());

        VooDTO vooDto = new VooDTO();
        vooDto.setData(request.getData());
        vooDto.setValorPassagem(request.getValorPassagem());
        vooDto.setQuantidadePoltronasTotal(request.getQuantidadePoltronasTotal());
        vooDto.setQuantidadePoltronasOcupadas(request.getQuantidadePoltronasOcupadas());
        vooDto.setAeroportoOrigem(origem);
        vooDto.setAeroportoDestino(destino);

        VooDTO vooCriado = service.cadastrarVoo(vooDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(vooCriado);
    }

    @PatchMapping("/{codigo}/estado")
    public ResponseEntity<VooDTO> alterarEstado(@PathVariable String codigo, @RequestBody Map<String, String> payload) {
        String novoEstado = payload.get("estado");
        
        VooDTO vooAtualizado = service.alterarEstado(codigo, novoEstado);
        
        return ResponseEntity.ok(vooAtualizado);
    }
}
