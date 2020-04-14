package com.freshbrigade.market.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.freshbrigade.market.Adapter.Money_Payment_Adaptor;
import com.freshbrigade.market.Adapter.Money_Payment_SetGet;
import com.freshbrigade.market.ModelsData.AmountDetailModel;
import com.freshbrigade.market.MySingleton;
import com.freshbrigade.market.R;
import com.freshbrigade.market.SessionManage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Amount_Detail extends AppCompatActivity {

    TextView due_total,total_amount,amount,due_amount,paid_amount,pay_date;
   String URL = "https://freshbrigade.com/b2b/api/vendor_amount.php";
    List<AmountDetailModel> amount_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount__detail);
        due_total=findViewById(R.id.due_total);
        total_amount=findViewById(R.id.total_amount);
        amount=findViewById(R.id.amountDetail);
        due_amount=findViewById(R.id.due_amount);
        paid_amount=findViewById(R.id.paid_amount);
        pay_date=findViewById(R.id.pay_date);


        AmountDetail();
    }

    public void AmountDetail()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                amount_details=new ArrayList<>();
                Log.e("hhhhh", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);




                    for (int i = 0 ; i < jsonArray.length() ; i++ ){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String due_total1=jsonObject.getString("due_total");
                         String total_amount1=jsonObject.getString("total_amount");
                         String amount1=jsonObject.getString("amount");
                         String due_amount1=jsonObject.getString("due_amount");
                         String paid_amount1=jsonObject.getString("paid_amount");
                         String pay_date1=jsonObject.getString("pay_date");


                         due_total.setText(due_total1);
                         total_amount.setText(total_amount1);
                         amount.setText(amount1);
                         due_amount.setText(due_amount1);
                         paid_amount.setText(paid_amount1);
                         pay_date.setText(pay_date1);



                    }




                } catch (JSONException e) {
                    Log.e("hhhhhssssss", e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("hhhhhssssfffbss", error.toString());

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                Intent intent=getIntent();
                String data=intent.getStringExtra("order_id");
                SessionManage sessionManage=new SessionManage(Amount_Detail.this);
                HashMap<String,String> hashMap;
                hashMap = sessionManage.get_vendor_session();
                String v_code=hashMap.get(sessionManage.CODE);
             /*   Log.e("hgj",v_code);
                Log.e("hdskjh",data);*/

                params.put("v_code",v_code);
                Log.e("hgkhfdh",v_code);
                params.put("action","amount");
                params.put("order_id",data);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
