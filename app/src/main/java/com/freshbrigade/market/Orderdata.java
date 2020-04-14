package com.freshbrigade.market;

public class Orderdata {

    String paymentMethodv,orderStatusv,price,orderId,date,orderdata,client_name;

    public Orderdata(String paymentMethodv, String orderStatusv, String price, String orderId, String date,String orderdata,String client_name) {
        this.paymentMethodv = paymentMethodv;
        this.orderStatusv = orderStatusv;
        this.price = price;
        this.orderId = orderId;
        this.date = date;
        this.orderdata = orderdata;
        this.client_name =client_name;
    }

    public String getPaymentMethodv() {
        return paymentMethodv;
    }

    public void setPaymentMethodv(String paymentMethodv) {
        this.paymentMethodv = paymentMethodv;
    }

    public String getOrderStatusv() {
        return orderStatusv;
    }

    public void setOrderStatusv(String orderStatusv) {
        this.orderStatusv = orderStatusv;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderdata() {
        return orderdata;
    }

    public void setOrderdata(String orderdata) {
        this.orderdata = orderdata;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
