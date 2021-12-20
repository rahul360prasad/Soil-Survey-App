package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddPhotos extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddPhotos.this, ProjectCredentials.class);
        startActivity(intent);
    }

    private Button backBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);

        //----------------------REFERENCES------------------------------------------
        backBtn = (Button) findViewById(R.id.back_btn);
        nextBtn = (Button) findViewById(R.id.next_btn);

//----------------------------HIDING THE ACTION BAR-----------------
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //--------------------------BUTTON OPERATIONS-------------------------------------------------
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SoilDataReport.class));
//                Toast.makeText(getApplicationContext(), "go to *** ADD PHOTOS *** page", Toast.LENGTH_SHORT).show();
            }
        });
    }
}