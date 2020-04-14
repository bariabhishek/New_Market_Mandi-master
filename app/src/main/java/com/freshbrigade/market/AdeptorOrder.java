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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdeptorOrder extends RecyclerView.Adapter<AdeptorOrder.ViewHolder> {

    Context context;
    List<Orderdata> list;
    String status;
    String date_Chack;

    public AdeptorOrder(Context context, List<Orderdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.order,viewGroup,false);

        return new AdeptorOrder.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        viewHolder.orderId.setText(list.get(i).orderId);
        viewHolder.date.setText(list.get(i).getDate());
        viewHolder.price.setText("₹"+list.get(i).getPrice());
        viewHolder.paymentMethodv.setText(list.get(i).getPaymentMethodv());
        viewHolder.orderStatusv.setText(list.get(i).orderStatusv);

        String date = new SimpleDateFormat("dd-MM-Y", Locale.getDefault()).format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-Y");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        sdf = new SimpleDateFormat("dd-MM-Y");
        Date resultdate = new Date(c.getTimeInMillis());
        String dateInString = sdf.format(resultdate);

        date_Chack = list.get(i).getDate();

        if(!date.equals(list.get(i).getDate()) && !dateInString.equals(list.get(i).getDate())){
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#ECECEC"));
            viewHolder.cardView.setBackgroundColor(Color.parseColor("#ECECEC"));
        }

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                datasendingOnORderForVender(i);

            }
        });

    }

    private void datasendingOnORderForVender(int i) {
        String ordrdata = list.get(i).orderdata;
        String date = list.get(i).date;
        String paymentethor = list.get(i).paymentMethodv;
        String orderid = list.get(i).orderId;
        String totalprice = list.get(i).price;
        String client_Name = list.get(i).client_name;
        Log.e("clienName",client_Name);
        status = list.get(i).getOrderStatusv();



        Intent intent = new Intent(context,FullDetailForVendor.class);


        intent.putExtra("totalprice",totalprice);

        intent.putExtra("orderidforve",orderid);
        Log.e("orderidforve ",orderid);
        intent.putExtra("dateoFClientOrder",date);
        Log.e("dateoFClientOrder ",date);
        intent.putExtra("paymentmethodOFcientOrder",paymentethor);
        Log.e("paymentmethodOFcient",paymentethor);
        intent.putExtra("abhishek",ordrdata);
        Log.e("abhi ",ordrdata);
        intent.putExtra("clientName",client_Name);
        intent.putExtra("status",status);





        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView paymentMethodv,orderStatusv,price,orderId,date;
        LinearLayout linearLayout;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.caddd);
            orderId = itemView.findViewById(R.id.orderIdV);
            linearLayout = itemView.findViewById(R.id.lll);
            date = itemView.findViewById(R.id.Datev);
            paymentMethodv = itemView.findViewById(R.id.paymentMethodv);
            orderStatusv = itemView.findViewById(R.id.orderStatusv);
            price = itemView.findViewById(R.id.orderAmountv);

        }
    }
}
