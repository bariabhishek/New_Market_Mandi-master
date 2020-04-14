package com.freshbrigade.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VendarActivation extends AppCompatActivity {

    EditText code;
    Button btn_requet;
    LinearLayout activeCodeLayout,layout;
    ImageButton im_active_code;
    LinearLayout vendorAleart;
    LinearLayout vendor_alert_relative_layout;
    String url=API.VENDOR_ACTIVE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendar_activation);
        activeCodeLayout=findViewById(R.id.active_code_layout);
        im_active_code=findViewById(R.id.btn_active_code);
        code = findViewById(R.id.activationCode);
        btn_requet=findViewById(R.id.btn_request);
        vendorAleart = findViewById(R.id.vendor_aleart);
        vendor_alert_relative_layout=findViewById(R.id.vendor_alert_relative_layout);


        get_vendore_request_info();


        btn_requet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send_vendor_request();

            }
        });


        im_active_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_vendore_active_code(code.getText().toString());
            }
        });

    }


    private void send_vendor_request()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("qwetttttt",response );

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status =jsonObject.getString("status");

                    if(status.equals("ok"))
                    {
                        activeCodeLayout.setVisibility(View.VISIBLE);
                        vendorAleart.setVisibility(View.GONE);
                        vendor_alert_relative_layout.setVisibility(View.GONE);
                    }else
                    {
                        activeCodeLayout.setVisibility(View.GONE);
                        vendor_alert_relative_layout.setVisibility(View.GONE);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

            }{

            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();
                SharedPreferences sharedPreferences;


                SessionManage sessionManage = new SessionManage(getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_client_session();

                String c_code = user.get(sessionManage.CODE);


                   params.put("c_code",c_code);

                params.put("action","genrate_code");


                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }



    private void get_vendore_request_info()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("ven",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status =jsonObject.getString("status");

                    if(status.equals("ok"))
                    {
                        String activeStatus =jsonObject.getString("active_status");

                        if(activeStatus.equals("pending")){

                            activeCodeLayout.setVisibility(View.VISIBLE);
                            vendorAleart.setVisibility(View.GONE);


                        }
                        else if(activeStatus.equals("not"))
                        {
                            activeCodeLayout.setVisibility(View.GONE);
                            vendorAleart.setVisibility(View.VISIBLE);

                        }
                            else {

                            AlertDialog.Builder alertBuilder =new AlertDialog.Builder(VendarActivation.this);

                            alertBuilder.setTitle("Info");
                            alertBuilder.setMessage("You Register as a Vendore Please Re-Login your Account");
                            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SessionManage sessionManage = new SessionManage(getApplicationContext());
                                    sessionManage.logOut();

                                    Intent i = new Intent(getApplicationContext(), NumberActivity.class);
                                    finish();
                                    startActivity(i);

                                }
                            });
                            AlertDialog alert = alertBuilder.create();
                            alert.show();


                        }



                    }else
                    {
                        vendor_alert_relative_layout.setVisibility(View.GONE);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

            }{

            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();

                SessionManage sessionManage = new SessionManage(getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_client_session();

                String c_code = user.get(sessionManage.CODE);


                params.put("c_code",c_code);
                params.put("action","get");


                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }



    private void send_vendore_active_code(final String active_code)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status =jsonObject.getString("status");

                    Log.e("sttt", status);

                    if(status.equals("ok"))
                    {
                        AlertDialog.Builder alertBuilder =new AlertDialog.Builder(VendarActivation.this);

                        alertBuilder.setTitle("Info");
                        alertBuilder.setMessage("You Register as a Vendore Please Re-Login your Account");
                        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SessionManage sessionManage = new SessionManage(getApplicationContext());
                                sessionManage.logOut();
                                finish();
                                Intent i = new Intent(getApplicationContext(),NumberActivity.class);
                                startActivity(i);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }else
                    {
                        AlertDialog.Builder alertBuilder =new AlertDialog.Builder(VendarActivation.this);
                        alertBuilder.setTitle("Info");
                        alertBuilder.setMessage("Activation Code Invalid");
                        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activeCodeLayout.setVisibility(View.VISIBLE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

            }{

            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();
                SharedPreferences sharedPreferences;


                SessionManage sessionManage = new SessionManage(getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_client_session();

                String c_code = user.get(sessionManage.CODE);


                params.put("c_code",c_code);
                params.put("action","code");
                params.put("active_code",active_code);
                Log.e("lp",active_code);


                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




    }
}
