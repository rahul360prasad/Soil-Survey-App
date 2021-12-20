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

public class MorphologicalParameters extends AppCompatActivity {

    //declaring variable for storing selected options from spinner
    //---------------------MORPHOLOGICAL PARAMS---------------------------------------------------------------------------------
    private String selectedBoundary, selectedDistinctness, selectedTopography, selectedDiagnostic, selectedMatrixCol;
    //----------------------MOTTLE COLOUR--------------------------------------------------------------------------------
    private String selected_MC_Abundance, selected_MC_Size, selected_MC_Contrast, selected_MC_Texture;
    //------------------------COARSE FRAGMENTS--------------------------------------------------------------------------------
    private String selected_CF_Size, selected_CF_Vol;
    //--------------------------STRUCTURE---------------------------------------------------------------------------------
    private String selected_Str_Size, selected_Str_Grade, selected_Str_Type;
    //---------------------------CONSISTENCE--------------------------------------------------------------------------------
    private String selected_Con_D, selected_Con_M, selected_Con_W;
    //---------------------------POROSITY--------------------------------------------------------------------------------
    private String selected_Poros_S, selected_Poros_Q;
    //---------------------------CUTANS--------------------------------------------------------------------------------
    private String selected_Cutans_Ty, selected_Cutans_Th, selected_Cutans_Q;
    //----------------------------NODULES--------------------------------------------------------------------------------
    private String selected_Nodules_S, selected_Nodules_Q;
    //-----------------------------ROOTS--------------------------------------------------------------------------------
    private String selected_Roots_S, selected_Roots_Q, selected_Roots_Effervescence;


    // defining spinner for all label spinners
    //---------------------MORPHOLOGICAL PARAMS---------------------------------------------------------------------------------
    private Spinner boundarySpinner, distinctnessSpinner, topographySpinner, diagnosticSpinner, matrixColSpinner;
    //----------------------MOTTLE COLOUR--------------------------------------------------------------------------------
    private Spinner abundance_MC_Spinner, size_MC_Spinner, contrast_MC_Spinner, texture_MC_Spinner;
    //------------------------COARSE FRAGMENTS--------------------------------------------------------------------------------
    private Spinner size_CF_Spinner, vol_CF_Spinner;
    //--------------------------STRUCTURE---------------------------------------------------------------------------------
    private Spinner size_Str_Spinner, grade_Str_Spinner, type_Str_Spinner;
    //---------------------------CONSISTENCE--------------------------------------------------------------------------------
    private Spinner d_Con_Spinner, m_Con_Spinner, w_Con_Spinner;
    //---------------------------POROSITY--------------------------------------------------------------------------------
    private Spinner s_Poro_Spinner, q_Poro_Spinner;
    //---------------------------CUTANS--------------------------------------------------------------------------------
    private Spinner ty_Cutans_Spinner, th_Cutans_Spinner, q_Cutans_Spinner;
    //----------------------------NODULES--------------------------------------------------------------------------------
    private Spinner s_Nodules_Spinner, q_Nodules_Spinner;
    //-----------------------------ROOTS--------------------------------------------------------------------------------
    private Spinner s_Roots_Spinner, q_Roots_Spinner, effervescence_Roots_Spinner;


    //defining and declaring array adapter all spinner options
    //---------------------MORPHOLOGICAL PARAMS---------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> boundaryAdapter, distinctnessAdapter, topographyAdapter, diagnosticAdapter, matrixColAdapter;
    //----------------------MOTTLE COLOUR--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> abundance_MC_Adapter, size_MC_Adapter, contrast_MC_Adapter, texture_MC_Adapter;
    //------------------------COARSE FRAGMENTS--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> size_CF_Adapter, vol_CF_Adapter;
    //--------------------------STRUCTURE---------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> size_Str_Adapter, grade_Str_Adapter, type_Str_Adapter;
    //---------------------------CONSISTENCE--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> d_Con_Adapter, m_Con_Adapter, w_Con_Adapter;
    //---------------------------POROSITY--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> s_Poro_Adapter, q_Poro_Adapter;
    //---------------------------CUTANS--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> ty_Cutans_Adapter, th_Cutans_Adapter, q_Cutans_Adapter;
    //----------------------------NODULES--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> s_Nodules_Adapter, q_Nodules_Adapter;
    //-----------------------------ROOTS--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> s_Roots_Adapter, q_Roots_Adapter, effervescence_Roots_Adapter;

