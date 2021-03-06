package com.example.weightmanagmentapp;

import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Hub extends AppCompatActivity {
    String id;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            token = extras.getString("token");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.subuser:
                        startActivity(new Intent(getApplicationContext(),Subuser.class).putExtra("id", id).putExtra("token", token));
//                        System.out.println(id + " test 1 " + token);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.weight:
                        startActivity(new Intent(getApplicationContext(),Weight.class).putExtra("id", id).putExtra("token", token));
//                        System.out.println(id + " test 2 " + token);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}