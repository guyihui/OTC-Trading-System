package tradergateway.gateway.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @GetMapping("/sendOrder")
    public String sendOrder(
            @RequestParam(value = "productid")String productid
    ){

        return "";
    }
}
