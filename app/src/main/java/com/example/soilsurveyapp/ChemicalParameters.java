package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChemicalParameters extends AppCompatActivity {

    private Button backBtn, nextBtn;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PhysicalParameters.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_parameters);

//-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

//------------------REFERENCE-----------------------------------------------------------------------
        backBtn= (Button) findViewById(R.id.chemparam_back_btn);
        nextBtn=(Button) findViewById(R.id.chemparam_next_btn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SoilFertilityParameters.class));
            }
        });
    }

}