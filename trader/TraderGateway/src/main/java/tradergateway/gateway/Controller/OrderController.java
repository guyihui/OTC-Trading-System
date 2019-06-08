package tradergateway.gateway.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tradergateway.gateway.Entity.TraderInfo;
import tradergateway.gateway.Service.OrderService;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @CrossOrigin(origins = "http://localhost:3000",maxAge = 3600)
    @GetMapping("/sendOrder")
    public String sendOrder(
            @RequestParam(value = "productId")String productid,@RequestParam(value = "type")String type,
            @RequestParam(value = "sellOrBuy")String sellorbuy,@RequestParam(value = "price")Integer price,
            @RequestParam(value = "quantity")Integer quantity,@RequestParam(value = "traderName")String name

    ){
        String orderid="";
        switch (type){
            case "limit":
                orderid=orderService.sendLimitOrder(TraderInfo.getUuid(),sellorbuy,price,quantity,productid,name);
                break;
            case "stop":
                orderid=orderService.sendStop(TraderInfo.getUuid(),sellorbuy,price,quantity,productid,name);
                break;
            case "market":
                orderid=orderService.sendMarket(TraderInfo.getUuid(),sellorbuy,quantity,productid,name);
                break;
        }
        return orderid;
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/sendCancel")
    public String sendCancel(
            @RequestParam(value = "productId")String productid,@RequestParam(value = "sellOrBuy")String sellorbuy,
            @RequestParam(value = "price")Integer price,@RequestParam(value = "cancelId")String cancelid,
            @RequestParam(value = "traderName")String name
    ){

        return orderService.sendCancel(TraderInfo.getUuid(),sellorbuy,price,productid,cancelid,name);


    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @GetMapping("/getBlotter")
    public String getBlotter(
            @RequestParam(value = "productId")String productid,@RequestParam(value = "startTime")String starttime,
            @RequestParam(value = "endTime")String endtime,@RequestParam(value = "traderName")String name
    ){

        return orderService.queryBlotter(productid,starttime,endtime,TraderInfo.getUuid(),name);
    }
}
