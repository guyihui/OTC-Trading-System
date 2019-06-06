package com.tradehistoryaccess.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTest {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    public void setOrderState(String orderid,String state){
        redisTemplate.opsForValue().set(orderid,state);
    }
    public String getOrderState(String orderid){
        return redisTemplate.opsForValue().get(orderid);
    }


}
