package com.a09.adam.abrar.spiiin;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRregister;
    private EditText edusername, edpassword, edemail;
    private ProgressDialog progressDialog;

    private TextView loginView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SpinActivity.class));
            return;
        }

        edusername = (EditText)findViewById(R.id.edit_name);
        edpassword = (EditText)findViewById(R.id.edit_password);
        edemail = (EditText)findViewById(R.id.edit_email);

        loginView = (TextView)findViewById(R.id.loginView);

        btnRregister = (Button)findViewById(R.id.registerUser);

        progressDialog = new ProgressDialog(this);
        btnRregister.setOnClickListener(this);

        loginView.setOnClickListener(this);
       /* android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Spinner app");
        String tirle =actionBar.getTitle().toString();
        actionBar.hide();*/

    }
    private void registerUser(){

        final String username = edusername.getText().toString().trim();
        final String password = edpassword.getText().toString().trim();
        final String email = edemail.getText().toString().trim();

        progressDialog.setMessage("Registering user");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.hide();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if(view == btnRregister)
            registerUser();
        if(view == loginView)
            startActivity(new Intent(this,LoginActivity.class));
    }

}
