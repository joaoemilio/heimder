package com.heimder.service;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.heimder.Heimder;
import com.heimder.dao.AbstractDAO;
import com.heimder.dao.AgendaDAO;
import com.heimder.dao.CampanhaDAO;
import com.heimder.dao.ContactDAO;
import com.heimder.dao.DBHelper;
import com.heimder.domain.Agenda;
import com.heimder.domain.Campanha;
import com.heimder.domain.Contact;
import com.heimder.domain.Mensagem;
import com.heimder.domain.StatusCampanha;
import com.heimder.util.DateUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class CampanhaService {

    public static final int POR_ARQUIVO = 0;
    public static final int TODOS_CLIENTES = 1;

    private Context context;

    public CampanhaService(){
        context = Heimder.getInstance().getContext();
    }

    public List<Campanha> fetchAll() {

        CampanhaDAO dao;
        dao = new CampanhaDAO();
        List<Campanha> list = dao.fetchAll();

        return list;

    }

    public void insert(Campanha domain) {
        CampanhaDAO dao;
        dao = new CampanhaDAO();
        dao.insert(domain);
    }

    public void iniciarCampanha(Campanha domain, String filePath, int tipo) {
        //iniciar transacao
        CampanhaDAO cadao = new CampanhaDAO();
        DBHelper.getInstance().getWritableDatabase().beginTransaction();
        MensagemService mservice = new MensagemService();
        Long mensagemid = domain.getMensagemid();
                //criar mensagem
        //Long mensagemid = mservice.criarMensagem(msg);
        //Log.i(Heimder.APP_NAME, "mensagem id (Campanha Service): " + mensagemid);
        //criar campanha
        //domain.setMensagemid(mensagemid);
        Long campanhaid = cadao.insert(domain);
        //Long campanhaid = 1l;
        //criar agenda para cada contato
        if(tipo == POR_ARQUIVO) {
            loadContactsFromFile(campanhaid, mensagemid, filePath);
        }else if(tipo == TODOS_CLIENTES) {
            loadTodosClientes(campanhaid, mensagemid);
        }


        DBHelper.getInstance().getWritableDatabase().setTransactionSuccessful();
        DBHelper.getInstance().getWritableDatabase().endTransaction();
    }

    private void loadTodosClientes(Long campanhaid, Long mensagemid) {
        ContactDAO codao = new ContactDAO();
        List<Contact> contacts = codao.fetchAll();
        AgendaDAO adao = new AgendaDAO();
        String inicio = DateUtil.getInstance().getNow();
        for(Contact contact: contacts) {
            Log.i(Heimder.APP_NAME, "adicionando contato: " + contact.getMobile());
            Agenda a = new Agenda();
            a.setInicio(inicio);
            a.setCampanhaid(campanhaid);
            a.setContatoid(contact.getId());
            a.setContact(contact);

            Mensagem m = new Mensagem();
            m.setId( mensagemid );
            a.setMensagemid(mensagemid);
            a.setMensagem( m );
            adao.insert(a);
        }
    }

    public StatusCampanha recuperarStatusCampanha(Campanha domain) {

        StatusCampanha sc = new StatusCampanha();

        AgendaDAO agendaDAO = new AgendaDAO();
        Integer total = agendaDAO.fetchTotalContatos(domain.getId());
        Integer enviados = agendaDAO.fetchTotalEnviados(domain.getId());  //agendaDAO.fetchTotalEnviados(domain.getId());
        Integer pendentes = agendaDAO.fetchTotalPendentes(domain.getId());; //agendaDAO.fetchTotalPendentes(domain.getId());

        sc.setTotalContatos(total);
        sc.setTotalEnviados(enviados);
        sc.setTotalPendente(pendentes);
        sc.setStatus(domain.getDescricaoStatus());

        return sc;
    }

    public Mensagem recuperarMensagem( Long idMensagem ) {
        MensagemService service = new MensagemService();
        Mensagem m = service.findMensagemById(idMensagem);
        return m;
    }

    public boolean sendNextSMS() {
        boolean hasNext = false;
        AgendaDAO agendaDAO = new AgendaDAO();
        Agenda domain = agendaDAO.findFirstContactNotSent();
        if(domain != null) {
            hasNext = true;
            domain.setStatus(Agenda.AgendaStatus.INICIADO);
            Log.i(Heimder.APP_NAME, "proximo numero: " + domain.getMobile());
            agendaDAO.setStatus(domain, Agenda.AgendaStatus.INICIADO);
            Log.i(Heimder.APP_NAME, "recuperar mensagem");

            try {
                String celular = domain.getMobile();
                SmsManager smsManager = SmsManager.getDefault();

                try {
                    agendaDAO.setStatus(domain, Agenda.AgendaStatus.FINALIZADO);
                    Log.i("heimder", "Registro atualizado - flag enviado");
                    smsManager.sendTextMessage(celular, null, domain.getConteudoMensagem(), null, null);
                    Log.i("heimder", "SMS enviado para: " + celular + " .");
                } catch (Exception e) {
                    Log.e("heimder", "SMS nao enviado para " + celular, e.getCause());
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.e("heimder", "Não consegui mandar o SMS para " + domain.getMobile() + " .");
                e.printStackTrace();
            }
        }
        return hasNext;
    }

    public void atualizarStatusCampanhas() {
        CampanhaDAO dao = new CampanhaDAO();
        List<Campanha> list = dao.fetchCampanhasPendentes();
        for(Campanha campanha: list) {
            AgendaDAO adao = new AgendaDAO();
            int totalPend = adao.fetchTotalPendentes(campanha.getId());
            if(totalPend == 0) {
                campanha.setTermino(DateUtil.getInstance().getNow());
                campanha.setStatus(Campanha.CampanhaStatus.FINALIZADO);
                dao.update(campanha);
            }
        }

    }

    public void loadContactsFromFile(Long campanhaid, Long mensagemid, String filePath) {
        //Find the directory for the SD Card using the API
        //*Don't* hardcode "/sdcard"
        File textFile = new File(filePath);

        //Read text from file
        StringBuilder text = new StringBuilder();

        List<String> celulares = new ArrayList<String>();

        AgendaDAO adao = new AgendaDAO();
        try {
            BufferedReader br = new BufferedReader(new FileReader(textFile));
            String line;
            ContactDAO dao = new ContactDAO();
            while ((line = br.readLine()) != null) {
                Contact contact = new Contact();
                contact.setMobile(line);
                Long contactid = dao.insert(contact);
                contact.setId(contactid);
                Log.i(Heimder.APP_NAME, "contactid: " + contactid);

                String inicio = DateUtil.getInstance().getNow();
                Log.i(Heimder.APP_NAME, "adicionando contato: " + contact.getMobile());
                Agenda a = new Agenda();
                a.setInicio(inicio);
                a.setCampanhaid(campanhaid);
                a.setContatoid(contactid);

                Mensagem m = new Mensagem();
                m.setId( mensagemid );
                a.setMensagemid(mensagemid);
                a.setMensagem( m );
                adao.insert(a);

            }
            br.close();
        } catch (IOException e) {
            Toast.makeText(this.context, "Não consegui mandar nenhum SMS. Erro ao ler arquivo de celulares", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("Heimder", "Erro ao ler arquivo de celulares" + e.getMessage());
        }

    }

}
