package com.freshbrigade.market;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.freshbrigade.market.Activity.ListOfVegetablefor_Vender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.WINDOW_SERVICE;

public class Adeptor_vender_price_update  extends RecyclerView.Adapter<Adeptor_vender_price_update.ViewHolder> {

    Context context;
    List<Data_vender_price_updata> list;
    static List<priceDataClass> priceData;
    String v_code;
    int var = 0;
    String url=API.VENDORE_PRODUCT_SELECTION;
WindowManager mWindowManager;


    public Adeptor_vender_price_update(Context context, List<Data_vender_price_updata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adeptor_vender_price_update.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.vender_update_price_data,viewGroup,false);

        return new Adeptor_vender_price_update.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adeptor_vender_price_update.ViewHolder viewHolder, final int i) {





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

       /* Glide.with(context)
                .load(list.get(i).imageView)
                .into(viewHolder.imageView);*/

        viewHolder.name.setText(list.get(i).getName());
        // viewHolder.editText.setText(list.get(i).getEdittext());




        viewHolder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  showDialog(context);


                final SessionManage sessionManage = new SessionManage(context);
                HashMap<String, String> user = new HashMap<>();
                user = sessionManage.get_vendor_session();

             //   viewHolder.mainLay.setBackgroundColor(Color.parseColor("#F37575"));

                v_code = user.get(sessionManage.CODE);

                final String code = list.get(i).pcode;

               // viewHolder.delete.setVisibility(View.VISIBLE);
                //     viewHolder.name.setVisibility(View.GONE);

//
//                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //list.remove(viewHolder.getAdapterPosition());
//
//                    }
//                });


/*
                 final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.create();
                }*/
              /*  dialog.setContentView(R.layout.custome_dialog);

                params.gravity = Gravity.CENTER;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Objects.requireNonNull(dialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    // 1. 'windowLayoutInDisplayCutoutMode' do not be set to 'never'
                    if (dialog.getWindow().getAttributes().layoutInDisplayCutoutMode == WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER) {
                        throw new RuntimeException("'windowLayoutInDisplayCutoutMode' do not be set to 'never'");
                    }
                    // 2. Do not set Activity to landscape
                    if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        throw new RuntimeException("Do not set Activity to landscape");
                    }
                }
                dialog.setCancelable(false);*/

             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(context)) {
                        askPermission();
                    }
                }*/
/*
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                final View promptView = layoutInflater.inflate(R.layout.custome_dialog, null);*/


              /*  View view=LayoutInflater.from(context).inflate(R.layout.custome_dialog,null);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.TYPE_PHONE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            WindowManager.LayoutParams.TYPE_APPLICATION_PANEL

                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                            PixelFormat.TRANSLUCENT);

                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                    params.x = 0;
                    params.y = 100;
                    mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
                    mWindowManager.addView(view,params);
                }
                else {
                    WindowManager.LayoutParams params  = new WindowManager.LayoutParams(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                            PixelFormat.TRANSLUCENT);


                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                    params.x = 0;
                    params.y = 100;
                    mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

                }


mWindowManager.removeView(view);*/
            /*    alertD.setTitle("Delete or Update");
                alertD.setMessage("Delete the item or update the value");
                alertD.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, list.size());
                        viewHolder.delete.setVisibility(View.VISIBLE);
                        // viewHolder.name.setVisibility(View.VISIBLE);
                        addCart(code, v_code, "remove");
                        sessionManage.set_AddProduct("remove", 1);
                    }
                });
                alertD.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   viewHolder.editText.setFocusable(true);
                   dialog.cancel();
                    }
                });*/



           /*     Button Update=view.findViewById(R.id.updateDialog);
                Button Delete=view.findViewById(R.id.deleteDialog);
                Button close=view.findViewById(R.id.crossClose);
                alertD.setView(view);
*/


           viewHolder.linearLayoutp.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {

                   AlertDialog.Builder alertD = new AlertDialog.Builder(context);

                   alertD.setTitle("Delete or Update");
                   alertD.setMessage("Delete the item or update the value");
                   alertD.setCancelable(true);
                   alertD.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           list.remove(i);
                           notifyItemRemoved(i);
                           notifyItemRangeChanged(i, list.size());

                           // viewHolder.name.setVisibility(View.VISIBLE);
                           addCart(code, v_code, "remove");
                           sessionManage.set_AddProduct("remove", 1);
                       }
                   });
                   alertD.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           viewHolder.editText.setFocusable(true);
                           dialog.cancel();
                       }
                   });
                   AlertDialog dialog = alertD.create();

                   dialog.show();

                   return false;
               }
           });









               /* Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, list.size());
                        viewHolder.delete.setVisibility(View.VISIBLE);
                        // viewHolder.name.setVisibility(View.VISIBLE);
                        addCart(code, v_code, "remove");
                        sessionManage.set_AddProduct("remove", 1);
                    }
                });*/


             //   alertD.show();

            }
        });

        String check = list.get(i).getTodayprice();

        if(check.equals("null")) {

           list.get(i).setTodayprice("0");
            viewHolder.editText.setText(list.get(i).getTodayprice());
          //  viewHolder.editText.setText("0");
            viewHolder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {



                    if (hasFocus) {

                        viewHolder.editText.setText("");

                    } else {

                    }
                }
            });
        }else {
            viewHolder.editText.setText(list.get(i).getTodayprice());
        }
        viewHolder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                try {
                    if( list.get(i) != null){
                        list.get(i).setTodayprice(s.toString());
                    }
                }
                catch ( IndexOutOfBoundsException e ) {
                    Log.e("ssdfs",e.toString());
                }



            }
        });




    }

    private void addCart(final String code, final String vcode, final String action) {

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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,delete;
        TextView name,btn,rbtn;
        LinearLayout linearLayoutp;
        EditText editText;
        LinearLayout mainLay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            priceData=new ArrayList();

            linearLayoutp = itemView.findViewById(R.id.vendor_price_card);
            mainLay=itemView.findViewById(R.id.main_lay);

            context=itemView.getContext();

            imageView = itemView.findViewById(R.id.a_vegi);
            name = itemView.findViewById(R.id.a_vaginame);
            btn = itemView.findViewById(R.id.a_additem);
            rbtn = itemView.findViewById(R.id.a_additemremove);
            editText = itemView.findViewById(R.id.edittext2);
            delete = itemView.findViewById(R.id.delete007);
        }
    }

    private boolean deviceOnWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }

  /*  public void showDialog(Context activity)
        {

        }*/



}
