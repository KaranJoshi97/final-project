package com.cst2335.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OCTranspoDatabaseHelper extends SQLiteOpenHelper {


    /**
     * Variables for data type int and string
     */
    private static final String DATABASE_NAME = "bus.db";        // Database name
    public static final String TABLE_NAME = "Stops";             // Table name
    public static final String KEY_ID = "_id";                   // Primary key
    public static final String KEY_STATION_NUMBER = "NUMBER";    // Number
    public static final String KEY_STATION_NAME = "NAME";        // Name
    private static final int VERSION_NUM = 3;                    // Database version


    /*
     * Context ctx – the Activity where the database is being opened
     * String DATABASE_NAME - The file that wil contain the data
     * CursorFactory – An object to create Cursor objects, normally this is null
     * int VERSION_NUM -  What is the version of your database
     */
    public OCTranspoDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    /**
     * This function is used to creating the table
     * db.execSQL is a function that executes a string SQL statement.
     */
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_STATION_NAME + " text, " + KEY_STATION_NUMBER + " text)");
    }

    @Override
    /**
     * This function is used to upgrading the table
     * db.execSQL is a function that deletes the data
     */
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

} // End Class OCTranspoDatabaseHelper