package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class admin_DataReport extends AppCompatActivity {

    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "soilDataReport";
    private static final String KEY_STATE = "state";
    public static ArrayList<LocationRecordData> LocationRecordDataArraylist = new ArrayList<>();
    LocationRecordData locationRecordData;
    String url_east_img, url_west_img, url_north_img, url_south_img;
    TextView heading_lbl, searchBy, lbl_statewise_report, lbl_projectidwise_report,
            lbl_project_details, lbl_location_details, lbl_soil_site_params_details,
            lbl_present_land_use_details, lbl_morphological_params_details, lbl_physical_params_details,
            lbl_chemical_params_details, lbl_soil_fertility_details, lbl_projectProfile_details, imageLblDialog;
    Button searchBtn;
    //--------------------FOR STATE WISE REPORT GENERATION CODE------------------------
    ListView listview, projReg_details_list, location_details_list, soilSiteParams_details_list,
            presentLandUse_details_list, morphological_details_list, physical_details_list, chemical_details_list,
            soilfertility_details_list, projProfileimg_details_list;
    LinearLayout state_report_root_layout, projID_report_root_layout, location_report_root_layouts,
            ssp_report_root_layouts, plu_report_root_layouts, mp_report_root_layout, pp_report_root_layout,
            cp_report_root_layout, sfp_report_root_layout, img_report_root_layout;
    //-----------for search by project ID and Date----------------
    ArrayList<String> projectIDList = new ArrayList<>();
    ArrayList<String> projectDateList = new ArrayList<>();
    RequestQueue requestQueue;
    //-----------for search by project profile ID and Date----------------
    ArrayList<String> projectprofileIDList = new ArrayList<>();
    RequestQueue requestQueue2;
    ProgressDialog progressDialog;
    //url_state for checking selected state from database!!
//    String url_state = "http://10.0.0.145/login/state_report.php";
    //url_id showing all project id list from projectregistrationdetail table
//    String url_projectID_list = "http://10.0.0.145/login/project_ID.php";
    //url_projectID for fetching particular project id details from database1!!
//    String url_projectID = "http://10.0.0.145/login/projectID_report.php";
    //url_profile_id for fetching all project profile id list from locationdetailsingle table
//    String url_profile_id = "http://10.0.0.145/login/project_profile_ID.php";
    //url_date for fetching all date from database
    String url_date = "http://10.0.0.145/login/projectDate.php";

    //------------live server links---------------------
    //url_state for checking selected state from database!!
    String url_state = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    //url_id showing all project id list from projectregistrationdetail table
    String url_projectID_list = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    //url_projectID for fetching particular project id details from database1!!
    String url_projectID = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    //url_profile_id for fetching all project profile id list from locationdetailsingle table
    String url_profile_id = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";

    //    String update_fields_url = "http://14.139.123.73:9090/web/NBSS/php/updateRecordsData.php";
    String adminStatusApproved_url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    String adminStatusNotApproved_url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";

    //--------------SHARED PREFERENCES----------------
    SharedPreferences sharedPreferences;
    ImageView report_east_imgView, report_west_imgView, report_north_imgView, report_south_imgView;
    //----LinearLayout-----------
    private LinearLayout stateLinear, districtLinear, projIDLinear, projProfileID, dateLinear;
    //declaring variable for storing selected data
    private String selectedState, selectedDistrict, selectedSearchBy, selectedProjectID, selectedProjectProfileID, selectedDate;
    // defining spinner
    private Spinner stateSpinner, districtSpinner, searchBySpinner, projectIDSpinner, projectProfileIDSpinner, projectDateSpinner;
    //defining and declaring array adapter
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter, searchByAdapter, projectIDAdapter, projectprofileIDAdapter, projectDateAdapter;

    private String approved, notApproved;
    private String admin_selectedProjectID, admin_selectedProjectProfileID, projectProfileID_admin;

    //--------------when pressing hardware back button----------------
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), admin_DataReport.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_report);

        approved = "Approved";
        notApproved = "Not Approved";

        //receiving intent params from updateRecords file
