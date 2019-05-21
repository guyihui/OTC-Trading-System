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


    public Boolean deal() {
        while (true) {
            Order limitBuy = buyOrders.candidateOrder();
            Order limitSell = sellOrders.candidateOrder();
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
                continue;      // no deal
            }

            //start deal

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

            System.out.println("deal: "+candidateBuy.getOrderId()+" and "+candidateSell.getOrderId());
            System.out.flush();
            candidateBuy.unlock();
            candidateSell.unlock();
            //交易结束



        }
    }

    public static void main(String []args){

        Product product=new Product("1","testProduct","j1");
        
      //  Orderbook orderbook=new Orderbook();
        class dealThread implements Runnable{

            public void run() {

            }
        }
    }


}