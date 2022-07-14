package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

public class UpdateRecords extends AppCompatActivity {
    //url_id showing all project id list from locationdetailsingle table
    String url_projectID_list = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    //url_profile_id for fetching all project profile id list from locationdetailsingle table
    String url_profile_id = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    //url_projectID for fetching particular project id details from database!!
    String url_projectID = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    //url_projID_delete is for deleting particular project id details from database!!
    String url_projID_delete = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";

    //-----------for search by project ID and Date----------------
    ArrayList<String> update_projectIDList = new ArrayList<>();
    //-----------for search by project profile ID and Date----------------
    ArrayList<String> update_projectprofileIDList = new ArrayList<>();
    RequestQueue update_requestQueue, update_requestQueue2;
    ProgressDialog progressDialog;
    //declaring variable for storing selected data
    private String update_selectedProjectID, update_selectedProjectProfileID;
    // defining spinner
    private Spinner update_projectIDSpinner, update_projectProfileIDSpinner;
    //defining and declaring array adapter
    private ArrayAdapter<CharSequence> update_projectIDAdapter, update_projectprofileIDAdapter;

    //--------------when pressing hardware back button----------------
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_records);

        //-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
//            this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {
        }

        update_projectIDSpinner = (Spinner) findViewById(R.id.spin_update_select_projID);
        update_projectProfileIDSpinner = (Spinner) findViewById(R.id.spin_update_select_projprofileID);

        update_requestQueue = Volley.newRequestQueue(this);
        update_requestQueue2 = Volley.newRequestQueue(this);

        //-----------below array json is for showing list of all project id from project locationdetailsingle table------------
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_projectID_list +
                "?TYPE=report_project_ID_list", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("locationdetailsingle");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectID = jsonObject.optString("projID");
                        update_projectIDList.add(projectID);
                        update_projectIDAdapter = new ArrayAdapter(UpdateRecords.this, android.R.layout.simple_spinner_item, update_projectIDList);
                        update_projectIDAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
                        update_projectIDSpinner.setAdapter(update_projectIDAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //                Toast.makeText(UpdateRecords.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
        update_requestQueue.add(jsonObjectRequest);

        //--------below code is for fetching all the details particular project id on spinner
        update_projectIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                update_selectedProjectID = update_projectIDSpinner.getSelectedItem().toString();
                if (adapterView.getItemAtPosition(i).equals(update_selectedProjectID)) {
                    update_projectprofileIDList.clear();
//                    String update_selectedProjectID = adapterView.getSelectedItem().toString();

//                    Toast.makeText(SoilDataReport.this, "starting", Toast.LENGTH_SHORT).show();
                    //-----------below array json is for project profile id from location detail single table------------
                    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST,
                            url_profile_id + "?TYPE=project_profile_ID_list&projID=" + update_selectedProjectID,
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                            Toast.makeText(SoilDataReport.this, "testingnnnnn", Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray jsonArray = response.getJSONArray("locationdetailsingle");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String projectprofileID = jsonObject.optString("projectProfileID");
                                    update_projectprofileIDList.add(projectprofileID);
                                    update_projectprofileIDAdapter = new ArrayAdapter(UpdateRecords.this, android.R.layout.simple_spinner_item, update_projectprofileIDList);
                                    update_projectprofileIDAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
                                    update_projectProfileIDSpinner.setAdapter(update_projectprofileIDAdapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            progressDialog.dismiss();
                            Toast.makeText(UpdateRecords.this, "Please check internet connection!!", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(UpdateRecords.this, "Please check internet connection!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    update_requestQueue2.add(jsonObjectRequest2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //-------below code is for showing list of project profile id on their spinner
        update_projectProfileIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                update_selectedProjectProfileID = update_projectProfileIDSpinner.getSelectedItem().toString();
//                Toast.makeText(SoilDataReport.this, "testingnnnnn", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

    //--------BUTTON for move to show custom dialog box, edit and delete records-----------
    public void NextforUpdate(View view) {
        //--------function for custom dialog box for delete and edit---------------
        customExitDialog();
    }

    //--------function for custom dialog box for delete and edit---------------
    private void customExitDialog() {
        // creating custom dialog
        final Dialog dialog = new Dialog(UpdateRecords.this);

        // setting content view to dialog
        dialog.setContentView(R.layout.custom_edit_delete_dialog);
        dialog.setTitle("PROJECT ID: " + update_selectedProjectID.toString().toUpperCase(Locale.ROOT));

        // getting reference of TextView
        TextView txtUpdate = (TextView) dialog.findViewById(R.id.txtUpdate);
        TextView textViewDelete = (TextView) dialog.findViewById(R.id.textViewDelete);
        TextView textViewEdit = (TextView) dialog.findViewById(R.id.textViewEdit);
        txtUpdate.setText("PROJECT ID: " + update_selectedProjectID.toString().toUpperCase(Locale.ROOT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // ------------click listener for DELETE RECORDS-----------------------
        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(UpdateRecords.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                // dismiss the dialog
                // and exit the exit
                //dismiss the dialog
                StringRequest deleteStringRequest = new StringRequest(Request.Method.POST, url_projID_delete +
                        "?TYPE=deleteRecordID&projID=" + update_selectedProjectID,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("Data Deleted")) {
                                    Toast.makeText(UpdateRecords.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), UpdateRecords.class));
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(UpdateRecords.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), UpdateRecords.class));
                                    dialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateRecords.this, "Something goes wrong...", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(UpdateRecords.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(deleteStringRequest);
            }
        });

        // ------------click listener for EDIT RECORDS-----------------------
        textViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(UpdateRecords.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                // dismiss the dialog
                // and exit the exit
                Intent intent = new Intent(UpdateRecords.this, UpdateLocationDetails.class);
                intent.putExtra("update_selectedProjectID", update_selectedProjectID);
                intent.putExtra("update_selectedProjectProfileID", update_selectedProjectProfileID);
                startActivity(intent);
            }
        });
        // show the exit dialog
        dialog.show();
    }
}