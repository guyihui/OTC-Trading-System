package tradergateway.gateway.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;


    public String sendLimitOrder(String traderid,String buyorsell,Integer price,Integer quantity,String productid,String productName,String period,String traderName){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url="http://localhost:8080/addLimit";

        Map<String,Object>body=new HashMap<>();
        body.put("type","limit");
        body.put("traderid",traderid);
        body.put("compName",traderid);
        body.put("productid",productid);
        body.put("prodectName",productName);
        body.put("period",period);
        body.put("price",price);
        body.put("buyorsell",buyorsell);
        body.put("quantity",quantity);
        body.put("traderName",traderName);

        HttpEntity<Map<String,Object>> reqEntity=new HttpEntity<>(body,headers);
        Map<String,Object> res=restTemplate.postForObject(url,reqEntity,Map.class);
     //   String res=restTemplate.getForObject("http://localhost:8080/testadd",String.class);
        System.out.println(res.get("id")+" "+res.get("time"));
        return (String)res.get("id");
    }
    public String sendMarket(String traderid,String buyorsell,Integer price,Integer quantity,String productid,String productName,String period,String traderName ){

    }
}
