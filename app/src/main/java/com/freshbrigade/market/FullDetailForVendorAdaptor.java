package com.freshbrigade.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class FullDetailForVendorAdaptor extends RecyclerView.Adapter<FullDetailForVendorAdaptor.ViewHolder> {
    int a=-1;
    Context context;
    List<FullDetailForVendorData> list;

    public FullDetailForVendorAdaptor(Context context, List<FullDetailForVendorData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_full_detail_for_vendor,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {




        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Log.e("vdfd",list.get(i).getStatus() );

                if(list.get(i).getStatus().equals("pending"))
                {
                    if(a==i){
                        viewHolder.quanityEdittext.setVisibility(View.GONE);
                        a++;
            }else {
                        viewHolder.quanityEdittext.setVisibility(View.VISIBLE);
                        a=i;
                    }
            }}
        });




        viewHolder.veginame.setText(list.get(i).getVeginame());
        viewHolder.Quantity.setText(list.get(i).getQuantity());
        viewHolder.rateSummary.setText(list.get(i).getRateSummary());
        viewHolder.priceAmmount.setText(list.get(i).getPriceAmount());

        Double a = Double.parseDouble(list.get(i).getQuantity());
        Double b = Double.parseDouble(list.get(i).getRateSummary());
        String totalrate = String.valueOf(a*b);
        Log.e("totalrate",totalrate );
        viewHolder.rateSummary.setText("= ₹ "+totalrate);


        viewHolder.quanityEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(i).setEditQty(String.valueOf(s));
               // String a = list.get(i).getQuantity();
                Log.e("text", String.valueOf(s) );
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name,status,veginame,Quantity,rateSummary,priceAmmount;
            RelativeLayout relativeLayout;
            EditText quanityEdittext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.ll);
            name = itemView.findViewById(R.id.vendoeName);
            status = itemView.findViewById(R.id.status);
            veginame = itemView.findViewById(R.id.veginame);
            Quantity = itemView.findViewById(R.id.Quantity);
            rateSummary = itemView.findViewById(R.id.priceAmmount);
            priceAmmount = itemView.findViewById(R.id.rateSummary);
            quanityEdittext = itemView.findViewById(R.id.quantitiEdittext);

        }
    }
}
