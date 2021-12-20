package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DataCollection extends AppCompatActivity {

    Button nextBtn1;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataCollection.this, HomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        //---HIDING THE ACTION BAR
//        if(getSupportActionBar()!=null)
//            this.getSupportActionBar().hide();
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        nextBtn1= findViewById(R.id.data_collectin_btn);
        nextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DataCollection.this, LocationDetails.class);
                startActivity(intent);
            }
        });
    }
}