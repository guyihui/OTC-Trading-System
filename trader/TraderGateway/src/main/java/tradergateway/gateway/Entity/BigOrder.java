package tradergateway.gateway.Entity;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class BigOrder {
    // TODO: 自动生成id
    private static int counts=0;

    synchronized private  static void add(){
        counts++;
    }
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
    private CopyOnWriteArraySet<Order>splitOrders=new CopyOnWriteArraySet<>();


    public BigOrder(){
        add();
        id=""+counts;
        cancelflag=false;
        waitingQuantity=0;
    }
    public void addSplitOrders(Order order){
        splitOrders.add(order);
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

    public CopyOnWriteArraySet<Order> getSplitOrders() {
        return splitOrders;
    }

}
