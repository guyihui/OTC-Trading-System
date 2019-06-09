package tradergateway.gateway.Service;

import org.springframework.stereotype.Service;
import tradergateway.gateway.Entity.BigOrder;

import javax.annotation.Resource;

@Service
public class BigOrderService {
    @Resource
    private OrderService orderService;

    public void TwapService(BigOrder bigOrder,String totalTime,String freqTime){
        
    }
}
