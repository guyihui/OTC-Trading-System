

public class AAASomeTest {

    private static OrderNodeList list = new OrderNodeList();

    public static void main(String[] args) {
//        singleThreadTest();

        for (int i = 0; i < 50; i++) {
            list.add(new Order("test" + i, "limit"));
        }
        Thread thread1 = new Thread(new TestThread1());
        thread1.start();
        Thread thread2 = new Thread(new TestThread2());
        thread2.start();
        for (int i = 50; i < 100; i++) {
            System.out.println("insert" + i);
            list.add(new Order("test" + i, "limit"));
        }
        try {
            thread1.join();
            thread2.join();
            System.out.println(list);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    static class TestThread1 implements Runnable {
        public void run() {
            for (int i = 0; i < 50; i++) {
                Order order = new Order("cancel" + i, "cancel");
                order.setCancelId("test" + i);
                Order canceled = list.cancelOrder(order);
                System.out.println("[Thread 1]" + (canceled == null ? i : canceled.toString()));
                System.out.flush();
            }
        }
    }

    static class TestThread2 implements Runnable {
        public void run() {
            for (int i = 0; i < 50; i++) {
                Order candidate = list.candidateOrder();
                System.out.println("[Thread 2] --->" + candidate);
                System.out.flush();
                candidate.unlock();
                if (list.removeOrder(candidate)) {
                    System.out.println(candidate.getOrderId());
                    System.out.flush();
                }
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
        cancel.setCancelId("test3");
        list.cancelOrder(cancel);
        System.out.println(list.toString());
    }

}
