package com.freshbrigade.market;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.freshbrigade.market.Activity.ListOfVegetablefor_Vender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vender_Update_price extends Activity {

    RecyclerView recyclerView;
    List<Data_vender_price_updata> list;
    String url = API.SHOW_VENDOR_KART;
    String url2 = API.UPDATE_PRICE;
    ProgressDialog progressBar;
    Button fab;
    ArrayList blank = new ArrayList();
    Boolean succes= true;
    LinearLayout addProduct;

    private AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender__update_price);
        createProgressBar();
        builder = new AlertDialog.Builder(Vender_Update_price.this);
        addProduct = findViewById(R.id.addtocart);
        final JSONObject jsonObject = new JSONObject();

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListOfVegetablefor_Vender.class);
                startActivity(intent);
                finish();
            }
        });

        fab =  findViewById(R.id.fabNext);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i< list.size();i++) {
                    succes = true;
                    View view1 = recyclerView.getChildAt(i);
                    //  EditText etPrice =view1.findViewById(R.id.edittext2);
                    String code = list.get(i).getPcode();


                    String price = list.get(i).getTodayprice();

                    if(!price.equals("") && !price.equals("0") )
                    {
                        try {
                            jsonObject.put("productCode"+i,code);
                            Log.e("priyyyyyyy",jsonObject.put("productCode"+i,code)+"");
                            jsonObject.put("price"+i,price);
                            Log.e("priyyyyyyy11111",jsonObject.put("productCode"+i,code)+"");

                            if(i >= blank.size()){
                                blank.add(i,"1");
                            }else{
                                // index exists
                                blank.set(i,"1");
                            }

                            Log.e("ap",list.get(i).getEdittext());


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
                        //  etPrice.setError("Please enter price");
                    }



                }

                for(int p=0 ; p< blank.size();p++){

                    if(blank.get(p).toString().equals("0")){
                        succes=false;




                    }}
                if(succes){


                    updatePrice(jsonObject);
                    builder.setMessage("Your today price updated").setTitle("Update");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent=new Intent(getApplicationContext(),ListOfVegetablefor_Vender.class);
                            startActivity(intent);
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

        recyclerView = findViewById(R.id.recycle5);


        list = new ArrayList<>();


        data();
        //  Adeptor_vender_price_update adeptor_all_veg = new Adeptor_vender_price_update(getApplicationContext(), list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        // recyclerView.setAdapter(adeptor_all_veg);
        progressBar.dismiss();
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
                params.put("price_data",jsonObject.toString());
                SessionManage sessionManage = new SessionManage(getApplicationContext());
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
                        String todayPrice = jsonObject.getString("today_rate");
                        Log.e("today",todayPrice);

                        list.add(new Data_vender_price_updata(selectedVegimage,selectedVegName,"","","",pcode,todayPrice));
                    }

                } catch (JSONException e) {
                    Log.e("error", e.toString());

                    e.printStackTrace();
                }

                Adeptor_vender_price_update adeptor_all_veg = new Adeptor_vender_price_update(Vender_Update_price.this, list);
                adeptor_all_veg.notifyDataSetChanged();
                recyclerView.setItemViewCacheSize(list.size());
                recyclerView.setAdapter(adeptor_all_veg);

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
                SessionManage sessionManage = new SessionManage(getApplicationContext());
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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
