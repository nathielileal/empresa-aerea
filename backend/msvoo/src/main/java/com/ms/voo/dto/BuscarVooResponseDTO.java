package com.ms.voo.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BuscarVooResponseDTO {

    private String inicio;
    private String fim;

    @JsonProperty("voos")
    private List<VooDTO> voos;

    public BuscarVooResponseDTO(String inicio, String fim, List<VooDTO> voos) {
        this.inicio = inicio;
        this.fim = fim;
        this.voos = voos;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public List<VooDTO> getVoos() {
        return voos;
    }

    public void setVoos(List<VooDTO> voos) {
        this.voos = voos;
    }
}
