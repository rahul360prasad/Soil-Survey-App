package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

public class LocationDetails extends AppCompatActivity {

    // defining spinner for state, district, tehsil, block, topo250k, topo50k
    private Spinner stateSpinner, districtSpinner, tehsilSpinner, blockSpinner, topo250kSpinner, topo50kSpinner;
    private EditText etsurveyorName, etvillageName, etelevation, etprojProfileID, etremark;
    private TextView dateText, timeText, latitudeText, longitudeText;
    private Button backBtn, nextBtn;

    //declaring variable for storing selected state, district, tehsil, block, topo250k, topo50k
    private String selectedState, selectedDistrict, selectedTehsil, selectedBlock, selectedTopo250k, selectedTopo50k;
    // creating a strings for storing our values from edittext fields.
    private String surveyorname, villagename, date, time, latitude, longitude, elevation, projectProfileID, remark;

    //defining and declaring array adapter for state, district, tehsil, block, topo250k, topo50k
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter, tehsilAdapter, blockAdapter, topo250kAdapter, topo50kAdapter;

    // url to post the data
    private static final String url = "http://10.0.0.145/login/locationDetails.php";

    //------------LOCATION CODE------------
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Button btnGetLocation;

    //---------SHARED PREFERENCES-------------------
    SharedPreferences sharedPreferences;
    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "locationDetails";
    private static final String KEY_PROJECT_PROFILE_ID = "ppid";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LocationDetails.this, DataCollection.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        //--------Location codes-------------------
        ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        latitudeText = findViewById(R.id.lbl_latitude);
        longitudeText = findViewById(R.id.lbl_longitude);
        btnGetLocation = findViewById(R.id.get_location);

        //------------------HIDING THE ACTION BAR
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        surveyorname = villagename = elevation = projectProfileID = remark = "";

        //---------------REFERENCES------------------------------
        //----------spinners-----------
        stateSpinner = (Spinner) findViewById(R.id.spin_select_state);
        districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
        tehsilSpinner = (Spinner) findViewById(R.id.spin_select_tehsil);
        blockSpinner = (Spinner) findViewById(R.id.spin_select_block);
        topo250kSpinner = (Spinner) findViewById(R.id.spin_toposheet250k);
        topo50kSpinner = (Spinner) findViewById(R.id.spin_toposheet50k);
        //----------edittext-------------
        etsurveyorName = (EditText) findViewById(R.id.surveyor_name);
        etvillageName = (EditText) findViewById(R.id.village_name);
        etelevation = (EditText) findViewById(R.id.elevation);
        etprojProfileID = (EditText) findViewById(R.id.profileID);
        etremark = (EditText) findViewById(R.id.remark);

        //-------------buttons---------------------------------------
        backBtn = (Button) findViewById(R.id.location_detail_back_btn);
        nextBtn = (Button) findViewById(R.id.location_detail_next_btn);
        //------------------------------date_time----------------------------
        dateText = (TextView) findViewById(R.id.lbl_full_date);
        timeText = (TextView) findViewById(R.id.lbl_full_time);
//        latitudeText= (TextView) findViewById(R.id.lbl_latitude);
//        longitudeText= (TextView) findViewById(R.id.lbl_longitude);


