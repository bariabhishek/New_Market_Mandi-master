package com.freshbrigade.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adep extends RecyclerView.Adapter<Adep.ViewHolder> {

    Context context;
    List<DataList> list ;
    List<Dl2> dataLists;
    HorizontalRecyceleAdeptor adeptor;
    Context ct;
    RecyclerView recyclerView;
    String p_code;
    int k=0;
    String pos;

    public Adep(Context context, List<DataList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =layoutInflater.inflate(R.layout.home,viewGroup,false);
        ct=  viewGroup.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,  int i) {




        Glide.with(context)
                .load(list.get(i).getImageView())
                .into(viewHolder.vagipic);

        viewHolder.name.setText(list.get(i).getName());
        p_code =list.get(i).getCode();
        pos=list.get(i).getPossion();


        k=i;
        dataLists=new ArrayList<>();


        data(list.get(i).getAdd_kart(), pos,p_code);


        String ty = list.get(i).getVenderType();
        Log.e("chack323232", ty);
        data(list.get(i).getAdd_kart(), pos,ty);
        Log.e("data",String.valueOf(dataLists.size()) );
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);



    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vagipic;
        TextView name,price;
        TextView minQty,vType;





        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            vType = itemView.findViewById(R.id.vType);

            vagipic = itemView.findViewById(R.id.vegi);
            name = itemView.findViewById(R.id.vaginame);
            price = itemView.findViewById(R.id.price);

            recyclerView = itemView.findViewById(R.id.recycle2);



        }
    }

    private void data(String add_kart,String pos,String p_code) {

        boolean matched = false;
        try {
            JSONArray jsonArray = new JSONArray(add_kart);

            for (int j=0 ; j<jsonArray.length() ; j ++ ){
                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                String today_price = jsonObject1.getString("today_price");
                String yesterday_price = jsonObject1.getString("yesterday_price");
                Log.e("yesterday_price",yesterday_price );
                String v_P_code = jsonObject1.getString("v_P_code");
                String v_code = jsonObject1.getString("v_code");
                Log.e("v_code",v_code );

                String vendor_type = jsonObject1.getString("v_type");
                Log.e("vendor_ty",vendor_type);
                //   String data=jsonObject1.getString("data");
                String min_product = jsonObject1.getString("min_qty");
                Log.e("min_product",min_product);

                String v_name = jsonObject1.getString("v_name");
                String sn = jsonObject1.getString("sn");
                String select_vendore = jsonObject1.getString("v_selection");
                String select_ratinge = jsonObject1.getString("v_rating");


                Log.e("qqqppp", String.valueOf(v_P_code) );
                if(p_code.equals(v_P_code)){
                    //  Log.e("count", p_code +"  _   "+ code + "   array "+ String.valueOf(j));
                    dataLists.add(new Dl2(v_code, v_name, today_price, "", String.valueOf(j), select_vendore, v_P_code, pos, vendor_type, min_product,select_ratinge,today_price,yesterday_price));

                    matched =true;

                }

            }
            HorizontalRecyceleAdeptor HR_adep = new HorizontalRecyceleAdeptor(context,dataLists);
            HR_adep.notifyDataSetChanged();
            recyclerView.setItemViewCacheSize(dataLists.size());

            recyclerView.setAdapter(HR_adep);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
            // recyclerView.setVisibility(View.GONE);
        }


    }

}
