package tradergateway.gateway.Service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tradergateway.gateway.Backend2UiSocket.WebSocketTest;
import tradergateway.gateway.Entity.Broker;
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

    @Autowired
    private WebSocketTest webSocketTest;

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
                System.err.printf("query state: %d products\n", map.size());

                List<Order> orders = new ArrayList<>();


                for (Product product : map.keySet()) {
                    for (WebSocketTest webSocketTest : map.get(product)) {
                        //取出需要查询的 orders
                        Set<Order> filtered =
                                orderStorage.getFilteredOrders(
                                        webSocketTest.getAskedBroker(),
                                        webSocketTest.getUser(),
                                        webSocketTest.getAskedProduct()
                                );
                        if (filtered != null) {
                            orders.addAll(filtered);
                        }
                    }
                }
                List<String> orderIds = new ArrayList<>();
                //得到 order id 的 list
                for (Order order : orders) {
                    orderIds.add(order.getOrderId());
                }
                if (orderIds.size() > 0) {
                    //获取查询结果
                    Map<String, String> states = orderService.getOrderStates(orderIds);
                    System.err.println(states.size());
                    //更新状态
                    for (Order order : orders) {
                        String state = states.get(order.getOrderId());
                        if (order.getFlag() > 0) {
                            order.incrementFlag();
                        }
                        if (state.indexOf("remain:") == 0) {
                            int remain = Integer.valueOf(state.substring("remain:".length()));
                            order.setRemainingQuantity(remain);
                            if (remain == 0) {
                                order.incrementFlag();
                            }
                        } else if (state.indexOf("canceled,remain:") == 0) {
                            int remain = Integer.valueOf(state.substring("canceled,remain:".length()));
                            order.setRemainingQuantity(remain);
                            order.incrementFlag();
                        } else if (state.indexOf("success") == 0 || state.indexOf("fail") == 0) {
                            order.incrementFlag();
                        }
                        order.setState(state);
                    }
                }
                webSocketTest.sendOrderState();
            }
        }
    }

}
