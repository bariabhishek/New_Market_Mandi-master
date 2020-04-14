package com.freshbrigade.market.VenderFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class Money_payments extends Fragment {

    RecyclerView recyclerView;
    List<Money_Payment_SetGet> list;


    String url = "https://freshbrigade.com/b2b/api/vendor_amount.php";


    public Money_payments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_payments, container, false);

        recyclerView = view.findViewById(R.id.recycleMoneyPayment);
        list = new ArrayList<>();
        data();



        return view;
    }

    private void data() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONArray jsonArray = new JSONArray(response);

                    Log.e("voifvnj", response );

                    for (int i = 0 ; i < jsonArray.length() ; i++ ){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String c_name=jsonObject.getString("c_name");
                       String c_code=jsonObject.getString("c_code");
                        list.add(new Money_Payment_SetGet(c_name,c_code));
                    }
                    Money_Payment_Adaptor money_payment_adaptor = new Money_Payment_Adaptor(getActivity(),list);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(money_payment_adaptor);
                    money_payment_adaptor.notifyDataSetChanged();

                } catch (JSONException e) {

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

                SessionManage sessionManage = new SessionManage(getActivity());
                HashMap<String,String> user = new HashMap<>();


                user = sessionManage.get_vendor_session();


                String v_code = user.get(sessionManage.CODE);


                Log.e("hgccj",v_code);
                Log.e("hgcdddddd","dvc");


                params.put("v_code",v_code);
                params.put("action","client");

                return params;
            }
        };

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


//        for (int i = 0 ; i< 10 ; i++)
//        list.add(new Money_Payment_SetGet("nirmal","12342"));
    }

    public static Money_payments newInstance(String text) {
        Money_payments f = new Money_payments();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

}
