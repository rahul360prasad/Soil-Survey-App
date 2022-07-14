package com.example.soilsurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateLocationDetails extends AppCompatActivity {

    //url_projectID for fetching particular project id details from database1!!
    String url_projectID = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    String update_fields_url = "http://14.139.123.73:9090/web/NBSS/php/updateRecordsData.php";
//    String update_fields_url = "http://10.0.0.145/login/updateRecordsData.php"; //for local server

    EditText update_surveyor_name, update_tehsil, update_block, update_village, update_elevation, update_ld_remark,
            update_geology, update_parentMaterial, update_climate, update_rainfall, update_topoLandform, update_naturalVegetation,
            update_state, update_district, update_topo250k, update_topo50k, update_physioCategory, update_subPhysioUnit,
            update_slopeGradiant, update_slopeLength, update_erosion, update_runoff, update_drainage, update_gndwaterdepth,
            update_flooding, update_saltAlkali, update_ssp_pH, update_ssp_Ec, update_stoneSize, update_stoiness, update_rockOutcrops,
            update_plu_forest, update_plu_cultivated, update_plu_terraces, update_plu_pastureLand, update_plu_degradedCulturable,
            update_plu_degradedUnCulturable, update_plu_phaseSurface, update_plu_phaseSubstratum, update_plu_landCapabilityClass,
            update_plu_landCapabilitysubClass, update_plu_landIrrigabilityClass, update_plu_landIrrigabilitysubClass, update_plu_remark,
            update_plu_crops, update_plu_yields, update_plu_mangPractices, update_plu_croppingSyst,
            update_pd_horizon, update_pd_depth, update_mpbd_distinctness, update_mpbd_topography, update_mpbd_daigonisticHorizon, update_mpbd_matrixColor,
            update_mpmc_abundance, update_mpmc_size, update_mpmc_contrast, update_mpmc_texture, update_mpcf_size, update_mpcf_vol,
            update_mpstr_size, update_mpstr_grade, update_mpstr_type, update_mpcons_d, update_mpcons_m, update_mpcons_w,
            update_mpporo_s, update_mpporo_q, update_mpcutans_ty, update_mpcutans_th, update_mpcutans_q, update_mpnodules_s, update_mpnodules_q,
            update_mproots_s, update_mproots_q, update_mproots_effervescence, update_mpotf_s, update_mpotf_abundance, update_mpotf_nature, update_mpotf_samplebag, update_mpotf_addnotes,
            update_pppsd_sand, update_pppsd_slit, update_pppsd_clay, update_pppsd_texturalClass, update_pppsd_bulkDensity,
            update_ppwr_33kPa, update_ppwr_1500kPa, update_ppwr_awc, update_ppwr_pawc, update_ppwr_graviMoisture,
            update_cp_pH, update_cp_ec, update_cp_oc, update_cp_caco, update_cp_ca, update_cp_mg, update_cp_na, update_cp_k, update_cp_totalBase, update_cp_cec, update_cp_bs, update_cp_esp,
            update_sfp_organicCarbon, update_sfpmn_nitrogen, update_sfpmn_phosphorus, update_sfpmn_potassium, update_sfpmn_sulphur, update_sfpmn_zinc, update_sfpmn_copper, update_sfpmn_iron, update_sfpmn_manganese;

    TextView update_date, update_time, update_latitude, update_longitude, update_projprofileID;

    String edtUpdate_surveyor_name, edtUpdate_tehsil, edtUpdate_block, edtUpdate_village, edtUpdate_elevation, edtUpdate_ld_remark,
            edtUpdate_geology, edtUpdate_parentMaterial, edtUpdate_climate, edtUpdate_rainfall, edtUpdate_topoLandform, edtUpdate_naturalVegetation,
            edtUpdate_state, edtUpdate_district, edtUpdate_topo250k, edtUpdate_topo50k, edtUpdate_physioCategory, edtUpdate_subPhysioUnit,
            edtUpdate_slopeGradiant, edtUpdate_slopeLength, edtUpdate_erosion, edtUpdate_runoff, edtUpdate_drainage, edtUpdate_gndwaterdepth,
            edtUpdate_flooding, edtUpdate_saltAlkali, edtUpdate_ssp_pH, edtUpdate_ssp_Ec, edtUpdate_stoneSize, edtUpdate_stoiness, edtUpdate_rockOutcrops,
            edtUpdate_plu_forest, edtUpdate_plu_cultivated, edtUpdate_plu_terraces, edtUpdate_plu_pastureLand, edtUpdate_plu_degradedCulturable,
            edtUpdate_plu_degradedUnCulturable, edtUpdate_plu_phaseSurface, edtUpdate_plu_phaseSubstratum, edtUpdate_plu_landCapabilityClass,
            edtUpdate_plu_landCapabilitysubClass, edtUpdate_plu_landIrrigabilityClass, edtUpdate_plu_landIrrigabilitysubClass, edtUpdate_plu_remark,
            edtUpdate_plu_crops, edtUpdate_plu_yields, edtUpdate_plu_mangPractices, edtUpdate_plu_croppingSyst,
            edtUpdate_pd_horizon, edtUpdate_pd_depth, edtUpdate_mpbd_distinctness, edtUpdate_mpbd_topography, edtUpdate_mpbd_daigonisticHorizon, edtUpdate_mpbd_matrixColor,
            edtUpdate_mpmc_abundance, edtUpdate_mpmc_size, edtUpdate_mpmc_contrast, edtUpdate_mpmc_texture, edtUpdate_mpcf_size, edtUpdate_mpcf_vol,
            edtUpdate_mpstr_size, edtUpdate_mpstr_grade, edtUpdate_mpstr_type, edtUpdate_mpcons_d, edtUpdate_mpcons_m, edtUpdate_mpcons_w,
            edtUpdate_mpporo_s, edtUpdate_mpporo_q, edtUpdate_mpcutans_ty, edtUpdate_mpcutans_th, edtUpdate_mpcutans_q, edtUpdate_mpnodules_s, edtUpdate_mpnodules_q,
            edtUpdate_mproots_s, edtUpdate_mproots_q, edtUpdate_mproots_effervescence, edtUpdate_mpotf_s, edtUpdate_mpotf_abundance, edtUpdate_mpotf_nature, edtUpdate_mpotf_samplebag, edtUpdate_mpotf_addnotes,
            edtUpdate_pppsd_sand, edtUpdate_pppsd_slit, edtUpdate_pppsd_clay, edtUpdate_pppsd_texturalClass, edtUpdate_pppsd_bulkDensity,
            edtUpdate_ppwr_33kPa, edtUpdate_ppwr_1500kPa, edtUpdate_ppwr_awc, edtUpdate_ppwr_pawc, edtUpdate_ppwr_graviMoisture,
            edtUpdate_cp_pH, edtUpdate_cp_ec, edtUpdate_cp_oc, edtUpdate_cp_caco, edtUpdate_cp_ca, edtUpdate_cp_mg, edtUpdate_cp_na, edtUpdate_cp_k, edtUpdate_cp_totalBase, edtUpdate_cp_cec, edtUpdate_cp_bs, edtUpdate_cp_esp,
            edtUpdate_sfp_organicCarbon, edtUpdate_sfpmn_nitrogen, edtUpdate_sfpmn_phosphorus, edtUpdate_sfpmn_potassium, edtUpdate_sfpmn_sulphur, edtUpdate_sfpmn_zinc, edtUpdate_sfpmn_copper, edtUpdate_sfpmn_iron, edtUpdate_sfpmn_manganese;

     private String update_selectedProjectID, update_selectedProjectProfileID;
    //declaring variable for storing selected state, district, tehsil, block, topo250k, topo50k
    private String selectedState, selectedDistrict, selectedTehsil, selectedBlock, selectedTopo250k, selectedTopo50k,
            selectedPhysioCatgy, selectedSubPhysioUnit, selectedSlopeGradient, selectedSlopeLength, selectedErosion,
            selectedRunoff, selectedDrainage, selectedGroundWaterDepth, selectedFlooding, selectedSalt_Alkali, selectedEc,
            selectedPH, selectedStoneSize, selectedStoiness, selectedRockOutcrops,
            selectedForest, selectedCultivated, selectedTerraces, selectedPastureLand, selectedDegCulturable, selectedDegUncultrable,
            selectedLandCapClass, selectedLandCapsubClass, selectedLandIrrigaClass, selectedLandIrrigaSubclass, selectedCrop, selectedManagPractice;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location_details);

        //spinner
