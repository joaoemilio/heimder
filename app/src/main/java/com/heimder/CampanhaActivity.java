package com.heimder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.heimder.domain.Campanha;
import com.heimder.domain.Empresa;
import com.heimder.domain.Mensagem;
import com.heimder.service.CampanhaService;
import com.heimder.service.ContactService;
import com.heimder.service.EmpresaService;
import com.heimder.service.MensagemService;
import com.heimder.ui.helper.CampanhaFormHelper;

import java.util.ArrayList;
import java.util.List;

import heimder.com.heimder.R;

public class CampanhaActivity extends HeimderAppCompatActivity  {

    private Spinner spinner = null;
    private Spinner mensagemSpinner = null;
    private List<Empresa> empresas = null;
    private CampanhaActivity activity;
    private String filePath;
    final int ACTIVITY_CHOOSE_FILE = 1;
    private List<Mensagem> mensagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_campanha);

        spinner = (Spinner)findViewById(R.id.empresaSpinner);
        loadSpinnerEmpresa();

        mensagemSpinner = (Spinner)findViewById(R.id.mensagemSpinner);
        loadSpinnerMensagem();

        Button buttonEscolherArquivo = (Button)findViewById(R.id.buttonEscolherArquivoCampanha);
        buttonEscolherArquivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
            }
        });

        Button buttonSalvar = (Button)findViewById(R.id.salvarNovaCampanhaButton);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("heimder", "process dialog show");
                ProgressDialog dialog = ProgressDialog.show(v.getContext(), "", "Criando campanha...", true);
                try {
                    Log.i(Heimder.APP_NAME, "onClick - salvarNovaCampanhaButton");
                    CampanhaFormHelper helper = new CampanhaFormHelper(activity);
                    Campanha campanha = helper.getCampanha();
                    //filePath = "celulares.txt";

                    CampanhaService service = new CampanhaService();
                    service.iniciarCampanha(campanha, filePath, CampanhaService.POR_ARQUIVO);
                    finish();
                }finally {
                    dialog.dismiss();
                }
            }
        });

    }

    private void loadSpinnerEmpresa() {

        EmpresaService service = new EmpresaService();
        empresas = service.fetchAll();

        ArrayAdapter<Empresa> empresaArrayAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_spinner_item, empresas );
        empresaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(empresaArrayAdapter);
    }

    private void loadSpinnerMensagem() {

        MensagemService service = new MensagemService();
        mensagens = service.fetchAll();

        ArrayAdapter<Mensagem> mensagemArrayAdapter = new ArrayAdapter<Mensagem>(this, android.R.layout.simple_spinner_item, mensagens );
        mensagemArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mensagemSpinner.setAdapter(mensagemArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case ACTIVITY_CHOOSE_FILE: {
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    filePath = uri.getPath();
                    String fileName = uri.getLastPathSegment();
                    TextView tv = (TextView)this.findViewById(R.id.nomeDoArquivoTextView);
                    tv.setText(fileName);
                } else {
                    Toast.makeText(activity, "escolher arquivo fim NOT OK", Toast.LENGTH_LONG);
                }
            }
        }
    }
}
