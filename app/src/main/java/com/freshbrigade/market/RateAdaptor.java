package com.freshbrigade.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RateAdaptor extends RecyclerView.Adapter<RateAdaptor.ViewHolder> {
    Context context ;
    List<ReteSetGet> list;

    public RateAdaptor(Context applicationContext, List<ReteSetGet> list) {
        context = applicationContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater  = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rate_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Glide.with(context)
                .load(list.get(i).getImage())
                .into(viewHolder.imageView);



        viewHolder.yesterday.setText("₹ "+list.get(i).getYesrate());
        viewHolder.today.setText("₹ "+list.get(i).getRate());
        viewHolder.pname.setText(list.get(i).getPname());
        viewHolder.date.setText(list.get(i).getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView today , yesterday , pname ,date;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            today = itemView.findViewById(R.id.todayrate);
            yesterday = itemView.findViewById(R.id.yesterdayrate);
            pname = itemView.findViewById(R.id.pName);
            imageView = itemView.findViewById(R.id.produce_image);
            date = itemView.findViewById(R.id.todaydate);

        }
    }
}
