package com.example.abhij.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abhij on 17-02-2018.
 */


public class OpenHelper extends SQLiteOpenHelper {


    private static  OpenHelper openHelper;

    public static OpenHelper getOpenHelperInstance(Context context)
    {
        if(openHelper==null)
        {
            openHelper=new OpenHelper(context);
        }
        return openHelper;



    }
   private OpenHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

         String createEventTable = "CREATE TABLE " + Contract.Events.TABLE_NAME + "("
                + Contract.Events.TITLE  +" TEXT , "
                + Contract.Events.CONTENT +" TEXT , "
                + Contract.Events.ID + " INTEGER PRIMARY KEY AUTOINCREMENT )";



         sqLiteDatabase.execSQL(createEventTable);

         String createCommnentTable = "CREATE TABLE " + Contract.Comments.TABLE_NAME + "( "
                 + Contract.Comments.ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "
                 + Contract.Comments.COMMENT + " TEXT , "
                 + Contract.Comments.EVENT_ID + " INTEGER )";

         sqLiteDatabase.execSQL(createCommnentTable);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
