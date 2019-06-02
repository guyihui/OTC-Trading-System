import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Map;

/**
 * 负责对每一个socketChannel的数据获取事件进行监听。<p>
 * <p>
 * 重要的说明：一个socketchannel都会有一个独立工作的SocketChannelReadHandle对象（CompletionHandler接口的实现），
 * 其中又都将独享一个“文件状态标示”对象FileDescriptor、
 * 一个独立的由程序员定义的Buffer缓存（这里我们使用的是ByteBuffer）、
 * 所以不用担心在服务器端会出现“窜对象”这种情况，因为JAVA AIO框架已经帮您组织好了。<p>
 * <p>
 * 但是最重要的，用于生成channel的对象：AsynchronousChannelProvider是单例模式，无论在哪组socketchannel，
 * 对是一个对象引用（但这没关系，因为您不会直接操作这个AsynchronousChannelProvider对象）。
 *
 * @author keep_trying
 */
public class SocketChannelReadHandle implements CompletionHandler<Integer, ByteBuffer> {

    /**
     * 专门用于进行这个通道数据缓存操作的ByteBuffer<br>
     * 当然，您也可以作为CompletionHandler的attachment形式传入。<br>
     * 这是，在这段示例代码中，attachment被我们用来记录所有传送过来的Stringbuffer了。
     */
//    private ByteBuffer byteBuffer;
    private AsynchronousSocketChannel client;
    private Map<Product, Orderbook> products;
    private volatile Boolean isConnected = false;

    public SocketChannelReadHandle(AsynchronousSocketChannel socketChannel,
                                   ByteBuffer byteBuffer,
                                   Map<Product, Orderbook> products) {
        this.client = socketChannel;
//        this.byteBuffer = byteBuffer;
        this.products = products;
    }

    @Override
    public void completed(Integer result_num, ByteBuffer attachment) {
        attachment.flip();
        CharBuffer charBuffer = CharBuffer.allocate(2048);
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        decoder.decode(attachment, charBuffer, false);
        charBuffer.flip();
        attachment.clear();
        client.read(attachment, attachment, this);
        String data = new String(charBuffer.array(), 0, charBuffer.limit());

        //TODO: 根据不同消息做相应处理
        //第一次接收，初始化 trader company 信息，若失败则直接关闭连接。
        if (data.length() < 9999) {
            Product product = new Product("test");
            if (!this.isConnected) {
                this.products.get(product).bindConnection(this.client);
                this.isConnected = true;
            } else {
                this.products.get(product).broadcast("you say:" + data);
            }
            System.out.println("read data:" + data);
        } else {
            this.failed(null, null);
        }

//        //TODO: 以下作废
//        //连接成功，等待 trader 发送进一步请求
//        //单独开辟线程用以业务处理，目前为阻塞。
//        try {
//            ByteBuffer receive = ByteBuffer.allocate(1024);
//            while (true) {
//                receive.clear();
//                client.read(receive).get();
//                receive.flip();
//                String content = Charset.forName("utf-8").decode(receive).toString();
//                // 解析请求并作出响应
//                System.out.println("receive：" + content);
//                //得到订阅的 Product
////                Orderbook orderbook = products.get(new Product());
////                try {
////                    int count = 0;
////                    while (count <= 10000) {
////                        Thread.sleep(1000);
////                        client.write(ByteBuffer.wrap(("message " + count++).getBytes()));
////                    }
////                    client.close();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            }
//        } catch (Exception e) {
//            this.failed(e, null);
//        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer historyContext) {
        if (exc != null) {
            System.err.println(exc.toString());
        }
        try {
            this.client.close();
            System.err.println(client + " closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}