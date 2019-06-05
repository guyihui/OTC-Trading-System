package com.tradehistoryaccess.BrokerService.History;


import com.tradehistoryaccess.Entity.DoneOrderRaw;
import com.tradehistoryaccess.Entity.Tradehistory;
import com.tradehistoryaccess.Util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.concurrent.LinkedBlockingQueue;


public class AddHistory {
    private LinkedBlockingQueue<DoneOrderRaw> doneOrders;
    private IdGenerator idGenerator;

    public AddHistory() {
        this.idGenerator = new IdGenerator();
        this.doneOrders=new LinkedBlockingQueue<DoneOrderRaw>();
    }

    public void add_order(DoneOrderRaw orderRaw){
        doneOrders.offer(orderRaw);
    }
    public void add_DB() throws InterruptedException {
        DoneOrderRaw orderRaw = doneOrders.take();
        System.out.println("order waiting to insert DB num: "+doneOrders.size());
        System.out.flush();
        Session s = HibernateUtils.getCurrentSession();
        //   Product
        Transaction t = s.beginTransaction();
        String id = idGenerator.getId(orderRaw.getProductid());
        Tradehistory u = orderRaw.createHistory(id);
        s.save(u);
        t.commit();

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
