import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TraderGateway {

    private static final Object waitObject = new Object();
    private static Map<Product, ProductChannels> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        //init map entry
        Product product1 = new Product("test1");
        connectionMap.putIfAbsent(product1, new ProductChannels(product1));
        Product product2 = new Product("test2");
        connectionMap.putIfAbsent(product2, new ProductChannels(product2));

        BrokerChannel brokerChannel = getBrokerChannel(new InetSocketAddress("127.0.0.1", 8888));
        if (brokerChannel != null) {//socket已连接，业务逻辑未连接
            if (brokerChannel.connect()) {//阻塞
                System.out.println("connected");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("subscribe product1:" + product1.getProductId());
                if (brokerChannel.subscribeProduct(product1)) {
                    connectionMap.get(product1).addBrokerChannel(brokerChannel);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("subscribe product2:" + product2.getProductId());
                if (brokerChannel.subscribeProduct(product2)) {
                    connectionMap.get(product2).addBrokerChannel(brokerChannel);
                }
            }
        }

//        BrokerChannel brokerChannel2 = getBrokerChannel(new InetSocketAddress("127.0.0.1", 8888));
//        if (brokerChannel2 != null) {//socket已连接，业务逻辑未连接
//            if (brokerChannel2.connect()) {//阻塞
//                if (brokerChannel2.subscribeProduct(product1)) {
//                    connectionMap.get(product1).addBrokerChannel(brokerChannel2);
//                }
//            }
//        }

        for (Product product : brokerChannel.getSubscribedProducts()) {
            System.out.println(product.getProductId());
        }
        for (Product product : connectionMap.keySet()) {
            System.out.println(product.getProductId());
            for (BrokerChannel channel : connectionMap.get(product).getBrokerChannels()) {
                System.out.println(channel);
            }
        }
        block();
    }

    private static BrokerChannel getBrokerChannel(InetSocketAddress brokerAddress) {
        try {
            AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
            channel.connect(brokerAddress).get();
            return new BrokerChannel(channel);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void block() {
        synchronized (waitObject) {
            try {
                waitObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


/*

            //TODO: company & target product
            Future<Integer> future = channel.write(ByteBuffer.wrap("中文,你好2".getBytes()));
            future.get();
            System.out.println("send ok");

            //注册 read 回调
            ByteBuffer buffer = ByteBuffer.allocate(512);
            channel.read(buffer, buffer, new TraderSocketChannelReadHandle(channel));

            int count = 0;
            while (count < 10000) {
                Thread.sleep(1000);
                channel.write(ByteBuffer.wrap(("nhs" + count++).getBytes())).get();
            }

 */