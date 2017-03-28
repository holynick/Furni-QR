package com.example.ckfyp.furniqr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    public static String usernameX, useridX;
    Button loginbutton;
    TextView registerlink;
    String url = "https://furnituredatabase.000webhostapp.com/furniqrlogin.php";
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        registerlink = (TextView) findViewById(R.id.registerlink);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameS = username.getText().toString();
                final String passwordS = password.getText().toString();

                if(usernameS.isEmpty())
                    username.setError("Please enter a valid username");
                else if (passwordS.isEmpty())
                    password.setError("Please enter a valid password");
                else {
                    loading = new ProgressDialog(LoginActivity.this);
                    loading.setTitle("Login");
                    loading.setMessage("Please wait...");
                    loading.show();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                boolean success = object.getBoolean("success");
                                String accounttype = object.getString("accounttype");
                                usernameX = object.getString("username");
                                useridX = object.getString("userid");
                                loading.dismiss();
                                if (success) {
                                    if (accounttype.equals("normaluser")) {
                                        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                                        LoginActivity.this.startActivity(intent);
                                    } else if (accounttype.equals("staff")) {
                                        Intent intent = new Intent(LoginActivity.this, StaffMainMenuActivity.class);
                                        LoginActivity.this.startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                loading.dismiss();
                                Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", usernameS);
                            params.put("password", passwordS);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    requestQueue.add(request);
                }
            }
        });
    }
}
