package tradergateway.gateway.Entity;


public class Order {

    private String orderId;
    private String orderType;
    private Product product;
    private String sellOrBuy;
    private String broker;
    private String traderName;
    private Integer totalQuantity;
    private Integer remainingQuantity;
    private Integer price;
    private String cancelId;
    private Long time;
    private String state;
    private Integer flag;

    public Order() {
        flag = 0;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSellOrBuy() {
        return sellOrBuy;
    }

    public void setSellOrBuy(String sellOrBuy) {
        this.sellOrBuy = sellOrBuy;
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

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCancelId() {
        return cancelId;
    }

    public void setCancelId(String cancelId) {
        this.cancelId = cancelId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return "{" + this.orderId + "\t," + this.orderType + "}";
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public void incrementFlag() {
        this.flag += 1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Order && ((Order) obj).getOrderId().equals(orderId);
    }

    @Override
    public int hashCode() {
        return "order".hashCode() + orderId.hashCode();
    }
}