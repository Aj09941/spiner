package com.a09.adam.abrar.spiiin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private EditText edusername, edpassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SpinActivity.class));
            return;
        }

        edusername = (EditText) findViewById(R.id.edit_name);
        edpassword = (EditText) findViewById(R.id.edit_password);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        btnLogin.setOnClickListener(this);

    }

    private void userLogin() {
        final String username = edusername.getText().toString().trim();
        final String password = edpassword.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(s);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("username"),
                                                obj.getString("email")
                                        );
                                startActivity(new Intent(getApplicationContext(), SpinActivity.class));
                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                volleyError.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("username", username);
                param.put("password", password);
                return param;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin)
            userLogin();
    }
}