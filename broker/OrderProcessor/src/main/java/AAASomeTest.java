import static java.lang.Thread.sleep;

public class AAASomeTest {


    private static OrderNodeList list = new OrderNodeList();
    private static PriceNodeList priceList = new PriceNodeList();
    private static Order order0 = new Order("test0", "limit",1000,"buy");
    private static Order order1 = new Order("test1", "limit",1000,"buy");
    private static Order order2 = new Order("test2", "limit",1000,"buy");
    private static Order order3 = new Order("test3", "limit",1000,"buy");
    private static Order order4 = new Order("test4", "limit",1000,"buy");
    private static Order order5 = new Order("test5", "limit",1000,"buy");
    private static Order order6 = new Order("test6", "limit",1000,"buy");
    private static Order order7 = new Order("test7", "limit",1050,"buy");
    private static Order order8 = new Order("test8", "limit",1050,"buy");
    private static Order order9 = new Order("test9", "limit",1050,"buy");
    private static Order order10 = new Order("test10", "limit",1050,"buy");
    private static Order order11 = new Order("test11", "limit",1060,"buy");
    private static Order order12 = new Order("test12", "limit",1060,"buy");
    private static Order order13 = new Order("test13", "limit",1055,"buy");
    private static Order order14 = new Order("test14", "limit",1060,"buy");

