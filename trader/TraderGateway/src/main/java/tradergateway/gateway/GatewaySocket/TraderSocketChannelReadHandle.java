package tradergateway.gateway.GatewaySocket;

import javafx.util.Pair;
import tradergateway.gateway.Entity.BrokerChannel;
import tradergateway.gateway.Entity.Products;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class TraderSocketChannelReadHandle implements CompletionHandler<Integer, ByteBuffer> {

    private final BrokerChannel brokerChannel;
    private final AsynchronousSocketChannel channel;
    private final CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

    public TraderSocketChannelReadHandle(BrokerChannel brokerChannel) {
        this.brokerChannel = brokerChannel;
        channel = brokerChannel.getChannel();
    }

    @Override
    public void completed(Integer result_num, ByteBuffer attachment) {
        attachment.flip();
        CharBuffer charBuffer = CharBuffer.allocate(512);
        decoder.decode(attachment, charBuffer, false);
        charBuffer.flip();
        attachment.clear();
        channel.read(attachment, attachment, this);
        String data = new String(charBuffer.array(), 0, charBuffer.limit());
        //TODO: 解析
        String[] list = data.split(":");
        if (list.length == 4 && list[0].equals("depth")) {
            String productId = list[1];
            String buyOrSell = list[2];
            String depth = list[3];
            brokerChannel.updateDepth(productId, buyOrSell, depth);
        } else if (list.length == 3 && list[0].equals("subscribe")) {
            String productId = list[1];
            String result = list[2];
            if (result.equals("success")) {
                brokerChannel.getSubscribedProducts().putIfAbsent(Products.get(productId), new Pair<>(null, null));
                System.out.printf("订阅_%s_成功\n", productId);
            } else {
                System.err.printf("订阅_%s_失败:%s\n", productId, result);
            }
        } else {
            System.err.println("服务器信息：" + data);
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("receive read error");
    }
}
