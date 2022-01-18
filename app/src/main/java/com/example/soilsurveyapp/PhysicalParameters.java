package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PhysicalParameters extends AppCompatActivity {

    private TextView lblHorizon, lblDepth;
    private EditText etPD_sand, etPD_silt, etPD_clay, etPD_textural, etPD_density, etWR_33kpa, etWR_1500kpa, etWR_awc, etWR_pawc, etWR_gravimetric;
    private String horizon, profile_depth, PD_sand, PD_silt, PD_clay, PD_textural, PD_density, WR_33kpa, WR_1500kpa, WR_awc, WR_pawc, WR_gravimetric;
    private Button backBtn, nextBtn;

    // url to post the data
    private static final String url = "http://10.0.0.145/login/physicalParameters.php";

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MorphologicalParameters.class));
    }

    //--------------SHARED PREFERENCES----------------
    SharedPreferences sharedPreferencesHorizon, sharedPreferencesDepth;
    //creating shared preference name and also creating key name
    //from project credentials
    private static final String SHARED_PRE_NAME = "projCred";
    private static final String KEY_HORIZON = "horizon";
    //from morphological parameters
    private static final String SHARED_PRE_NAME2 = "morphologicalParams";
    private static final String KEY_DEPTH = "depth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_parameters);

//-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

//-------------------REFERENCES--------------------------------------------------------------
        //------displaying shared preference value on labels
        lblHorizon = findViewById(R.id.input_pp_horizons);
        lblDepth = findViewById(R.id.input_pp_profileDepth);

        sharedPreferencesHorizon = getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        sharedPreferencesDepth = getSharedPreferences(SHARED_PRE_NAME2, Context.MODE_PRIVATE);
        lblHorizon.setText(sharedPreferencesHorizon.getString(KEY_HORIZON, ""));
        lblDepth.setText(sharedPreferencesDepth.getString(KEY_DEPTH, ""));

        etPD_sand = (EditText) findViewById(R.id.input_pp_sand);
        etPD_silt = (EditText) findViewById(R.id.input_pp_silt);
        etPD_clay = (EditText) findViewById(R.id.input_pp_clay);
        etPD_textural = (EditText) findViewById(R.id.input_pp_texture);
        etPD_density = (EditText) findViewById(R.id.input_pp_bulkDensity);

        etWR_33kpa = (EditText) findViewById(R.id.input_pp_33kPa);
        etWR_1500kpa = (EditText) findViewById(R.id.input_pp_1500kPa);
        etWR_awc = (EditText) findViewById(R.id.input_pp_awc);
        etWR_pawc = (EditText) findViewById(R.id.input_pp_pawc);
        etWR_gravimetric = (EditText) findViewById(R.id.input_pp_graviMoisture);

        backBtn = (Button) findViewById(R.id.phyparam_back_btn);
        nextBtn = (Button) findViewById(R.id.phyparam_next_btn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ChemicalParameters.class));
//            }
//        });
    }

    public void ppNext(View view) {
// below is for progress dialog box
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        //--------------------SHAREDPREFERENCE-------------------
        //when clicking btn put data on shared preference
        SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
        editor.putString(KEY_DEPTH, lblDepth.getText().toString());
        editor.apply();

        horizon = lblHorizon.getText().toString().trim();
        profile_depth = lblDepth.getText().toString().trim();
        PD_sand = etPD_sand.getText().toString().trim();
        PD_silt = etPD_silt.getText().toString().trim();
        PD_clay = etPD_clay.getText().toString().trim();
        PD_textural = etPD_textural.getText().toString().trim();
        PD_density = etPD_density.getText().toString().trim();
        WR_33kpa = etWR_33kpa.getText().toString().trim();
        WR_1500kpa = etWR_1500kpa.getText().toString().trim();
        WR_awc = etWR_awc.getText().toString().trim();
        WR_pawc = etWR_pawc.getText().toString().trim();
        WR_gravimetric = etWR_gravimetric.getText().toString().trim();

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
                if (TextUtils.equals(response, "success")) {
//                        tvStatus.setText("Successfully registered.");
                    Toast.makeText(PhysicalParameters.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                } else {
//                        tvStatus.setText("Something went wrong!");
                    SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
                    editor.putString(KEY_DEPTH, profile_depth);
                    editor.apply();
                    finish();
                    Toast.makeText(PhysicalParameters.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ChemicalParameters.class));
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
                data.put("horizon", horizon);
                data.put("profile_depth", profile_depth);
                data.put("sand", PD_sand);
                data.put("silt", PD_silt);
                data.put("clay", PD_clay);
                data.put("USDA_textural_class", PD_textural);
                data.put("bulk_density", PD_density);
                data.put("33kPa", WR_33kpa);
                data.put("1500kPa", WR_1500kpa);
                data.put("AWC", WR_awc);
                data.put("PAWC", WR_pawc);
                data.put("gravimetric_moisture", WR_gravimetric);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}