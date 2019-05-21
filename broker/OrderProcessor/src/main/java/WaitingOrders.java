
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


public class WaitingOrders {

   private  Product product;
   public static Comparator<Order> buyComparator=new Comparator<Order>() {
      //最小堆，
      public int compare(Order o1, Order o2) {

         if(o1.getPrice()>o2.getPrice()){
            return -1;
         }
         else {
            if(o1.getPrice().equals(o2.getPrice())){
               if(o1.getTime().equals(o2.getTime())){return 0;}
               return o1.getTime()>o2.getTime()?1:-1;
            }
            else {
               return 1;
            }
         }
      }
   };
   public static Comparator<Order> sellComparator=new Comparator<Order>() {
      public int compare(Order o1, Order o2) {
         if(o1.getPrice()>o2.getPrice()){
            return 1;
         }
         else {
            if(o1.getPrice().equals(o2.getPrice())){
               if(o1.getTime().equals(o2.getTime())){return 0;}
               return o1.getTime()>o2.getTime()?-1:1;
            }
            else {
               return -1;
            }
         }
      }
   };

   private PriorityBlockingQueue<Order> buyLimit;

   private PriorityBlockingQueue<Order> sellLimit;

   private LinkedBlockingQueue<Order> stop;
   private LinkedBlockingQueue<Order> cancel;

   private ConcurrentLinkedQueue<Order> buyMarket;

   private ConcurrentLinkedQueue<Order> sellMarket;
   

   public WaitingOrders(Product product){
      this.product=product;
      this.buyLimit=new PriorityBlockingQueue<Order>(10,buyComparator);
      this.sellLimit=new PriorityBlockingQueue<Order>(10,sellComparator);
      this.cancel=new LinkedBlockingQueue<Order>();
      this.stop=new LinkedBlockingQueue<Order>();
      this.buyMarket=new ConcurrentLinkedQueue<Order>();
      this.sellMarket=new ConcurrentLinkedQueue<Order>();
   }
   public boolean addBuyLimit(Order order){
      return this.buyLimit.offer(order);
   }
   public boolean addSellLimit(Order order){
      return this.sellLimit.offer(order);
   }
   public boolean addStop(Order order){
      return this.stop.offer(order);
   }
   public boolean addCancel(Order order){
      return this.cancel.offer(order);
   }
   public boolean addBuyMarket(Order order){
      return this.buyMarket.offer(order);
   }
   public boolean addSellMarket(Order order){
      return this.sellMarket.offer(order);
   }

   public Order getStop() throws InterruptedException {
      return this.stop.take();
   }
   public Order getCancel() throws InterruptedException {
      return this.cancel.take();
   }
   public Order getBuyLimit() throws InterruptedException {
      return this.buyLimit.take();
   }
   public Order getSellLimit() throws InterruptedException {
      return this.sellLimit.take();
   }
   public Order getBuyMarket(){
      return this.buyMarket.poll();
   }
   public Order getSellMarket(){
      return this.sellMarket.poll();
   }
   public static void main(String []args){
      final WaitingOrders waitingOrders=new WaitingOrders(new Product("1","1","1"));
      Order order1=new Order("1","a");
      order1.setPrice(10);
      order1.setTime(1L);
      Order order2=new Order("2","a");
      order2.setPrice(100);
      order2.setTime(2L);
      Order order3=new Order("3","a");
      order3.setPrice(100);
      order3.setTime(1L);
      waitingOrders.addBuyLimit(order1);

      waitingOrders.addBuyLimit(order2);
      waitingOrders.addBuyLimit(order3);

       class TestThread1 implements Runnable {
         public void run() {
            // test blocking
            while (true){
               Order temp= null;
               try {
                  temp = waitingOrders.getBuyLimit();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
               System.out.println("price: "+temp.getPrice());
               System.out.flush();
            }
         }
      }

      class TestThread2 implements Runnable {
         public void run() {
            // test blocking
            try {
               for(int i=1;i<50;i++){
                  Order order=new Order(""+i,"a");
                  order.setTime(i+0L);
                  order.setPrice(i+100);
                  waitingOrders.addBuyLimit(order);
                  Thread.sleep(1000);
               }

            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }

      Thread thread1 = new Thread(new TestThread1());
      thread1.start();

      Thread thread2 = new Thread(new TestThread2());
      thread2.start();

   }


}