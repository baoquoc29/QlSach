package com.example.qlsach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final String TABLE_NAME = "Books";
    private static final String COLUMN_BOOK_ID = "book_id";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PAGE = "page";
    private static final String COLUMN_NAME = "book_name";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_BOOK_ID + " INTEGER, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PRICE + " FLOAT, " +
            COLUMN_PAGE + " INTEGER, " +
            COLUMN_DESCRIPTION + " TEXT);";



    public SqliteHelper(@Nullable Context context) {
       super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insertData(int id, String book_name, int page, float price, String des) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_ID, id);
        contentValues.put(COLUMN_NAME, book_name);
        contentValues.put(COLUMN_PAGE, page);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_DESCRIPTION, des);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long newRowId = -1;

        try {
            newRowId = sqLiteDatabase.insertOrThrow(TABLE_NAME, null, contentValues);
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.close();
        }

        return newRowId;
    }

    public List<Books> display_view(){
        List<Books> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] list_columns = {
                COLUMN_DESCRIPTION,COLUMN_PAGE,COLUMN_NAME,COLUMN_BOOK_ID,COLUMN_PRICE
        };
        Cursor cursor = db.query(TABLE_NAME,list_columns,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
            String book_name = cursor.getString((cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            int page = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAGE));
            float price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
            String des = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            list.add(new Books(id,book_name,page,price,des));
        }
        cursor.close();
        db.close();
        return list;
    }
    public void deleteColumns(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_BOOK_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
    public void updateColumn(int id, String newDescription, float newPrice, String newBookName,int newPage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, newDescription);
        values.put(COLUMN_PRICE, newPrice);
        values.put(COLUMN_NAME, newBookName);
        values.put(COLUMN_PAGE,newPage);
        String whereClause = COLUMN_BOOK_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }
}
