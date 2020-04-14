package com.freshbrigade.market;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class History_ka_Adaptor extends RecyclerView.Adapter<History_ka_Adaptor.ViewHolder> {

    Context context;
    List<History_ka_data> list;

    public History_ka_Adaptor(Context context, List<History_ka_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public History_ka_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.history_ko_deto,viewGroup,false);

        return new History_ka_Adaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final History_ka_Adaptor.ViewHolder viewHolder, final int i) {

        String date = new SimpleDateFormat("dd-MM-yy", Locale.US).format(new Date());
        SimpleDateFormat sdf;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        Date resultdate = new Date(c.getTimeInMillis());
        String dateInString = sdf.format(resultdate);

        if(!date.equals(list.get(i).getOrderDate()) && !dateInString.equals(list.get(i).getOrderDate())){
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#ECECEC"));
            viewHolder.cardView.setBackgroundColor(Color.parseColor("#ECECEC"));
        }

        viewHolder.orderId.setText(list.get(i).getOrderId());

        viewHolder.price.setText("₹ "+list.get(i).getOrderAmount());
        viewHolder.timea.setText(list.get(i).getOrderTime());
        viewHolder.datea.setText( list.get(i).getOrderDate());
        viewHolder.paymentMethod.setText(list.get(i).getPaymentMethod());
        viewHolder.orderStatus.setText(list.get(i).getOrderStatus());
        viewHolder.cashData.setText(list.get(i).getFrom_cash());
        viewHolder.walletData.setText(list.get(i).getFrom_wallet());


        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderdata = list.get(i).getOrderData().toString();
                String orderId =list.get(i).orderId;
                String date = list.get(i).orderDate;
                String paymentMethod = list.get(i).paymentMethod;
                String cash=list.get(i).from_cash;
                String wallet=list.get(i).from_wallet;

                Intent i = new Intent(context,FullDetails.class);
                i.putExtra("orderdata",orderdata);
                i.putExtra("orderid",orderId);
                i.putExtra("orderDate",date);
                i.putExtra("paymentMethod",paymentMethod);
                i.putExtra("from_cash",cash);
                i.putExtra("from_wallet",wallet);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView orderId,price,timea,datea,orderStatus,paymentMethod,cashData,walletData;
        LinearLayout linearLayout;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.ll);

            orderId = itemView.findViewById(R.id.orderId);

            price = itemView.findViewById(R.id.orderAmount);
            timea = itemView.findViewById(R.id.time);
            datea = itemView.findViewById(R.id.Date);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            paymentMethod = itemView.findViewById(R.id.paymentMethod);
            cardView = itemView.findViewById(R.id.cad);
            cashData=itemView.findViewById(R.id.CashData);
            walletData=itemView.findViewById(R.id.WalletData);

        }
    }
}

