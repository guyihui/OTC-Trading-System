

public class AAASomeTest {


    private static OrderNodeList list = new OrderNodeList();

    public static void main(String[] args) {
//        singleThreadTest();

//        for (int i = 0; i < 10; i++) {
//            list.add(new Order("test" + i, "limit"));
//        }
        Thread thread1 = new Thread(new TestThread1());
        thread1.start();
        Thread thread2 = new Thread(new TestThread2());
        thread2.start();
        for (int i = 0; i < 20; i++) {
//            System.out.println("insert" + i);
            list.add(new Order("insert" + i, "limit"));
        }
        try {
            thread1.join();
            thread2.join();
            System.out.println(list);
            System.out.println(list.activateStop());
        } catch (Exception e) {
            System.out.println(e.toString());
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

}
