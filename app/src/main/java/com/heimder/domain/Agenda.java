package com.heimder.domain;

import java.util.Date;

/**
 * Created by JoaoEmilio on 28/12/2015.
 */
public class Agenda extends Domain {

    private Long mensagemid;
    private Long campanhaid;
    private Long contatoid;
    private Integer status;
    private String inicio;
    private String termino;
    private Contact contact;
    private Mensagem mensagem;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Long getMensagemid() {
        return mensagemid;
    }

    public void setMensagemid(Long mensagemid) {
        this.mensagemid = mensagemid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public Long getContatoid() {
        return contatoid;
    }

    public void setContatoid(Long contatoid) {
        this.contatoid = contatoid;
    }

    public Long getCampanhaid() {
        return campanhaid;
    }

    public void setCampanhaid(Long campanhaid) {
        this.campanhaid = campanhaid;
    }

    public String getMobile() {
        String mobile = null;
        if(this.contact != null) {
            mobile = this.contact.getMobile();
        }
        return mobile;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public String getConteudoMensagem() {
        String msg = null;
        if(this.mensagem != null) {
            msg = this.mensagem.getConteudo();
        }
        return msg;
    }

    public static abstract class AgendaStatus {
        public static Integer AGENDADO = 0;
        public static Integer INICIADO = 1;
        public static Integer FINALIZADO = 2;
        public static Integer CANCELADO = 3;
    }
}
