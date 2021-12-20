package com.example.soilsurveyapp;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    private long pressedTime;
    CardView porjRegCard, dataCollectionCard, soilDataReport, aboutApp, logoutCard ;
    SharedPreferences sharedPreferences;

    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "mypref";

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
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF018786"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        //--------------------------PROJECT REGISTRATION CARD--------------------
        porjRegCard= findViewById(R.id.proj_reg);
        porjRegCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Card is Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePage.this, ProjectRegistrationForm.class);
                startActivity(intent);
             }
        });


        //---------------------------DATA COLLECTION CARD---------------------------------
        dataCollectionCard= findViewById(R.id.data_collection);
        dataCollectionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent= new Intent(HomePage.this, DataCollection.class);
                startActivity(intent);
            }
        });

        //---------------------------SOIL DATA REPORT CARD---------------------------------
        soilDataReport= findViewById(R.id.soil_data_report);
        soilDataReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent= new Intent(HomePage.this, SoilDataReport.class);
                startActivity(intent);
            }
        });

        //----------------------------ABOUT APP CARD--------------------------------------
        aboutApp=findViewById(R.id.about_app);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, AboutApp.class));
            }
        });

        //-------------------------LOGOUT CARD---------------------------------------------
        logoutCard= findViewById(R.id.logout);
        sharedPreferences =getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);
        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(HomePage.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

//        public void customExitDialog() {
//
//        }
    }

//    public class MainActivity extends AppCompatActivity {
//
//
//    }

//    private class UploadTask {
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//        }

//        public void customExitDialog() {
//            // creating custom dialog
//            final Dialog dialog = new Dialog(HomePage.this);
//
//            // setting content view to dialog
//            dialog.setContentView(R.layout.custom_exit_dialog);
//
//            // getting reference of TextView
//            TextView dialogButtonYes = (TextView) dialog.findViewById(R.id.textViewYes);
//            TextView dialogButtonNo = (TextView) dialog.findViewById(R.id.textViewNo);
//
//            // click listener for No
//            dialogButtonNo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //dismiss the dialog
//                    dialog.dismiss();
//
//                }
//            });
//
//            // click listener for Yes
//            dialogButtonYes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // dismiss the dialog
//                    // and exit the exit
//                    dialog.dismiss();
//                    finish();
//
//                }
//            });
//
//            // show the exit dialog
//            dialog.show();
//        }

//        @Override
//        public void onBackPressed() {
//            // calling the function
//            customExitDialog();
//        }
//    }
}

