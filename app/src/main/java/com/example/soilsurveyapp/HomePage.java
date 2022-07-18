package com.example.soilsurveyapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
 import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

import java.util.Locale;

public class HomePage extends AppCompatActivity {

    public static final String KEY_NAME = "name";
    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "mypref";
    CardView porjRegCard, dataCollectionCard, soilDataReport, aboutApp, logoutCard, soilDataUpdate;
    TextView username, adminBtn;
    //--------------SHARED PREFERENCES----------------
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    //----------------------------------POPUP EXIT APP Msg-----------------------------------------------
    @Override
    public void onBackPressed() {
        customExitDialog();
    }

    private void customExitDialog() {
        // creating custom dialog
        final Dialog dialog = new Dialog(HomePage.this);

        // setting content view to dialog
        dialog.setContentView(R.layout.custom_exit_dialog);

        // getting reference of TextView
        TextView dialogButtonYes = (TextView) dialog.findViewById(R.id.textViewYes);
        TextView dialogButtonNo = (TextView) dialog.findViewById(R.id.textViewNo);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // click listener for No
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss the dialog
                dialog.dismiss();

            }
        });

        // click listener for Yes
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog
                // and exit the exit
                dialog.dismiss();
                finishAffinity();
            }
        });
        // show the exit dialog
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //below is the shared preference displaying the login user name
        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        username = findViewById(R.id.userName_dashboard);
        username.setText("Welcome, " + sharedPreferences.getString(KEY_NAME, ""));

        //------------------HIDING THE ACTION BAR--------------------
//        getSupportActionBar().setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF018786"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        //--------------------------PROJECT REGISTRATION CARD--------------------
        porjRegCard = findViewById(R.id.proj_reg);
        porjRegCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(HomePage.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Intent intent = new Intent(HomePage.this, ProjectRegistrationForm.class);
                startActivity(intent);
            }
        });


        //---------------------------DATA COLLECTION CARD---------------------------------
        dataCollectionCard = findViewById(R.id.data_collection);
        dataCollectionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(HomePage.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Intent intent = new Intent(HomePage.this, DataCollection.class);
                startActivity(intent);
            }
        });

        //---------------------------SOIL DATA REPORT CARD---------------------------------
        soilDataReport = findViewById(R.id.soil_data_report);
        soilDataReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(HomePage.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Intent intent = new Intent(HomePage.this, SoilDataReport.class);
                startActivity(intent);
            }
        });

        //---------------------------SOIL DATA UPDATE CARD---------------------------------
        soilDataUpdate = findViewById(R.id.soil_data_update);
        soilDataUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(HomePage.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Intent intent = new Intent(HomePage.this, UpdateRecords.class);
                startActivity(intent);
            }
        });

        //----------------------------ABOUT APP CARD--------------------------------------
        aboutApp = findViewById(R.id.about_app);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(HomePage.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                startActivity(new Intent(HomePage.this, AboutApp.class));
            }
        });

        //-------------------------LOGOUT CARD---------------------------------------------
        logoutCard = findViewById(R.id.logout);
        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(HomePage.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                setLoginState(true);
                Toast.makeText(HomePage.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePage.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            private void setLoginState(boolean status) {
                SharedPreferences sp = getSharedPreferences("LoginState", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean("setLoggingOut", status);
                ed.commit();
            }
        });

        //-----------Button for admin textview on homepage-----------
        adminBtn = findViewById(R.id.adminBtn_dashboard);
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below is for progress dialog box
                //Initialinzing the progress Dialog
                progressDialog = new ProgressDialog(HomePage.this);
                //show Dialog
                progressDialog.show();
                //set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Intent intent = new Intent(HomePage.this, adminLogin.class);
                startActivity(intent);
            }
        });
    }
}

