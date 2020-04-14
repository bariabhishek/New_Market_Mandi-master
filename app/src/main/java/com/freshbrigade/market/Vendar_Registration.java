package com.freshbrigade.market;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshbrigade.market.Activity.Client_Side;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Vendar_Registration extends AppCompatActivity {
    String mobileNumber;
    String name ,sname,saddress,pin;
    String city;
    EditText vname,vcity,shop_address,pincode,shop_name;
    SessionManage sessionManage;
    ImageButton next;
    private String REGISTER_USER_API=API.CLIENT_REGISTER; ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendar__registration);
        vname=findViewById(R.id.user_nameV);
//        vcity=findViewById(R.id.CityV);
        shop_address=findViewById(R.id.shop_addressV);
//        pincode=findViewById(R.id.pincodeV);
      //  shop_name=findViewById(R.id.shop_nameV);
        next=findViewById(R.id.imagebuttonVender);
        sessionManage = new SessionManage(getApplicationContext());

        volleydata();

        HashMap<String,String > user = new HashMap<>();
        user = sessionManage.get_client_session();
         mobileNumber =  user.get(sessionManage.MOBILE);


        Log.d("mob",mobileNumber);






        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(vname.getText().toString().isEmpty()||shop_address.getText().toString().isEmpty()
                       ) {

                    Log.e("chack",vname.getText().toString() );
                    Toast.makeText(getApplicationContext(), "Please fill all details properly!", Toast.LENGTH_LONG).show();

                }
                else{
                    name=vname.getText().toString();
                    city=shop_address.getText().toString();
//                    Toast.makeText(getApplicationContext(),name+" "+city,Toast.LENGTH_LONG).show();
////                    pin = pincode.getText().toString();
////                    sname = shop_name.getText().toString();
////                    saddress = shop_address.getText().toString();
//
//                 //   Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_LONG).show();

                    apiRegistration(mobileNumber,name,city);

                }
            }
        });

    }

    private void volleydata() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_USER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response009",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if(status.equals("ok")) {


                        String c_name = jsonObject.getString("c_name");


                       // String c_shop_name = jsonObject.getString("c_shop_name");
                        String c_address = jsonObject.getString("c_address");
                        Log.d("address",c_address);

                    //    String c_city = jsonObject.getString("c_city");
                        //String c_pincode = jsonObject.getString("c_pincode");

                     //   Log.e("c_name",c_name+c_address+c_city+c_pincode+c_shop_name );


                        if(c_address.equals("null")||c_name.equals(null)){

                            vname.setText("");
    //                        vcity.setText("");
                            shop_address.setText("");
  //                          pincode.setText("");
//                            shop_name.setText("");

                        }else {

                            vname.setText(c_name);
                         //   vcity.setText(c_city);
                            shop_address.setText(c_address);
                           /* pincode.setText(c_pincode);
                            shop_name.setText(c_shop_name);*/
                        }
                    }
                    else if(status.equals("elert")){
                        Toast.makeText(getApplicationContext(),"user already registor this number",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.e("response",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String,String>params=new HashMap<String,String>();

                SessionManage sessionManage = new SessionManage(getApplicationContext());

                HashMap<String,String > user = new HashMap<>();
                user = sessionManage.get_client_session();
                String cose_c =  user.get(sessionManage.CODE);


                params.put("c_name","a");
             /*   params.put("c_shop_name","a");*/
                params.put("c_mobile","a");
                params.put("c_address","a");
              /*  params.put("c_city","a");
                params.put("c_pincode","a");*/
                params.put("action","get");
                params.put("code",cose_c);
                Log.d("cos",cose_c);


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

    private void apiRegistration(final String mobileNumber, final String name,final String saddress) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_USER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("123",response );
                            String status = jsonObject.getString("status");

                            if(status.equals("ok"))
                            {

                                String c_code = jsonObject.getString("c_code");
                                String c_mobile = jsonObject.getString("c_mobile");
                                String userType = jsonObject.getString("userType");
                                String user_activation_status = jsonObject.getString("user_activation_status");

                                sessionManage.create_client_session(c_code,c_mobile,userType,user_activation_status);
                                Snackbar.make(findViewById(android.R.id.content), "Update Succesfull", Snackbar.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), Client_Side.class);
                                startActivity(intent);
                                finish();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("cache",e.toString() );
                        }


                        Log.e("c",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("responce", error.toString() );
                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();
                SessionManage sessionManage = new SessionManage(getApplicationContext());

                HashMap<String,String > user = new HashMap<>();
                user = sessionManage.get_client_session();
                String cose_c =  user.get(sessionManage.CODE);


                params.put("c_name",name);
               // params.put("c_shop_name",sname);
                params.put("c_mobile",mobileNumber);
                params.put("c_address",saddress);
               // params.put("c_city",city);
              //  params.put("c_pincode",pin);
                params.put("action","update");
                params.put("code",cose_c);
                params.put("c_city","jaipur");

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
}
