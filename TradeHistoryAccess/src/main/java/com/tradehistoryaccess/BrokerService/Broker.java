package com.tradehistoryaccess.BrokerService;

import com.tradehistoryaccess.BrokerService.GatewaySocket.ServerSocketChannelAcceptHandle;
import com.tradehistoryaccess.Entity.Trader;
import com.tradehistoryaccess.Entity.TraderManage;
import com.tradehistoryaccess.BrokerService.OrderBook.*;
import com.tradehistoryaccess.Entity.Products;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Lazy(value = false)
@Scope(value = "singleton")
public class Broker implements InitializingBean {
    private static ConcurrentHashMap<Product, Orderbook> orderBookMap = new ConcurrentHashMap<>();
    private static final String brokerName = "RedPanda";//"RedPanda-broker-prototype";

    @Resource
    private TraderManage traderManage;

    @Override
    public void afterPropertiesSet() throws Exception {

        Product gold1 = Products.get("01");
        Orderbook gold1Orderbook = new Orderbook(gold1, brokerName);
        orderBookMap.putIfAbsent(gold1, gold1Orderbook);

        Product oil1 = Products.get("03");
        Orderbook oil1Orderbook = new Orderbook(oil1, brokerName);
        orderBookMap.putIfAbsent(oil1, oil1Orderbook);

//        Product gold2 = Products.get("02");
//        Orderbook gold2Orderbook = new Orderbook(gold2, brokerName);
//        orderBookMap.putIfAbsent(gold2, gold2Orderbook);

//        Product oil2 = Products.get("04");
//        Orderbook oil2Orderbook = new Orderbook(oil2, brokerName);
//        orderBookMap.putIfAbsent(oil2, oil2Orderbook);


        System.out.println("initialize broker complete");

        startupGateway();

        Thread thread2 = new Thread(new TestSocketThread2());
        thread2.start();
        Thread.sleep(3000);
        Thread thread1 = new Thread(new TestSocketThread());
        thread1.start();

    }

    public Boolean addOrder(Order order) {
        Product product = order.getProduct();
        Orderbook orderbook = orderBookMap.get(product);
        System.out.println("add order broker name:" + orderbook.getBrokerName());
        return orderbook.addWOOrder(order);
    }

    private void startupGateway() {
        try {
            //使用线程池管理监听线程
            ExecutorService threadPool = Executors.newFixedThreadPool(20);
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);

            //设置要监听的端口“0.0.0.0”代表本机所有IP设备
            final AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel
                    .open(group)
                    .bind(new InetSocketAddress("0.0.0.0", 8888));

            //为AsynchronousServerSocketChannel注册监听
            channel.accept(null, new ServerSocketChannelAcceptHandle(channel, orderBookMap, traderManage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasProduct(Product product) {
        return orderBookMap.containsKey(product);
    }

    static class TestSocketThread implements Runnable {

        public void run() {
            int count = 0;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {

                int buyDepth = orderBookMap.get(Products.get("01")).getBuyOrders().getDepth();
                int sellDepth = orderBookMap.get(Products.get("01")).getSellOrders().getDepth();

                String buyOrSell;
                if (buyDepth != Integer.MAX_VALUE && sellDepth != 0) {
                    buyOrSell = buyDepth + sellDepth < 2000 ? "buy" : "sell";
                } else {
                    buyOrSell = Math.random() < 0.5 ? "buy" : "sell";
                }

                double random = Math.random();
                String orderType = random < 0.2 ? "stop" : random < 0.22 ? "market" : "limit";

                int price =
                        buyOrSell.equals("buy") ?
                                sellDepth == 0 ?
                                        buyDepth == Integer.MAX_VALUE ?
                                                1000
                                                :
                                                buyDepth + (int) (1.1 * Math.random())
                                        :
                                        sellDepth + (int) ((4.5 * Math.random()) * (orderType.equals("stop") ? -1 : 1))
                                :
                                buyDepth == Integer.MAX_VALUE ?
                                        sellDepth == 0 ?
                                                1000
                                                :
                                                sellDepth - (int) (1.1 * Math.random())
                                        :
                                        buyDepth - (int) ((4.5 * Math.random()) * (orderType.equals("stop") ? -1 : 1));
                Order testOrder = new Order(
                        "test" + count,
                        orderType,
                        price,
                        buyOrSell
                );
                if (testOrder.getOrderType().equals("cancel")) {
                    testOrder.setCancelId("test" + (int) count * Math.random());
                }
                int qqqq = Math.random() < 0.5 ? 20 + (int) (10 * Math.random()) : 1 + (int) (50 * Math.random());
                if (testOrder.getOrderType().equals("market")) {
                    qqqq = 5 + (int) (15 * Math.random());
                }
                testOrder.setRemainingQuantity(qqqq);
                testOrder.setTotalQuantity(qqqq);
                Trader trader = new Trader("1", "CorpA");
                testOrder.setTrader(trader);
                testOrder.setTime(System.currentTimeMillis());
                testOrder.setProduct(new Product("01"));
                //orderBookMap.get(new Product((count%2==0)?"01":"02")).addWOBuyLimit(testOrder);
                orderBookMap.get(new Product("01")).addWOOrder(testOrder);

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                count++;
            }


        }
    }

    static class TestSocketThread2 implements Runnable {

        public void run() {
            int count = 0;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {

                String buyOrSell = Math.random() < 0.5 ? "buy" : "sell";

                double random = Math.random();
                String orderType = random < 0.2 ? "stop" : random < 0.22 ? "market" : "limit";

                Order testOrder = new Order(
                        "test" + count,
                        orderType,
                        992 + (int) (17 * Math.random()),
                        buyOrSell
                );
                if (testOrder.getOrderType().equals("cancel")) {
                    testOrder.setCancelId("test" + (int) count * Math.random());
                }
                int qqqq = Math.random() < 0.5 ? 20 + (int) (10 * Math.random()) : 5 + (int) (35 * Math.random());
                if (testOrder.getOrderType().equals("market")) {
                    qqqq = 5 + (int) (15 * Math.random());
                }
                testOrder.setRemainingQuantity(qqqq);
                testOrder.setTotalQuantity(qqqq);
                Trader trader = new Trader("1", "CorpA");
                testOrder.setTrader(trader);
                testOrder.setTime(System.currentTimeMillis());
                testOrder.setProduct(new Product("01"));
                //orderBookMap.get(new Product((count%2==0)?"01":"02")).addWOBuyLimit(testOrder);
                orderBookMap.get(new Product("01")).addWOOrder(testOrder);

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                count++;
            }


        }
    }
}
