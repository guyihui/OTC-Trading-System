import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class TraderSocketChannelReadHandle implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel server;

    public TraderSocketChannelReadHandle(AsynchronousSocketChannel serverChannel) {
        this.server = serverChannel;
    }

    @Override
    public void completed(Integer result_num, ByteBuffer attachment) {
        attachment.flip();
        CharBuffer charBuffer = CharBuffer.allocate(512);
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        decoder.decode(attachment, charBuffer, false);
        charBuffer.flip();
        attachment.clear();
        server.read(attachment, attachment, this);
        String data = new String(charBuffer.array(), 0, charBuffer.limit());
        System.out.println("服务器信息：" + data);

        //TODO: 解析
        if (data.length() < 9999) {

        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("receive read error");
    }
}
