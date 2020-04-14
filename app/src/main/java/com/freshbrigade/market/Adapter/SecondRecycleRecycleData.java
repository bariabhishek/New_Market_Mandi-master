package com.freshbrigade.market.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freshbrigade.market.Activity.Amount_Detail;
import com.freshbrigade.market.ModelsData.ModelData007;
import com.freshbrigade.market.R;

import java.util.List;

public class SecondRecycleRecycleData  extends RecyclerView.Adapter<SecondRecycleRecycleData.ViewHolderSecond>
{
    Context context;
    List<ModelData007> list;

    public SecondRecycleRecycleData(Context context, List<ModelData007> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SecondRecycleRecycleData.ViewHolderSecond onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.second_recyleddata,viewGroup,false);
        return new ViewHolderSecond(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondRecycleRecycleData.ViewHolderSecond viewHolderSecond, final int i) {

        viewHolderSecond.order_date.setText("("+list.get(i).getOrder_date()+")");
        viewHolderSecond.amount.setText("₹ "+list.get(i).getAmount());
        viewHolderSecond.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Amount_Detail.class);
                ModelData007 modelData007=list.get(i);
                intent.putExtra("order_id",list.get(i).getOrder_id());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderSecond extends RecyclerView.ViewHolder {
        TextView order_date,amount;
        LinearLayout relativeLayout;
        public ViewHolderSecond(@NonNull View itemView) {
            super(itemView);
            order_date=itemView.findViewById(R.id.order_date);
            amount=itemView.findViewById(R.id.amount);
            relativeLayout=itemView.findViewById(R.id.rL89);
        }
    }
}
