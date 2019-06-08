package tradergateway.gateway.Service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tradergateway.gateway.Entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Scope(value = "singleton")
@Lazy(value = false)
public class OrderStateService implements InitializingBean {

    @Autowired
    private OrderService orderService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread queryThread = new StateQueryingThread();
        queryThread.start();
    }

    class StateQueryingThread extends Thread {
        @Override
        public void run() {
            List<String> orderIds = new ArrayList<>();
            Map<String, String> states = orderService.getOrderStates(orderIds);
        }
    }

}
