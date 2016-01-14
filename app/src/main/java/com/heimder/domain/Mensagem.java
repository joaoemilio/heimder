package com.heimder.domain;

/**
 * Created by JoaoEmilio on 28/12/2015.
 */
public class Mensagem extends Domain {

    private Long mensagemid;
    private Long id;
    private String conteudo;
    private Long empresaid;

    public Long getMensagemid() {
        return mensagemid;
    }

    public void setMensagemid(Long mensagemid) {
        this.mensagemid = mensagemid;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Long getEmpresaid() {
        return empresaid;
    }

    public void setEmpresaid(Long empresaid) {
        this.empresaid = empresaid;
    }

    public String toString() {
        return conteudo;
    }
}
