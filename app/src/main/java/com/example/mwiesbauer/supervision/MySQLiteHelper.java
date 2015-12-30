package com.example.mwiesbauer.supervision;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mwiesbauer on 12/30/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sitesuper.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_ACTIVITIES = "ACTIVITIES";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_START = "START";
    public static final String COLUMN_DURATION = "DURATION";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ACTIVITIES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_START + " TEXT, " +
                    COLUMN_DURATION + " INTEGER" +
                    ")";

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
        onCreate(db);
    }

}
