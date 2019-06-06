package tradergateway.gateway.Controller;

//import org.springframework.http.HttpHeaders;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tradergateway.gateway.Service.OrderService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class testRest {
    private final OrderService orderService;

    @Autowired
    public testRest(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/test")
    public String test(){
         String id=orderService.sendLimitOrder("01","buy",1000,15,"01","gold","201907","aaa1");
         orderService.sendCancel("01","buy",1000,"01","gold","201907",id,"aaa1");
         return "";
    }

    @GetMapping(value = "/test2")
    public String test2(){
        orderService.sendLimitOrder("01","sell",1000,15,"01","gold","201907","aaa2");

        return "";
    }
    @GetMapping(value = "/testState")
    public String teststate(){
        List<String> ids=new ArrayList<>();

        String id1=orderService.sendLimitOrder("01","sell",1000,15,"01","gold","201907","aaa2");
        String id2=orderService.sendLimitOrder("01","sell",1100,10,"01","gold","201907","aaa2");
        String id3=orderService.sendStop("01","buy",1050,10,"01","gold","201907","aaa3");
        ids.add(id1);
        ids.add(id2);
        ids.add(id3);
        orderService.getOrderStates(ids);

        String id4=orderService.sendMarket("01","buy",15,"01","gold","201907","aaa4");
        List<String> ids2=new ArrayList<>();
        ids2.add(id3);
        ids2.add(id4);
        orderService.getOrderStates(ids2);
        return "test state";
    }







}
