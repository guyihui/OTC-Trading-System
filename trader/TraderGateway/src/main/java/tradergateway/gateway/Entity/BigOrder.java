package tradergateway.gateway.Entity;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Set;

public class BigOrder {
    // TODO: 自动生成id
    private static int counts=0;

    synchronized private  static void add(){
        counts++;
    }
    private String id;
    private Product product;
    private String sellOrBuy;
    private String strategy;
    private String broker;
    private String traderName;
    private Integer totalQuantity;
    private Integer unsentQuantity;
    private Integer waitingQuantity;
    private Boolean cancelFlag;
    private Set<Order> splitOrders = new CopyOnWriteArraySet<>();


    public BigOrder() {
        add();
        id = "" + counts;
        cancelFlag = false;
        waitingQuantity = 0;
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

    public Boolean getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(Boolean cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Set<Order> getSplitOrders() {
        return splitOrders;
    }

    public void addSplitOrder(Order order) {
        order.setBigOrderId(id);
        splitOrders.add(order);
    }

    public void removeSplitOrder(Order order) {
        splitOrders.remove(order);
    }

    public void clearFinishedSplitOrders() {
        for (Order order : splitOrders) {
            if (order.getDisplayFlag() > 0) {
                splitOrders.remove(order);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BigOrder && ((BigOrder) obj).getId().equals(id);
    }

    @Override
    public int hashCode() {
        return "BigOrder".hashCode() + id.hashCode();
    }
}
