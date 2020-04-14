package com.freshbrigade.market.ClientFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.freshbrigade.market.API;
import com.freshbrigade.market.Activity.Client_Side;
import com.freshbrigade.market.Adeptor_All_veg;
import com.freshbrigade.market.Data_All_veg;
import com.freshbrigade.market.ModelsData.OrdeProductModel;
import com.freshbrigade.market.MySingleton;
import com.freshbrigade.market.Quantity_Slection;
import com.freshbrigade.market.R;
import com.freshbrigade.market.SessionManage;
import com.freshbrigade.market.Summmry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Veg_Home extends Fragment
{

    View view;

    RecyclerView recyclerView;
    List<Data_All_veg> data_all_vegs_list;
    Adeptor_All_veg adeptor_all_veg;
    public static TextView nextBtn;
    static Boolean next=false;
    String PhoneNumber;
    ProgressDialog progressBar;
    AlertDialog.Builder builder;
    String url= API.PRODUCT;
    public static boolean first_Time =true;
    TextView btn_veg,btn_exo,btn_org,btn_fru;
    Button fab;
    Boolean OrderStatus;
    RelativeLayout rl1,rl2,rl3,rl4;
    int fruit,exotic,organic;
    ArrayList blank ;

    Boolean succes= false;

    JSONObject jsonObject;

    SessionManage sessionManage;

    public static ArrayList<OrdeProductModel> ordeProductModels;

    Boolean isItemSelected = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.veg_home,container,false);

        blank = new ArrayList();
        ordeProductModels = new ArrayList<>();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("FreshBrigade","FreshBrigade", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("freshbrigade")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "SuccessFul";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    //    Log.d("FCM", msg);
                        // Toast.makeText(VegHome.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        sessionManage = new SessionManage(getActivity().getApplicationContext());


          jsonObject = new JSONObject();


        builder = new AlertDialog.Builder(getActivity());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = this.getLayoutInflater();
        View dialogView = inflater1.inflate(R.layout.new_order_dialg, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        CardView NewOrder=  dialogView.findViewById(R.id.card_new);
        CardView OldOrder=  dialogView.findViewById(R.id.card_old);
        btn_veg=view.findViewById(R.id.btn_veg2);
        btn_exo=view.findViewById(R.id.btn_exo2);
        btn_fru=view.findViewById(R.id.btn_fru2);
/*
        btn_org=view.findViewById(R.id.btn_org2);
*/
        createProgressBar();
        Volley();
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

//        NewOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createProgressBar();
//                alertDialog.dismiss();
//                sessionManage.set_Add_Order(false);
//                ResetKart();
//                int num = sessionManage.get_AddProduct();
//                sessionManage.set_AddProduct("remove",num);
//                Volley();
//            }
//        });
//
//        OldOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createProgressBar();
//                alertDialog.dismiss();
//                Volley();
//            }
//        });
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.setTitle("Select your order");
        // alertDialog.show();
        toolbar_intentpass();
        floatingButton();


//        OrderStatus = sessionManage.get_Add_Order();
//
//        if(OrderStatus && first_Time){
//            alertDialog.show();
//            first_Time =false;
//
//
//
//        }else
//        {
//
//
//            first_Time =false;
//            createProgressBar();
//            // alertDialog.dismiss();
//            Volley();
//        }

        return view;
    }

    public static Veg_Home newInstance(String text) {
        Veg_Home f = new Veg_Home();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    private void toolbar_intentpass() {
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Html.fromHtml("<font color='#fff'>ActionBartitle </font>"));*/
        Intent intent = getActivity().getIntent();
        PhoneNumber = intent.getStringExtra("mobilenumber");


        next=false;
        recyclerView = view.findViewById(R.id.recycleoo7);
        data_all_vegs_list = new ArrayList();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
      //  recyclerView.setItemViewCacheSize(data_all_vegs_list.size());
        recyclerView.hasFixedSize();
    }

    private void floatingButton() {
        fab =  view.findViewById(R.id.fab007  );
//        for(int i=0;i<data_all_vegs_list.size();i++){
//
//            Log.e("SSh",data_all_vegs_list.get(i).getQty());
//            Log.e("SSh",data_all_vegs_list.size()+"");
//        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ordeProductModels.clear();

               // int status = sessionManage. get_AddProduct();
              //  Log.e("aaapapap",String.valueOf(status));

//                if(status>0){
//
//                    Intent intent=new Intent(getActivity(), Quantity_Slection.class);
//                    startActivity(intent);
//                }
//                else
//                {
//                    Toast.makeText(getActivity().getApplicationContext(),"Please select product first",Toast.LENGTH_LONG).show();
//                }
                Bundle bundle= new Bundle();
                for(int i=0;i< data_all_vegs_list.size();i++) {
                  //  succes = true;

                    String productCode = data_all_vegs_list.get(i).getCode();
                    String quantity = data_all_vegs_list.get(i).getQty();
                    String pname=data_all_vegs_list.get(i).getName();
                    String price=data_all_vegs_list.get(i).getPrice();
                    String v_code=data_all_vegs_list.get(i).getVcode();
                    String min_qty=data_all_vegs_list.get(i).getGetminqty();

                  //  Log.d("QUANTITY:: ",price);


                    if(quantity.equals("") || quantity.equals("0")) {
                    //    Log.e("THISgggg",ordeProductModels.toString());
                    }else{
                        isItemSelected = true;
                      //  Log.e("THISgggg",ordeProductModels.toString());
                        //Log.e("THIS:",vegprice);


                        ordeProductModels.add(new OrdeProductModel(productCode, quantity, pname, price, v_code, min_qty));


                    }
                }

                if(!ordeProductModels.isEmpty()){
                    Intent intent2 = new Intent(getContext(), Summmry.class);
                    //intent2.putExtra("bundle", bundle);
                    startActivity(intent2);
                }else {
                    Toast.makeText(getContext(), "Please add product quantity.", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public  void Volley() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              //  Log.e("resdadasd",response.toString());

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0 ; i<= jsonArray.length() ; i ++){

                    //    Log.e("fvvfvv", String.valueOf(jsonArray.length()) );

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String price_ = jsonObject.getString("price");

                        String vegname = jsonObject.getString("pname");//+"\n("+ jsonObject.getString("hindi_pname")+")";
                        String img = jsonObject.getString("img");

                        String code=jsonObject.getString("code");

                        String v_code = jsonObject.getString("v_code");




                        if(code.equals("FB1298")){
                            fruit=i;
                        }else if(code.equals("FB1256")){
                            exotic=i;
                        }
                      /*  else if(code.equals("FB1299")){
                            organic=i;
                        }*/

                        String addKart =jsonObject.getString("kart");
                        String sn =jsonObject.getString("sn");
                        if(addKart.equals("1")){
                            sessionManage.set_AddProduct("setAll",i+1);
                        }

                        Data_All_veg data_all_veg = new Data_All_veg(img,vegname,"Add","Remove",code,addKart,sn,price_,"",
                                "0",v_code,"0");



                        data_all_vegs_list.add(data_all_veg);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adeptor_all_veg  = new Adeptor_All_veg(getActivity().getApplicationContext(), data_all_vegs_list);
                adeptor_all_veg.notifyDataSetChanged();
               // recyclerView.setItemViewCacheSize(data_all_vegs_list.size());
                recyclerView.setAdapter(adeptor_all_veg);
                recyclerView.setItemViewCacheSize(data_all_vegs_list.size());

                progressBar.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity().getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

            }{

            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String,String>();
                SharedPreferences sharedPreferences;

                sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("fcm", Context.MODE_PRIVATE);
                String fcmId = sharedPreferences.getString("fcmID",null);

                SessionManage sessionManage = new SessionManage(getActivity().getApplicationContext());
                HashMap<String,String> user = new HashMap<>();

                user = sessionManage.get_client_session();

                String c_code = user.get(sessionManage.CODE);

                if(fcmId.isEmpty()){
                    fcmId="not available";
                }

                params.put("c_code",c_code);
                params.put("fcm",fcmId);
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

    public void createProgressBar(){
        progressBar= new ProgressDialog(getActivity());
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Product Loading.... ");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }

    private void ResetKart() {
        SessionManage sessionManage = new SessionManage(getActivity().getApplicationContext());
        HashMap<String,String> user = new HashMap<>();

        user = sessionManage.get_client_session();

        final String c_code = user.get(sessionManage.CODE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ADD_TO_KART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Log.e("response222", response);
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
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("prodcutcode", "NOT");
                params.put("c_code", c_code);
                params.put("action", "all");
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

}
