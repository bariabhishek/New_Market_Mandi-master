package com.freshbrigade.market.ClientFragment;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshbrigade.market.API;
import com.freshbrigade.market.History_ka_Adaptor;
import com.freshbrigade.market.History_ka_data;
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


public class His_tory extends Fragment
{

    View view;
    RecyclerView recyclerView;
    List<History_ka_data> data_all_vegs;
    TextView product ,history,venders,wallet;
    String url= API.ORDER_INFO;
    ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.hist_ory,container,false);

        createProgressBar();
        recyclerView = view.findViewById(R.id.recycleoo9);
        data_all_vegs = new ArrayList<>();
        data();
        History_ka_Adaptor adeptor_all_veg = new History_ka_Adaptor(getActivity(),data_all_vegs);




        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    public static His_tory newInstance(String text) {
        His_tory f = new His_tory();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    private void data() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    Log.e("sas",response);
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String orderId=jsonObject.getString("order_id");
                        String orderAmount=jsonObject.getString("amount");
                        String paymentMethod=jsonObject.getString("payment_method");
                        String orderStatus=jsonObject.getString("order_status");
                        String amountStatus=jsonObject.getString("amount_status");
                        String orderDate=jsonObject.getString("order_date");
                        String orderTime=jsonObject.getString("order_time");
                        String from_cash=jsonObject.getString("from_cash");
                        String from_wallet=jsonObject.getString("from_wallet");
                        String order_data = jsonObject.getString("order_data");
                        data_all_vegs.add(new History_ka_data(orderId,orderAmount,paymentMethod,orderStatus,amountStatus,orderDate,orderTime,order_data,from_cash,from_wallet));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("volleychack", response);
                History_ka_Adaptor q1= new History_ka_Adaptor(getActivity(), data_all_vegs);
                q1.notifyDataSetChanged();
                recyclerView.setAdapter(q1);
                recyclerView.setItemViewCacheSize(data_all_vegs.size());

                progressBar.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();

                SessionManage sessionManage = new SessionManage(getActivity().getApplicationContext());

                HashMap<String,String > user = new HashMap<>();
                user = sessionManage.get_client_session();
                String cose_c =  user.get(sessionManage.CODE);

              //  Log.d("cose_c",cose_c);

                params.put("userType","client");
                params.put("code",cose_c);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getActivity().getApplicationContext()).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }


    public void createProgressBar(){
        progressBar= new ProgressDialog(getActivity());
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading.... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }

}
