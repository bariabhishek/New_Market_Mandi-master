package com.freshbrigade.market;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vender_slection_item_Adeptor extends RecyclerView.Adapter<Vender_slection_item_Adeptor.ViewHolder> {

    String kartItem;
    static List<Data_All_veg> copyList;
    int product_sn;
   // static boolean selectionStart;
    String url=API.VENDORE_PRODUCT_SELECTION;
    SessionManage sessionManage ;
    Context context;
    String v_code;
    List<Data_All_veg> list;

    public Vender_slection_item_Adeptor(Context context, List<Data_All_veg> list) {
        this.context = context;
        this.list = list;
        copyList=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public Vender_slection_item_Adeptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.vender_slection_item_data,viewGroup,false);
      //  SessionManage sessionManage = new SessionManage(context);
        HashMap<String,String> user = new HashMap<>();
        sessionManage = new SessionManage(context);
        user = sessionManage.get_vendor_session();

         v_code = user.get(sessionManage.CODE);
        return new Vender_slection_item_Adeptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Vender_slection_item_Adeptor.ViewHolder viewHolder,  final int i) {
      //  selectionStart = true;
        Log.e("fsdfsdf","fdgfdgd");
        viewHolder.name.setText(list.get(i).getName());
        product_sn = Integer.parseInt(list.get(i).getSn());
        kartItem = copyList.get(product_sn).getAdd_kart();

        RequestOptions requestOptions = new RequestOptions();


        RequestOptions requestOptions1 = new RequestOptions();


        if (deviceOnWifi()) {
            Glide.with(context).load(list.get(i).imageView).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).transition(DrawableTransitionOptions.withCrossFade())
                    .into(viewHolder.imageView);
        } else {
            Glide.with(context)
                    .load(list.get(i).imageView)
                    .thumbnail(
                            Glide.with(context).load(list.get(i).imageView)
                    )
                    .apply(requestOptions1)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //  progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            // progressBar.setVisibility(View.GONE);
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(viewHolder.imageView);
        }

       /* Glide.with(context)
                .load(list.get(i).imageView)
                .into(viewHolder.imageView);*/


        if (kartItem.equals("1")) {
            Log.e("fsdfsdf",v_code);
            viewHolder.name.setBackgroundResource(R.drawable.item_slected);
        } else {

           viewHolder.name.setBackgroundResource(R.drawable.black);
        }
        viewHolder.selectLayout.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                Log.e("fsdfsdf","fdgfdgd");

                product_sn = Integer.parseInt(list.get(i).getSn());
                kartItem = list.get(i).getAdd_kart();

                if (kartItem.equals("0")) {
                    String action = "add";
                    viewHolder.name.setBackgroundResource(R.drawable.item_slected);
                    copyList.get(product_sn).setAdd_kart("1");
                    // String ccode = "v101";
                    String code = list.get(i).getCode();
                    addCart(code, v_code, action);
                    sessionManage.set_Add_Order(true);
                    sessionManage.set_AddProduct("add", 1);


                } else {
                    String action = "remove";
                    String code = list.get(i).getCode();

                    Log.e("action", code);
                    viewHolder.name.setBackgroundResource(R.drawable.black);
                    copyList.get(product_sn).setAdd_kart("0");
                    addCart(code, v_code, action);
                    sessionManage.set_AddProduct("remove", 1);


                }
            }


        });


//
//                product_sn = Integer.parseInt(list.get(i).getSn());
//        kartItem = list.get(i).getAdd_kart();
//
//        if (kartItem.equals("0")) {
//            Log.e("fsdfsdf","fdgfdgd");
//            String action = "add";
//           viewHolder.name.setBackgroundResource(R.drawable.item_slected);
//            copyList.get(product_sn).setAdd_kart("1");
//            // String ccode = "v101";
//            String code = list.get(i).getCode();
//           // addCart(code, v_code, action);
//            sessionManage.set_Add_Order(true);
//            sessionManage.set_AddProduct("add", 1);
//
//
//        } else {
//            String action = "remove";
//            String code = list.get(i).getCode();
//
//            Log.e("action", code);
//            viewHolder.name.setBackgroundResource(R.drawable.black);
//            copyList.get(product_sn).setAdd_kart("0");
//           // addCart(code, v_code, action);
//            sessionManage.set_AddProduct("remove", 1);
//
//           // Intent intent = new Intent(context, Vender_Update_price.class);
//           // context.startActivity(intent);
//           // ((Activity) context).finish();
//        }
    }

    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,btn,rbtn;
        EditText editText;
         LinearLayout selectLayout;

        TextView txt_add_kart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            imageView = itemView.findViewById(R.id.a_vegi);
            name = itemView.findViewById(R.id.a_vaginame);
            btn = itemView.findViewById(R.id.a_additem);
            rbtn = itemView.findViewById(R.id.a_additemremove);
            editText = itemView.findViewById(R.id.edittext2);
            selectLayout = itemView.findViewById(R.id.layoutSelect);
        }
    }
    private void addCart(final String code, final String vcode, final String action) {
        Log.e("response", "vghvjvj");

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
                params.put("prodcutcode", code);
                params.put("v_code", vcode);
                params.put("action", action);
                Log.e("action", "add to kart " + vcode + code + action);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(context).addTorequestque(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private boolean deviceOnWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }
}
