package com.freshbrigade.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;




public class VendorMinimumQuantityAdaptor extends RecyclerView.Adapter<VendorMinimumQuantityAdaptor.ViewHolder> {



    Context context;
    List<VendorMinimumQuantityData> list;

    public VendorMinimumQuantityAdaptor(Context applicationContext, List<VendorMinimumQuantityData> dataList) {
        context= applicationContext;
        list = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.vendor_minimum_quantity_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.quantity.setText(list.get(i).getEditText());
        viewHolder.vagiName.setText(list.get(i).getVegname());
        viewHolder.quantity.setText(list.get(i).getMinQty());
        Glide.with(context)
                .load(list.get(i).getImage())
                .into(viewHolder.image);


        viewHolder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            list.get(i).setEditText(s.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView vagiName;
        EditText quantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.a_vegiMQ);
            vagiName = itemView.findViewById(R.id.a_vaginameMQ);
            quantity = itemView.findViewById(R.id.edittext2MQ);
        }
    }
}
