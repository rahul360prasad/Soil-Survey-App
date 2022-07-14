package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class adminRegistration extends AppCompatActivity {
    ProgressDialog progressDialog;
    private EditText admin_etName, admin_etEmail, admin_etPassword, admin_etReenterPassword;
    private String admin_name, admin_email, admin_password, admin_reenterPassword;
    private String url = "http://10.0.0.145/login/mysqls.php";
    //    private String url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    private Button admin_btnRegister;

    //--------hardware back button functionality----------
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(adminRegistration.this, HomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        //------------------HIDING THE ACTION BAR----------------------
        //below code is for hiding the title bar of app
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //input box
        admin_etName = findViewById(R.id.admin_etName);
        admin_etEmail = findViewById(R.id.admin_etEmail);
        admin_etPassword = findViewById(R.id.admin_etPassword);
        admin_etReenterPassword = findViewById(R.id.admin_etReenterPassword);
        admin_btnRegister = findViewById(R.id.admin_reg_btn_reg);

        admin_name = admin_email = admin_password = admin_reenterPassword = "";
    }

    //----------Register Button method-----------------------
    public void adminRegisterBtn(View view) {

        // below is for progress dialog box
        //Initialinzing the progress Dialog
        progressDialog = new ProgressDialog(adminRegistration.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        admin_name = admin_etName.getText().toString().trim();
        admin_email = admin_etEmail.getText().toString().trim();
        admin_password = admin_etPassword.getText().toString().trim();
        admin_reenterPassword = admin_etReenterPassword.getText().toString().trim();

        if (!admin_password.equals(admin_reenterPassword)) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (!admin_name.equals("") && !admin_email.equals("") && !admin_password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "?" +
                    "TYPE=ADMIN_REGISTER&name=" + admin_name + "&email=" + admin_email + "&password=" + admin_password,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (TextUtils.equals(response, "1")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(adminRegistration.this, "Registered Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(adminRegistration.this, adminRegistration.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(adminRegistration.this, "Failed to register!!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
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
    public void admin_moveToLogin(View view) {
        startActivity(new Intent(getApplicationContext(), adminLogin.class));
        finish();
    }
}