package tradergateway.gateway.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;


    public String queryBlotter(String productid,String period,String starttime,String endtime,String tradername){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker

        String corpname="01";

        String url="http://localhost:8080/history?productid={productid}&period={period}&starttime={starttime}&endtime={endtime}";

        Map<String,Object>params=new HashMap<>();
        params.put("productid",productid);
        params.put("period",period);
        params.put("starttime",starttime);
        params.put("endtime",endtime);
        params.put("corpname",corpname);

        String blotterjson=restTemplate.getForObject(url,String.class,params);
        return blotterjson;


    }
    public String sendLimitOrder(String traderid,String buyorsell,Integer price,Integer quantity,String productid,String traderName){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url="http://localhost:8080/addOrder";

        Map<String,Object>body=new HashMap<>();
        body.put("type","limit");
        body.put("traderid",traderid);
       // body.put("compName",traderid);
        body.put("productid",productid);
     //   body.put("prodectName",productName);
     //   body.put("period",period);
        body.put("price",price);
        body.put("buyorsell",buyorsell);
        body.put("quantity",quantity);
        body.put("traderName",traderName);

        HttpEntity<Map<String,Object>> reqEntity=new HttpEntity<>(body,headers);
        Map<String,Object> res=restTemplate.postForObject(url,reqEntity,Map.class);
        System.out.println(res.get("id")+" "+res.get("time"));
        return (String)res.get("id");
    }
    public String sendMarket(String traderid,String buyorsell,Integer quantity,String productid,String traderName ){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url="http://localhost:8080/addOrder";

        Map<String,Object>body=new HashMap<>();
        Object put = body.put("type", "market");
        body.put("traderid",traderid);
     //   body.put("compName", traderid);
        body.put("productid",productid);
     //   body.put("prodectName",productName);
     //   body.put("period",period);
        body.put("buyorsell",buyorsell);
        body.put("quantity",quantity);
        body.put("traderName",traderName);

        HttpEntity<Map<String,Object>> reqEntity=new HttpEntity<>(body,headers);
        Map<String,Object> res=restTemplate.postForObject(url,reqEntity,Map.class);
        System.out.println(res.get("id")+" "+res.get("time"));
        return (String)res.get("id");
    }

    public  String sendStop(String traderid,String buyorsell,Integer price,Integer quantity,String productid,String traderName ){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url="http://localhost:8080/addOrder";

        Map<String,Object>body=new HashMap<>();
        body.put("type","stop");
        body.put("traderid",traderid);
   //     body.put("compName",traderid);
        body.put("productid",productid);
   //     body.put("prodectName",productName);
   //     body.put("period",period);
        body.put("price",price);
        body.put("buyorsell",buyorsell);
        body.put("quantity",quantity);
        body.put("traderName",traderName);

        HttpEntity<Map<String,Object>> reqEntity=new HttpEntity<>(body,headers);
        Map<String,Object> res=restTemplate.postForObject(url,reqEntity,Map.class);
        System.out.println(res.get("id")+" "+res.get("time"));
        return (String)res.get("id");
    }
    public String sendCancel(String traderid,String buyorsell,Integer price,String productid,String cancelid,String traderName){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url="http://localhost:8080/addOrder";

        Map<String,Object>body=new HashMap<>();
        body.put("type","cancel");
        body.put("traderid",traderid);
  //      body.put("compName",traderid);
        body.put("productid",productid);
  //      body.put("prodectName",productName);
  //      body.put("period",period);
        body.put("price",price);
        body.put("buyorsell",buyorsell);
        body.put("cancelid",cancelid);
        body.put("traderName",traderName);

        HttpEntity<Map<String,Object>> reqEntity=new HttpEntity<>(body,headers);
        Map<String,Object> res=restTemplate.postForObject(url,reqEntity,Map.class);
        System.out.println(res.get("id")+" "+res.get("time"));
        return (String)res.get("id");
    }
    public Map<String,String>getOrderStates(List<String> orderids){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //TODO:多个broker
        String url="http://localhost:8080/getState";
        Map<String,Object>body=new HashMap<>();
        body.put("orderids",orderids);
        HttpEntity<Map<String,Object>> reqEntity=new HttpEntity<>(body,headers);
        Map<String,String> res=restTemplate.postForObject(url,reqEntity,Map.class);
        for(String id :orderids){
            System.out.println(id+"states: "+res.get(id));
        }
        return res;
    }


}
