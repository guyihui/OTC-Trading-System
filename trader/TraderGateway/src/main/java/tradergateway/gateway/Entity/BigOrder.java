package tradergateway.gateway.Entity;

public class BigOrder {
    private String id;
    private Product product;
    private String sellorbuy;
    public String strategy;
    private String broker;
    private String traderName;
    private Integer totalQuantity;
    private Integer unsentQuantity;
    private Integer waitingQuantity;
    private Boolean cancelflag;

    public BigOrder(){
        cancelflag=false;
        waitingQuantity=0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSellorbuy() {
        return sellorbuy;
    }

    public void setSellorbuy(String sellorbuy) {
        this.sellorbuy = sellorbuy;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getUnsentQuantity() {
        return unsentQuantity;
    }

    public void setUnsentQuantity(Integer unsentQuantity) {
        this.unsentQuantity = unsentQuantity;
    }

    public Integer getWaitingQuantity() {
        return waitingQuantity;
    }

    public void setWaitingQuantity(Integer waitingQuantity) {
        this.waitingQuantity = waitingQuantity;
    }

    public Boolean getCancelflag() {
        return cancelflag;
    }

    public void setCancelflag(Boolean cancelflag) {
        this.cancelflag = cancelflag;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}
