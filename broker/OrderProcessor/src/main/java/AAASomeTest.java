

public class AAASomeTest {


    private static OrderNodeList list = new OrderNodeList();
    private static PriceNodeList priceList = new PriceNodeList();

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
            for (int i = 0; i < 10; i++) {
                priceList.addOrder(new Order("T1_" + i, "limit",1000+i%3,"sell"));
                System.out.println(priceList.toString()+"\n"+"11111111111111111111\n");
            }
//            Order candi = priceList.candidateOrder();
//            while(candi != null) {
//                priceList.removeOrder(candi);
//                candi.unlock();
//                candi = priceList.candidateOrder();
//            }
//            candi.unlock();
            System.out.println(priceList.toString()+"\n"+"11111111111111111111\n");
        }
    }

    static class TestPriceNodeThread2 implements Runnable {
        public void run() {
            for (int i = 0; i < 10; i++) {
                priceList.addOrder(new Order("T2_" + i, "limit",1000+i%3,"sell"));
                System.out.println(priceList.toString()+"\n"+"22222222222222222222\n");;
            }
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
