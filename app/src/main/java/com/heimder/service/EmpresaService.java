package com.heimder.service;

import android.content.Context;

import com.heimder.Heimder;
import com.heimder.dao.EmpresaDAO;
import com.heimder.domain.Empresa;

import java.util.List;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class EmpresaService {

    private Context context;

    public EmpresaService(){
        context = Heimder.getInstance().getContext();
    }

    public List<Empresa> fetchAll() {

        EmpresaDAO dao;
        dao = new EmpresaDAO();
        List<Empresa> list = dao.fetchAll();

        return list;

    }

    public void insert(Empresa e) {
        EmpresaDAO dao;
        dao = new EmpresaDAO();
        dao.insert(e);
    }
}