//        update_state= (Spinner) findViewById(R.id.spin_uptselect_state);
//        update_district= (Spinner) findViewById(R.id.spin_uptselect_districts);
//        update_topo250k= (Spinner) findViewById(R.id.spin_upttopo250k);
//        update_topo50k= (Spinner) findViewById(R.id.spin_upttopo50k);
//        update_physioCategory= (Spinner) findViewById(R.id.spin_ssp_uptphycategory);
//        update_subPhysioUnit= (Spinner) findViewById(R.id.spin_ssp_uptsubphyunit);
//        update_slopeGradiant= (Spinner) findViewById(R.id.spin_ssp_uptslopeGradiant);
//        update_slopeLength= (Spinner) findViewById(R.id.spin_ssp_uptslopeLength);
//        update_erosion= (Spinner) findViewById(R.id.spin_ssp_upterosion);
//        update_runoff= (Spinner) findViewById(R.id.spin_ssp_uptrunoff);
//        update_drainage= (Spinner) findViewById(R.id.spin_ssp_uptdrainage);
//        update_gndwaterdepth= (Spinner) findViewById(R.id.spin_ssp_uptgndwaterdpth);
//        update_flooding= (Spinner) findViewById(R.id.spin_ssp_uptflooding);
//        update_saltAlkali= (Spinner) findViewById(R.id.spin_ssp_uptsaltAlkali);
//        update_ssp_pH= (Spinner) findViewById(R.id.spin_ssp_uptpH);
//        update_ssp_Ec= (Spinner) findViewById(R.id.spin_ssp_uptEc);
//        update_stoneSize= (Spinner) findViewById(R.id.spin_ssp_uptstoneSize);
//        update_stoiness= (Spinner) findViewById(R.id.spin_ssp_uptstoiness);
//        update_rockOutcrops= (Spinner) findViewById(R.id.spin_ssp_uptrockOutcrops);


        //textview
        update_date = (TextView) findViewById(R.id.lbl_uptdate_disable);
        update_time = (TextView) findViewById(R.id.lbl_upttime_disable);
        update_latitude = (TextView) findViewById(R.id.lbl_uptlatitude_disable);
        update_longitude = (TextView) findViewById(R.id.lbl_uptlongitude_disable);
        update_projprofileID = (TextView) findViewById(R.id.lbl_uptprojprofileid);

        // edittext
        update_surveyor_name = (EditText) findViewById(R.id.upt_edtsurveyor_name);
        update_tehsil = (EditText) findViewById(R.id.edt_upttehsil_name);
        update_block = (EditText) findViewById(R.id.edt_uptblock_name);
        update_village = (EditText) findViewById(R.id.edt_uptvillage_name);
        update_elevation = (EditText) findViewById(R.id.edt_uptelevation);
        update_ld_remark = (EditText) findViewById(R.id.edt_ld_uptremark);
        update_geology = (EditText) findViewById(R.id.edt_ssp_uptgeology);
        update_parentMaterial = (EditText) findViewById(R.id.edt_ssp_uptparentmaterial);
        update_climate = (EditText) findViewById(R.id.edt_ssp_uptclimate);
        update_rainfall = (EditText) findViewById(R.id.edt_ssp_uptrainfall);
        update_topoLandform = (EditText) findViewById(R.id.edt_ssp_upttopoLandform);
        update_naturalVegetation = (EditText) findViewById(R.id.edt_ssp_uptnaturalveg);

        update_state = (EditText) findViewById(R.id.spin_uptselect_state);
        update_district = (EditText) findViewById(R.id.spin_uptselect_districts);
        update_topo250k = (EditText) findViewById(R.id.spin_upttopo250k);
        update_topo50k = (EditText) findViewById(R.id.spin_upttopo50k);
        update_physioCategory = (EditText) findViewById(R.id.spin_ssp_uptphycategory);
        update_subPhysioUnit = (EditText) findViewById(R.id.spin_ssp_uptsubphyunit);
        update_slopeGradiant = (EditText) findViewById(R.id.spin_ssp_uptslopeGradiant);
        update_slopeLength = (EditText) findViewById(R.id.spin_ssp_uptslopeLength);
        update_erosion = (EditText) findViewById(R.id.spin_ssp_upterosion);
        update_runoff = (EditText) findViewById(R.id.spin_ssp_uptrunoff);
        update_drainage = (EditText) findViewById(R.id.spin_ssp_uptdrainage);
        update_gndwaterdepth = (EditText) findViewById(R.id.spin_ssp_uptgndwaterdpth);
        update_flooding = (EditText) findViewById(R.id.spin_ssp_uptflooding);
        update_saltAlkali = (EditText) findViewById(R.id.spin_ssp_uptsaltAlkali);
        update_ssp_pH = (EditText) findViewById(R.id.spin_ssp_uptpH);
        update_ssp_Ec = (EditText) findViewById(R.id.spin_ssp_uptEc);
        update_stoneSize = (EditText) findViewById(R.id.spin_ssp_uptstoneSize);
        update_stoiness = (EditText) findViewById(R.id.spin_ssp_uptstoiness);
        update_rockOutcrops = (EditText) findViewById(R.id.spin_ssp_uptrockOutcrops);

        update_plu_forest = (EditText) findViewById(R.id.edt_plu_uptforest);
        update_plu_cultivated = (EditText) findViewById(R.id.edt_plu_cultivated);
        update_plu_terraces = (EditText) findViewById(R.id.edt_plu_terraces);
        update_plu_pastureLand = (EditText) findViewById(R.id.edt_plu_pastureland);
        update_plu_degradedCulturable = (EditText) findViewById(R.id.edt_plu_degradedculturable);
        update_plu_degradedUnCulturable = (EditText) findViewById(R.id.edt_plu_degradedunculturable);
        update_plu_phaseSurface = (EditText) findViewById(R.id.edt_plu_phasesurface);
        update_plu_phaseSubstratum = (EditText) findViewById(R.id.edt_plu_phaseSubstratum);
        update_plu_landCapabilityClass = (EditText) findViewById(R.id.edt_plu_landcapabilityclass);
        update_plu_landCapabilitysubClass = (EditText) findViewById(R.id.edt_plu_landcapabilitysubclass);
        update_plu_landIrrigabilityClass = (EditText) findViewById(R.id.edt_plu_landirrigabilityclass);
        update_plu_landIrrigabilitysubClass = (EditText) findViewById(R.id.edt_plu_landirrigabilitysubclass);
        update_plu_remark = (EditText) findViewById(R.id.edt_plu_classification_remark);
        update_plu_crops = (EditText) findViewById(R.id.edt_plu_crops);
        update_plu_yields = (EditText) findViewById(R.id.edt_plu_yields);
        update_plu_mangPractices = (EditText) findViewById(R.id.edt_plu_mngtpractices);
        update_plu_croppingSyst = (EditText) findViewById(R.id.edt_plu_cropingSystem);
        update_pd_horizon = (EditText) findViewById(R.id.edt_plu_horizon);
        update_pd_depth = (EditText) findViewById(R.id.edt_plu_depth);
        update_mpbd_distinctness = (EditText) findViewById(R.id.edt_mpb_distinctness);
        update_mpbd_topography = (EditText) findViewById(R.id.edt_mpb_topography);
        update_mpbd_daigonisticHorizon = (EditText) findViewById(R.id.edt_mpb_daigonisticHorizon);
        update_mpbd_matrixColor = (EditText) findViewById(R.id.edt_mpb_matrixColor);
        update_mpmc_abundance = (EditText) findViewById(R.id.edt_mp_abundance_mc);
        update_mpmc_size = (EditText) findViewById(R.id.edt_mp_size_mc);
        update_mpmc_contrast = (EditText) findViewById(R.id.edt_mp_contrast_mc);
        update_mpmc_texture = (EditText) findViewById(R.id.edt_mp_texture_mc);
        update_mpcf_size = (EditText) findViewById(R.id.edt_mp_size_cf);
        update_mpcf_vol = (EditText) findViewById(R.id.edt_mp_vol_cf);
        update_mpstr_size = (EditText) findViewById(R.id.edt_mp_size_str);
        update_mpstr_grade = (EditText) findViewById(R.id.edt_mp_grade_str);
        update_mpstr_type = (EditText) findViewById(R.id.edt_mp_type_str);
        update_mpcons_d = (EditText) findViewById(R.id.edt_mp_dry_consistence);
        update_mpcons_m = (EditText) findViewById(R.id.edt_mp_moist_consistence);
        update_mpcons_w = (EditText) findViewById(R.id.edt_mp_wet_consistence);
        update_mpporo_s = (EditText) findViewById(R.id.edt_mp_size_porosity);
        update_mpporo_q = (EditText) findViewById(R.id.edt_mp_quantity_porosity);
        update_mpcutans_ty = (EditText) findViewById(R.id.edt_mp_argillan_cutans);
        update_mpcutans_th = (EditText) findViewById(R.id.edt_mp_thickness_cutans);
        update_mpcutans_q = (EditText) findViewById(R.id.edt_mp_quantity_cutans);
        update_mpnodules_s = (EditText) findViewById(R.id.edt_mp_size_nodules);
        update_mpnodules_q = (EditText) findViewById(R.id.edt_mp_quantity_nodules);
        update_mproots_s = (EditText) findViewById(R.id.edt_mp_size_roots);
        update_mproots_q = (EditText) findViewById(R.id.edt_mp_quantity_roots);
        update_mproots_effervescence = (EditText) findViewById(R.id.edt_mp_effervescence_roots);
        update_mpotf_s = (EditText) findViewById(R.id.edt_mp_size_otf);
        update_mpotf_abundance = (EditText) findViewById(R.id.edt_mp_abundance_otf);
        update_mpotf_nature = (EditText) findViewById(R.id.edt_mp_nature_otf);
        update_mpotf_samplebag = (EditText) findViewById(R.id.edt_mp_samplebagno_otf);
        update_mpotf_addnotes = (EditText) findViewById(R.id.edt_mp_additionalnotes_otf);
        update_pppsd_sand = (EditText) findViewById(R.id.edt_pp_sand_psd);
        update_pppsd_slit = (EditText) findViewById(R.id.edt_pp_slit_psd);
        update_pppsd_clay = (EditText) findViewById(R.id.edt_pp_clay_psd);
        update_pppsd_texturalClass = (EditText) findViewById(R.id.edt_pp_texturalclass_psd);
        update_pppsd_bulkDensity = (EditText) findViewById(R.id.edt_pp_bulkdensity_psd);
        update_ppwr_33kPa = (EditText) findViewById(R.id.edt_pp_33kpa_wr);
        update_ppwr_1500kPa = (EditText) findViewById(R.id.edt_pp_kpa1500_wr);
        update_ppwr_awc = (EditText) findViewById(R.id.edt_pp_awc_wr);
        update_ppwr_pawc = (EditText) findViewById(R.id.edt_pp_pawc_wr);
        update_ppwr_graviMoisture = (EditText) findViewById(R.id.edt_pp_gravimetricMoisture_wr);
        update_cp_pH = (EditText) findViewById(R.id.edt_cp_pH_cp);
        update_cp_ec = (EditText) findViewById(R.id.edt_cp_EC_cp);
        update_cp_oc = (EditText) findViewById(R.id.edt_cp_OC_cp);
        update_cp_caco = (EditText) findViewById(R.id.edt_cp_CaCo_cp);
        update_cp_ca = (EditText) findViewById(R.id.edt_cp_Ca_cp);
        update_cp_mg = (EditText) findViewById(R.id.edt_cp_Mg_cp);
        update_cp_na = (EditText) findViewById(R.id.edt_cp_Na_cp);
        update_cp_k = (EditText) findViewById(R.id.edt_cp_K_cp);
        update_cp_totalBase = (EditText) findViewById(R.id.edt_cp_totalBase_cp);
        update_cp_cec = (EditText) findViewById(R.id.edt_cp_CEC_cp);
        update_cp_bs = (EditText) findViewById(R.id.edt_cp_BS_cp);
        update_cp_esp = (EditText) findViewById(R.id.edt_cp_ESP_cp);
        update_sfp_organicCarbon = (EditText) findViewById(R.id.edt_sfp_organicCarbon_sfp);
        update_sfpmn_nitrogen = (EditText) findViewById(R.id.edt_sfp_nitrogen_mjn);
        update_sfpmn_phosphorus = (EditText) findViewById(R.id.edt_sfp_phosphorus_mjn);
        update_sfpmn_potassium = (EditText) findViewById(R.id.edt_sfp_potassium_mjn);
        update_sfpmn_sulphur = (EditText) findViewById(R.id.edt_sfp_sulphur_min);
        update_sfpmn_zinc = (EditText) findViewById(R.id.edt_sfp_zinc_min);
        update_sfpmn_copper = (EditText) findViewById(R.id.edt_sfp_copper_min);
        update_sfpmn_iron = (EditText) findViewById(R.id.edt_sfp_iron_min);
        update_sfpmn_manganese = (EditText) findViewById(R.id.edt_sfp_manganese_min);

        //update button
