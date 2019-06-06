package tradergateway.gateway.GatewaySocket;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tradergateway.gateway.Entity.Product;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(value = "singleton")
@Lazy(value = false)
public class GatewaySocketService implements InitializingBean {

    private static Map<Product, ProductChannels> connectionMap = new ConcurrentHashMap<>();
    private final String traderCompanyName = "RPCompany";

    @Override
    public void afterPropertiesSet() throws Exception {

        //init map entry
        Product product1 = initProduct("01");
//        Product product2 = initProduct("02");

        BrokerChannel brokerChannel = this.getBrokerChannel("127.0.0.1", 8888);
        if (brokerChannel != null) {//socket已连接，业务逻辑未连接
            if (brokerChannel.connect()) {//阻塞
                System.out.println("connected");
                System.out.println("subscribe product1:" + product1.getProductId());
                if (brokerChannel.subscribeProduct(product1)) {
                    connectionMap.get(product1).addBrokerChannel(brokerChannel);
                }

            }
        }
    }

    private Product initProduct(String productId) {
        Product product1 = new Product(productId);
        connectionMap.putIfAbsent(product1, new ProductChannels(product1));
        return product1;
    }

    private BrokerChannel getBrokerChannel(String hostname, int port) {
        try {
            InetSocketAddress brokerAddress = new InetSocketAddress(hostname, port);
            AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
            channel.connect(brokerAddress).get();
            return new BrokerChannel(channel, traderCompanyName);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
