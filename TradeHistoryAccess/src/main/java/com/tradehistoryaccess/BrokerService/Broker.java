package com.tradehistoryaccess.BrokerService;

import com.tradehistoryaccess.BrokerService.OrderBook.*;
import com.tradehistoryaccess.BrokerService.Backend2UiSocket.WebSocketTest;
import com.tradehistoryaccess.Redis.RedisTest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Lazy(value = false)
@Scope(value = "singleton")
public class Broker implements InitializingBean {
    private static ConcurrentHashMap<Product, Orderbook> orderBookMap = new ConcurrentHashMap<>();
    private static final String brokerName = "RedPanda";//"RedPanda-broker-prototype";

    @Autowired
    private static WebSocketTest websocketTest;

    @Resource
    private RedisTest redisTest;

    @Override
    public void afterPropertiesSet() throws Exception {

        Product gold = new Product("01", "gold", "201907");
        Orderbook goldOrderbook = new Orderbook(gold, brokerName);

        Product oil = new Product("02", "oil", "201908");
        Orderbook oilOrderbook = new Orderbook(oil, brokerName);

        orderBookMap.putIfAbsent(gold, goldOrderbook);
        orderBookMap.putIfAbsent(oil, oilOrderbook);
        System.out.println("initialize broker  complete");

        startupGateway();
        Thread thread1 = new Thread(new TestSocketThread());
        thread1.start();

    }

    public Boolean addOrder(Order order) {
        Product product = order.getProduct();
        Orderbook orderbook = orderBookMap.get(product);
        System.out.println("addorder broker name:" + orderbook.getBrokerName());
        return orderbook.addWOOrder(order);
    }

    private void startupGateway() {
        try {
            /*
             * 1、Executors是线程池生成工具，可以生成“固定大小的线程池”、“调度池”、“可伸缩线程数量的池”。
             * 2、也可以通过ThreadPoolExecutor直接生成池。
             * 3、这个线程池是用来得到操作系统的“IO事件通知”的，不是用来进行“得到IO数据后的业务处理的”。
             *    要进行后者的操作，您可以再使用一个池（最好不要混用）
             * 4、（不推荐）如果不使用线程池，直接 AsynchronousServerSocketChannel.open()。
             */
            ExecutorService threadPool = Executors.newFixedThreadPool(20);
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);

            //设置要监听的端口“0.0.0.0”代表本机所有IP设备
            final AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel
                    .open(group)
                    .bind(new InetSocketAddress("0.0.0.0", 8888));

            //为AsynchronousServerSocketChannel注册监听
            channel.accept(null, new ServerSocketChannelAcceptHandle(channel, orderBookMap));

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


//            Order testOrder = new Order("test", "limit", 1000, "sell");
//            testOrder.setRemainingQuantity(100000000);
//            testOrder.setTotalQuantity(100000000);
//            Trader trader = new Trader("1", "CorpA");
//            testOrder.setTrader(trader);
//            testOrder.setTime(System.currentTimeMillis());
//            orderBookMap.get(new Product("01")).addWOOrder(testOrder);

//            while (true) {
//                count++;
//                Order buyOrder = new Order("test" + count, "limit", 995 + count, "buy");
//                buyOrder.setRemainingQuantity(count);
//                buyOrder.setTotalQuantity(count);
//                Trader buyTrader = new Trader("2", "CorpB");
//                buyOrder.setTrader(buyTrader);
//                buyOrder.setTime(System.currentTimeMillis());
//                orderBookMap.get(new Product("01")).addWOOrder(buyOrder);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }


            while (true) {

  //              System.out.println("count:" + count);
//                Order testOrder = new Order("test" + count, "limit", (count % 2 == 0) ? (1000 + count % 20) : (1031 - count % 20), (count % 2 == 0) ? "buy" : "sell");
//                testOrder.setRemainingQuantity(50 + count % 20);
//                testOrder.setTotalQuantity(100 + count % 20);
//                Trader trader = new Trader("1", "CorpA");
//                testOrder.setTrader(trader);
//                testOrder.setTime(System.currentTimeMillis());
//                //orderBookMap.get(new Product((count%2==0)?"01":"02")).addWOBuyLimit(testOrder);
//                orderBookMap.get(new Product((count % 3 == 0) ? "02" : "01")).addWOOrder(testOrder);

//                try {
//                    websocketTest.sendMessage(orderBookMap.get(new Product((count%3==0)?"02":"01")).getBuyOrders(),orderBookMap.get(new Product((count%3==0)?"02":"01")).getSellOrders(),new Product((count%3==0)?"02":"01"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


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
