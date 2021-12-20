package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SoilSiteParameter_2 extends AppCompatActivity {
    //declaring variable for storing selected slope_gradient, slope_length, erosion, runoff, drainage, ground_water_depth, flooding, salt/alkali, Ec, pH, stone_size, stoiness, rock_outcrops
    private String selectedSlopeGradient, selectedSlopeLength, selectedErosion, selectedRunoff, selectedDrainage, selectedGroundWaterDepth, selectedFlooding, selectedSalt_Alkali, selectedEc, selectedPH, selectedStoneSize, selectedStoiness, selectedRockOutcrops;
    // defining spinner for slope_gradient, slope_length, erosion, runoff, drainage, ground_water_depth, flooding, salt/alkali, Ec, pH, stone_size, stoiness, rock_outcrops
    private Spinner slopeGradientSpinner, slopeLengthSpinner, erosionSpinner, runoffSpinner, drainageSpinner, groundWaterDepthSpinner, floodingSpinner, salt_AlkaliSpinner, ecSpinner, pHSpinner, stoneSizeSpinner, stoinessSpinner, rockOutcropsSpinner;
    //defining and declaring array adapter for slope_gradient, slope_length, erosion, runoff, drainage, ground_water_depth, flooding, salt/alkali, Ec, pH, stone_size, stoiness, rock_outcrops
    private ArrayAdapter<CharSequence> slopeGradientAdapter, slopeLengthAdapter, erosionAdapter, runoffAdapter, drainageAdapter, groundWaterDepthAdapter, floodingAdapter, salt_AlkaliAdapter, ecAdapter, pHAdapter, stoneSizeAdapter, stoinessAdapter, rockOutcropsAdapter;

    Button backBtn, nextBtn;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SoilSiteParameters.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_site_parameter2);

 //-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

//---------------------REFERENCES----------------------------------------------------------------------
        slopeGradientSpinner=(Spinner) findViewById(R.id.spin_slope_gradient);
        slopeLengthSpinner= (Spinner) findViewById(R.id.spin_slope_length);
        erosionSpinner= (Spinner) findViewById(R.id.spin_erosion);
        runoffSpinner= (Spinner) findViewById(R.id.spin_runoff);
        drainageSpinner=(Spinner) findViewById(R.id.spin_drainage);
        groundWaterDepthSpinner=(Spinner) findViewById(R.id.spin_ground_water);
        floodingSpinner=(Spinner) findViewById(R.id.spin_flooding);
        salt_AlkaliSpinner=(Spinner) findViewById(R.id.spin_salt_alkali);
        ecSpinner=(Spinner) findViewById(R.id.spin_Ec);
        pHSpinner=(Spinner) findViewById(R.id.spin_pH);
        stoneSizeSpinner=(Spinner) findViewById(R.id.spin_stone_size);
        stoinessSpinner=(Spinner) findViewById(R.id.spin_stoiness);
        rockOutcropsSpinner=(Spinner) findViewById(R.id.spin_rock_outcrops);

        backBtn=(Button) findViewById(R.id.soil_site_param2_back_btn);
        nextBtn=(Button) findViewById(R.id.soil_site_param2_next_btn);

//-------------------------------LIST OF SLOPE OF GRADIANT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        slopeGradientAdapter = ArrayAdapter.createFromResource(this, R.array.array_slope_gradient, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        slopeGradientAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        slopeGradientSpinner.setAdapter(slopeGradientAdapter);
        //now when selecting any option from spinner
        slopeGradientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedSlopeGradient = slopeGradientSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF SLOPE OF LENGTH ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        slopeLengthAdapter = ArrayAdapter.createFromResource(this, R.array.array_slope_length, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        slopeLengthAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        slopeLengthSpinner.setAdapter(slopeLengthAdapter);
        //now when selecting any option from spinner
        slopeLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedSlopeLength = slopeLengthSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF EROSION ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        erosionAdapter = ArrayAdapter.createFromResource(this, R.array.array_errosion, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        erosionAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        erosionSpinner.setAdapter(erosionAdapter);
        //now when selecting any option from spinner
        erosionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedErosion = erosionSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF RUNOFF ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        runoffAdapter = ArrayAdapter.createFromResource(this, R.array.array_runoff, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        runoffAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        runoffSpinner.setAdapter(runoffAdapter);
        //now when selecting any option from spinner
        runoffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedRunoff = runoffSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF DRAINAGE ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        drainageAdapter = ArrayAdapter.createFromResource(this, R.array.array_drainage, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        drainageAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        drainageSpinner.setAdapter(drainageAdapter);
        //now when selecting any option from spinner
        drainageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedDrainage = drainageSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF GROUND WATER DEPTH ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        groundWaterDepthAdapter = ArrayAdapter.createFromResource(this, R.array.array_ground_water, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        groundWaterDepthAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        groundWaterDepthSpinner.setAdapter(groundWaterDepthAdapter);
        //now when selecting any option from spinner
        groundWaterDepthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedGroundWaterDepth = groundWaterDepthSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF FLOODING ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        floodingAdapter = ArrayAdapter.createFromResource(this, R.array.array_flodding, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        floodingAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        floodingSpinner.setAdapter(floodingAdapter);
        //now when selecting any option from spinner
        floodingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedFlooding = floodingSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF SALT/ALKALI ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        salt_AlkaliAdapter = ArrayAdapter.createFromResource(this, R.array.array_salt_alkali, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        salt_AlkaliAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        salt_AlkaliSpinner.setAdapter(salt_AlkaliAdapter);
        //now when selecting any option from spinner
        salt_AlkaliSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedSalt_Alkali = salt_AlkaliSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF pH ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        pHAdapter = ArrayAdapter.createFromResource(this, R.array.array_ph, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        pHAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        pHSpinner.setAdapter(pHAdapter);
        //now when selecting any option from spinner
        pHSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedPH = pHSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF Ec ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        ecAdapter = ArrayAdapter.createFromResource(this, R.array.array_ec, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        ecAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        ecSpinner.setAdapter(ecAdapter);
        //now when selecting any option from spinner
        ecSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedEc = ecSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF STONE SIZE ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        stoneSizeAdapter = ArrayAdapter.createFromResource(this, R.array.array_stone_size, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        stoneSizeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        stoneSizeSpinner.setAdapter(stoneSizeAdapter);
        //now when selecting any option from spinner
        stoneSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedStoneSize = stoneSizeSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF STOINESS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        stoinessAdapter = ArrayAdapter.createFromResource(this, R.array.array_stoiness, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        stoinessAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        stoinessSpinner.setAdapter(stoinessAdapter);
        //now when selecting any option from spinner
        stoinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedStoiness = stoinessSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF ROCK OUTCROPS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        rockOutcropsAdapter = ArrayAdapter.createFromResource(this, R.array.array_rock_outcrops, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        rockOutcropsAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        rockOutcropsSpinner.setAdapter(rockOutcropsAdapter);
        //now when selecting any option from spinner
        rockOutcropsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selected... variable
                selectedRockOutcrops = rockOutcropsSpinner.getSelectedItem().toString();
                //when selecting the state name district spinner is populated
                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//-------------------------------LAST TWO BUTTONS-----------------------------------------------------------------
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PresentLandUse.class));
            }
        });

    }
}