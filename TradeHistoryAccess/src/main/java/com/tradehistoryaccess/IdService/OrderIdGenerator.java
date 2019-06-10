package com.tradehistoryaccess.IdService;

import org.hibernate.annotations.Synchronize;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Scope(value = "singleton")
public class OrderIdGenerator {

    private final long startTime=0L;
    private final int timeBits=15;
    private final int flagBits=3;
    private final int productbits=2;

    private int flag=0;

    public synchronized  String getID(String traderid,String productId,Long time){
        flag=(flag+1)%1000;
        String flagStr=String.format("%0"+flagBits+"d",flag);
        String timeStr=String.format("%0"+timeBits+"d",time-startTime);
        return traderid+productId+timeStr+flagStr;

    }
}
