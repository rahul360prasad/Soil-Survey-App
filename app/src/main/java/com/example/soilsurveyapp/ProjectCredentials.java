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

public class ProjectCredentials extends AppCompatActivity {

    private Button addBtn, backBtn, nextBtn;
    private TextView lbl_projID, lbl_projProfileID;
    private EditText etHorizon;
    private String projID, projProfileID, horizon;

    //--------------SHARED PREFERENCES----------------
    SharedPreferences sharedPreferencesProjID;
    SharedPreferences sharedPreferencesProjProID;
    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "proReg";
    private static final String SHARED_PRE_NAME2 = "locationDetails";
    private static final String KEY_PROJECT_ID = "id";
    private static final String KEY_PROJECT_PROFILE_ID = "ppid";

    // url to post the data
    private static final String url = "http://10.0.0.145/login/projDetails.php";

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PresentLandUse.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_credentials);

        sharedPreferencesProjID = getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        sharedPreferencesProjProID = getSharedPreferences(SHARED_PRE_NAME2, Context.MODE_PRIVATE);

        lbl_projID = findViewById(R.id.input_project_id);
        lbl_projProfileID = findViewById(R.id.input_projectProfile_id);
        etHorizon = findViewById(R.id.input_proCredential_horizon);

        lbl_projID.setText(sharedPreferencesProjID.getString(KEY_PROJECT_ID, ""));
        lbl_projProfileID.setText(sharedPreferencesProjProID.getString(KEY_PROJECT_PROFILE_ID, ""));

//-------------------------REFERENCE--------------------------------------------------------------
        addBtn = (Button) findViewById(R.id.proCredential_add_horizon);
        backBtn = (Button) findViewById(R.id.projDetail_back_btn);
        nextBtn = (Button) findViewById(R.id.projDetail_next_btn);

//----------------------------HIDING THE ACTION BAR-----------------
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

//--------------------------BUTTON OPERATIONS-------------------------------------------------
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), MorphologicalParameters.class));
//            }
//        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), AddPhotos.class));
////                Toast.makeText(getApplicationContext(), "go to *** ADD PHOTOS *** page", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void addBtn(View view) {
        // below is for progress dialog box
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        projID = lbl_projID.getText().toString().trim();
        projProfileID = lbl_projProfileID.getText().toString().trim();
        horizon = etHorizon.getText().toString().trim();

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
                    Toast.makeText(ProjectCredentials.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                } else {
//                        tvStatus.setText("Something went wrong!");
                    Toast.makeText(ProjectCredentials.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MorphologicalParameters.class));
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
                data.put("projID", projID);
                data.put("projProfileID", projProfileID);
                data.put("horizon", horizon);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}