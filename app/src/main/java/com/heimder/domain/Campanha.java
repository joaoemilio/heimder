package com.heimder.domain;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class Campanha extends Domain {

    private Long campanhaid;
    private String nome;
    private Long mensagemid;
    private Long empresaid;
    private String inicio;
    private String termino;
    private Integer status;
    private Mensagem mensagem;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCampanhaid() {
        return campanhaid;
    }

    public void setCampanhaid(Long campanhaid) {
        this.campanhaid = campanhaid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getMensagemid() {
        return mensagemid;
    }

    public void setMensagemid(Long mensagemid) {
        this.mensagemid = mensagemid;
    }

    public Long getEmpresaid() {
        return empresaid;
    }

    public void setEmpresaid(Long empresaid) {
        this.empresaid = empresaid;
    }

    public void setConteudo(String conteudo) {
        if(mensagem == null) {
            mensagem = new Mensagem();
        }
        mensagem.setConteudo(conteudo);
    }

    public String getConteudo() {
        String msg = null;
        if(mensagem != null && mensagem.getConteudo() != null) {
            msg = mensagem.getConteudo();
        }
        return msg;
    }

    public final static class CampanhaStatus {
        public final static Integer AGENDADO = 0;
        public final static Integer INICIADO = 1;
        public final static Integer FINALIZADO = 2;
        public final static Integer CANCELADO = 3;
    }

    public String getDescricaoStatus() {

        if(status == CampanhaStatus.AGENDADO) {
            return "AGENDADO";
        } else if(status == CampanhaStatus.INICIADO) {
            return "INICIADO";
        } else if(status == CampanhaStatus.FINALIZADO) {
            return "FINALIZADO";
        } else if(status == CampanhaStatus.CANCELADO) {
            return "CANCELADO";
        } else  {
            return "INVALIDO";
        }
    }
}
