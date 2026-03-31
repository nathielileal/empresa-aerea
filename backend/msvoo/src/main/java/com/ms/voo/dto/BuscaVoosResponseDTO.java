package com.ms.voo.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class BuscaVoosResponseDTO {
    private String origem;
    private String destino;
    private ZonedDateTime data;
    private List<VooDTO> voos;

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public BuscaVoosResponseDTO(String origem, String destino, ZonedDateTime data, List<VooDTO> voos) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.voos = voos;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }



    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public List<VooDTO> getVoos() {
        return voos;
    }

    public void setVoos(List<VooDTO> voos) {
        this.voos = voos;
    }
}
