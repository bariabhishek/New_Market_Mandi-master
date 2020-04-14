package com.freshbrigade.market;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Adeptor_All_veg extends RecyclerView.Adapter<Adeptor_All_veg.ViewHolder> implements Filterable {
    Boolean refreshData = false;
    Context context;
    static List<Data_All_veg> list,copyList;
    private List<Data_All_veg> listremove;
    int j;
    List<Horizontal_data> list2;
    static List collection = new ArrayList();
    static boolean selectionStart;
    static boolean fillArray=false;
    String REGISTER_USER_API = API.ADD_TO_KART;
    String kartItem;
    SessionManage sessionManage;
    HashMap<String,String> user;
    String c_code;
    String price_of_items;
    //int lastPosition=-1;


    int product_sn;

    public Adeptor_All_veg(Context context, List<Data_All_veg> list) {
        this.context = context;
        this.list = list;
        listremove = new ArrayList<>(list);
        copyList=new ArrayList<>(list);
         sessionManage = new SessionManage(context);
      user = new HashMap<>();
        user = sessionManage.get_client_session();
        c_code = user.get(sessionManage.CODE);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.all_veg_layout_data, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        selectionStart = true;
        viewHolder.name.setText(list.get(i).getName());
        product_sn= Integer.parseInt(list.get(i).getSn());
        kartItem = copyList.get(product_sn).getAdd_kart();

        viewHolder.less.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               price_of_items = list.get(i).getPrice();
               Log.e("vffffff", price_of_items );

               if(viewHolder.ET_quantity.getText().toString().isEmpty()){
                   viewHolder.ET_quantity.setText("0");
               }
               else {
                   if (Integer.parseInt(viewHolder.ET_quantity.getText().toString())<= 0){
                       viewHolder.ET_quantity.setText("0");
                   }
                   else {

                       String less_Value =(viewHolder.ET_quantity.getText().toString());
                       int value = Integer.parseInt(less_Value);
                       value--;
                       viewHolder.ET_quantity.setText(String.valueOf(value));

                       //  Toast.makeText(context,price_of_items,Toast.LENGTH_LONG).show();

                       int k = (value*Integer.parseInt(price_of_items));
                       viewHolder.tv_show_prcieAmount.setText("₹ "+(k));
                       list.get(i).setGetAmt(j+"");
                       list.get(i).setQty(value+"");

                   }

                   //Log.e("pdfpsdfsdf",less_Value.toString());
//                        switch(less_Value){
//
//                            case "1.0" :
//                                ET_quantity.setText("0.500");
//                                break;
//                            case "0.500" :
//                                ET_quantity.setText("0.250");
//                                break;
//                            case "0.250" :
//                                ET_quantity.setText("0.100");
//                                break;
//                            case "0.100" :
//                                ET_quantity.setText("0");
//                                break;
//
//                            default:
//                                Double value=Double.parseDouble(less_Value);
//
//                                if(value > 0){
//                                    value--;
//                                    ET_quantity.setText(String.valueOf(value));
//                                }
//
//
//                        }

               }


           }
       });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                price_of_items = list.get(i).getPrice();
                Log.e("vffffff", price_of_items );

                if(viewHolder.ET_quantity.getText().toString().isEmpty())
                {
                    viewHolder.ET_quantity.setText("0");
                }
                else {

                    String plus_Value = (viewHolder.ET_quantity.getText().toString());
                    int value = Integer.parseInt(plus_Value);

                    value++;
                    viewHolder.ET_quantity.setText(String.valueOf(value));
                   // Log.e("testingMode" ,String.valueOf(value));


                    int j = (value*Integer.parseInt(price_of_items));
                    //    Toast.makeText(context,price_of_items,Toast.LENGTH_LONG).show();
                    viewHolder.tv_show_prcieAmount.setText("₹ "+j);
                    // ET_quantity.setText(plus_Value);
                    list.get(i).setGetAmt(j+"");
                    list.get(i).setQty(value+"");

                  //

                    //Log.e("testingMode2", list.toString() );
                }

            }
        });

      // Toast.makeText(context,price_of_items,Toast.LENGTH_LONG).show();

       viewHolder.price_show.setText("₹ "+list.get(i).getPrice()+" "+"/"+" kg");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions.priority(Priority.IMMEDIATE);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);

        RequestOptions requestOptions1=new RequestOptions();
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
        requestOptions1.onlyRetrieveFromCache(true);
        requestOptions1.skipMemoryCache(false);
        requestOptions1.priority(Priority.IMMEDIATE);
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.DATA);


        if (deviceOnWifi())
        {
            Glide.with(context).load(list.get(i).getImageView()).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }) .transition(DrawableTransitionOptions.withCrossFade())
                    .into(viewHolder.imageView);
        }
        else
        {
            Glide.with(context)
                    .load(list.get(i).getImageView())
                    .thumbnail(
                            Glide.with(context).load(list.get(i).getImageView())
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

      /*  Glide.with(context)
                .load(list.get(i).imageView)
                .into(viewHolder.imageView);*/


//        if (kartItem.equals("1") ) {
//
//            //viewHolder.name.setBackgroundResource(R.drawable.item_slected);
//        } else {
//
//          // viewHolder.name.setBackgroundResource(R.drawable.black);
//        }




        viewHolder.selectLayout.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                product_sn=Integer.parseInt(list.get(i).getSn());
                kartItem =list.get(i).getAdd_kart();;

                        if (kartItem.equals("0")) {

                            String action = "add";
                            //viewHolder.name.setBackgroundResource(R.drawable.item_slected);
                            copyList.get(product_sn).setAdd_kart("1");
                            String code = list.get(i).getCode();
                            //addCart(code, c_code, action);
                            SessionManage sessionManage = new SessionManage(context);
                            sessionManage.set_Add_Order(true);
                            sessionManage.set_AddProduct("add",1);

                        } else {
                            String action = "remove";
                            String code = list.get(i).getCode();
                           // viewHolder.name.setBackgroundResource(R.drawable.black);
                            copyList.get(product_sn).setAdd_kart("0");
                          //  addCart(code, c_code, action);
                            sessionManage.set_AddProduct("remove",1);

                        }
                    }




        });

        list2 = new ArrayList<>();
        Adeptor_for_horizontal adep = new Adeptor_for_horizontal(context, list2);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        viewHolder.recyclerView.setAdapter(adep);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        RecyclerView recyclerView;
        LinearLayout selectLayout;
        CardView cardView;
        TextView price_show,ET_quantity,tv_show_prcieAmount;
        ImageView plus,less;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.a_vegi);
            name = itemView.findViewById(R.id.a_vaginame);
            cardView = itemView.findViewById(R.id.cardViewClick);
            selectLayout = itemView.findViewById(R.id.layoutSelect);
            recyclerView = itemView.findViewById(R.id.recycle8);
            price_show = itemView.findViewById(R.id.tv_prcie_show);
            plus = itemView.findViewById(R.id.Q_add_button);
            less = itemView.findViewById(R.id.Q_minus_button);
            ET_quantity = itemView.findViewById(R.id.Q_editext);
            tv_show_prcieAmount = itemView.findViewById(R.id.tv_show_prcie_amount);

            plus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

