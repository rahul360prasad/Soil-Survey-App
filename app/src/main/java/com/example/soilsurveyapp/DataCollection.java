package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataCollection extends AppCompatActivity {
    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "dataCollection";
    private static final String KEY_PROJECT_ID = "id";
    ProgressDialog progressDialog;
    //-----------for search by project ID and Date----------------
    ArrayList<String> projectIDList = new ArrayList<>();
    RequestQueue requestQueue;
    // check the project id is available or not in database
    String url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    // showing the list of all project id
    String url_id = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    SharedPreferences sharedPreferences;
//    String url = "http://10.0.0.145/login/mysql.php";
    // creating variables for our edit text
    private Spinner projectIDSpinner;
    private String projID;
    private String selectedProjectID;
    //defining and declaring array adapter
    private ArrayAdapter<CharSequence> projectIDAdapter;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataCollection.this, HomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        requestQueue = Volley.newRequestQueue(this);

        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);

        //---HIDING THE ACTION BAR
        try {
            //this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {

        }

        projectIDSpinner = (Spinner) findViewById(R.id.spin_dc_projID);

        //----------------------------SEARCH BY PROJECT ID--------------------------
        //--------below code is for showing list of project id in spinner
        projectIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProjectID = projectIDSpinner.getSelectedItem().toString();
                Log.d("respppp", selectedProjectID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_id + "?TYPE=project_ID_list", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d("respppp", "response");
                try {
                    JSONArray jsonArray = response.getJSONArray("projectregistrationtbl");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectID = jsonObject.optString("projID");
                        projectIDList.add(projectID);
                        projectIDAdapter = new ArrayAdapter(DataCollection.this, android.R.layout.simple_spinner_item, projectIDList);
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

    public void Next(View view) {

        //Initialinzing the progress Dialog
        progressDialog = new ProgressDialog(DataCollection.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(DataCollection.this);

        //         on below line we are calling a string
        //         request method to post the data to our API
        //         in this we are calling a post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "?TYPE=PROJECTID_CHK&projID=" + selectedProjectID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (TextUtils.equals(response, "0")) {
                        Toast.makeText(DataCollection.this, "Data not exist!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //below code is working and it is for validation of data collection page
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString(KEY_PROJECT_ID, selectedProjectID);
//                            editor.apply();
//                            progressDialog.dismiss();
//                            Toast.makeText(DataCollection.this, "Welcome", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(DataCollection.this, LocationDetails.class);
//                            startActivity(intent);
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_PROJECT_ID, selectedProjectID);
                        editor.apply();
                        Intent intent = new Intent(DataCollection.this, LocationDetails.class);
                        startActivity(intent);
                        Toast.makeText(DataCollection.this, "Welcome", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //below code is working and it is for validation of data collection page
//                            progressDialog.dismiss();
//                            Toast.makeText(DataCollection.this, "Project ID already exist!! \nPlease register project first...", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(DataCollection.this, "Failed to go!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DataCollection.this, "Please check internet connection!!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
