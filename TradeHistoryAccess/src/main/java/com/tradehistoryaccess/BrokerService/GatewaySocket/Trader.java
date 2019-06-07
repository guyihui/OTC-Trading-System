package com.tradehistoryaccess.BrokerService.GatewaySocket;

import com.tradehistoryaccess.BrokerService.OrderBook.Product;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class Trader {

    private String traderId;
    private String traderCompany;
    private AsynchronousSocketChannel connection;
    private Set<Product> subscribedProducts = new CopyOnWriteArraySet<>();
    private UUID uuid = UUID.randomUUID();

    public Trader(AsynchronousSocketChannel channel) {
        this.connection = channel;
    }

    public Trader(String traderId, String traderCompany) {
        this.setTraderCompany(traderCompany);
        this.setTraderId(traderId);
    }

    public String getTraderId() {
        return traderId;
    }

    public void setTraderId(String traderId) {
        this.traderId = traderId;
    }

    public String getTraderCompany() {
        return traderCompany;
    }

    public void setTraderCompany(String traderCompany) {
        this.traderCompany = traderCompany;
    }

    public AsynchronousSocketChannel getConnection() {
        return connection;
    }

    public void setConnection(AsynchronousSocketChannel connection) {
        this.connection = connection;
    }

    public Set<Product> getSubscribedProducts() {
        return subscribedProducts;
    }

    public void addSubscribedProduct(Product product) {
        subscribedProducts.add(product);
    }

    public UUID getUuid() {
        return uuid;
    }
}