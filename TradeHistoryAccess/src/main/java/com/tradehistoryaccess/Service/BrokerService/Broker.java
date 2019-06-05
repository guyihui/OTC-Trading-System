package com.tradehistoryaccess.Service.BrokerService;

import com.tradehistoryaccess.Service.BrokerService.OrderBook.Order;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.Orderbook;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.Product;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.ServerSocketChannelAcceptHandle;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
    private static final String brokerName = "RedPanda-broker-prototype";

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

    }

    public Boolean addOrder(Order order) {
        Product product = order.getProduct();
        if (product == null) {
            return false;
        }
        Orderbook orderbook = orderBookMap.get(product);
        System.out.println("addOrder broker name:" + orderbook.getBrokerName());
        return orderBookMap.containsKey(product) ? orderbook.addWOOrder(order) : false;
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

}
