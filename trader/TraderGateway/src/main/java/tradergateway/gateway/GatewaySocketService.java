package tradergateway.gateway;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tradergateway.gateway.Entity.*;
import tradergateway.gateway.GatewaySocket.ProductChannels;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(value = "singleton")
@Lazy(value = false)
public class GatewaySocketService implements InitializingBean {

    private static Map<Product, ProductChannels> connectionMap = new ConcurrentHashMap<>();
    public static final String traderCompanyName = "RPCompany";

    @Override
    public void afterPropertiesSet() throws Exception {

        //init map entry
        Product product1 = initProduct("01");
        Product product2 = initProduct("02");

        Broker broker = Brokers.get("01");
        broker.connect();
        broker.subscribe(product1);


    }

    private Product initProduct(String productId) {
        Product product1 = Products.get(productId);
        connectionMap.putIfAbsent(product1, new ProductChannels(product1));
        return product1;
    }


}
