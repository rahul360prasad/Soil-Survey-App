package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DataCollection extends AppCompatActivity {

    // creating variables for our edit text
    private EditText etProjID;
    private String projID;
    // url to post our data
    String url = "http://10.0.0.145/login/dataCollections.php";
//    private Button nextBtn1;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataCollection.this, HomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        //---HIDING THE ACTION BAR
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        projID = "";
        etProjID = findViewById(R.id.dc_projID);
    }

    public void Next(View view) {
        // creating a new variable for our request queue
//        RequestQueue queue = Volley.newRequestQueue(DataCollection.this);

        projID = etProjID.getText().toString().trim();

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        if (!projID.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Intent intent = new Intent(DataCollection.this, LocationDetails.class);
                        startActivity(intent);
                    } else if (response.equals("failure")) {
                        Toast.makeText(DataCollection.this, "Invalid Project ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DataCollection.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("projID", projID);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}
