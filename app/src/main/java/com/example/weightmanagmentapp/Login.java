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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class Login extends AppCompatActivity {

    public static String id;
    private RequestQueue requestQueue;
    private EditText email;
    private static String test;
    private EditText password;
    public static String token;
    private String url = "https://wmanage.azurewebsites.net/api/token";
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                    System.out.println(response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                    Login.id = jsonObject.getString("userId");
                    Login.token = jsonObject.getString("accessToken");
                    System.out.println(Login.token + " " + Login.id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("LOG_RESPONSE", response);
                    test = response;
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
        if (test != null){
            Intent intent = new Intent(this, Hub.class);
            String message = "Uspesno si se prijavil.";
            intent.putExtra("id", Login.id);
            intent.putExtra("token", Login.token);
            startActivity(intent);
        }
    }

}
