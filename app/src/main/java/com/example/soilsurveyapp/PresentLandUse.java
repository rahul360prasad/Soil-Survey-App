package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class PresentLandUse extends AppCompatActivity {

    private EditText etPhaseSurface, etPhaseSubstratum, etRemark, etYield, etCroppingSys;
    private String phaseSurface, phaseSubstratum, remark, yield, croppingSystem;
    //declaring variable for storing selected spinner items or options
    private String selectedForest, selectedCultivated, selectedTerraces, selectedPastureLand, selectedDegCulturable, selectedDegUncultrable, selectedLandCapClass, selectedLandCapsubClass, selectedLandIrrigaClass, selectedLandIrrigaSubclass, selectedCrop, selectedManagPractice;
    // defining spinner for all labels which is mentioned
    private Spinner forestSpinner, cultivatedSpinner, terracesSpinner, pastureLandSpinner, degrCulturableSpinner, degrunCulturableSpinner, landCapaClassSpinner, landCapaSubClassSpinner, landIrrigaClassSpinner, landIrrigaSubClassSpinner, cropSpinner, managPractSpinner;
    //defining and declaring array adapter for all adapters
    private ArrayAdapter<CharSequence> forestAdapter, cultivatedAdapter, terracesAdapter, pastureLandAdapter, degrCulturableAdapter, degrunCulturableAdapter, landCapaClassAdapter, landCapaSubClassAdapter, landIrrigaClassAdapter, landIrrigaSubClassAdapter, cropAdapter, managPractAdapter;

    Button backBtn, saveBtn;

    ProgressDialog progressDialog;

    // url to post the data
    private static final String url = "http://10.0.0.145/login/presentLandUse.php";

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SoilSiteParameter_2.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_land_use);

//----------------------------HIDING THE ACTION BAR-----------------
        try {
//            this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {
        }

//---------------------REFERENCES----------------------------------------------------------------------
        forestSpinner = (Spinner) findViewById(R.id.spin_forest);
        cultivatedSpinner = (Spinner) findViewById(R.id.spin_cultivated);
        terracesSpinner = (Spinner) findViewById(R.id.spin_terraces);
        pastureLandSpinner = (Spinner) findViewById(R.id.spin_pasture_land);
        degrCulturableSpinner = (Spinner) findViewById(R.id.spin_degraded_culturable);
        degrunCulturableSpinner = (Spinner) findViewById(R.id.spin_degraded_unculturable);
        landCapaClassSpinner = (Spinner) findViewById(R.id.spin_land_capability);
        landCapaSubClassSpinner = (Spinner) findViewById(R.id.spin_land_capab_subclass);
        landIrrigaClassSpinner = (Spinner) findViewById(R.id.spin_land_irrigability_class);
        landIrrigaSubClassSpinner = (Spinner) findViewById(R.id.spin_land_irrigability_subclass);
        cropSpinner = (Spinner) findViewById(R.id.spin_crop);
        managPractSpinner = (Spinner) findViewById(R.id.spin_management_practice);

        etPhaseSurface=(EditText) findViewById(R.id.et_phase_surface);
        etPhaseSubstratum=(EditText) findViewById(R.id.et_phase_substratum);
        etRemark=(EditText) findViewById(R.id.pre_land_remark);
        etYield=(EditText) findViewById(R.id.et_yield);
        etCroppingSys=(EditText) findViewById(R.id.et_cropping_system);

        backBtn = (Button) findViewById(R.id.present_back_btn);
        saveBtn = (Button) findViewById(R.id.preLand_next_btn);

