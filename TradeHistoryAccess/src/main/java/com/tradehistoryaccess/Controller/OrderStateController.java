package com.tradehistoryaccess.Controller;

import com.tradehistoryaccess.Redis.RedisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderStateController {
    @Autowired
    private RedisTest redisTest;

    @PostMapping("/getState")
    public Map<String,String> getState(
            @RequestBody Map<String,Object> req
    ){
        List<String>orderids=(List<String>)req.get("orderids");
        for(String id : orderids){
            System.out.println("query orders: "+id);
        }
        Map<String,String> ret=new HashMap<>();
        List<String> states=redisTest.getOrderState(orderids);
        for(String i : states){
            System.out.println("states: "+i);
        }
        for(int i=0;i<orderids.size();i++){
            ret.put(orderids.get(i),states.get(i));
        }
        return  ret;
    }

}
