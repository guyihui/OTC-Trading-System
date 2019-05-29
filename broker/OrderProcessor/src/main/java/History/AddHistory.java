package History;

import java.sql.*;

import Entity.DoneOrderRaw;
import Entity.Tradehistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import util.HibernateUtils;

import java.util.concurrent.LinkedBlockingQueue;


public class AddHistory {
    private static LinkedBlockingQueue<DoneOrderRaw> testOrders;
    private static AddHistory addH;// for test
    private static LinkedBlockingQueue<DoneOrderRaw> testOrders2;
    private static AddHistory addH2;// for test
    private static LinkedBlockingQueue<DoneOrderRaw> testOrders3;
    private static AddHistory addH3;// for test

    private LinkedBlockingQueue<DoneOrderRaw> doneOrders;
    private IdGenerator idGenerator;

    public AddHistory(LinkedBlockingQueue<DoneOrderRaw> doneOrderRaws) {
        this.doneOrders = doneOrderRaws;
        this.idGenerator = new IdGenerator();
    }

    public void add() throws InterruptedException {
        DoneOrderRaw orderRaw = doneOrders.take();
        Session s = HibernateUtils.getCurrentSession();
        //   Product
        Transaction t = s.beginTransaction();
        String id = idGenerator.getId(orderRaw.getProductid());
        Tradehistory u = orderRaw.createHistory(id);
        s.save(u);
        t.commit();
        System.out.println("add complete");
        System.out.flush();
    }




    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
        testOrders=new LinkedBlockingQueue<DoneOrderRaw>();
        testOrders2=new LinkedBlockingQueue<DoneOrderRaw>();
        testOrders3=new LinkedBlockingQueue<DoneOrderRaw>();

        DoneOrderRaw doneOrderRaw1=new DoneOrderRaw("1","01","june1",10,10,"A","corpA","buy","B","corpB","sell","123123213");


        addH = new AddHistory(testOrders);
        addH2=new AddHistory(testOrders2);
        addH3=new AddHistory(testOrders3);
        class th_test implements Runnable{

            public void run() {
                while(true){
                    try {
                        addH.add();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class th_test2 implements Runnable{

            public void run() {
                while (true){
                    testOrders.offer(new DoneOrderRaw("1","01","june1",10,10,"A","corpA","buy","B","corpB","sell","123123213"));
                    System.out.println("waiting order num: "+testOrders.size());
                    System.out.flush();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        class th_test3 implements Runnable{

            public void run() {
                while(true){
                    try {
                        addH2.add();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class th_test4 implements Runnable{

            public void run() {
                while (true){
                    testOrders2.offer(new DoneOrderRaw("1","02","june1",10,10,"A","corpA","buy","B","corpB","sell","123123213"));
                    System.out.println("waiting order2 num: "+testOrders2.size());
                    System.out.flush();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class th_test5 implements Runnable{

            public void run() {
                while(true){
                    try {
                        addH3.add();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class th_test6 implements Runnable{

            public void run() {
                while (true){
                    testOrders3.offer(new DoneOrderRaw("1","03","june1",10,10,"A","corpA","buy","B","corpB","sell","123123213"));
                    System.out.println("waiting order3 num: "+testOrders3.size());
                    System.out.flush();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Thread thread=new Thread(new th_test());
        Thread thread2=new Thread(new th_test2());
        Thread thread3=new Thread(new th_test3());
        Thread thread4=new Thread(new th_test4());
        Thread thread5=new Thread(new th_test5());
        Thread thread6=new Thread(new th_test6());
        thread.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread2.start();


    }

}

//jdbc test
//    public  void  add_jdbc() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");//加载驱动
//
//        String jdbc="jdbc:mysql://localhost:3306/otc";
//        Connection conn=DriverManager.getConnection(jdbc, "root", "123456");//链接到数据库
//        long time=System.nanoTime();
//        for (int i=0;i<100;i++) {
//            String id = idGenerator.getId("02");
//            String sql = "insert into tradehistory values (?,'2','3','4','5','6','7','8','9','10','11','12')";   //SQL语句
//            PreparedStatement pts = conn.prepareStatement(sql);
//            pts.setString(1, id);
//            pts.executeUpdate();
//        }
//        conn.close();
//        System.out.println(System.nanoTime()-time+" passed");
//    }
