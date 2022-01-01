package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SoilSiteParameters extends AppCompatActivity {
    //declaring variable for storing Physiographic category, Sub Physiographic unit
    private String selectedPhysioCatgy, selectedSubPhysioUnit;
    private EditText etGeology, etParentMaterial, etClimate, etRainfall, etTopographyLandform;
    // creating a strings for storing our values from edittext fields.
    private String geology, parentMaterial, climate, rainfall, topographyLandform;


    // defining spinner for Physiographic category, Sub Physiographic unit
    private Spinner physioCatgySpinner, subPhysioUnitSpinner;
    //defining and declaring array adapter for Physiographic category, Sub Physiographic unit
    private ArrayAdapter<CharSequence> physioCatgyAdapter, subPhysioUnitAdapter;

    Button backBtn, nextBtn;

    // url to post the data
    private static final String url = "http://10.0.0.145/login/soilsiteparameters.php";

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
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

//---------------------REFERENCES--------------------------------------------------------------------
        physioCatgySpinner = (Spinner) findViewById(R.id.spin_phy_catgy);
        subPhysioUnitSpinner = (Spinner) findViewById(R.id.spin_phy_unit);
        etGeology = (EditText) findViewById(R.id.geology);
        etParentMaterial = (EditText) findViewById(R.id.parent_material);
        etClimate = (EditText) findViewById(R.id.climate);
        etRainfall = (EditText) findViewById(R.id.rainfall);
        etTopographyLandform = (EditText) findViewById(R.id.topography_type);


        backBtn = (Button) findViewById(R.id.soil_site_back_btn);
        nextBtn = (Button) findViewById(R.id.soil_site_next_btn);

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
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(SoilSiteParameters.this, SoilSiteParameter_2.class);
//                startActivity(intent);
//            }
//        });
    }

    public void sspBtn(View view) {
        // below is for progress dialog box
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        geology = etGeology.getText().toString().trim();
        parentMaterial = etParentMaterial.getText().toString().trim();
        climate = etClimate.getText().toString().trim();
        rainfall = etRainfall.getText().toString().trim();
        topographyLandform = etTopographyLandform.getText().toString().trim();

        //        state = stateSpinner.getSelectedItem().toString().trim();
//        tehsil = tehsilSpinner.getSelectedItem().toString().trim();
//        block = blockSpinner.getSelectedItem().toString().trim();
//        toposheet250k = topo250kSpinner.getSelectedItem().toString().trim();
//        toposheet50k = topo50kSpinner.getSelectedItem().toString().trim();

        //----------validating the text fields if empty or not.-------------------//commenting only for next page codig
//        if (TextUtils.isEmpty(geology)) {
//            etsurveyorName.setError("Please enter geology...");
//        } else if (TextUtils.isEmpty(parentMaterial)) {
//            etvillageName.setError("Please enter parent material...");
//        } else if (TextUtils.isEmpty(climate)) {
//            etelevation.setError("Please enter climate...");
//        } else if (TextUtils.isEmpty(rainfall)) {
//            etprojProfileID.setError("Please enter rainfall...");
//        } else if (TextUtils.isEmpty(topographyLandform)) {
//            etremark.setError("Please enter topography landform...");
//        }
//        else if (selectedPhysioCatgy.equals("Select Category...")) {
//            Toast.makeText(SoilSiteParameters.this, "Please Select Physiographic Category !!", Toast.LENGTH_LONG).show();
//        } else if (selectedSubPhysioUnit.equals("Select Unit...")) {
//            Toast.makeText(SoilSiteParameters.this, "Please Select unit !!", Toast.LENGTH_LONG).show();
//        } else {
        // calling method to add data to Firebase Firestore.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resssss", response);
                if (TextUtils.equals(response, "success")) {
//                        tvStatus.setText("Successfully registered.");
                    Toast.makeText(SoilSiteParameters.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                } else {
//                        tvStatus.setText("Something went wrong!");
                    Toast.makeText(SoilSiteParameters.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SoilSiteParameters.this, SoilSiteParameter_2.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("physiographicCategory", selectedPhysioCatgy);
                data.put("subPhysiographicUnit", selectedSubPhysioUnit);
                data.put("geology", geology);
                data.put("parentMaterial", parentMaterial);
                data.put("climate", climate);
                data.put("rainfall", rainfall);
                data.put("topographyLandformType", topographyLandform);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}