//-------------------------------LIST OF FOREST ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        forestAdapter = ArrayAdapter.createFromResource(this, R.array.array_forest, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        forestAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        forestSpinner.setAdapter(forestAdapter);
        //now when selecting any option from spinner
        forestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedForest = forestSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF CULTIVATED ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        cultivatedAdapter = ArrayAdapter.createFromResource(this, R.array.array_cultivated, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        cultivatedAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        cultivatedSpinner.setAdapter(cultivatedAdapter);
        //now when selecting any option from spinner
        cultivatedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedCultivated = cultivatedSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF TERRACES ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        terracesAdapter = ArrayAdapter.createFromResource(this, R.array.array_terraces, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        terracesAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        terracesSpinner.setAdapter(terracesAdapter);
        //now when selecting any option from spinner
        terracesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedTerraces = terracesSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF PASTURE LAND ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        pastureLandAdapter = ArrayAdapter.createFromResource(this, R.array.array_pasture_land, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        pastureLandAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        pastureLandSpinner.setAdapter(pastureLandAdapter);
        //now when selecting any option from spinner
        pastureLandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedPastureLand = pastureLandSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF DEGRADED CULTURABLE ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        degrCulturableAdapter = ArrayAdapter.createFromResource(this, R.array.array_degraded_culturable, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        degrCulturableAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        degrCulturableSpinner.setAdapter(degrCulturableAdapter);
        //now when selecting any option from spinner
        degrCulturableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedDegCulturable = degrCulturableSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF DEGRADED UNCULTURABLE ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        degrunCulturableAdapter = ArrayAdapter.createFromResource(this, R.array.array_degraded_unculturable, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        degrunCulturableAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        degrunCulturableSpinner.setAdapter(degrunCulturableAdapter);
        //now when selecting any option from spinner
        degrunCulturableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedDegUncultrable = degrunCulturableSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND CAPABILITY CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landCapaClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_capability_class, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landCapaClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landCapaClassSpinner.setAdapter(landCapaClassAdapter);
        //now when selecting any option from spinner
        landCapaClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandCapClass = landCapaClassSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND CAPABILITY SUB-CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landCapaSubClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_capability_subclass, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landCapaSubClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landCapaSubClassSpinner.setAdapter(landCapaSubClassAdapter);
        //now when selecting any option from spinner
        landCapaSubClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandCapsubClass = landCapaSubClassSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND IRRIGABILITY CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landIrrigaClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_irrigability_class, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landIrrigaClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landIrrigaClassSpinner.setAdapter(landIrrigaClassAdapter);
        //now when selecting any option from spinner
        landIrrigaClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandIrrigaClass = landIrrigaClassSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND IRRIGABILITY SUB-CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landIrrigaSubClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_irrigability_subclass, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landIrrigaSubClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landIrrigaSubClassSpinner.setAdapter(landIrrigaSubClassAdapter);
        //now when selecting any option from spinner
        landIrrigaSubClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandIrrigaSubclass = landIrrigaSubClassSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF CROP ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        cropAdapter = ArrayAdapter.createFromResource(this, R.array.array_crop, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        cropAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        cropSpinner.setAdapter(cropAdapter);
        //now when selecting any option from spinner
        cropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedCrop = cropSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //-------------------------------LIST OF MANAGEMENT PRACTICES ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        managPractAdapter = ArrayAdapter.createFromResource(this, R.array.array_management_practice, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        managPractAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        managPractSpinner.setAdapter(managPractAdapter);
        //now when selecting any option from spinner
        managPractSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedManagPractice = managPractSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//-------------------------------LAST TWO BUTTONS-----------------------------------------------------------------
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ProjectCredentials.class));
//            }
//        });

    }

    //-----------HOME ICON on action bar-------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu:
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveNsubmit(View view) {
        // below is for progress dialog box
        //Initialinzing the progress Dialog
        progressDialog= new ProgressDialog(PresentLandUse.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        phaseSurface = etPhaseSurface.getText().toString().trim();
        phaseSubstratum = etPhaseSubstratum.getText().toString().trim();
        remark = etRemark.getText().toString().trim();
        yield = etYield.getText().toString().trim();
        croppingSystem = etCroppingSys.getText().toString().trim();

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
                    progressDialog.dismiss();
                    Toast.makeText(PresentLandUse.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                } else {
//                        tvStatus.setText("Something went wrong!");
                    progressDialog.dismiss();
                    Toast.makeText(PresentLandUse.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PresentLandUse.this, ProjectCredentials.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("forest", selectedForest);
                data.put("cultivated", selectedCultivated);
                data.put("terraces", selectedTerraces);
                data.put("pastureLand", selectedPastureLand);
                data.put("degradedCulturable", selectedDegCulturable);
                data.put("degradedUnCulturable", selectedDegUncultrable);
                data.put("landCapabilityClass", selectedLandCapClass);
                data.put("landCapabilitySubClass", selectedLandCapsubClass);
                data.put("landIrrigabilityClass", selectedLandIrrigaClass);
                data.put("landIrrigabilitySubClass", selectedLandIrrigaSubclass);
                data.put("crop", selectedCrop);
                data.put("managementPractice", selectedManagPractice);
                data.put("phaseSurface", phaseSurface);
                data.put("phaseSubstratum", phaseSubstratum);
                data.put("remark", remark);
                data.put("yield", yield);
                data.put("croppingSystem", croppingSystem);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}