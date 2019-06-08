package tradergateway.gateway.Service;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tradergateway.gateway.Backend2UiSocket.WebSocketTest;
import tradergateway.gateway.Entity.Brokers;
import tradergateway.gateway.Entity.Order;
import tradergateway.gateway.Entity.Product;
import tradergateway.gateway.OrderStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Scope(value = "singleton")
@Lazy(value = false)
public class OrderStateService implements InitializingBean {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStorage orderStorage;

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread queryThread = new StateQueryingThread();
        queryThread.start();
    }

    class StateQueryingThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<Product, CopyOnWriteArraySet<WebSocketTest>> map = WebSocketTest.getWebSocketMap();
                System.err.printf("query state: %d products", map.size());
                for (Product product : map.keySet()) {
                    for (WebSocketTest webSocketTest : map.get(product)) {
                        Set<Order> orders = orderStorage.getFilteredOrders(
                                Brokers.get("01"),
                                webSocketTest.getUser(),
                                product
                        );
                        List<String> orderIds = new ArrayList<>();
                        for (Order order : orders) {
                            orderIds.add(order.getOrderId());
                        }
                        System.err.println("query state");
                        Map<String, String> states = orderService.getOrderStates(orderIds);
                        System.err.println(states.size());

                    }
                }
            }
        }
    }

}