        //----------DATE-----------
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy");
        String dateString = sdf.format(date);
        dateText.setText(dateString);
        //-----------TIME-----------
        long time = System.currentTimeMillis();
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeString = timeFormat.format(date);
        timeText.setText(timeString);

//---------------------------------------------LIST OF TEHSIL ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        tehsilAdapter = ArrayAdapter.createFromResource(this, R.array.array_tehsil_subdivision, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        tehsilAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e tehsilAdapter
        tehsilSpinner.setAdapter(tehsilAdapter);
        //now when selecting any tehsil from spinner
        tehsilSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selectedTehsil variable
                selectedTehsil = tehsilSpinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//---------------------------------------------LIST OF BLOCK ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        blockAdapter = ArrayAdapter.createFromResource(this, R.array.array_block_mandal, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        blockAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e blockAdapter
        blockSpinner.setAdapter(blockAdapter);
        //now when selecting any block from spinner
        blockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of block spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selectedBlock variable
                selectedBlock = blockSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//---------------------------------------------LIST OF TOPOSHEET 250K ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        topo250kAdapter = ArrayAdapter.createFromResource(this, R.array.array_toposheet_250k, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        topo250kAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e toposheet250kAdapter
        topo250kSpinner.setAdapter(topo250kAdapter);
        //now when selecting any toposheet 250k from spinner
        topo250kSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selectedTopo250k variable
                selectedTopo250k = topo250kSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//---------------------------------------------LIST OF TOPOSHEET 50K ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        topo50kAdapter = ArrayAdapter.createFromResource(this, R.array.array_toposheet_50k, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        topo50kAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e toposheet50kAdapter
        topo50kSpinner.setAdapter(topo50kAdapter);
        //now when selecting any toposheet 50k from spinner
        topo50kSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }
                //storing selected option from spinner save into selectedTopo50k variable
                selectedTopo50k = topo50kSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//---------------------------------------------LIST OF STATE AND DISTRICT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.array_indian_state, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        stateAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e stateAdapter
        stateSpinner.setAdapter(stateAdapter);

        //now when selecting any state from spinner
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }


                //storing the selected state to "selectedState" variable
                selectedState = stateSpinner.getSelectedItem().toString();
                int parentID = parent.getId();
                if (parentID == R.id.spin_select_state) {
                    switch (selectedState) {
                        case "Select Your State":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_default_districts, R.layout.spinner_item);
                            break;
                        case "Andhra Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_andhra_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Arunachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_arunachal_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Assam":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_assam_districts, R.layout.spinner_item);
                            break;
                        case "Bihar":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_bihar_districts, R.layout.spinner_item);
                            break;
                        case "Chhattisgarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_chhattisgarh_districts, R.layout.spinner_item);
                            break;
                        case "Goa":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_goa_districts, R.layout.spinner_item);
                            break;
                        case "Gujarat":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_gujarat_districts, R.layout.spinner_item);
                            break;
                        case "Haryana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_haryana_districts, R.layout.spinner_item);
                            break;
                        case "Himachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_himachal_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Jharkhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_jharkhand_districts, R.layout.spinner_item);
                            break;
                        case "Karnataka":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_karnataka_districts, R.layout.spinner_item);
                            break;
                        case "Kerala":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_kerala_districts, R.layout.spinner_item);
                            break;
                        case "Madhya Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_madhya_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Maharashtra":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_maharashtra_districts, R.layout.spinner_item);
                            break;
                        case "Manipur":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_manipur_districts, R.layout.spinner_item);
                            break;
                        case "Meghalaya":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_meghalaya_districts, R.layout.spinner_item);
                            break;
                        case "Mizoram":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_mizoram_districts, R.layout.spinner_item);
                            break;
                        case "Nagaland":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_nagaland_districts, R.layout.spinner_item);
                            break;
                        case "Odisha":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_odisha_districts, R.layout.spinner_item);
                            break;
                        case "Punjab":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_punjab_districts, R.layout.spinner_item);
                            break;
                        case "Rajasthan":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_rajasthan_districts, R.layout.spinner_item);
                            break;
                        case "Sikkim":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_sikkim_districts, R.layout.spinner_item);
                            break;
                        case "Tamil Nadu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_tamil_nadu_districts, R.layout.spinner_item);
                            break;
                        case "Telangana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_telangana_districts, R.layout.spinner_item);
                            break;
                        case "Tripura":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_tripura_districts, R.layout.spinner_item);
                            break;
                        case "Uttar Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_uttar_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Uttarakhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_uttarakhand_districts, R.layout.spinner_item);
                            break;
                        case "West Bengal":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_west_bengal_districts, R.layout.spinner_item);
                            break;
                        case "Andaman and Nicobar Islands":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_andaman_nicobar_districts, R.layout.spinner_item);
                            break;
                        case "Chandigarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_chandigarh_districts, R.layout.spinner_item);
                            break;
                        case "Dadra and Nagar Haveli":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_item);
                            break;
                        case "Daman and Diu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_daman_diu_districts, R.layout.spinner_item);
                            break;
                        case "Delhi":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_delhi_districts, R.layout.spinner_item);
                            break;
                        case "Jammu and Kashmir":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_jammu_kashmir_districts, R.layout.spinner_item);
                            break;
                        case "Lakshadweep":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_lakshadweep_districts, R.layout.spinner_item);
                            break;
                        case "Ladakh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_ladakh_districts, R.layout.spinner_item);
                            break;
                        case "Puducherry":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_puducherry_districts, R.layout.spinner_item);
                            break;
                        default:
                            break;
                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);     // Specify the layout to use when the list of choices appears
                    districtSpinner.setAdapter(districtAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //now when selecting any district for spinner
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //for disabling the first option of state spinner
                selectedDistrict = districtSpinner.getSelectedItem().toString();
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//----------------------------------------------------------------------------------------------------------------------------------------------------------

        //for back button

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });

        //for next button
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LocationDetails.this, SoilSiteParameters.class);
//                startActivity(intent);
//            }
//        });
    //---------shared preference----------------
        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);
        //when open the activity then first check "shared preference" data available or not
        String projID = sharedPreferences.getString(KEY_PROJECT_PROFILE_ID, null);
        if (projID == null) {
            Toast.makeText(LocationDetails.this, "Enter project profile id!!", Toast.LENGTH_SHORT).show();
        }
    }

