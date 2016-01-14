package com.heimder.ui.helper;

import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.heimder.CampanhaActivity;
import com.heimder.Heimder;
import com.heimder.domain.Campanha;
import com.heimder.domain.Empresa;
import com.heimder.domain.Mensagem;

import heimder.com.heimder.R;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class CampanhaFormHelper extends UIHelper {

    public CampanhaFormHelper(CampanhaActivity activity) {
        this.activity = activity;
    }

    public Campanha getCampanha() {
        Campanha domain = new Campanha();

        domain.setNome(getEditTextString(R.id.nomeCampanhaEditText));
        Log.i(Heimder.APP_NAME, "nome: " + domain.getNome());
        Empresa empresa = (Empresa)getSpinnerItemSelected(R.id.empresaSpinner);
        domain.setEmpresaid(empresa.getId());
        //domain.setConteudo(getEditTextString(R.id.mensagemEditText));
        Mensagem mensagem = (Mensagem) getSpinnerItemSelected(R.id.mensagemSpinner);
        domain.setConteudo(mensagem.getConteudo());
        domain.setMensagemid(mensagem.getId());
        return domain;
    }

}
