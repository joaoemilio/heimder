package com.heimder.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.heimder.Heimder;
import com.heimder.domain.Contact;
import com.heimder.domain.Domain;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class CampanhaQueries extends  AbstractDAO {

    private static String QUERY_ALL_CONTACTS = "select mobile from " + ContactDAO.Table.TABLE_NAME;

    public CampanhaQueries(){
        this.context = Heimder.getInstance().getContext();    }

    @Override
    public Long insert(Domain domain) {
        return null;
    }
}
