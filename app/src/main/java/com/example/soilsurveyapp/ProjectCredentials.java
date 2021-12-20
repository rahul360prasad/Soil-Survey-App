package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProjectCredentials extends AppCompatActivity {

    private Button addBtn, backBtn, nextBtn;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PresentLandUse.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_credentials);

//-------------------------REFERENCE--------------------------------------------------------------
        addBtn=(Button) findViewById(R.id.proCredential_add_horizon);
        backBtn=(Button) findViewById(R.id.projDetail_back_btn);
        nextBtn=(Button) findViewById(R.id.projDetail_next_btn);

//----------------------------HIDING THE ACTION BAR-----------------
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

//--------------------------BUTTON OPERATIONS-------------------------------------------------
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MorphologicalParameters.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddPhotos.class));
//                Toast.makeText(getApplicationContext(), "go to *** ADD PHOTOS *** page", Toast.LENGTH_SHORT).show();
            }
        });
    }
}