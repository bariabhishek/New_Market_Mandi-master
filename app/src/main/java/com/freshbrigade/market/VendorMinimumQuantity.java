package com.freshbrigade.market;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorMinimumQuantity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<VendorMinimumQuantityData> dataList;
    ProgressDialog progressBar;
    TextView product,history1 ;
    ArrayList blank = new ArrayList();
    FloatingActionButton fab;
    SessionManage sessionManage;
    Boolean succes= true;
    private AlertDialog.Builder builder;

    String url2 =API.MINIMUM_QTY;
    String url = API.SHOW_VENDOR_KART;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_minimum_quantity);
        builder = new AlertDialog.Builder(VendorMinimumQuantity.this);
        sessionManage=new SessionManage(this);

        createProgressBar();

        final JSONObject jsonObject = new JSONObject();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i< dataList.size();i++) {
                    succes = true;
                    String name = dataList.get(i).getVegname();
                    String quantity = dataList.get(i).getMinQty();
                    String code = dataList.get(i).getPcode();
                    Log.e("prpr",name.toString());
                    Log.e("prpr",quantity.toString());
                    if(!quantity.equals(""))
                    {
                        try {
                            jsonObject.put("productCode"+i,code);
                            jsonObject.put("qty"+i,quantity);

                            if(i >= blank.size()){
                                blank.add(i,"1");
                            }else{
                                // index exists
                                blank.set(i,"1");
                            }

                            Log.e("ap",dataList.get(i).getEditText());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        if(i >= blank.size()){
                            blank.add(i,"0");
                        }else{
                            // index exists
                            blank.set(i,"0");
                        }

                    }



                }

                for(int p=0 ; p< blank.size();p++){

                    if(blank.get(p).toString().equals("0")){
                        succes=false;




                    }}
                if(succes){



                    Log.e("prpr",jsonObject.toString());
                    updatePrice(jsonObject);
                    builder.setMessage("Your min quantity updated").setTitle("Update");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent=new Intent(getApplicationContext(),Vender_Update_price.class);
                            startActivity(intent);

                            sessionManage.set_Add_Product(false);
                            finish();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }else {
                    Log.e("pri","plese enter value for all product");
                    builder.setMessage("plese enter rate for all product").setTitle("error");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    builder.setCancelable(false);
                    builder.show();

               }

            }
        });

        recyclerView = findViewById(R.id.recycleMinimumQuantity);

        dataList = new ArrayList<>();
        data();
        VendorMinimumQuantityAdaptor vendorMinimumQuantityAdaptor = new VendorMinimumQuantityAdaptor(getApplicationContext(),dataList);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        recyclerView.setAdapter(vendorMinimumQuantityAdaptor);
    }



    private void updatePrice(final JSONObject jsonObject) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("response", error.toString());

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("min_qty",jsonObject.toString());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_vendor_session();

                String c_code = user.get(sessionManage.CODE);
                params.put("v_code",c_code);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(this).addTorequestque(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void data() {




            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("selec", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i =0 ; i<= jsonArray.length() ; i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String selectedVegName = jsonObject.getString("pname");
                            Log.e("selectedVegName", selectedVegName);
                            String selectedVegimage = jsonObject.getString("img");
                            String pcode=jsonObject.getString("productcode");
                            Log.e("selectedVegimage", selectedVegimage);
                            String minQty = jsonObject.getString("min_qty");
                            Log.e("min", minQty);

                            dataList.add(new VendorMinimumQuantityData(selectedVegimage,selectedVegName,"",pcode,minQty));
                        }

                    } catch (JSONException e) {
                        Log.e("error", e.toString());

                        e.printStackTrace();
                    }

                    VendorMinimumQuantityAdaptor adeptor_all_veg = new VendorMinimumQuantityAdaptor(getApplicationContext(), dataList);
                    adeptor_all_veg.notifyDataSetChanged();
                    recyclerView.setItemViewCacheSize(dataList.size());
                    recyclerView.setAdapter(adeptor_all_veg);
                    progressBar.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("errorResponce", error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params=new HashMap<String,String>();
                     HashMap<String,String> user = new HashMap<>();

                    user = sessionManage.get_vendor_session();

                    String c_code = user.get(sessionManage.CODE);
                    params.put("v_code",c_code);
                    return params;
                }};
        stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



        }

    public void createProgressBar(){
        progressBar= new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading assets");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }

}
