package Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

        if (tradeid != null ? !tradeid.equals(that.tradeid) : that.tradeid != null) return false;
        if (broker != null ? !broker.equals(that.broker) : that.broker != null) return false;
        if (productid != null ? !productid.equals(that.productid) : that.productid != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (initTrader != null ? !initTrader.equals(that.initTrader) : that.initTrader != null) return false;
        if (initCompany != null ? !initCompany.equals(that.initCompany) : that.initCompany != null) return false;
        if (initSide != null ? !initSide.equals(that.initSide) : that.initSide != null) return false;
        if (compTrader != null ? !compTrader.equals(that.compTrader) : that.compTrader != null) return false;
        if (compCompany != null ? !compCompany.equals(that.compCompany) : that.compCompany != null) return false;
        if (compSide != null ? !compSide.equals(that.compSide) : that.compSide != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tradeid != null ? tradeid.hashCode() : 0;
        result = 31 * result + (broker != null ? broker.hashCode() : 0);
        result = 31 * result + (productid != null ? productid.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (initTrader != null ? initTrader.hashCode() : 0);
        result = 31 * result + (initCompany != null ? initCompany.hashCode() : 0);
        result = 31 * result + (initSide != null ? initSide.hashCode() : 0);
        result = 31 * result + (compTrader != null ? compTrader.hashCode() : 0);
        result = 31 * result + (compCompany != null ? compCompany.hashCode() : 0);
        result = 31 * result + (compSide != null ? compSide.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
