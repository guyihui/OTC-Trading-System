import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Broker {

    private static final String name = "RedPanda-broker-prototype";
    private static final Object waitObject = new Object();
    private static final Map<Product, Orderbook> products = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        initializeOrderBookMap();

        startupGateway();

        block();
    }

    private static void initializeOrderBookMap() {
        products.put(new Product("test"), new Orderbook());
    }

    private static void startupGateway() {
        try {
            /*
             * 1、Executors是线程池生成工具，可以生成“固定大小的线程池”、“调度池”、“可伸缩线程数量的池”。
             * 2、也可以通过ThreadPoolExecutor直接生成池。
             * 3、这个线程池是用来得到操作系统的“IO事件通知”的，不是用来进行“得到IO数据后的业务处理的”。
             *    要进行后者的操作，您可以再使用一个池（最好不要混用）
             * 4、（不推荐）如果不使用线程池，直接 AsynchronousServerSocketChannel.open()。
             */
            ExecutorService threadPool = Executors.newFixedThreadPool(20);
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);

            //设置要监听的端口“0.0.0.0”代表本机所有IP设备
            final AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel
                    .open(group)
                    .bind(new InetSocketAddress("0.0.0.0", 8888));

            //为AsynchronousServerSocketChannel注册监听
            channel.accept(null, new ServerSocketChannelAcceptHandle(channel, products));

        } catch (Exception e) {
            e.printStackTrace();
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
