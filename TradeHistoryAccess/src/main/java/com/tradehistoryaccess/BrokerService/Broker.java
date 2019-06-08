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

        Product gold2 = Products.get("02");
        Orderbook gold2Orderbook = new Orderbook(gold2, brokerName);

//        Product oil1 = Products.get("03");
//        Orderbook oil1Orderbook = new Orderbook(oil1, brokerName);
//        Product oil2 = Products.get("04");
//        Orderbook oil2Orderbook = new Orderbook(oil2, brokerName);


        orderBookMap.putIfAbsent(gold1, gold1Orderbook);
        orderBookMap.putIfAbsent(gold2, gold2Orderbook);
//        orderBookMap.putIfAbsent(oil1, oil1Orderbook);
//        orderBookMap.putIfAbsent(oil2, oil2Orderbook);

        System.out.println("initialize broker  complete");

        startupGateway();
 //       Thread thread1 = new Thread(new TestSocketThread());
 //       thread1.start();

    }

    public Boolean addOrder(Order order) {
        Product product = order.getProduct();
        Orderbook orderbook = orderBookMap.get(product);
        System.out.println("addorder broker name:" + orderbook.getBrokerName());
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

    static class TestSocketThread implements Runnable {
        public void run() {
            int count = 0;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {

                double random = Math.random();
                Order testOrder = new Order(
                        "test" + count,
                        random < 0.2 ? "stop" : random < 0.4 ? "cancel" : "limit",
                        995 + (int) (11 * Math.random()),
                        Math.random() < 0.5 ? "buy" : "sell"
                );
                if (testOrder.getOrderType().equals("cancel")) {
                    testOrder.setCancelId("test" + (int) count * Math.random());
                }
                int qqqq = Math.random() < 0.5 ? 20 + (int) (10 * Math.random()) : 1 + (int) (100 * Math.random());
                testOrder.setRemainingQuantity(qqqq);
                testOrder.setTotalQuantity(qqqq);
                Trader trader = new Trader("1", "CorpA");
                testOrder.setTrader(trader);
                testOrder.setTime(System.currentTimeMillis());
                testOrder.setProduct(new Product("01"));
                //orderBookMap.get(new Product((count%2==0)?"01":"02")).addWOBuyLimit(testOrder);
                orderBookMap.get(new Product("01")).addWOOrder(testOrder);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                try {
//                    websocketTest.sendMessage(
//                            orderBookMap.get(new Product("01")).getBuyOrders(),
//                            orderBookMap.get(new Product("01")).getSellOrders(),
//                            new Product("01"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                count++;
            }


        }
    }
}
