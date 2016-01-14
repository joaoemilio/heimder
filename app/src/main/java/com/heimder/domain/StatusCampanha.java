package com.heimder.domain;

/**
 * Created by JoaoEmilio on 30/12/2015.
 */
public class StatusCampanha extends Domain {

    private Integer totalContatos;
    private Integer totalEnviados;
    private Integer totalPendente;
    private String status;

    public Integer getTotalContatos() {
        return totalContatos;
    }

    public void setTotalContatos(Integer totalContatos) {
        this.totalContatos = totalContatos;
    }

    public Integer getTotalEnviados() {
        return totalEnviados;
    }

    public void setTotalEnviados(Integer totalEnviados) {
        this.totalEnviados = totalEnviados;
    }

    public Integer getTotalPendente() {
        return totalPendente;
    }

    public void setTotalPendente(Integer totalPendente) {
        this.totalPendente = totalPendente;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
