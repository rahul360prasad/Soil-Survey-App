package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SoilSiteParameters extends AppCompatActivity {
    //declaring variable for storing Physiographic category, Sub Physiographic unit
    private String selectedPhysioCatgy, selectedSubPhysioUnit;
    // defining spinner for Physiographic category, Sub Physiographic unit
    private Spinner physioCatgySpinner, subPhysioUnitSpinner;
    //defining and declaring array adapter for Physiographic category, Sub Physiographic unit
    private ArrayAdapter<CharSequence> physioCatgyAdapter, subPhysioUnitAdapter;

    Button backBtn, nextBtn;

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(SoilSiteParameters.this, LocationDetails.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_site_parameters);

//-------------------HIDING THE ACTION BAR-----------------------------------------------------------
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

//---------------------REFERENCES--------------------------------------------------------------------
        physioCatgySpinner= (Spinner) findViewById(R.id.spin_phy_catgy);
        subPhysioUnitSpinner= (Spinner) findViewById(R.id.spin_phy_unit);

        backBtn= (Button) findViewById(R.id.soil_site_back_btn);
        nextBtn=(Button) findViewById(R.id.soil_site_next_btn);

//-------------------------------LIST OF PHYSIOGRAPHIC CATEGORY ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        physioCatgyAdapter = ArrayAdapter.createFromResource(this, R.array.array_physiographic_category, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        physioCatgyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e physioCatgyAdapter
        physioCatgySpinner.setAdapter(physioCatgyAdapter);
        //now when selecting any physiographic Category from spinner
        physioCatgySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selectedPhysioCatgy variable
                selectedPhysioCatgy = physioCatgySpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//---------------------------------------------LIST OF SUB-PHYSIOGRAPHIC UNIT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        subPhysioUnitAdapter = ArrayAdapter.createFromResource(this, R.array.array_subPhysiographic_unit, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        subPhysioUnitAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e physioCatgyAdapter
        subPhysioUnitSpinner.setAdapter(subPhysioUnitAdapter);
        //now when selecting any physiographic Category from spinner
        subPhysioUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selectedSubPhysioUnit variable
                selectedSubPhysioUnit = subPhysioUnitSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //soil parameter's back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
             }
        });

        // soil parameter's next button
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SoilSiteParameters.this, SoilSiteParameter_2.class);
                startActivity(intent);
            }
        });


    }
}