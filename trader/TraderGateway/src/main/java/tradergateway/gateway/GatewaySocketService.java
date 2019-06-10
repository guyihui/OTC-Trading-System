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

//    private static Map<Product, ProductChannels> connectionMap = new ConcurrentHashMap<>();

    public static final String traderCompanyName = "RPCompany";

    @Override
    public void afterPropertiesSet() throws Exception {

        //init map entry
        Product product1 = initProduct("01");
        Product product3 = initProduct("03");

        Broker broker1 = Brokers.get("01");
        if (broker1.connect()) {
            try {
                broker1.subscribe(product1);
                Thread.sleep(1000);
                broker1.subscribe(product3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private Product initProduct(String productId) {
        Product product = Products.get(productId);
//        connectionMap.putIfAbsent(product, new ProductChannels(product));
        return product;
    }


}
