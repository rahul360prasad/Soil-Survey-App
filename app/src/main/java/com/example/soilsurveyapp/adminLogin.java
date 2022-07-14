package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class adminLogin extends AppCompatActivity {

    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_admin_NAME = "admin_mypref";
    private static final String admin_KEY_ID = "admin_id";
    private static final String admin_KEY_NAME = "admin_name";
    private static final String admin_KEY_EMAIL = "admin_email";
    private static final String admin_KEY_PASSWORD = "admin_password";
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    String url = "http://10.0.0.145/login/mysqls.php";
    private EditText admin_etName, admin_etPassword;
    private String admin_name, admin_password;
//    private String url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";

    //--------hardware back button functionality----------
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(adminLogin.this, HomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        //------holding the name of the admin login user---------------
//        sharedPreferences = getSharedPreferences(SHARED_PRE_admin_NAME, MODE_PRIVATE);

        //------------------HIDING THE ACTION BAR----------------------
        //below code is for hiding the title bar of app
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //login's input box
        admin_name = admin_password = "";
        admin_etName = findViewById(R.id.admin_etName);
        admin_etPassword = findViewById(R.id.admin_etPassword);

        //when open the activity then first check "shared preference" data available or not
//        String name = sharedPreferences.getString(admin_KEY_NAME, null);
//        if (name != null) {
//            // if data is availabe then directly call to homepage i.e dashboard page
//            startActivity(new Intent(adminLogin.this, HomePage.class));
//        }

    }

    public void adminLoginBtn(View view) {

        //Initialinzing the progress Dialog
        progressDialog = new ProgressDialog(adminLogin.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //--------------------SHAREDPREFERENCE-------------------
        //when clicking register btn put data on shared preference
//       SharedPreferences.Editor editor = sharedPreferences.edit();
//       editor.putString(admin_KEY_NAME, admin_etName.getText().toString());
//       editor.putString(admin_KEY_PASSWORD, admin_etPassword.getText().toString());
//       editor.apply();
//        editor.clear();
//        editor.commit();

        admin_name = admin_etName.getText().toString().trim();
        admin_password = admin_etPassword.getText().toString().trim();

        if (!admin_name.equals("") && !admin_password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    url + "?TYPE=ADMIN_LOGIN&name=" + admin_name + "&password=" + admin_password,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("res", response);
//                    if (response.equals("success")) {
//                        Intent intent = new Intent(MainActivity.this, HomePage.class);
//                        startActivity(intent);
////                        finish();
//                    } else if (response.equals("failure")) {
//                        Toast.makeText(MainActivity.this, "Invalid Login Id/Password", Toast.LENGTH_SHORT).show();
//                    }
                            try {
                                if (TextUtils.equals(response, "0")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(adminLogin.this, "Failed to LoggedIn!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONArray jsonarray = new JSONArray(response);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        String admin_id = jsonobject.getString("admin_id");
                                        String admin_name = jsonobject.getString("admin_name");
                                        String admin_email = jsonobject.getString("admin_email");

//                                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                                        editor.putString(admin_KEY_ID, admin_id);
//                                        editor.putString(admin_KEY_NAME, admin_name);
//                                        editor.putString(admin_KEY_EMAIL, admin_email);
//                                        editor.apply();
//                                        editor.clear();
//                                        editor.commit();

                                        progressDialog.dismiss();
                                        Toast.makeText(adminLogin.this, "LoggedIn Successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(adminLogin.this, admin_DataReport.class);
                                        startActivity(intent);
                                        finish();

                                    }
//                            Toast.makeText(MainActivity.this, "LoggedIn Successfull", Toast.LENGTH_SHORT).show();
//                           // JSONObject jsonObject = new JSONObject(response);
//                            // on below line we are displaying a success toast message.
//                            Intent intent = new Intent(MainActivity.this, HomePage.class);
//                            startActivity(intent);
//                            finish();
                                }
                            } catch (Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(adminLogin.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(adminLogin.this, "server error\nplease retry after sometime", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }

    }

    //in ADMIN PAGE textview to move from login to register page
    public void admin_moveToRegistration(View view) {
        startActivity(new Intent(this, adminRegistration.class));
        finish();
    }
}