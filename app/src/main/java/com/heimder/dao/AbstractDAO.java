package com.heimder.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.heimder.domain.Domain;
import com.heimder.domain.Empresa;

import java.util.List;

/**
 * Created by JoaoEmilio on 28/12/2015.
 */
public abstract class AbstractDAO {
    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INTEGER_TYPE = " INTEGER ";
    protected static final String COMMA_SEP = ",";
    protected Context context;

    protected abstract class Table implements BaseColumns {
    }

    public List<Domain> fetchAll;

    public abstract Long insert(Domain domain);


}
