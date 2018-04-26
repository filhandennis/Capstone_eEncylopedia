package com.develop.filhan.eencyclopediaone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ASUS on 23/04/2018.
 */

public class FavoriteHelper extends SQLiteOpenHelper {
    public FavoriteHelper(Context context) {
        super(context, DBConstructor.SQL_DB_NAME, null, DBConstructor.SQL_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE favlist(id INTEGER PRIMARY KEY AUTOINCREMENT, item INTEGER UNIQUE, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(sql);
        Log.d("SQLITE::FAVLIST","onCREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean deleteFav(int idItem){
        SQLiteDatabase db = getWritableDatabase();
        int delete = db.delete("favlist","item = "+idItem, null);
        Log.d("SQL:FAV:DELETE","itemID "+idItem);
        return delete>0;
    }
    public long addFav(int idItem){
        String sql = "INSERT INTO favlist(item) VALUES(?)";
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, idItem);

        long rowId = stmt.executeInsert();
        Log.d("SQL:FAV:INSERT","itemID "+idItem+", rowID "+rowId);

        return (rowId<0)?-1:rowId;
    }

    public ArrayList<Integer> selectAllItem(){
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor;
        SQLiteDatabase db = getReadableDatabase();
        cursor=db.rawQuery("SELECT * FROM favlist",null);
        cursor.moveToFirst();
        if(cursor.getCount()<1){return null;}
        while (cursor.isAfterLast()==false){
            int itemId = cursor.getInt(cursor.getColumnIndex("item"));
            list.add(itemId);
            cursor.moveToNext();
        }
        return list;
    }

    public Cursor findOne(int idItem){
        if(size()<1){return null;}
        Cursor cursor;
        SQLiteDatabase db = getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM favlist WHERE item = "+idItem,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){Log.d("SQL:FAV:FIND","FOUND! "+cursor.getInt(cursor.getColumnIndex("id")));}
        else{cursor=null;}
        return cursor;
    }

    public int size(){
        Cursor cursor;
        SQLiteDatabase db = getReadableDatabase();
        cursor = db.rawQuery("SELECT id FROM favlist", null);
        return cursor.getCount();
    }
}
