package tradergateway.gateway;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tradergateway.gateway.Entity.Broker;
import tradergateway.gateway.Entity.Order;
import tradergateway.gateway.Entity.Product;
import tradergateway.gateway.Entity.User;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Scope(value = "singleton")
@Lazy(value = false)
public class OrderStorage implements InitializingBean {

    private static Map<Broker, Map<User, Map<Product, Set<Order>>>> allOrders = new ConcurrentHashMap<>();

    public static OrderStorage orderStorage;

    @PostConstruct
    public void init() {
        orderStorage = this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void addOrder(Broker broker, User user, Product product, Order order) {
        //broker
        Map<User, Map<Product, Set<Order>>> userMap = allOrders.get(broker);
        if (userMap == null) {
            allOrders.putIfAbsent(broker, new ConcurrentHashMap<>());
            userMap = allOrders.get(broker);
        }
        //user(trader)
        Map<Product, Set<Order>> productMap = userMap.get(user);
        if (productMap == null) {
            userMap.putIfAbsent(user, new ConcurrentHashMap<>());
            productMap = userMap.get(user);
        }
        //set of orders
        Set<Order> orderSet = productMap.get(product);
        if (orderSet == null) {
            productMap.putIfAbsent(product, new CopyOnWriteArraySet<>());
            orderSet = productMap.get(product);
        }
        orderSet.add(order);
    }

    public void removeOrder(Broker broker, User user, Product product, Order order) {
        try {
            allOrders.get(broker).get(user).get(product).remove(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Order> getFilteredOrders(Broker broker, User user, Product product) {
        try {
            return allOrders.get(broker).get(user).get(product);
        } catch (NullPointerException e) {
            return new CopyOnWriteArraySet<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new CopyOnWriteArraySet<>();
        }
    }

    public Set<Order> getAllOrders(Broker broker, User user, Product product) {
        try {
            Set<Order> result = new HashSet<>();
            for (Broker broker1 : allOrders.keySet()) {
                broker1 = broker;
                Map<User, Map<Product, Set<Order>>> brokerMap = allOrders.get(broker);
                for (User user1 : brokerMap.keySet()) {
                    Map<Product, Set<Order>> userMap = brokerMap.get(user);
                    for (Product product1 : userMap.keySet()) {
                        Set<Order> userOrderSet = userMap.get(product);
                        result.addAll(userOrderSet);
                    }
                }
            }
            return result;
        } catch (NullPointerException e) {
            return new CopyOnWriteArraySet<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new CopyOnWriteArraySet<>();
        }
    }

}
