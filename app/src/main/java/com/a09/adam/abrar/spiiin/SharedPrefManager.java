package com.a09.adam.abrar.spiiin;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by abrar on 11/7/2017.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static int counter = 250;
    private String stringVal;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_ID = "userid";
   // private static final String KEY_USER_Value = "btcvalue";
   // private static final String KEY_USER_addres = "btcaddress";

    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
        return true;
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME, null)!=null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

  public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);
    }
    public String getUseremail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }
     public int Value(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
         counter = sharedPreferences.getInt("btc",0);
         counter+=250;
         int i= counter /2;
         sharedPreferences.edit().putInt("btc",counter).commit();
         return i;


    }

}
