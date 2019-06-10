package com.tradehistoryaccess.Entity;

public class DoneOrderRaw {

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


    public DoneOrderRaw(String broker, String productid, String period, int price, int quantity, String initTrader, String initCompany, String initSide, String compTrader, String compCompany, String compSide, String time) {
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
    public Tradehistory createHistory(String id){
        Tradehistory tradehistory= new Tradehistory();
        tradehistory.setTradeid(id);
        tradehistory.setBroker(broker);
        tradehistory.setProductid(productid);
        tradehistory.setPeriod(period);
        tradehistory.setPrice(price);
        tradehistory.setQuantity(quantity);
        tradehistory.setInitTrader(initTrader);
        tradehistory.setInitCompany(initCompany);
        tradehistory.setInitSide(initSide);
        tradehistory.setCompTrader(compTrader);
        tradehistory.setCompCompany(compCompany);
        tradehistory.setCompSide(compSide);
        tradehistory.setTimestamp(time);
        return tradehistory;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
