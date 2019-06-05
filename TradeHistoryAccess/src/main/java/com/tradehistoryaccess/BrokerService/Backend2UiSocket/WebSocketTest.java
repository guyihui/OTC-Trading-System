package com.tradehistoryaccess.BrokerService.Backend2UiSocket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tradehistoryaccess.BrokerService.OrderBook.PriceNodeList;
import com.tradehistoryaccess.BrokerService.OrderBook.Product;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint("/WebSocket")
@Component
public class WebSocketTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<Product,CopyOnWriteArraySet<WebSocketTest>> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    static {System.out.println("WebSocket service start.");}

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
//        webSocketSet.add(this);     //加入set中
        System.out.println("A client is connecting.");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        for (Product product : webSocketMap.keySet()) {
            if(webSocketMap.get(product).remove(this)){
                break;
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        Product askedProduct = new Gson().fromJson(message,Product.class);
        CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<>();

        if(webSocketMap.containsKey(askedProduct)){
            System.out.println("Add a client!");
            webSocketSet = webSocketMap.get(askedProduct);
            webSocketSet.add(this);
            webSocketMap.put(askedProduct,webSocketSet);
        }
        else{
            System.out.println("Add a client and a new Product set!");
            CopyOnWriteArraySet<WebSocketTest> wsSet = new CopyOnWriteArraySet<>();
            wsSet.add(this);
            webSocketMap.put(askedProduct,wsSet);
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @throws IOException
     */
    public static void sendMessage(PriceNodeList buyList,PriceNodeList sellList,Product product) throws IOException{
        JsonObject orderBook = new JsonObject();
        JsonArray sell = sellList.orderBooktoString();
        JsonArray buy = buyList.orderBooktoString();
        orderBook.add("sellList",sell);
        orderBook.add("buyList",buy);
        String result = orderBook.toString();
        if(webSocketMap.containsKey(product)){
            CopyOnWriteArraySet<WebSocketTest> ws = webSocketMap.get(product);
            for(WebSocketTest socket:ws){
                System.out.println("Now send a message!");
                System.out.println(socket);
                System.out.println(socket.getSession());
                Session ss = socket.getSession();
                System.out.println(ss);
                ss.getAsyncRemote().sendText(result);
            }
        }

    }

    public Session getSession(){
        return this.session;
    }

//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        WebSocketTest.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebSocketTest.onlineCount--;
//    }
}
