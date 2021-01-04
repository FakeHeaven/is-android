package com.example.weightmanagmentapp;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Subuser extends AppCompatActivity {
    String id;
    String token;
    private TextView teDisplay;
    private String url = "https://wmanage.azurewebsites.net/api/subusers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subuser);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            token = extras.getString("token");
        }
        teDisplay = (TextView) findViewById(R.id.teDisplay);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.subuser);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.subuser:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Hub.class).putExtra("id", id).putExtra("token", token));
                        ;
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.weight:
                        startActivity(new Intent(getApplicationContext(), Weight.class).putExtra("id", id).putExtra("token", token));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://wmanage.azurewebsites.net/api/subusers?userId=53991b28-ba99-4d85-97f4-787a86a2468d", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(teDisplay);
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

//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String responseString = "";
//                if (response != null) {
//                    responseString = String.valueOf(response.statusCode);
//
//                }
//                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer 1a13b8ca-ee80-42dd-b7dd-f0d5e98bd3e0");
                return headers;
            }



        };

        requestQueue.add(stringRequest);


    }
}