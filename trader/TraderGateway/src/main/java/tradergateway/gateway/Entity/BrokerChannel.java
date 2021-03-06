package tradergateway.gateway.Entity;

import javafx.util.Pair;
import tradergateway.gateway.Backend2UiSocket.WebSocketTest;
import tradergateway.gateway.GatewaySocket.TraderSocketChannelReadHandle;
import tradergateway.gateway.GatewaySocketService;

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
    //TODO:注入websocket,暂不注入（使用静态方法）

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
                    identification = uuid;
                    System.out.println("id code:" + uuid);
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
            //发送订阅请求，不会直接成功
            channel.write(ByteBuffer.wrap(("subscribe:" + product.getProductId()).getBytes())).get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateDepth(String productId, String buyOrSell, String depth) {
        Product product = new Product(productId);
        Pair<String, String> pair = subscribedProducts.get(product);
        if (pair == null) {
            //TODO:可以向前端推送未订阅的信息
            return;
        }
        Pair<String, String> updatedPair;

        switch (buyOrSell) {
            case "buy":
                updatedPair = new Pair<>(depth, pair.getValue());
                break;
            case "sell":
                updatedPair = new Pair<>(pair.getKey(), depth);
                break;
            case "noUpdate":
                updatedPair = pair;
                break;
            default:
                System.err.println("update depth error");
                updatedPair = pair;
        }
        subscribedProducts.put(product, updatedPair);
//        System.out.printf("%2s.depth:%4s,%4s\n", productId, updatedPair.getKey(), updatedPair.getValue());
        //TODO: 向前端推送深度
        WebSocketTest.sendDepth(product, updatedPair);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public Map<Product, Pair<String, String>> getSubscribedProducts() {
        return subscribedProducts;
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