//        update_locationDetail_btn= (Button) findViewById(R.id.upt_locationDetail_btn);

        //receiving intent params from updateRecords file
        Intent intent = getIntent();
        update_selectedProjectID = intent.getExtras().getString("update_selectedProjectID");
        update_selectedProjectProfileID = intent.getExtras().getString("update_selectedProjectProfileID");

        Log.d(";laksdf", "hasdu---" + update_selectedProjectID);
        Log.d(";laksdf", "hasdu2222---" + update_selectedProjectProfileID);

        //---------------------------------------------LIST OF STATE AND DISTRICT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
//        stateAdapter = ArrayAdapter.createFromResource(this, R.array.array_indian_state, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        stateAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter i.e stateAdapter
//        update_state.setAdapter(stateAdapter);
//
//        //now when selecting any state from spinner
//        update_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//
//                //storing the selected state to "selectedState" variable
//                selectedState = update_state.getSelectedItem().toString();
//                int parentID = parent.getId();
//                if (parentID == R.id.spin_select_state) {
//                    switch (selectedState) {
//                        case "Select Your State":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_default_districts, R.layout.spinner_item);
//                            break;
//                        case "Andhra Pradesh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_andhra_pradesh_districts, R.layout.spinner_item);
//                            break;
//                        case "Arunachal Pradesh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_arunachal_pradesh_districts, R.layout.spinner_item);
//                            break;
//                        case "Assam":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_assam_districts, R.layout.spinner_item);
//                            break;
//                        case "Bihar":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_bihar_districts, R.layout.spinner_item);
//                            break;
//                        case "Chhattisgarh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_chhattisgarh_districts, R.layout.spinner_item);
//                            break;
//                        case "Goa":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_goa_districts, R.layout.spinner_item);
//                            break;
//                        case "Gujarat":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_gujarat_districts, R.layout.spinner_item);
//                            break;
//                        case "Haryana":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_haryana_districts, R.layout.spinner_item);
//                            break;
//                        case "Himachal Pradesh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_himachal_pradesh_districts, R.layout.spinner_item);
//                            break;
//                        case "Jharkhand":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_jharkhand_districts, R.layout.spinner_item);
//                            break;
//                        case "Karnataka":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_karnataka_districts, R.layout.spinner_item);
//                            break;
//                        case "Kerala":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_kerala_districts, R.layout.spinner_item);
//                            break;
//                        case "Madhya Pradesh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_madhya_pradesh_districts, R.layout.spinner_item);
//                            break;
//                        case "Maharashtra":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_maharashtra_districts, R.layout.spinner_item);
//                            break;
//                        case "Manipur":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_manipur_districts, R.layout.spinner_item);
//                            break;
//                        case "Meghalaya":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_meghalaya_districts, R.layout.spinner_item);
//                            break;
//                        case "Mizoram":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_mizoram_districts, R.layout.spinner_item);
//                            break;
//                        case "Nagaland":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_nagaland_districts, R.layout.spinner_item);
//                            break;
//                        case "Odisha":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_odisha_districts, R.layout.spinner_item);
//                            break;
//                        case "Punjab":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_punjab_districts, R.layout.spinner_item);
//                            break;
//                        case "Rajasthan":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_rajasthan_districts, R.layout.spinner_item);
//                            break;
//                        case "Sikkim":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_sikkim_districts, R.layout.spinner_item);
//                            break;
//                        case "Tamil Nadu":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_tamil_nadu_districts, R.layout.spinner_item);
//                            break;
//                        case "Telangana":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_telangana_districts, R.layout.spinner_item);
//                            break;
//                        case "Tripura":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_tripura_districts, R.layout.spinner_item);
//                            break;
//                        case "Uttar Pradesh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_uttar_pradesh_districts, R.layout.spinner_item);
//                            break;
//                        case "Uttarakhand":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_uttarakhand_districts, R.layout.spinner_item);
//                            break;
//                        case "West Bengal":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_west_bengal_districts, R.layout.spinner_item);
//                            break;
//                        case "Andaman and Nicobar Islands":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_andaman_nicobar_districts, R.layout.spinner_item);
//                            break;
//                        case "Chandigarh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_chandigarh_districts, R.layout.spinner_item);
//                            break;
//                        case "Dadra and Nagar Haveli":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_item);
//                            break;
//                        case "Daman and Diu":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_daman_diu_districts, R.layout.spinner_item);
//                            break;
//                        case "Delhi":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_delhi_districts, R.layout.spinner_item);
//                            break;
//                        case "Jammu and Kashmir":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_jammu_kashmir_districts, R.layout.spinner_item);
//                            break;
//                        case "Lakshadweep":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_lakshadweep_districts, R.layout.spinner_item);
//                            break;
//                        case "Ladakh":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_ladakh_districts, R.layout.spinner_item);
//                            break;
//                        case "Puducherry":
//                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
//                                    R.array.array_puducherry_districts, R.layout.spinner_item);
//                            break;
//                        default:
//                            break;
//                    }
//                    districtAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);     // Specify the layout to use when the list of choices appears
//                    update_district.setAdapter(districtAdapter);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        //now when selecting any district for spinner
//        update_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                selectedDistrict = update_district.getSelectedItem().toString();
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//---------------------------------------------LIST OF TOPOSHEET 250K ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
//        topo250kAdapter = ArrayAdapter.createFromResource(this, R.array.array_toposheet_250k, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        topo250kAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter i.e toposheet250kAdapter
//        update_topo250k.setAdapter(topo250kAdapter);
//        //now when selecting any toposheet 250k from spinner
//        update_topo250k.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selectedTopo250k variable
//                selectedTopo250k = update_topo250k.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //---------------------------------------------LIST OF TOPOSHEET 50K ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
//        topo50kAdapter = ArrayAdapter.createFromResource(this, R.array.array_toposheet_50k, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        topo50kAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter i.e toposheet50kAdapter
//        update_topo50k.setAdapter(topo50kAdapter);
//        //now when selecting any toposheet 50k from spinner
//        update_topo50k.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//                //storing selected option from spinner save into selectedTopo50k variable
//                selectedTopo50k = update_topo50k.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //--------------------------SOIL SITE PARAMETERS----------------------------
//-------------------------------LIST OF PHYSIOGRAPHIC CATEGORY ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
//        physioCatgyAdapter = ArrayAdapter.createFromResource(this, R.array.array_physiographic_category, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        physioCatgyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter i.e physioCatgyAdapter
//        update_physioCategory.setAdapter(physioCatgyAdapter);
//        //now when selecting any physiographic Category from spinner
//        update_physioCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selectedPhysioCatgy variable
//                selectedPhysioCatgy = update_physioCategory.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });


