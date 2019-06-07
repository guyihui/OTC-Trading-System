package com.tradehistoryaccess.Redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
@Lazy(value = false)
public class RedisTest  {
    @Autowired
    private  RedisTemplate<String,String> redisTemplate;

    public static RedisTest redisTest;

    public static void setOrderState(String orderid,String state){
        redisTest.redisTemplate.opsForValue().set(orderid,state);
    }
    public static List<String> getOrderState(List<String> orderids){
        return redisTest.redisTemplate.opsForValue().multiGet(orderids);
    }


    @PostConstruct
    public void init(){
        redisTest=this;
    }

}
