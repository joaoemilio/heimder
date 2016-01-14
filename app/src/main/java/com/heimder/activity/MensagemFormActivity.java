package com.heimder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heimder.domain.Empresa;
import com.heimder.domain.Mensagem;
import com.heimder.service.EmpresaService;
import com.heimder.service.MensagemService;

import heimder.com.heimder.R;

public class MensagemFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem_form);
        final EditText mensagemEditText = (EditText)findViewById(R.id.mensagemEditText);
        Button salvarButton = (Button)findViewById(R.id.salvarMensagemButton);
        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensagem e = new Mensagem();
                e.setConteudo(mensagemEditText.getText().toString());

                MensagemService service = new MensagemService();
                service.insert(e);
                finish();
            }
        });
    }
}
