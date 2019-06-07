package tradergateway.gateway.GatewaySocket;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import tradergateway.gateway.Backend2UiSocket.WebSocketTest;
import tradergateway.gateway.Entity.Product;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BrokerChannel {

    private AsynchronousSocketChannel channel;
    private volatile Boolean isConnected = false;
    private String identification;//uuid
    //Pair<buy,sell>
    private Map<Product, Pair<String, String>> subscribedProducts = new ConcurrentHashMap<>();

    private ByteBuffer byteBuffer = ByteBuffer.allocate(512);
    private long retryInterval = 10;
    //TODO:注入websocket
    @Autowired
    private WebSocketTest webSocketTest;

    public BrokerChannel(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    public synchronized boolean connect() {
        ByteBuffer receive = ByteBuffer.allocate(512);
        int count = 0;
        while (!isConnected && (retryInterval *= 2) <= 10240) {
            try {
                //重试间隔
                if (count++ > 0) {
                    Thread.sleep(retryInterval);
                    System.out.printf("第%d次尝试连接\n", count);
                }
                //write companyName
                channel.write(ByteBuffer.wrap(("company:" + GatewaySocketService.traderCompanyName).getBytes())).get();
                //处理连接请求响应
                receive.clear();
                channel.read(receive).get();
                receive.flip();
                String content = Charset.forName("utf-8").decode(receive).toString();
                System.out.println("[" + channel + "]" + content);
                //TODO: 判断连接成功，接收broker身份
                if (content.indexOf("connected:") == 0) {
                    isConnected = true;
                    String uuid = content.substring("connected:".length());
                    System.err.println(uuid);
                    //注册read回调
                    channel.read(byteBuffer, byteBuffer, new TraderSocketChannelReadHandle(this));
                    System.out.println("[" + channel + "]" + " connected");
                } else {
                    System.err.println("连接失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        retryInterval = 10;
        return isConnected;
    }

    public boolean subscribeProduct(Product product) {
        try {
            this.subscribedProducts.putIfAbsent(product, new Pair<>(null, null));
            channel.write(ByteBuffer.wrap(("subscribe:" + product.getProductId()).getBytes())).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateDepth(String productId, String buyOrSell, String depth) {
        Product product = new Product(productId);
        Pair<String, String> pair = subscribedProducts.get(product);
        Pair<String, String> updatedPair;

        if (buyOrSell.equals("buy")) {
            updatedPair = new Pair<>(depth, pair.getValue());
        } else if (buyOrSell.equals("sell")) {
            updatedPair = new Pair<>(pair.getKey(), depth);
        } else {
            System.err.println("update depth error");
            updatedPair = pair;
        }
        subscribedProducts.put(product, updatedPair);
        System.out.printf("%2s.depth:%4s,%4s\n", productId, updatedPair.getKey(), updatedPair.getValue());
        //TODO: 向前端推送深度
        webSocketTest.sendMessage(product);
    }

    public Set<Product> getSubscribedProducts() {
        return subscribedProducts.keySet();
    }

    public AsynchronousSocketChannel getChannel() {
        return channel;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
