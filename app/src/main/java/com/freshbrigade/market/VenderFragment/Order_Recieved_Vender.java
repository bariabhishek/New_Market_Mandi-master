package com.freshbrigade.market.VenderFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshbrigade.market.API;
import com.freshbrigade.market.AdeptorOrder;
import com.freshbrigade.market.MySingleton;
import com.freshbrigade.market.Orderdata;
import com.freshbrigade.market.R;
import com.freshbrigade.market.SessionManage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order_Recieved_Vender extends Fragment {

    View view;


    RecyclerView recyclerView;
    List<Orderdata> data_all_vegs;
    ProgressDialog progressBar;
    String url= API.ORDER_INFO;
    TextView product,rate,noorede ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.order_recieved_vender,container,false);

        noorede = view.findViewById(R.id.emptyRecycle009);
        recyclerView =view.findViewById(R.id.recycle009);
        data_all_vegs = new ArrayList<>();

        data();
        createProgressBar();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public static Order_Recieved_Vender newInstance(String text) {
        Order_Recieved_Vender f = new Order_Recieved_Vender();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    private void data() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response );
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0 ; i< jsonArray.length() ; i ++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String payment = jsonObject.getString("payment_method");
                        Log.e("paymen",payment );
                        String price = jsonObject.getString("amount");
                        Log.e("price",price );
                        String orderid = jsonObject.getString("order_id");
                        String date = jsonObject.getString("order_date");
                        String orderstatus = jsonObject.getString("order_status");
                        String orderdata = jsonObject.getString("order_data");
                        String clicent_name = jsonObject.getString("client_name");
                        Log.e("status",orderstatus );
                        Log.e("orrdata",orderdata );
                        data_all_vegs.add(new Orderdata(payment,orderstatus,price,orderid,date,orderdata,clicent_name));



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AdeptorOrder adeptorOrder = new AdeptorOrder(getActivity(),data_all_vegs);
                adeptorOrder.notifyDataSetChanged();
                recyclerView.setAdapter(adeptorOrder);
                recyclerView.setItemViewCacheSize(data_all_vegs.size());
                progressBar.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();
                SessionManage sessionManage = new SessionManage(getActivity());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_vendor_session();

                String c_code = user.get(sessionManage.CODE);

                params.put("userType","vender");

                params.put("code",c_code);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getActivity()).addTorequestque(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void createProgressBar(){
        progressBar= new ProgressDialog(getActivity());

        progressBar.setMessage("Loading Data");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }


}
