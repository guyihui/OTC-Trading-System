import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class BrokerChannel {
    private AsynchronousSocketChannel channel;
    private volatile Boolean isConnected = false;
    private long retryInterval = 10;

    private Set<Product> subscribedProducts = new CopyOnWriteArraySet<>();
    private ByteBuffer byteBuffer = ByteBuffer.allocate(512);

    public BrokerChannel(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    public boolean connect() {
        ByteBuffer receive = ByteBuffer.allocate(512);
        int count = 0;
        while (!isConnected && (retryInterval *= 2) <= 10240) {
            try {
                //TODO: 重试间隔而非超时时间
                Thread.sleep(retryInterval);
                if (count++ > 0) {
                    System.out.printf("重试第%d次\n", count);
                }
                //TODO: write company
                channel.write(ByteBuffer.wrap("company:RedPanda".getBytes())).get();
                //TODO: 处理响应
                receive.clear();
                channel.read(receive).get();
                receive.flip();
                String content = Charset.forName("utf-8").decode(receive).toString();
                System.out.println("[" + channel + "]" + content);
                //TODO: 连接成功
                if (content.length() < 9999) {
                    isConnected = true;
                    System.out.println("[" + channel + "]" + " connected");
                    //TODO: read回调
                    channel.read(byteBuffer, byteBuffer, new TraderSocketChannelReadHandle(channel));
                } else {
                    System.out.println("失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isConnected;
    }

    public boolean subscribeProduct(Product product) {
        try {
            channel.write(ByteBuffer.wrap(("subscribe:" + product.getProductId()).getBytes())).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.subscribedProducts.add(product);
        return true;
    }

    public Set<Product> getSubscribedProducts() {
        return subscribedProducts;
    }

}
