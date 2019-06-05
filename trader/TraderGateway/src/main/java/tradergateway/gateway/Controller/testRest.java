package tradergateway.gateway.Controller;

//import org.springframework.http.HttpHeaders;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tradergateway.gateway.Service.OrderService;

@RestController
public class testRest {
    private final OrderService orderService;

    @Autowired
    public testRest(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/test")
    public String test(){
         orderService.sendLimitOrder("01","buy",1000,15,"01","gold","201907","SB1");
         orderService.sendLimitOrder("01","sell",1000,15,"01","gold","201907","SB2");

         return "";
    }








}
