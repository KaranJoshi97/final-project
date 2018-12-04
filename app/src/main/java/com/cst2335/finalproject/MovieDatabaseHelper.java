package com.cst2335.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    /*
     * Variables for data type int and string
     */
    private static final String DATABASE_NAME = "movies.db";    // Database name
    public static final String TABLE_NAME = "Info";             // Table name
    public static final String KEY_ID = "_id";                  // Primary key
    public static final String KEY_TITLE = "TITLE";             // Title
    public static final String KEY_YEAR = "YEAR";               // Year
    public static final String KEY_RATING = "RATING";           // Rating
    public static final String KEY_RUNTIME = "RUNTIME";         // Runtime
    public static final String KEY_ACTORS = "ACTORS";           // Actors
    public static final String KEY_PLOT = "PLOT";               // Plot
    public static final String KEY_POSTER = "POSTER";           // Poster
    private static final int VERSION_NUM = 1;                   // Database version


    public MovieDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    /*
     * This function is used to creating the table
     */
    public void onCreate(SQLiteDatabase db){
        // This function is creating the table
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " text, " + KEY_YEAR + " text, " + KEY_RATING + " text, " + KEY_RUNTIME + " text, " + KEY_ACTORS + " text, " + KEY_PLOT + " text, " + KEY_POSTER + " text)");
        Log.i("MovieDatabaseHelper", "Calling onCreate");
    }

    @Override

    /*
     * This function is used to upgrading the table
     */
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("MovieDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }

}