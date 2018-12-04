package com.cst2335.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    public static final String TABLE_NAME = "Info";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_YEAR = "YEAR";
    public static final String KEY_RATING = "RATING";
    public static final String KEY_RUNTIME = "RUNTIME";
    public static final String KEY_ACTORS = "ACTORS";
    public static final String KEY_PLOT = "PLOT";
    public static final String KEY_POSTER = "POSTER";
    private static final int VERSION_NUM = 1;

    public MovieDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " text, " + KEY_YEAR + " text, " + KEY_RATING + " text, " + KEY_RUNTIME + " text, " + KEY_ACTORS + " text, " + KEY_PLOT + " text, " + KEY_POSTER + " text)");
        Log.i("MovieDatabaseHelper", "Calling onCreate");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("MovieDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }

}

