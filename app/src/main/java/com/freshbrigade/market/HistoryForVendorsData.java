package com.freshbrigade.market;

class HistoryForVendorsData {

    private String orderId,orderAmount,paymentMethod,orderStatus,amountStatus,orderDate,orderTime;

    public HistoryForVendorsData(String orderId, String orderAmount, String paymentMethod, String orderStatus, String amountStatus, String orderDate, String orderTime) {
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.amountStatus = amountStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
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
}
