package com.example.soilsurveyapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager minst;
    private static Context mct;

    private static final String SHARED_PRE_NAME = "mypref";
    private static final String KEY_ID="id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private SharedPrefManager(Context context){
        mct=context;
    }
    public static synchronized SharedPrefManager getInstans(Context context){
        if (minst==null){
            minst=new SharedPrefManager(context);
        }
        return minst;
    }
    public boolean userLogin(String id,String name, String email,String password){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ID,id);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.apply();
        return true;
    }
    public boolean isLogin(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_NAME,null)!=null){
            return true;
        }
        return false;
    }
//    public boolean logout(){
//        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//        return true;
//
//    }
//    public String getUserId(){
//        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_ID,null);
//
//    }
//    public String getUsername(){
//        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_USERNAME,null);
//
//    }
//    public String getUserEmail(){
//        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_PASSWORD,null);
//
//    }
}
