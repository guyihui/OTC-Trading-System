
package com.tradehistoryaccess.BrokerService.OrderBook;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PriceNodeList {

    private Orderbook orderbook;
    private String sellOrBuy;
    private final PriceNode limitHead = new PriceNode();
    private final PriceNode stopHead = new PriceNode();
    private PriceNodeList other;
    private Lock lock = new ReentrantLock();

    public PriceNodeList(String sellOrBuy, Orderbook orderbook) {
        this.orderbook = orderbook;
        this.sellOrBuy = sellOrBuy;
        if (sellOrBuy.equals("sell")) {
            this.limitHead.setPrice(0);
            this.stopHead.setPrice(0);
        } else {
            this.limitHead.setPrice(Integer.MAX_VALUE);
            this.stopHead.setPrice(Integer.MAX_VALUE);
        }
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public void setOther(PriceNodeList otherList) {
        this.other = otherList;
    }

    public String getSellOrBuy() {
        return sellOrBuy;
    }

    public Integer getDepth() {
        limitHead.lock();
        PriceNode limitNext = limitHead.getNext();
        if (limitNext != null) {
            Integer price = limitNext.getPrice();
            limitHead.unlock();
            return price;
        } else {
            limitHead.unlock();
            return limitHead.getPrice();
        }
    }

    public Boolean addOrder(Order order) {
        Integer price = order.getPrice();
        if (other.getDepth() != null) {
            if (order.getSellOrBuy().equals("sell") && order.getOrderType().equals("stop")) {
                if (order.getPrice() >= other.getDepth()) {
                    order.setOrderType("limit");
                }
            } else if (order.getSellOrBuy().equals("buy") && order.getOrderType().equals("stop")) {
                if (order.getPrice() <= other.getDepth()) {
                    order.setOrderType("limit");
                }
            }
        }
        PriceNode temp;
        if (order.getOrderType().equals("limit")) {
            temp = limitHead;
        } else {
            temp = stopHead;
        }
        temp.lock();
        while (temp.getNext() != null) {
            temp.getNext().lock();
            PriceNode tempNext = temp.getNext();
            if (price == tempNext.getPrice()) { //新的订单的价格在原price node list中存在，则直接在后面加上节点
                Boolean tempAddOrder = tempNext.addOrder(order);
                tempNext.unlock();
                temp.unlock();
                return tempAddOrder;
            } else if (!order.priceBetterThan(temp.getPrice()) && order.priceBetterThan(tempNext.getPrice())) {
                PriceNode newMedium = new PriceNode(price);
                newMedium.setNext(tempNext);
                temp.setNext(newMedium);
                if (temp == limitHead) {
                    //TODO:Depth变化时动作：如 Broadcast 等
                    this.broadcastOnDepthChange(newMedium);
                }
                Boolean tempAddOrder = newMedium.addOrder(order);
                tempNext.unlock();
                temp.unlock();
                return tempAddOrder;
            }
            PriceNode tempPrev = temp;
            temp = tempNext;
            tempPrev.unlock();
        }
        PriceNode newTail = new PriceNode(price);
        Boolean tempAddOrder = newTail.addOrder(order);
        temp.setNext(newTail);
        temp.unlock();
        return tempAddOrder;

    }

    public Order cancelOrder(Order order) {
        Integer price = order.getPrice();
        PriceNode temp;
        temp = stopHead;
        temp.lock();
        while (temp.getNext() != null) {
            temp.getNext().lock();
            PriceNode tempNext = temp.getNext();
            if (price == tempNext.getPrice()) { //新的订单的价格在原price node list中存在，则cancel
                Order tempCanceledOrder = tempNext.cancelOrder(order);
                if (tempNext.isEmpty() == 1) {
                    temp.setNext(tempNext.getNext());
                }
                tempNext.unlock();
                temp.unlock();
                return tempCanceledOrder;
            }
            PriceNode tempPrev = temp;
            temp = tempNext;
            tempPrev.unlock();
        }

        temp.unlock();

        temp = limitHead;
        temp.lock();
        while (temp.getNext() != null) {
            temp.getNext().lock();
            PriceNode tempNext = temp.getNext();
            if (price == tempNext.getPrice()) { //新的订单的价格在原price node list中存在，则直接在后面加上节点
                Order tempCanceledOrder = tempNext.cancelOrder(order);
                if (tempNext.isEmpty() == 1) {
                    if (temp == limitHead) {
                        //TODO:Depth变化时动作：如 Broadcast 等
                        this.broadcastOnDepthChange(tempNext.getNext());
                        other.checkStop(
                                tempNext.getPrice(),
                                tempNext.getNext() == null ?
                                        Integer.MAX_VALUE - limitHead.getPrice()
                                        :
                                        tempNext.getNext().getPrice()
                        );
                    }
                    temp.setNext(tempNext.getNext());
                }
                tempNext.unlock();
                temp.unlock();
                return tempCanceledOrder;
            }
            PriceNode tempPrev = temp;
            temp = tempNext;
            tempPrev.unlock();
        }
        temp.unlock();
        return null;
    }

    public Order candidateOrder() {
        limitHead.lock();
        PriceNode limitNext = limitHead.getNext();
        if (limitNext == null) {
            limitHead.unlock();
            return null;
        }
        limitNext.lock();
        Order ord = limitNext.candidateOrder();
        limitNext.unlock();
        limitHead.unlock();
        return ord;
    }

    private boolean between(PriceNode node, int oldStopPrice, int newStopPrice) {
        return (node.getPrice() - oldStopPrice) * (node.getPrice() - newStopPrice) <= 0;
    }

    public Boolean checkStop(int oldStopPrice, int newStopPrice) {

        int flag = 0;

        PriceNode limit = limitHead;
        PriceNode stop = stopHead;
        stop.lock();
        while (stop.getNext() != null) {
            stop.getNext().lock();
            PriceNode stopNext = stop.getNext();
            if (between(stopNext, oldStopPrice, newStopPrice)) {
                if (flag == 0) {
                    flag = 1;
                    limit.lock();
                }
                while (limit.getNext() != null) {
                    limit.getNext().lock();
                    PriceNode limitNext = limit.getNext();

                    if (stopNext.getPrice() == limitNext.getPrice()) { //stop价格在limit中存在，合并order list
                        limitNext.getOrders().concat(stopNext.getOrders().activateStop());
                        limit.unlock();
                        limit = limitNext;
                        stopNext.unlock();
                        break;
                    } else if (between(stopNext, limit.getPrice(), limitNext.getPrice())) {
                        limit.setNext(stopNext);
                        stopNext.setNext(limitNext);
                        stopNext.activateStop();
                        if (limit == limitHead) {
                            //TODO:Depth变化时动作：如 Broadcast 等
                            this.broadcastOnDepthChange(stopNext);
                        }
                        limit.unlock();
                        limit = stopNext;
                        break;
                    } else {
                        limit.unlock();
                        limit = limitNext;
                    }
                }
                if (limit.getNext() == null) {
                    limit.setNext(stopNext);
                    stopNext.activateStop();
                    limit.unlock();
                    limit = stopNext;
                }
                stop.setNext(stopNext.getNext());
            } else {
                if (flag == 1) {
                    System.err.println("check stop error");
                }
                stop.unlock();
                stop = stopNext;
            }
        }
        stop.unlock();
        if (flag == 1) {
            limit.unlock();
        }
        return flag == 1;
    }

    public Boolean removeOrder(Order order) {
        Integer price = order.getPrice();
        PriceNode temp;
        temp = limitHead;
        temp.lock();
        while (temp.getNext() != null) {
            temp.getNext().lock();
            PriceNode tempNext = temp.getNext();
            if (price == tempNext.getPrice()) { //新的订单的价格在原price node list中存在，则直接在后面加上节点
                Boolean tempRemovedOrder = tempNext.removeOrder(order);
                if (tempNext.isEmpty() == 1) {
                    if (temp == limitHead) {
                        //TODO:Depth变化时动作：如 Broadcast 等
                        this.broadcastOnDepthChange(tempNext.getNext());
                        other.checkStop(
                                tempNext.getPrice(),
                                tempNext.getNext() == null ?
                                        Integer.MAX_VALUE - limitHead.getPrice()
                                        :
                                        tempNext.getNext().getPrice()
                        );
                    }
                    temp.setNext(tempNext.getNext());
                }
                tempNext.unlock();
                temp.unlock();
                return tempRemovedOrder;
            }
            PriceNode tempPrev = temp;
            temp = tempNext;
            tempPrev.unlock();
        }

        temp.unlock();
        return false;

    }

    private void broadcastOnDepthChange(PriceNode newDepth) {
        this.orderbook.broadcast(
                String.format(
                        "depth:%s:%s:%d",
                        this.orderbook.getProduct().getProductId(),
                        this.getSellOrBuy(),
                        newDepth.getPrice()
                )
        );
    }

   public String toString(){
      PriceNode node = this.limitHead;
      int count = 0;
      StringBuilder str = new StringBuilder();
      str.append("Test:"+Thread.currentThread().toString()+"\n");
      while (node.getNext() != null) {
         if(node!=limitHead) {
            str.append("PriceNode_");
         }
         else{
            str.append("DepthNode_");
         }
         str.append(++count);
         str.append(": ");
         str.append(node.getPrice()+"\n");
         str.append("Limit:\n");
         str.append(node.getOrders().toString());
         str.append("------------------------------\n");
         str.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
         node = node.getNext();
      }
      return str.toString();

   }

   public JsonArray orderBooktoString(){
       PriceNode node = this.limitHead;
       JsonArray nodeList = new JsonArray();
       while (node.getNext() != null) {
           JsonObject priceNode = new JsonObject();
           JsonArray temp = node.getNext().getOrders().orderListToJsonArray();
           if(temp != null){
               priceNode.addProperty("price", node.getNext().getPrice());
               priceNode.add("orderList",temp);
               nodeList.add(priceNode);
           }
           node = node.getNext();
       }
       return nodeList;
    }
}