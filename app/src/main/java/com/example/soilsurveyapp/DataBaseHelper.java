package com.example.soilsurveyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.BoringLayout;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    //initialize the name of database name
    public static final String DATABASE_NAME= "loginDB.db";

    //below is constructor
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    //"onCreate" means creating table name and fields name in Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT )");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST user");
    }

    //creating methods for INSERTING and CHECKING the existing user

    public boolean insert(String username, String password){          //when the user input the data this method is called
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result= sqLiteDatabase.insert("user",null,contentValues);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }
    //below method is for checking the existing user name that is exist or not
    public Boolean checkUsername(String username){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=?", new String[]{username});
        if(cursor.getCount()>0){
            return false;
        }else {
            return true;
        }
    }

    //below code is for checking the "username and password with database" if the exist the login is successful
    public Boolean checkLogin(String username, String password){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[]{username, password});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
}
