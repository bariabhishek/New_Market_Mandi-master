package com.freshbrigade.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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

public class Rate extends AppCompatActivity {

    String urlrate =API.GET_PRICE;
    RecyclerView recyclerView ;
    List<ReteSetGet> list;
    TextView product,history1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
       // colourChangeWorking();
        recyclerView = findViewById(R.id.recycleRate);



        list = new ArrayList<>();

        volley();



        RateAdaptor rateAdaptor = new RateAdaptor(getApplicationContext() , list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rateAdaptor);


    }

//    private void colourChangeWorking() {
//
//        product = findViewById(R.id.product_text_activity);
//        history1 = findViewById(R.id.history_text_activity);
//        TextView minimn_quantity = findViewById(R.id.mini_quan_axy);
//
//        minimn_quantity.setBackgroundResource(R.drawable.item_slected);
//
//        history1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
//                startActivity(intent);
//
//            }
//        });
//        product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Vender_selection_Item.class);
//                startActivity(intent);
//            }
//        });
//    }

    private void volley() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlrate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("rdvsvdbf",response );

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0 ; i<jsonArray.length() ; i ++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String image = jsonObject.getString("img");
                        Log.e("stufbfbs", image);

                        String date = jsonObject.getString("dat");
                        Log.e("stufbfbsdvdbdbd", date);

                        String pname = jsonObject.getString("pname");
                        Log.e("statufbfbfbs", pname);

                        String today_price = jsonObject.getString("today_price");
                        Log.e("statufbf", today_price);

                        String yesterday_price = jsonObject.getString("yesterday_price");
                        Log.e("sttufbf", yesterday_price);


                        list.add(new ReteSetGet(today_price,yesterday_price,pname,image,date));



                    }
                    RateAdaptor rateAdaptor = new RateAdaptor(getApplicationContext(),list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(rateAdaptor);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("rdvsv",error.toString() );

            }
        }){

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                SessionManage sessionManage = new SessionManage(getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_vendor_session();

                String v_code = user.get(sessionManage.CODE);
                params.put("v_code",v_code);
                Log.e("12345",v_code );
                return  params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }



}
