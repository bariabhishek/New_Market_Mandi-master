package com.freshbrigade.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshbrigade.market.Activity.ListOfVegetablefor_Vender;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VendorDetailUpdate extends AppCompatActivity {
    EditText vName,vCity;
    ImageButton buttonvender;
    Spinner spinner;
    String item;
    String[] vendorTypeSlectionArray = {"Select One","Aarath","Farmer","Mandi merchent"};
    String url=API.VENDOR_ACTIVE;
    SessionManage sessionManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail_update);

        vName = findViewById(R.id.usernameV) ;
        vCity = findViewById(R.id.City_V);



        buttonvender = findViewById(R.id.buttonvender);
        spinner =findViewById(R.id.spinerVendorType);
        sessionManage = new SessionManage(this);





        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,vendorTypeSlectionArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        buttonvender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = String.valueOf(spinner.getSelectedItem());

                if(vName.getText().toString().isEmpty() || vCity.getText().toString().isEmpty() || item.isEmpty() || item.equals("Select One")) {

                    Log.e("chack",vName.getText().toString() );
                    Toast.makeText(getApplicationContext(), "Please fill all details properly!", Toast.LENGTH_LONG).show();

                }
                else{

                    volleyWork();

                }
            }
        });



    }

    private void volleyWork() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("usr", response);
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status").toString();

                 //   Toast.makeText(getApplicationContext(),"try",Toast.LENGTH_LONG).show();


                    if(status.equals("ok")) {


                        String v_code = jsonObject.getString("v_code");
                        String v_mobile = jsonObject.getString("v_mobile");
                        String userType = jsonObject.getString("userType");
                        String userStatus = jsonObject.getString("userStatus");
                        String vendor_type = jsonObject.getString("vendor_type");
                        Log.e("usr", userType);
                        Log.e("status",userStatus);
                        sessionManage.create_vendor_session(v_code, v_mobile, userStatus, vendor_type);
                        sessionManage.set_user(userType);




                        Intent intent = new Intent(VendorDetailUpdate.this, ListOfVegetablefor_Vender.class);

                        startActivity(intent);
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();

                SessionManage sessionManage = new SessionManage(getApplicationContext());

                HashMap<String,String > user = new HashMap<>();
                user = sessionManage.get_vendor_session();
                String code_v =  user.get(sessionManage.CODE);

              //  Log.d("cose_c",cose_c);

                    params.put("v_type",item);
                    params.put("c_code","0dsfs");
                    params.put("v_code",code_v);
                    params.put("city",vCity.getText().toString());
                    params.put("name",vName.getText().toString());
                    params.put("action","update");
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
