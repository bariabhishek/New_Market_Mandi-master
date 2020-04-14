package com.freshbrigade.market.ClientFragment;

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
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.freshbrigade.market.API;
import com.freshbrigade.market.Adep;
import com.freshbrigade.market.DataList;
import com.freshbrigade.market.Data_All_veg;
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

public class Recycle_Main_Activity extends Fragment
{

    View view;

    static RecyclerView recyclerView;
    List<DataList> dataLists;
    Adep adep;
    RelativeLayout rl1,rl2,rl3,rl4;
    TextView btn_veg,btn_exo,btn_org,btn_fru;
    int fruit,exotic,organic;

    String item="All";
    List<Data_All_veg> data_all_vegs_list;
    String urlString= API.VENDOR_SHOW_WITH_PRODUCT;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.recycle_main_activity,container,false);
        progressBar =view.findViewById(R.id.progressbaroo9);
        progressBar.setVisibility(View.VISIBLE);
        btn_veg=view.findViewById(R.id.btn_veg2);
        btn_exo=view.findViewById(R.id.btn_exo2);
        btn_fru=view.findViewById(R.id.btn_fru2);
        /*btn_org=view.findViewById(R.id.btn_org2);*/

        rl1 = view.findViewById(R.id.rl01);
        rl2 = view.findViewById(R.id.rl02);
        rl3 = view.findViewById(R.id.rl03);
        /*rl4 = view.findViewById(R.id.rl04);*/


        btn_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl1.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.GONE);
                rl3.setVisibility(View.GONE);
                /*rl4.setVisibility(View.GONE);*/

                recyclerView.smoothScrollToPosition(0);
            }
        });
       /* btn_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.GONE);
                rl3.setVisibility(View.GONE);
                rl4.setVisibility(View.VISIBLE);

                recyclerView.smoothScrollToPosition(organic);
                Toast.makeText(getActivity(),"Not Available",Toast.LENGTH_LONG).show();
            }
        });*/
        btn_exo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.GONE);
                rl3.setVisibility(View.VISIBLE);
                /*rl4.setVisibility(View.GONE);*/

                recyclerView.smoothScrollToPosition(exotic);
            }
        });
        btn_fru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                rl3.setVisibility(View.GONE);
                /*rl4.setVisibility(View.GONE);*/

                recyclerView.smoothScrollToPosition(fruit);
            }
        });



        Volley();
        recyclerView =view. findViewById(R.id.recycleoop);
        dataLists=new ArrayList<>();
        data_all_vegs_list = new ArrayList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public static Recycle_Main_Activity newInstance(String text) {
        Recycle_Main_Activity f = new Recycle_Main_Activity();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    private void Volley() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("vendor_krat_date",jsonArray.toString());

                    for (int i = 0 ; i<jsonArray.length() ; i ++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String vegname = jsonObject.getString("product_name");//+"\n("+ jsonObject.getString("hindi_pname")+")";
                        String img = jsonObject.getString("imagepath");
                        String VendorData   = jsonObject.getString("data");
                        String code=jsonObject.getString("p_code");

                        Log.e("tezz", jsonObject.getString("data")+"" );


                        if(code.equals("FB1287")){
                            fruit=i;
                        }else if(code.equals("FB1258")){
                            exotic=i;
                        }
                      /*  else if(code.equals("FB1232")){
                            organic=i;
                        }*/


                        String possion = String.valueOf(i);
                        JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                        String jsonArra = jsonObject.getJSONArray("data").toString();
                        DataList dataList = new DataList(img,vegname,"","",code,jsonArra,possion,VendorData);
                        //  Log.e("vegbandadad",vegname);
                        dataLists.add(dataList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                    Log.e("vegbandadad",e.toString());
                }

                adep  = new Adep(getActivity(), dataLists);
                adep.notifyDataSetChanged();
                recyclerView.setItemViewCacheSize(dataLists.size());
                recyclerView.setAdapter(adep);
                progressBar.setVisibility(View.INVISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }{

            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();


                SessionManage sessionManage = new SessionManage(getActivity());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_client_session();

                String c_code = user.get(sessionManage.CODE);
                params.put("c_code",c_code);
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

}
