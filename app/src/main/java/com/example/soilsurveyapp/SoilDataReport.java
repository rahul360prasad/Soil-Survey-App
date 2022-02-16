package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SoilDataReport extends AppCompatActivity {

    TextView heading_lbl, searchBy, lbl_report;
    //----LinearLayout-----------
    private LinearLayout stateLinear, districtLinear, projIDLinear, dateLinear;
    Button searchBtn;

    //--------------------FOR STATE WISE REPORT GENERATION CODE------------------------
    ListView listview;
    LinearLayout report_root_layout;

    //declaring variable for storing selected data
    private String selectedState, selectedDistrict, selectedSearchBy, selectedProjectID, selectedDate;
    // defining spinner
    private Spinner stateSpinner, districtSpinner, searchBySpinner, projectIDSpinner, projectDateSpinner;
    //defining and declaring array adapter
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter, searchByAdapter, projectIDAdapter, projectDateAdapter;

    //-----------for search by project ID and Date----------------
    ArrayList<String> projectIDList = new ArrayList<>();
    ArrayList<String> projectDateList = new ArrayList<>();
    RequestQueue requestQueue;

    ProgressDialog progressDialog;
    //url_state for checking selected state from database
    String url_state = "http://10.0.0.145/login/state_report.php";

    //--------------SHARED PREFERENCES----------------
    SharedPreferences sharedPreferences;
    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "soilDataReport";
    private static final String KEY_STATE = "state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_data_report);

        heading_lbl = (TextView) findViewById(R.id.h_soil_data_report);
        searchBy = (TextView) findViewById(R.id.lbl_search_by);
        report_root_layout = (LinearLayout) findViewById(R.id.report_root_layout);
        listview = (ListView) findViewById(R.id.listView);
        lbl_report = (TextView) findViewById(R.id.lbl_report);

        //url_id for fetching all project id from database
        String url_id = "http://10.0.0.145/login/project_ID.php";
        //url_date for fetching all date from database
        String url_date = "http://10.0.0.145/login/projectDate.php";

        requestQueue = Volley.newRequestQueue(this);

        //-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
//            this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {
        }

        //----------spinnersArrayAdapter REFERENCES------------------------------
        searchBySpinner = (Spinner) findViewById(R.id.spin_search_by);

        //-----hidden part of Search by state, district, projectID and date------
        stateLinear = (LinearLayout) findViewById(R.id.statelinearLayout);
        districtLinear = (LinearLayout) findViewById(R.id.districtlinearLayout);
        projIDLinear = (LinearLayout) findViewById(R.id.projIDlinearLayout);
        dateLinear = (LinearLayout) findViewById(R.id.datelinearLayout);


        stateSpinner = (Spinner) findViewById(R.id.spin_sr_select_state);
        districtSpinner = (Spinner) findViewById(R.id.spin_sr_select_district);
        projectIDSpinner = (Spinner) findViewById(R.id.spin_sr_select_projID);
        projectDateSpinner = (Spinner) findViewById(R.id.spin_sr_select_date);

        searchBtn = (Button) findViewById(R.id.search_btn);

        //---------------------------------------------LIST OF SEARCH BY AND STATE AND DISTRICT ON SPINNER-----------------------------------------------------------------

        //-------------------------------SEARCH BY---------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        searchByAdapter = ArrayAdapter.createFromResource(this, R.array.array_search_by, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        searchByAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        searchBySpinner.setAdapter(searchByAdapter);
        //now when selecting any option from spinner
        searchBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedSearchBy = searchBySpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);

                int parentID = parent.getId();
                if (parentID == R.id.spin_search_by) {
                    switch (selectedSearchBy) {
                        case "Select Option...":
                            stateLinear.setVisibility(View.GONE);
                            districtLinear.setVisibility(View.GONE);
                            projIDLinear.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.GONE);
                            break;
                        case "State":
                            stateLinear.setVisibility(View.VISIBLE);
                            districtLinear.setVisibility(View.VISIBLE);
                            projIDLinear.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.VISIBLE);
                            break;
                        case "Project ID":
                            projIDLinear.setVisibility(View.VISIBLE);
                            stateLinear.setVisibility(View.GONE);
                            districtLinear.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.VISIBLE);
                            break;
                        case "Date":
                            dateLinear.setVisibility(View.VISIBLE);
                            stateLinear.setVisibility(View.GONE);
                            districtLinear.setVisibility(View.GONE);
                            projIDLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //-----------------------------------STATE LIST---------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.array_grd_indian_state, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        stateAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e stateAdapter
        stateSpinner.setAdapter(stateAdapter);

        //now when selecting any state from spinner
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                searchBtn.setVisibility(View.VISIBLE);
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }
                //when selecting the state name district spinner is populated
                districtSpinner = (Spinner) findViewById(R.id.spin_sr_select_district);

                //storing the selected state to "selectedState" variable
                selectedState = stateSpinner.getSelectedItem().toString();
                int parentID = parent.getId();
                if (parentID == R.id.spin_sr_select_state) {
                    switch (selectedState) {
                        case "Select Your State":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_default_districts, R.layout.spinner_item);
                            break;
                        case "Andhra Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_andhra_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Arunachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_arunachal_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Assam":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_assam_districts, R.layout.spinner_item);
                            break;
                        case "Bihar":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_bihar_districts, R.layout.spinner_item);
                            break;
                        case "Chhattisgarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_chhattisgarh_districts, R.layout.spinner_item);
                            break;
                        case "Goa":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_goa_districts, R.layout.spinner_item);
                            break;
                        case "Gujarat":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_gujarat_districts, R.layout.spinner_item);
                            break;
                        case "Haryana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_haryana_districts, R.layout.spinner_item);
                            break;
                        case "Himachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_himachal_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Jharkhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_jharkhand_districts, R.layout.spinner_item);
                            break;
                        case "Karnataka":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_karnataka_districts, R.layout.spinner_item);
                            break;
                        case "Kerala":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_kerala_districts, R.layout.spinner_item);
                            break;
                        case "Madhya Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_madhya_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Maharashtra":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_maharashtra_districts, R.layout.spinner_item);
                            break;
                        case "Manipur":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_manipur_districts, R.layout.spinner_item);
                            break;
                        case "Meghalaya":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_meghalaya_districts, R.layout.spinner_item);
                            break;
                        case "Mizoram":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_mizoram_districts, R.layout.spinner_item);
                            break;
                        case "Nagaland":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_nagaland_districts, R.layout.spinner_item);
                            break;
                        case "Odisha":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_odisha_districts, R.layout.spinner_item);
                            break;
                        case "Punjab":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_punjab_districts, R.layout.spinner_item);
                            break;
                        case "Rajasthan":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_rajasthan_districts, R.layout.spinner_item);
                            break;
                        case "Sikkim":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_sikkim_districts, R.layout.spinner_item);
                            break;
                        case "Tamil Nadu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_tamil_nadu_districts, R.layout.spinner_item);
                            break;
                        case "Telangana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_telangana_districts, R.layout.spinner_item);
                            break;
                        case "Tripura":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_tripura_districts, R.layout.spinner_item);
                            break;
                        case "Uttar Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_uttar_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Uttarakhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_uttarakhand_districts, R.layout.spinner_item);
                            break;
                        case "West Bengal":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_west_bengal_districts, R.layout.spinner_item);
                            break;
                        case "Andaman and Nicobar Islands":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_andaman_nicobar_districts, R.layout.spinner_item);
                            break;
                        case "Chandigarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_chandigarh_districts, R.layout.spinner_item);
                            break;
                        case "Dadra and Nagar Haveli":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_dadra_nagar_haveli_districts, R.layout.spinner_item);
                            break;
                        case "Daman and Diu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_daman_diu_districts, R.layout.spinner_item);
                            break;
                        case "Delhi":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_delhi_districts, R.layout.spinner_item);
                            break;
                        case "Jammu and Kashmir":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_jammu_kashmir_districts, R.layout.spinner_item);
                            break;
                        case "Lakshadweep":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_lakshadweep_districts, R.layout.spinner_item);
                            break;
                        case "Ladakh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_ladakh_districts, R.layout.spinner_item);
                            break;
                        case "Puducherry":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_grd_puducherry_districts, R.layout.spinner_item);
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

        //----------------------------SEARCH BY PROJECT ID--------------------------

        projectIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProjectID = projectIDSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("projectregistrationtbl");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectID = jsonObject.optString("projID");
                        projectIDList.add(projectID);
                        projectIDAdapter = new ArrayAdapter(SoilDataReport.this, android.R.layout.simple_spinner_item, projectIDList);
                        projectIDAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
                        projectIDSpinner.setAdapter(projectIDAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        //----------------------------SEARCH BY DATE--------------------------
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, url_date, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("locationdetails");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectID = jsonObject.optString("date");
                        projectDateList.add(projectID);
                        projectDateAdapter = new ArrayAdapter(SoilDataReport.this, android.R.layout.simple_spinner_item, projectDateList);
                        projectDateAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
                        projectDateSpinner.setAdapter(projectDateAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest2);
        //----------------------------SHOWING DATE CALENDER------------------------note: working--------------------------
//        dateTextView=findViewById(R.id.date_sr_date);
//        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
//        materialDateBuilder.setTitleText("DD/MM/YYYY");
//        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
//
//        dateTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
//            }
//        });
//
//        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onPositiveButtonClick(Object selection) {
//                dateTextView.setText(materialDatePicker.getHeaderText());
//            }
//        });

        //---------shared preference----------------
        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);
        //when open the activity then first check "shared preference" data available or not
        String state = sharedPreferences.getString(KEY_STATE, null);
        if (state == null) {
            Toast.makeText(SoilDataReport.this, "Enter State!!", Toast.LENGTH_SHORT).show();
        }
    }

    //-----------HOME ICON on action bar-------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu:
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //search button functionality
    public void searchBtn(View view) {
        //Initialinzing the progress Dialog
        progressDialog = new ProgressDialog(SoilDataReport.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

//--------------------SHAREDPREFERENCE-------------------
        //when clicking btn put data on shared preference
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_STATE, stateSpinner.getSelectedItem().toString());
        editor.apply();

        if (selectedSearchBy.equals("State")) {
            if (!selectedState.equals("Select Your State")) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_state, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_STATE, selectedState);
                            editor.apply();
                            finish();

                            showJSON(response);
                            report_root_layout.setVisibility(View.VISIBLE);
                            lbl_report.setVisibility(View.VISIBLE);
                            heading_lbl.setVisibility(View.GONE);
                            searchBy.setVisibility(View.GONE);
                            searchBySpinner.setVisibility(View.GONE);
                            stateLinear.setVisibility(View.GONE);
                            districtLinear.setVisibility(View.GONE);
                            projIDLinear.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        } else {
                            showJSON(response);
                            report_root_layout.setVisibility(View.VISIBLE);
                            lbl_report.setVisibility(View.VISIBLE);
                            heading_lbl.setVisibility(View.GONE);
                            searchBy.setVisibility(View.GONE);
                            searchBySpinner.setVisibility(View.GONE);
                            stateLinear.setVisibility(View.GONE);
                            districtLinear.setVisibility(View.GONE);
                            projIDLinear.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SoilDataReport.this, "Connectivity problem...", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("state", selectedState);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Choose state and district...", Toast.LENGTH_SHORT).show();
            }
        } else if (selectedSearchBy.equals("Project ID")) {
            Toast.makeText(this, selectedProjectID + " project id selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            Intent intent = new Intent(SoilDataReport.this, SearchByProjID.class);
            startActivity(intent);
//            if (!selectedState.equals("Select Your State")) {
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_state, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.equals("success")) {
//                            progressDialog.dismiss();
//                            Intent intent = new Intent(SoilDataReport.this, SearchByState.class);
//                            startActivity(intent);
//                        } else if (response.equals("failure")) {
//                            progressDialog.dismiss();
//                            Toast.makeText(SoilDataReport.this, "State not exist !!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(SoilDataReport.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> data = new HashMap<>();
//                        data.put("state", selectedState);
//                        return data;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
//            } else {
//                progressDialog.dismiss();
//                Toast.makeText(this, "Choose state and district...", Toast.LENGTH_SHORT).show();
//            }
        } else if (selectedSearchBy.equals("Date")) {
            Toast.makeText(this, "date id selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    //------------------METHOD FOR STATE CALLING FROM INSIDE SEARCH BUTTON METHOD-----------------------
    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String projID = jo.getString("projID");
                String projName = jo.getString("projName");
                String projProfID = jo.getString("projectProfileID");
                String state = jo.getString("state");
                String district = jo.getString("district");
                String tehsil = jo.getString("tehsil");
                String block = jo.getString("block");
                String village = jo.getString("villagename");
                String date = jo.getString("date");

                final HashMap<String, String> stateInfo = new HashMap<>();
                stateInfo.put("projID", projID);
                stateInfo.put("projName", projName);
                stateInfo.put("projProfID", projProfID);
                stateInfo.put("state", state);
                stateInfo.put("district", district);
                stateInfo.put("tehsil", tehsil);
                stateInfo.put("block", block);
                stateInfo.put("villagename", village);
                stateInfo.put("date", date);
                 list.add(stateInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(SoilDataReport.this, list, R.layout.report_list,
                new String[]{"projID","projName","projProfID","state","district","tehsil","block","village","date"},
                new int[]{R.id.tvPorjID, R.id.tvProjName, R.id.tvProjProfID, R.id.tvState, R.id.tvDistrict, R.id.tvTehsil, R.id.tvBlock, R.id.tvVillage, R.id.tvDate});
        listview.setAdapter(adapter);
    }
}