package tradergateway.gateway.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tradergateway.gateway.Entity.Brokers;
import tradergateway.gateway.Service.OrderService;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @CrossOrigin(value = "*")
    @GetMapping("/sendOrder")
    public String sendOrder(
            @RequestParam(value = "productId") String productid, @RequestParam(value = "type") String type,
            @RequestParam(value = "sellOrBuy") String sellorbuy, @RequestParam(value = "price") Integer price,
            @RequestParam(value = "quantity") Integer quantity, @RequestParam(value = "traderName") String name

    ) {
        String orderid = "";
        switch (type) {
            case "limit":
                orderid = orderService.sendLimitOrder(Brokers.get("01").getUuid(), sellorbuy, price, quantity, productid, name);
                break;
            case "stop":
                orderid = orderService.sendStop(Brokers.get("01").getUuid(), sellorbuy, price, quantity, productid, name);
                break;
            case "market":
                orderid = orderService.sendMarket(Brokers.get("01").getUuid(), sellorbuy, quantity, productid, name);
                break;
        }
        return orderid;
    }

    @CrossOrigin(value = "*")
    @GetMapping("/sendCancel")
    public String sendCancel(
            @RequestParam(value = "productId") String productid, @RequestParam(value = "sellOrBuy") String sellorbuy,
            @RequestParam(value = "price") Integer price, @RequestParam(value = "cancelId") String cancelid,
            @RequestParam(value = "traderName") String name
    ) {

        return orderService.sendCancel(Brokers.get("01").getUuid(), sellorbuy, price, productid, cancelid, name);


    }

    @CrossOrigin(value = "*")
    @GetMapping("/getBlotter")
    public String getBlotter(
            @RequestParam(value = "productId") String productid, @RequestParam(value = "startTime") String starttime,
            @RequestParam(value = "endTime") String endtime, @RequestParam(value = "traderName") String name
    ) {

        return orderService.queryBlotter(productid, starttime, endtime, Brokers.get("01").getUuid(), name);
    }
}
