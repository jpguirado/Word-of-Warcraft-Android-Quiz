package com.example.alserranog.practicaquiz;

/**
 * Created by juanp on 09/11/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

public class DbManager implements Serializable {

    private DbHelper helper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.helper = new DbHelper(context);
        this.db = this.helper.getWritableDatabase();
    }

    public void insertEntry(String playerName, String numResult){
        this.db.insert(DbContract.DbEntry.TABLE_NAME, null,
                this.generateContentValues(playerName, numResult));
    }

    public Cursor getEntries () {
        String[] columns = new String[]{
                DbContract.DbEntry.COLUMN_NAME_PLAYER,
                DbContract.DbEntry.COLUMN_NAME_RESULT};
        return db.query(DbContract.DbEntry.TABLE_NAME, columns, null, null,
                null, null, null);
    }

    public void deleteAll() {
        this.db.delete(DbContract.DbEntry.TABLE_NAME, null,null);
    }

    private ContentValues generateContentValues(String playerName, String numResult){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_PLAYER, playerName);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_RESULT, numResult);
        return contentValues;
    }
}
