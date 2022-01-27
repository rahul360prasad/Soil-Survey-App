package com.example.soilsurveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    EditText userInput, userPass;
//    String str_name, str_password;
//    String url = "http://10.0.0.145/soil_survey/login.php";

    private EditText etName, etPassword;
    private String name, password;
    private String url = "http://14.139.123.73:9090/web/NBSS/php/mysql.php";
    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;


    //creating shared preference name and also creating key name
    private static final String SHARED_PRE_NAME = "mypref";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);

        //---HIDING THE ACTION BAR
        getSupportActionBar().setTitle("");

        //login's input box
//        userInput = (EditText) findViewById(R.id.LoginUserInput);
//        userPass = (EditText) findViewById(R.id.LoginPass);
        name = password = "";
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        //when open the activity then first check "shared preference" data available or not
        String name = sharedPreferences.getString(KEY_NAME, null);
        if (name != null) {
            // if data is availabe then directly call to homepage i.e dashboard page
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }
    }

    public void Login(View view) {

        //Initialinzing the progress Dialog
        progressDialog= new ProgressDialog(MainActivity.this);
        //show Dialog
        progressDialog.show();
        //set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //--------------------SHAREDPREFERENCE-------------------
        //when clicking register btn put data on shared preference
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, etName.getText().toString());
        editor.putString(KEY_PASSWORD, etPassword.getText().toString());
        editor.apply();
//                editor.clear();
//                editor.commit();

        name = etName.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if(!name.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"?TYPE=LOGIN&name="+name+"&password="+password, new Response.Listener<String>() {
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
                        if(TextUtils.equals(response,"0")){
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed to LoggedIn!!", Toast.LENGTH_SHORT).show();
                        }else{
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String id = jsonobject.getString("id");
                                String name = jsonobject.getString("name");
                                String email = jsonobject.getString("email");

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_ID, id);
                                editor.putString(KEY_NAME, name);
                                editor.putString(KEY_EMAIL, email);
                                editor.apply();

                                finish();
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "LoggedIn Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomePage.class);
                                startActivity(intent);
                            }
//                            Toast.makeText(MainActivity.this, "LoggedIn Successfull", Toast.LENGTH_SHORT).show();
//                           // JSONObject jsonObject = new JSONObject(response);
//                            // on below line we are displaying a success toast message.
//                            Intent intent = new Intent(MainActivity.this, HomePage.class);
//                            startActivity(intent);
//                            finish();
                        }
                    }catch (Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Failed to LoggedIn!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> data = new HashMap<>();
//                    data.put("name", name);
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

    public void moveToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterPage.class));
        finish();
    }
}