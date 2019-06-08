package tradergateway.gateway.GatewaySocket;

import tradergateway.gateway.Entity.BrokerChannel;
import tradergateway.gateway.Entity.Product;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ProductChannels {
    private Product product;
    private Set<BrokerChannel> brokerChannels = new CopyOnWriteArraySet<>();

    public ProductChannels(Product product) {
        this.product = product;
    }

    public void addBrokerChannel(BrokerChannel brokerChannel) {
        this.brokerChannels.add(brokerChannel);
    }

    public Set<BrokerChannel> getBrokerChannels() {
        return brokerChannels;
    }
}
