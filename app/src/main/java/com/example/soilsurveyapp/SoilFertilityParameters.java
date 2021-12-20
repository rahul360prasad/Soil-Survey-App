package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SoilFertilityParameters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_fertility_parameters);

//-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

    }
}