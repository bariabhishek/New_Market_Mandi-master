package com.freshbrigade.market.ModelsData;

public class ModelData007
{
    String order_date,amount,order_id;

    public ModelData007(String order_date, String amount, String order_id) {
        this.order_date = order_date;
        this.amount = amount;
        this.order_id = order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
