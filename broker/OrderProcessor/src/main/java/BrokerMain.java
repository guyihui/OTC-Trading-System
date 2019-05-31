import java.util.concurrent.ConcurrentHashMap;

public class BrokerMain {
    private static ConcurrentHashMap<Product,Orderbook> orderBookMap = new ConcurrentHashMap<Product, Orderbook>();

    public static void main(String[] args) {
        Product gold = new Product("1","gold","201907");
        Orderbook goldOrderbook = new Orderbook(gold);
        Product oil = new Product("2","oil","201908");
        Orderbook oilOrderbook = new Orderbook(oil);
        orderBookMap.putIfAbsent(gold,goldOrderbook);
        orderBookMap.putIfAbsent(oil,oilOrderbook);
        //可以开始添加订单了
    }
}
