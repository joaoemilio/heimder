package com.heimder.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.heimder.Heimder;
import com.heimder.domain.Agenda;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static Integer DB_VERSION = 13;
    protected static final String DATABASE = "Heimder.db";

    private static DBHelper me;
    public DBHelper() {
        super(Heimder.getInstance().getContext(), DATABASE, null, DB_VERSION);
        Log.i(Heimder.APP_NAME, "context: " + Heimder.getInstance().getContext());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(Heimder.APP_NAME, AgendaDAO.SQL_CREATE_ENTRIES);
        db.execSQL(AgendaDAO.SQL_CREATE_ENTRIES);
        db.execSQL("CREATE UNIQUE INDEX IN_AGE_CON_MEN on " + AgendaDAO.Table.TABLE_NAME + " ( mensagemid, contatoid )");
        Log.i(Heimder.APP_NAME, CampanhaDAO.SQL_CREATE_ENTRIES);
        db.execSQL(CampanhaDAO.SQL_CREATE_ENTRIES);
        Log.i(Heimder.APP_NAME, ContactDAO.SQL_CREATE_ENTRIES);
        db.execSQL(ContactDAO.SQL_CREATE_ENTRIES);
        Log.i(Heimder.APP_NAME, EmpresaDAO.SQL_CREATE_ENTRIES);
        db.execSQL(EmpresaDAO.SQL_CREATE_ENTRIES);
        Log.i(Heimder.APP_NAME, MensagemDAO.SQL_CREATE_ENTRIES);
        db.execSQL(MensagemDAO.SQL_CREATE_ENTRIES);
        Log.i(Heimder.APP_NAME, ConfigDAO.SQL_CREATE_ENTRIES);
        db.execSQL(ConfigDAO.SQL_CREATE_ENTRIES);

        db.execSQL("insert into " + EmpresaDAO.Table.TABLE_NAME + "( " + EmpresaDAO.Table.COLUMN_NAME_NAME + " ) values ( '" + "DiskPicole" + "')");
        db.execSQL("insert into " + ConfigDAO.Table.TABLE_NAME + " ( " + ConfigDAO.Table.COLUMN_NAME_INTERVALO + ") values(10)");
        db.execSQL("insert into " + MensagemDAO.Table.TABLE_NAME + " (conteudo) values ('Tah calor neh? Que tal um picoleh? Entregamos igual pizza. http://www.festadopicole.com.br')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( newVersion == 13) {
            db.execSQL( AgendaDAO.SQL_DELETE_ENTRIES);
            db.execSQL( CampanhaDAO.SQL_DELETE_ENTRIES );
            db.execSQL( ContactDAO.SQL_DELETE_ENTRIES );
            db.execSQL( EmpresaDAO.SQL_DELETE_ENTRIES );
            db.execSQL( MensagemDAO.SQL_DELETE_ENTRIES );
            db.execSQL( ConfigDAO.SQL_DELETE_ENTRIES );
            onCreate(db);
        } else if( newVersion == 12) {
                db.execSQL( "delete from " + CampanhaDAO.Table.TABLE_NAME );
                db.execSQL( "delete from " + MensagemDAO.Table.TABLE_NAME );
                db.execSQL( "delete from " + AgendaDAO.Table.TABLE_NAME );
                db.execSQL( "delete from " + ContactDAO.Table.TABLE_NAME );
            try {
                db.execSQL("drop index index_name");
            }catch(Exception e) {
                e.printStackTrace();
            }
                db.execSQL("CREATE UNIQUE INDEX IN_AGE_CON_MEN on " + AgendaDAO.Table.TABLE_NAME + " ( mensagemid, contatoid )");
        }else if(newVersion == 10) {
            db.execSQL( ConfigDAO.SQL_CREATE_ENTRIES );
            db.execSQL("insert into " + ConfigDAO.Table.TABLE_NAME + " (intervalo) values (5)");
        } else {
            onCreate(db);
        }
        //db.execSQL( AgendaDAO.SQL_DELETE_ENTRIES);
        //db.execSQL( CampanhaDAO.SQL_DELETE_ENTRIES );
        //db.execSQL( ContactDAO.SQL_DELETE_ENTRIES );
        //db.execSQL( EmpresaDAO.SQL_DELETE_ENTRIES );
        //db.execSQL( MensagemDAO.SQL_DELETE_ENTRIES );
        //onCreate(db);
    }

    public static DBHelper getInstance() {
        if(me == null) {
            me = new DBHelper();
        }
        Log.i(Heimder.APP_NAME, "DBHelper.getInstance() context: " + Heimder.getInstance().getContext() );
        return me;
    }
}
