package com.freshbrigade.market;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryForVendors extends AppCompatActivity {

    String url=API.ORDER_INFO;

    RecyclerView recyclerView;
    List<HistoryForVendorsData> historyForVendorslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_for_vendors);

        recyclerView = findViewById(R.id.recycleHistoryVendor);
        historyForVendorslist = new ArrayList<>();

        data();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void data() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("responce",response );
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String orderId = jsonObject.getString("order_id");
                        Log.e("orderId",orderId );
                        String orderAmount = jsonObject.getString("amount");
                        String paymentMethod = jsonObject.getString("payment_method");
                        String orderStatus = jsonObject.getString("order_status");
                        String amountStatus = jsonObject.getString("amount_status");
                        String orderDate = jsonObject.getString("order_date");
                        String orderTime = jsonObject.getString("order_time");
                        historyForVendorslist.add(new HistoryForVendorsData(orderId, orderAmount, paymentMethod, orderStatus, amountStatus, orderDate, orderTime));
                        Log.e("Response", response);

                    }

                } catch (JSONException e) {
                    Log.e("error",e.toString() );
                    e.printStackTrace();
                }
                HistoryVendorAdeptor historyVendorAdeptor = new HistoryVendorAdeptor(getApplicationContext(),historyForVendorslist);
                historyVendorAdeptor.notifyDataSetChanged();
                recyclerView.setAdapter(historyVendorAdeptor);


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

                user = sessionManage.get_vendor_session();

                String c_code = user.get(sessionManage.CODE);
                params.put("userType","vendor");
                params.put("code",c_code);
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
