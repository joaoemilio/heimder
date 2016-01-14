package com.heimder.service;

import android.content.Context;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.heimder.dao.ContactDAO;
import com.heimder.domain.Contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import heimder.com.heimder.R;

/**
 * Created by JoaoEmilio on 26/12/2015.
 */
public class ContactService {

    private Context context = null;

    public ContactService(Context context) {
        this.context = context;
    }

    public void loadContactsFromFile() {
        String state = Environment.getExternalStorageState();
        if (!(state.equals(Environment.MEDIA_MOUNTED))) {
            Toast.makeText(this.context, "There is no any sd card", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.context, "Sd card available", Toast.LENGTH_LONG).show();
            File file = Environment.getExternalStorageDirectory();
            loadContactsFromFile(file.getAbsolutePath() + File.separator + "celulares.txt");
        }
    }

    public void loadContactsFromFile(String filePath) {
        //Find the directory for the SD Card using the API
        //*Don't* hardcode "/sdcard"
        File textFile = new File(filePath);

        //Read text from file
        StringBuilder text = new StringBuilder();

        List<String> celulares = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(textFile));
            String line;
            ContactDAO dao = new ContactDAO();
            while ((line = br.readLine()) != null) {
                Contact domain = new Contact();
                domain.setMobile(line);
                dao.insert(domain);
            }
            br.close();
        } catch (IOException e) {
            Toast.makeText(this.context, "Não consegui mandar nenhum SMS. Erro ao ler arquivo de celulares", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("Heimder", "Erro ao ler arquivo de celulares" + e.getMessage());
        }

    }

    public void sendNextSMS() {
        String message = "Envio aleatorio de SMS para divulgar o Disk Picole. Entregamos Picole igual Pizza. Chamou, refrescou! http://www.festadopicole.com.br";

        ContactDAO dao = new ContactDAO();
        Contact domain = dao.findFirstContactNotSent();
        if(domain != null) {
            try {
                String celular = domain.getMobile();
                SmsManager smsManager = SmsManager.getDefault();

                try {
                    dao.setEnviado(domain);
                    Log.i("heimder", "Registro atualizado - flag enviado");
                    smsManager.sendTextMessage(celular, null, message, null, null);
                    Log.i("heimder", "SMS enviado para: " + domain.getMobile() + " .");
                } catch (Exception e) {
                    Log.e("heimder", "SMS nao enviado para " + celular, e.getCause());
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.e("heimder", "Não consegui mandar o SMS para " + domain.getMobile() + " .");
                e.printStackTrace();
            }
        }

    }
}
