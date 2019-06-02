package com.tradehistoryaccess.Controller;

import com.tradehistoryaccess.Service.BrokerService.Broker;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.Order;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.Product;
import com.tradehistoryaccess.Service.BrokerService.OrderBook.Trader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestBrokerController {
    @Resource
    private Broker broker;
    @RequestMapping(value = "/testadd",method = RequestMethod.GET)
    public String AddOrder(
            //@RequestParam(value = "orderid")String orderid,@RequestParam(value = "type")String type,@RequestParam(value = "buyorsell")String buyorsell,@RequestParam(value = "price")Integer price
    ){
        Trader trader=new Trader("1","CorpA");
        Product gold = new Product("01","gold","201907");

        Order order=new Order("asd1","limit",1510,"buy");
        order.setRemainingQuantity(10);
        order.setTime(System.currentTimeMillis());
        order.setTrader(trader);
        order.setTraderName("xiaoming");
        order.setProduct(gold);

        Order order1=new Order("asd2","limit",1500,"sell");
        order1.setRemainingQuantity(10);
        order1.setTime(System.currentTimeMillis()+2);
        order1.setTrader(trader);
        order1.setTraderName("xiaoming");
        order1.setProduct(gold);


        broker.addOrder(order);
        broker.addOrder(order1);

        return "add complete";
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addOrder(
            @RequestParam(value = "orderid")String orderid,@RequestParam(value = "type")String type,@RequestParam(value = "buyorsell")String buyorsell,@RequestParam(value = "price")Integer price,@RequestParam(value = "quantity")Integer quantity
    ){
        Trader trader=new Trader("1","CorpA");
        Product gold = new Product("01","gold","201907");
        Order order=new Order(orderid,type,price,buyorsell);
        order.setRemainingQuantity(quantity);
        order.setTraderName("abc");
        order.setTrader(trader);
        order.setProduct(gold);
        order.setTime(System.currentTimeMillis());

        broker.addOrder(order);
        return "add order "+orderid+" type: "+type+" price: "+price+" end";
    }
}