//---------------------------------------------LIST OF SUB-PHYSIOGRAPHIC UNIT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
//        subPhysioUnitAdapter = ArrayAdapter.createFromResource(this, R.array.array_subPhysiographic_unit, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        subPhysioUnitAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter i.e physioCatgyAdapter
//        update_subPhysioUnit.setAdapter(subPhysioUnitAdapter);
//        //now when selecting any physiographic Category from spinner
//        update_subPhysioUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selectedSubPhysioUnit variable
//                selectedSubPhysioUnit = update_subPhysioUnit.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//-------------------------------LIST OF SLOPE OF GRADIANT ON SPINNER-----------------------------------------------------------------
        //populate arrayAdapter using string array and spinner layout that we will define
//        slopeGradientAdapter = ArrayAdapter.createFromResource(this, R.array.array_slope_gradient, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        slopeGradientAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_slopeGradiant.setAdapter(slopeGradientAdapter);
//        //now when selecting any option from spinner
//        update_slopeGradiant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selectedPhysioCatgy variable
//                selectedSlopeGradient = update_slopeGradiant.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF SLOPE OF LENGTH ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        slopeLengthAdapter = ArrayAdapter.createFromResource(this, R.array.array_slope_length, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        slopeLengthAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_slopeLength.setAdapter(slopeLengthAdapter);
//        //now when selecting any option from spinner
//        update_slopeLength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selectedPhysioCatgy variable
//                selectedSlopeLength = update_slopeLength.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF EROSION ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        erosionAdapter = ArrayAdapter.createFromResource(this, R.array.array_errosion, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        erosionAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_erosion.setAdapter(erosionAdapter);
//        //now when selecting any option from spinner
//        update_erosion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedErosion = update_erosion.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF RUNOFF ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        runoffAdapter = ArrayAdapter.createFromResource(this, R.array.array_runoff, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        runoffAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_runoff.setAdapter(runoffAdapter);
//        //now when selecting any option from spinner
//        update_runoff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedRunoff = update_runoff.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF DRAINAGE ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        drainageAdapter = ArrayAdapter.createFromResource(this, R.array.array_drainage, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        drainageAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_drainage.setAdapter(drainageAdapter);
//        //now when selecting any option from spinner
//        update_drainage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedDrainage = update_drainage.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF GROUND WATER DEPTH ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        groundWaterDepthAdapter = ArrayAdapter.createFromResource(this, R.array.array_ground_water, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        groundWaterDepthAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_gndwaterdepth.setAdapter(groundWaterDepthAdapter);
//        //now when selecting any option from spinner
//        update_gndwaterdepth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedGroundWaterDepth = update_gndwaterdepth.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF FLOODING ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        floodingAdapter = ArrayAdapter.createFromResource(this, R.array.array_flodding, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        floodingAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_flooding.setAdapter(floodingAdapter);
//        //now when selecting any option from spinner
//        update_flooding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedFlooding = update_flooding.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF SALT/ALKALI ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        salt_AlkaliAdapter = ArrayAdapter.createFromResource(this, R.array.array_salt_alkali, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        salt_AlkaliAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_saltAlkali.setAdapter(salt_AlkaliAdapter);
//        //now when selecting any option from spinner
//        update_saltAlkali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedSalt_Alkali = update_saltAlkali.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF pH ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        pHAdapter = ArrayAdapter.createFromResource(this, R.array.array_ph, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        pHAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_ssp_pH.setAdapter(pHAdapter);
//        //now when selecting any option from spinner
//        update_ssp_pH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedPH = update_ssp_pH.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF Ec ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        ecAdapter = ArrayAdapter.createFromResource(this, R.array.array_ec, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        ecAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_ssp_Ec.setAdapter(ecAdapter);
//        //now when selecting any option from spinner
//        update_ssp_Ec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedEc = update_ssp_Ec.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF STONE SIZE ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        stoneSizeAdapter = ArrayAdapter.createFromResource(this, R.array.array_stone_size, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        stoneSizeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_stoneSize.setAdapter(stoneSizeAdapter);
//        //now when selecting any option from spinner
//        update_stoneSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedStoneSize = update_stoneSize.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF STOINESS ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        stoinessAdapter = ArrayAdapter.createFromResource(this, R.array.array_stoiness, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        stoinessAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_stoiness.setAdapter(stoinessAdapter);
//        //now when selecting any option from spinner
//        update_stoiness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedStoiness = update_stoiness.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
////-------------------------------LIST OF ROCK OUTCROPS ON SPINNER-----------------------------------------------------------------
//        //populate arrayAdapter using string array and spinner layout that we will define
//        rockOutcropsAdapter = ArrayAdapter.createFromResource(this, R.array.array_rock_outcrops, R.layout.spinner_item);
//
//        //now specifying the layout to use when list of choice is appears
//        rockOutcropsAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//
//        //now simply populate the spinner using the adapter
//        update_rockOutcrops.setAdapter(rockOutcropsAdapter);
//        //now when selecting any option from spinner
//        update_rockOutcrops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                //for disabling the first option of state spinner
//                TextView tv = (TextView) view;
//                if (position == 0) {
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                    tv.setTextSize(18);
//                }
//
//                //storing selected option from spinner save into selected... variable
//                selectedRockOutcrops = update_rockOutcrops.getSelectedItem().toString();
//                //when selecting the state name district spinner is populated
//                //districtSpinner = (Spinner) findViewById(R.id.spin_select_district);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //fetching details of location details
        retrieveData();

        //-----------------HIDING THE ACTION BAR-----------------------------------------------------------
        try {
//            this.getSupportActionBar().hide();
            getSupportActionBar().setTitle("");
        } catch (NullPointerException e) {
        }
    }
    //------------------------------------END OF ON CREATE METHOD-----------------------

    //fetch data with their existing values
    private void retrieveData() {

        StringRequest request = new StringRequest(Request.Method.POST, url_projectID +
                "?TYPE=projectID_report&projID=" + update_selectedProjectID +
                "&projectProfileID=" + update_selectedProjectProfileID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showJSON_projectidwise(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateLocationDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void showJSON_projectidwise(String response) {
        ArrayList<HashMap<String, String>> id_list = new ArrayList<HashMap<String, String>>();
        id_list.clear();
        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray("results");
            JSONArray resultList = new JSONArray(response);
            if (resultList.length() > 0) {
                for (int i = 0; i < resultList.length(); i++) {
                    JSONObject jo = resultList.getJSONObject(i);

//                    -----------------------FOR LOCATION---------------
                    String surveyorname = jo.getString("surveyorname");
                    String state = jo.getString("state");
                    String district = jo.getString("district");
                    String tehsil = jo.getString("tehsil");
                    String block = jo.getString("block");
                    String villagename = jo.getString("villagename");
                    String toposheet250k = jo.getString("toposheet250k");
                    String toposheet50k = jo.getString("toposheet50k");
                    String time = jo.getString("time");
                    String date = jo.getString("date");
                    String latitude = jo.getString("latitude");
                    String longitude = jo.getString("longitude");
                    String elevation = jo.getString("elevation");
                    String projectProfileID = jo.getString("projectProfileID");
                    String physiographicCategory = jo.getString("physiographicCategory");
                    String subPhysiographicUnit = jo.getString("subPhysiographicUnit");
                    String geology = jo.getString("geology");
                    String parentMaterial = jo.getString("parentMaterial");
                    String climate = jo.getString("climate");
                    String rainfall = jo.getString("rainfall");
                    String topographyLandformType = jo.getString("topographyLandformType");
                    String slopeGradiant = jo.getString("slopeGradiant");
                    String slopeLength = jo.getString("slopeLength");
                    String erosion = jo.getString("erosion");
                    String runoff = jo.getString("runoff");
                    String drainage = jo.getString("drainage");
                    String groundWaterDepth = jo.getString("groundWaterDepth");
                    String flooding = jo.getString("flooding");
                    String saltAlkali = jo.getString("saltAlkali");
                    String pHssp = jo.getString("pH");
                    String Ecssp = jo.getString("Ec");
                    String stoneSize = jo.getString("stoneSize");
                    String stoiness = jo.getString("stoiness");
                    String rockOutcrops = jo.getString("rockOutcrops");
                    String naturalVegetation = jo.getString("naturalVegetation");

                    String forest = jo.getString("forest");
                    String cultivated = jo.getString("cultivated");
                    String terraces = jo.getString("terraces");
                    String pastureLand = jo.getString("pastureLand");
                    String degradedCulturable = jo.getString("degradedCulturable");
                    String degradedUnCulturable = jo.getString("degradedUnCulturable");
                    String phaseSurface = jo.getString("phaseSurface");
                    String phaseSubstratum = jo.getString("phaseSubstratum");
                    String landCapabilityClass = jo.getString("landCapabilityClass");
                    String landCapabilitySubClass = jo.getString("landCapabilitySubClass");
                    String landIrrigabilityClass = jo.getString("landIrrigabilityClass");
                    String landIrrigabilitySubClass = jo.getString("landIrrigabilitySubClass");
                    String remarks = jo.getString("remarks");
                    String crop = jo.getString("crop");
                    String yield = jo.getString("yield");
                    String managementPractice = jo.getString("managementPractice");
                    String croppingSystem = jo.getString("croppingSystem");
                    String horizon = jo.getString("horizon");
                    String depth = jo.getString("depth");
                    String b_distinctness = jo.getString("b_distinctness");
                    String b_topography = jo.getString("b_topography");
                    String b_diagnostic = jo.getString("b_diagnostic");
                    String b_matrixColour = jo.getString("b_matrixColour");
                    String mc_abundance = jo.getString("mc_abundance");
                    String mc_size = jo.getString("mc_size");
                    String mc_contrast = jo.getString("mc_contrast");
                    String mc_texture = jo.getString("mc_texture");
                    String cf_size = jo.getString("cf_size");
                    String cf_vol = jo.getString("cf_vol");
                    String str_size = jo.getString("str_size");
                    String str_grade = jo.getString("str_grade");
                    String str_type = jo.getString("str_type");
                    String con_d = jo.getString("con_d");
                    String con_m = jo.getString("con_m");
                    String con_w = jo.getString("con_w");
                    String poros_s = jo.getString("poros_s");
                    String poros_q = jo.getString("poros_q");
                    String cutans_ty = jo.getString("cutans_ty");
                    String cutans_th = jo.getString("cutans_th");
                    String cutans_q = jo.getString("cutans_q");
                    String nodules_s = jo.getString("nodules_s");
                    String nodules_q = jo.getString("nodules_q");
                    String roots_s = jo.getString("roots_s");
                    String roots_q = jo.getString("roots_q");
                    String roots_effervescence = jo.getString("roots_effervescence");
                    String of_size = jo.getString("of_size");
                    String of_abundance = jo.getString("of_abundance");
                    String of_nature = jo.getString("of_nature");
                    String of_samplebagno = jo.getString("of_samplebagno");
                    String of_additionalnotes = jo.getString("of_additionalnotes");
                    String sand = jo.getString("sand");
                    String silt = jo.getString("silt");
                    String clay = jo.getString("clay");
                    String USDA_textural_class = jo.getString("USDA_textural_class");
                    String bulk_density = jo.getString("bulk_density");
                    String wr_33kPa = jo.getString("wr_33kPa");
                    String wr_1500kPa = jo.getString("wr_1500kPa");
                    String AWC = jo.getString("AWC");
                    String PAWC = jo.getString("PAWC");
                    String gravimetric_moisture = jo.getString("gravimetric_moisture");
                    String cp_pH = jo.getString("cp_pH");
                    String cp_EC = jo.getString("cp_EC");
                    String OC = jo.getString("OC");
                    String CaCo = jo.getString("CaCo");
                    String Ca = jo.getString("Ca");
                    String Mg = jo.getString("Mg");
                    String Na = jo.getString("Na");
                    String K = jo.getString("K");
                    String totalBase = jo.getString("totalBase");
                    String CEC = jo.getString("CEC");
                    String BS = jo.getString("BS");
                    String ESP = jo.getString("ESP");
                    String organicCarbon = jo.getString("organicCarbon");
                    String MaN_nitrogen = jo.getString("MaN_nitrogen");
                    String MaN_phosphorus = jo.getString("MaN_phosphorus");
                    String MaN_potassium = jo.getString("MaN_potassium");
                    String MiN_sulphur = jo.getString("MiN_sulphur");
                    String MiN_zinc = jo.getString("MiN_zinc");
                    String MiN_copper = jo.getString("MiN_copper");
                    String MiN_iron = jo.getString("MiN_iron");
                    String MiN_manganese = jo.getString("MiN_manganese");

//                    Log.d("surveyorname","surveyorname---------"+state);
                    update_surveyor_name.setText(surveyorname);
//                    update_state.setText(state);
                    update_state.setText(state);
                    update_district.setText(district);
                    update_tehsil.setText(tehsil);
                    update_block.setText(block);
                    update_village.setText(villagename);
                    update_topo250k.setText(toposheet250k);
                    update_topo50k.setText(toposheet50k);
                    update_time.setText(time);
                    update_date.setText(date);
                    update_latitude.setText(latitude);
                    update_longitude.setText(longitude);
                    update_elevation.setText(elevation);
                    update_projprofileID.setText(projectProfileID);
                    update_physioCategory.setText(physiographicCategory);
                    update_subPhysioUnit.setText(subPhysiographicUnit);
                    update_geology.setText(geology);
                    update_parentMaterial.setText(parentMaterial);
                    update_climate.setText(climate);
                    update_rainfall.setText(rainfall);
                    update_topoLandform.setText(topographyLandformType);
                    update_slopeGradiant.setText(slopeGradiant);
                    update_slopeLength.setText(slopeLength);
                    update_erosion.setText(erosion);
                    update_runoff.setText(runoff);
                    update_drainage.setText(drainage);
                    update_gndwaterdepth.setText(groundWaterDepth);
                    update_flooding.setText(flooding);
                    update_saltAlkali.setText(saltAlkali);
                    update_ssp_pH.setText(pHssp);
                    update_ssp_Ec.setText(Ecssp);
                    update_stoneSize.setText(stoneSize);
                    update_stoiness.setText(stoiness);
                    update_rockOutcrops.setText(rockOutcrops);
                    update_naturalVegetation.setText(naturalVegetation);

                    update_plu_forest.setText(forest);
                    update_plu_cultivated.setText(cultivated);
                    update_plu_terraces.setText(terraces);
                    update_plu_pastureLand.setText(pastureLand);
                    update_plu_degradedCulturable.setText(degradedCulturable);
                    update_plu_degradedUnCulturable.setText(degradedUnCulturable);
                    update_plu_phaseSurface.setText(phaseSurface);
                    update_plu_phaseSubstratum.setText(phaseSubstratum);
                    update_plu_landCapabilityClass.setText(landCapabilityClass);
                    update_plu_landCapabilitysubClass.setText(landCapabilitySubClass);
                    update_plu_landIrrigabilityClass.setText(landIrrigabilityClass);
                    update_plu_landIrrigabilitysubClass.setText(landIrrigabilitySubClass);
                    update_plu_remark.setText(remarks);
                    update_plu_crops.setText(crop);
                    update_plu_yields.setText(yield);
                    update_plu_mangPractices.setText(managementPractice);
                    update_plu_croppingSyst.setText(croppingSystem);
                    update_pd_horizon.setText(horizon);
                    update_pd_depth.setText(depth);
                    update_mpbd_distinctness.setText(b_distinctness);
                    update_mpbd_topography.setText(b_topography);
                    update_mpbd_daigonisticHorizon.setText(b_diagnostic);
                    update_mpbd_matrixColor.setText(b_matrixColour);
                    update_mpmc_abundance.setText(mc_abundance);
                    update_mpmc_size.setText(mc_size);
                    update_mpmc_contrast.setText(mc_contrast);
                    update_mpmc_texture.setText(mc_texture);
                    update_mpcf_size.setText(cf_size);
                    update_mpcf_vol.setText(cf_vol);
                    update_mpstr_size.setText(str_size);
                    update_mpstr_grade.setText(str_grade);
                    update_mpstr_type.setText(str_type);
                    update_mpcons_d.setText(con_d);
                    update_mpcons_m.setText(con_m);
                    update_mpcons_w.setText(con_w);
                    update_mpporo_s.setText(poros_s);
                    update_mpporo_q.setText(poros_q);
                    update_mpcutans_ty.setText(cutans_ty);
                    update_mpcutans_th.setText(cutans_th);
                    update_mpcutans_q.setText(cutans_q);
                    update_mpnodules_s.setText(nodules_s);
                    update_mpnodules_q.setText(nodules_q);
                    update_mproots_s.setText(roots_s);
                    update_mproots_q.setText(roots_q);
                    update_mproots_effervescence.setText(roots_effervescence);
                    update_mpotf_s.setText(of_size);
                    update_mpotf_abundance.setText(of_abundance);
                    update_mpotf_nature.setText(of_nature);
                    update_mpotf_samplebag.setText(of_samplebagno);
                    update_mpotf_addnotes.setText(of_additionalnotes);
                    update_pppsd_sand.setText(sand);
                    update_pppsd_slit.setText(silt);
                    update_pppsd_clay.setText(clay);
                    update_pppsd_texturalClass.setText(USDA_textural_class);
                    update_pppsd_bulkDensity.setText(bulk_density);
                    update_ppwr_33kPa.setText(wr_33kPa);
                    update_ppwr_1500kPa.setText(wr_1500kPa);
                    update_ppwr_awc.setText(AWC);
                    update_ppwr_pawc.setText(PAWC);
                    update_ppwr_graviMoisture.setText(gravimetric_moisture);
                    update_cp_pH.setText(cp_pH);
                    update_cp_ec.setText(cp_EC);
                    update_cp_oc.setText(OC);
                    update_cp_caco.setText(CaCo);
                    update_cp_ca.setText(Ca);
                    update_cp_mg.setText(Mg);
                    update_cp_na.setText(Na);
                    update_cp_k.setText(K);
                    update_cp_totalBase.setText(totalBase);
                    update_cp_cec.setText(CEC);
                    update_cp_bs.setText(BS);
                    update_cp_esp.setText(ESP);
                    update_sfp_organicCarbon.setText(organicCarbon);
                    update_sfpmn_nitrogen.setText(MaN_nitrogen);
                    update_sfpmn_phosphorus.setText(MaN_phosphorus);
                    update_sfpmn_potassium.setText(MaN_potassium);
                    update_sfpmn_sulphur.setText(MiN_sulphur);
                    update_sfpmn_zinc.setText(MiN_zinc);
                    update_sfpmn_copper.setText(MiN_copper);
                    update_sfpmn_iron.setText(MiN_iron);
                    update_sfpmn_manganese.setText(MiN_manganese);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //--------------when pressing hardware back button----------------
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), UpdateRecords.class));
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

    //update page button
    public void updateBtn(View view) {
        // below is for progress dialog box
        //Initialinzing the progress Dialog
        ProgressDialog progressDialog = new ProgressDialog(UpdateLocationDetails.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // dismiss the dialog
        // and exit the exit

        //getting the values for boxes
        edtUpdate_surveyor_name = update_surveyor_name.getText().toString().trim();
        edtUpdate_tehsil = update_tehsil.getText().toString().trim();
        edtUpdate_block = update_block.getText().toString().trim();
        edtUpdate_village = update_village.getText().toString().trim();
        edtUpdate_elevation = update_elevation.getText().toString().trim();
        edtUpdate_ld_remark = update_ld_remark.getText().toString().trim();
        edtUpdate_geology = update_geology.getText().toString().trim();
        edtUpdate_parentMaterial = update_parentMaterial.getText().toString().trim();
        edtUpdate_climate = update_climate.getText().toString().trim();
        edtUpdate_rainfall = update_rainfall.getText().toString().trim();
        edtUpdate_topoLandform = update_topoLandform.getText().toString().trim();
        edtUpdate_naturalVegetation = update_naturalVegetation.getText().toString().trim();
        edtUpdate_state = update_state.getText().toString().trim();
        edtUpdate_district = update_district.getText().toString().trim();
        edtUpdate_topo250k = update_topo250k.getText().toString().trim();
        edtUpdate_topo50k = update_topo50k.getText().toString().trim();
        edtUpdate_physioCategory = update_physioCategory.getText().toString().trim();
        edtUpdate_subPhysioUnit = update_subPhysioUnit.getText().toString().trim();
        edtUpdate_slopeGradiant = update_slopeGradiant.getText().toString().trim();
        edtUpdate_slopeLength = update_slopeLength.getText().toString().trim();
        edtUpdate_erosion = update_erosion.getText().toString().trim();
        edtUpdate_runoff = update_runoff.getText().toString().trim();
        edtUpdate_drainage = update_drainage.getText().toString().trim();
        edtUpdate_gndwaterdepth = update_gndwaterdepth.getText().toString().trim();
        edtUpdate_flooding = update_flooding.getText().toString().trim();
        edtUpdate_saltAlkali = update_saltAlkali.getText().toString().trim();
        edtUpdate_ssp_pH = update_ssp_pH.getText().toString().trim();
        edtUpdate_ssp_Ec = update_ssp_Ec.getText().toString().trim();
        edtUpdate_stoneSize = update_stoneSize.getText().toString().trim();
        edtUpdate_stoiness = update_stoiness.getText().toString().trim();
        edtUpdate_rockOutcrops = update_rockOutcrops.getText().toString().trim();
        Log.d("edtUpdate_surveyor_name", "edtUpdate_surveyor_name---------" + edtUpdate_surveyor_name);

        edtUpdate_plu_forest = update_plu_forest.getText().toString().trim();
        edtUpdate_plu_cultivated = update_plu_cultivated.getText().toString().trim();
        edtUpdate_plu_terraces = update_plu_terraces.getText().toString().trim();
        edtUpdate_plu_pastureLand = update_plu_pastureLand.getText().toString().trim();
        edtUpdate_plu_degradedCulturable = update_plu_degradedCulturable.getText().toString().trim();
        edtUpdate_plu_degradedUnCulturable = update_plu_degradedUnCulturable.getText().toString().trim();
        edtUpdate_plu_phaseSurface = update_plu_phaseSurface.getText().toString().trim();
        edtUpdate_plu_phaseSubstratum = update_plu_phaseSubstratum.getText().toString().trim();
        edtUpdate_plu_landCapabilityClass = update_plu_landCapabilityClass.getText().toString().trim();
        edtUpdate_plu_landCapabilitysubClass = update_plu_landCapabilitysubClass.getText().toString().trim();
        edtUpdate_plu_landIrrigabilityClass = update_plu_landIrrigabilityClass.getText().toString().trim();
        edtUpdate_plu_landIrrigabilitysubClass = update_plu_landIrrigabilitysubClass.getText().toString().trim();
        edtUpdate_plu_remark = update_plu_remark.getText().toString().trim();
        edtUpdate_plu_crops = update_plu_crops.getText().toString().trim();
        edtUpdate_plu_yields = update_plu_yields.getText().toString().trim();
        edtUpdate_plu_mangPractices = update_plu_mangPractices.getText().toString().trim();
        edtUpdate_plu_croppingSyst = update_plu_croppingSyst.getText().toString().trim();
        edtUpdate_pd_horizon = update_pd_horizon.getText().toString().trim();
        edtUpdate_pd_depth = update_pd_depth.getText().toString().trim();
        edtUpdate_mpbd_distinctness = update_mpbd_distinctness.getText().toString().trim();
        edtUpdate_mpbd_topography = update_mpbd_topography.getText().toString().trim();
        edtUpdate_mpbd_daigonisticHorizon = update_mpbd_daigonisticHorizon.getText().toString().trim();
        edtUpdate_mpbd_matrixColor = update_mpbd_matrixColor.getText().toString().trim();
        edtUpdate_mpmc_abundance = update_mpmc_abundance.getText().toString().trim();
        edtUpdate_mpmc_size = update_mpmc_size.getText().toString().trim();
        edtUpdate_mpmc_contrast = update_mpmc_contrast.getText().toString().trim();
        edtUpdate_mpmc_texture = update_mpmc_texture.getText().toString().trim();
        edtUpdate_mpcf_size = update_mpcf_size.getText().toString().trim();
        edtUpdate_mpcf_vol = update_mpcf_vol.getText().toString().trim();
        edtUpdate_mpstr_size = update_mpstr_size.getText().toString().trim();
        edtUpdate_mpstr_grade = update_mpstr_grade.getText().toString().trim();
        edtUpdate_mpstr_type = update_mpstr_type.getText().toString().trim();
        edtUpdate_mpcons_d = update_mpcons_d.getText().toString().trim();
        edtUpdate_mpcons_m = update_mpcons_m.getText().toString().trim();
        edtUpdate_mpcons_w = update_mpcons_w.getText().toString().trim();
        edtUpdate_mpporo_s = update_mpporo_s.getText().toString().trim();
        edtUpdate_mpporo_q = update_mpporo_q.getText().toString().trim();
        edtUpdate_mpcutans_ty = update_mpcutans_ty.getText().toString().trim();
        edtUpdate_mpcutans_th = update_mpcutans_th.getText().toString().trim();
        edtUpdate_mpcutans_q = update_mpcutans_q.getText().toString().trim();
        edtUpdate_mpnodules_s = update_mpnodules_s.getText().toString().trim();
        edtUpdate_mpnodules_q = update_mpnodules_q.getText().toString().trim();
        edtUpdate_mproots_s = update_mproots_s.getText().toString().trim();
        edtUpdate_mproots_q = update_mproots_q.getText().toString().trim();
        edtUpdate_mproots_effervescence = update_mproots_effervescence.getText().toString().trim();
        edtUpdate_mpotf_s = update_mpotf_s.getText().toString().trim();
        edtUpdate_mpotf_abundance = update_mpotf_abundance.getText().toString().trim();
        edtUpdate_mpotf_nature = update_mpotf_nature.getText().toString().trim();
        edtUpdate_mpotf_samplebag = update_mpotf_samplebag.getText().toString().trim();
        edtUpdate_mpotf_addnotes = update_mpotf_addnotes.getText().toString().trim();
        edtUpdate_pppsd_sand = update_pppsd_sand.getText().toString().trim();
        edtUpdate_pppsd_slit = update_pppsd_slit.getText().toString().trim();
        edtUpdate_pppsd_clay = update_pppsd_clay.getText().toString().trim();
        edtUpdate_pppsd_texturalClass = update_pppsd_texturalClass.getText().toString().trim();
        edtUpdate_pppsd_bulkDensity = update_pppsd_bulkDensity.getText().toString().trim();
        edtUpdate_ppwr_33kPa = update_ppwr_33kPa.getText().toString().trim();
        edtUpdate_ppwr_1500kPa = update_ppwr_1500kPa.getText().toString().trim();
        edtUpdate_ppwr_awc = update_ppwr_awc.getText().toString().trim();
        edtUpdate_ppwr_pawc = update_ppwr_pawc.getText().toString().trim();
        edtUpdate_ppwr_graviMoisture = update_ppwr_graviMoisture.getText().toString().trim();
        edtUpdate_cp_pH = update_cp_pH.getText().toString().trim();
        edtUpdate_cp_ec = update_cp_ec.getText().toString().trim();
        edtUpdate_cp_oc = update_cp_oc.getText().toString().trim();
        edtUpdate_cp_caco = update_cp_caco.getText().toString().trim();
        edtUpdate_cp_ca = update_cp_ca.getText().toString().trim();
        edtUpdate_cp_mg = update_cp_mg.getText().toString().trim();
        edtUpdate_cp_na = update_cp_na.getText().toString().trim();
        edtUpdate_cp_k = update_cp_k.getText().toString().trim();
        edtUpdate_cp_totalBase = update_cp_totalBase.getText().toString().trim();
        edtUpdate_cp_cec = update_cp_cec.getText().toString().trim();
        edtUpdate_cp_bs = update_cp_bs.getText().toString().trim();
        edtUpdate_cp_esp = update_cp_esp.getText().toString().trim();
        edtUpdate_sfp_organicCarbon = update_sfp_organicCarbon.getText().toString().trim();
        edtUpdate_sfpmn_nitrogen = update_sfpmn_nitrogen.getText().toString().trim();
        edtUpdate_sfpmn_phosphorus = update_sfpmn_phosphorus.getText().toString().trim();
        edtUpdate_sfpmn_potassium = update_sfpmn_potassium.getText().toString().trim();
        edtUpdate_sfpmn_sulphur = update_sfpmn_sulphur.getText().toString().trim();
        edtUpdate_sfpmn_zinc = update_sfpmn_zinc.getText().toString().trim();
        edtUpdate_sfpmn_copper = update_sfpmn_copper.getText().toString().trim();
        edtUpdate_sfpmn_iron = update_sfpmn_iron.getText().toString().trim();
        edtUpdate_sfpmn_manganese = update_sfpmn_manganese.getText().toString().trim();


        // calling method to add data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_fields_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //---------add photos------------
//                east_imgView.setImageResource(R.drawable.select_img);
//                west_imgView.setImageResource(R.drawable.select_img);
//                north_imgView.setImageResource(R.drawable.select_img);
//                south_imgView.setImageResource(R.drawable.select_img);

                //below code is for live server
                try {
                    if (TextUtils.equals(response, "1")) {
//                        progressDialog.dismiss();
                        Toast.makeText(UpdateLocationDetails.this, "Data update Successfully", Toast.LENGTH_SHORT).show();
                        //JSONObject jsonObject = new JSONObject(response);
                        // on below line we are displaying a success toast message.
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(KEY_PROJECT_ID, projID);
//                        editor.apply();
//                        finish();
//                        progressDialog.dismiss();
                        startActivity(new Intent(UpdateLocationDetails.this, UpdateRecords.class));
//                      Toast.makeText(LocationDetails.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                    } else {

//                        progressDialog.dismiss();
                        Toast.makeText(UpdateLocationDetails.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                ---------------------old method----------
//                if (TextUtils.equals(response, "success")) {
//                    progressDialog.dismiss();
//                    Toast.makeText(LocationDetails.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
//                } else {
////                        tvStatus.setText("Something went wrong!");
////                        SharedPreferences.Editor editor = sharedPreferences.edit();
////                        editor.putString(KEY_PROJECT_PROFILE_ID, projectProfileID);
////                        editor.apply();
////                        finish();
//                    progressDialog.dismiss();
//                    startActivity(new Intent(LocationDetails.this, SoilDataReport.class));
//                    Toast.makeText(LocationDetails.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Enter location details...", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Please choose image!!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                Log.d("edtUpdate_surveyor_name", "edtUpdate_surveyor_name2222---------" + edtUpdate_surveyor_name);
                data.put("projID", update_selectedProjectID);
                data.put("surveyorname", edtUpdate_surveyor_name);
                data.put("state", edtUpdate_state);
                data.put("district", edtUpdate_district);
                data.put("tehsil", edtUpdate_tehsil);
                data.put("block", edtUpdate_block);
                data.put("villagename", edtUpdate_village);
                data.put("toposheet250k", edtUpdate_topo250k);
                data.put("toposheet50k", edtUpdate_topo50k);
                data.put("elevation", edtUpdate_elevation);
                data.put("physiographicCategory", edtUpdate_physioCategory);
                data.put("subPhysiographicUnit", edtUpdate_subPhysioUnit);
                data.put("geology", edtUpdate_geology);
                data.put("parentMaterial", edtUpdate_parentMaterial);
                data.put("climate", edtUpdate_climate);
                data.put("rainfall", edtUpdate_rainfall);
                data.put("topographyLandformType", edtUpdate_topoLandform);
                data.put("slopeGradiant", edtUpdate_slopeGradiant);
                data.put("slopeLength", edtUpdate_slopeLength);
                data.put("erosion", edtUpdate_erosion);
                data.put("runoff", edtUpdate_runoff);
                data.put("drainage", edtUpdate_drainage);
                data.put("groundWaterDepth", edtUpdate_gndwaterdepth);
                data.put("flooding", edtUpdate_flooding);
                data.put("saltAlkali", edtUpdate_saltAlkali);
                data.put("pH", edtUpdate_ssp_pH);
                data.put("Ec", edtUpdate_ssp_Ec);
                data.put("stoneSize", edtUpdate_stoneSize);
                data.put("stoiness", edtUpdate_stoiness);
                data.put("rockOutcrops", edtUpdate_rockOutcrops);
                data.put("naturalVegetation", edtUpdate_naturalVegetation);
                data.put("ld_remark", edtUpdate_ld_remark);

                data.put("forest", edtUpdate_plu_forest);
                data.put("cultivated", edtUpdate_plu_cultivated);
                data.put("terraces", edtUpdate_plu_terraces);
                data.put("pastureLand", edtUpdate_plu_pastureLand);
                data.put("degradedCulturable", edtUpdate_plu_degradedCulturable);
                data.put("degradedUnCulturable", edtUpdate_plu_degradedUnCulturable);
                data.put("phaseSurface", edtUpdate_plu_phaseSurface);
                data.put("phaseSubstratum", edtUpdate_plu_phaseSubstratum);
                data.put("landCapabilityClass", edtUpdate_plu_landCapabilityClass);
                data.put("landCapabilitySubClass", edtUpdate_plu_landCapabilitysubClass);
                data.put("landIrrigabilityClass", edtUpdate_plu_landIrrigabilityClass);
                data.put("landIrrigabilitySubClass", edtUpdate_plu_landIrrigabilitysubClass);
//                data.put("plu_remarks",edtUpdate_plu_remark);

                data.put("crop", edtUpdate_plu_crops);
                data.put("yield", edtUpdate_plu_yields);
                data.put("managementPractice", edtUpdate_plu_mangPractices);
                data.put("croppingSystem", edtUpdate_plu_croppingSyst);
                data.put("horizon", edtUpdate_pd_horizon);
                data.put("depth", edtUpdate_pd_depth);
                data.put("b_distinctness", edtUpdate_mpbd_distinctness);
                data.put("b_topography", edtUpdate_mpbd_topography);
                data.put("b_diagnostic", edtUpdate_mpbd_daigonisticHorizon);
                data.put("b_matrixColour", edtUpdate_mpbd_matrixColor);
                data.put("mc_abundance", edtUpdate_mpmc_abundance);
                data.put("mc_size", edtUpdate_mpmc_size);
                data.put("mc_contrast", edtUpdate_mpmc_contrast);
                data.put("mc_texture", edtUpdate_mpmc_texture);
                data.put("cf_size", edtUpdate_mpcf_size);
                data.put("cf_vol", edtUpdate_mpcf_vol);
                data.put("str_size", edtUpdate_mpstr_size);
                data.put("str_grade", edtUpdate_mpstr_grade);
                data.put("str_type", edtUpdate_mpstr_type);
                data.put("con_d", edtUpdate_mpcons_d);
                data.put("con_m", edtUpdate_mpcons_m);
                data.put("con_w", edtUpdate_mpcons_w);
                data.put("poros_s", edtUpdate_mpporo_s);
                data.put("poros_q", edtUpdate_mpporo_q);
                data.put("cutans_ty", edtUpdate_mpcutans_ty);
                data.put("cutans_th", edtUpdate_mpcutans_th);
                data.put("cutans_q", edtUpdate_mpcutans_q);
                data.put("nodules_s", edtUpdate_mpnodules_s);
                data.put("nodules_q", edtUpdate_mpnodules_q);
                data.put("roots_s", edtUpdate_mproots_s);
                data.put("roots_q", edtUpdate_mproots_q);
                data.put("roots_effervescence", edtUpdate_mproots_effervescence);
                data.put("of_size", edtUpdate_mpotf_s);
                data.put("of_abundance", edtUpdate_mpotf_abundance);
                data.put("of_nature", edtUpdate_mpotf_nature);
                data.put("of_samplebagno", edtUpdate_mpotf_samplebag);
                data.put("of_additionalnotes", edtUpdate_mpotf_addnotes);
                data.put("sand", edtUpdate_pppsd_sand);
                data.put("silt", edtUpdate_pppsd_slit);
                data.put("clay", edtUpdate_pppsd_clay);
                data.put("USDA_textural_class", edtUpdate_pppsd_texturalClass);
                data.put("bulk_density", edtUpdate_pppsd_bulkDensity);
                data.put("wr_33kPa", edtUpdate_ppwr_33kPa);
                data.put("wr_1500kPa", edtUpdate_ppwr_1500kPa);
                data.put("AWC", edtUpdate_ppwr_awc);
                data.put("PAWC", edtUpdate_ppwr_pawc);
                data.put("gravimetric_moisture", edtUpdate_ppwr_graviMoisture);
                data.put("cp_pH", edtUpdate_cp_pH);
                data.put("cp_EC", edtUpdate_cp_ec);
                data.put("OC", edtUpdate_cp_oc);
                data.put("CaCo", edtUpdate_cp_caco);
                data.put("Ca", edtUpdate_cp_ca);
                data.put("Mg", edtUpdate_cp_mg);
                data.put("Na", edtUpdate_cp_na);
                data.put("K", edtUpdate_cp_k);
                data.put("totalBase", edtUpdate_cp_totalBase);
                data.put("CEC", edtUpdate_cp_cec);
                data.put("BS", edtUpdate_cp_bs);
                data.put("ESP", edtUpdate_cp_esp);
                data.put("organicCarbon", edtUpdate_sfp_organicCarbon);
                data.put("MaN_nitrogen", edtUpdate_sfpmn_nitrogen);
                data.put("MaN_phosphorus", edtUpdate_sfpmn_phosphorus);
                data.put("MaN_potassium", edtUpdate_sfpmn_potassium);
                data.put("MiN_sulphur", edtUpdate_sfpmn_sulphur);
                data.put("MiN_zinc", edtUpdate_sfpmn_zinc);
                data.put("MiN_copper", edtUpdate_sfpmn_copper);
                data.put("MiN_iron", edtUpdate_sfpmn_iron);
                data.put("MiN_manganese", edtUpdate_sfpmn_manganese);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}