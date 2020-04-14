package com.freshbrigade.market.ModelsData;

public class AmountDetailModel
{
    String due_total,total_amount,amount,due_amount,paid_amount,pay_date;

    public AmountDetailModel(String due_total, String total_amount, String amount, String due_amount, String paid_amount, String pay_date) {
        this.due_total = due_total;
        this.total_amount = total_amount;
        this.amount = amount;
        this.due_amount = due_amount;
        this.paid_amount = paid_amount;
        this.pay_date = pay_date;
    }

    public String getDue_total() {
        return due_total;
    }

    public void setDue_total(String due_total) {
        this.due_total = due_total;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }
}
