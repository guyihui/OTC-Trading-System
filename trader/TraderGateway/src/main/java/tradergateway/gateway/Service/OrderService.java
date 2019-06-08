package tradergateway.gateway.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tradergateway.gateway.Entity.Brokers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;


    public String queryBlotter(String productId, String period, String startTime, String endTime, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker

        String corpName = "01";

        String url = Brokers.get("01").getApiUrl() + "/history?" +
                "productid={productid}" +
                "&period={period}" +
                "&starttime={starttime}" +
                "&endtime={endtime}";

        Map<String, Object> params = new HashMap<>();
        params.put("productid", productId);
        params.put("period", period);
        params.put("starttime", startTime);
        params.put("endtime", endTime);
        params.put("corpname", corpName);

        String blotterJson = restTemplate.getForObject(url, String.class, params);
        return blotterJson;


    }

    public String sendLimitOrder(String traderId, String buyOrSell, Integer price, Integer quantity, String productId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get("01").getApiUrl() + "/addOrder";

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
        return (String) res.get("id");
    }

    public String sendMarket(String traderId, String buyOrSell, Integer quantity, String productId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get("01").getApiUrl() + "/addOrder";

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
        System.out.println(res.get("id") + " " + res.get("time"));
        return (String) res.get("id");
    }

    public String sendStop(String traderId, String buyOrSell, Integer price, Integer quantity, String productId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get("01").getApiUrl() + "/addOrder";

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
        System.out.println(res.get("id") + " " + res.get("time"));
        return (String) res.get("id");
    }

    public String sendCancel(String traderId, String buyOrSell, Integer price, String productId, String cancelId, String traderName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get("01").getApiUrl() + "/addOrder";

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
        System.out.println(res.get("id") + " " + res.get("time"));
        return (String) res.get("id");
    }

    public Map<String, String> getOrderStates(List<String> orderIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url = Brokers.get("01").getApiUrl() + "/getState";
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
