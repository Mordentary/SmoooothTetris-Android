package com.e.testgame;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.e.testgame.GameClasses.PlayerStatistic;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "PlayerScore.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table playerScore(ID integer primary key autoincrement, score TEXT, name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists playerScore");
    }

    public Boolean insertUserData(String name, String score)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("score", score);
        contentValues.put("name", name);
        long result=DB.insert("playerScore", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    /**
     *  Gets information about the players from the database.
     */
    public List<PlayerStatistic> getEveryPlayer(){

        List<PlayerStatistic> returnList= new ArrayList<>();
        String queryString=" SELECT * FROM "+ "playerScore";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString, null );
        if(cursor.moveToFirst()) {
            do {
                String playerScore = cursor.getString(1);
                String playerName = cursor.getString(2);
                PlayerStatistic customerNew = new PlayerStatistic(playerScore,playerName);
                returnList.add(customerNew);
            }
            while (cursor.moveToNext());
        }
        else{}
        cursor.close();
        db.close();
        return returnList;
    }

}