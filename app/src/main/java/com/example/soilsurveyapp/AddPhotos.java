package com.example.soilsurveyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.TedPermission;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPhotos extends AppCompatActivity {

//    private Button backBtn, nextBtn, btn_choose_east, btn_submit_east;
//    private Button btn_choose_west, btn_submit_west;
//    ImageView eastImgView, westImgView;
//    ProgressBar progressBar;
//    Bitmap bitmap, bitmap2;
//    String url = "http://10.0.0.145/login/addPhotos.php";
//    String encodeEastImg,encodeWestImg;
//    Uri selectedUri;

    Button east_browse, west_browse, north_browse, south_browse, uploadBtn, backBtn, nextBtn;
    ImageView east_imgView, west_imgView, north_imgView, south_imgView;
    String east_encodeImgStr, west_encodeImgStr, north_encodeImgStr, south_encodeImgStr;
    Bitmap east_bitmap, west_bitmap, north_bitmap, south_bitmap;

    private static final String url = "http://10.0.0.145/login/addPhotos.php";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddPhotos.this, ProjectCredentials.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);

        //----------------------REFERENCES------------------------------------------
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
//
//
        uploadBtn = (Button) findViewById(R.id.upload_btn);
        backBtn = (Button) findViewById(R.id.back_btn);
        nextBtn = (Button) findViewById(R.id.next_btn);

