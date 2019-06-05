package com.tradehistoryaccess.BrokerService.OrderBook;

import com.tradehistoryaccess.Entity.DoneOrderRaw;
import com.tradehistoryaccess.BrokerService.Backend2UiSocket.WebSocketTest;
import com.tradehistoryaccess.BrokerService.History.AddHistory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CopyOnWriteArraySet;


public class Orderbook {
    @Autowired
    private static WebSocketTest websocketTest;

    private String brokerName = "";

    private Product product;

    public PriceNodeList getBuyOrders() {
        return buyOrders;
    }

    public PriceNodeList getSellOrders() {
        return sellOrders;
    }

    private PriceNodeList buyOrders;
    private PriceNodeList sellOrders;
    private WaitingOrders waitingQueue;
    private CopyOnWriteArraySet<Trader> connectedTraders = new CopyOnWriteArraySet<>();
    private AddHistory addHistory;

    public Orderbook(Product product) {
        this.product = product;
        this.buyOrders = new PriceNodeList("buy");
        this.sellOrders = new PriceNodeList("sell");
        buyOrders.setOther(sellOrders);
        sellOrders.setOther(buyOrders);
        this.waitingQueue = new WaitingOrders(product);
        this.addHistory = new AddHistory();
        setUpOrderBook();
    }

    public Orderbook(Product product, String brokerName) {
        this.brokerName = brokerName;
        this.product = product;
        this.buyOrders = new PriceNodeList("buy");
        this.sellOrders = new PriceNodeList("sell");
        buyOrders.setOther(sellOrders);
        sellOrders.setOther(buyOrders);
        this.waitingQueue = new WaitingOrders(product);
        this.addHistory = new AddHistory();
        setUpOrderBook();
    }

    public Orderbook(Product product, PriceNodeList buyOrders, PriceNodeList sellOrders, WaitingOrders waitingQueue) {
        this.product = product;
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
        this.waitingQueue = waitingQueue;

    }

    public void bindConnection(Trader trader) {
        this.connectedTraders.add(trader);
    }

    public void removeConnection(Trader trader) {
        this.connectedTraders.remove(trader);
    }

