package tradergateway.gateway.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tradergateway.gateway.BigOrderStorage;
import tradergateway.gateway.Entity.*;
import tradergateway.gateway.Service.BigOrderService;

import javax.annotation.Resource;
import java.util.Set;

@RestController
public class BigOrderController {
    @Resource
    private BigOrderService bigOrderService;
    @Resource
    private BigOrderStorage bigOrderStorage;
    @GetMapping(value = "/sendBigOrder")
    public void sendBigOrder(
            @RequestParam(value = "productId")String productid,
            @RequestParam(value = "sellOrBuy")String sellOrBuy,
            @RequestParam(value = "quantity") Integer quantity,
            @RequestParam(value = "traderName") String traderName,
            @RequestParam(value = "totalSeconds") Integer totalSeconds,
            @RequestParam(value = "intervalSeconds") Integer freqSeconds,
            @RequestParam(value = "brokerId") String brokerId,
            @RequestParam(value = "strategy")String strategy
    ){

        BigOrder bigOrder=new BigOrder();
        bigOrder.setProduct(Products.get(productid));
        bigOrder.setSellOrBuy(sellOrBuy);
        bigOrder.setStrategy(strategy);
        bigOrder.setBroker(brokerId);
        bigOrder.setTraderName(traderName);
        bigOrder.setTotalQuantity(quantity);
        bigOrder.setUnsentQuantity(quantity);

        Broker broker= Brokers.get(brokerId);
        Product product=Products.get(productid);
        User user=new User(traderName);
        bigOrderStorage.addOrder(broker,user,product,bigOrder);

        bigOrderService.TwapService(bigOrder,totalSeconds,freqSeconds,brokerId);


    }

    @GetMapping(value = "/cancelBigOrder")
    public void cancelBigOrder(
            @RequestParam(value = "brokerId") String brokerId,
            @RequestParam(value = "bigOrderId")String bigOrderId,
            @RequestParam(value = "traderName")String traderName,
            @RequestParam(value = "productId")String productId
    ){
        Broker broker=Brokers.get(brokerId);
        User user=new User(traderName);
        Product product=Products.get(productId);
        Set<BigOrder> bigOrders=bigOrderStorage.getFilteredOrders(broker,user,product);
        for(BigOrder bigOrder:bigOrders){
            if(bigOrder.getId().equals(bigOrderId)){
                bigOrder.setCancelFlag(true);
            }
        }

    }
}
