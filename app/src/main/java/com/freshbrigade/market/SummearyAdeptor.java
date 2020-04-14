package com.freshbrigade.market;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freshbrigade.market.ModelsData.OrdeProductModel;

import java.util.List;

public class SummearyAdeptor extends RecyclerView.Adapter<SummearyAdeptor.ViewHolder> {
    Context context;
    List<OrdeProductModel> list;

    public SummearyAdeptor(Context context, List<OrdeProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.summary_layout,viewGroup,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Float a,b;

       a = Float.parseFloat(list.get(i).getQuantity());
       b = Float.parseFloat(list.get(i).getVegprice());

       Float total = a*b;

       viewHolder.prise.setText(total+"");


        viewHolder.name.setText(list.get(i).getVegname());
        //Toast.makeText(context,list.get(i).getName(),Toast.LENGTH_LONG).show();
        //Log.d("getName", list.get(i).getName());
        viewHolder.quantity.setText(list.get(i).getQuantity());



       // viewHolder.prise.setText("₹ "+list.get(i).getVegprice());
        viewHolder.rate.setText(list.get(i).getVegprice());






    }

    private void click() {

        Intent intent = new Intent(context,FullDetails.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView name,quantity,prise,rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.ll);
            name = itemView.findViewById(R.id.nameSummary);
            quantity = itemView.findViewById(R.id.Quantity);
            prise = itemView.findViewById(R.id.priceAmmount);
            rate = itemView.findViewById(R.id.rateSummary);
        }
    }
}
