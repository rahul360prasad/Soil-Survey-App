package com.example.soilsurveyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class LocationDetails extends AppCompatActivity {

    public static final String KEY_PROJECT_ID = "id";
    public static final String KEY_ID = "id";
    // url to post the data
//    private static final String url = "http://10.0.0.145/login/locationDetailSingle.php";
//    private static final String url = "http://10.0.0.145/login/locationDetailSingle1.php";
//    private static final String url = "http://14.139.123.73:9090/web/NBSS/php/locationDetailSingle.php";  //working 100%
    private static final String url = "http://14.139.123.73:9090/web/NBSS/php/locationDetailSingle1.php"; //working 100%
    //------------LOCATION CODE------------
    private static final int REQUEST_LOCATION = 1;
    private static final String SHARED_PRE_NAME = "dataCollection";
    private static final String SHARED_PRE_NAME2 = "mypref";
    ProgressDialog progressDialog;
    //-------------------ADD PHOTOS-----------------------
    Button east_browse, west_browse, north_browse, south_browse, uploadBtn;
    ImageView east_imgView, west_imgView, north_imgView, south_imgView;
    String projID, east_encodeImgStr, west_encodeImgStr, north_encodeImgStr, south_encodeImgStr;
    Bitmap east_bitmap, west_bitmap, north_bitmap, south_bitmap;
    Button btnGetLocation;
    LocationManager locationManager;
    TextView lbl_latitude, lbl_longitude;
    //---------SHARED PREFERENCES-------------------
    //taking project id from datacollection page
    SharedPreferences sharedPreferencesId;
    //taking user id from login/register
    SharedPreferences sharedPreferences_userId;
    //---------holding the images on image view------------
    //------------------------------EAST--------------------------
    ActivityResultLauncher<Intent> someActivityResultLauncherEst = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent estData = result.getData();
                        Uri imageUri;
                        if (estData != null) {
                            imageUri = estData.getData();
                            InputStream inputStream;
                            try {
                                inputStream = getContentResolver().openInputStream(imageUri);
                                east_bitmap = BitmapFactory.decodeStream(inputStream);
                                east_imgView.setImageBitmap(east_bitmap);
                                east_encodeBitmapImg(east_bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
    //-------------------------------------WEST-----------------------------------
    ActivityResultLauncher<Intent> someActivityResultLauncherWst = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent wstData = result.getData();
                        Uri imageUri;
                        if (wstData != null) {
                            imageUri = wstData.getData();
                            InputStream inputStream;
                            try {
                                inputStream = getContentResolver().openInputStream(imageUri);
                                west_bitmap = BitmapFactory.decodeStream(inputStream);
                                west_imgView.setImageBitmap(west_bitmap);
                                west_encodeBitmapImage(west_bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    //--------------------------------------------NORTH-------------------------------
    ActivityResultLauncher<Intent> someActivityResultLauncherNth = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent wstData = result.getData();
                        Uri imageUri;
                        if (wstData != null) {
                            imageUri = wstData.getData();
                            InputStream inputStream;
                            try {
                                inputStream = getContentResolver().openInputStream(imageUri);
                                north_bitmap = BitmapFactory.decodeStream(inputStream);
                                north_imgView.setImageBitmap(north_bitmap);
                                north_encodeBitmapImage(north_bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    //--------------------------------------------SOUTH-------------------------------
    ActivityResultLauncher<Intent> someActivityResultLauncherSth = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent wstData = result.getData();
                        Uri imageUri;
                        if (wstData != null) {
                            imageUri = wstData.getData();
                            InputStream inputStream;
                            try {
                                inputStream = getContentResolver().openInputStream(imageUri);
                                south_bitmap = BitmapFactory.decodeStream(inputStream);
                                south_imgView.setImageBitmap(south_bitmap);
                                south_encodeBitmapImage(south_bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    // defining spinner for state, district, tehsil, block, topo250k, topo50k
    private Spinner stateSpinner, districtSpinner, tehsilSpinner, blockSpinner, topo250kSpinner, topo50kSpinner,
            physioCatgySpinner, subPhysioUnitSpinner, slopeGradientSpinner, slopeLengthSpinner, erosionSpinner, runoffSpinner,
            drainageSpinner, groundWaterDepthSpinner, floodingSpinner, salt_AlkaliSpinner, ecSpinner, pHSpinner,
            stoneSizeSpinner, stoinessSpinner, rockOutcropsSpinner, forestSpinner, cultivatedSpinner, terracesSpinner, pastureLandSpinner,
            degrCulturableSpinner, degrunCulturableSpinner, landCapaClassSpinner, landCapaSubClassSpinner, landIrrigaClassSpinner,
            landIrrigaSubClassSpinner, cropSpinner, managPractSpinner;
    //---------------------MORPHOLOGICAL PARAMS---------------------------------------------------------------------------------
    private Spinner distinctnessSpinner, topographySpinner, diagnosticSpinner, matrixColSpinner;
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
    private EditText etsurveyorName, ettehsilName, etblockName, etvillageName, etelevation, etprojProfileID, etremark, etGeology,
            etParentMaterial, etClimate, etRainfall, etTopographyLandform, etNaturalVegetation,
            etPhaseSurface, etPhaseSubstratum, etpluRemark, etYield, etCroppingSys, etHorizon, etDepth,
            et_CF_Vol, et_OF_Size, et_OF_Abundance, et_OF_Nature, et_OF_SampleBagNo, et_OF_AdditionalNotes,
            etPD_sand, etPD_silt, etPD_clay, etPD_textural, etPD_density, etWR_33kpa, etWR_1500kpa, etWR_awc, etWR_pawc, etWR_gravimetric,
            etPH, etEC, etOC, etCaCo, etCa, etMg, etNa, etK, etTotalBase, etCEC, etBS, etESP,
            etOrganicCarbon, et_MaN_nitrogen, et_MaN_phosphorus, et_MaN_potassium, et_MiN_sulphur, et_MiN_zinc, et_MiN_copper, et_MiN_iron, et_MiN_manganese;
    private TextView dateText, timeText;
    private Button backBtn, nextBtn;
    //declaring variable for storing selected state, district, tehsil, block, topo250k, topo50k
    private String selectedState, selectedDistrict, selectedTehsil, selectedBlock, selectedTopo250k, selectedTopo50k,
            selectedPhysioCatgy, selectedSubPhysioUnit, selectedSlopeGradient, selectedSlopeLength, selectedErosion,
            selectedRunoff, selectedDrainage, selectedGroundWaterDepth, selectedFlooding, selectedSalt_Alkali, selectedEc,
            selectedPH, selectedStoneSize, selectedStoiness, selectedRockOutcrops,
            selectedForest, selectedCultivated, selectedTerraces, selectedPastureLand, selectedDegCulturable, selectedDegUncultrable,
            selectedLandCapClass, selectedLandCapsubClass, selectedLandIrrigaClass, selectedLandIrrigaSubclass, selectedCrop, selectedManagPractice;
    //---------------------MORPHOLOGICAL PARAMS---------------------------------------------------------------------------------
    private String selectedDistinctness, selectedTopography, selectedDiagnostic, selectedMatrixCol;
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
    // creating a strings for storing our values from edittext fields.
    private String surveyorname, tehsilname, blockname, villagename, date, time, latitude, longitude, elevation, projectProfileID, remark,
            geology, parentMaterial, climate, rainfall, topographyLandform, naturalVegetation,
            phaseSurface, phaseSubstratum, pluremark, yield, croppingSystem, horizon, depth,
            cf_vol, of_size, of_abundance, of_nature, of_samplebagno, of_additionalnotes,
            PD_sand, PD_silt, PD_clay, PD_textural, PD_density, WR_33kpa, WR_1500kpa, WR_awc, WR_pawc, WR_gravimetric,
            PH, EC, OC, CaCo, Ca, Mg, Na, K, TotalBase, CEC, BS, ESP,
            organicCarbon, MaN_nitrogen, MaN_phosphorus, MaN_potassium, MiN_sulphur, MiN_zinc, MiN_copper, MiN_iron, MiN_manganese;
    //defining and declaring array adapter for state, district, tehsil, block, topo250k, topo50k
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter, tehsilAdapter, blockAdapter, topo250kAdapter, topo50kAdapter, physioCatgyAdapter, subPhysioUnitAdapter,
            slopeGradientAdapter, slopeLengthAdapter, erosionAdapter, runoffAdapter, drainageAdapter, groundWaterDepthAdapter, floodingAdapter, salt_AlkaliAdapter, ecAdapter,
            pHAdapter, stoneSizeAdapter, stoinessAdapter, rockOutcropsAdapter, forestAdapter, cultivatedAdapter, terracesAdapter, pastureLandAdapter, degrCulturableAdapter,
            degrunCulturableAdapter, landCapaClassAdapter, landCapaSubClassAdapter, landIrrigaClassAdapter, landIrrigaSubClassAdapter, cropAdapter, managPractAdapter;
    //---------------------MORPHOLOGICAL PARAMS---------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> distinctnessAdapter, topographyAdapter, diagnosticAdapter, matrixColAdapter;
    //----------------------MOTTLE COLOUR--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> abundance_MC_Adapter, size_MC_Adapter, contrast_MC_Adapter, texture_MC_Adapter;
    //------------------------COARSE FRAGMENTS--------------------------------------------------------------------------------
    private ArrayAdapter<CharSequence> size_CF_Adapter;
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
    private String projectID, admin_status;
    private int userid;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LocationDetails.this, DataCollection.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        admin_status = "";
        //--------Location codes-------------------
        //Add permission
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        lbl_latitude = findViewById(R.id.lbl_latitude);
        lbl_longitude = findViewById(R.id.lbl_longitude);
        btnGetLocation = findViewById(R.id.get_location);

        //------------------HIDING THE ACTION BAR----------------------
        try {
//            this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {
        }

        surveyorname = tehsilname = blockname = villagename = elevation = projectProfileID = remark = "";

        //---------------REFERENCES------------------------------
        //----------spinners-----------
        stateSpinner = (Spinner) findViewById(R.id.spin_select_state);
        districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
        topo250kSpinner = (Spinner) findViewById(R.id.spin_toposheet250k);
        topo50kSpinner = (Spinner) findViewById(R.id.spin_toposheet50k);
        //----------edittext-------------
        etsurveyorName = (EditText) findViewById(R.id.surveyor_name);
        ettehsilName = (EditText) findViewById(R.id.tehsil_name);
        etblockName = (EditText) findViewById(R.id.block_name);
        etvillageName = (EditText) findViewById(R.id.village_name);
        etelevation = (EditText) findViewById(R.id.elevation);
        etprojProfileID = (EditText) findViewById(R.id.profileID);
        etremark = (EditText) findViewById(R.id.remark);

        //-----------SOIL SITE PARAMETERS------------------------
        physioCatgySpinner = (Spinner) findViewById(R.id.spin_phy_catgy);
        subPhysioUnitSpinner = (Spinner) findViewById(R.id.spin_phy_unit);
        etGeology = (EditText) findViewById(R.id.geology);
        etParentMaterial = (EditText) findViewById(R.id.parent_material);
        etClimate = (EditText) findViewById(R.id.climate);
        etRainfall = (EditText) findViewById(R.id.rainfall);
        etTopographyLandform = (EditText) findViewById(R.id.topography_type);
        slopeGradientSpinner = (Spinner) findViewById(R.id.spin_slope_gradient);
        slopeLengthSpinner = (Spinner) findViewById(R.id.spin_slope_length);
        erosionSpinner = (Spinner) findViewById(R.id.spin_erosion);
        runoffSpinner = (Spinner) findViewById(R.id.spin_runoff);
        drainageSpinner = (Spinner) findViewById(R.id.spin_drainage);
        groundWaterDepthSpinner = (Spinner) findViewById(R.id.spin_ground_water);
        floodingSpinner = (Spinner) findViewById(R.id.spin_flooding);
        salt_AlkaliSpinner = (Spinner) findViewById(R.id.spin_salt_alkali);
        ecSpinner = (Spinner) findViewById(R.id.spin_Ec);
        pHSpinner = (Spinner) findViewById(R.id.spin_pH);
        stoneSizeSpinner = (Spinner) findViewById(R.id.spin_stone_size);
        stoinessSpinner = (Spinner) findViewById(R.id.spin_stoiness);
        rockOutcropsSpinner = (Spinner) findViewById(R.id.spin_rock_outcrops);
        etNaturalVegetation = (EditText) findViewById(R.id.input_natural_vegetation);

        //------------PRESENT LAND USE---------------
        forestSpinner = (Spinner) findViewById(R.id.spin_forest);
        cultivatedSpinner = (Spinner) findViewById(R.id.spin_cultivated);
        terracesSpinner = (Spinner) findViewById(R.id.spin_terraces);
        pastureLandSpinner = (Spinner) findViewById(R.id.spin_pasture_land);
        degrCulturableSpinner = (Spinner) findViewById(R.id.spin_degraded_culturable);
        degrunCulturableSpinner = (Spinner) findViewById(R.id.spin_degraded_unculturable);
        landCapaClassSpinner = (Spinner) findViewById(R.id.spin_land_capability);
        landCapaSubClassSpinner = (Spinner) findViewById(R.id.spin_land_capab_subclass);
        landIrrigaClassSpinner = (Spinner) findViewById(R.id.spin_land_irrigability_class);
        landIrrigaSubClassSpinner = (Spinner) findViewById(R.id.spin_land_irrigability_subclass);
        cropSpinner = (Spinner) findViewById(R.id.spin_crop);
        managPractSpinner = (Spinner) findViewById(R.id.spin_management_practice);
        etPhaseSurface = (EditText) findViewById(R.id.et_phase_surface);
        etPhaseSubstratum = (EditText) findViewById(R.id.et_phase_substratum);
        etpluRemark = (EditText) findViewById(R.id.pre_land_remark);
        etYield = (EditText) findViewById(R.id.et_yield);
        etCroppingSys = (EditText) findViewById(R.id.et_cropping_system);

        //----------PROJECT CREDENTIALS-----------------
        etHorizon = (EditText) findViewById(R.id.input_proCredential_horizon);
        etDepth = (EditText) findViewById(R.id.input_depth);

        //----------MORPHOLOGICAL PARAMETERS---------------
        //------------edittext REFERENCES----------------
        etDepth = (EditText) findViewById(R.id.input_depth);
        et_CF_Vol = (EditText) findViewById(R.id.txt_cf_vol);
        et_OF_Size = (EditText) findViewById(R.id.txt_of_size);
        et_OF_Abundance = (EditText) findViewById(R.id.txt_of_abundance);
        et_OF_Nature = (EditText) findViewById(R.id.txt_of_nature);
        et_OF_SampleBagNo = (EditText) findViewById(R.id.txt_of_sample_no_);
        et_OF_AdditionalNotes = (EditText) findViewById(R.id.txt_additional_notes);

        //-------------PHYSICAL PARAMETERS-------------------
        etPD_sand = (EditText) findViewById(R.id.input_pp_sand);
        etPD_silt = (EditText) findViewById(R.id.input_pp_silt);
        etPD_clay = (EditText) findViewById(R.id.input_pp_clay);
        etPD_textural = (EditText) findViewById(R.id.input_pp_texture);
        etPD_density = (EditText) findViewById(R.id.input_pp_bulkDensity);

        etWR_33kpa = (EditText) findViewById(R.id.input_pp_33kPa);
        etWR_1500kpa = (EditText) findViewById(R.id.input_pp_1500kPa);
        etWR_awc = (EditText) findViewById(R.id.input_pp_awc);
        etWR_pawc = (EditText) findViewById(R.id.input_pp_pawc);
        etWR_gravimetric = (EditText) findViewById(R.id.input_pp_graviMoisture);

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

        //---------------CHEMICAL PARAMETERS----------------
        etPH = (EditText) findViewById(R.id.input_cp_ph);
        etEC = (EditText) findViewById(R.id.input_cp_ec);
        etOC = (EditText) findViewById(R.id.input_cp_oc);
        etCaCo = (EditText) findViewById(R.id.input_cp_caco);
        etCa = (EditText) findViewById(R.id.input_cp_ca);
        etMg = (EditText) findViewById(R.id.input_cp_mg);
        etNa = (EditText) findViewById(R.id.input_cp_na);
        etK = (EditText) findViewById(R.id.input_cp_k);
        etTotalBase = (EditText) findViewById(R.id.input_cp_totalBase);
        etCEC = (EditText) findViewById(R.id.input_cp_cec);
        etBS = (EditText) findViewById(R.id.input_cp_bs);
        etESP = (EditText) findViewById(R.id.input_cp_esp);

        //---------SOIL FERTILITY----------------------
        etOrganicCarbon = (EditText) findViewById(R.id.input_sfp_organicCarbon);
        et_MaN_nitrogen = (EditText) findViewById(R.id.input_sfp_nitrogen);
        et_MaN_phosphorus = (EditText) findViewById(R.id.input_sfp_phosphorus);
        et_MaN_potassium = (EditText) findViewById(R.id.input_sfp_potassium);
        et_MiN_sulphur = (EditText) findViewById(R.id.input_sfp_sulphur);
        et_MiN_zinc = (EditText) findViewById(R.id.input_sfp_zinc);
        et_MiN_copper = (EditText) findViewById(R.id.input_sfp_copper);
        et_MiN_iron = (EditText) findViewById(R.id.input_sfp_iron);
        et_MiN_manganese = (EditText) findViewById(R.id.input_sfp_manganese);

        //------------ADD PHOTOS-------------------------
        //for choosing img btn
        east_browse = (Button) findViewById(R.id.east_img_btn);
        west_browse = (Button) findViewById(R.id.west_img_btn);
        north_browse = (Button) findViewById(R.id.north_img_btn);
        south_browse = (Button) findViewById(R.id.south_img_btn);

        //for img logo
        east_imgView = (ImageView) findViewById(R.id.east_img);
        west_imgView = (ImageView) findViewById(R.id.west_img);
        north_imgView = (ImageView) findViewById(R.id.north_img);
        south_imgView = (ImageView) findViewById(R.id.south_img);

        //------------------------------date_time----------------------------
        dateText = (TextView) findViewById(R.id.lbl_full_date);
        timeText = (TextView) findViewById(R.id.lbl_full_time);

        //----------DATE-----------
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy");
        String dateString = sdf.format(date);
        dateText.setText(dateString);
        //-----------TIME-----------
        long time = System.currentTimeMillis();
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeString = timeFormat.format(date);
        timeText.setText(timeString);

        //---------------------------------------------LIST OF TOPOSHEET 250K ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        topo250kAdapter = ArrayAdapter.createFromResource(this, R.array.array_toposheet_250k, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        topo250kAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e toposheet250kAdapter
        topo250kSpinner.setAdapter(topo250kAdapter);
        //now when selecting any toposheet 250k from spinner
        topo250kSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selectedTopo250k variable
                selectedTopo250k = topo250kSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//---------------------------------------------LIST OF TOPOSHEET 50K ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        topo50kAdapter = ArrayAdapter.createFromResource(this, R.array.array_toposheet_50k, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        topo50kAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e toposheet50kAdapter
        topo50kSpinner.setAdapter(topo50kAdapter);
        //now when selecting any toposheet 50k from spinner
        topo50kSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }
                //storing selected option from spinner save into selectedTopo50k variable
                selectedTopo50k = topo50kSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//---------------------------------------------LIST OF STATE AND DISTRICT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.array_indian_state, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        stateAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e stateAdapter
        stateSpinner.setAdapter(stateAdapter);

        //now when selecting any state from spinner
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }


                //storing the selected state to "selectedState" variable
                selectedState = stateSpinner.getSelectedItem().toString();
                int parentID = parent.getId();
                if (parentID == R.id.spin_select_state) {
                    switch (selectedState) {
                        case "Select Your State":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_default_districts, R.layout.spinner_item);
                            break;
                        case "Andhra Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_andhra_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Arunachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_arunachal_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Assam":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_assam_districts, R.layout.spinner_item);
                            break;
                        case "Bihar":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_bihar_districts, R.layout.spinner_item);
                            break;
                        case "Chhattisgarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_chhattisgarh_districts, R.layout.spinner_item);
                            break;
                        case "Goa":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_goa_districts, R.layout.spinner_item);
                            break;
                        case "Gujarat":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_gujarat_districts, R.layout.spinner_item);
                            break;
                        case "Haryana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_haryana_districts, R.layout.spinner_item);
                            break;
                        case "Himachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_himachal_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Jharkhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_jharkhand_districts, R.layout.spinner_item);
                            break;
                        case "Karnataka":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_karnataka_districts, R.layout.spinner_item);
                            break;
                        case "Kerala":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_kerala_districts, R.layout.spinner_item);
                            break;
                        case "Madhya Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_madhya_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Maharashtra":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_maharashtra_districts, R.layout.spinner_item);
                            break;
                        case "Manipur":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_manipur_districts, R.layout.spinner_item);
                            break;
                        case "Meghalaya":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_meghalaya_districts, R.layout.spinner_item);
                            break;
                        case "Mizoram":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_mizoram_districts, R.layout.spinner_item);
                            break;
                        case "Nagaland":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_nagaland_districts, R.layout.spinner_item);
                            break;
                        case "Odisha":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_odisha_districts, R.layout.spinner_item);
                            break;
                        case "Punjab":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_punjab_districts, R.layout.spinner_item);
                            break;
                        case "Rajasthan":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_rajasthan_districts, R.layout.spinner_item);
                            break;
                        case "Sikkim":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_sikkim_districts, R.layout.spinner_item);
                            break;
                        case "Tamil Nadu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_tamil_nadu_districts, R.layout.spinner_item);
                            break;
                        case "Telangana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_telangana_districts, R.layout.spinner_item);
                            break;
                        case "Tripura":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_tripura_districts, R.layout.spinner_item);
                            break;
                        case "Uttar Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_uttar_pradesh_districts, R.layout.spinner_item);
                            break;
                        case "Uttarakhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_uttarakhand_districts, R.layout.spinner_item);
                            break;
                        case "West Bengal":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_west_bengal_districts, R.layout.spinner_item);
                            break;
                        case "Andaman and Nicobar Islands":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_andaman_nicobar_districts, R.layout.spinner_item);
                            break;
                        case "Chandigarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_chandigarh_districts, R.layout.spinner_item);
                            break;
                        case "Dadra and Nagar Haveli":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_item);
                            break;
                        case "Daman and Diu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_daman_diu_districts, R.layout.spinner_item);
                            break;
                        case "Delhi":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_delhi_districts, R.layout.spinner_item);
                            break;
                        case "Jammu and Kashmir":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_jammu_kashmir_districts, R.layout.spinner_item);
                            break;
                        case "Lakshadweep":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_lakshadweep_districts, R.layout.spinner_item);
                            break;
                        case "Ladakh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_ladakh_districts, R.layout.spinner_item);
                            break;
                        case "Puducherry":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_puducherry_districts, R.layout.spinner_item);
                            break;
                        default:
                            break;
                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);     // Specify the layout to use when the list of choices appears
                    districtSpinner.setAdapter(districtAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //now when selecting any district for spinner
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //for disabling the first option of state spinner
                selectedDistrict = districtSpinner.getSelectedItem().toString();
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //--------------------------SOIL SITE PARAMETERS----------------------------
//-------------------------------LIST OF PHYSIOGRAPHIC CATEGORY ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        physioCatgyAdapter = ArrayAdapter.createFromResource(this, R.array.array_physiographic_category, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        physioCatgyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e physioCatgyAdapter
        physioCatgySpinner.setAdapter(physioCatgyAdapter);
        //now when selecting any physiographic Category from spinner
        physioCatgySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedPhysioCatgy = physioCatgySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//---------------------------------------------LIST OF SUB-PHYSIOGRAPHIC UNIT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        subPhysioUnitAdapter = ArrayAdapter.createFromResource(this, R.array.array_subPhysiographic_unit, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        subPhysioUnitAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter i.e physioCatgyAdapter
        subPhysioUnitSpinner.setAdapter(subPhysioUnitAdapter);
        //now when selecting any physiographic Category from spinner
        subPhysioUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //for disabling the first option of state spinner
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setTextSize(18);
                }

                //storing selected option from spinner save into selectedSubPhysioUnit variable
                selectedSubPhysioUnit = subPhysioUnitSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //----------------PRESENT LAND USE-------------------------
        //-------------------------------LIST OF FOREST ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        forestAdapter = ArrayAdapter.createFromResource(this, R.array.array_forest, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        forestAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        forestSpinner.setAdapter(forestAdapter);
        //now when selecting any option from spinner
        forestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedForest = forestSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF CULTIVATED ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        cultivatedAdapter = ArrayAdapter.createFromResource(this, R.array.array_cultivated, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        cultivatedAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        cultivatedSpinner.setAdapter(cultivatedAdapter);
        //now when selecting any option from spinner
        cultivatedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedCultivated = cultivatedSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF TERRACES ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        terracesAdapter = ArrayAdapter.createFromResource(this, R.array.array_terraces, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        terracesAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        terracesSpinner.setAdapter(terracesAdapter);
        //now when selecting any option from spinner
        terracesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedTerraces = terracesSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF PASTURE LAND ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        pastureLandAdapter = ArrayAdapter.createFromResource(this, R.array.array_pasture_land, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        pastureLandAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        pastureLandSpinner.setAdapter(pastureLandAdapter);
        //now when selecting any option from spinner
        pastureLandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedPastureLand = pastureLandSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF DEGRADED CULTURABLE ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        degrCulturableAdapter = ArrayAdapter.createFromResource(this, R.array.array_degraded_culturable, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        degrCulturableAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        degrCulturableSpinner.setAdapter(degrCulturableAdapter);
        //now when selecting any option from spinner
        degrCulturableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedDegCulturable = degrCulturableSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF DEGRADED UNCULTURABLE ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        degrunCulturableAdapter = ArrayAdapter.createFromResource(this, R.array.array_degraded_unculturable, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        degrunCulturableAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        degrunCulturableSpinner.setAdapter(degrunCulturableAdapter);
        //now when selecting any option from spinner
        degrunCulturableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedDegUncultrable = degrunCulturableSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND CAPABILITY CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landCapaClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_capability_class, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landCapaClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landCapaClassSpinner.setAdapter(landCapaClassAdapter);
        //now when selecting any option from spinner
        landCapaClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandCapClass = landCapaClassSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND CAPABILITY SUB-CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landCapaSubClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_capability_subclass, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landCapaSubClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landCapaSubClassSpinner.setAdapter(landCapaSubClassAdapter);
        //now when selecting any option from spinner
        landCapaSubClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandCapsubClass = landCapaSubClassSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND IRRIGABILITY CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landIrrigaClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_irrigability_class, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landIrrigaClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landIrrigaClassSpinner.setAdapter(landIrrigaClassAdapter);
        //now when selecting any option from spinner
        landIrrigaClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandIrrigaClass = landIrrigaClassSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF LAND IRRIGABILITY SUB-CLASS ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        landIrrigaSubClassAdapter = ArrayAdapter.createFromResource(this, R.array.array_irrigability_subclass, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        landIrrigaSubClassAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        landIrrigaSubClassSpinner.setAdapter(landIrrigaSubClassAdapter);
        //now when selecting any option from spinner
        landIrrigaSubClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedLandIrrigaSubclass = landIrrigaSubClassSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//-------------------------------LIST OF CROP ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        cropAdapter = ArrayAdapter.createFromResource(this, R.array.array_crop, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        cropAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        cropSpinner.setAdapter(cropAdapter);
        //now when selecting any option from spinner
        cropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedCrop = cropSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //-------------------------------LIST OF MANAGEMENT PRACTICES ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
        managPractAdapter = ArrayAdapter.createFromResource(this, R.array.array_management_practice, R.layout.spinner_item);

        //now specifying the layout to use when list of choice is appears
        managPractAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        //now simply populate the spinner using the adapter
        managPractSpinner.setAdapter(managPractAdapter);
        //now when selecting any option from spinner
        managPractSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                selectedManagPractice = managPractSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //-------------------MORPHOLOGICAL PRAMETERS--------------------------
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------------------------------ADD PHOTOS---------------------------------------
        //--------------------------BUTTON OPERATIONS-------------------------------------------------
//-----------------------------------------------EAST IMG-----------------------------------------------------------
        east_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(LocationDetails.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent, "Browse East Image"), 1);
                                someActivityResultLauncherEst.launch(Intent.createChooser(intent, "Browse East Image"));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(LocationDetails.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //-----------------------------------------------WEST IMG-----------------------------------------------------------
        west_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(LocationDetails.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent, "Browse West Image"), 2);
                                someActivityResultLauncherWst.launch(Intent.createChooser(intent, "Browse West Image"));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(LocationDetails.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //-----------------------------------------------NORTH IMG-----------------------------------------------------------
        north_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(LocationDetails.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 3);
                                someActivityResultLauncherNth.launch(Intent.createChooser(intent, "Browse North Image"));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(LocationDetails.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
//
//        //-----------------------------------------------SOUTH IMG-----------------------------------------------------------
        south_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(LocationDetails.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 4);
                                someActivityResultLauncherSth.launch(Intent.createChooser(intent, "Browse South Image"));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(LocationDetails.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //--------------BUTTON METHOD FOR GETTING CURRENT LOCATION DETAILS---------------
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps

                    OnGPS();
                } else {
                    //GPS is already On then

                    getLocation();
                }
            }
        });

    }

    private void getLocation() {
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(LocationDetails.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationDetails.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps != null) {
                double lat = LocationGps.getLatitude();
                double longi = LocationGps.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                lbl_latitude.setText(latitude);
                lbl_longitude.setText(longitude);
            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                lbl_latitude.setText(latitude);
                lbl_longitude.setText(longitude);
            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                lbl_latitude.setText(latitude);
                lbl_longitude.setText(longitude);
            } else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //-----------HOME ICON on action bar-------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //---------onclicking go to home dashboard-------------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu:
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------EAST-------------------
    private void east_encodeBitmapImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        east_encodeImgStr = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    //-------------------------WEST-----------------------
    private void west_encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        west_encodeImgStr = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    //-----------------------------NORTH--------------------
    private void north_encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        north_encodeImgStr = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    //-------------------------------SOUTH------------------------
    private void south_encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        south_encodeImgStr = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    //----------BUTTON METHOD FOR SAVING DATA------------------
    public void SubmitBtn1(View view) {
        // below is for progress dialog box
        //Initialinzing the progress Dialog
        progressDialog = new ProgressDialog(LocationDetails.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // for project id
        sharedPreferencesId = getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        projectID = sharedPreferencesId.getString(KEY_PROJECT_ID, "");

        // for user id
        sharedPreferencesId = getSharedPreferences(SHARED_PRE_NAME2, Context.MODE_PRIVATE);
        userid = Integer.parseInt(sharedPreferencesId.getString(KEY_ID, ""));

        surveyorname = etsurveyorName.getText().toString().trim();
        tehsilname = ettehsilName.getText().toString().trim();
        blockname = etblockName.getText().toString().trim();
        villagename = etvillageName.getText().toString().trim();
        elevation = etelevation.getText().toString().trim();
        projectProfileID = etprojProfileID.getText().toString().trim();
        remark = etremark.getText().toString().trim();
        date = dateText.getText().toString().trim();
        time = timeText.getText().toString().trim();

        //---soil site parameters-----
        geology = etGeology.getText().toString().trim();
        parentMaterial = etParentMaterial.getText().toString().trim();
        climate = etClimate.getText().toString().trim();
        rainfall = etRainfall.getText().toString().trim();
        topographyLandform = etTopographyLandform.getText().toString().trim();
        naturalVegetation = etNaturalVegetation.getText().toString().trim();

        //------present land use------
        phaseSurface = etPhaseSurface.getText().toString().trim();
        phaseSubstratum = etPhaseSubstratum.getText().toString().trim();
        pluremark = etpluRemark.getText().toString().trim();
        yield = etYield.getText().toString().trim();
        croppingSystem = etCroppingSys.getText().toString().trim();

        //---------project details---------
        horizon = etHorizon.getText().toString().trim();
        depth = etDepth.getText().toString().trim();

        //-----morphological parameters--------
        cf_vol = et_CF_Vol.getText().toString().trim();
        of_size = et_OF_Size.getText().toString().trim();
        of_abundance = et_OF_Abundance.getText().toString().trim();
        of_nature = et_OF_Nature.getText().toString().trim();
        of_samplebagno = et_OF_SampleBagNo.getText().toString().trim();
        of_additionalnotes = et_OF_AdditionalNotes.getText().toString().trim();

        //--------physical parameters--------
        PD_sand = etPD_sand.getText().toString().trim();
        PD_silt = etPD_silt.getText().toString().trim();
        PD_clay = etPD_clay.getText().toString().trim();
        PD_textural = etPD_textural.getText().toString().trim();
        PD_density = etPD_density.getText().toString().trim();
        WR_33kpa = etWR_33kpa.getText().toString().trim();
        WR_1500kpa = etWR_1500kpa.getText().toString().trim();
        WR_awc = etWR_awc.getText().toString().trim();
        WR_pawc = etWR_pawc.getText().toString().trim();
        WR_gravimetric = etWR_gravimetric.getText().toString().trim();

        //---------chemical parameters-----------
        PH = etPH.getText().toString().trim();
        EC = etEC.getText().toString().trim();
        OC = etOC.getText().toString().trim();
        CaCo = etCaCo.getText().toString().trim();
        Ca = etCa.getText().toString().trim();
        Mg = etMg.getText().toString().trim();
        Na = etNa.getText().toString().trim();
        K = etK.getText().toString().trim();
        TotalBase = etTotalBase.getText().toString().trim();
        CEC = etCEC.getText().toString().trim();
        BS = etBS.getText().toString().trim();
        ESP = etESP.getText().toString().trim();

        //---------soil fertility-----------------
        organicCarbon = etOrganicCarbon.getText().toString().trim();
        MaN_nitrogen = et_MaN_nitrogen.getText().toString().trim();
        MaN_phosphorus = et_MaN_phosphorus.getText().toString().trim();
        MaN_potassium = et_MaN_potassium.getText().toString().trim();
        MiN_sulphur = et_MiN_sulphur.getText().toString().trim();
        MiN_zinc = et_MiN_zinc.getText().toString().trim();
        MiN_copper = et_MiN_copper.getText().toString().trim();
        MiN_iron = et_MiN_iron.getText().toString().trim();
        MiN_manganese = et_MiN_manganese.getText().toString().trim();

        //----------validating the text fields if empty or not.-------------------//commenting only for next page codig
        if (TextUtils.isEmpty(surveyorname)) {
            progressDialog.dismiss();
            etsurveyorName.setError("Please enter Surveyor name");
            Toast.makeText(this, "Surveyor name required...", Toast.LENGTH_LONG).show();
            return;
        } else if (selectedState.equals("Select Your State")) {
            progressDialog.dismiss();
            Toast.makeText(this, "State required...", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(latitude)) {
            progressDialog.dismiss();
            lbl_latitude.setError("Please enter latitude");
            Toast.makeText(this, "Latitude required...", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(longitude)) {
            progressDialog.dismiss();
            lbl_longitude.setError("Please enter logitude");
            Toast.makeText(this, "Longitude required...", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(projectProfileID)) {
            progressDialog.dismiss();
            etprojProfileID.setError("Please enter Project profile id");
            Toast.makeText(this, "Project profile id required...", Toast.LENGTH_LONG).show();
            return;
        } else {
            // calling method to add data
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //reseting all data back to empty after sending to database
                    //---------add photos------------
                    east_imgView.setImageResource(R.drawable.select_img);
                    west_imgView.setImageResource(R.drawable.select_img);
                    north_imgView.setImageResource(R.drawable.select_img);
                    south_imgView.setImageResource(R.drawable.select_img);

                    //below code is for live server
                    try {
                        if (TextUtils.equals(response, "1")) {
                            progressDialog.dismiss();
                            Toast.makeText(LocationDetails.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LocationDetails.this, "Data stored Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LocationDetails.this, SoilDataReport.class));
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast toast = Toast.makeText(getApplicationContext(), "Images required...", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 20);
                    toast.show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("userid", String.valueOf(userid));
                    data.put("projectID", projectID);
                    data.put("surveyorname", surveyorname);
                    data.put("state", selectedState);
                    data.put("district", selectedDistrict);
                    data.put("tehsil", tehsilname);
                    data.put("block", blockname);
                    data.put("villagename", villagename);
                    data.put("toposheet250k", selectedTopo250k);
                    data.put("toposheet50k", selectedTopo50k);
                    data.put("date", date);
                    data.put("time", time);
                    data.put("latitude", latitude);
                    data.put("longitude", longitude);
                    data.put("elevation", elevation);
                    data.put("projectProfileID", projectProfileID);
                    data.put("ld_remark", remark);

                    data.put("physiographicCategory", selectedPhysioCatgy);
                    data.put("subPhysiographicUnit", selectedSubPhysioUnit);
                    data.put("geology", geology);
                    data.put("parentMaterial", parentMaterial);
                    data.put("climate", climate);
                    data.put("rainfall", rainfall);
                    data.put("topographyLandformType", topographyLandform);

                    data.put("slopeGradiant", selectedSlopeGradient);
                    data.put("slopeLength", selectedSlopeLength);
                    data.put("erosion", selectedErosion);
                    data.put("runoff", selectedRunoff);
                    data.put("drainage", selectedDrainage);
                    data.put("groundWaterDepth", selectedGroundWaterDepth);
                    data.put("flooding", selectedFlooding);
                    data.put("saltAlkali", selectedSalt_Alkali);
                    data.put("ssp_pH", selectedPH);
                    data.put("ssp_Ec", selectedEc);
                    data.put("stoneSize", selectedStoneSize);
                    data.put("stoiness", selectedStoiness);
                    data.put("rockOutcrops", selectedRockOutcrops);
                    data.put("naturalVegetation", naturalVegetation);

                    data.put("forest", selectedForest);
                    data.put("cultivated", selectedCultivated);
                    data.put("terraces", selectedTerraces);
                    data.put("pastureLand", selectedPastureLand);
                    data.put("degradedCulturable", selectedDegCulturable);
                    data.put("degradedUnCulturable", selectedDegUncultrable);
                    data.put("landCapabilityClass", selectedLandCapClass);
                    data.put("landCapabilitySubClass", selectedLandCapsubClass);
                    data.put("landIrrigabilityClass", selectedLandIrrigaClass);
                    data.put("landIrrigabilitySubClass", selectedLandIrrigaSubclass);
                    data.put("crop", selectedCrop);
                    data.put("managementPractice", selectedManagPractice);
                    data.put("phaseSurface", phaseSurface);
                    data.put("phaseSubstratum", phaseSubstratum);
                    data.put("plu_remark", pluremark);
                    data.put("yield", yield);
                    data.put("croppingSystem", croppingSystem);

                    data.put("horizon", horizon);
                    data.put("depth", depth);

                    //--------BOUNDARY-----------------
                    data.put("b_distinctness", selectedDistinctness);
                    data.put("b_topography", selectedTopography);
                    data.put("b_diagnostic", selectedDiagnostic);
                    data.put("b_matrixColour", selectedMatrixCol);
                    //--------MOTTLE COLOUR-----------------
                    data.put("mc_abundance", selected_MC_Abundance);
                    data.put("mc_size", selected_MC_Size);
                    data.put("mc_contrast", selected_MC_Contrast);
                    data.put("mc_texture", selected_MC_Texture);
                    //--------COARSE FRAGMENTS-----------------
                    data.put("cf_size", selected_CF_Size);
                    data.put("cf_vol", cf_vol);
                    //--------STRUCTURE-----------------
                    data.put("str_size", selected_Str_Size);
                    data.put("str_grade", selected_Str_Grade);
                    data.put("str_type", selected_Str_Type);
                    //--------CONSISTENCE-----------------
                    data.put("con_d", selected_Con_D);
                    data.put("con_m", selected_Con_M);
                    data.put("con_w", selected_Con_W);
                    //--------POROSITY-----------------
                    data.put("poros_s", selected_Poros_S);
                    data.put("poros_q", selected_Poros_Q);
                    //--------CUTANS-----------------
                    data.put("cutans_ty", selected_Cutans_Ty);
                    data.put("cutans_th", selected_Cutans_Th);
                    data.put("cutans_q", selected_Cutans_Q);
                    //--------NODULES-----------------
                    data.put("nodules_s", selected_Nodules_S);
                    data.put("nodules_q", selected_Nodules_Q);
                    //--------ROOTS-----------------
                    data.put("roots_s", selected_Roots_S);
                    data.put("roots_q", selected_Roots_Q);
                    data.put("roots_effervescence", selected_Roots_Effervescence);
                    //--------OTHER FEATURES-----------------
                    data.put("of_size", of_size);
                    data.put("of_abundance", of_abundance);
                    data.put("of_nature", of_nature);
                    data.put("of_samplebagno", of_samplebagno);
                    data.put("of_additionalnotes", of_additionalnotes);

                    data.put("sand", PD_sand);
                    data.put("silt", PD_silt);
                    data.put("clay", PD_clay);
                    data.put("USDA_textural_class", PD_textural);
                    data.put("bulk_density", PD_density);
                    data.put("33kPa", WR_33kpa);
                    data.put("1500kPa", WR_1500kpa);
                    data.put("AWC", WR_awc);
                    data.put("PAWC", WR_pawc);
                    data.put("gravimetric_moisture", WR_gravimetric);

                    data.put("cp_pH", PH);
                    data.put("cp_EC", EC);
                    data.put("OC", OC);
                    data.put("CaCo", CaCo);
                    data.put("Ca", Ca);
                    data.put("Mg", Mg);
                    data.put("Na", Na);
                    data.put("K", K);
                    data.put("totalBase", TotalBase);
                    data.put("CEC", CEC);
                    data.put("BS", BS);
                    data.put("ESP", ESP);

                    data.put("organicCarbon", organicCarbon);
                    data.put("MaN_nitrogen", MaN_nitrogen);
                    data.put("MaN_phosphorus", MaN_phosphorus);
                    data.put("MaN_potassium", MaN_potassium);
                    data.put("MiN_sulphur", MiN_sulphur);
                    data.put("MiN_zinc", MiN_zinc);
                    data.put("MiN_copper", MiN_copper);
                    data.put("MiN_iron", MiN_iron);
                    data.put("MiN_manganese", MiN_manganese);

                    data.put("east_image", east_encodeImgStr);
                    data.put("west_image", west_encodeImgStr);
                    data.put("north_image", north_encodeImgStr);
                    data.put("south_image", south_encodeImgStr);
                    data.put("admin_status", admin_status);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    }

}
