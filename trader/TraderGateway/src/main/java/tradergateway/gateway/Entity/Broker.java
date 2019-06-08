package tradergateway.gateway.Entity;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;


public class Broker {

    private String brokerId;
    private String hostname;
    private int port;
    private String apiUrl;
    private BrokerChannel brokerChannel;

    public Broker(String id, String hostname, int port, String apiUrl) {
        this.brokerId = id;
        this.hostname = hostname;
        this.port = port;
        this.apiUrl = apiUrl;
    }

    public boolean connect() {
        try {
            InetSocketAddress brokerAddress = new InetSocketAddress(hostname, port);
            AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
            channel.connect(brokerAddress).get();
            brokerChannel = new BrokerChannel(channel);
            return brokerChannel.connect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean subscribe(Product product) {
        if (brokerChannel != null && brokerChannel.isConnected()) {
            return brokerChannel.subscribeProduct(product);
        } else {
            return false;
        }
    }

    public String getBrokerId() {
        return brokerId;
    }

    public String getUuid() {
        return brokerChannel == null ? null : brokerChannel.getIdentification();
    }

    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Broker && brokerId.equals(((Broker) obj).getBrokerId());
    }

    @Override
    public int hashCode() {
        return "broker".hashCode() + brokerId.hashCode();
    }
}