//                    if(ET_quantity.getText().toString().isEmpty())
//                    {
//                        ET_quantity.setText("0");
//                    }
//                    else {
//
//                        String plus_Value = (ET_quantity.getText().toString());
//                        int value = Integer.parseInt(plus_Value);
//                        value++;
//                        ET_quantity.setText(String.valueOf(value));
//
//                        int i = (value*Integer.parseInt(price_of_items));
//                    //    Toast.makeText(context,price_of_items,Toast.LENGTH_LONG).show();
//                        tv_show_prcieAmount.setText(String.valueOf(i));
//                       // ET_quantity.setText(plus_Value);
//                    }

                       // Log.e("pdfpsdfsdf",plus_Value.toString());
//                        switch(plus_Value){
//
//                            case "0" :
//                                ET_quantity.setText("0.100");
//                                break;
//                            case "0.100" :
//                                ET_quantity.setText("0.250");
//                                break;
//                            case "0.250" :
//                                ET_quantity.setText("0.500");
//                                break;
//                            case "0.500" :
//                                ET_quantity.setText("1.0");
//                                break;
//
//                            default:
//                                Double value=Double.parseDouble(plus_Value);
//                                value++;
//                                ET_quantity.setText(String.valueOf(value));
//
//                        }

                //    }
                }
            });
            less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private void addCart(final String code, final String ccode, final String action) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_USER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response222", response);
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
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("prodcutcode", code);
                params.put("c_code", ccode);
                params.put("action", action);
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


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Data_All_veg> filtered = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                filtered.addAll(listremove);
            } else {
                String filterpatern = constraint.toString().toLowerCase().trim();

                for (Data_All_veg item : listremove) {
                    if (item.getName().toLowerCase().contains(filterpatern)) {
                        filtered.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private boolean deviceOnWifi()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }
}