import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CopyOnWriteArraySet;

public class Orderbook {
    private CopyOnWriteArraySet<AsynchronousSocketChannel> connections = new CopyOnWriteArraySet<>();

    public void bindConnection(AsynchronousSocketChannel client) {
        this.connections.add(client);
    }

    public void removeConnection(AsynchronousSocketChannel client) {
        this.connections.remove(client);
    }

    public void broadcast(String message) {
        for (AsynchronousSocketChannel client : connections) {
            client.write(ByteBuffer.wrap(message.getBytes()));
        }
    }
}
