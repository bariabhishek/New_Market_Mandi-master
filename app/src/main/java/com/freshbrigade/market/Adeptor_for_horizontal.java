package com.freshbrigade.market;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import java.util.List;

public class Adeptor_for_horizontal extends RecyclerView.Adapter<Adeptor_for_horizontal.ViewHolder> {

    Context context;
    List<Horizontal_data> list;

    public Adeptor_for_horizontal(Context context, List<Horizontal_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adeptor_for_horizontal.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.horizontal_data_recycle,viewGroup,false);

        return new Adeptor_for_horizontal.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adeptor_for_horizontal.ViewHolder viewHolder, int i) {






        viewHolder.rbtn.setText(list.get(i).getDate());
        viewHolder.rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.rbtn.setBackgroundColor(Color.RED);
                viewHolder.rbtn.setTextColor(Color.WHITE);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            rbtn = itemView.findViewById(R.id.tt);

        }
    }
}
