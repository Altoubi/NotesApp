package com.altoubi.muthakerat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by altou on 28/07/2016.
 */
public class DB_Helper extends SQLiteOpenHelper {
    // Database
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "Muthakerat";
    private static final String TABLE_ITEMS = "items_m";
    public static final String ITEM_ID = "_id";
    public static final String ITEM_TITLE = "title";
    public static final String ITEM_TEXT = "text";
    public static final String ITEM_ADDED = "added";
    // table sql code
    private static final String DATABASE_TABLE = "CREATE TABLE " + TABLE_ITEMS + " ( " +
            ITEM_ID + "  INTEGER PRIMARY KEY, " +
            ITEM_TITLE + " text, " +
            ITEM_TEXT + " text, " +
            ITEM_ADDED + " text " +
            " );";


    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
    }


    // get all data from database as cursor
    public Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    // adding new  note

    public void addNew(String title, String Text) {

        // get time to save note added time
        Calendar c = Calendar.getInstance();
        long time= System.currentTimeMillis();


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // put value (columns,data)
        values.put("title", title);
        values.put("text", Text);
        values.put("added", time);
        // insert query
        db.insert(TABLE_ITEMS, null, values);
    }

    // update an notes by passing Note id, Note Title, Note text
       public boolean updateItem(int noteID, String noteTitle, String noteText) {
        SQLiteDatabase db = this.getWritableDatabase();
        // SQL Query for updateing ...
        db.execSQL("UPDATE "+TABLE_ITEMS+" SET text = '"+noteText+"', title ='"+noteTitle+"' WHERE "+ITEM_ID+"="+noteID);
        return true;
    }

    // delete from database by ID
    public boolean deleteItem(int noteID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEMS, ITEM_ID + "=" + noteID, null) > 0;
    }
}
