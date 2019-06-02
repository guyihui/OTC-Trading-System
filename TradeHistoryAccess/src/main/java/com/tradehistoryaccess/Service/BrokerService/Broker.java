package com.tradehistoryaccess.Service.BrokerService;

import com.tradehistoryaccess.Service.BrokerService.OrderBook.Order;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.Orderbook;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.Product;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Lazy(value = false)
@Scope(value = "singleton")
public class Broker implements InitializingBean {
    private static ConcurrentHashMap<Product,Orderbook> orderBookMap = new ConcurrentHashMap<Product, Orderbook>();

    @Override
    public void afterPropertiesSet() throws Exception {

        Product gold = new Product("01","gold","201907");
        Orderbook goldOrderbook = new Orderbook(gold);
        goldOrderbook.setBrokerName("Broker1");
        Product oil = new Product("02","oil","201908");
        Orderbook oilOrderbook = new Orderbook(oil);
        oilOrderbook.setBrokerName("Broker1");
        orderBookMap.putIfAbsent(gold,goldOrderbook);
        orderBookMap.putIfAbsent(oil,oilOrderbook);
        System.out.println("initialize broker  complete");
    }

    public Boolean addOrder(Order order){
        Product product=order.getProduct();
        Orderbook orderbook=orderBookMap.get(product);
        System.out.println("addorder broker name:"+orderbook.getBrokerName());
        return orderbook.addWOOrder(order);
    }

}
