
public class OrderNodeList {

    private final OrderNode head = new OrderNode(null);
    private OrderNode tail = this.head;

    public Boolean isEmpty() {
        this.head.lock();
        Boolean isEmpty = (this.head.getNext() == null);
        this.head.unlock();
        return isEmpty;
    }

    public Boolean add(Order order) {
        OrderNode node = new OrderNode(order);
        // lock & append
        this.tail.lock();
        OrderNode oldTail = this.tail;
        {
            oldTail.setNext(node);
            this.tail = node;
        }
        oldTail.unlock();
        return true;
    }

    public Order cancelOrder(Order cancelOrder) {
        OrderNode node = this.head;
        OrderNode prev;
        node.lock();
        while (true) {
            if (node.getNext() != null) {
                prev = node;
                node = prev.getNext();
                Order order = node.getOrder();
                order.lock();
                if (order.getOrderId().equals(cancelOrder.getCancelId())) {
                    node.lock();
                    prev.setNext(node.getNext());
                    prev.unlock();
                    node.unlock();
                    order.unlock();
                    return order;
                }
                order.unlock();
                node.lock();
                prev.unlock();
            } else {
                return null;
            }
        }
    }

    public Order candidateOrder() {
        this.head.lock();
        OrderNode firstNode = this.head.getNext();
        if (firstNode != null) {
            firstNode.lock();
            Order order = firstNode.getOrder();
            order.lock();
            firstNode.unlock();
            this.head.unlock();
            return order;
        }
        return null;
    }

    public Boolean removeOrder(Order toRemove) {
        OrderNode node = this.head;
        OrderNode prev;
        node.lock();
        while (true) {
            if (node.getNext() != null) {
                prev = node;
                node = prev.getNext();
                Order order = node.getOrder();
                order.lock();
                if (order == toRemove) {
                    node.lock();
                    prev.setNext(node.getNext());
                    prev.unlock();
                    node.unlock();
                    order.unlock();
                    return true;
                }
                order.unlock();
                node.lock();
                prev.unlock();
            } else {
                return false;
            }
        }
    }

}