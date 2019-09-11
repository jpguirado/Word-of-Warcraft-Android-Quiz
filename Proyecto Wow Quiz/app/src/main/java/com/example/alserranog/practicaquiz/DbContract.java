package com.example.alserranog.practicaquiz;

/**
 * Created by juanp on 09/11/2018.
 */

import android.provider.BaseColumns;

public class DbContract {

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbContract.DbEntry.TABLE_NAME + " (" +
                    DbContract.DbEntry._ID + " INTEGER PRIMARY KEY," +
                    DbContract.DbEntry.COLUMN_NAME_PLAYER + " TEXT," +
                    DbContract.DbEntry.COLUMN_NAME_RESULT + " TEXT)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbContract.DbEntry.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbContract() {}

    /* Inner class that defines the table contents */
    static class DbEntry implements BaseColumns {
        static final String TABLE_NAME = "game";
        static final String COLUMN_NAME_PLAYER = "player";
        static final String COLUMN_NAME_RESULT = "result";
    }

}
