package com.tradehistoryaccess.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Tradehistory {
    private String tradeid;
    private String broker;
    private String productid;
    private String period;
    private Integer price;
    private Integer quantity;
    private String initTrader;
    private String initCompany;
    private String initSide;
    private String compTrader;
    private String compCompany;
    private String compSide;
    private String timestamp;

    @Id
    @Column(name = "tradeid", nullable = false, length = 30)
    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    @Basic
    @Column(name = "broker", nullable = true, length = 20)
    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    @Basic
    @Column(name = "productid", nullable = true, length = 20)
    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    @Basic
    @Column(name = "period", nullable = true, length = 20)
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Basic
    @Column(name = "price", nullable = true)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "quantity", nullable = true)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "initTrader", nullable = true, length = 20)
    public String getInitTrader() {
        return initTrader;
    }

    public void setInitTrader(String initTrader) {
        this.initTrader = initTrader;
    }

    @Basic
    @Column(name = "initCompany", nullable = true, length = 20)
    public String getInitCompany() {
        return initCompany;
    }

    public void setInitCompany(String initCompany) {
        this.initCompany = initCompany;
    }

    @Basic
    @Column(name = "initSide", nullable = true, length = 20)
    public String getInitSide() {
        return initSide;
    }

    public void setInitSide(String initSide) {
        this.initSide = initSide;
    }

    @Basic
    @Column(name = "compTrader", nullable = true, length = 20)
    public String getCompTrader() {
        return compTrader;
    }

    public void setCompTrader(String compTrader) {
        this.compTrader = compTrader;
    }

    @Basic
    @Column(name = "compCompany", nullable = true, length = 20)
    public String getCompCompany() {
        return compCompany;
    }

    public void setCompCompany(String compCompany) {
        this.compCompany = compCompany;
    }

    @Basic
    @Column(name = "compSide", nullable = true, length = 20)
    public String getCompSide() {
        return compSide;
    }

    public void setCompSide(String compSide) {
        this.compSide = compSide;
    }

    @Basic
    @Column(name = "Timestamp", nullable = true, length = 20)
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tradehistory that = (Tradehistory) o;
        return Objects.equals(tradeid, that.tradeid) &&
                Objects.equals(broker, that.broker) &&
                Objects.equals(productid, that.productid) &&
                Objects.equals(period, that.period) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(initTrader, that.initTrader) &&
                Objects.equals(initCompany, that.initCompany) &&
                Objects.equals(initSide, that.initSide) &&
                Objects.equals(compTrader, that.compTrader) &&
                Objects.equals(compCompany, that.compCompany) &&
                Objects.equals(compSide, that.compSide) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeid, broker, productid, period, price, quantity, initTrader, initCompany, initSide, compTrader, compCompany, compSide, timestamp);
    }
}
