package com.freshbrigade.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FullDetailsAdeptor extends RecyclerView.Adapter<FullDetailsAdeptor.ViewHolder> {
    Context context ;
    List<FullDetailData> list;

    public FullDetailsAdeptor(Context context, List<FullDetailData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_full_detail,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.status.setText(list.get(i).getPatani());
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.veginame.setText(list.get(i).getVeginame());
        viewHolder.Quantity.setText(list.get(i).getQuantity());
        viewHolder.rateSummary.setText(list.get(i).getPriceAmmount());




        Double a = Double.parseDouble(list.get(i).getQuantity());
        Double b = Double.parseDouble(list.get(i).getPriceAmmount());
        String totalrate = String.valueOf(a*b);
       // Log.e("totalrate",totalrate );
        viewHolder.priceAmmount.setText("₹ "+totalrate);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,status,veginame,Quantity,rateSummary,priceAmmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.vendoeName);
            status = itemView.findViewById(R.id.status);
            veginame = itemView.findViewById(R.id.veginame);
            Quantity = itemView.findViewById(R.id.Quantity);
            rateSummary = itemView.findViewById(R.id.rateSummary);
            priceAmmount = itemView.findViewById(R.id.priceAmmount);

        }
    }
}