//--------------BUTTON METHOD FOR GETTING CURRENT LOCATION DETAILS---------------
    public void getLocation(View view){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                LocationDetails.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                LocationDetails.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                latitudeText.setText(latitude);
                longitudeText.setText(longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

//----------BUTTON METHOD FOR SAVING DATA------------------
    public void SubmitBtn1(View view) {
        // below is for progress dialog box
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        //--------------------SHAREDPREFERENCE-------------------
        //when clicking btn put data on shared preference
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PROJECT_PROFILE_ID, etprojProfileID.getText().toString());
        editor.apply();

        surveyorname = etsurveyorName.getText().toString().trim();
        villagename = etvillageName.getText().toString().trim();
        elevation = etelevation.getText().toString().trim();
        projectProfileID = etprojProfileID.getText().toString().trim();
        remark = etremark.getText().toString().trim();
        date = dateText.getText().toString().trim();
        time = timeText.getText().toString().trim();

        Log.d("dateeee", date);
//        state = stateSpinner.getSelectedItem().toString().trim();
//        tehsil = tehsilSpinner.getSelectedItem().toString().trim();
//        block = blockSpinner.getSelectedItem().toString().trim();
//        toposheet250k = topo250kSpinner.getSelectedItem().toString().trim();
//        toposheet50k = topo50kSpinner.getSelectedItem().toString().trim();

        //----------validating the text fields if empty or not.-------------------//commenting only for next page codig
//        if (TextUtils.isEmpty(surveyorname)) {
//            etsurveyorName.setError("Please enter surveyor name..");
//        } else if (TextUtils.isEmpty(villagename)) {
//            etvillageName.setError("Please enter village name...");
//        } else if (TextUtils.isEmpty(elevation)) {
//            etelevation.setError("Please enter elevation...");
//        } else if (TextUtils.isEmpty(projectProfileID)) {
//            etprojProfileID.setError("Please enter project profile ID...");
//        } else if (TextUtils.isEmpty(remark)) {
//            etremark.setError("Please enter remark...");
//        }
//        else if (selectedState.equals("Select Your State")) {
//            Toast.makeText(LocationDetails.this, "Please Select state !!", Toast.LENGTH_LONG).show();
//        } else if (selectedDistrict.equals("Select Your District")) {
//            Toast.makeText(LocationDetails.this, "Please Select district !!", Toast.LENGTH_LONG).show();
//        } else if (selectedTehsil.equals("Select Tehsil...")) {
//            Toast.makeText(LocationDetails.this, "Please Select tehsil !!", Toast.LENGTH_LONG).show();
//        } else if (selectedBlock.equals("Select Block...")) {
//            Toast.makeText(LocationDetails.this, "Please Select district !!", Toast.LENGTH_LONG).show();
//        } else if (selectedTopo250k.equals("Select Toposheet 250k...")) {
//            Toast.makeText(LocationDetails.this, "Please Select toposheet 250k !!", Toast.LENGTH_LONG).show();
//        } else if (selectedTopo50k.equals("Select Toposheet 50k...")) {
//            Toast.makeText(LocationDetails.this, "Please Select toposheet 50k !!", Toast.LENGTH_LONG).show();
//        }
//        else {
            // calling method to add data to Firebase Firestore.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("resssss", response);
                    if (TextUtils.equals(response, "success")) {
//                        tvStatus.setText("Successfully registered.");
                        Toast.makeText(LocationDetails.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                    } else {
//                        tvStatus.setText("Something went wrong!");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_PROJECT_PROFILE_ID, projectProfileID);
                        editor.apply();
                        finish();
                        startActivity(new Intent(LocationDetails.this, SoilSiteParameters.class));
                        Toast.makeText(LocationDetails.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
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
                    data.put("surveyorname", surveyorname);
                    data.put("villagename", villagename);
                    data.put("date", date);
                    data.put("time", time);
                    data.put("latitude", latitude);
                    data.put("longitude", longitude);
                    data.put("elevation", elevation);
                    data.put("projectProfileID", projectProfileID);
                    data.put("remark", remark);
                    data.put("state", selectedState);
                    data.put("district", selectedDistrict);
                    data.put("tehsil", selectedTehsil);
                    data.put("block", selectedBlock);
                    data.put("toposheet250k", selectedTopo250k);
                    data.put("toposheet50k", selectedTopo50k);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

//    }

}