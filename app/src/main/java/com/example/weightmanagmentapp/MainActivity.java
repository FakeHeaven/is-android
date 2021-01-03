package com.example.weightmanagmentapp;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static String id;
    private RequestQueue requestQueue;
    private EditText email;
    public static int test;
    private EditText password;
    private static String token;
    private String url = "https://wmanage.azurewebsites.net/api/token";
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.teEmail);
        password = (EditText) findViewById(R.id.tePassword);
        status = (TextView) findViewById(R.id.status);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public void getToken(View view){
        final String EXTRA_MESSAGE = "com.example.universityapp.MESSAGE";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email.getText());
            jsonBody.put("password", password.getText());
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                    MainActivity.id = jsonObject.getString("userId");
                    MainActivity.token = jsonObject.getString("accessToken");
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
                }
            }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        status.setText(responseString);
                        test = Integer.parseInt(responseString);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }

            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (test == 200){
            Intent intent = new Intent(this,MainPage.class);
            String message = "Uspesno si se prijavil.";
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

}
