package com.heimder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.heimder.alarm.SampleAlarmReceiver;
import com.heimder.domain.Campanha;
import com.heimder.domain.Mensagem;
import com.heimder.domain.StatusCampanha;
import com.heimder.service.CampanhaService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import heimder.com.heimder.R;

public class MainActivity extends AppCompatActivity {
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    private Activity activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.activity = this;

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Heimder.getInstance().setContext(this);
        loadCampanhas();

    }


    protected void sendSMSMessage() {
        Log.i("Send SMS", "");
        String phoneNo = "21987295027";
        String message = "Envio aleatorio de SMS para divulgar o Disk Picole. Entregamos Picole igual Pizza. Chamou, refrescou! http://www.festadopicole.com.br";
        //Find the directory for the SD Card using the API
        //*Don't* hardcode "/sdcard"
        String state = Environment.getExternalStorageState();
        File textFile = null;
        if (!(state.equals(Environment.MEDIA_MOUNTED))) {
            Toast.makeText(this, "There is no any sd card", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Sd card available", Toast.LENGTH_LONG).show();
            File file = Environment.getExternalStorageDirectory();
            textFile = new File(file.getAbsolutePath() + File.separator + "celulares.txt");
        }

        //Read text from file
        StringBuilder text = new StringBuilder();

        List<String> celulares = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(textFile));
            String line;

            while ((line = br.readLine()) != null) {
                celulares.add(line);
            }
            br.close();
        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Não consegui mandar nenhum SMS. Erro ao ler arquivo de celulares", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("Heimder", "Erro ao ler arquivo de celulares" + e.getMessage());
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();

            for(String celular: celulares)
                try {
                    smsManager.sendTextMessage(celular, null, message, null, null);
                    Toast.makeText(this, "SMS enviado para " + celular, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("heimder", "SMS nao enviado para " + celular);
                    Toast.makeText(this, "SMS NAO enviado para " + celular, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Não consegui mandar nenhum SMS.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.menu_campanha:
                intent = new Intent(this,CampanhaActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_importar:
                intent = new Intent(this,ImportarArquivoActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_empresas:
                intent = new Intent(this,ListaEmpresasActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_mensagens:
                intent = new Intent(this,ListaMensagensActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_config:
                intent = new Intent(this,PreferenciasActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadCampanhas() {

        CampanhaService service = new CampanhaService();
        List<Campanha> list = service.fetchAll();

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);

        ViewGroup vg = (ViewGroup)findViewById(R.id.layoutScrollView);
        vg.removeAllViews();

        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.i(Heimder.APP_NAME, "campanhas: " + list.size());

        for(Campanha campanha: list) {
            View view = inflater.inflate(R.layout.activity_campanha_agendada, null);

            TextView tv1 = (TextView)view.findViewById(R.id.nomeCampanhaAgendadaTextView);
            tv1.setText( campanha.getNome() );

            TextView tv2 = (TextView)view.findViewById(R.id.statusCampanhaTextView);
            tv2.setText( campanha.getDescricaoStatus() );

            TextView tv3 = (TextView)view.findViewById(R.id.statusCampanhaTextView);
            tv3.setText( campanha.getDescricaoStatus() );

            TextView tv4 = (TextView)view.findViewById(R.id.inicioCampanhaTextView);
            tv4.setText( campanha.getInicio() );

            TextView tv5 = (TextView)view.findViewById(R.id.terminoCampanhaTextView);
            if(campanha.getTermino() != null) {
                tv5.setText(campanha.getTermino());
            } else {
                tv5.setText("");
            }

            StatusCampanha sc = service.recuperarStatusCampanha(campanha);
            TextView tv6 = (TextView)view.findViewById(R.id.totalContatosTextView);
            tv6.setText( sc.getTotalContatos().toString() );

            TextView tv7 = (TextView)view.findViewById(R.id.totalContatosEnviadosTextView);
            tv7.setText( sc.getTotalEnviados().toString() );

            TextView tv8 = (TextView)view.findViewById(R.id.totalContatosPendentesTextView);
            tv8.setText( sc.getTotalPendente().toString() );

            TextView tv9 = (TextView)view.findViewById(R.id.mensagemCampanhaAgendadaTextView);
            Mensagem m = service.recuperarMensagem(campanha.getMensagemid());
            tv9.setText( m.getConteudo() );

            TextView tv10 = (TextView)view.findViewById(R.id.statusCampanhaTextView);
            tv10.setText(sc.getStatus());

            vg.addView(view);
        }
    }

}
