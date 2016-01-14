package com.heimder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.heimder.domain.Empresa;
import com.heimder.service.EmpresaService;

import java.util.ArrayList;
import java.util.List;

import heimder.com.heimder.R;

public class ListaEmpresasActivity extends AppCompatActivity {

    ListView listaEmpresas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_empresas);
        listaEmpresas = (ListView) findViewById(R.id.listEmpresasView);
    }
    @Override
    protected void onResume() {
        super.onResume();

        EmpresaService service = new EmpresaService();
        List<Empresa> empresas = service.fetchAll();

        ArrayAdapter<Empresa> empresaAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_1, empresas);
        listaEmpresas.setAdapter(empresaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.menu_novo:
                intent = new Intent(this,EmpresaFormActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_home:
                Log.i(Heimder.APP_NAME, "menu_home");
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
