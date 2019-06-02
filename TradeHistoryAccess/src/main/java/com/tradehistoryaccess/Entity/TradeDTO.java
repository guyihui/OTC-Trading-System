package com.tradehistoryaccess.Entity;

public class TradeDTO {
    private String id;
    private String broker;
    private String productid;
    private String period;
    private int price;
    private int quantity;
    private String initTrader;
    private String initCompany;
    private String initSide;
    private String compTrader;
    private String compCompany;
    private String compSide;
    private String time;


    public TradeDTO(String id,String broker, String productid, String period, int price, int quantity, String initTrader, String initCompany, String initSide, String compTrader, String compCompany, String compSide, String time) {
       this.id=id;
        this.broker = broker;
        this.productid = productid;
        this.period = period;
        this.price = price;
        this.quantity = quantity;
        this.initTrader = initTrader;
        this.initCompany = initCompany;
        this.initSide = initSide;
        this.compTrader = compTrader;
        this.compCompany = compCompany;
        this.compSide = compSide;
        this.time = time;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInitTrader() {
        return initTrader;
    }

    public void setInitTrader(String initTrader) {
        this.initTrader = initTrader;
    }

    public String getInitCompany() {
        return initCompany;
    }

    public void setInitCompany(String initCompany) {
        this.initCompany = initCompany;
    }

    public String getInitSide() {
        return initSide;
    }

    public void setInitSide(String initSide) {
        this.initSide = initSide;
    }

    public String getCompTrader() {
        return compTrader;
    }

    public void setCompTrader(String compTrader) {
        this.compTrader = compTrader;
    }

    public String getCompCompany() {
        return compCompany;
    }

    public void setCompCompany(String compCompany) {
        this.compCompany = compCompany;
    }

    public String getCompSide() {
        return compSide;
    }

    public void setCompSide(String compSide) {
        this.compSide = compSide;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
