package tradergateway.gateway;

import org.springframework.stereotype.Service;
import tradergateway.gateway.Entity.*;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class BigOrderStorage {

    private static Map<Broker, Map<User, Map<Product, Set<BigOrder>>>> allOrders = new ConcurrentHashMap<>();

    public static BigOrderStorage bigOrderStorage;

    @PostConstruct
    public void init() {
        bigOrderStorage = this;
    }

    public void addOrder(Broker broker, User user, Product product, BigOrder bigOrder) {
        //broker
        Map<User, Map<Product, Set<BigOrder>>> userMap = allOrders.get(broker);
        if (userMap == null) {
            allOrders.putIfAbsent(broker, new ConcurrentHashMap<>());
            userMap = allOrders.get(broker);
        }
        //user(trader)
        Map<Product, Set<BigOrder>> productMap = userMap.get(user);
        if (productMap == null) {
            userMap.putIfAbsent(user, new ConcurrentHashMap<>());
            productMap = userMap.get(user);
        }
        //set of orders
        Set<BigOrder> orderSet = productMap.get(product);
        if (orderSet == null) {
            productMap.putIfAbsent(product, new CopyOnWriteArraySet<>());
            orderSet = productMap.get(product);
        }
        orderSet.add(bigOrder);
    }

    public void removeOrder(Broker broker, User user, Product product, BigOrder bigOrder) {
        try {
            allOrders.get(broker).get(user).get(product).remove(bigOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<BigOrder> getFilteredOrders(Broker broker, User user, Product product) {
        try {
            Set<BigOrder> bigOrders = allOrders.get(broker).get(user).get(product);
            if (bigOrders == null) {
                return new CopyOnWriteArraySet<>();
            }
            return bigOrders;
        } catch (NullPointerException e) {
            return new CopyOnWriteArraySet<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new CopyOnWriteArraySet<>();
        }
    }

}
