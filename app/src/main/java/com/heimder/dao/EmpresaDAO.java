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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoaoEmilio on 25/12/2015.
 */
public class EmpresaDAO extends AbstractDAO {

    public EmpresaDAO() {
        this.context = Heimder.getInstance().getContext();
    }

    /* Inner class that defines the table contents */
    protected class Table implements BaseColumns {
        public static final String TABLE_NAME = "empresa";
        public static final String COLUMN_NAME_EMPRESA_ID = "empresaid";
        public static final String COLUMN_NAME_NAME = "nome";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Table.TABLE_NAME + " (" +
                    Table._ID + " INTEGER PRIMARY KEY," +
                    Table.COLUMN_NAME_EMPRESA_ID + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    Table.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Table.TABLE_NAME;

    public Long insert(Domain d) {
        Empresa domain = (Empresa)d;
        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_NAME_NAME, domain.getNome());
        Long id = null;
        try {
            id = DBHelper.getInstance().getWritableDatabase().insert(Table.TABLE_NAME, null, cv);
        }catch(Exception e) {
            Log.e("Heimder", "erro ao inserir registro", e.getCause());
            e.printStackTrace();
        }
        return id;
    }

    public List<Empresa> fetchAll() {
        List<Empresa> list = new ArrayList<Empresa>();

        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("select * from " + Table.TABLE_NAME, null);
        while(cursor.moveToNext()) {
            Empresa e = new Empresa();
            e.setId(cursor.getLong(cursor.getColumnIndex(Table._ID)));
            e.setNome(cursor.getString(cursor.getColumnIndex(Table.COLUMN_NAME_NAME)));
            list.add(e);
        }

        return list;
    }
}
