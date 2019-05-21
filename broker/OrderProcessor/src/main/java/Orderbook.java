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
            Order limitBuy = buyOrders.candidateOrder();
            Order limitSell = sellOrders.candidateOrder();

            System.out.println("candidate limit: "+limitBuy.getOrderId()+" and "+limitSell.getOrderId());
            System.out.flush();

            Order market = waitingQueue.peekMarket();

            int market_flag = -1;

            Order candidateBuy = null;
            Order candidateSell = null;


            int dealPrice=0;

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
                                candidateBuy = market;
                                limitBuy.unlock();
                                dealPrice=candidateSell.getPrice();
                            }
                            else {
                                market.unlock();
                                dealPrice=(candidateBuy.getTime()<candidateSell.getTime())?candidateBuy.getPrice():candidateSell.getPrice();
                            }
                            break;
                        case 2:
                            if (limitSell.getTime() >= market.getTime()) {
                                candidateSell = market;
                                limitSell.unlock();
                                dealPrice=candidateBuy.getPrice();
                            }
                            else {
                                dealPrice=(candidateBuy.getTime()<candidateSell.getTime())?candidateBuy.getPrice():candidateSell.getPrice();
                                market.unlock();
                            }
                            break;
                        case 0:
                            break;
                    }
                } else {
                    switch (market_flag) {
                        case 1:
                            candidateBuy = market;
                            candidateSell = limitSell;
                            limitBuy.unlock();
                            dealPrice=candidateSell.getPrice();
                            break;
                        case 2:
                            candidateBuy = limitBuy;
                            candidateSell = market;
                            limitSell.unlock();
                            dealPrice=candidateBuy.getPrice();
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
                }
                else {
                    limitSell.unlock();
                }
            }
            if (limitBuy != null && limitSell == null) {
                if (market_flag == 2) {
                    candidateBuy = limitBuy;
                    candidateSell = market;
                }
                else {
                    limitBuy.unlock();
                }
            }
            if ( candidateSell == null || candidateBuy==null) {

                break;  //test
          //      continue;      // no deal
            }

            //start deal

            System.out.println("deal: "+candidateBuy.getOrderId()+" and "+candidateSell.getOrderId());
            System.out.flush();

            int quantity = Math.min(candidateBuy.getRemainingQuantity(), candidateSell.getRemainingQuantity());
            int tempBuyQuantity = candidateBuy.getRemainingQuantity() - quantity;
            int tempSellQuantity = candidateSell.getRemainingQuantity() - quantity;
            if (tempBuyQuantity == 0) {
                if (candidateBuy.getOrderType().equals("limit")) {
                    buyOrders.removeOrder(candidateBuy);
                } else {
                    waitingQueue.getMarket();
                    //交易完成，移除现有market
                }
            } else {
                candidateBuy.setRemainingQuantity(tempBuyQuantity);
            }

            if (tempSellQuantity == 0) {
                if (candidateSell.getOrderType().equals("market")) {
                    waitingQueue.getMarket();
                } else {
                    sellOrders.removeOrder(candidateSell);
                }
            } else {
                candidateSell.setRemainingQuantity(tempSellQuantity);
            }


            candidateBuy.unlock();
            candidateSell.unlock();
            //交易结束



        }
    }

    public static void main(String []args){

        Product product=new Product("1","testProduct","j1");
        PriceNodeList buyList=new PriceNodeList();
        PriceNodeList sellList=new PriceNodeList();
        Order order1 = new Order("test1", "limit",1000,"sell");
        order1.setRemainingQuantity(10);
        order1.setTime(10L);
        Order order2 = new Order("test2", "limit",1000,"sell");
        order2.setRemainingQuantity(10);
        order2.setTime(11L);
        Order order3 = new Order("test3", "limit",1061,"sell");
        order3.setRemainingQuantity(10);
        order3.setTime(12L);
        Order order4 = new Order("test4", "limit",1030,"sell");
        order4.setRemainingQuantity(10);
        order4.setTime(13L);
        Order order5 = new Order("test5", "limit",1050,"sell");
        order5.setRemainingQuantity(10);
        order5.setTime(14L);
        sellList.addOrder(order1);
        sellList.addOrder(order2);
        sellList.addOrder(order3);
        sellList.addOrder(order4);
        sellList.addOrder(order5);




        Order order6 = new Order("test6", "limit",1050,"buy");
        order6.setRemainingQuantity(20);
        order6.setTime(15L);
        Order order7 = new Order("test7", "limit",1000,"buy");
        order7.setRemainingQuantity(10);
        order7.setTime(16L);
        Order order8 = new Order("test8", "limit",1061,"buy");
        order8.setRemainingQuantity(10);
        order8.setTime(17L);
        Order order9 = new Order("test9", "limit",1030,"buy");
        order9.setRemainingQuantity(10);
        order9.setTime(18L);
        Order order10 = new Order("test10", "limit",1050,"buy");
        order10.setRemainingQuantity(10);
        order10.setTime(19L);
        buyList.addOrder(order6);
        buyList.addOrder(order7);
        buyList.addOrder(order8);
        buyList.addOrder(order9);
        buyList.addOrder(order10);
        WaitingOrders waitingOrders=new WaitingOrders(product);

        Order candidateSell=sellList.candidateOrder();
        System.out.println(candidateSell.getOrderId());

    }


}