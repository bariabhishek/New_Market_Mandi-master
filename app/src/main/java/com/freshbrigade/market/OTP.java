package com.freshbrigade.market;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.freshbrigade.market.Activity.Client_Side;
import com.freshbrigade.market.Activity.ListOfVegetablefor_Vender;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class OTP extends AppCompatActivity  {

    FirebaseAuth auth;
    TextView editnumber,number;
    public EditText editText, et2;
    PhoneAuthProvider.ForceResendingToken ResendToken;
    String varification_code;
    public String mobileNumber;
    private String mVerificationId;
    FirebaseAuth mAuth;
    boolean verificationStatus;
    Button resendCode;
    Button next;
    String enteredOtpCode;
    Button resendOtp;
    Boolean Authsuccess=false;
    String code;
    ProgressBar progressBar;
    LinearLayout btnLayout;
    private String REGISTER_USER_API=API.LOG_IN ;
    String Otp;
    SessionManage sessionManage;
    AlertDialog.Builder builder;
    int rand;


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        mobileNumber = intent.getStringExtra("mobilenumber");
        number.setText(mobileNumber);
      //  sendVerificationCode(mobileNumber);
        verificationStatus=false;






    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarOtp);

        sessionManage = new SessionManage(this);
        editText = findViewById(R.id.otpEdit_);
        next=findViewById(R.id.otpconfirm);
        editnumber=findViewById(R.id.editnumber);
        number=findViewById(R.id.number);
        btnLayout=findViewById(R.id.butlayout);
        resendOtp=findViewById(R.id.resendcode);
        builder = new AlertDialog.Builder(OTP.this);
        rand=getRandomNumber(100000,999999);
        final Intent intent = getIntent();
        mobileNumber = intent.getStringExtra("mobilenumber");
        number.setText(mobileNumber);


        auth = FirebaseAuth.getInstance();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String b =editText.getText().toString();
                if(b.equals("")){

                    Toast.makeText(getApplicationContext(),"Please insert verification code",Toast.LENGTH_LONG).show();
                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    Otp =editText.getText().toString();
                    checkOtp(mobileNumber);

                }

            }
        });

        editnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(OTP.this,NumberActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOtp(mobileNumber);
            }
        });


    }

    private void checkOtp(final String mobileNumber) {



        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://control.msg91.com/api/verifyRequestOTP.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("aaa",response );
                        //   JSONArray jsonArray = new JSONArray(response);
                        try {

                            JSONObject jsonObject = new  JSONObject(response);
                            String status = jsonObject.getString("type").toString();
                            String message = jsonObject.getString("message").toString();

                            Log.e("phpppp",response);
                            if(status.equals("success")){

                              //  Toast.makeText(getApplicationContext(),"Server ok",Toast.LENGTH_LONG).show();
                                checkUser(mobileNumber);



                            }else {

                                builder.setMessage(message).setTitle("Error");


                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        progressBar.setVisibility(View.INVISIBLE);

                                    }
                                });

                                builder.setCancelable(false);

                                builder.show();

                                progressBar.setVisibility(View.INVISIBLE);

                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            Log.e("json",e.toString());
                            e.printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.e("json",error.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String>params=new HashMap<String,String>();
                params.put("authkey","281608AqE2Kl6kNfy5d08c2d2");
                params.put("mobile","91"+mobileNumber);
                params.put("otp",String.valueOf(Otp));

                return params;
            }
        };

        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void checkUser(final String mobileNumber) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_USER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("aaa",response );
                        //   JSONArray jsonArray = new JSONArray(response);
                        try {

                            JSONObject jsonObject = new  JSONObject(response);
                            String status = jsonObject.getString("status").toString();

                            if(status.equals("ok")){
                                String usertype = jsonObject.getString("userType");

                                if(usertype.equals("client")) {
                                    String c_code = jsonObject.getString("c_code");
                                    String c_mobile = jsonObject.getString("c_mobile");
                                    String userType = jsonObject.getString("userType");
                                    String user_activation_status = jsonObject.getString("userStatus");
                                    String userStatus = jsonObject.getString("userStatus");
                                    sessionManage.create_client_session(c_code,c_mobile,userType,user_activation_status);
                                    sessionManage.set_user(userType);
                                    perfrence();
                                    progressBar.setVisibility(View.INVISIBLE);
//                                    Intent intent = new Intent(OTP.this, ClientRegistration.class);
//                                    intent.putExtra("mobilenumber", mobileNumber);
//                                    startActivity(intent);
//                                    finish();

                                    if(userStatus.equals("1"))
                                    {
                                        Intent intent = new Intent(OTP.this, Client_Side.class);
                                        intent.putExtra("mobilenumber", mobileNumber);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else
                                    {   Intent intent = new Intent(OTP.this, ClientRegistration.class);
                                        intent.putExtra("mobilenumber", mobileNumber);
                                        startActivity(intent);
                                        finish();
                                    }

                                }else {

                                    String v_code = jsonObject.getString("v_code");
                                    String v_mobile = jsonObject.getString("v_mobile");
                                    String userType = jsonObject.getString("userType");
                                    String userStatus = jsonObject.getString("userStatus");
                                    String vendor_type = jsonObject.getString("vendor_type");
                                    Log.e("usr",userType );
                                    Log.e("status",userStatus);


                                    sessionManage.create_vendor_session(v_code,v_mobile,userStatus,vendor_type);
                                    sessionManage.set_user(userType);
                                    perfrence();

                                    Intent intent = new Intent(OTP.this, ListOfVegetablefor_Vender.class);
                                        intent.putExtra("mobilenumber", mobileNumber);
                                        startActivity(intent);
                                    finish();
//                                    if(userStatus.equals("1"))
//                                    {
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                        Intent intent = new Intent(OTP.this, ListOfVegetablefor_Vender.class);
//                                        intent.putExtra("mobilenumber", mobileNumber);
//                                        startActivity(intent);

//
//                                    }
//                                    else
//                                    {  progressBar.setVisibility(View.INVISIBLE);
//                                        Intent intent = new Intent(OTP.this, VendorDetailUpdate.class);
//                                        intent.putExtra("mobilenumber", mobileNumber);
//                                        startActivity(intent);
//                                        finish();
//
//                                    }



                                }

                            }else {
                                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();



                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            Log.e("json",e.toString());
                            e.printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.e("json",error.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();




                params.put("mobile",mobileNumber);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void perfrence() {

        SharedPreferences preferences = getSharedPreferences("chack",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NUMBERCHACK","true");
        editor.commit();
    }



    private void SendOtp(final String mobileNumber) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://control.msg91.com/api/sendotp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("aaa",response );
                        //   JSONArray jsonArray = new JSONArray(response);
                        try {

                            JSONObject jsonObject = new  JSONObject(response);
                            String status = jsonObject.getString("type");

                            Log.e("phpppp",response);
                            if(status.equals("success")){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Resend Your OTP on your mobile number",Toast.LENGTH_LONG).show();
                               // Intent intent=new Intent(OTP.this,OTP.class);
                              //  intent.putExtra("mobilenumber",mobileNumber);
                              //  startActivity(intent);
                              //  finish();



                            }else {
                                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);


                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            Log.e("json",e.toString());
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            //  progressBar.dismiss();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.e("json",error.toString());
                progressBar.setVisibility(View.GONE);
//                progressBar.dismiss();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();





                params.put("authkey","281608AqE2Kl6kNfy5d08c2d2");
                params.put("mobile","91"+mobileNumber);
                params.put("otp",String.valueOf(rand));
                params.put("sender","FreshBrigade");
                params.put("message","Your OTP for Fresh Brigade is : "+rand+" .");
                params.put("otp_length","6");

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 96) + min;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}