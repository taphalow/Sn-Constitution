package com.ut.sn.citoyensn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Favoris.db";
    private static final int DATABASE_VERSION=1;


    public DatabaseManager(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String s="create table T_Articles ("
                + "   indexArticle integer primary key autoincrement"
                + ")";
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String s="drop table T_Articles";
        db.execSQL(s);
        this.onCreate(db);
    }
    public void insertAtilce(int index){
        String s="insert into T_Articles (indexArticle) values ("+ index +")";
        this.getWritableDatabase().execSQL(s);
    }
    public List<Integer> readArticles(){
        List<Integer> idArtilces=new ArrayList<>();
        String s="select * from T_Articles order by indexArticle asc";
        Cursor cursor=this.getReadableDatabase().rawQuery(s,null);
        cursor.moveToFirst();
        while ( ! cursor.isAfterLast()){
            idArtilces.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();
        return idArtilces;
    }

    public boolean searchArtcile(int index){
        String s="select * from T_Articles";
        Cursor cursor=this.getReadableDatabase().rawQuery(s,null);
        cursor.moveToFirst();
        while ( ! cursor.isAfterLast()) {
            if(cursor.getInt(0)==index)
                return true;
            cursor.moveToNext();
        }
        cursor.close();
        return false;
    }
    public void deleteArticle(int index){
        String s="delete from T_Articles where indexArticle = "+index;
        this.getWritableDatabase().execSQL(s);
    }


}
