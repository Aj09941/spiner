package com.a09.adam.abrar.spiiin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_TIME =3000;
    private boolean InternetCheck = true;
    private ProgressBar spinner;
    ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        imgLogo =(ImageView)findViewById(R.id.imgLogo);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);

        PostDelayedMethod();

    }

    public void PostDelayedMethod(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean InternetResult = checkConnection();
                if(InternetResult){

                    Intent intent = new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    spinner.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);

                    DialogAppear();
                }
            }
        },SPLASH_TIME);

    }
    public void DialogAppear(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Splashscreen.this);
        builder.setTitle("Network Error");
        builder.setMessage("No Internet Connectivity");

        //negative message
        builder.setNegativeButton("Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        //positive message
        builder.setPositiveButton("Retry",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PostDelayedMethod();
                    }
                });
        builder.show();
    }
//check internet status of the mobile
    public boolean isOnline(){

        ConnectivityManager cn = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cn.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkConnection(){
        if(isOnline()){
            return InternetCheck;
        }else {
            InternetCheck=false;
            return InternetCheck;
        }
    }
}
