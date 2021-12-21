package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProjectRegistrationForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] dropdownItems = new String[]{"Choose options...", "Institute Funded", "External Funded", "International Funded"};

    // creating variables for our edit text
    private EditText etProjName, etProjPeriod, etProjDuration, etProjID, etProjPrinInvestName;
    private Spinner dropDown;
    // creating variable for button
    private Button submitBtn;

    // creating a strings for storing our values from edittext fields.
    private String projName, projPeriod, projDuration, projID, projPrinInvestName, projFundSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_registration_form);

        //---HIDING THE ACTION BAR
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //below code for dropdown box
        dropDown = findViewById(R.id.spFundingSource);
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

        etProjName = findViewById(R.id.et_projName);
        etProjPeriod = findViewById(R.id.et_projPeriod);
        etProjDuration = findViewById(R.id.et_projDuration);
        etProjID = findViewById(R.id.et_projID);
        etProjPrinInvestName = findViewById(R.id.et_priInvestName);
         submitBtn = findViewById(R.id.proj_reg_submit);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                projName = etProjName.getText().toString();
                projPeriod = etProjPeriod.getText().toString();
                projDuration = etProjDuration.getText().toString();
                projID = etProjID.getText().toString();
                projPrinInvestName = etProjPrinInvestName.getText().toString();
                projFundSrc = dropDown.getSelectedItem().toString();

                 //----------validating the text fields if empty or not.-------------------
                if (TextUtils.isEmpty(projName)) {
                    etProjName.setError("Please enter Project Details");
                } else if (TextUtils.isEmpty(projPeriod)) {
                    etProjPeriod.setError("Please enter Project Details");
                } else if (TextUtils.isEmpty(projDuration)) {
                    etProjDuration.setError("Please enter Project Details");
                } else if (TextUtils.isEmpty(projID)) {
                    etProjID.setError("Please enter Project Details");
                } else if (TextUtils.isEmpty(projPrinInvestName)) {
                     etProjPrinInvestName.setError("Please enter Project Details");
                 } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToDatabase(projName, projPeriod, projDuration, projID, projPrinInvestName, projFundSrc);
                }

                //-----------validating spinner feild---------------------------------
                if(!projFundSrc.equals("Choose options...")){
                    dropDown.getSelectedItem().toString();
                }
                else{
                    Toast.makeText(ProjectRegistrationForm.this,"Please Select Funding Source !!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    private void addDataToDatabase(String projName, String projPeriod, String projDuration, String projID, String projPrinInvestName, String projFundSrc) {

        // url to post the data
        String url = "http://10.0.0.145/login/projRegistration.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(ProjectRegistrationForm.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(ProjectRegistrationForm.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // and setting data to edit text as empty
                etProjName.setText("");
                etProjPeriod.setText("");
                etProjDuration.setText("");
                etProjID.setText("");
                etProjPrinInvestName.setText("");
                dropDown.setPrompt("");
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(ProjectRegistrationForm.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("projName", projName);
                params.put("projPeriod", projPeriod);
                params.put("projDuration", projDuration);
                params.put("projID", projID);
                params.put("projPrinInvestName", projPrinInvestName);
                params.put("projFundSrc", projFundSrc);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    //--------------performing action onItemSelected and onNothingSelected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    //Toast.makeText(getApplicationContext(), dropdownItems[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}