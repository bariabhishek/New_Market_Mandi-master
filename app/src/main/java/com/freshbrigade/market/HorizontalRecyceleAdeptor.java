package com.freshbrigade.market;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorizontalRecyceleAdeptor extends RecyclerView.Adapter<HorizontalRecyceleAdeptor.ViewHolder> {

    String vendore_select;
    int vendore_sn;
    Context ctx;
    List<Dl2> dl2s,copyList;
   String Vendore_Selection_Api =API.CLIENT_VENDORE_SELECTION;
    public HorizontalRecyceleAdeptor(Context ctx, List<Dl2> dl2s) {
        this.ctx = ctx;
        this.dl2s = dl2s;
        copyList=new ArrayList<>(dl2s);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View viewitem = layoutInflater.inflate(R.layout.venders_item_data,viewGroup,false);
        return new ViewHolder(viewitem);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder view, final int i) {





        view.name.setText(dl2s.get(i).getName());
        view.price.setText("₹ "+dl2s.get(i).getPrice()+"/kg");
        String v = dl2s.get(i).vType;
        String vtype = v.substring(0, 1);
        view.v_type.setText("(" + vtype + ")");
        view.rating.setText(dl2s.get(i).getRating());

        String today_price =(dl2s.get(i).getToday_price());
        String yesterday_price =(dl2s.get(i).getYesterday_price());

        Log.e("qweewqewqeq",today_price +"  "+yesterday_price);




        try {
            if(Integer.parseInt(today_price) == Integer.parseInt(yesterday_price)){
                view.redArrow.setVisibility(View.GONE);
                view.greenArrow.setVisibility(View.VISIBLE);
                Log.e("edjvnnv",today_price );
                Log.e("edjvnnvfbb",yesterday_price );
                Log.e("qweewqewqeq","1");
            }
            if(Integer.parseInt(today_price) > Integer.parseInt(yesterday_price)) {
                view.redArrow.setVisibility(View.VISIBLE);
                view.greenArrow.setVisibility(View.GONE);
                Log.e("qweewqewqeq","2");
            }
            if(Integer.parseInt(today_price) < Integer.parseInt(yesterday_price)){
                view.redArrow.setVisibility(View.GONE);
                view.greenArrow.setVisibility(View.VISIBLE);
                Log.e("qweewqewqeq","3");
            }
        } catch(NumberFormatException nfe) {
            Log.e("cccccc " , nfe.toString());
        }


        vendore_sn = Integer.parseInt(dl2s.get(i).getSn());
        vendore_select = dl2s.get(vendore_sn).getSelect_vendor();

        if (vendore_select.equals("1")) {
            view.vendore_select_layout.setBackgroundResource(R.drawable.vendorrs_rate_green);
            view.imageView.setVisibility(View.VISIBLE);
        } else {
            view.vendore_select_layout.setBackgroundResource(R.drawable.vendors_rate_background_design);
            view.imageView.setVisibility(View.GONE);
        }

        view.minQty.setText(dl2s.get(i).getMinQty() + "KG");
        view.vendore_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                vendore_sn = Integer.parseInt(dl2s.get(i).getSn());
                vendore_select = dl2s.get(i).getSelect_vendor();

                if (vendore_select.equals("0")) {

                    for (int d = 0; d < dl2s.size(); d++) {
                        dl2s.get(d).setSelect_vendor("0");

                    }
                    String action = "add";
                    view.imageView.setVisibility(View.VISIBLE);
                    view.vendore_select_layout.setBackgroundResource(R.drawable.vendorrs_rate_green);
                    dl2s.get(vendore_sn).setSelect_vendor("1");
                    SessionManage sessionManage = new SessionManage(ctx);
                    HashMap<String, String> user = new HashMap<>();

                    user = sessionManage.get_client_session();

                    String c_code = user.get(sessionManage.CODE);
                  //  String ccode = c_code;
                    String code = dl2s.get(i).getV_code();
                    String product_code = dl2s.get(i).getProduct_code();
                    addVendore(code, c_code, action, product_code);
                    notifyDataSetChanged();

                }





            }
        });


    }

    private void addVendore(final String code, final String ccode, final String action ,final String product_code) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Vendore_Selection_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("v_code", code);
                params.put("c_code", ccode);
                params.put("action", action);
                params.put("product_code", product_code);
                Log.e("action", "vendor selected " + ccode + code + action);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(ctx).addTorequestque(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public int getItemCount() {
        return dl2s.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,v_type,minQty,rating,redArrow,greenArrow;
        ImageView imageView;
        LinearLayout vendore_select_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rating=itemView.findViewById(R.id.priviousday);
            v_type= itemView.findViewById(R.id.vType);
            name = itemView.findViewById(R.id.venname);
            price = itemView.findViewById(R.id.venprice);
            minQty= itemView.findViewById(R.id.minQty);
            vendore_select_layout = itemView.findViewById(R.id.vendor_select_layout);
            rating = itemView.findViewById(R.id.priviousday);
            greenArrow  = itemView.findViewById(R.id.high_price_arrow);
            redArrow   = itemView.findViewById(R.id.law_price_arrow);
            imageView = itemView.findViewById(R.id.slecteditemImage);
        }
    }

}
