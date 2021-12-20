package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhysicalParameters extends AppCompatActivity {

    private Button backBtn, nextBtn;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MorphologicalParameters.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_parameters);

//-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

//-------------------REFERENCES--------------------------------------------------------------
        backBtn= (Button) findViewById(R.id.phyparam_back_btn);
        nextBtn=(Button) findViewById(R.id.phyparam_next_btn);


       backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChemicalParameters.class));
            }
        });
    }
}