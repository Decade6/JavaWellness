//database for shopping item
package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class ShoppingItemProvider extends ContentProvider {
    public final static String DBNAME = "ShoppingItems";

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
    public final static String TABLE_NAMESTABLE = "ShoppingItems";
    public static final String AUTHORITY = "sprovider";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY +"/" + TABLE_NAMESTABLE);

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " + TABLE_NAMESTABLE +
            "(" +
            " _ID INTEGER PRIMARY KEY, " +
            "Name" + " TEXT," +
            "Info" + " TEXT," +
            "Quantity" + " INTEGER)";

    public ShoppingItemProvider() {
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
        String name = values.getAsString("Name").trim();
        String info = values.getAsString("Info").trim();
        int quantity = values.getAsInteger("Quantity");


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
        return mOpenHelper.getReadableDatabase().query(TABLE_NAMESTABLE, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String name = values.getAsString("Name").trim();
        String info = values.getAsString("Info").trim();
        int quantity = values.getAsInteger("Quantity");
        System.out.println(name + ", " + info + ", " + quantity);
        System.out.println(selection);
        return mOpenHelper.getWritableDatabase().update(TABLE_NAMESTABLE, values, selection, selectionArgs);
    }
}