//        Intent intent = getIntent();
//        admin_selectedProjectID = intent.getExtras().getString("admin_selectedProjectID");
//        admin_selectedProjectProfileID = intent.getExtras().getString("admin_selectedProjectProfileID");

        heading_lbl = (TextView) findViewById(R.id.h_soil_data_report);
        searchBy = (TextView) findViewById(R.id.lbl_search_by);
        state_report_root_layout = (LinearLayout) findViewById(R.id.state_report_root_layout);
        projID_report_root_layout = (LinearLayout) findViewById(R.id.projID_report_root_layout);
        location_report_root_layouts = (LinearLayout) findViewById(R.id.location_report_root_layouts);
        ssp_report_root_layouts = (LinearLayout) findViewById(R.id.ssp_report_root_layouts);
        plu_report_root_layouts = (LinearLayout) findViewById(R.id.plu_report_root_layouts);
        mp_report_root_layout = (LinearLayout) findViewById(R.id.mp_report_root_layout);
        pp_report_root_layout = (LinearLayout) findViewById(R.id.pp_report_root_layout);
        cp_report_root_layout = (LinearLayout) findViewById(R.id.cp_report_root_layout);
        sfp_report_root_layout = (LinearLayout) findViewById(R.id.sfp_report_root_layout);
        img_report_root_layout = (LinearLayout) findViewById(R.id.img_report_root_layout);

        listview = (ListView) findViewById(R.id.listView);
        projReg_details_list = (ListView) findViewById(R.id.projReg_details_list);
        location_details_list = (ListView) findViewById(R.id.location_details_list);
        soilSiteParams_details_list = (ListView) findViewById(R.id.soilSiteParams_details_list);
        presentLandUse_details_list = (ListView) findViewById(R.id.presentLandUse_details_list);
        morphological_details_list = (ListView) findViewById(R.id.morphological_details_list);
        physical_details_list = (ListView) findViewById(R.id.physical_details_list);
        chemical_details_list = (ListView) findViewById(R.id.chemical_details_list);
        soilfertility_details_list = (ListView) findViewById(R.id.soilfertility_details_list);
        projProfileimg_details_list = (ListView) findViewById(R.id.projProfileimg_details_list);

        lbl_statewise_report = (TextView) findViewById(R.id.lbl_statewise_report);
        lbl_projectidwise_report = (TextView) findViewById(R.id.lbl_projectidwise_report);
        lbl_project_details = (TextView) findViewById(R.id.lbl_project_details);
        //-----location details-----------
        lbl_location_details = (TextView) findViewById(R.id.lbl_location_details);
        lbl_soil_site_params_details = (TextView) findViewById(R.id.lbl_soil_site_params_details);
        lbl_present_land_use_details = (TextView) findViewById(R.id.lbl_present_land_use_details);
        lbl_morphological_params_details = (TextView) findViewById(R.id.lbl_morphological_params_details);
        lbl_physical_params_details = (TextView) findViewById(R.id.lbl_physical_params_details);
        lbl_chemical_params_details = (TextView) findViewById(R.id.lbl_chemical_params_details);
        lbl_soil_fertility_details = (TextView) findViewById(R.id.lbl_soil_fertility_details);
        lbl_projectProfile_details = (TextView) findViewById(R.id.lbl_projectProfile_details);

        //for img logo
        report_east_imgView = (ImageView) findViewById(R.id.report_east_img);
        report_west_imgView = (ImageView) findViewById(R.id.report_west_imgView);
        report_north_imgView = (ImageView) findViewById(R.id.report_north_imgView);
        report_south_imgView = (ImageView) findViewById(R.id.report_south_imgView);


        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);

        //-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {
        }

        //----------spinnersArrayAdapter REFERENCES------------------------------
        searchBySpinner = (Spinner) findViewById(R.id.spin_search_by);

        //-----hidden part of Search by state, district, projectID and date------
        stateLinear = (LinearLayout) findViewById(R.id.statelinearLayout);
        districtLinear = (LinearLayout) findViewById(R.id.districtlinearLayout);
        projIDLinear = (LinearLayout) findViewById(R.id.projIDlinearLayout);
        projProfileID = (LinearLayout) findViewById(R.id.projProfileIDlinearLayout);
        dateLinear = (LinearLayout) findViewById(R.id.datelinearLayout);

        stateSpinner = (Spinner) findViewById(R.id.spin_sr_select_state);
        districtSpinner = (Spinner) findViewById(R.id.spin_sr_select_district);
        projectIDSpinner = (Spinner) findViewById(R.id.spin_sr_select_projID);
        projectProfileIDSpinner = (Spinner) findViewById(R.id.spin_sr_select_projprofileID);
        projectDateSpinner = (Spinner) findViewById(R.id.spin_sr_select_date);

        imageLblDialog = (TextView) findViewById(R.id.img_lbl_dialog);

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
                            projProfileID.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.GONE);
                            break;
                        case "State":
                            stateLinear.setVisibility(View.VISIBLE);
                            districtLinear.setVisibility(View.GONE);
                            projIDLinear.setVisibility(View.GONE);
                            projProfileID.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.VISIBLE);
                            break;
                        case "Project ID":
                            projIDLinear.setVisibility(View.VISIBLE);
                            projProfileID.setVisibility(View.VISIBLE);
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
                            projProfileID.setVisibility(View.GONE);
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
        //--------below code is for fetching all the details particular project id on spinner
        projectIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProjectID = projectIDSpinner.getSelectedItem().toString();
                if (adapterView.getItemAtPosition(i).equals(selectedProjectID)) {
                    projectprofileIDList.clear();
                    String selectedProjectID = adapterView.getSelectedItem().toString();
                    //-----------below array json is for project profile id from location detail single table------------
                    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST,
                            url_profile_id + "?TYPE=project_profile_ID_list&projID=" + selectedProjectID,
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("locationdetailsingle");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String projectprofileID = jsonObject.optString("projectProfileID");
                                    projectprofileIDList.add(projectprofileID);
                                    projectprofileIDAdapter = new ArrayAdapter(admin_DataReport.this, android.R.layout.simple_spinner_item, projectprofileIDList);
                                    projectprofileIDAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
                                    projectProfileIDSpinner.setAdapter(projectprofileIDAdapter);
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //-------below code is for showing list of project profile id on their spinner
        projectProfileIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProjectProfileID = projectProfileIDSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //-----------below array json is for showing the list of all project id from project locationdetailsingle table------------
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_projectID_list + "?TYPE=report_project_ID_list", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(String.valueOf(response), "jsonobject");
                try {
                    JSONArray jsonArray = response.getJSONArray("locationdetailsingle");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectID = jsonObject.optString("projID");
                        projectIDList.add(projectID);
                        projectIDAdapter = new ArrayAdapter(admin_DataReport.this, android.R.layout.simple_spinner_item, projectIDList);
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
        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.POST, url_date, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("locationdetails");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectID = jsonObject.optString("date");
                        projectDateList.add(projectID);
                        projectDateAdapter = new ArrayAdapter(admin_DataReport.this, android.R.layout.simple_spinner_item, projectDateList);
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
        requestQueue2.add(jsonObjectRequest3);
        //---------shared preference----------------
        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);
        //when open the activity then first check "shared preference" data available or not
        String state = sharedPreferences.getString(KEY_STATE, null);
        if (state == null) {
            Toast.makeText(admin_DataReport.this, "Enter State!!", Toast.LENGTH_SHORT).show();
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
        progressDialog = new ProgressDialog(admin_DataReport.this);
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

        //-----------STATE----------
        if (selectedSearchBy.equals("State")) {
            if (!selectedState.equals("Select Your State")) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_state + "?TYPE=state_report&state=" + selectedState, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res", response);
                        if (response.equals("success")) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_STATE, selectedState);
                            editor.apply();
                            finish();
                            showJSON_statewise(response);
                            state_report_root_layout.setVisibility(View.VISIBLE);
                            projID_report_root_layout.setVisibility(View.GONE);
                            location_report_root_layouts.setVisibility(View.GONE);
                            ssp_report_root_layouts.setVisibility(View.GONE);
                            plu_report_root_layouts.setVisibility(View.GONE);
                            mp_report_root_layout.setVisibility(View.GONE);
                            pp_report_root_layout.setVisibility(View.GONE);
                            cp_report_root_layout.setVisibility(View.GONE);
                            sfp_report_root_layout.setVisibility(View.GONE);
                            img_report_root_layout.setVisibility(View.GONE);
                            lbl_statewise_report.setVisibility(View.VISIBLE);
                            lbl_projectidwise_report.setVisibility(View.GONE);
                            lbl_project_details.setVisibility(View.GONE);
                            lbl_location_details.setVisibility(View.GONE);
                            lbl_soil_site_params_details.setVisibility(View.GONE);
                            lbl_present_land_use_details.setVisibility(View.GONE);
                            lbl_morphological_params_details.setVisibility(View.GONE);
                            lbl_physical_params_details.setVisibility(View.GONE);
                            lbl_chemical_params_details.setVisibility(View.GONE);
                            lbl_soil_fertility_details.setVisibility(View.GONE);
                            lbl_projectProfile_details.setVisibility(View.GONE);
                            heading_lbl.setVisibility(View.GONE);
                            searchBy.setVisibility(View.GONE);
                            searchBySpinner.setVisibility(View.GONE);
                            stateLinear.setVisibility(View.GONE);
                            districtLinear.setVisibility(View.GONE);
                            projIDLinear.setVisibility(View.GONE);
                            projProfileID.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        } else {
                            showJSON_statewise(response);
                            state_report_root_layout.setVisibility(View.VISIBLE);
                            projID_report_root_layout.setVisibility(View.GONE);
                            location_report_root_layouts.setVisibility(View.GONE);
                            ssp_report_root_layouts.setVisibility(View.GONE);
                            plu_report_root_layouts.setVisibility(View.GONE);
                            mp_report_root_layout.setVisibility(View.GONE);
                            pp_report_root_layout.setVisibility(View.GONE);
                            cp_report_root_layout.setVisibility(View.GONE);
                            sfp_report_root_layout.setVisibility(View.GONE);
                            img_report_root_layout.setVisibility(View.GONE);
                            lbl_statewise_report.setVisibility(View.VISIBLE);
                            lbl_projectidwise_report.setVisibility(View.GONE);
                            lbl_project_details.setVisibility(View.GONE);
                            lbl_location_details.setVisibility(View.GONE);
                            lbl_soil_site_params_details.setVisibility(View.GONE);
                            lbl_present_land_use_details.setVisibility(View.GONE);
                            lbl_morphological_params_details.setVisibility(View.GONE);
                            lbl_physical_params_details.setVisibility(View.GONE);
                            lbl_chemical_params_details.setVisibility(View.GONE);
                            lbl_soil_fertility_details.setVisibility(View.GONE);
                            lbl_projectProfile_details.setVisibility(View.GONE);
                            heading_lbl.setVisibility(View.GONE);
                            searchBy.setVisibility(View.GONE);
                            searchBySpinner.setVisibility(View.GONE);
                            stateLinear.setVisibility(View.GONE);
                            districtLinear.setVisibility(View.GONE);
                            projIDLinear.setVisibility(View.GONE);
                            projProfileID.setVisibility(View.GONE);
                            dateLinear.setVisibility(View.GONE);
                            searchBtn.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(admin_DataReport.this, "Connectivity problem...", Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Choose state and district...", Toast.LENGTH_SHORT).show();
            }
        } else if (selectedSearchBy.equals("Project ID")) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_projectID +
                    "?TYPE=projectID_report&projID=" + selectedProjectID + "&projectProfileID=" +
                    selectedProjectProfileID, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.show();
                    if (response.equals("success")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_STATE, selectedState);
                        editor.apply();
                        finish();

                        showJSON_projectidwise(response);
                        projID_report_root_layout.setVisibility(View.VISIBLE);
                        location_report_root_layouts.setVisibility(View.VISIBLE);
                        ssp_report_root_layouts.setVisibility(View.VISIBLE);
                        plu_report_root_layouts.setVisibility(View.VISIBLE);
                        mp_report_root_layout.setVisibility(View.VISIBLE);
                        pp_report_root_layout.setVisibility(View.VISIBLE);
                        cp_report_root_layout.setVisibility(View.VISIBLE);
                        sfp_report_root_layout.setVisibility(View.VISIBLE);
                        img_report_root_layout.setVisibility(View.VISIBLE);
                        state_report_root_layout.setVisibility(View.GONE);
                        lbl_projectidwise_report.setVisibility(View.VISIBLE);
                        lbl_project_details.setVisibility(View.VISIBLE);
                        lbl_location_details.setVisibility(View.VISIBLE);
                        lbl_soil_site_params_details.setVisibility(View.VISIBLE);
                        lbl_present_land_use_details.setVisibility(View.VISIBLE);
                        lbl_morphological_params_details.setVisibility(View.VISIBLE);
                        lbl_physical_params_details.setVisibility(View.VISIBLE);
                        lbl_chemical_params_details.setVisibility(View.VISIBLE);
                        lbl_soil_fertility_details.setVisibility(View.VISIBLE);
                        lbl_projectProfile_details.setVisibility(View.VISIBLE);
                        lbl_statewise_report.setVisibility(View.GONE);
                        heading_lbl.setVisibility(View.GONE);
                        searchBy.setVisibility(View.GONE);
                        searchBySpinner.setVisibility(View.GONE);
                        stateLinear.setVisibility(View.GONE);
                        districtLinear.setVisibility(View.GONE);
                        projIDLinear.setVisibility(View.GONE);
                        projProfileID.setVisibility(View.GONE);
                        dateLinear.setVisibility(View.GONE);
                        searchBtn.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    } else {
                        showJSON_projectidwise(response);
                        projID_report_root_layout.setVisibility(View.VISIBLE);
                        location_report_root_layouts.setVisibility(View.VISIBLE);
                        ssp_report_root_layouts.setVisibility(View.VISIBLE);
                        plu_report_root_layouts.setVisibility(View.VISIBLE);
                        mp_report_root_layout.setVisibility(View.VISIBLE);
                        pp_report_root_layout.setVisibility(View.VISIBLE);
                        cp_report_root_layout.setVisibility(View.VISIBLE);
                        sfp_report_root_layout.setVisibility(View.VISIBLE);
                        img_report_root_layout.setVisibility(View.VISIBLE);
                        state_report_root_layout.setVisibility(View.GONE);
                        lbl_projectidwise_report.setVisibility(View.VISIBLE);
                        lbl_project_details.setVisibility(View.VISIBLE);
                        lbl_location_details.setVisibility(View.VISIBLE);
                        lbl_soil_site_params_details.setVisibility(View.VISIBLE);
                        lbl_present_land_use_details.setVisibility(View.VISIBLE);
                        lbl_morphological_params_details.setVisibility(View.VISIBLE);
                        lbl_physical_params_details.setVisibility(View.VISIBLE);
                        lbl_chemical_params_details.setVisibility(View.VISIBLE);
                        lbl_soil_fertility_details.setVisibility(View.VISIBLE);
                        lbl_projectProfile_details.setVisibility(View.VISIBLE);
                        lbl_statewise_report.setVisibility(View.GONE);
                        heading_lbl.setVisibility(View.GONE);
                        searchBy.setVisibility(View.GONE);
                        searchBySpinner.setVisibility(View.GONE);
                        stateLinear.setVisibility(View.GONE);
                        districtLinear.setVisibility(View.GONE);
                        projIDLinear.setVisibility(View.GONE);
                        projProfileID.setVisibility(View.GONE);
                        dateLinear.setVisibility(View.GONE);
                        searchBtn.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(admin_DataReport.this, "Please check your internet...", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else if (selectedSearchBy.equals("Date")) {
            Toast.makeText(this, "date id selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    //------------------METHOD FOR STATE CALLING FROM INSIDE SEARCH BUTTON METHOD-----------------------
    private void showJSON_statewise(String response) {
        Log.d("respo", response);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONArray resultList = new JSONArray(response);

            if (resultList.length() > 0) {
                for (int i = 0; i < resultList.length(); i++) {
                    JSONObject resultData = resultList.getJSONObject(i);
                    String projID = resultData.getString("projID");
                    String projName = resultData.getString("projNames");
                    String projProfID = resultData.getString("projectProfileID");
                    String state = resultData.getString("state");
                    String district = resultData.getString("district");
                    String tehsil = resultData.getString("tehsil");
                    String block = resultData.getString("block");
                    String villagename = resultData.getString("villagename");
                    String date = resultData.getString("date");

                    final HashMap<String, String> stateInfo = new HashMap<String, String>();
                    stateInfo.put("projID", projID);
                    stateInfo.put("projName", projName);
                    stateInfo.put("projProfID", projProfID);
                    stateInfo.put("state", state);
                    stateInfo.put("district", district);
                    stateInfo.put("tehsil", tehsil);
                    stateInfo.put("block", block);
                    stateInfo.put("villagename", villagename);
                    stateInfo.put("date", date);
                    list.add(stateInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.statewise_records,
                new String[]{"projID", "projName", "projProfID", "state", "district", "tehsil", "block", "villagename", "date"},
                new int[]{R.id.tvPorjID, R.id.tvProjName, R.id.tvProjProfID, R.id.tvState, R.id.tvDistrict, R.id.tvTehsil, R.id.tvBlock, R.id.tvVillage, R.id.tvDate});
        listview.setAdapter(adapter);
    }

    //------------------METHOD FOR PROJECT ID CALLING FROM INSIDE SEARCH BUTTON METHOD-----------------------
    private void showJSON_projectidwise(String response) {
        ArrayList<HashMap<String, String>> id_list = new ArrayList<HashMap<String, String>>();
        id_list.clear();
        try {
            JSONArray resultList = new JSONArray(response);
            if (resultList.length() > 0) {
                for (int i = 0; i < resultList.length(); i++) {
                    JSONObject jo = resultList.getJSONObject(i);
                    String projNames = jo.getString("projNames");
                    String projPeriod = jo.getString("projPeriod");
                    String projDuration = jo.getString("projDuration");
                    String projFundSrc = jo.getString("projFundSrc");
                    String date = jo.getString("date");

//                    -----------------------FOR LOCATION---------------
                    String projectProfileID = jo.getString("projectProfileID");
                    String surveyorname = jo.getString("surveyorname");
                    String state = jo.getString("state");
                    String district = jo.getString("district");
                    String tehsil = jo.getString("tehsil");
                    String block = jo.getString("block");
                    String villagename = jo.getString("villagename");
                    String toposheet250k = jo.getString("toposheet250k");
                    String toposheet50k = jo.getString("toposheet50k");
                    String loc_date = jo.getString("date");
                    String time = jo.getString("time");
                    String latitude = jo.getString("latitude");
                    String longitude = jo.getString("longitude");
                    String elevation = jo.getString("elevation");

                    //  -----------------------FOR SOIL SITE PARAMETERS---------------
                    String physiographicCategory = jo.getString("physiographicCategory");
                    String subPhysiographicUnit = jo.getString("subPhysiographicUnit");
                    String geology = jo.getString("geology");
                    String parentMaterial = jo.getString("parentMaterial");
                    String climate = jo.getString("climate");
                    String rainfall = jo.getString("rainfall");
                    String topographyLandformType = jo.getString("topographyLandformType");
                    String slopeGradiant = jo.getString("slopeGradiant");
                    String slopeLength = jo.getString("slopeLength");
                    String erosion = jo.getString("erosion");
                    String runoff = jo.getString("runoff");
                    String drainage = jo.getString("drainage");
                    String groundWaterDepth = jo.getString("groundWaterDepth");
                    String flooding = jo.getString("flooding");
                    String saltAlkali = jo.getString("saltAlkali");
                    String pH = jo.getString("pH");
                    String Ec = jo.getString("Ec");
                    String stoneSize = jo.getString("stoneSize");
                    String stoiness = jo.getString("stoiness");
                    String rockOutcrops = jo.getString("rockOutcrops");
                    String naturalVegetation = jo.getString("naturalVegetation");

                    //  -----------------------FOR PRESENT LAND USE---------------
                    String forest = jo.getString("forest");
                    String cultivated = jo.getString("cultivated");
                    String terraces = jo.getString("terraces");
                    String pastureLand = jo.getString("pastureLand");
                    String degradedCulturable = jo.getString("degradedCulturable");
                    String degradedUnCulturable = jo.getString("degradedUnCulturable");
                    String phaseSurface = jo.getString("phaseSurface");
                    String phaseSubstratum = jo.getString("phaseSubstratum");
                    String landCapabilityClass = jo.getString("landCapabilityClass");
                    String landCapabilitySubClass = jo.getString("landCapabilitySubClass");
                    String landIrrigabilityClass = jo.getString("landIrrigabilityClass");
                    String landIrrigabilitySubClass = jo.getString("landIrrigabilitySubClass");
                    String remarks = jo.getString("remarks");
                    String crop = jo.getString("crop");
                    String yield = jo.getString("yield");
                    String managementPractice = jo.getString("managementPractice");
                    String croppingSystem = jo.getString("croppingSystem");

                    //  -----------------------FOR MORPHOLOGICAL PARAMETERS---------------
                    String horizon = jo.getString("horizon");
                    String depth = jo.getString("depth");
                    String b_distinctness = jo.getString("b_distinctness");
                    String b_topography = jo.getString("b_topography");
                    String b_diagnostic = jo.getString("b_diagnostic");
                    String b_matrixColour = jo.getString("b_matrixColour");
                    String mc_abundance = jo.getString("mc_abundance");
                    String mc_size = jo.getString("mc_size");
                    String mc_contrast = jo.getString("mc_contrast");
                    String mc_texture = jo.getString("mc_texture");
                    String cf_size = jo.getString("cf_size");
                    String cf_vol = jo.getString("cf_vol");
                    String str_size = jo.getString("str_size");
                    String str_grade = jo.getString("str_grade");
                    String str_type = jo.getString("str_type");
                    String con_d = jo.getString("con_d");
                    String con_m = jo.getString("con_m");
                    String con_w = jo.getString("con_w");
                    String poros_s = jo.getString("poros_s");
                    String poros_q = jo.getString("poros_q");
                    String cutans_ty = jo.getString("cutans_ty");
                    String cutans_th = jo.getString("cutans_th");
                    String cutans_q = jo.getString("cutans_q");
                    String nodules_s = jo.getString("nodules_s");
                    String nodules_q = jo.getString("nodules_q");
                    String roots_s = jo.getString("roots_s");
                    String roots_q = jo.getString("roots_q");
                    String roots_effervescence = jo.getString("roots_effervescence");
                    String of_size = jo.getString("of_size");
                    String of_abundance = jo.getString("of_abundance");
                    String of_nature = jo.getString("of_nature");
                    String of_samplebagno = jo.getString("of_samplebagno");
                    String of_additionalnotes = jo.getString("of_additionalnotes");

                    //  -----------------------FOR PHYSICAL PARAMETERS---------------
                    String sand = jo.getString("sand");
                    String silt = jo.getString("silt");
                    String clay = jo.getString("clay");
                    String USDA_textural_class = jo.getString("USDA_textural_class");
                    String bulk_density = jo.getString("bulk_density");
                    String wr_33kPa = jo.getString("wr_33kPa");
                    String wr_1500kPa = jo.getString("wr_1500kPa");
                    String AWC = jo.getString("AWC");
                    String PAWC = jo.getString("PAWC");
                    String gravimetric_moisture = jo.getString("gravimetric_moisture");

                    //  -----------------------FOR CHEMICAL PARAMETERS---------------
                    String cp_pH = jo.getString("cp_pH");
                    String cp_EC = jo.getString("cp_EC");
                    String OC = jo.getString("OC");
                    String CaCo = jo.getString("CaCo");
                    String Ca = jo.getString("Ca");
                    String Mg = jo.getString("Mg");
                    String Na = jo.getString("Na");
                    String K = jo.getString("K");
                    String totalBase = jo.getString("totalBase");
                    String CEC = jo.getString("CEC");
                    String BS = jo.getString("BS");
                    String ESP = jo.getString("ESP");

                    //  -----------------------FOR SOIL FERTILITY PARAMETERS---------------
                    String organicCarbon = jo.getString("organicCarbon");
                    String MaN_nitrogen = jo.getString("MaN_nitrogen");
                    String MaN_phosphorus = jo.getString("MaN_phosphorus");
                    String MaN_potassium = jo.getString("MaN_potassium");
                    String MiN_sulphur = jo.getString("MiN_sulphur");
                    String MiN_zinc = jo.getString("MiN_zinc");
                    String MiN_copper = jo.getString("MiN_copper");
                    String MiN_iron = jo.getString("MiN_iron");
                    String MiN_manganese = jo.getString("MiN_manganese");//

                    //  -----------------------FOR IMAGES---------------
                    String east_image = jo.getString("east_image");
                    String west_image = jo.getString("west_image");
                    String north_image = jo.getString("north_image");
                    String south_image = jo.getString("south_image");

                    locationRecordData = new LocationRecordData(surveyorname);
                    LocationRecordDataArraylist.add(locationRecordData);

                    final HashMap<String, String> stateInfo = new HashMap<>();
                    stateInfo.put("projNames", projNames);
                    stateInfo.put("projPeriod", projPeriod);
                    stateInfo.put("projDuration", projDuration);
                    stateInfo.put("projFundSrc", projFundSrc);
                    stateInfo.put("date", date);

                    //    -----------------------FOR LOCATION---------------
                    stateInfo.put("projectProfileID", projectProfileID);
                    stateInfo.put("surveyorname", surveyorname);
                    stateInfo.put("state", state);
                    stateInfo.put("district", district);
                    stateInfo.put("tehsil", tehsil);
                    stateInfo.put("block", block);
                    stateInfo.put("villagename", villagename);
                    stateInfo.put("toposheet250k", toposheet250k);
                    stateInfo.put("toposheet50k", toposheet50k);
                    stateInfo.put("date", loc_date);
                    stateInfo.put("time", time);
                    stateInfo.put("latitude", latitude);
                    stateInfo.put("longitude", longitude);
                    stateInfo.put("elevation", elevation);

                    //    -----------------------FOR SOIL SITE PARAMETERS---------------
                    stateInfo.put("physiographicCategory", physiographicCategory);
                    stateInfo.put("subPhysiographicUnit", subPhysiographicUnit);
                    stateInfo.put("geology", geology);
                    stateInfo.put("parentMaterial", parentMaterial);
                    stateInfo.put("climate", climate);
                    stateInfo.put("rainfall", rainfall);
                    stateInfo.put("topographyLandformType", topographyLandformType);
                    stateInfo.put("slopeGradiant", slopeGradiant);
                    stateInfo.put("slopeLength", slopeLength);
                    stateInfo.put("erosion", erosion);
                    stateInfo.put("runoff", runoff);
                    stateInfo.put("drainage", drainage);
                    stateInfo.put("groundWaterDepth", groundWaterDepth);
                    stateInfo.put("flooding", flooding);
                    stateInfo.put("saltAlkali", saltAlkali);
                    stateInfo.put("pH", pH);
                    stateInfo.put("Ec", Ec);
                    stateInfo.put("stoneSize", stoneSize);
                    stateInfo.put("stoiness", stoiness);
                    stateInfo.put("rockOutcrops", rockOutcrops);
                    stateInfo.put("naturalVegetation", naturalVegetation);

                    //    -----------------------FOR PRESENT LAND USE---------------
                    stateInfo.put("forest", forest);
                    stateInfo.put("cultivated", cultivated);
                    stateInfo.put("terraces", terraces);
                    stateInfo.put("pastureLand", pastureLand);
                    stateInfo.put("degradedCulturable", degradedCulturable);
                    stateInfo.put("degradedUnCulturable", degradedUnCulturable);
                    stateInfo.put("phaseSurface", phaseSurface);
                    stateInfo.put("phaseSubstratum", phaseSubstratum);
                    stateInfo.put("landCapabilityClass", landCapabilityClass);
                    stateInfo.put("landCapabilitySubClass", landCapabilitySubClass);
                    stateInfo.put("landIrrigabilityClass", landIrrigabilityClass);
                    stateInfo.put("landIrrigabilitySubClass", landIrrigabilitySubClass);
                    stateInfo.put("remarks", remarks);
                    stateInfo.put("crop", crop);
                    stateInfo.put("yield", yield);
                    stateInfo.put("managementPractice", managementPractice);
                    stateInfo.put("croppingSystem", croppingSystem);

                    //    -----------------------FOR MORPHOLOGICAL PARAMETERS---------------
                    stateInfo.put("horizon", horizon);
                    stateInfo.put("depth", depth);
                    stateInfo.put("b_distinctness", b_distinctness);
                    stateInfo.put("b_topography", b_topography);
                    stateInfo.put("b_diagnostic", b_diagnostic);
                    stateInfo.put("b_matrixColour", b_matrixColour);
                    stateInfo.put("mc_abundance", mc_abundance);
                    stateInfo.put("mc_size", mc_size);
                    stateInfo.put("mc_contrast", mc_contrast);
                    stateInfo.put("mc_texture", mc_texture);
                    stateInfo.put("cf_size", cf_size);
                    stateInfo.put("cf_vol", cf_vol);
                    stateInfo.put("str_size", str_size);
                    stateInfo.put("str_grade", str_grade);
                    stateInfo.put("str_type", str_type);
                    stateInfo.put("con_d", con_d);
                    stateInfo.put("con_m", con_m);
                    stateInfo.put("con_w", con_w);
                    stateInfo.put("poros_s", poros_s);
                    stateInfo.put("poros_q", poros_q);
                    stateInfo.put("cutans_ty", cutans_ty);
                    stateInfo.put("cutans_th", cutans_th);
                    stateInfo.put("cutans_q", cutans_q);
                    stateInfo.put("nodules_s", nodules_s);
                    stateInfo.put("nodules_q", nodules_q);
                    stateInfo.put("roots_s", roots_s);
                    stateInfo.put("roots_q", roots_q);
                    stateInfo.put("roots_effervescence", roots_effervescence);
                    stateInfo.put("of_size", of_size);
                    stateInfo.put("of_abundance", of_abundance);
                    stateInfo.put("of_nature", of_nature);
                    stateInfo.put("of_samplebagno", of_samplebagno);
                    stateInfo.put("of_additionalnotes", of_additionalnotes);

                    //  -----------------------FOR PHYSICAL PARAMETERS---------------
                    stateInfo.put("sand", sand);
                    stateInfo.put("silt", silt);
                    stateInfo.put("clay", clay);
                    stateInfo.put("USDA_textural_class", USDA_textural_class);
                    stateInfo.put("bulk_density", bulk_density);
                    stateInfo.put("wr_33kPa", wr_33kPa);
                    stateInfo.put("wr_1500kPa", wr_1500kPa);
                    stateInfo.put("AWC", AWC);
                    stateInfo.put("PAWC", PAWC);
                    stateInfo.put("gravimetric_moisture", gravimetric_moisture);

                    //  -----------------------FOR CHEMICAL PARAMETERS---------------
                    stateInfo.put("cp_pH", cp_pH);
                    stateInfo.put("cp_EC", cp_EC);
                    stateInfo.put("OC", OC);
                    stateInfo.put("CaCo", CaCo);
                    stateInfo.put("Ca", Ca);
                    stateInfo.put("Mg", Mg);
                    stateInfo.put("Na", Na);
                    stateInfo.put("K", K);
                    stateInfo.put("totalBase", totalBase);
                    stateInfo.put("CEC", CEC);
                    stateInfo.put("BS", BS);
                    stateInfo.put("ESP", ESP);

                    //  -----------------------FOR SOIL FERTILITY PARAMS---------------
                    stateInfo.put("organicCarbon", organicCarbon);
                    stateInfo.put("MaN_nitrogen", MaN_nitrogen);
                    stateInfo.put("MaN_phosphorus", MaN_phosphorus);
                    stateInfo.put("MaN_potassium", MaN_potassium);
                    stateInfo.put("MiN_sulphur", MiN_sulphur);
                    stateInfo.put("MiN_zinc", MiN_zinc);
                    stateInfo.put("MiN_copper", MiN_copper);
                    stateInfo.put("MiN_iron", MiN_iron);
                    stateInfo.put("MiN_manganese", MiN_manganese);

                    //  -----------------------FOR IMAGES---------------
                    //---------east img---------
                    String url_east_imgs = "http://14.139.123.73:9090/web/NBSS/php/images/" + east_image + ".jpg";
                    url_east_img = url_east_imgs;
                    Picasso.get().load(url_east_img)
                            .resize(1100, 700)
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(report_east_imgView);
                    //----------west img--------------
                    String url_west_imgs = "http://14.139.123.73:9090/web/NBSS/php/images/" + west_image + ".jpg";
                    url_west_img = url_west_imgs;
                    Picasso.get().load(url_west_img)
                            .resize(1100, 700)
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(report_west_imgView);
                    //-----------north img-----------
                    String url_north_imgs = "http://14.139.123.73:9090/web/NBSS/php/images/" + north_image + ".jpg";
                    url_north_img = url_north_imgs;
                    Picasso.get().load(url_north_img)
                            .resize(1100, 700)
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(report_north_imgView);
                    //-----------south img---------
                    String url_south_imgs = "http://14.139.123.73:9090/web/NBSS/php/images/" + south_image + ".jpg";
                    url_south_img = url_south_imgs;
                    Picasso.get().load(url_south_img)
                            .resize(1100, 700)
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(report_south_imgView);
                    id_list.add(stateInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //                    -----------------------FOR PROJECT REGISTRATION---------------
        ListAdapter adapter2 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.projectregistration_records,
                new String[]{"projNames", "projPeriod", "projDuration", "projFundSrc", "date", "state"},
                new int[]{R.id.tvProjNames, R.id.tvprojPeriod, R.id.tvprojDuration, R.id.tvprojFundSrc, R.id.tvregisteredDate});
        projReg_details_list.setAdapter(adapter2);

        //                    -----------------------FOR LOCATION---------------
        ListAdapter adapter3 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.locationdetails_records,
                new String[]{"projectProfileID", "surveyorname", "state", "district", "tehsil", "block",
                        "villagename", "toposheet250k", "toposheet50k", "date", "time", "latitude",
                        "longitude", "elevation"},
                new int[]{R.id.tv_loc_projectProfileID, R.id.tv_loc_surveyorname, R.id.tv_loc_state, R.id.tv_loc_district,
                        R.id.tv_loc_tehsil, R.id.tv_loc_block, R.id.tv_loc_villagename, R.id.tv_loc_toposheet250k,
                        R.id.tv_loc_toposheet50k, R.id.tv_loc_date, R.id.tv_loc_time, R.id.tv_loc_latitude,
                        R.id.tv_loc_longitude, R.id.tv_loc_elevation});
        location_details_list.setAdapter(adapter3);

        //                    -----------------------FOR SOIL SITE PARAMETERS---------------
        ListAdapter adapter4 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.soilsiteparams_records,
                new String[]{"physiographicCategory", "subPhysiographicUnit", "geology", "parentMaterial", "climate", "rainfall",
                        "topographyLandformType", "slopeGradiant", "slopeLength", "erosion", "runoff", "drainage", "groundWaterDepth",
                        "flooding", "saltAlkali", "pH", "Ec", "stoneSize", "stoiness", "rockOutcrops", "naturalVegetation"},
                new int[]{R.id.tv_ssp_physiographicCategory, R.id.tv_ssp_subPhysiographicUnit, R.id.tv_ssp_geology, R.id.tv_ssp_parentMaterial,
                        R.id.tv_ssp_climate, R.id.tv_ssp_rainfall, R.id.tv_ssp_topographyLandformType, R.id.tv_ssp_slopeGradiant,
                        R.id.tv_ssp_slopeLength, R.id.tv_ssp_erosion, R.id.tv_ssp_runoff, R.id.tv_ssp_drainage, R.id.tv_ssp_groundWaterDepth,
                        R.id.tv_ssp_flooding, R.id.tv_ssp_saltAlkali, R.id.tv_ssp_pH, R.id.tv_ssp_Ec, R.id.tv_ssp_stoneSize, R.id.tv_ssp_stoiness,
                        R.id.tv_ssp_rockOutcrops, R.id.tv_ssp_naturalVegetation});
        soilSiteParams_details_list.setAdapter(adapter4);

        //                    -----------------------FOR PRESENT LAND USE---------------
        ListAdapter adapter5 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.presentlanduse_records,
                new String[]{"forest", "cultivated", "terraces", "pastureLand", "degradedCulturable", "degradedUnCulturable", "phaseSurface",
                        "phaseSubstratum", "landCapabilityClass", "landCapabilitySubClass", "landIrrigabilityClass", "landIrrigabilitySubClass",
                        "remarks", "crop", "yield", "managementPractice", "croppingSystem"},
                new int[]{R.id.tv_plu_forest, R.id.tv_plu_cultivated, R.id.tv_plu_terraces, R.id.tv_plu_pastureLand, R.id.tv_plu_degradedCulturable,
                        R.id.tv_plu_degradedUnCulturable, R.id.tv_plu_phaseSurface, R.id.tv_plu_phaseSubstratum, R.id.tv_plu_landCapabilityClass,
                        R.id.tv_plu_landCapabilitySubClass, R.id.tv_plu_landIrrigabilityClass, R.id.tv_plu_landIrrigabilitySubClass, R.id.tv_plu_remarks,
                        R.id.tv_plu_crop, R.id.tv_plu_yield, R.id.tv_plu_managementPractice, R.id.tv_plu_croppingSystem});
        presentLandUse_details_list.setAdapter(adapter5);

        //                    -----------------------FOR MORPHOLOGICAL PARAMETERS---------------
        ListAdapter adapter6 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.morphologicalparams_records,
                new String[]{"horizon", "depth", "b_distinctness", "b_topography", "b_diagnostic", "b_matrixColour", "mc_abundance",
                        "mc_size", "mc_contrast", "mc_texture", "cf_size", "cf_vol", "str_size", "str_grade", "str_type", "con_d", "con_m",
                        "con_w", "poros_s", "poros_q", "cutans_ty", "cutans_th", "cutans_q", "nodules_s", "nodules_q", "roots_s", "roots_q",
                        "roots_effervescence", "of_size", "of_abundance", "of_nature", "of_samplebagno", "of_additionalnotes"},
                new int[]{R.id.tv_mp_horizon, R.id.tv_mp_depth, R.id.tv_mp_b_distinctness, R.id.tv_mp_b_topography, R.id.tv_mp_b_diagnostic,
                        R.id.tv_mp_b_matrixColour, R.id.tv_mp_mc_abundance, R.id.tv_mp_mc_size, R.id.tv_mp_mc_contrast, R.id.tv_mp_mc_texture,
                        R.id.tv_mp_cf_size, R.id.tv_mp_cf_vol, R.id.tv_mp_str_size, R.id.tv_mp_str_grade, R.id.tv_mp_str_type, R.id.tv_mp_con_d,
                        R.id.tv_mp_con_m, R.id.tv_mp_con_w, R.id.tv_mp_poros_s, R.id.tv_mp_poros_q, R.id.tv_mp_cutans_ty, R.id.tv_mp_cutans_th,
                        R.id.tv_mp_cutans_q, R.id.tv_mp_nodules_s, R.id.tv_mp_nodules_q, R.id.tv_mp_roots_s, R.id.tv_mp_roots_q, R.id.tv_mp_roots_effervescence,
                        R.id.tv_mp_of_size, R.id.tv_mp_of_abundance, R.id.tv_mp_of_nature, R.id.tv_mp_of_samplebagno, R.id.tv_mp_of_additionalnotes});
        morphological_details_list.setAdapter(adapter6);

        //                    -----------------------FOR PHYSICAL PARAMETERS---------------
        ListAdapter adapter7 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.physicalparams_records,
                new String[]{"horizon", "depth", "sand", "silt", "clay", "USDA_textural_class",
                        "bulk_density", "wr_33kPa", "wr_1500kPa", "AWC", "PAWC", "gravimetric_moisture"},
                new int[]{R.id.tv_pp_horizon_dummy, R.id.tv_pp_depth_dummy, R.id.tv_pp_sand, R.id.tv_pp_silt, R.id.tv_pp_clay,
                        R.id.tv_pp_USDATexturalClass, R.id.tv_pp_bulkDensity, R.id.tv_pp_33kPa, R.id.tv_pp_1500kPa, R.id.tv_pp_AWC,
                        R.id.tv_pp_PAWC, R.id.tv_pp_gravimetricMoisture});
        physical_details_list.setAdapter(adapter7);

        //                    -----------------------FOR CHEMICAL PARAMETERS---------------
        ListAdapter adapter8 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.chemical_records,
                new String[]{"horizon", "depth", "cp_pH", "cp_EC", "OC", "CaCo",
                        "Ca", "Mg", "Na", "K", "totalBase", "CEC", "BS", "ESP"},
                new int[]{R.id.tv_cp_horizon, R.id.tv_cp_depth, R.id.tv_cp_pH, R.id.tv_cp_EC, R.id.tv_cp_OC,
                        R.id.tv_cp_CaCo, R.id.tv_cp_Ca, R.id.tv_cp_Mg, R.id.tv_cp_Na, R.id.tv_cp_K,
                        R.id.tv_cp_TotalBase, R.id.tv_cp_CEC, R.id.tv_cp_BS, R.id.tv_cp_ESP});
        chemical_details_list.setAdapter(adapter8);

        //                    -----------------------FOR SOIL FERTILITY PARAMETERS---------------
        ListAdapter adapter9 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.soilfertilityparams_records,
                new String[]{"horizon", "depth", "organicCarbon", "MaN_nitrogen", "MaN_phosphorus", "MaN_potassium",
                        "MiN_sulphur", "MiN_zinc", "MiN_copper", "MiN_iron", "MiN_manganese"},
                new int[]{R.id.tv_sfp_horizon, R.id.tv_sfp_depth, R.id.tv_sfp_organicCarbon, R.id.tv_sfp_nitrogen, R.id.tv_sfp_phosphorus,
                        R.id.tv_sfp_potassium, R.id.tv_sfp_sulphur, R.id.tv_sfp_zinc, R.id.tv_sfp_copper, R.id.tv_sfp_iron,
                        R.id.tv_sfp_manganese});
        soilfertility_details_list.setAdapter(adapter9);

        //                    -----------------------FOR IMAGES---------------
        ListAdapter adapter10 = new SimpleAdapter(admin_DataReport.this, id_list, R.layout.projectprofileid_img_records,
                new String[]{"projectProfileID"},
                new int[]{R.id.tvProjProfileID});
        projProfileimg_details_list.setAdapter(adapter10);
    }

    public void eastImgClick(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_img_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ImageView south_dialog_img = (ImageView) dialog.findViewById(R.id.img_dialog_img);
        Picasso.get().load(url_east_img).into(south_dialog_img);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        Toast.makeText(this, "EAST image selected", Toast.LENGTH_LONG).show();
    }

    public void westImgClick(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_img_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ImageView south_dialog_img = (ImageView) dialog.findViewById(R.id.img_dialog_img);
        Picasso.get().load(url_west_img).into(south_dialog_img);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        Toast.makeText(this, "WEST image selected", Toast.LENGTH_LONG).show();
    }

    public void northImgClick(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_img_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ImageView south_dialog_img = (ImageView) dialog.findViewById(R.id.img_dialog_img);
        Picasso.get().load(url_north_img).into(south_dialog_img);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        Toast.makeText(this, "NORTH image selected", Toast.LENGTH_LONG).show();
    }

    public void southImgClick(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("helll");
        dialog.setContentView(R.layout.custom_img_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ImageView south_dialog_img = (ImageView) dialog.findViewById(R.id.img_dialog_img);
        Picasso.get().load(url_south_img).into(south_dialog_img);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        Toast.makeText(this, "SOUTH image selected", Toast.LENGTH_LONG).show();
    }

    public void adminApproveBtn(View view) {
        Toast.makeText(this, "APPROVED", Toast.LENGTH_LONG).show();
        // below is for progress dialog box
        //Initialinzing the progress Dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // dismiss the dialog
        // and exit the exit
        Log.d("selectedProjectID", "selectedProjectID-------" + selectedProjectID);

        // calling method to add data
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                adminStatusApproved_url + "?TYPE=adminApproveRecords&projID=" +
                        selectedProjectID + "&approved=" + approved,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //below code is for live server
                        try {
                            if (TextUtils.equals(response, "1")) {
                                Toast.makeText(admin_DataReport.this, "Response saved Successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("res", "response------" + jsonObject);
                            } else {
                                Toast.makeText(admin_DataReport.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data = new HashMap<>();
//                data.put("projID", selectedProjectID);
//                data.put("approved", approved);
//                return data;
//            }
//        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    public void adminNotApproveBtn(View view) {
        Toast.makeText(this, "NOT APPROVED", Toast.LENGTH_LONG).show();
        // below is for progress dialog box
        //Initialinzing the progress Dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // dismiss the dialog
        // and exit the exit

        // calling method to add data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, adminStatusNotApproved_url +
                "?TYPE=adminNotApproveRecords&projID=" + selectedProjectID + "&notApproved=" + notApproved
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //below code is for live server
                try {
                    if (TextUtils.equals(response, "1")) {
                        Toast.makeText(admin_DataReport.this, "Response saved Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(admin_DataReport.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) ;
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data = new HashMap<>();
//                data.put("projID", selectedProjectID);
//                data.put("notApproved", notApproved);
//                return data;
//            }
//        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}