//----------------------------HIDING THE ACTION BAR-----------------
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //--------------------------BUTTON OPERATIONS-------------------------------------------------
//-----------------------------------------------EAST IMG-----------------------------------------------------------
        east_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddPhotos.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse East Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(AddPhotos.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
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
                Dexter.withActivity(AddPhotos.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse West Image"), 2);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(AddPhotos.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
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
                Dexter.withActivity(AddPhotos.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 3);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(AddPhotos.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
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
                Dexter.withActivity(AddPhotos.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 4);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(AddPhotos.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddatatodb();
            }
        });
//        btn_submit_east.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // calling method to add data to Firebase Firestore.
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (TextUtils.equals(response, "success")) {
////                        tvStatus.setText("Successfully registered.");
//                            Toast.makeText(AddPhotos.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
//                        } else {
////                        tvStatus.setText("Something went wrong!");
////                            SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
////                            editor.putString(KEY_DEPTH, soilDepth);
////                            editor.apply();
////                            finish();
//                            Toast.makeText(AddPhotos.this, "Image stored successfully", Toast.LENGTH_SHORT).show();
////                            startActivity(new Intent(getApplicationContext(), AddPhotos.class));
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> data = new HashMap<>();
//                        data.put("east_image", encodeEastImg);
//                        return data;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
//
//            }
//        });

        //-----------------------------------------------WEST IMG-----------------------------------------------------------
//        btn_choose_west.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                askPermission();
//            }
//        });
//        btn_submit_west.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // calling method to add data to Firebase Firestore.
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (TextUtils.equals(response, "success")) {
////                        tvStatus.setText("Successfully registered.");
//                            Toast.makeText(AddPhotos.this, "Something went wrong!! .", Toast.LENGTH_SHORT).show();
//                        } else {
////                        tvStatus.setText("Something went wrong!");
////                            SharedPreferences.Editor editor = sharedPreferencesDepth.edit();
////                            editor.putString(KEY_DEPTH, soilDepth);
////                            editor.apply();
////                            finish();
//                            Toast.makeText(AddPhotos.this, "Image stored successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), AddPhotos.class));
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> data = new HashMap<>();
//                        data.put("west_image", encodeWestImg);
//                        return data;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
//
//            }
//        });
//
//        //-----------------------BACK BTN-----------------------------------------------------------
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        //-----------------------------------------------NEXT BTN-----------------------------------------------------------
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), SoilDataReport.class));
////                Toast.makeText(getApplicationContext(), "go to *** ADD PHOTOS *** page", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void chooseImg() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        startActivityForResult(intent, 1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 || requestCode == RESULT_OK || data != null || data.getData() != null) {
//            selectedUri = data.getData();
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(selectedUri);
//                bitmap = BitmapFactory.decodeStream(inputStream);
//                bitmap2 = BitmapFactory.decodeStream(inputStream);
//                eastImgView.setImageBitmap(bitmap);
//                westImgView.setImageBitmap(bitmap2);
//                imageStore(bitmap);
//                imageStores(bitmap2);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //method for storing image in compressed format and with extension
//    private void imageStores(Bitmap bitmap2) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//         westImgView.setVisibility(westImgView.VISIBLE);
//        byte[] imagebyte = stream.toByteArray();
//         encodeWestImg = android.util.Base64.encodeToString(imagebyte, Base64.DEFAULT);
//    }
//    //method for storing image in compressed format and with extension
//    private void imageStore(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        eastImgView.setVisibility(eastImgView.VISIBLE);
//         byte[] imagebyte = stream.toByteArray();
//        encodeEastImg = android.util.Base64.encodeToString(imagebyte, Base64.DEFAULT);
//     }
//
//    //permission method for accessing gallery
//    private void askPermission() {
//        PermissionListener permissionListener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                chooseImg();
//            }
//
//            @Override
//            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(AddPhotos.this, "Permission Required!!", Toast.LENGTH_SHORT).show();
//            }
//        };
//        TedPermission.with(AddPhotos.this)
//                .setPermissionListener(permissionListener)
//                .setPermissions(Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE)
//                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 ||requestCode == 2||requestCode == 3||requestCode == 4 || resultCode==RESULT_OK || data != null || data.getData() != null) {
            switch(requestCode){
                case 1:
                    if(resultCode == RESULT_OK){
                        Uri filepath = data.getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(filepath);
                            east_bitmap = BitmapFactory.decodeStream(inputStream);
                            east_imgView.setImageBitmap(east_bitmap);
                            east_encodeBitmapImg(east_bitmap);
                        } catch (Exception ex) {

                        }
                    }//if resultCode Case 1
                    break;
                case 2:
                    if(resultCode == RESULT_OK) {
                        Uri filepath = data.getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(filepath);
                            west_bitmap = BitmapFactory.decodeStream(inputStream);
                            west_imgView.setImageBitmap(west_bitmap);
                            west_encodeBitmapImage(west_bitmap);
                        } catch (Exception ex) {

                        }

                    }//if resultCode Case 2
                    break;
                case 3:
                    if(resultCode == RESULT_OK) {
                        Uri filepath = data.getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(filepath);
                            north_bitmap = BitmapFactory.decodeStream(inputStream);
                            north_imgView.setImageBitmap(north_bitmap);
                            north_encodeBitmapImage(north_bitmap);
                        } catch (Exception ex) {

                        }

                    }//if resultCode Case 3
                    break;
                case 4:
                    if(resultCode == RESULT_OK) {
                        Uri filepath = data.getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(filepath);
                            south_bitmap = BitmapFactory.decodeStream(inputStream);
                            south_imgView.setImageBitmap(south_bitmap);
                            south_encodeBitmapImage(south_bitmap);
                        } catch (Exception ex) {

                        }

                    }//if resultCode Case 3


            }//Switch

        }}

    private void east_encodeBitmapImg(Bitmap sec_bitmap) {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        sec_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        east_encodeImgStr= android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void west_encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        west_encodeImgStr= android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void north_encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        north_encodeImgStr= android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void south_encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        south_encodeImgStr= android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }
    private void uploaddatatodb() {
        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                east_imgView.setImageResource(R.drawable.select_img);
                west_imgView.setImageResource(R.drawable.select_img);
                north_imgView.setImageResource(R.drawable.select_img);
                south_imgView.setImageResource(R.drawable.select_img);
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map= new HashMap<String, String>();
                map.put("east_image",east_encodeImgStr);
                map.put("west_image",west_encodeImgStr);
                map.put("north_image",north_encodeImgStr);
                map.put("south_image",south_encodeImgStr);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

}