package com.freshbrigade.market.VenderFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.freshbrigade.market.Data_All_veg;
import com.freshbrigade.market.Quantity_Slection;
import com.freshbrigade.market.R;
import com.freshbrigade.market.SessionManage;
import com.freshbrigade.market.Vender_Update_price;
import com.freshbrigade.market.Vender_slection_item_Adeptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorListItem extends Fragment {

    View view;

    String url= API.VENDOR_PRODUCT;
    List<Data_All_veg> data_all_vegs_list;
    Vender_slection_item_Adeptor adeptor_all_veg;
    int fruit,exotic,organic;
    ProgressDialog progressBar;
    static Boolean next=false;
    RecyclerView recyclerView;
    SessionManage sessionManage;
    RelativeLayout rl1,rl2,rl3,rl4;
    Button fab;
    TextView btn_veg,btn_exo,btn_org,btn_fru;
    int status;
    public static boolean first_Time =true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.vender_list_item,container,false);
        sessionManage = new SessionManage(getActivity());

        btn_veg=view.findViewById(R.id.btn_veg1);
        btn_exo=view.findViewById(R.id.btn_exo1);
        btn_fru=view.findViewById(R.id.btn_fru1);
        /*btn_org=view.findViewById(R.id.btn_org1);*/

        rl1 = view.findViewById(R.id.rl1);
        rl2 = view.findViewById(R.id.rl2);
        rl3 = view.findViewById(R.id.rl3);
        /*rl4 = view.findViewById(R.id.rl4);*/

        createProgressBar();


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
      /*  btn_org.setOnClickListener(new View.OnClickListener() {
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
         status = sessionManage.get_AddProduct();


//        if(status>0  && first_Time){
//            first_Time =false;
//
//            Intent intent=new Intent(getActivity(), Vender_Update_price.class);
//            startActivity(intent);
//        }
//        else
//        {
//            first_Time =false;
//                }


       fab =  view.findViewById(R.id.fab009);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int statuss= sessionManage.get_AddProduct();
                Log.e("aaapapap",String.valueOf(status));

                if(statuss>0){

                    Intent intent=new Intent(getActivity(), Vender_Update_price.class);
                    startActivity(intent);
                }
                else
                {

                    Toast.makeText(getActivity().getApplicationContext(),"Please select product first",Toast.LENGTH_LONG).show();
                }






            }
        });



       // next=false;
        recyclerView = view.findViewById(R.id.recycle007);
        data_all_vegs_list = new ArrayList();



        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));

        Volley();
        return view;
    }

    private void Volley() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.e("abhishek",response );

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0 ; i<= jsonArray.length() ; i ++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String vegname = jsonObject.getString("pname");//+"\n("+ jsonObject.getString("hindi_pname")+")";
                        String img = jsonObject.getString("img");
                        Log.e("volleychack", img );
                        String code=jsonObject.getString("code");
                        String price_ = jsonObject.getString("price");
                        Log.e("code123",code);

                        if(code.equals("FB1298")){
                            fruit=i;
                        }else if(code.equals("FB1256")){
                            exotic=i;
                        }
                       /* else if(code.equals("FB1299")){
                            organic=i;
                        }*/

                        String addKart =jsonObject.getString("kart");
                        String sn =jsonObject.getString("sn");
                        if(addKart.equals("1")){
                            sessionManage.set_AddProduct("setAll",i+1);
                        }
                        Data_All_veg data_all_veg = new Data_All_veg(img,vegname,"Add","Remove",code,addKart,sn,price_,""
                        ,"","","");



                        data_all_vegs_list.add(data_all_veg);
                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("volleychack", response );

                adeptor_all_veg  = new Vender_slection_item_Adeptor(getActivity(), data_all_vegs_list);

                recyclerView.setAdapter(adeptor_all_veg);
                adeptor_all_veg.notifyDataSetChanged();
                progressBar.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();

            }{

            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();
                SharedPreferences sharedPreferences;

                sharedPreferences = getActivity().getApplicationContext()
                        .getSharedPreferences("fcm", Context.MODE_PRIVATE);
                String fcmId = sharedPreferences.getString("fcmID",null);

                SessionManage sessionManage = new SessionManage(getActivity());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_vendor_session();

                String v_code = user.get(sessionManage.CODE);
                params.put("fcm",fcmId);
                params.put("v_code",v_code);

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

    public static VendorListItem newInstance(String text) {
        VendorListItem f = new VendorListItem();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void createProgressBar(){
        progressBar= new ProgressDialog(getActivity());
        // progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading assets");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }

    private boolean isNetworkAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

    @Override
    public void onStart() {
        boolean c = isNetworkAvailable();
        if (c == true) {


          fab.setVisibility(View.VISIBLE);

            fab.setClickable(true);
            //LoadJSon();
        } else {
            createProgressBar();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar.create();
            }
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();

            fab.setVisibility(View.GONE);
            fab.setClickable(false);
        }
        super.onStart();
    }
}
