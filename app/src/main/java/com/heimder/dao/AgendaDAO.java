package com.heimder.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.heimder.Heimder;
import com.heimder.domain.Agenda;
import com.heimder.domain.Contact;
import com.heimder.domain.Domain;
import com.heimder.domain.Empresa;
import com.heimder.domain.Mensagem;

/**
 * Created by JoaoEmilio on 25/12/2015.
 */
public class AgendaDAO extends AbstractDAO {

    public AgendaDAO() {
        this.context = Heimder.getInstance().getContext();
    }

    /* Inner class that defines the table contents */
    protected class Table implements BaseColumns {
        public static final String TABLE_NAME = "agenda";
        public static final String COLUMN_NAME_AGENDA_ID = "agendaid";
        public static final String COLUMN_NAME_MENSAGEM_ID = "mensagemid";
        public static final String COLUMN_NAME_CONTATO_ID = "contatoid";
        public static final String COLUMN_NAME_CAMPANHA_ID = "campanhaid";
        public static final String COLUMN_NAME_INICIO = "inicio";
        public static final String COLUMN_NAME_TERMINO = "termino";
        public static final String COLUMN_NAME_STATUS = "status";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Table.TABLE_NAME + " (" +
                    Table._ID + " INTEGER PRIMARY KEY," +
                    Table.COLUMN_NAME_AGENDA_ID + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_MENSAGEM_ID + INTEGER_TYPE + " NOT NULL " + COMMA_SEP +
                    Table.COLUMN_NAME_CONTATO_ID + INTEGER_TYPE + " NOT NULL " + COMMA_SEP +
                    Table.COLUMN_NAME_CAMPANHA_ID + INTEGER_TYPE + " NOT NULL " + COMMA_SEP +
                    Table.COLUMN_NAME_INICIO + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_TERMINO + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_STATUS + INTEGER_TYPE + " DEFAULT " + Agenda.AgendaStatus.AGENDADO +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Table.TABLE_NAME;

    public Long insert(Domain d) {
        Agenda domain = (Agenda) d;
        ContentValues cv = new ContentValues();
        Log.i(Heimder.APP_NAME, "mensagem id (Agenda DAO): " + domain.getMensagemid());
        cv.put(Table.COLUMN_NAME_INICIO, domain.getInicio());
        cv.put(Table.COLUMN_NAME_MENSAGEM_ID, domain.getMensagemid());
        cv.put(Table.COLUMN_NAME_CAMPANHA_ID, domain.getCampanhaid());
        cv.put(Table.COLUMN_NAME_CONTATO_ID, domain.getContatoid());

        Long id = null;
        try {
            id = DBHelper.getInstance().getWritableDatabase().insert(Table.TABLE_NAME, null, cv);
        }catch(Exception e) {
            Log.e("Heimder", "erro ao inserir registro", e.getCause());
            e.printStackTrace();
        }
        return id;
    }

    public Agenda findFirstContactNotSent() {
        String sql = "SELECT C." + ContactDAO.Table.COLUMN_NAME_MOBILE + " mobile " + COMMA_SEP + "A." + Table._ID + COMMA_SEP + "M." + MensagemDAO.Table.COLUMN_NAME_CONTEUDO + " msg " +
                " FROM " + Table.TABLE_NAME + " A, " + ContactDAO.Table.TABLE_NAME + " C " + COMMA_SEP + MensagemDAO.Table.TABLE_NAME + " M" +
                " WHERE A." + Table.COLUMN_NAME_STATUS + " = " + Agenda.AgendaStatus.AGENDADO +
                " AND A." + Table.COLUMN_NAME_CONTATO_ID + " = C." + ContactDAO.Table._ID + " AND M." + MensagemDAO.Table._ID + " = A." + Table.COLUMN_NAME_MENSAGEM_ID +
                " limit 1";
        Log.i("heimder", sql);
        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery(sql, null);

        Agenda a = null;
        if( cursor.moveToNext() ) {
            Contact c = new Contact();
            Mensagem m = new Mensagem();
            c.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
            m.setConteudo(cursor.getString(cursor.getColumnIndex("msg")));
            a = new Agenda();
            a.setId(cursor.getLong(cursor.getColumnIndex(Table._ID)));
            a.setContact(c);
            a.setMensagem(m);
        }
        return a;
    }

    public Integer fetchTotalContatos(Long id) {
        String sql = "SELECT COUNT(*) FROM " + Table.TABLE_NAME +
                " WHERE " + Table.COLUMN_NAME_CAMPANHA_ID + " = "  + id;

        Log.i("heimder", sql);
        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery(sql, null);
        Integer total = 0;
        if( cursor.moveToNext() ) {
            total = cursor.getInt(0);
        }
        return total;
    }

    public Integer fetchTotalEnviados(Long id) {
        String sql = "SELECT COUNT(*) FROM " + Table.TABLE_NAME +
                " WHERE " + Table.COLUMN_NAME_CAMPANHA_ID + " = "  + id +
                " AND " + Table.COLUMN_NAME_STATUS + " = " + Agenda.AgendaStatus.FINALIZADO;

        Log.i("heimder", sql);
        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery(sql, null);
        Integer total = 0;
        if( cursor.moveToNext() ) {
            total = cursor.getInt(0);
        }
        return total;
    }

    public Integer fetchTotalPendentes(Long id) {
        String sql = "SELECT COUNT(*) FROM " + Table.TABLE_NAME +
                " WHERE " + Table.COLUMN_NAME_CAMPANHA_ID + " = "  + id +
                " AND " + Table.COLUMN_NAME_STATUS + " = " + Agenda.AgendaStatus.AGENDADO;

        Log.i("heimder", sql);
        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery(sql, null);
        Integer total = 0;
        if( cursor.moveToNext() ) {
            total = cursor.getInt(0);
        }
        return total;
    }

    public void setStatus(Agenda domain, Integer status) {
        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_NAME_STATUS, status);
        String[] args = {domain.getId().toString()};
        Log.i("heimder", "id: " + domain.getId().toString());
        DBHelper.getInstance().getWritableDatabase().update(Table.TABLE_NAME, cv, Table._ID+"=?", args );
    }

}
