package com.freshbrigade.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshbrigade.market.Activity.ListOfVegetablefor_Vender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullDetailForVendor extends AppCompatActivity {

    RecyclerView  recyclerView ;
    List<FullDetailForVendorData> fullDetailForVendorDataList;
    TextView orderID2,date,paymentMethod,pese;
    Spinner spinner;
    JSONArray jsonArray;
    FullDetailForVendorAdaptor fullDetailForVendorAdaptor;
    TextView clientName_vender;
    String status;
    private AlertDialog.Builder builder;
    String[] paymetnMethodString = {"Select One","Pending","Accept"};
     JSONObject jsonObject;
     String url = API.ORDER_UPDATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail_for_vendor);


        spinner = findViewById(R.id.spinner);
        orderID2 = findViewById(R.id.orderIdV);
        date = findViewById(R.id.dateV);
        paymentMethod = findViewById(R.id.paymentMethodV);
        pese = findViewById(R.id.pese);
        clientName_vender = findViewById(R.id.clientName);
        jsonObject = new JSONObject();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(int i=0;i< fullDetailForVendorDataList.size();i++) {
                    String code = fullDetailForVendorDataList.get(i).getpCode();
                    String quantity = fullDetailForVendorDataList.get(i).getEditQty();
                    String price = fullDetailForVendorDataList.get(i).getPriceAmount();


                    fullDetailForVendorDataList.get(i).setQuantity(quantity);

                    fullDetailForVendorAdaptor.notifyDataSetChanged();
                    recyclerView.setAdapter(fullDetailForVendorAdaptor);
                    try {
                        jsonObject.put("productCode"+i,code);

                        jsonObject.put("quantity"+i,quantity);
                        jsonObject.put("price"+i,price);

                        Log.e("apl",jsonObject.toString());
                        Log.e("apl",orderID2.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                placeOrder();

            }



        });


       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String item = parent.getItemAtPosition(position).toString();

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,paymetnMethodString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent =getIntent();


        String orderid1 = intent.getStringExtra("orderidforve");

        String Date = intent.getStringExtra("dateoFClientOrder");

        String payment = intent.getStringExtra("paymentmethodOFcientOrder");

        String client_Name = intent.getStringExtra("clientName");

        String pese1 = intent.getStringExtra("totalprice");
         status = intent.getStringExtra("status");



        pese.setText("₹ "+pese1);


        clientName_vender.setText(client_Name);
        orderID2.setText(orderid1);
        date.setText(Date);
        paymentMethod.setText(payment);

        recyclerView = findViewById(R.id.fullDetailsVender);
        fullDetailForVendorDataList = new ArrayList<>();

         fullDetailForVendorAdaptor = new FullDetailForVendorAdaptor(getApplicationContext(),fullDetailForVendorDataList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
             jsonArray = new JSONArray(getIntent().getStringExtra("abhishek"));

            volleywork(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(fullDetailForVendorAdaptor);

        }

        private void volleywork(JSONArray jsonArray) throws JSONException {
        Double totalOfRate = 0.0;
        for (int i =0 ; i < jsonArray.length() ; i ++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String pname = jsonObject.getString("v_code");
            String productname = jsonObject.getString("product_name");
            String price = jsonObject.getString("price");
            String qty = jsonObject.getString("qty");
            String orderstatus = jsonObject.getString("order_status");
            String rateSummery = jsonObject.getString("price");
            String vname = jsonObject.getString("v_name");
            String ccode = jsonObject.getString("c_code");
            String pCode = jsonObject.getString("product_code");

//            String clientname = jsonObject.getString("client_name");
            Log.e("clientvvvname",qty );

            Double a = Double.parseDouble(rateSummery);
            Double b = Double.parseDouble(qty);
            totalOfRate =  totalOfRate + (a * b);
            Log.e("000000000",status);
            fullDetailForVendorDataList.add( new  FullDetailForVendorData("",orderstatus,productname,qty,rateSummery,price,totalOfRate,qty,pCode, status));

        }
        Log.e("totalchack", String.valueOf(totalOfRate));
        Intent intent = getIntent();
        intent.putExtra("totalOf",totalOfRate);
    }


    private void  placeOrder()  {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                         try {
                            JSONObject jsonObjectRE = new JSONObject(response);

                            String code = jsonObjectRE.get("status").toString();
                           //  Log.e("response1233", code);

                            if(code.equals("ok"));
                            {


                                builder = new AlertDialog.Builder(FullDetailForVendor.this);
                                builder.setMessage("Your order update succesfully!").setTitle("Order Update");
                               // AlertDialog dialog = builder.create();
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent=new Intent(FullDetailForVendor.this, ListOfVegetablefor_Vender.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                builder.setCancelable(false);

                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

                SessionManage sessionManage = new SessionManage(getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_vendor_session();

                String c_code = user.get(sessionManage.CODE);

                params.put("v_code",c_code);
                params.put("update_data",jsonObject.toString().trim());
                Log.e("aaaa", jsonObject.toString().trim() );
                params.put("order_id",orderID2.getText().toString().trim());



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
   }