    public void broadcast(String message) {
        for (Trader trader : connectedTraders) {
            synchronized (trader.getConnection()) {
                try {
                    trader.getConnection().write(ByteBuffer.wrap(message.getBytes())).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Boolean addBuyLimit() {
        while (true) {
            try {
                Order temp = waitingQueue.getBuyLimit();
                buyOrders.addOrder(temp);
                //尝试推送
                try {
                    websocketTest.sendMessage(buyOrders,sellOrders,product);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                //尝试推送
                try {
                    websocketTest.sendMessage(buyOrders,sellOrders,product);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            limitBuy = buyOrders.candidateOrder();
            Order limitSell;
            limitSell = sellOrders.candidateOrder();

            String initFlag = "buy";

            if (limitBuy != null && limitSell != null) {
//                System.out.println("candidate limit: " + limitBuy.getOrderId() + " and " + limitSell.getOrderId());
//                System.out.flush();
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

//                                System.out.println("market buy + limit sell");
//                                System.out.flush();
                                candidateBuy = market;
                                limitBuy.unlock();
                                dealPrice = candidateSell.getPrice();
                                initFlag = "sell";

                            } else {
//                                System.out.println("limit buy + limit sell");
//                                System.out.flush();
                                market.unlock();
                                if (candidateBuy.getTime() < candidateSell.getTime()) {
                                    dealPrice = candidateBuy.getPrice();
                                    initFlag = "buy";
                                } else {
                                    dealPrice = candidateSell.getPrice();
                                    initFlag = "sell";
                                }
                            }
                            break;
                        case 2:
                            if (limitSell.getTime() >= market.getTime()) {
//                                System.out.println("market sell + limit buy");
//                                System.out.flush();
                                candidateSell = market;
                                limitSell.unlock();
                                dealPrice = candidateBuy.getPrice();
                                initFlag = "buy";
                            } else {
//                                System.out.println("limit buy + limit sell");
//                                System.out.flush();
                                market.unlock();
                                if (candidateBuy.getTime() < candidateSell.getTime()) {
                                    dealPrice = candidateBuy.getPrice();
                                    initFlag = "buy";
                                } else {
                                    dealPrice = candidateSell.getPrice();
                                    initFlag = "sell";
                                }

                            }
                            break;
                        case 0:
                            if (candidateBuy.getTime() < candidateSell.getTime()) {
                                dealPrice = candidateBuy.getPrice();
                                initFlag = "buy";
                            } else {
                                dealPrice = candidateSell.getPrice();
                                initFlag = "sell";
                            }
                            break;
                    }
                } else {
                    switch (market_flag) {
                        case 1:
                            candidateBuy = market;
                            candidateSell = limitSell;
                            limitBuy.unlock();
                            dealPrice = candidateSell.getPrice();
                            initFlag = "sell";
                            break;
                        case 2:
                            candidateBuy = limitBuy;
                            candidateSell = market;
                            limitSell.unlock();
                            dealPrice = candidateBuy.getPrice();
                            initFlag = "buy";
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
                    dealPrice = candidateSell.getPrice();
                    initFlag = "sell";
                } else {
                    limitSell.unlock();
                }
            }
            if (limitBuy != null && limitSell == null) {
                if (market_flag == 2) {
                    candidateBuy = limitBuy;
                    candidateSell = market;
                    dealPrice = candidateBuy.getPrice();
                    initFlag = "buy";
                } else {
                    limitBuy.unlock();
                }
            }


            if (candidateSell == null || candidateBuy == null) {


                continue;      // no deal
            }

            //start deal


            String initTrader = "";
            String initCompany = "";
            String initSide = "";
            String compTrader = "";
            String compCompany = "";
            String compSide = "";
            if (initFlag.equals("buy")) {
                initTrader = candidateBuy.getTraderName();
                initCompany = candidateBuy.getTrader().getTraderCompany();
                initSide = "buy";
                compTrader = candidateSell.getTraderName();
                compCompany = candidateSell.getTrader().getTraderCompany();
                compSide = "sell";
            } else {
                compTrader = candidateBuy.getTraderName();
                compCompany = candidateBuy.getTrader().getTraderCompany();
                compSide = "buy";
                initTrader = candidateSell.getTraderName();
                initCompany = candidateSell.getTrader().getTraderCompany();
                initSide = "sell";
            }
            int quantity = Math.min(candidateBuy.getRemainingQuantity(), candidateSell.getRemainingQuantity());
            System.out.println("deal: " + candidateBuy.getOrderId() + " and " + candidateSell.getOrderId() + "  at price: " + dealPrice + " at quantity: " + quantity);
            System.out.flush();
            int tempBuyQuantity = candidateBuy.getRemainingQuantity() - quantity;
            int tempSellQuantity = candidateSell.getRemainingQuantity() - quantity;
            if (tempBuyQuantity == 0) {
                if (candidateBuy.getOrderType().equals("limit")) {
//                    System.out.println("limit Buy remove");
//                    System.out.flush();
                    buyOrders.removeOrder(candidateBuy);
                } else {
//                    System.out.println("remove market buy");
//                    System.out.flush();
                    waitingQueue.getMarket();
                    //交易完成，移除现有market
                    //尝试推送

                }
            } else {
//                System.out.println("buy reset quantity");
//                System.out.flush();
                candidateBuy.setRemainingQuantity(tempBuyQuantity);
            }

            if (tempSellQuantity == 0) {
                if (candidateSell.getOrderType().equals("market")) {
//                    System.out.println("remove market sell");
//                    System.out.flush();
                    waitingQueue.getMarket();
                } else {
//                    System.out.println("remove limit sell");
//                    System.out.flush();
                    sellOrders.removeOrder(candidateSell);
                }
            } else {
//                System.out.println("sell reset quantity");
//                System.out.flush();
                candidateSell.setRemainingQuantity(tempSellQuantity);
            }
            System.out.println("remove complete");
            System.out.flush();

            try {
                websocketTest.sendMessage(buyOrders,sellOrders,product);
            } catch (IOException e) {
                e.printStackTrace();
            }

            candidateBuy.unlock();
            candidateSell.unlock();
            //交易结束
            Long time = System.currentTimeMillis();
            addHistory.add_order
                    (new DoneOrderRaw(brokerName, product.getProductId(), product.getProductPeriod(), dealPrice, quantity, initTrader, initCompany, initSide, compTrader, compCompany, compSide, time + ""));


        }
    }

    public Boolean addWOOrder(Order order) {
        String type = order.getOrderType();
        String buyOrSell = order.getSellOrBuy();
        System.out.println("add order type" + type);
        switch (type) {

            case "limit":
                if (buyOrSell.equals("buy")) {
                    return waitingQueue.addBuyLimit(order);
                } else {
                    return waitingQueue.addSellLimit(order);
                }
            case "market":
                return waitingQueue.addMarket(order);

            case "stop":
                return waitingQueue.addStop(order);

            case "cancel":
                return waitingQueue.addCancel(order);
        }
        return false;
    }

    public Boolean addWOBuyLimit(Order order) {
        return waitingQueue.addBuyLimit(order);
    }

    public Boolean addWOSellLimit(Order order) {
        return waitingQueue.addSellLimit(order);
    }

    public Boolean addWOStop(Order order) {
        return waitingQueue.addStop(order);
    }

    public Boolean addWOCancel(Order order) {
        return waitingQueue.addCancel(order);
    }

    public Boolean addWOMarket(Order order) {
        return waitingQueue.addMarket(order);
    }

    public void setUpOrderBook() {
        Thread addBuyLimitThread = new Thread(new AddBuyLimitThread());
        Thread addSellLimitThread = new Thread(new AddSellLimitThread());
        Thread addStopThread = new Thread(new AddStopThread());
        Thread cancelOrderThread = new Thread(new CancelOrderThread());
        Thread dealThread = new Thread(new DealThread());
        Thread addDbThread = new Thread(new AddDbThread());
        addBuyLimitThread.start();
        addSellLimitThread.start();
        addStopThread.start();
        cancelOrderThread.start();
        dealThread.start();
        addDbThread.start();


    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    class AddBuyLimitThread implements Runnable {
        public void run() {
            System.out.println("add buy limit thread  start....");
            addBuyLimit();
        }
    }

    class AddSellLimitThread implements Runnable {
        public void run() {
            System.out.println("add  sell  limit thread  start....");
            addSellLimit();
        }
    }

    class AddStopThread implements Runnable {

        public void run() {
            System.out.println("add stop thread  start....");
            addStop();
        }
    }

    class CancelOrderThread implements Runnable {
        public void run() {
            System.out.println("add cancel thread  start....");
            cancelOrder();
        }
    }

    class DealThread implements Runnable {
        public void run() {
            System.out.println("deal thread  start....");
            deal();
        }
    }

    class AddDbThread implements Runnable {
        public void run() {
            System.out.println("add history to DB thread  start....");
            while (true) {
                try {
                    addHistory.add_DB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}