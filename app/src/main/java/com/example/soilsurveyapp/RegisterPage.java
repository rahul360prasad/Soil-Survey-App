package com.example.soilsurveyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etReenterPassword;
    private String name, email, password, reenterPassword;

    ProgressDialog progressDialog;

//    private String url = "http://10.0.0.145/soil_survey/register.php";
    private String url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

//        ---HIDING THE ACTION BAR--------
        getSupportActionBar().setTitle("");

        //input box
//        userInput = (EditText) findViewById(R.id.RegUserInput);
//        userPassInput = (EditText) findViewById(R.id.RegPass);
//        userCnfPassInput = (EditText) findViewById(R.id.RegCnfPass);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etReenterPassword = findViewById(R.id.etReenterPassword);
        btnRegister = findViewById(R.id.reg_btn_reg);

        name = email = password = reenterPassword = "";
    }

    //----------Register Button method-----------------------
    public void Register(View view) {

        // below is for progress dialog box
        //Initialinzing the progress Dialog
        progressDialog= new ProgressDialog(RegisterPage.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //-----------validations for empty input box--------------
//        if(userInput.getText().toString().equals("")){
//            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
//        }else if(userPassInput.getText().toString().equals("")){
//            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
//        }else if(userCnfPassInput.getText().toString().equals("")){
//            Toast.makeText(this, "Re-Enter Password", Toast.LENGTH_SHORT).show();
//        }else if(!userCnfPassInput.getText().toString().equals(userPassInput.getText().toString())){
//            Toast.makeText(RegisterPage.this,"Password Not matching",Toast.LENGTH_SHORT).show();
////            temp=false;
//        }else {
//            progressDialog.show();
//            str_name = userInput.getText().toString().trim();
//            str_password = userPassInput.getText().toString().trim();
//            str_cnf_password = userCnfPassInput.getText().toString().trim();
//
//            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    progressDialog.dismiss();
//                    userInput.setText("");
//                    userPassInput.setText("");
//                    userCnfPassInput.setText("");
//                    Toast.makeText(RegisterPage.this, response, Toast.LENGTH_SHORT).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
//                    Toast.makeText(RegisterPage.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            ){
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String,String> params = new HashMap<String, String>();
//                    params.put("name",str_name);
//                    params.put("email",str_password);
//                    params.put("password",str_cnf_password);
//                    return params;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(RegisterPage.this);
//            requestQueue.add(request);
//        }

        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();
        if(!password.equals(reenterPassword)){
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("") && !email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"?TYPE=REGISTER&name="+name+"&email="+email+"&password="+password, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if(TextUtils.equals(response,"1")){
                            progressDialog.dismiss();
                            Toast.makeText(RegisterPage.this, "Registered Successfull", Toast.LENGTH_SHORT).show();
                            //JSONObject jsonObject = new JSONObject(response);
                            // on below line we are displaying a success toast message.
                            Intent intent = new Intent(RegisterPage.this, RegisterPage.class);
                            startActivity(intent);
                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(RegisterPage.this, "Failed to register!!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
//                    if (response.equals("success")) {
////                        tvStatus.setText("Successfully registered.");
//                        Toast.makeText(RegisterPage.this,"Successfully registered.",Toast.LENGTH_SHORT).show();
//                        btnRegister.setClickable(false);
//                    } else if (response.equals("failure")) {
////                        tvStatus.setText("Something went wrong!");
//                        Toast.makeText(RegisterPage.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> data = new HashMap<>();
//                    data.put("name", name);
//                    data.put("email", email);
//                    data.put("password", password);
//                    return data;
//                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            progressDialog.dismiss();
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    //----------Go to Login page Button-----------------------
    public void moveToLogin(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

}