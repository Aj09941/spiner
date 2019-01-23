package com.a09.adam.abrar.spiiin;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.valueOf;

public class SpinActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSpin;
    private static int counter = 0;
    private TextView textView,text,txtbtc;
   private SharedPreferences prefs;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    ImageView spin;
    private AdView mAdView;
    Random r;
    int degree = 0, degree_old = 0 ,new_degree=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);

        //editText =(EditText)findViewById(R.id.editText);

        prefs = getSharedPreferences("myperfs", MODE_PRIVATE);

        btnSpin =(Button)findViewById(R.id.button);

        textView = (TextView)findViewById(R.id.textView);
        text = (TextView)findViewById(R.id.textView3);
        txtbtc= (TextView)findViewById(R.id.txtBtc);

        //text.setText(""+Integer.valueOf(SharedPrefManager.getInstance(this).Value()));

        spin = (ImageView)findViewById(R.id.imageView);
        r = new Random();
         counter = prefs.getInt("btc",0);

        ///banner add
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7584874423919070/9130491668");
        // mInterstitialAd.setAdUnitId("ca-app-pub-7584874423919070/1439564288");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
       // mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-7584874423919070/1953998786",new AdRequest.Builder().build());


        btnSpin.setOnClickListener(this);



    }
    @Override
   protected void onPause(){
        super.onPause();
        prefs.edit().putInt("btc",counter).commit();
        //txtbtc.setVisibility(counter);

    }


    public void onSpin() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else {
        degree_old = degree % 360;
       degree = r.nextInt(3600) + 720;

        //Rotate
        RotateAnimation rotate = new RotateAnimation(degree_old, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3600);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.setText("");
                // result = degree_old + degree;
                // text.setText(" Min : 0.00000000000000" + degree);


            }

            @Override
            public void onAnimationEnd(Animation animation) {
               // textView.setText( ""+degree);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        spin.startAnimation(rotate);
        //text.setText("btc");
    }}
    public void getCounter(){
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }else {
            counter += 250;
            text.setText(counter + " KH/s");
            int i = counter / 3;
            txtbtc.setText(i + " KH/s");

        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnSpin)
            onSpin();
        getCounter();
        //text.setText(""+counter);
      //  stringVal = Integer.toString(counter);
        //haredPreferences prefs = getApplicationContext().getSharedPreferences("myperfs", MODE_PRIVATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(this,SettingActivity.class));
                break;
        }
        return true;
    }
}