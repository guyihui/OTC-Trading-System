package com.tradehistoryaccess.BrokerService.GatewaySocket;

import com.tradehistoryaccess.BrokerService.OrderBook.Orderbook;
import com.tradehistoryaccess.BrokerService.OrderBook.Product;
import com.tradehistoryaccess.Entity.Trader;
import com.tradehistoryaccess.Entity.TraderManage;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;

/**
 * 这个处理器类，专门用来响应 ServerSocketChannel 的事件。
 * ServerSocketChannel只有一种事件：接受客户端的连接
 */
public class ServerSocketChannelAcceptHandle implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private AsynchronousServerSocketChannel serverSocketChannel;
    private Map<Product, Orderbook> products;
    private TraderManage traderManage;

    public ServerSocketChannelAcceptHandle(AsynchronousServerSocketChannel serverSocketChannel,
                                           Map<Product, Orderbook> products,
                                           TraderManage traderManage) {
        this.serverSocketChannel = serverSocketChannel;
        this.products = products;
        this.traderManage = traderManage;
    }

    @Override
    public void completed(final AsynchronousSocketChannel client, Void attachment) {
        //每次都要重新注册监听（一次注册，一次响应），但是由于“文件状态标示符”是独享的，所以不需要担心有“漏掉的”事件
        serverSocketChannel.accept(null, this);

        //为这个新的socketChannel注册“read”事件，以便操作系统在收到数据并准备好后，主动通知应用程序
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        Trader connectedTrader = traderManage.buildTraderInstance(client);
        client.read(buffer, buffer, new SocketChannelReadHandle(connectedTrader, buffer, this.products));

    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        System.out.println("accept error");
    }
}
