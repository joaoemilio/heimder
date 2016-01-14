package com.heimder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.heimder.service.ContactService;

import heimder.com.heimder.R;

public class ImportarArquivoActivity extends HeimderAppCompatActivity {
    final int ACTIVITY_CHOOSE_FILE = 1;
    final Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_arquivo);
        Button btn = (Button) this.findViewById(R.id.buttonEscolherArquivo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("file/*");
                Toast.makeText(activity, "escolher arquivo", Toast.LENGTH_LONG);
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
                Toast.makeText(activity, "escolher arquivo fim", Toast.LENGTH_LONG);
            }
        });

        Button btnImportarContatos = (Button)this.findViewById(R.id.buttonImportar);
        btnImportarContatos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("heimder", "contact service");
                ContactService service = new ContactService(v.getContext());
                Log.i("heimder", "process dialog show");
                ProgressDialog dialog = ProgressDialog.show(v.getContext(), "", "Carregando arquivo no banco de dados...", true);
                Log.i("heimder", "load contacts");
                EditText editText = (EditText)activity.findViewById(R.id.editTextImportarArquivoPath);
                String filePath = editText.getText().toString();
                if(filePath.trim().equals("")) {
                    dialog.dismiss();
                    Toast.makeText(v.getContext(), "Escolha um arquivo.", Toast.LENGTH_LONG).show();
                } else {
                    service.loadContactsFromFile(filePath);

                    Log.i("heimder", "process dialog dismiss");
                    dialog.dismiss();
                    Log.i("heimder", "toast arquivo carregado");
                    Toast.makeText(v.getContext(), "Arquivo carregado.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case ACTIVITY_CHOOSE_FILE: {
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    String filePath = uri.getPath();
                    EditText editText = (EditText)this.findViewById(R.id.editTextImportarArquivoPath);
                    editText.setText(filePath);
                }
            }
        }
    }}
