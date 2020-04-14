package com.freshbrigade.market;

public class History_ka_data {


    String orderId,orderAmount,paymentMethod,orderStatus,amountStatus,orderDate,orderTime,orderData,from_cash,from_wallet;

    public History_ka_data(String orderId, String orderAmount, String paymentMethod, String orderStatus, String amountStatus, String orderDate, String orderTime, String orderData, String from_cash, String from_wallet) {
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.amountStatus = amountStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderData = orderData;
        this.from_cash = from_cash;
        this.from_wallet = from_wallet;
    }

    public String getFrom_cash() {
        return from_cash;
    }

    public void setFrom_cash(String from_cash) {
        this.from_cash = from_cash;
    }

    public String getFrom_wallet() {
        return from_wallet;
    }

    public void setFrom_wallet(String from_wallet) {
        this.from_wallet = from_wallet;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAmountStatus() {
        return amountStatus;
    }

    public void setAmountStatus(String amountStatus) {
        this.amountStatus = amountStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderData() {
        return orderData;
    }

    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }
}
