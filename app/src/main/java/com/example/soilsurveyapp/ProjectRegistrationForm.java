package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProjectRegistrationForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String KEY_ID = "id";
    // url to post the data
    //    private static final String url = "http://10.0.0.145/login/projRegForm.php";
    private static final String url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "proReg";
    private static final String KEY_PROJECT_ID = "id";
    private static final String SHARED_PRE_NAME2 = "mypref";
    String[] dropdownItems = new String[]{"Choose options...", "Institute Funded", "External Funded", "International Funded"};
    ProgressDialog progressDialog;
    //---------SHARED PREFERENCES-------------------
    SharedPreferences sharedPreferences;
    //taking user id from login/register
    SharedPreferences sharedPreferencesId;
    // creating variables for our edit text
    private EditText etProjName, etProjPeriod, etProjDuration, etProjID, etProjPrinInvestName;
    private Spinner dropDown;
    // creating a strings for storing our values from edittext fields.
    private String projName, projPeriod, projDuration, projID, projPrinInvestName, projFundSrc;
    private int userid;
    // creating variable for button
    private Button submitBtn;

    //-----------hardware back button----------------
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProjectRegistrationForm.this, HomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_registration_form);

        sharedPreferencesId = getSharedPreferences(SHARED_PRE_NAME2, Context.MODE_PRIVATE);
        userid = Integer.parseInt(sharedPreferencesId.getString(KEY_ID, ""));
        //Log.d("userid", userid);

        //---HIDING THE ACTION BAR
        try {
//          this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {
        }

        etProjName = findViewById(R.id.et_projName);
        etProjPeriod = findViewById(R.id.et_projPeriod);
        etProjDuration = findViewById(R.id.et_projDuration);
        etProjID = findViewById(R.id.et_projID);
        etProjPrinInvestName = findViewById(R.id.et_priInvestName);
        submitBtn = findViewById(R.id.proj_reg_submit);
        //below code for dropdown box
        dropDown = findViewById(R.id.spFundingSource);

        projName = projPeriod = projDuration = projID = projPrinInvestName = projFundSrc = "";

        dropDown.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        //-----------Creating the ArrayAdapter instance having the name list
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dropdownItems) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(adapter);

        //---------shared preference----------------
        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);
        //when open the activity then first check "shared preference" data available or not
        String projID = sharedPreferences.getString(KEY_PROJECT_ID, null);
        if (projID == null) {
            Toast.makeText(ProjectRegistrationForm.this, "Enter project id!!", Toast.LENGTH_SHORT).show();
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

    public void SubmitBtn(View view) {
        // below is for progress dialog box
        //Initialinzing the progress Dialog
        progressDialog = new ProgressDialog(ProjectRegistrationForm.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //--------------------SHAREDPREFERENCE-------------------
        //when clicking register btn put data on shared preference
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PROJECT_ID, etProjID.getText().toString());
        editor.apply();

        projName = etProjName.getText().toString().trim();
        projPeriod = etProjPeriod.getText().toString().trim();
        projDuration = etProjDuration.getText().toString().trim();
        projID = etProjID.getText().toString().trim();
        projPrinInvestName = etProjPrinInvestName.getText().toString().trim();
        projFundSrc = dropDown.getSelectedItem().toString().trim();

        //----------validating the text fields if empty or not.-------------------
        if (TextUtils.isEmpty(projName)) {
            progressDialog.dismiss();
            etProjName.setError("Please enter Project name");
            return;
        } else if (TextUtils.isEmpty(projPeriod)) {
            progressDialog.dismiss();
            etProjPeriod.setError("Please enter Project period");
            return;
        } else if (TextUtils.isEmpty(projDuration)) {
            progressDialog.dismiss();
            etProjDuration.setError("Please enter Project duration");
            return;
        } else if (TextUtils.isEmpty(projID)) {
            progressDialog.dismiss();
            etProjID.setError("Please enter Project id");
            return;
        } else if (TextUtils.isEmpty(projPrinInvestName)) {
            progressDialog.dismiss();
            etProjPrinInvestName.setError("Please enter Project principle name");
            return;
        } else if (projFundSrc.equals("Choose options...")) {
            progressDialog.dismiss();
            Toast.makeText(ProjectRegistrationForm.this, "Please Select Funding Source !!", Toast.LENGTH_LONG).show();
            return;
        } else {

            // calling method to add data to Firebase Firestore.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "?TYPE=PROJECT_REG&userid=" + userid +
                    "&projName=" + projName +
                    "&projPeriod=" + projPeriod +
                    "&projDuration=" + projDuration +
                    "&projID=" + projID +
                    "&projPrinInvestName=" + projPrinInvestName +
                    "&projFundSrc=" + projFundSrc + "", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //below code is for live server
                    try {
                        if (TextUtils.equals(response, "1")) {
                            progressDialog.dismiss();
                            Toast.makeText(ProjectRegistrationForm.this, "Data stored Successfully", Toast.LENGTH_SHORT).show();
                            // on below line we are displaying a success toast message.
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_PROJECT_ID, projID);
                            editor.apply();
                            finish();
                            Intent intent = new Intent(ProjectRegistrationForm.this, HomePage.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(ProjectRegistrationForm.this, "Failed to store!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }


    //--------------performing action onItemSelected and onNothingSelected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}