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
import com.heimder.domain.Contact;
import com.heimder.domain.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoaoEmilio on 25/12/2015.
 */
public class ContactDAO extends AbstractDAO {

    public ContactDAO() {
        this.context = Heimder.getInstance().getContext();
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Table.TABLE_NAME + " (" +
                    Table._ID + " INTEGER PRIMARY KEY," +
                    Table.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_MOBILE + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    Table.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    Table.COLUMN_NAME_ENVIADO + " INTEGER " + COMMA_SEP +
                    Table.COLUMN_NAME_FACEBOOK + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Table.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static abstract class Table implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_NAME_CONTACT_ID = "contactid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MOBILE = "mobile";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FACEBOOK = "facebook";

        public static final String COLUMN_NAME_ENVIADO = "enviado";
    }

    public Long insert(Domain d) {
        Contact domain = (Contact)d;
        ContentValues cv = new ContentValues();
        Log.i(Heimder.APP_NAME, domain.getMobile());
        cv.put(Table.COLUMN_NAME_MOBILE, domain.getMobile());

        Long id = null;
        try {
            Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("select * from " + Table.TABLE_NAME + " where " + Table.COLUMN_NAME_MOBILE + " = '" + domain.getMobile() + "'",null) ;
            if(cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(Table._ID));
            } else {
                id = DBHelper.getInstance().getWritableDatabase().insert(Table.TABLE_NAME, null, cv);
            }
            Log.i(Heimder.APP_NAME, "insert contact id: " + id);
        }catch(Exception e) {
            Log.e("Heimder", "erro ao inserir registro", e.getCause());
            e.printStackTrace();
        }
        return id;
    }

    public List<Contact> fetchAll() {
        List<Contact> list = new ArrayList<Contact>();

        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery("select * from " + Table.TABLE_NAME, null);
        while(cursor.moveToNext()) {
            Contact domain = new Contact();
            domain.setId(cursor.getLong(cursor.getColumnIndex(Table._ID)));
            domain.setMobile(cursor.getString(cursor.getColumnIndex(Table.COLUMN_NAME_MOBILE)));
            list.add(domain);
        }

        return list;
    }

    public Contact findFirstContactNotSent() {
        String sql = "SELECT * FROM " + Table.TABLE_NAME + " where " + Table.COLUMN_NAME_ENVIADO + " is null limit 1";
        Log.i("heimder", sql);
        Cursor cursor = DBHelper.getInstance().getReadableDatabase().rawQuery(sql, null);

        Contact domain = null;
        if( cursor.moveToNext() ) {
            domain = new Contact();
            domain.setId(cursor.getLong(cursor.getColumnIndex(Table._ID)));
            Log.i("heimder","id: " + domain.getId().toString());
            domain.setMobile(cursor.getString(cursor.getColumnIndex(Table.COLUMN_NAME_MOBILE)));
        }
        return domain;
    }

    public void setEnviado(Contact domain) {
        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_NAME_ENVIADO, 1);
        String[] args = {domain.getId().toString()};
        Log.i("heimder", "id: " + domain.getId().toString());
        DBHelper.getInstance().getWritableDatabase().update(Table.TABLE_NAME, cv, Table._ID+"=?", args );
    }

}
