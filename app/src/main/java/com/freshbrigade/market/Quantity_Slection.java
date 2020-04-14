package com.freshbrigade.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshbrigade.market.Activity.Client_Side;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quantity_Slection extends AppCompatActivity {


    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;
    List<QuantityData> list;
    String url =API.SHOW_KART;

    Boolean succes= true;
    ImageView imageView;
    ImageView less;
    EditText ET_quantity;
    String value;
    TextView textView;
    private AlertDialog.Builder builder;
    ArrayList blank = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity__slection);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Client_Side.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycle9Quatity);
        builder = new AlertDialog.Builder(Quantity_Slection.this);

        list = new ArrayList<>();
        data();
        Quantity_Adaptor_class quantity_adaptor_class = new Quantity_Adaptor_class(getApplicationContext(), list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(quantity_adaptor_class);

        final JSONObject jsonObject = new JSONObject();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(int i=0;i< list.size();i++) {
                    succes = true;



                    String code = list.get(i).getPcode();
                    String quantity = list.get(i).getQty();
                    String vegname=list.get(i).getName();
                    String vegprice=list.get(i).getAmount();
                    String vcode=list.get(i).getVcode();
                    String min_qty=list.get(i).getMin_qty();

                    Log.e("price==",vegprice);


                    if(!quantity.equals("")  && !quantity.equals("0") )
                    {
                        try {
                            jsonObject.put("productCode"+i,code);
                            jsonObject.put("quantity"+i,quantity);
                            jsonObject.put("pname"+i,vegname);
                            jsonObject.put("v_code"+i,vcode);
                            jsonObject.put("price"+i,vegprice);
                            jsonObject.put("min_qty"+i,min_qty);
                            if(i >= blank.size()){
                                blank.add(i,"1");
                            }else{
                                // index exists
                                blank.set(i,"1");
                            }




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
                    Log.e("pri",jsonObject.toString());
                    Intent intent = new Intent(getApplicationContext(),Summmry.class);
                    intent.putExtra("productData",jsonObject.toString());
                    startActivity(intent);
                }else {
                    Log.e("pri","plese enter value for all product");
                    builder.setMessage("please enter  quantity for  product").setTitle("error");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    builder.setCancelable(false);
                    builder.show();


                }


            }
        });




    }



    private void data() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String vegname = jsonObject.getString("pname");//+"\n("+ jsonObject.getString("hindi_pname")+")";

                        String price = jsonObject.getString("price");
                        Log.e("price",price);
                        String pcode=jsonObject.getString("productcode");
                        String vcode=jsonObject.getString("v_code");
                        String mini_qty=jsonObject.getString("min_qty");
                        String img = jsonObject.getString("img");
                        Log.e("img",img);
                         list.add(new QuantityData("", vegname, price,pcode,vcode,"",mini_qty));
                        Log.e("Response",response);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("volleychack", response);
                Quantity_Adaptor_class q1= new Quantity_Adaptor_class(getApplicationContext(), list);
                q1.notifyDataSetChanged();
                recyclerView.setItemViewCacheSize(list.size());
                recyclerView.setAdapter(q1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();
                SessionManage sessionManage = new SessionManage(getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_client_session();

                String c_code = user.get(sessionManage.CODE);
                Log.e("volleychack", c_code);
                params.put("c_code",c_code);
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