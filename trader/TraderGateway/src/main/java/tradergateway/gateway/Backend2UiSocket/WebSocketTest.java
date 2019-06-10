package tradergateway.gateway.Backend2UiSocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tradergateway.gateway.BigOrderStorage;
import tradergateway.gateway.Entity.*;
import tradergateway.gateway.OrderStorage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/WebSocket")
@Component
public class WebSocketTest {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<Product, CopyOnWriteArraySet<WebSocketTest>> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Broker askedBroker;
    private User user;
    private Product askedProduct;
    private Session session;

    @Autowired
    private OrderStorage orderStorage;

    @Autowired
    private BigOrderStorage bigOrderStorage;

    static {
        System.out.println("WebSocket service start.");
    }

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("A client is connecting.");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSocketMap.get(askedProduct).remove(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("socket onClose error.");
        }

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        JsonParser parser = new JsonParser();
        JsonObject msgJson = parser.parse(message).getAsJsonObject();
        System.out.println("Message:" + msgJson);

        this.askedBroker = Brokers.get(msgJson.get("broker").toString().replace("\"", ""));
        this.user = new User(msgJson.get("traderName").toString().replace("\"", ""));
        this.askedProduct = Products.get(msgJson.get("productId").toString().replace("\"", ""));


        if (webSocketMap.containsKey(askedProduct)) {
            System.out.println("Add a client!");
            webSocketMap.get(askedProduct).add(this);
        } else {
            System.out.println("Add a client and a new Product set!");
            CopyOnWriteArraySet<WebSocketTest> wsSet = new CopyOnWriteArraySet<>();
            wsSet.add(this);
            webSocketMap.put(askedProduct, wsSet);
        }

        askedBroker.getBrokerChannel().updateDepth(askedProduct.getProductId(), "noUpdate", "");

    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     */
    public void sendOrderState() {
        try {
            for (Product product : webSocketMap.keySet()) {
                for (WebSocketTest socket : webSocketMap.get(product)) {
                    System.out.println("Now update order state!");

                    //需要推送的order
                    Set<Order> orders = orderStorage.getFilteredOrders(
                            socket.askedBroker,
                            socket.user,
                            socket.askedProduct
                    );

                    if (orders == null) {
                        continue;
                    }
                    //对部分order进行单独的额外推送、大单特殊处理
                    Map<String, Integer> bigOrderRemainingQuantity = new HashMap<>();
                    for (Order order : orders) {
                        if (order.getDisplayFlag() > 2) {
                            orders.remove(order);
                        }
                        if (order.getState().indexOf("canceled,remain:") == 0) {
                            sendCancelMsg(product, socket, order, "cancelSuccess");
                        }
                        if (order.getOrderType().equals("cancel") && order.getState().indexOf("fail") == 0) {
                            sendCancelMsg(product, socket, order, "cancelFailure");
                        }
                        //如果是拆出来的小单，进行统计
                        if (order.getBigOrderId() != null) {
                            if (!bigOrderRemainingQuantity.containsKey(order.getBigOrderId())) {
                                bigOrderRemainingQuantity.put(order.getBigOrderId(), 0);
                            }
                            bigOrderRemainingQuantity.put(
                                    order.getBigOrderId(),
                                    bigOrderRemainingQuantity.get(order.getBigOrderId()) + order.getRemainingQuantity()
                            );
                        }
                    }
                    //统计完拆分单信息，更新 big order
                    //对于已经结束的小单，需要从big order中移除
                    Set<BigOrder> bigOrders = bigOrderStorage.getFilteredOrders(socket.askedBroker, socket.user, socket.askedProduct);
                    for (BigOrder bigOrder : bigOrders) {
                        Integer waitingQuantity = bigOrderRemainingQuantity.get(bigOrder.getId());
                        if (waitingQuantity == null) {
                            waitingQuantity = 0;
                        }
                        bigOrder.setWaitingQuantity(waitingQuantity);
                        bigOrder.clearFinishedSplitOrders();
                    }

                    JsonObject state = new JsonObject();
                    state.addProperty("productId", product.getProductId());
                    state.addProperty("type", "state");
                    state.addProperty("orders", (new Gson()).toJson(orders));
                    synchronized (socket) {
                        socket.getSession().getBasicRemote().sendText(state.toString());
                    }

                    JsonObject bigOrderState = new JsonObject();
                    bigOrderState.addProperty("productId", product.getProductId());
                    bigOrderState.addProperty("type", "bigOrderState");
                    bigOrderState.addProperty("orders", (new Gson()).toJson(bigOrders));
                    synchronized (socket) {
                        socket.getSession().getBasicRemote().sendText(bigOrderState.toString());
                    }
//                ss.getBasicRemote().sendText(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendCancelMsg(Product product, WebSocketTest socket, Order order, String cancelSuccess) throws IOException {
        JsonObject cancel = new JsonObject();
        cancel.addProperty("productId", product.getProductId());
        cancel.addProperty("type", cancelSuccess);
        cancel.addProperty("order", (new Gson()).toJson(order));
        synchronized (socket) {
            socket.getSession().getBasicRemote().sendText(cancel.toString());
        }
    }

    public static void sendDepth(Product product, Pair<String, String> depthPair) {
        try {
            JsonObject depth = new JsonObject();
            depth.addProperty("productId", product.getProductId());
            depth.addProperty("type", "depth");
            depth.addProperty("buy", depthPair.getKey());
            depth.addProperty("sell", depthPair.getValue());

            broadcastToUi(product, depth.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void broadcastToUi(Product product, String s) throws IOException {
        if (webSocketMap.containsKey(product)) {
            CopyOnWriteArraySet<WebSocketTest> ws = webSocketMap.get(product);
            for (WebSocketTest socket : ws) {
                System.out.println("Now send a message!");
                synchronized (socket) {
                    socket.getSession().getBasicRemote().sendText(s);
                }
//                ss.getBasicRemote().sendText(result);
            }
        }
    }

    public Session getSession() {
        return this.session;
    }

    public User getUser() {
        return this.user;
    }

    public Product getAskedProduct() {
        return askedProduct;
    }

    public Broker getAskedBroker() {
        return askedBroker;
    }

    public static ConcurrentHashMap<Product, CopyOnWriteArraySet<WebSocketTest>> getWebSocketMap() {
        return webSocketMap;
    }

}
