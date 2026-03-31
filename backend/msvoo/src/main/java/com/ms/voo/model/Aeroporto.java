package com.ms.voo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aeroporto")
public class Aeroporto {

    @Id
    private String codigo; 

    private String nome;
    private String cidade;
    private String uf;
    
    public Aeroporto() {
    }

    public Aeroporto(String codigo, String nome, String cidade, String uf) {
        this.codigo = codigo.toUpperCase();
        this.nome = nome;
        this.cidade = cidade;
        this.uf = uf.toUpperCase();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo.toUpperCase(); 
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf.toUpperCase();
    }
}
