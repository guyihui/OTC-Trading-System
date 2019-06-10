package tradergateway.gateway.Entity;

import java.util.HashMap;
import java.util.Map;

public class Brokers {
    private static Map<String, Broker> brokers = new HashMap<>();

    public static Broker get(String id) {
        return brokers.get(id);
    }

    static {
        brokers.put("01", new Broker("01", "127.0.0.1", 8888, "http://localhost:30482"));
        brokers.put("02", new Broker("02", "127.0.0.1", 8889, "http://localhost:30484"));
    }
}
