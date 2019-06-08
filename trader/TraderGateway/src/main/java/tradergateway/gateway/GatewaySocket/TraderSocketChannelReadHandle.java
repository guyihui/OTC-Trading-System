package tradergateway.gateway.GatewaySocket;

import tradergateway.gateway.Entity.BrokerChannel;

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
            System.err.println("服务器信息：" + data);
        } else {
            //System.err.println("服务器信息：" + data);
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("receive read error");
    }
}
