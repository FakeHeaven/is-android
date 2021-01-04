package com.example.weightmanagmentapp;

import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Subuser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subuser);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.subuser);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.subuser:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Hub.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.weight:
                        startActivity(new Intent(getApplicationContext(),Weight.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}