package tradergateway.gateway.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tradergateway.gateway.Entity.*;
import tradergateway.gateway.OrderStorage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private OrderStorage orderStorage;

    public String queryBlotter(String brokerId, String productId, String startTime, String endTime, String corpId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get(brokerId).getApiUrl() + "/history?" +
                "productid={productid}" +
                "&starttime={starttime}" +
                "&endtime={endtime}" +
                "&corpid={corpid}" +
                "&tradername={tradername}";

        Map<String, Object> params = new HashMap<>();
        params.put("productid", productId);
        params.put("starttime", startTime);
        params.put("endtime", endTime);
        params.put("corpid", corpId);
        params.put("tradername", traderName);

        String blotterJson = restTemplate.getForObject(url, String.class, params);
        return blotterJson;


    }

    public String sendLimitOrder(String brokerId, String traderId, String buyOrSell, Integer price, Integer quantity, String productId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get(brokerId).getApiUrl() + "/addOrder";

        Map<String, Object> body = new HashMap<>();
        body.put("type", "limit");
        body.put("traderid", traderId);
        // body.put("compName",traderid);
        body.put("productid", productId);
        //   body.put("prodectName",productName);
        //   body.put("period",period);
        body.put("price", price);
        body.put("buyorsell", buyOrSell);
        body.put("traderName", traderName);
        body.put("quantity", quantity);

        HttpEntity<Map<String, Object>> reqEntity = new HttpEntity<>(body, headers);
        Map<String, Object> res = restTemplate.postForObject(url, reqEntity, Map.class);
        System.out.println(res.get("id") + " " + res.get("time"));

        Order order = new Order();
        order.setOrderId((String) res.get("id"));
        order.setBroker("01");        //TODO: 多个broker
        order.setOrderType("limit");
        order.setProduct(Products.get(productId));
        order.setSellOrBuy(buyOrSell);
        order.setTraderName(traderName);
        order.setTotalQuantity(quantity);
        order.setRemainingQuantity(quantity);
        order.setPrice(price);
        order.setTime((Long) res.get("time"));
        order.setState("waiting");

        Broker broker = Brokers.get(brokerId);
        User user = new User(traderName);
        Product product = Products.get(productId);

        orderStorage.addOrder(broker, user, product, order);

        return (String) res.get("id");
    }

    public String sendMarket(String brokerId, String traderId, String buyOrSell, Integer quantity, String productId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get(brokerId).getApiUrl() + "/addOrder";

        Map<String, Object> body = new HashMap<>();
        Object put = body.put("type", "market");
        body.put("traderid", traderId);
        //   body.put("compName", traderId);
        body.put("productid", productId);
        //   body.put("productName",productName);
        //   body.put("period",period);
        body.put("quantity", quantity);
        body.put("traderName", traderName);
        body.put("buyorsell", buyOrSell);

        HttpEntity<Map<String, Object>> reqEntity = new HttpEntity<>(body, headers);
        Map<String, Object> res = restTemplate.postForObject(url, reqEntity, Map.class);

        Order order = new Order();
        order.setOrderId((String) res.get("id"));
        order.setBroker("01");        //TODO: 多个broker
        order.setOrderType("market");
        order.setProduct(Products.get(productId));
        order.setSellOrBuy(buyOrSell);
        order.setTraderName(traderName);
        order.setTotalQuantity(quantity);
        order.setRemainingQuantity(quantity);
        order.setPrice(0);
        order.setTime((Long) res.get("time"));
        order.setState("waiting");

        Broker broker = Brokers.get(brokerId);
        User user = new User(traderName);
        Product product = Products.get(productId);
        orderStorage.addOrder(broker, user, product, order);


        System.out.println(res.get("id") + " " + res.get("time"));
        return (String) res.get("id");
    }

    public String sendStop(String brokerId, String traderId, String buyOrSell, Integer price, Integer quantity, String productId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get(brokerId).getApiUrl() + "/addOrder";

        Map<String, Object> body = new HashMap<>();
        body.put("type", "stop");
        body.put("traderid", traderId);
        body.put("productid", productId);
        //     body.put("period",period);
        body.put("price", price);
        body.put("buyorsell", buyOrSell);
        body.put("quantity", quantity);
        body.put("traderName", traderName);

        HttpEntity<Map<String, Object>> reqEntity = new HttpEntity<>(body, headers);
        Map<String, Object> res = restTemplate.postForObject(url, reqEntity, Map.class);

        Order order = new Order();
        order.setOrderId((String) res.get("id"));
        order.setBroker("01");        //TODO: 多个broker
        order.setOrderType("stop");
        order.setProduct(Products.get(productId));
        order.setSellOrBuy(buyOrSell);
        order.setTraderName(traderName);
        order.setTotalQuantity(quantity);
        order.setRemainingQuantity(quantity);
        order.setPrice(price);
        order.setTime((Long) res.get("time"));
        order.setState("waiting");

        Broker broker = Brokers.get(brokerId);
        User user = new User(traderName);
        Product product = Products.get(productId);

        orderStorage.addOrder(broker, user, product, order);


        System.out.println(res.get("id") + " " + res.get("time"));
        return (String) res.get("id");
    }

    public String sendCancel(String brokerId, String traderId, String buyOrSell, Integer price, String productId, String cancelId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get(brokerId).getApiUrl() + "/addOrder";

        Map<String, Object> body = new HashMap<>();
        body.put("type", "cancel");
        body.put("traderid", traderId);
        //      body.put("compName",traderid);
        body.put("productid", productId);
        //      body.put("prodectName",productName);
        //      body.put("period",period);
        body.put("price", price);
        body.put("buyorsell", buyOrSell);
        body.put("cancelid", cancelId);
        body.put("traderName", traderName);

        HttpEntity<Map<String, Object>> reqEntity = new HttpEntity<>(body, headers);
        Map<String, Object> res = restTemplate.postForObject(url, reqEntity, Map.class);

        Order order = new Order();
        order.setOrderId((String) res.get("id"));
        order.setBroker("01");        //TODO: 多个broker
        order.setOrderType("cancel");
        order.setProduct(Products.get(productId));
        order.setSellOrBuy(buyOrSell);
        order.setTraderName(traderName);
        order.setTotalQuantity(0);
        order.setRemainingQuantity(0);
        order.setPrice(price);
        order.setTime((Long) res.get("time"));
        order.setCancelId(cancelId);
        order.setState("waiting");

        Broker broker = Brokers.get(brokerId);
        User user = new User(traderName);
        Product product = Products.get(productId);

        orderStorage.addOrder(broker, user, product, order);


        System.out.println(res.get("id") + " " + res.get("time"));
        return (String) res.get("id");
    }

    public Map<String, String> getOrderStates(String brokerId, List<String> orderIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get(brokerId).getApiUrl() + "/getState";
        Map<String, Object> body = new HashMap<>();
        body.put("orderids", orderIds);
        HttpEntity<Map<String, Object>> reqEntity = new HttpEntity<>(body, headers);
        Map<String, String> res = restTemplate.postForObject(url, reqEntity, Map.class);
        for (String id : orderIds) {
            System.out.println(id + "states: " + res.get(id));
        }
        return res;
    }


}
