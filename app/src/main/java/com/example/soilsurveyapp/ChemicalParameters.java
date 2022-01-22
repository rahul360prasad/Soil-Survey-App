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

public class ChemicalParameters extends AppCompatActivity {

    private TextView lblHorizon, lblDepth;
    private EditText etPH, etEC, etOC, etCaCo, etCa, etMg, etNa, etK, etTotalBase, etCEC, etBS, etESP;
    private String horizon, soilDepth, PH, EC, OC, CaCo, Ca, Mg, Na, K, TotalBase, CEC, BS, ESP;
    private Button backBtn, nextBtn;
    ProgressDialog progressDialog;


    // url to post the data
    private static final String url = "http://10.0.0.145/login/chemicalParameters.php";

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
        setContentView(R.layout.activity_chemical_parameters);

//-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

//------------------REFERENCE-----------------------------------------------------------------------
        //------displaying shared preference value on label
        lblHorizon = findViewById(R.id.input_cp_horizons);
        sharedPreferencesHorizon = getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        lblHorizon.setText(sharedPreferencesHorizon.getString(KEY_HORIZON, ""));

        lblDepth = findViewById(R.id.input_cp_soilDepth);
        sharedPreferencesDepth = getSharedPreferences(SHARED_PRE_NAME2, Context.MODE_PRIVATE);
        lblDepth.setText(sharedPreferencesDepth.getString(KEY_DEPTH, ""));

        etPH = (EditText) findViewById(R.id.input_cp_ph);
        etEC = (EditText) findViewById(R.id.input_cp_ec);
        etOC = (EditText) findViewById(R.id.input_cp_oc);
        etCaCo = (EditText) findViewById(R.id.input_cp_caco);
        etCa = (EditText) findViewById(R.id.input_cp_ca);
        etMg = (EditText) findViewById(R.id.input_cp_mg);
        etNa = (EditText) findViewById(R.id.input_cp_na);
        etK = (EditText) findViewById(R.id.input_cp_k);
        etTotalBase = (EditText) findViewById(R.id.input_cp_totalBase);
        etCEC = (EditText) findViewById(R.id.input_cp_cec);
        etBS = (EditText) findViewById(R.id.input_cp_bs);
        etESP = (EditText) findViewById(R.id.input_cp_esp);

        backBtn = (Button) findViewById(R.id.chemparam_back_btn);
        nextBtn = (Button) findViewById(R.id.chemparam_next_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), SoilFertilityParameters.class));
//            }
//        });
    }

    public void cpNext(View view) {

        // below is for progress dialog box
        //Initialinzing the progress Dialog
        progressDialog= new ProgressDialog(ChemicalParameters.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //--------------------SHAREDPREFERENCE-------------------
        //when clicking btn put data on shared preference
        SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
        editor.putString(KEY_DEPTH, lblDepth.getText().toString());
        editor.apply();

        horizon = lblHorizon.getText().toString().trim();
        soilDepth = lblDepth.getText().toString().trim();
        PH = etPH.getText().toString().trim();
        EC = etEC.getText().toString().trim();
        OC = etOC.getText().toString().trim();
        CaCo = etCaCo.getText().toString().trim();
        Ca = etCa.getText().toString().trim();
        Mg = etMg.getText().toString().trim();
        Na = etNa.getText().toString().trim();
        K = etK.getText().toString().trim();
        TotalBase = etTotalBase.getText().toString().trim();
        CEC = etCEC.getText().toString().trim();
        BS = etBS.getText().toString().trim();
        ESP = etESP.getText().toString().trim();

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
                    progressDialog.dismiss();
                    Toast.makeText(ChemicalParameters.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                } else {
//                        tvStatus.setText("Something went wrong!");
                    SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
                    editor.putString(KEY_DEPTH, soilDepth);
                    editor.apply();
                    finish();
                    //Dismiss Progress Dialog
                    progressDialog.dismiss();
                    Toast.makeText(ChemicalParameters.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SoilFertilityParameters.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Dismiss Progress Dialog
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("horizon", horizon);
                data.put("soilDepth", soilDepth);
                data.put("pH", PH);
                data.put("EC", EC);
                data.put("OC", OC);
                data.put("CaCo", CaCo);
                data.put("Ca", Ca);
                data.put("Mg", Mg);
                data.put("Na", Na);
                data.put("K", K);
                data.put("totalBase", TotalBase);
                data.put("CEC", CEC);
                data.put("BS", BS);
                data.put("ESP", ESP);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}