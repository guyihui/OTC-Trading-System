/***********************************************************************
 * Module:  Orderbook.java
 * Author:  gyh
 * Purpose: Defines the Class Orderbook
 ***********************************************************************/

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;


public class Orderbook {

    private Product product;

    private PriceNodeList buyOrders;

    private PriceNodeList sellOrders;

    private WaitingOrders waitingQueue;


    public Orderbook(Product product, PriceNodeList buyOrders, PriceNodeList sellOrders, WaitingOrders waitingQueue) {
        this.product = product;
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
        this.waitingQueue = waitingQueue;

    }

    public Boolean addBuyLimit() {
        while (true) {
            try {
                Order temp = waitingQueue.getBuyLimit();
                buyOrders.addOrder(temp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addStop() {
        while (true) {
            try {
                Order temp = waitingQueue.getStop();
                if (temp.getSellOrBuy().equals("buy")) {
                    buyOrders.addOrder(temp);
                }
                else {
                    sellOrders.addOrder(temp);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public Order cancelOrder() {
        while (true) {
            try {
                Order temp = waitingQueue.getCancel();
                if (temp.getSellOrBuy().equals("buy")) {
                    buyOrders.cancelOrder(temp);
                }
                else {
                    sellOrders.cancelOrder(temp);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public Boolean deal() {

        // TODO: implement
        return null;
    }

}