package com.heimder.domain;

/**
 * Created by JoaoEmilio on 28/12/2015.
 */
public class Empresa extends Domain {

    private Long empresaid;
    private String nome;

    public Long getEmpresaid() {
        return empresaid;
    }

    public void setEmpresaid(Long empresaid) {
        this.empresaid = empresaid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return this.getNome();
    }
}
