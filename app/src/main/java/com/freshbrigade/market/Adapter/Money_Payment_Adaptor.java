package com.freshbrigade.market.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.freshbrigade.market.ModelsData.ModelData007;
import com.freshbrigade.market.MySingleton;
import com.freshbrigade.market.R;
import com.freshbrigade.market.SessionManage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Money_Payment_Adaptor extends RecyclerView.Adapter<Money_Payment_Adaptor.ViewHolder007> {


    Context context;
    List<Money_Payment_SetGet> list;

    public Money_Payment_Adaptor(FragmentActivity activity, List<Money_Payment_SetGet> list) {
        context = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder007 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.money_payment_layout,viewGroup,false);
        return new ViewHolder007(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder007 viewHolder, final int i) {

        viewHolder.clientName.setText(list.get(i).getName());
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.doubleCLick==false)
                {
                    viewHolder.recyclerView.setVisibility(View.VISIBLE);
                    viewHolder.doubleCLick=true;

                }
                else
                {
                    viewHolder.recyclerView.setVisibility(View.GONE);
                    viewHolder.doubleCLick=false;
                }
            }
        });


      //  viewHolder.clientCode.setText(list.get(i).getCode());
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, viewHolder.JSonURl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Log.e("wsdfqdx",response);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String order_id=jsonObject.getString("order_id");
                        Log.e("wqdkjx",order_id);
                        String order_date=jsonObject.getString("order_date");
                        Log.e("wqkkkkkkkkdkjx",order_date);
                        String amount=jsonObject.getString("amount");
                        Log.e("wzzzzzzqdkjx",amount);
                        viewHolder.list1.add(new ModelData007(order_date,amount,order_id));

                    }
                    SecondRecycleRecycleData recycleAdapterHai=new SecondRecycleRecycleData(context,viewHolder.list1);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    viewHolder.recyclerView.setLayoutManager(linearLayoutManager);
                    viewHolder.recyclerView.setAdapter(recycleAdapterHai);
                    recycleAdapterHai.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                SessionManage sessionManage=new SessionManage(context);
                HashMap<String,String> hashMap,hashMap1;
                hashMap1=sessionManage.get_client_session();
                hashMap = sessionManage.get_vendor_session();
                String v_code=hashMap.get(sessionManage.CODE);
                String c_code=list.get(i).getCode();
                Log.e("qwgdiyw",v_code );
                Log.e("tytyt",c_code );
                params.put("v_code",v_code);
                params.put("action","order");
                params.put("c_code",c_code);
                return params;
            }
        };
        stringRequest.setShouldCache(false);


        requestQueue.add(stringRequest);

//        MySingleton.getInstance(context).addTorequestque(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder007 extends RecyclerView.ViewHolder {
        TextView clientName,clientCode;
        List<ModelData007> list1;
        RecyclerView recyclerView;
        RelativeLayout relativeLayout;
        Boolean doubleCLick=false;
        String JSonURl = "https://freshbrigade.com/b2b/api/vendor_amount.php";
        public ViewHolder007(View itemView) {
            super(itemView);

            clientName = itemView.findViewById(R.id.clientName1);
            relativeLayout=itemView.findViewById(R.id.rl009);
            recyclerView=itemView.findViewById(R.id.recyleViewData);
            list1=new ArrayList<>();
           // clientCode = itemView.findViewById(R.id.clientCode1);
        }
    }
}
