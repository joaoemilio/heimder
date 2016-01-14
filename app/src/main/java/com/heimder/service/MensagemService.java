package com.heimder.service;

import com.heimder.dao.EmpresaDAO;
import com.heimder.dao.MensagemDAO;
import com.heimder.domain.Empresa;
import com.heimder.domain.Mensagem;

import java.util.List;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class MensagemService {

    public Long criarMensagem(String msg) {
        MensagemDAO dao = new MensagemDAO();
        Mensagem domain = new Mensagem();
        domain.setConteudo(msg);

        Long id = dao.insert(domain);
        return id;
    }

    public Long insert(Mensagem msg) {
        MensagemDAO dao = new MensagemDAO();
        Mensagem domain = new Mensagem();
        domain.setConteudo(msg.getConteudo());

        Long id = dao.insert(domain);
        return id;
    }

    public Mensagem findMensagemById(Long id) {
        MensagemDAO dao = new MensagemDAO();
        Mensagem domain = new Mensagem();
        domain = dao.findMensagemById(id);
        return domain;
    }

    public List<Mensagem> fetchAll() {

        MensagemDAO dao;
        dao = new MensagemDAO();
        List<Mensagem> list = dao.fetchAll();

        return list;

    }

}
