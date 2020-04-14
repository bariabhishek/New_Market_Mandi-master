package com.freshbrigade.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

public class Quantity_Adaptor_class extends RecyclerView.Adapter<Quantity_Adaptor_class.ViewHolder> {
     String valuenumber;
    Context context;
    static List<QuantityData> list;
    public Quantity_Adaptor_class(Context context, List<QuantityData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.quantity_data_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {


        viewHolder.amount.setText("₹ "+list.get(i).getAmount()+"/kg");
        viewHolder.productname.setText(list.get(i).getName());
        viewHolder.ET_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            list.get(i).setQty(s.toString());
            }
        });

        viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

//                viewHolder.repuueeetext.setVisibility(View.GONE);
//                viewHolder.amount.setVisibility(View.VISIBLE);
                //viewHolder.delete.setVisibility(View.VISIBLE);
//                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        list.remove(viewHolder.getAdapterPosition());
//                        notifyDataSetChanged();
//                    }
//
//                });

                return false;
            }



        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView plus,less,delete;
        EditText ET_quantity;
        TextView amount,productname,repuueeetext,tv_show_prcieAmount;
        int value;
        CardView cardView;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            plus = itemView.findViewById(R.id.Q_add_button);
            less = itemView.findViewById(R.id.Q_minus_button);
            ET_quantity = itemView.findViewById(R.id.Q_editext);
            amount = itemView.findViewById(R.id.Q_amount);
            productname = itemView.findViewById(R.id.Q_productname);
           cardView = itemView.findViewById(R.id.card);
           repuueeetext = itemView.findViewById(R.id.rupeetext);
           delete = itemView.findViewById(R.id.delete);




            plus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(ET_quantity.getText().toString().isEmpty()) {
                        ET_quantity.setText("0.100");

                    }else {
                        String plus_Value =ET_quantity.getText().toString();

                        Log.e("pdfpsdfsdf",plus_Value.toString());
                        switch(plus_Value){

                            case "0" :
                                ET_quantity.setText("0.100");
                                break;
                            case "0.100" :
                                ET_quantity.setText("0.250");
                                break;
                            case "0.250" :
                                ET_quantity.setText("0.500");
                                break;
                            case "0.500" :
                                ET_quantity.setText("1.0");
                                break;

                            default:
                                Double value=Double.parseDouble(plus_Value);
                                value++;
                                ET_quantity.setText(String.valueOf(value));


                        }

                    }






//                    value = Integer.parseInt(ET_quantity.getText().toString());
//                    value++;
//                    ET_quantity.setText(String.valueOf(value));
                }
            });
            less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(ET_quantity.getText().toString().isEmpty()){
                        ET_quantity.setText("0");
                    }
                    else {

                        String less_Value =ET_quantity.getText().toString();

                        Log.e("pdfpsdfsdf",less_Value.toString());
                        switch(less_Value){

                            case "1.0" :
                                ET_quantity.setText("0.500");
                                break;
                            case "0.500" :
                                ET_quantity.setText("0.250");
                                break;
                            case "0.250" :
                                ET_quantity.setText("0.100");
                                break;
                            case "0.100" :
                                ET_quantity.setText("0");
                                break;

                            default:
                                Double value=Double.parseDouble(less_Value);

                                if(value > 0){
                                    value--;
                                    ET_quantity.setText(String.valueOf(value));
                                }


                        }



//
//                    value = Integer.parseInt(ET_quantity.getText().toString());
//                    value--;
//                    ET_quantity.setText(String.valueOf(value));
                    }}
            });





        }
    }
}
