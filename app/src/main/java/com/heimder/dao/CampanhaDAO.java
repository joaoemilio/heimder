package com.heimder.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.heimder.Heimder;
import com.heimder.domain.Campanha;
import com.heimder.domain.Domain;
import com.heimder.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoaoEmilio on 25/12/2015.
 */
public class CampanhaDAO extends AbstractDAO {

    public CampanhaDAO() {
        this.context = Heimder.getInstance().getContext();
    }

    /* Inner class that defines the table contents */
    protected class Table implements BaseColumns {
        public static final String TABLE_NAME = "campanha";
        public static final String COLUMN_NAME_CAMPANHA_ID = "campanhaid";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_MENSAGEM_ID = "mensagenid";
        public static final String COLUMN_NAME_EMPRESA_ID = "empresaid";
        public static final String COLUMN_NAME_INICIO = "inicio";
        public static final String COLUMN_NAME_TERMINO = "termino";
        public static final String COLUMN_NAME_STATUS = "status";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Table.TABLE_NAME + " (" +
                    Table._ID + " INTEGER PRIMARY KEY," +
                    Table.COLUMN_NAME_CAMPANHA_ID + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_NOME + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_MENSAGEM_ID + INTEGER_TYPE + " NOT NULL " + COMMA_SEP +
                    Table.COLUMN_NAME_EMPRESA_ID + INTEGER_TYPE + " NOT NULL " + COMMA_SEP +
                    Table.COLUMN_NAME_INICIO + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_TERMINO + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_STATUS + INTEGER_TYPE + " DEFAULT " + Campanha.CampanhaStatus.AGENDADO +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Table.TABLE_NAME;

    public Long insert(Domain d) {
        Campanha domain = (Campanha)d;
        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_NAME_INICIO, DateUtil.getInstance().getNow());
        cv.put(Table.COLUMN_NAME_MENSAGEM_ID, domain.getMensagemid());
        cv.put(Table.COLUMN_NAME_EMPRESA_ID, domain.getEmpresaid());
        cv.put(Table.COLUMN_NAME_NOME, domain.getNome());

        Long id = null;
        try {
            id = DBHelper.getInstance().getWritableDatabase().insert(Table.TABLE_NAME, null, cv);
        }catch(Exception e) {
            Log.e("Heimder", "erro ao inserir registro", e.getCause());
            e.printStackTrace();
        }
        return id;
    }

    public void update(Campanha campanha) {
        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_NAME_STATUS, campanha.getStatus());
        cv.put(Table.COLUMN_NAME_TERMINO, campanha.getTermino());
        String[] args = {campanha.getId().toString()};
        Log.i("heimder", "id: " + campanha.getId().toString());
        DBHelper.getInstance().getWritableDatabase().update(Table.TABLE_NAME, cv, Table._ID+"=?", args );

    }


    public List<Campanha> fetchAll() {
        List<Campanha> list = new ArrayList<Campanha>();

        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("select * from " + Table.TABLE_NAME, null);
        while(cursor.moveToNext()) {
            Campanha campanha = new Campanha();
            campanha = getCampanha(cursor);
            list.add(campanha);
        }

        return list;
    }

    public List<Campanha> fetchCampanhasPendentes() {
        List<Campanha> list = new ArrayList<Campanha>();

        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("select * from " + Table.TABLE_NAME +
                " where " + Table.COLUMN_NAME_STATUS + " <> " + Campanha.CampanhaStatus.FINALIZADO +
                " and " + Table.COLUMN_NAME_TERMINO + " is null", null);
        while(cursor.moveToNext()) {
            Campanha campanha = new Campanha();
            campanha = getCampanha(cursor);
            list.add(campanha);
        }

        return list;
    }

    private Campanha getCampanha(Cursor cursor) {
        Campanha e = new Campanha();
        e.setId(cursor.getLong(cursor.getColumnIndex(Table._ID)));
        e.setNome(cursor.getString(cursor.getColumnIndex(Table.COLUMN_NAME_NOME)));
        e.setMensagemid(cursor.getLong(cursor.getColumnIndex(Table.COLUMN_NAME_MENSAGEM_ID)));
        e.setEmpresaid(cursor.getLong(cursor.getColumnIndex(Table.COLUMN_NAME_EMPRESA_ID)));
        e.setInicio(cursor.getString(cursor.getColumnIndex(Table.COLUMN_NAME_INICIO)));
        e.setTermino(cursor.getString(cursor.getColumnIndex(Table.COLUMN_NAME_TERMINO)));
        e.setStatus(cursor.getInt(cursor.getColumnIndex(Table.COLUMN_NAME_STATUS)));
        return e;
    }

}
