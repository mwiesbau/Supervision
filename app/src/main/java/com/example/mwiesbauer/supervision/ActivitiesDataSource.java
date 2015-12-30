package com.example.mwiesbauer.supervision;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwiesbauer on 12/30/2015.
 */
public class ActivitiesDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME,
                                   MySQLiteHelper.COLUMN_START, MySQLiteHelper.COLUMN_DURATION };


    public ActivitiesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Activity createActivity(String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        long insertId = database.insert(MySQLiteHelper.TABLE_ACTIVITIES, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ACTIVITIES, allColumns,
                                    MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Activity newActivity = cursorToActivity(cursor);
        cursor.close();
        return newActivity;
    }

    public void deleteActivity(Activity activity) {
        long id = activity.getId();
        System.out.println("Activity deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ACTIVITIES, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }



    public List<Activity> getAllActivities() {
        List<Activity> activities = new ArrayList<Activity>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ACTIVITIES, allColumns, null, null, null,
                null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Activity activity = cursorToActivity(cursor);
            activities.add(activity);
            cursor.moveToNext();
        } // end while

        cursor.close();
        return activities;
    }


    private Activity cursorToActivity(Cursor cursor) {
        Activity activity = new Activity();
        activity.setId(cursor.getLong(0));
        activity.setName(cursor.getString(1));
        return activity;
    }


}
