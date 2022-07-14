package com.example.soilsurveyapp;

import android.util.Log;

public class LocationRecordData {
      private String surveyorname;

    public LocationRecordData(){

    };

    public LocationRecordData(String surveyorname) {
        this.surveyorname=surveyorname;
    }

    public String getSurveyorname() {
         return surveyorname;
    }

    public void setSurveyorname(String surveyorname) {
        this.surveyorname = surveyorname;
    }
}
