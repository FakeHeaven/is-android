package com.example.weightmanagmentapp;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;


public class Login extends AppCompatActivity {

    public static String id;
    private RequestQueue requestQueue;
    private EditText email;
//    private static String test;
    private EditText password;
    public static String token;
    private final String url = "https://wmanage.azurewebsites.net/api/token";
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.teEmail);
        password = findViewById(R.id.tePassword);
        status = findViewById(R.id.status);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public void getToken(View view){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email.getText());
            jsonBody.put("password", password.getText());
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    System.out.println(response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                    Login.id = jsonObject.getString("userId");
                    Login.token = jsonObject.getString("accessToken");
//                    System.out.println("idd " + id);
//                        System.out.println("token " + token);
                    status.setText("Granted");
//                    System.out.println(Login.token + " " + Login.id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("LOG_RESPONSE", response);
//                    status.setText(response);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                    status.setText("Denied");
                }
            }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return mRequestBody == null ? null : mRequestBody.getBytes(StandardCharsets.UTF_8);
                }

//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                        status.setText(responseString);
//                        test = Integer.parseInt(responseString);
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }

            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println(test);
    }

    public void Enter(View view) {
            if (status.getText().toString().equals("Granted")) {
                Intent intent = new Intent(this, Hub.class);
                intent.putExtra("id", id);
                intent.putExtra("token", token);
                startActivity(intent);
            }
    }
}
