package tradergateway.gateway.Controller;

//import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tradergateway.gateway.Entity.Brokers;
import tradergateway.gateway.Service.OrderService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class testRest {
    @Resource
    private OrderService orderService;

    @Autowired
    public testRest(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/testdeal")
    public String test() {
        String id = orderService.sendLimitOrder(Brokers.get("01").getBrokerId(), Brokers.get("01").getUuid(), "buy", 1000, 15, "01", "aaa1");
        String id2 = orderService.sendLimitOrder(Brokers.get("01").getBrokerId(), Brokers.get("01").getUuid(), "sell", 1000, 15, "01", "aaa1");
        //   orderService.sendCancel(TraderInfo.getUuid(),"buy",1000,"01",id,"aaa1");
        return "";
    }

    @GetMapping(value = "/test2")
    public String test2() {
        orderService.sendLimitOrder(Brokers.get("01").getBrokerId(), Brokers.get("01").getUuid(), "sell", 1000, 15, "01", "aaa2");
        return "";
    }

    @GetMapping(value = "/testState")
    public String teststate() {
        List<String> ids = new ArrayList<>();

        String id1 = orderService.sendLimitOrder(Brokers.get("01").getBrokerId(), Brokers.get("01").getUuid(), "sell", 1000, 15, "01", "aaa2");
        String id2 = orderService.sendLimitOrder(Brokers.get("01").getBrokerId(), Brokers.get("01").getUuid(), "sell", 1100, 10, "01", "aaa2");
        String id3 = orderService.sendStop(Brokers.get("01").getBrokerId(), Brokers.get("01").getUuid(), "buy", 1050, 10, "01", "aaa3");
        ids.add(id1);
        ids.add(id2);
        ids.add(id3);
        orderService.getOrderStates(Brokers.get("01").getBrokerId(), ids);

        String id4 = orderService.sendCancel(Brokers.get("01").getBrokerId(), Brokers.get("01").getUuid(), "sell", 1000, "01", id1, "jb");
        List<String> ids2 = new ArrayList<>();
        ids2.add(id1);

        ids2.add(id3);
        ids2.add(id4);
        orderService.getOrderStates(Brokers.get("01").getBrokerId(), ids2);
        return "test state";
    }


}
