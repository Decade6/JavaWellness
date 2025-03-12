/*
    Database for Exercises
 */
package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class ExerciseProvider extends ContentProvider {
    public final static String DBNAME = "WellnessApp";

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    };

    private MainDatabaseHelper mOpenHelper;
    public final static String TABLE_NAMESTABLE = "Exercises";
    public static final String AUTHORITY = "provider";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY +"/" + TABLE_NAMESTABLE);

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " + TABLE_NAMESTABLE +
            "(" +
            " _ID INTEGER PRIMARY KEY, " +
            "description" + " TEXT," +
            "calories" + " REAL," +
            "duration" + " INTEGER," +
            "time" + " TEXT," +
            "type" + " TEXT)";

    public ExerciseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return mOpenHelper.getWritableDatabase().delete(TABLE_NAMESTABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        String description = values.getAsString("description").trim();
        double calories = 0;
        int duration = 0;
        try {
            calories = values.getAsDouble("calories");
        } catch (Exception e) {
            // catches error
        }
        try {
            duration = values.getAsInteger("duration");
        } catch (Exception e) {
            // catches error
        }
        String time = values.getAsString("time").trim();
        String type = values.getAsString("type").trim();

        long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAMESTABLE,null,values);
        return Uri.withAppendedPath(CONTENT_URI,"" +id);

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        return mOpenHelper.getReadableDatabase().query(TABLE_NAMESTABLE, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return mOpenHelper.getWritableDatabase().update(TABLE_NAMESTABLE, values, selection, selectionArgs);
    }
}