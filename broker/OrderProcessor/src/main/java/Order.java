import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Order {

    private String orderId;
    private String orderType;
    private Product product;
    private String sellOrBuy;
    private String broker;
    private Trader trader;
    private Integer totalQuantity;
    private Integer remainingQuantity;
    private Integer price;
    private String cancelId;
    private Long time;
    private Lock lock = new ReentrantLock();

    public Order(String id, String type) {
        this.orderId = id;
        this.orderType = type;
    }

   public Order(String id, String type, Integer price,String sellOrBuy) {
      this.orderId = id;
      this.orderType = type;
      this.price = price;
      this.sellOrBuy = sellOrBuy;
   }

    public Order(String id, String type, Product product, String sellOrBuy, Trader trader,
                 Integer total, Integer price, String cancelId) {
        this.orderId = id;
        this.orderType = type;
        this.product = product;
        this.sellOrBuy = sellOrBuy;
        this.broker = null;
        this.trader = trader;
        this.totalQuantity = total;
        this.remainingQuantity = total;
        this.price = price;
        this.cancelId = cancelId;
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public String toString() {
        return "{" + this.orderId + "\t," + this.orderType + "}";
    }

    public void stopToLimit() {
        this.orderType = "stop->limit";
    }

    public Integer fill() {
        // TODO: implement
        return null;
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

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
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
}