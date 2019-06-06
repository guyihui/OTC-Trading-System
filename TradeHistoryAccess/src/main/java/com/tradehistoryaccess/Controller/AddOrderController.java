package com.tradehistoryaccess.Controller;

import com.tradehistoryaccess.BrokerService.Broker;
import com.tradehistoryaccess.BrokerService.OrderBook.Order;
import com.tradehistoryaccess.BrokerService.OrderBook.Product;
import com.tradehistoryaccess.BrokerService.OrderBook.Trader;
import com.tradehistoryaccess.IdService.OrderIdGenerator;
import com.tradehistoryaccess.Redis.RedisTest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AddOrderController {
    @Resource
    private Broker broker;
    @Resource
    private OrderIdGenerator idGenerator;
    @Resource
    private RedisTest redisTest;

    @RequestMapping(value = "/testadd", method = RequestMethod.GET)
    public String AddOrder(
            //@RequestParam(value = "orderid")String orderid,@RequestParam(value = "type")String type,@RequestParam(value = "buyorsell")String buyorsell,@RequestParam(value = "price")Integer price
    ) {

        redisTest.setOrderState("1","fuck");
      //  System.out.println("states1:" +redisTest.getOrderState("1"));
        redisTest.setOrderState("1","not fuck");
      //  System.out.println("states2:" +redisTest.getOrderState("1"));
//        Trader trader = new Trader("1", "CorpA");
//        Product gold = new Product("01", "gold", "201907");
//
//        Order order = new Order("asd1", "limit", 1510, "buy");
//        order.setRemainingQuantity(10);
//        order.setTime(System.currentTimeMillis());
//        order.setTrader(trader);
//        order.setTraderName("xiaoming");
//        order.setProduct(gold);
//
//        Order order1 = new Order("asd2", "limit", 1500, "sell");
//        order1.setRemainingQuantity(10);
//        order1.setTime(System.currentTimeMillis() + 2);
//        order1.setTrader(trader);
//        order1.setTraderName("xiaoming");
//        order1.setProduct(gold);
//
//
//        broker.addOrder(order);
//        broker.addOrder(order1);

        return "add complete";
    }

    @PostMapping(value = "/addOrder")
    public Map<String, Object> addLimitOrder(
            //     @RequestParam(value = "orderid")String orderid,@RequestParam(value = "buyorsell")String buyorsell,@RequestParam(value = "price")Integer price,@RequestParam(value = "quantity")Integer quantity
            @RequestBody Map<String, Object> request
    ) {
        String type = (String) request.get("type");

        Trader trader = new Trader((String) request.get("traderid"), (String) request.get("compName"));
        Product product = new Product((String) request.get("productid"), (String) request.get("prodectName"), (String) request.get("period"));
        long time = System.currentTimeMillis();
        String id = idGenerator.getID((String) request.get("traderid"), (String) request.get("productid"), time);
        Order order;
        switch (type) {
            case "limit": case "stop":
                order = new Order(id, type, (Integer) request.get("price"), (String) request.get("buyorsell"));
                order.setRemainingQuantity((Integer) request.get("quantity"));
                order.setTraderName((String) request.get("traderName"));
                order.setTrader(trader);
                order.setProduct(product);
                order.setTime(time);
                redisTest.setOrderState(id,"waiting");
                broker.addOrder(order);
                break;
            case "market":
                order = new Order(id, type, 0, (String) request.get("buyorsell"));
                order.setRemainingQuantity((Integer) request.get("quantity"));
                order.setTraderName((String) request.get("traderName"));
                order.setTrader(trader);
                order.setProduct(product);
                order.setTime(time);
                redisTest.setOrderState(id,"waiting");
                broker.addOrder(order);
                break;
            case "cancel":
                order = new Order(id, type, (Integer) request.get("price"), (String) request.get("buyorsell"));
                order.setRemainingQuantity(0);
                order.setTraderName((String) request.get("traderName"));
                order.setTrader(trader);
                order.setProduct(product);
                order.setTime(time);
                order.setCancelId((String)request.get("cancelid"));
                redisTest.setOrderState(id,"waiting");
                broker.addOrder(order);
        }

        Map<String, Object> ret = new HashMap<>();
        ret.put("id", id);
        ret.put("time", "" + time);
        return ret;
    }
}