    public static void main(String[] args) {
    //    singleThreadPriceNodeTest();

////        for (int i = 0; i < 10; i++) {
////            list.add(new Order("test" + i, "limit"));
////        }
//        Thread thread1 = new Thread(new TestThread1());
//        thread1.start();
//        Thread thread2 = new Thread(new TestThread2());
//        thread2.start();
//        for (int i = 0; i < 20; i++) {
////            System.out.println("insert" + i);
//            list.add(new Order("insert" + i, "limit"));
//        }
//        try {
//            thread1.join();
//            thread2.join();
//            System.out.println(list);
//            System.out.println(list.activateStop());
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }

//        Order order1 = new Order("test1", "limit",1040,"sell");
//        Order order2 = new Order("test2", "limit",1061,"sell");
//        Order order3 = new Order("test3", "limit",1030,"sell");
//        Order order4 = new Order("test4", "limit",1030,"sell");
//        Order order5 = new Order("test5", "limit",1035,"sell");
//        Order order6 = new Order("test1", "limit",1040,"sell");
//        Order order7 = new Order("test2", "limit",1061,"sell");
//        Order order8 = new Order("test3", "limit",1030,"sell");
//        Order order9 = new Order("test4", "limit",1030,"sell");
//        Order order10 = new Order("test5", "limit",1035,"sell");
        priceList.addOrder(order0);
        priceList.addOrder(order1);
        priceList.addOrder(order2);
        priceList.addOrder(order3);
        priceList.addOrder(order4);
        priceList.addOrder(order5);
        priceList.addOrder(order6);
        priceList.addOrder(order7);
        priceList.addOrder(order8);
        priceList.addOrder(order9);
        priceList.addOrder(order10);
//        try {
//            sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Thread thread1 = new Thread(new TestPriceNodeThread1());
        thread1.start();
        Thread thread2 = new Thread(new TestPriceNodeThread2());
        thread2.start();
        try {
            thread1.join();
            thread2.join();
            System.out.println(priceList.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class TestThread1 implements Runnable {
        public void run() {
            for (int j = 0; j < 10; j++) {
                OrderNodeList shortList = new OrderNodeList();
                for (int i = 0; i < 3; i++) {
                    shortList.add(new Order("thread1-" + j + "-" + i, "limit"));
                }
                list.concat(shortList);
            }
        }
    }

    static class TestThread2 implements Runnable {
        public void run() {
            for (int i = 0; i < 30; i++) {
                list.add(new Order("test" + i, "limit"));
            }
        }
    }

    static class dealThread implements Runnable{
        public void run() {

        }


    }

    static class TestPriceNodeThread1 implements Runnable {
        public void run() {
//            System.out.println("11111111111111111111start");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.addOrder(order11);
//            System.out.println("11111111111111111111");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.addOrder(order12);
//            System.out.println("11111111111111111111");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.addOrder(order13);
//            System.out.println("11111111111111111111");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.addOrder(order14);
//            System.out.println("11111111111111111111");
//            System.out.println(priceList.toString());
//            System.out.flush();
           // priceList.removeOrder(order4);
//            System.out.println("11111111111111111111");
//            System.out.println(priceList.toString());
//            System.out.flush();
          //  priceList.removeOrder(order5);
//            System.out.println("11111111111111111111");
//            System.out.println(priceList.toString());
//            System.out.flush();
          //  priceList.removeOrder(order6);
//            System.out.println("11111111111111111111");
//            System.out.println(priceList.toString());
//            System.out.flush();
//            Order candi = priceList.candidateOrder();
//            while(candi != null) {
//                priceList.removeOrder(candi);
//                candi.unlock();
//                candi = priceList.candidateOrder();
//            }
//            candi.unlock();
        }
    }

    static class TestPriceNodeThread2 implements Runnable {
        public void run() {
//            System.out.println("22222222222222222222start");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.removeOrder(order0);
            priceList.removeOrder(order1);
//            System.out.println("22222222222222222222");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.removeOrder(order2);
//            System.out.println("22222222222222222222");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.removeOrder(order3);
//            System.out.println("22222222222222222222");
//            System.out.println(priceList.toString());
//            System.out.flush();
            priceList.removeOrder(order4);
            priceList.removeOrder(order5);
            priceList.removeOrder(order6);
            priceList.removeOrder(order7);
            priceList.removeOrder(order8);
            priceList.removeOrder(order9);
            priceList.removeOrder(order10);
            priceList.removeOrder(order11);

            Order canceledOrder = new Order("cancel1","limit",1060,"sell");
            canceledOrder.setCancelId("test12");
            priceList.cancelOrder(canceledOrder);

            priceList.removeOrder(order13);
            priceList.removeOrder(order14);
//            System.out.println("22222222222222222222");
//            System.out.println(priceList.toString());
//            System.out.flush();
        }
    }

    private static void singleThreadTest() {
        String divider = "=========================";

        Order order1 = new Order("test1", "limit");
        Order order2 = new Order("test2", "limit");
        Order order3 = new Order("test3", "limit");
        Order order4 = new Order("test4", "limit");
        Order order5 = new Order("test5", "limit");

        System.out.println(divider);
        System.out.println("isEmpty: " + list.isEmpty());
        list.add(order1);
        list.add(order2);
        list.add(order3);
        System.out.println("isEmpty: " + list.isEmpty());
        System.out.println(list.toString());
        System.out.println("Candidate:" + list.candidateOrder());
        list.add(order4);
        list.add(order5);
        list.removeOrder(order1);
        Order cancel = new Order("cancel_test", "cancel");
        cancel.setCancelId("test5");
        list.cancelOrder(cancel);
        System.out.println(list.toString());

        OrderNodeList anotherList = new OrderNodeList();
        anotherList.add(new Order("test6", "limit"));
        anotherList.add(new Order("test7", "limit"));
        anotherList.add(new Order("test8", "limit"));
        list.concat(anotherList);
        System.out.println(list);

    }

    private static void singleThreadPriceNodeTest(){
        String divider = "=========================";

        Order order1 = new Order("test1", "limit",1040,"sell");
        Order order2 = new Order("test2", "limit",1061,"sell");
        Order order3 = new Order("test3", "limit",1030,"sell");
        Order order4 = new Order("test4", "limit",1030,"sell");
        Order order5 = new Order("test5", "limit",1035,"sell");

        System.out.println(divider);
        //System.out.println("isEmpty: " + list.isEmpty());
        priceList.addOrder(order1);
        System.out.println(priceList.toString());
        priceList.addOrder(order2);
        System.out.println(priceList.toString());
        priceList.addOrder(order3);
//        System.out.println("isEmpty: " + list.isEmpty());
//        System.out.println(list.toString());
//        System.out.println("Candidate:" + list.candidateOrder());
        System.out.println(priceList.toString());

        priceList.addOrder(order4);
        System.out.println(priceList.toString());
        priceList.addOrder(order5);
        System.out.println(priceList.toString());
        System.out.println("Candidate: "+priceList.candidateOrder().getOrderId());
        priceList.removeOrder(order5);
        System.out.println(priceList.toString());
        Order cancel = new Order("cancel_test", "cancel");
        cancel.setCancelId("test2");
        cancel.setPrice(1061);
        priceList.cancelOrder(cancel);
        System.out.println(priceList.toString());
        priceList.removeOrder(order3);
        System.out.println(priceList.toString());
        priceList.removeOrder(order1);
        System.out.println(priceList.toString());
        priceList.removeOrder(order1);
        System.out.println(priceList.toString());
        priceList.removeOrder(order4);
        System.out.println(priceList.toString());
        priceList.removeOrder(order4);
        System.out.println(priceList.toString());
        priceList.addOrder(order1);
        System.out.println(priceList.toString());
        priceList.addOrder(order3);
        System.out.println(priceList.toString());

//        OrderNodeList anotherList = new OrderNodeList();
//        anotherList.add(new Order("test6", "limit"));
//        anotherList.add(new Order("test7", "limit"));
//        anotherList.add(new Order("test8", "limit"));
//        list.concat(anotherList);
//        System.out.println(list);
    }

}
