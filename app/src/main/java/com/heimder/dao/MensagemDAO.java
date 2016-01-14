package com.heimder.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.heimder.Heimder;
import com.heimder.domain.Domain;
import com.heimder.domain.Empresa;
import com.heimder.domain.Mensagem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoaoEmilio on 25/12/2015.
 */
public class MensagemDAO  extends AbstractDAO {

    public MensagemDAO() {
        this.context = Heimder.getInstance().getContext();
    }

    /* Inner class that defines the table contents */
    protected class Table implements BaseColumns {
        public static final String TABLE_NAME = "mensagem";
        public static final String COLUMN_NAME_MENSAGEM_ID = "mensagemid";
        public static final String COLUMN_NAME_CONTEUDO = "conteudo";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Table.TABLE_NAME + " (" +
                    Table._ID + " INTEGER PRIMARY KEY," +
                    Table.COLUMN_NAME_CONTEUDO + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Table.TABLE_NAME;

    public Long insert(Domain d) {
        Mensagem domain = (Mensagem)d;
        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_NAME_CONTEUDO, domain.getConteudo());
        Long id = null;
        try {
            id = DBHelper.getInstance().getWritableDatabase().insert(Table.TABLE_NAME, null, cv);
            Log.i(Heimder.APP_NAME, "mensagem id: " + id);
        }catch(Exception e) {
            Log.e("Heimder", "erro ao inserir registro", e.getCause());
            e.printStackTrace();
        }
        return id;
    }

    public Mensagem findMensagemById( Long id ) {
        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("SELECT * FROM " + Table.TABLE_NAME +
            " WHERE " + Table._ID + " = " + id.toString(), null );

        Mensagem m = null;
        if(cursor.moveToNext()) {
            m = new Mensagem();
            m.setConteudo( cursor.getString( cursor.getColumnIndex(Table.COLUMN_NAME_CONTEUDO)));
            m.setId(id);
        }

        return m;
    }

    public List<Mensagem> fetchAll() {
        List<Mensagem> list = new ArrayList<Mensagem>();

        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("select * from " + Table.TABLE_NAME, null);
        while(cursor.moveToNext()) {
            Mensagem e = new Mensagem();
            e.setId(cursor.getLong(cursor.getColumnIndex(Table._ID)));
            e.setConteudo(cursor.getString(cursor.getColumnIndex(Table.COLUMN_NAME_CONTEUDO)));
            list.add(e);
        }

        return list;
    }
}
