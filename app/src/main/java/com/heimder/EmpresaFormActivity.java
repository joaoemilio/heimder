package com.heimder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heimder.domain.Empresa;
import com.heimder.service.EmpresaService;

import heimder.com.heimder.R;

public class EmpresaFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_form);

        final EditText nomeEmpresaEditText = (EditText)findViewById(R.id.nomeEmpresaEditText);
        Button salvarEmpresaButton = (Button)findViewById(R.id.salvarEmpresaButton);
        salvarEmpresaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empresa e = new Empresa();
                e.setNome(nomeEmpresaEditText.getText().toString());
                EmpresaService service = new EmpresaService();
                service.insert(e);
                finish();
            }
        });
    }
}
