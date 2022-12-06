package com.example.app_body_mass_index;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseOpenHelper extends SQLiteOpenHelper
{
    public MaBaseOpenHelper(Context context, String nom, CursorFactory cursorfactory, int version)
    {
        super(context, nom, cursorfactory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table user (id integer primary key autoincrement, login text unique not null, fname text not null, lname text not null, password text not null );");
        db.execSQL("create table imc (id integer primary key autoincrement, login text, datei text not null, poid int not null, taille int not null, imc text not null );");
        db.execSQL("create table cours (id integer primary key autoincrement, login text, nomc text not null, timed text, timef text, datec text);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table user;");
        db.execSQL("drop table cours;");
        db.execSQL("drop table imc;");
        onCreate(db);
    }
}