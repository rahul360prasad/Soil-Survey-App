package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SoilFertilityParameters extends AppCompatActivity {

    private TextView lblHorizon, lblDepth;
    private EditText etOrganicCarbon, et_MaN_nitrogen, et_MaN_phosphorus, et_MaN_potassium, et_MiN_sulphur, et_MiN_zinc, et_MiN_copper, et_MiN_iron, et_MiN_manganese;
    private String horizon, soilDepth, organicCarbon, MaN_nitrogen, MaN_phosphorus, MaN_potassium, MiN_sulphur, MiN_zinc, MiN_copper, MiN_iron, MiN_manganese;
    private Button backBtn;

    // url to post the data
    private static final String url = "http://10.0.0.145/login/soilFertilityParameters.php";

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PhysicalParameters.class));
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
        setContentView(R.layout.activity_soil_fertility_parameters);

//-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //------------------REFERENCE-----------------------------------------------------------------------
        //------displaying shared preference value on label
        lblHorizon = findViewById(R.id.input_sfp_horizons);
        sharedPreferencesHorizon = getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        lblHorizon.setText(sharedPreferencesHorizon.getString(KEY_HORIZON, ""));

        lblDepth = findViewById(R.id.input_sfp_soilDepth);
        sharedPreferencesDepth = getSharedPreferences(SHARED_PRE_NAME2, Context.MODE_PRIVATE);
        lblDepth.setText(sharedPreferencesDepth.getString(KEY_DEPTH, ""));

        etOrganicCarbon = (EditText) findViewById(R.id.input_sfp_organicCarbon);
        et_MaN_nitrogen = (EditText) findViewById(R.id.input_sfp_nitrogen);
        et_MaN_phosphorus = (EditText) findViewById(R.id.input_sfp_phosphorus);
        et_MaN_potassium = (EditText) findViewById(R.id.input_sfp_potassium);
        et_MiN_sulphur = (EditText) findViewById(R.id.input_sfp_sulphur);
        et_MiN_zinc = (EditText) findViewById(R.id.input_sfp_zinc);
        et_MiN_copper = (EditText) findViewById(R.id.input_sfp_copper);
        et_MiN_iron = (EditText) findViewById(R.id.input_sfp_iron);
        et_MiN_manganese = (EditText) findViewById(R.id.input_sfp_manganese);

        backBtn = (Button) findViewById(R.id.soilFertParam_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void sfpNext(View view) {
        // below is for progress dialog box
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        //--------------------SHAREDPREFERENCE-------------------
        //when clicking btn put data on shared preference
        SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
        editor.putString(KEY_DEPTH, lblDepth.getText().toString());
        editor.apply();

        horizon = lblHorizon.getText().toString().trim();
        soilDepth = lblDepth.getText().toString().trim();
        organicCarbon = etOrganicCarbon.getText().toString().trim();
        MaN_nitrogen = et_MaN_nitrogen.getText().toString().trim();
        MaN_phosphorus = et_MaN_phosphorus.getText().toString().trim();
        MaN_potassium = et_MaN_potassium.getText().toString().trim();
        MiN_sulphur = et_MiN_sulphur.getText().toString().trim();
        MiN_zinc = et_MiN_zinc.getText().toString().trim();
        MiN_copper = et_MiN_copper.getText().toString().trim();
        MiN_iron = et_MiN_iron.getText().toString().trim();
        MiN_manganese = et_MiN_manganese.getText().toString().trim();

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
                    Toast.makeText(SoilFertilityParameters.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                } else {
//                        tvStatus.setText("Something went wrong!");
                    SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
                    editor.putString(KEY_DEPTH, soilDepth);
                    editor.apply();
                    finish();
                    Toast.makeText(SoilFertilityParameters.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AddPhotos.class));
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
                data.put("soilDepth", soilDepth);
                data.put("organicCarbon", organicCarbon);
                data.put("MaN_nitrogen", MaN_nitrogen);
                data.put("MaN_phosphorus", MaN_phosphorus);
                data.put("MaN_potassium", MaN_potassium);
                data.put("MiN_sulphur", MiN_sulphur);
                data.put("MiN_zinc", MiN_zinc);
                data.put("MiN_copper", MiN_copper);
                data.put("MiN_iron", MiN_iron);
                data.put("MiN_manganese", MiN_manganese);
                 return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}