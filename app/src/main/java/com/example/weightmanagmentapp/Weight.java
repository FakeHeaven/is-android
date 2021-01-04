package com.example.weightmanagmentapp;

import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Weight extends AppCompatActivity {
    String id;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            token = extras.getString("token");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.weight);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.subuser:
                        startActivity(new Intent(getApplicationContext(),Subuser.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Hub.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.weight:
                        return true;
                }
                return false;
            }
        });
    }
}