/***********************************************************************
 * Module:  Orderbook.java
 * Author:  gyh
 * Purpose: Defines the Class Orderbook
 ***********************************************************************/

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.Or;

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

    public void run(){

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

    public Boolean addSellLimit() {
        while (true) {
            try {
                Order temp = waitingQueue.getSellLimit();
                sellOrders.addOrder(temp);
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
                } else {
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
                Order canceledOrder = null;
                if (temp.getSellOrBuy().equals("buy")) {
                    canceledOrder = buyOrders.cancelOrder(temp);
                } else {
                    canceledOrder = sellOrders.cancelOrder(temp);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void deal() {
        while (true) {
            Order limitBuy;
            limitBuy= buyOrders.candidateOrder();
            Order limitSell;
            limitSell= sellOrders.candidateOrder();

            if(limitBuy!=null && limitSell!=null) {
                System.out.println("candidate limit: " + limitBuy.getOrderId() + " and " + limitSell.getOrderId());
                System.out.flush();
            }
            Order market = waitingQueue.peekMarket();

            int market_flag = -1;

            Order candidateBuy = null;
            Order candidateSell = null;


            int dealPrice = 0;

            if (market != null) {
                market.lock();
                market_flag = (market.getSellOrBuy().equals("buy")) ? 1 : 2;
            } else {
                market_flag = 0;
            }

            if (limitBuy != null && limitSell != null) {

                if (limitBuy.getPrice() >= limitSell.getPrice()) {

                    candidateBuy = limitBuy;
                    candidateSell = limitSell;
                    switch (market_flag) {
                        case 1:
                            if (limitBuy.getTime() >= market.getTime()) {

                                System.out.println("market buy + limit sell");
                                System.out.flush();
                                candidateBuy = market;
                                limitBuy.unlock();
                                dealPrice = candidateSell.getPrice();
                            } else {
                                System.out.println("limit buy + limit sell");
                                System.out.flush();
                                market.unlock();
                                dealPrice = (candidateBuy.getTime() < candidateSell.getTime()) ? candidateBuy.getPrice() : candidateSell.getPrice();
                            }
                            break;
                        case 2:
                            if (limitSell.getTime() >= market.getTime()) {
                                System.out.println("market sell + limit buy");
                                System.out.flush();
                                candidateSell = market;
                                limitSell.unlock();
                                dealPrice = candidateBuy.getPrice();
                            } else {
                                System.out.println("limit buy + limit sell");
                                System.out.flush();
                                market.unlock();
                                dealPrice = (candidateBuy.getTime() < candidateSell.getTime()) ? candidateBuy.getPrice() : candidateSell.getPrice();

                            }
                            break;
                        case 0:
                            dealPrice = (candidateBuy.getTime() < candidateSell.getTime()) ? candidateBuy.getPrice() : candidateSell.getPrice();
                            break;
                    }
                }
                else {
                    switch (market_flag) {
                        case 1:
                            candidateBuy = market;
                            candidateSell = limitSell;
                            limitBuy.unlock();
                            dealPrice = candidateSell.getPrice();
                            break;
                        case 2:
                            candidateBuy = limitBuy;
                            candidateSell = market;
                            limitSell.unlock();
                            dealPrice = candidateBuy.getPrice();
                            break;
                        case 0:
                            limitBuy.unlock();
                            limitSell.unlock();
                            break;
                    }
                }
            }
            if (limitBuy == null && limitSell != null) {
                if (market_flag == 1) {
                    candidateBuy = market;
                    candidateSell = limitSell;
                    dealPrice=candidateSell.getPrice();
                } else {
                    limitSell.unlock();
                }
            }
            if (limitBuy != null && limitSell == null) {
                if (market_flag == 2) {
                    candidateBuy = limitBuy;
                    candidateSell = market;
                    dealPrice=candidateBuy.getPrice();
                } else {
                    limitBuy.unlock();
                }
            }


            if (candidateSell == null || candidateBuy == null) {


                      continue;      // no deal
            }

            //start deal



            int quantity = Math.min(candidateBuy.getRemainingQuantity(), candidateSell.getRemainingQuantity());
            System.out.println("deal: " + candidateBuy.getOrderId() + " and " + candidateSell.getOrderId() + "  at price: " + dealPrice+ " at quantity: "+quantity);
            System.out.flush();
            int tempBuyQuantity = candidateBuy.getRemainingQuantity() - quantity;
            int tempSellQuantity = candidateSell.getRemainingQuantity() - quantity;
            if (tempBuyQuantity == 0) {
                if (candidateBuy.getOrderType().equals("limit")) {
                    System.out.println("limit Buy remove");
                    System.out.flush();
                    buyOrders.removeOrder(candidateBuy);
                } else {
                    System.out.println("remove market buy");
                    System.out.flush();
                    waitingQueue.getMarket();
                    //交易完成，移除现有market
                }
            } else {
                System.out.println("buy reset quantity");
                System.out.flush();
                candidateBuy.setRemainingQuantity(tempBuyQuantity);
            }

            if (tempSellQuantity == 0) {
                if (candidateSell.getOrderType().equals("market")) {
                    System.out.println("remove market sell");
                    System.out.flush();
                    waitingQueue.getMarket();
                } else {
                    System.out.println("remove limit sell");
                    System.out.flush();
                    sellOrders.removeOrder(candidateSell);
                }
            } else {
                System.out.println("sell reset quantity");
                System.out.flush();
                candidateSell.setRemainingQuantity(tempSellQuantity);
            }
            System.out.println("remove complete");
            System.out.flush();

            candidateBuy.unlock();
            candidateSell.unlock();
            //交易结束


        }
    }


    private static Orderbook orderbook;
    private static WaitingOrders waitingOrders;
    public static void main(String[] args) {
        Product product = new Product("1", "testProduct", "j1");
        PriceNodeList buyList = new PriceNodeList();
        PriceNodeList sellList = new PriceNodeList();
        Order order1 = new Order("test1", "limit", 1000, "sell");
        order1.setRemainingQuantity(10);
        order1.setTime(10L);
        Order order2 = new Order("test2", "limit", 1000, "sell");
        order2.setRemainingQuantity(10);
        order2.setTime(11L);
        Order order3 = new Order("test3", "limit", 1061, "sell");
        order3.setRemainingQuantity(10);
        order3.setTime(12L);
        Order order4 = new Order("test4", "limit", 1030, "sell");
        order4.setRemainingQuantity(10);
        order4.setTime(13L);
        Order order5 = new Order("test5", "limit", 1050, "sell");
        order5.setRemainingQuantity(10);
        order5.setTime(14L);
        sellList.addOrder(order1);
        sellList.addOrder(order2);
        sellList.addOrder(order3);
        sellList.addOrder(order4);
        sellList.addOrder(order5);




        Order order6 = new Order("test6", "limit", 1050, "buy");
        order6.setRemainingQuantity(20);
        order6.setTime(15L);
        Order order7 = new Order("test7", "limit", 1000, "buy");
        order7.setRemainingQuantity(10);
        order7.setTime(16L);
        Order order8 = new Order("test8", "limit", 1061, "buy");
        order8.setRemainingQuantity(10);
        order8.setTime(17L);
        Order order9 = new Order("test9", "limit", 1030, "buy");
        order9.setRemainingQuantity(10);
        order9.setTime(18L);
        Order order10 = new Order("test10", "limit", 1050, "buy");
        order10.setRemainingQuantity(10);
        order10.setTime(19L);
        buyList.addOrder(order6);
        buyList.addOrder(order7);
        buyList.addOrder(order8);
        buyList.addOrder(order9);
        buyList.addOrder(order10);

        waitingOrders = new WaitingOrders(product);
        Order mark1 = new Order("mk1", "market", 0, "buy");
        mark1.setRemainingQuantity(15);
        mark1.setTime(5L);

        Order mark2 = new Order("mk2", "market", 0, "buy");
        mark2.setRemainingQuantity(15);
        mark2.setTime(20L);

        waitingOrders.addMarket(mark1);
        waitingOrders.addMarket(mark2);
        orderbook = new Orderbook(product, buyList, sellList, waitingOrders);




        class th_create_market implements Runnable {
            public void run() {
                int id = 0;
                while (true) {
                    id++;
//                    Product product = new Product("1", "testProduct", "j1");
                    Random random = new Random();
                    int flag = random.nextInt(2);
                    String sellorbuy = (flag == 0) ? "buy" : "sell";
                    Order newOrder = new Order("market" + id, "market", 0, sellorbuy);
                    newOrder.setRemainingQuantity(10 + random.nextInt(10));
                    newOrder.setTime(System.currentTimeMillis());

                    waitingOrders.addMarket(newOrder);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class th_create_limit implements Runnable {
            public void run() {
                int id = 0;
                while (true) {
                    id++;
//                    Product product = new Product("1", "testProduct", "j1");
                    Random random = new Random();
                    int flag = random.nextInt(2);
                    int price = 1000 + random.nextInt(1000);
                    String sellorbuy = (flag == 0) ? "buy" : "sell";
                    Order newOrder = new Order("limit" + id, "limit", price, sellorbuy);
                    newOrder.setRemainingQuantity(10 + random.nextInt(10));
                    newOrder.setTime(System.currentTimeMillis());
                    if (sellorbuy == "buy") {
                        waitingOrders.addBuyLimit(newOrder);
                    } else {
                        waitingOrders.addSellLimit(newOrder);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        class th_create_stop implements Runnable {
            public void run() {
                int id = 0;
                while (true) {
                    id++;
//                    Product product = new Product("1", "testProduct", "j1");
                    Random random = new Random();
                    int flag = random.nextInt(2);
                    String sellorbuy = (flag == 0) ? "buy" : "sell";
                    int price = 1000 + random.nextInt(1000);
                    Order newOrder = new Order(id + "", "stop", price, sellorbuy);
                    newOrder.setRemainingQuantity(10 + random.nextInt(10));
                    newOrder.setTime(System.currentTimeMillis());

                    waitingOrders.addStop(newOrder);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        class th_add_buylimit implements Runnable {
            public void run() {
                orderbook.addBuyLimit();
            }
        }
        class th_add_selllimit implements Runnable {
            public void run() {
                orderbook.addSellLimit();
            }
        }
        class th_add_stop implements Runnable {

            public void run() {
                orderbook.addStop();
            }
        }
        class th_deal implements Runnable{

            public void run() {
                orderbook.deal();
            }
        }

        Thread thread_create_market=new Thread(new th_create_market());
        Thread thread_create_limit=new Thread(new th_create_limit());
        Thread thread_add_buy=new Thread(new th_add_buylimit());
        Thread thread_add_sell=new Thread(new th_add_selllimit());
        Thread thread_deal=new Thread(new th_deal());

        thread_create_market.start();
        thread_create_limit.start();
        thread_add_buy.start();
        thread_add_sell.start();


        thread_deal.start();
        try {
            thread_deal.join();
            thread_create_market.join();
            thread_create_limit.join();
            thread_add_buy.join();
            thread_add_sell.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}