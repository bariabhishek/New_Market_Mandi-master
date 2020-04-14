package com.freshbrigade.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.freshbrigade.market.Activity.Client_Side;
import com.freshbrigade.market.ClientFragment.Veg_Home;
import com.freshbrigade.market.ModelsData.OrdeProductModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Summmry extends AppCompatActivity {

    RecyclerView recyclerView ;
    List<OrdeProductModel> dataList;

    String[] paymentType={"SELECT PAYMENT TYPE","CREDIT","CASH","BOTH"};

    JSONObject jsonObject,newJsonObject ,jsonObjectLast;
    Double total = 0.0;
    TextView totalamount;
    TextView checkout;
    String url =API.ORDER;
    private AlertDialog.Builder builder;
    Map<String, String> postParam= new HashMap<String, String>();
    Spinner spinner;

    String paymentMethod;
    ArrayList<OrdeProductModel> ordeProductModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summmry);


        spinner = findViewById(R.id.simpleSpinner);
        totalamount = findViewById(R.id.totalamount);
        checkout=findViewById(R.id.chackitem);
        ordeProductModels = new ArrayList<>();
        newJsonObject =new JSONObject();
   //     chackUserDetail();

        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String total = "0";
        ordeProductModels.clear();
        ordeProductModels = Veg_Home.ordeProductModels;
        for (int i=0;i<ordeProductModels.size();i++){


            OrdeProductModel ordeProductModel = ordeProductModels.get(i);
            total = String.valueOf(Float.parseFloat(total)+Float.parseFloat(ordeProductModel.getQuantity())*Float.parseFloat(ordeProductModel.getVegprice()));
            Log.e("textig",ordeProductModel.getQuantity()  );
        }

        totalamount.setText(total);



//       // jsonString=intent.getStringExtra("productData");
//        try {
//             jsonObject = new JSONObject(bundle.getString("obj"));
//            Log.d("cv", jsonObject.toString());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,paymentType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    paymentMethod = String.valueOf(spinner.getSelectedItem());

                    Gson gson = new Gson();

                    String listString = gson.toJson(
                            Veg_Home.ordeProductModels,
                            new TypeToken<ArrayList<OrdeProductModel>>() {}.getType());
                    JSONArray jsArray = new JSONArray(listString);

                    Log.e("ORDERJSONARRAY",listString);
                    placeOrder(jsArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                builder = new AlertDialog.Builder(Summmry.this);
                builder.setMessage("Your order placed succesfully!").setTitle("Order Placed");
                AlertDialog dialog = builder.create();
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(Summmry.this, Client_Side.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setCancelable(false);

                builder.show();
                //
                //  builder.show();
            }
        });
//        try {
//
//
//            //jsonObject=new JSONObject(intent.getStringExtra("slected_product"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        recyclerView = findViewById(R.id.SummaryRecycle);
        dataList = new ArrayList<>();

//        try {
//            //data();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        SummearyAdeptor summearyAdeptor  = new SummearyAdeptor(this,ordeProductModels);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(summearyAdeptor);


    }

    @Override
    public void onBackPressed() {
        Veg_Home.ordeProductModels.clear();
        ordeProductModels.clear();

        super.onBackPressed();
    }

//    private void chackUserDetail() {
//        SessionManage sessionManage = new SessionManage(getApplicationContext());
//        //JSONObject jsonObject= getIntent().getParcelableExtra("slected_product");
//        //jsonObjectLast=new JSONObject();
//
//        HashMap<String,String > user = new HashMap<>();
//        user = sessionManage.get_client_session();
//        String cose_c =  user.get(sessionManage.ACTIVATION_CODE);
//
//        Log.e("aaa",cose_c );
//
//
//        if(cose_c.equals("0")){
//            Intent intent = new Intent(getApplicationContext(),ClientRegistration.class);
//            intent.putExtra("productData",jsonObjectLast.toString());
//            startActivity(intent);
//            finish();
//        }
//    }

//    private void data() throws JSONException {
//
//        for (int i=0 ; i<jsonObject.length() ; i++ ){
//            String name=jsonObject.getString("pname"+i);
//            String quantity=jsonObject.getString("quantity"+i);
//            String amount=jsonObject.getString("price"+i);
//            Log.e("amoount", amount);
//            Double price=Double.parseDouble(quantity)*Double.parseDouble(amount);
//            total = price+total;
//            Log.e("total", String.valueOf(total) );
//            totalamount.setText(String.valueOf(total));
//            dataList.add(new SummaryData(name,quantity,amount,String.valueOf(price)));
//
//
//
//            newJsonObject.put("productCode"+i,jsonObject.getString("productCode"+i));
//            newJsonObject.put("quantity"+i,quantity);
//            newJsonObject.put("pname"+i,name);
//            newJsonObject.put("v_code"+i,jsonObject.getString("v_code"+i));
//            newJsonObject.put("price"+i,amount);
//
//        }
//        Toast.makeText(getApplicationContext(),String.valueOf(total),Toast.LENGTH_LONG).show();
//
//    }
    private void  placeOrder(final String data_order) throws JSONException {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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

                SessionManage sessionManage = new SessionManage(getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_client_session();

                String c_code = user.get(sessionManage.CODE);

                params.put("c_code",c_code);
                params.put("payment_methode","CASH/CREDIT");
                params.put("product_data",data_order);
                params.put("cash","");
                params.put("wallet","");
                //Log.e("pri",jsonObjectLast.toString());

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



