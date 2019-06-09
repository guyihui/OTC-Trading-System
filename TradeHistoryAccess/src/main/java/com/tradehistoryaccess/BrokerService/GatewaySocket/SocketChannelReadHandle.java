package com.tradehistoryaccess.BrokerService.GatewaySocket;

import com.tradehistoryaccess.BrokerService.OrderBook.Orderbook;
import com.tradehistoryaccess.BrokerService.OrderBook.Product;
import com.tradehistoryaccess.Entity.Trader;

import java.io.IOException;
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
 * 都是一个对象引用（但这没关系，因为您不会直接操作这个AsynchronousChannelProvider对象）。
 */
public class SocketChannelReadHandle implements CompletionHandler<Integer, ByteBuffer> {

    /**
     * 专门用于进行这个通道数据缓存操作的ByteBuffer<br>
     * 也可以作为CompletionHandler的attachment形式传入。<br>
     */
//    private ByteBuffer byteBuffer;
    private Trader trader;
    private AsynchronousSocketChannel client;
    private Map<Product, Orderbook> products;
    private volatile Boolean isConnected = false;

    public SocketChannelReadHandle(Trader traderWithSocketChannel,
                                   ByteBuffer byteBuffer,
                                   Map<Product, Orderbook> products) {
        this.trader = traderWithSocketChannel;
        this.client = traderWithSocketChannel.getConnection();
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
//        System.out.println("[" + client + "]" + data);

        //TODO: 根据不同消息做相应处理
        //第一次接收，初始化 trader company 信息。
        if (data.indexOf("company:") == 0) {
            if (!this.isConnected) {
                this.trader.setTraderId(data.substring("company:".length()));
                this.trader.setTraderCompany(data.substring("company:".length()));
                this.isConnected = true;
                System.out.println(client + data);
                client.write(ByteBuffer.wrap(("connected:" + trader.getUuid().toString()).getBytes()));
            } else {
                System.out.println("Already connected.");
                client.write(ByteBuffer.wrap("Already connected.".getBytes()));
            }
        } else if (this.isConnected) {
            //TODO: switch by request type
            //订阅 product
            if (data.indexOf("subscribe:") == 0) {
                Product product = new Product(data.substring("subscribe:".length()));
                Orderbook orderbook = this.products.get(product);
                if (orderbook != null) {
                    orderbook.bindConnection(this.trader);
                    //TODO: give response
                } else {
                    System.err.println("subscribe error " + data);
                }
            } else {
                System.err.println(data);
//                this.failed(null, null);
            }
        } else {
            System.out.println(data);
            client.write(ByteBuffer.wrap("Please tell me your company first. e.g. company:xxx".getBytes()));
        }

    }

    @Override
    public void failed(Throwable exc, ByteBuffer historyContext) {
        if (exc != null) {
            System.err.println(exc.toString());
        }
        try {
            for (Product product : this.products.keySet()) {
                this.products.get(product).removeConnection(this.trader);
            }
            this.client.close();
            System.err.println(client + " closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}