package com.tradehistoryaccess.Service.BrokerService.OrderBook;

import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


public class WaitingOrders {

    private Product product;
    public static Comparator<Order> buyComparator = new Comparator<Order>() {
        //最小堆，
        public int compare(Order o1, Order o2) {

            if (o1.getPrice() > o2.getPrice()) {
                return -1;
            } else {
                if (o1.getPrice().equals(o2.getPrice())) {
                    if (o1.getTime().equals(o2.getTime())) {
                        return 0;
                    }
                    return o1.getTime() > o2.getTime() ? 1 : -1;
                } else {
                    return 1;
                }
            }
        }
    };
    public static Comparator<Order> sellComparator = new Comparator<Order>() {
        public int compare(Order o1, Order o2) {
            if (o1.getPrice() > o2.getPrice()) {
                return 1;
            } else {
                if (o1.getPrice().equals(o2.getPrice())) {
                    if (o1.getTime().equals(o2.getTime())) {
                        return 0;
                    }
                    return o1.getTime() > o2.getTime() ? -1 : 1;
                } else {
                    return -1;
                }
            }
        }
    };

    private PriorityBlockingQueue<Order> buyLimit;
    private PriorityBlockingQueue<Order> sellLimit;

    private LinkedBlockingQueue<Order> stop;
    private LinkedBlockingQueue<Order> cancel;

    private ConcurrentLinkedQueue<Order> market;


    public WaitingOrders(Product product) {
        this.product = product;
        this.buyLimit = new PriorityBlockingQueue<Order>(10, buyComparator);
        this.sellLimit = new PriorityBlockingQueue<Order>(10, sellComparator);
        this.cancel = new LinkedBlockingQueue<Order>();
        this.stop = new LinkedBlockingQueue<Order>();
        this.market = new ConcurrentLinkedQueue<Order>();
    }

    public boolean addBuyLimit(Order order) {
        return this.buyLimit.offer(order);
    }

    public boolean addSellLimit(Order order) {
        return this.sellLimit.offer(order);
    }

    public boolean addStop(Order order) {
        return this.stop.offer(order);
    }

    public boolean addCancel(Order order) {
        return this.cancel.offer(order);
    }

    public boolean addMarket(Order order) {
        return this.market.offer(order);
    }


    public Order getStop() throws InterruptedException {
        return this.stop.take();
    }

    public Order getCancel() throws InterruptedException {
        return this.cancel.take();
    }

    public Order getBuyLimit() throws InterruptedException {
        return this.buyLimit.take();
    }

    public Order getSellLimit() throws InterruptedException {
        return this.sellLimit.take();
    }

    public Order peekMarket() {
        return this.market.peek();
    }


    public Order getMarket() {
        return this.market.poll();
    }





}