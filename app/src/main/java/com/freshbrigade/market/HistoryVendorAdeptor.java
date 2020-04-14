package com.freshbrigade.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HistoryVendorAdeptor extends RecyclerView.Adapter<HistoryVendorAdeptor.ViewHolder> {
    Context context;
    List<HistoryForVendorsData> list;

    public HistoryVendorAdeptor(Context context, List<HistoryForVendorsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_history_vendor,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.orderId.setText(list.get(i).getOrderId());
        viewHolder.date.setText(list.get(i).getOrderDate());
        viewHolder.orderAmount.setText(list.get(i).getOrderAmount());
        viewHolder.paymentMethod.setText(list.get(i).getPaymentMethod());
        viewHolder.orderStatus.setText(list.get(i).getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId,date,orderAmount,paymentMethod,orderStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderId);
            date = itemView.findViewById(R.id.Date);
            orderAmount = itemView.findViewById(R.id.orderAmount);
            paymentMethod = itemView.findViewById(R.id.paymentMethod);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }
    }
}
