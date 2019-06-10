import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class BrokerMain {
    private static ConcurrentHashMap<Product,Orderbook> orderBookMap = new ConcurrentHashMap<Product, Orderbook>();

    public static void main(String[] args) {
        Product gold = new Product("01","gold","201907");
        Orderbook goldOrderbook = new Orderbook(gold);
        Product oil = new Product("02","oil","201908");
        Orderbook oilOrderbook = new Orderbook(oil);
        orderBookMap.putIfAbsent(gold,goldOrderbook);
        orderBookMap.putIfAbsent(oil,oilOrderbook);
        //可以开始添加订单了

//        Orderbook orderbook1=orderBookMap.get(new Product("01","gold","201907"));
//        Orderbook orderbook2=orderBookMap.get(new Product("02","oil","201908"));
//        if(orderbook1==null){
//            System.out.println("orderbook1 null");
//            System.out.flush();
//        }
//        if(orderbook2==null){
//            System.out.println("orderbook2 null");
//            System.out.flush();
//        }
        Thread thread1=new Thread(new Test_thread1());
        Thread thread2=new Thread(new test_thread2());
        thread1.start();
        thread2.start();
    }

    static class Test_thread1 implements Runnable{
        public void run() {
            int id=0;
            while(true){
                Trader trader=new Trader("1","CorpA");
                Orderbook orderbook=orderBookMap.get(new Product("01","gold","201907"));
                id++;
                Random random = new Random();
                int flag = random.nextInt(2);
                int price = 1000 + random.nextInt(1000);
                String sellorbuy = (flag == 0) ? "buy" : "sell";
                Order newOrder = new Order("limit" + id, "limit", price, sellorbuy);
                newOrder.setRemainingQuantity(10 + random.nextInt(10));
                newOrder.setTime(System.currentTimeMillis());
                newOrder.setTraderName("AWSL");
                newOrder.setTrader(trader);

                if (sellorbuy .equals("buy")) {
                    orderbook.addWOBuyLimit(newOrder);
                } else {
                    orderbook.addWOSellLimit(newOrder);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class test_thread2 implements Runnable{
        public void run() {
            int id=0;
            while(true){
                Trader trader=new Trader("2","CorpB");
                Orderbook orderbook=orderBookMap.get(new Product("02","oil","201908"));
                id++;
                Random random = new Random();
                int flag = random.nextInt(2);
                int price = 1000 + random.nextInt(1000);
                String sellorbuy = (flag == 0) ? "buy" : "sell";
                Order newOrder = new Order("limit" + id, "limit", price, sellorbuy);
                newOrder.setRemainingQuantity(10 + random.nextInt(10));
                newOrder.setTime(System.currentTimeMillis());
                newOrder.setTraderName("AWSL");
                newOrder.setTrader(trader);

                if (sellorbuy .equals("buy")) {
                    orderbook.addWOBuyLimit(newOrder);
                } else {
                    orderbook.addWOSellLimit(newOrder);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
