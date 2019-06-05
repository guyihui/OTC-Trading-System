package com.tradehistoryaccess.Bean;

public class TestDao {
    private String orderid;
    private int quantity;

    public TestDao(String orderid, int quantity) {
        this.orderid = orderid;
        this.quantity = quantity;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
