package com.freshbrigade.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NumberActivity extends AppCompatActivity {
    EditText phoneNumber;
    Button submit;
    String mobileNumber;
    boolean numberCompletedStatus;
    AlertDialog.Builder builder;
    int rand;
    ProgressBar progressBar;
    @Override
    protected void onStart() {
        super.onStart();
        numberCompletedStatus=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        rand=getRandomNumber(100000,999999);
        phoneNumber = findViewById(R.id.number);
        submit = findViewById(R.id.submit);
        progressBar=findViewById(R.id.progressBar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendOtp(phoneNumber.getText().toString());
                progressBar.setVisibility(View.VISIBLE);

//                if(numberCompletedStatus==true){
//                    mobileNumber=phoneNumber.getText().toString().trim();
//
//                    builder = new AlertDialog.Builder(NumberActivity.this);
//                    builder.setMessage("Please Confirm your mobile number: "+mobileNumber).setTitle("Confirm Number");
//
//                    AlertDialog dialog = builder.create();
//                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//
//
//                        }
//                    });
//                    builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                        }
//                    });
//                    builder.setCancelable(false);
//
//                    builder.show();
//
//
//                }
//                else{
//
//                }
            }
        });
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<10){
                    phoneNumber.setError("Please Enter 10 numbers!");
                }
                else if(s.length()>10){
                    phoneNumber.setError("Please Enter 10 numbers!");
                }
                else if(s.length()==10){
                    numberCompletedStatus=true;
                }

            }
        });




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
                            String message = jsonObject.getString("message").toString();

                            Log.e("phpppp",response);
                            if(status.equals("success")){
                                progressBar.setVisibility(View.GONE);
                                Intent intent=new Intent(NumberActivity.this,OTP.class);
                                intent.putExtra("mobilenumber",mobileNumber);
                                startActivity(intent);
                                finish();



                            }else {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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
                params.put("otp_expiry","1");

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

}
