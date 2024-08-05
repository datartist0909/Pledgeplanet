package com.example.pledgeplanet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnBeginner, btnIntermediate, btnAdvanced;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        btnBeginner = findViewById(R.id.btn_beginner);
        btnIntermediate = findViewById(R.id.btn_intermediate);
        btnAdvanced = findViewById(R.id.btn_advanced);

        btnBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start BeginnerActivity or fragment
                Intent intent = new Intent(DashboardActivity.this, BeginnerActivity.class);
                startActivity(intent);
            }
        });

        btnIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start IntermediateActivity or fragment
                Intent intent = new Intent(DashboardActivity.this, IntermediateActivity.class);
                startActivity(intent);
            }
        });

        btnAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AdvancedActivity or fragment
                Intent intent = new Intent(DashboardActivity.this, AdvancedActivity.class);
                startActivity(intent);
            }
        });
    }
}
