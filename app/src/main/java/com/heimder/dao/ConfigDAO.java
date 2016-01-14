package com.heimder.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.heimder.Heimder;
import com.heimder.domain.Domain;
import com.heimder.domain.Mensagem;

/**
 * Created by JoaoEmilio on 25/12/2015.
 */
public class ConfigDAO extends AbstractDAO {

    public ConfigDAO() {
        this.context = Heimder.getInstance().getContext();
    }

    @Override
    public Long insert(Domain domain) {
        return null;
    }

    /* Inner class that defines the table contents */
    protected class Table implements BaseColumns {
        public static final String TABLE_NAME = "config";
        public static final String COLUMN_NAME_INTERVALO = "intervalo";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Table.TABLE_NAME + " (" +
                    Table._ID + " INTEGER PRIMARY KEY," +
                    Table.COLUMN_NAME_INTERVALO + INTEGER_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Table.TABLE_NAME;

    public void setIntervalo(Integer intervalo) {

        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_NAME_INTERVALO, intervalo);
        Log.i(Heimder.APP_NAME, "intervalo: " + intervalo);

        DBHelper.getInstance().getWritableDatabase().update(Table.TABLE_NAME, cv, "_id=1", null);
    }

    public Integer getIntervalo() {
        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("SELECT * FROM " + Table.TABLE_NAME + " where " + Table._ID + " = 1", null );
        Integer intervalo = 5;
        if(cursor.moveToNext()) {
            intervalo = cursor.getInt(cursor.getColumnIndex(Table.COLUMN_NAME_INTERVALO));
        }
        return intervalo;
    }

}
