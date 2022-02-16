package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SearchByState extends AppCompatActivity {
    private TextView lbl_reports;

    SharedPreferences sharedPreferencesHorizon, sharedPreferencesState;
    private static final String SHARED_PRE_NAME = "soilDataReport";
    private static final String KEY_STATE = "state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_by_state);
//        lbl_reports = findViewById(R.id.lbl_report);

        sharedPreferencesState = getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        lbl_reports.setText(sharedPreferencesState.getString(KEY_STATE, ""));

        //-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
//            this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("Report");
        } catch (NullPointerException e) {
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
        switch (item.getItemId()){
            case R.id.home_menu:
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}