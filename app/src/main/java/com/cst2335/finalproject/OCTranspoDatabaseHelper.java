package com.cst2335.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OCTranspoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bus.db";
    public static final String TABLE_NAME = "Stops";
    public static final String KEY_ID = "_id";
    public static final String KEY_STATION_NUMBER = "NUMBER";
    public static final String KEY_STATION_NAME = "NAME";
    private static final int VERSION_NUM = 3;

    public OCTranspoDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_STATION_NAME + " text, " + KEY_STATION_NUMBER + " text)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