    private Button backBtn, nextBtn;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PresentLandUse.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morphological_parameters);
        //--------------HIDING THE ACTION BAR
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

//-------------------------REFERENCES-----------------------------------

        //----------spinners REFERENCES------------------------------
//        boundarySpinner = (Spinner) findViewById(R.id.spin_boundary);
        distinctnessSpinner = (Spinner) findViewById(R.id.spin_distinctness);
        topographySpinner = (Spinner) findViewById(R.id.spin_topography);
        diagnosticSpinner = (Spinner) findViewById(R.id.spin_diagnostic_horizon);
        matrixColSpinner = (Spinner) findViewById(R.id.spin_matrix_color);
        abundance_MC_Spinner = (Spinner) findViewById(R.id.spin_mc_abundance);
        size_MC_Spinner = (Spinner) findViewById(R.id.spin_mc_size);
        contrast_MC_Spinner = (Spinner) findViewById(R.id.spin_mc_contrast);
        texture_MC_Spinner = (Spinner) findViewById(R.id.spin_mc_texture);
        size_CF_Spinner = (Spinner) findViewById(R.id.spin_cf_size);
//        vol_CF_Spinner = (Spinner) findViewById(R.id.spin_cf_vol);
        size_Str_Spinner = (Spinner) findViewById(R.id.spin_structure_size);
        grade_Str_Spinner = (Spinner) findViewById(R.id.spin_structure_grade);
        type_Str_Spinner = (Spinner) findViewById(R.id.spin_structure_type);
        d_Con_Spinner = (Spinner) findViewById(R.id.spin_consistence_d);
        m_Con_Spinner = (Spinner) findViewById(R.id.spin_consistence_m);
        w_Con_Spinner = (Spinner) findViewById(R.id.spin_consistence_w);
        s_Poro_Spinner = (Spinner) findViewById(R.id.spin_porosity_s);
        q_Poro_Spinner = (Spinner) findViewById(R.id.spin_porosity_q);
        ty_Cutans_Spinner = (Spinner) findViewById(R.id.spin_cutans_ty);
        th_Cutans_Spinner = (Spinner) findViewById(R.id.spin_cutans_th);
        q_Cutans_Spinner = (Spinner) findViewById(R.id.spin_cutans_q);
        s_Nodules_Spinner = (Spinner) findViewById(R.id.spin_nodules_s);
        q_Nodules_Spinner = (Spinner) findViewById(R.id.spin_nodules_q);
        s_Roots_Spinner = (Spinner) findViewById(R.id.spin_roots_s);
        q_Roots_Spinner = (Spinner) findViewById(R.id.spin_roots_q);
        effervescence_Roots_Spinner = (Spinner) findViewById(R.id.spin_roots_effervescence);

        //----------BUTTON---------------
        backBtn = (Button) findViewById(R.id.morphology_back_btn);
        nextBtn = (Button) findViewById(R.id.morphology_next_btn);


        //---------------------------------------------LIST OF MORPHOLOGICAL PARAMETER'S SPINNER-----------------------------------------------------------------

        //---------------------------------------------LIST OF DISTINCTNESS'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        distinctnessAdapter = ArrayAdapter.createFromResource(this, R.array.array_distinctness, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        distinctnessAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        distinctnessSpinner.setAdapter(distinctnessAdapter);
        //now when selecting any options from spinner
        distinctnessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selectedDistinctness = distinctnessSpinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF TOPOGRAPHY'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        topographyAdapter = ArrayAdapter.createFromResource(this, R.array.array_topography, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        topographyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        topographySpinner.setAdapter(topographyAdapter);
        //now when selecting any options from spinner
        topographySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selectedTopography = topographySpinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF DIAGNOSTIC HORIZON'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        diagnosticAdapter = ArrayAdapter.createFromResource(this, R.array.array_diagnostic_horizon, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        diagnosticAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        diagnosticSpinner.setAdapter(diagnosticAdapter);
        //now when selecting any options from spinner
        diagnosticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selectedDiagnostic = diagnosticSpinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF MATRIX COLOUR'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        matrixColAdapter = ArrayAdapter.createFromResource(this, R.array.array_matrix_colour, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        matrixColAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        matrixColSpinner.setAdapter(matrixColAdapter);
        //now when selecting any options from spinner
        matrixColSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selectedMatrixCol = matrixColSpinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------MOTTLE COLOUR-----------------------------------------------------------------
        //---------------------------------------------LIST OF MC_ABUNDANCE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        abundance_MC_Adapter = ArrayAdapter.createFromResource(this, R.array.array_mc_abundance, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        abundance_MC_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        abundance_MC_Spinner.setAdapter(abundance_MC_Adapter);
        //now when selecting any options from spinner
        abundance_MC_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_MC_Abundance = abundance_MC_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF MC_SIZE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        size_MC_Adapter = ArrayAdapter.createFromResource(this, R.array.array_mc_size, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        size_MC_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        size_MC_Spinner.setAdapter(size_MC_Adapter);
        //now when selecting any options from spinner
        size_MC_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_MC_Size = size_MC_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF MC_CONTRAST'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        contrast_MC_Adapter = ArrayAdapter.createFromResource(this, R.array.array_mc_contrast, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        contrast_MC_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        contrast_MC_Spinner.setAdapter(contrast_MC_Adapter);
        //now when selecting any options from spinner
        contrast_MC_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_MC_Contrast = contrast_MC_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF MC_TEXTURE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        texture_MC_Adapter = ArrayAdapter.createFromResource(this, R.array.array_mc_texture, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        texture_MC_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        texture_MC_Spinner.setAdapter(texture_MC_Adapter);
        //now when selecting any options from spinner
        texture_MC_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_MC_Texture = texture_MC_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------COARSE FRAGMENTS-----------------------------------------------------------------
        //---------------------------------------------LIST OF CF_SIZE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        size_CF_Adapter = ArrayAdapter.createFromResource(this, R.array.array_cf_size, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        size_CF_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        size_CF_Spinner.setAdapter(size_CF_Adapter);
        //now when selecting any options from spinner
        size_CF_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_CF_Size = size_CF_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF CF_VOL'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
//        vol_CF_Adapter = ArrayAdapter.createFromResource(this, R.array.array_cf_vol, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        vol_CF_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter i.e boundaryAdapter
//        vol_CF_Spinner.setAdapter(vol_CF_Adapter);
//        //now when selecting any options from spinner
//        vol_CF_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of tehsil spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
////                    tv.setActivated(false);
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner
//                selected_CF_Vol = vol_CF_Spinner.getSelectedItem().toString();
//
//                //when selecting the state name district spinner is populated
////                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //---------------------------------------------LIST OF STR_SIZE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        size_Str_Adapter = ArrayAdapter.createFromResource(this, R.array.array_str_size, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        size_Str_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        size_Str_Spinner.setAdapter(size_Str_Adapter);
        //now when selecting any options from spinner
        size_Str_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Str_Size = size_Str_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF STR_GRADE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        grade_Str_Adapter = ArrayAdapter.createFromResource(this, R.array.array_str_grade, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        grade_Str_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        grade_Str_Spinner.setAdapter(grade_Str_Adapter);
        //now when selecting any options from spinner
        grade_Str_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Str_Grade = grade_Str_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF STR_TYPE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        type_Str_Adapter = ArrayAdapter.createFromResource(this, R.array.array_str_type, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        type_Str_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        type_Str_Spinner.setAdapter(type_Str_Adapter);
        //now when selecting any options from spinner
        type_Str_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Str_Type = type_Str_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------CONSISTENCE (D-M-W)-----------------------------------------------------------------
        //---------------------------------------------LIST OF CON_D'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        d_Con_Adapter = ArrayAdapter.createFromResource(this, R.array.array_con_d, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        d_Con_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        d_Con_Spinner.setAdapter(d_Con_Adapter);
        //now when selecting any options from spinner
        d_Con_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Con_D = d_Con_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF CON_M'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        m_Con_Adapter = ArrayAdapter.createFromResource(this, R.array.array_con_m, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        m_Con_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        m_Con_Spinner.setAdapter(m_Con_Adapter);
        //now when selecting any options from spinner
        m_Con_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Con_M = m_Con_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF CON_W'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        w_Con_Adapter = ArrayAdapter.createFromResource(this, R.array.array_con_w, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        w_Con_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        w_Con_Spinner.setAdapter(w_Con_Adapter);
        //now when selecting any options from spinner
        w_Con_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Con_W = w_Con_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------POROSITY (S-Q)-----------------------------------------------------------------
        //---------------------------------------------LIST OF PORO_S'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        s_Poro_Adapter = ArrayAdapter.createFromResource(this, R.array.array_poro_s, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        s_Poro_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        s_Poro_Spinner.setAdapter(s_Poro_Adapter);
        //now when selecting any options from spinner
        s_Poro_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Poros_S = s_Poro_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF PORO_Q'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        q_Poro_Adapter = ArrayAdapter.createFromResource(this, R.array.array_poro_q, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        q_Poro_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        q_Poro_Spinner.setAdapter(q_Poro_Adapter);
        //now when selecting any options from spinner
        q_Poro_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Poros_Q = q_Poro_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------CUTANS (TY-TH-Q)-----------------------------------------------------------------
        //---------------------------------------------LIST OF CUTANS_TY'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        ty_Cutans_Adapter = ArrayAdapter.createFromResource(this, R.array.array_cutans_ty, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        ty_Cutans_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        ty_Cutans_Spinner.setAdapter(ty_Cutans_Adapter);
        //now when selecting any options from spinner
        ty_Cutans_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Cutans_Ty = ty_Cutans_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF CUTANS_TH'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        th_Cutans_Adapter = ArrayAdapter.createFromResource(this, R.array.array_cutans_th, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        th_Cutans_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        th_Cutans_Spinner.setAdapter(th_Cutans_Adapter);
        //now when selecting any options from spinner
        th_Cutans_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Cutans_Th = th_Cutans_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF CUTANS_Q'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        q_Cutans_Adapter = ArrayAdapter.createFromResource(this, R.array.array_cutans_q, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        q_Cutans_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        q_Cutans_Spinner.setAdapter(q_Cutans_Adapter);
        //now when selecting any options from spinner
        q_Cutans_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Cutans_Q = q_Cutans_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------NODULES (S-Q)-----------------------------------------------------------------
        //---------------------------------------------LIST OF NODULES_S'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        s_Nodules_Adapter = ArrayAdapter.createFromResource(this, R.array.array_nodules_s, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        s_Nodules_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        s_Nodules_Spinner.setAdapter(s_Nodules_Adapter);
        //now when selecting any options from spinner
        s_Nodules_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Nodules_S = s_Nodules_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF NODULES_Q'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        q_Nodules_Adapter = ArrayAdapter.createFromResource(this, R.array.array_nodules_q, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        q_Nodules_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        q_Nodules_Spinner.setAdapter(q_Nodules_Adapter);
        //now when selecting any options from spinner
        q_Nodules_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Nodules_Q = q_Nodules_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------ROOTS (S-Q)-----------------------------------------------------------------
        //---------------------------------------------LIST OF ROOTS_S'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        s_Roots_Adapter = ArrayAdapter.createFromResource(this, R.array.array_roots_s, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        s_Roots_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        s_Roots_Spinner.setAdapter(s_Roots_Adapter);
        //now when selecting any options from spinner
        s_Roots_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Roots_S = s_Roots_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF ROOTS_Q'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        q_Roots_Adapter = ArrayAdapter.createFromResource(this, R.array.array_roots_q, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        q_Roots_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        q_Roots_Spinner.setAdapter(q_Roots_Adapter);
        //now when selecting any options from spinner
        q_Roots_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Roots_Q = q_Roots_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------------LIST OF ROOTS_EFFERVESCENCE'S SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        effervescence_Roots_Adapter = ArrayAdapter.createFromResource(this, R.array.array_roots_effervescence, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        effervescence_Roots_Adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e boundaryAdapter
        effervescence_Roots_Spinner.setAdapter(effervescence_Roots_Adapter);
        //now when selecting any options from spinner
        effervescence_Roots_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of tehsil spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
//                    tv.setActivated(false);
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner
                selected_Roots_Effervescence = effervescence_Roots_Spinner.getSelectedItem().toString();

                //when selecting the state name district spinner is populated
//                districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//---------------------------BUTTON OPERATIONS--------------------------
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PhysicalParameters.class));
            }
        });
    }
}