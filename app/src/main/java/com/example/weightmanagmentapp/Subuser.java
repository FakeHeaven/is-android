package com.example.weightmanagmentapp;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        teDisplay();

    }

public void teDisplay(){
    System.out.println("ID is " + id + "\n" + "Token is " + token);
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://wmanage.azurewebsites.net/api/subusers?userId=" + id, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("LOG_RESPONSE", response);
            teDisplay.setMovementMethod(new ScrollingMovementMethod());
            JSONArray jsonObject;
            try {
                jsonObject= new JSONArray(response);
                ArrayList<String> data = new ArrayList<>();
                for (int i = 0; i < jsonObject.length(); i++){
                    try {
                        JSONObject object =jsonObject.getJSONObject(i);
                        String uid = object.getString("id");
                        String name = object.getString("name");
                        String date = object.getString("dateOfBirth");

                        data.add(uid + " " + name + " " + date);

                    } catch (JSONException e){
                        e.printStackTrace();
                        return;

                    }

                }

                teDisplay.setText("");


                for (String row: data){
                    String currentText = teDisplay.getText().toString();
                    teDisplay.setText(currentText + "\n\n" + row);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
//                    status.setText(response);


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
            headers.put("Authorization", "Bearer " + token);
            return headers;
        }
    };
        requestQueue.add(stringRequest);
}
}