package com.tradehistoryaccess.BrokerService.GatewaySocket;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Lazy(value = false)
@Scope(value = "singleton")
public class TraderManage {

    private static ConcurrentHashMap<String, Trader> allTraders = new ConcurrentHashMap<>();

    public Trader buildTraderInstance(AsynchronousSocketChannel channel) {
        Trader trader = new Trader(channel);
        allTraders.put(trader.getUuid().toString(), trader);
        return trader;
    }

    public Trader getTrader(String uuid) {
        return allTraders.get(uuid);
    }
}
