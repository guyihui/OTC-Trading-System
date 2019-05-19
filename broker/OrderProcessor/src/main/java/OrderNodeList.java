
public class OrderNodeList {

    private final OrderNode head = new OrderNode(null);
    private volatile OrderNode tail = this.head;

    public Boolean isEmpty() {
        this.head.lock();
        Boolean isEmpty = (this.head.getNext() == null);
        this.head.unlock();
        return isEmpty;
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
        this.head.unlock();
        return null;
    }

    private OrderNode getTail() {
        OrderNode tail;
        while (true) {
            tail = this.tail;
            tail.lock();
            if (tail.getNext() == null) {
                break;
            }
            tail.unlock();
        }
        return tail;
    }

    public Boolean add(Order order) {
        OrderNode node = new OrderNode(order);
        // lock & append
        OrderNode oldTail;
        oldTail = getTail();
        {
            oldTail.setNext(node);
            this.tail = node;
        }
        oldTail.unlock();
        return true;
    }

    public void concat(OrderNodeList list2) {
        OrderNode oldTail;
        oldTail = getTail();
        list2.head.lock();
        {
            oldTail.setNext(list2.head.getNext());
            OrderNode newTail;
            newTail = list2.getTail();
            this.tail = newTail;
            newTail.unlock();
        }
        list2.head.unlock();
        oldTail.unlock();
    }

    public OrderNodeList activateStop() {
        this.head.lock();
        OrderNode node;
        OrderNode prev;
        node = this.head;
        while (node.getNext() != null) {
            prev = node;
            node = prev.getNext();
            node.lock();
            prev.unlock();
            Order order = node.getOrder();
            order.lock();
            {
                order.stopToLimit();
            }
            order.unlock();
        }
        return this;
    }

    public Order cancelOrder(Order cancelOrder) {
        this.head.lock();
        OrderNode node;
        OrderNode prev;
        node = this.head;
        while (true) {
            if (node.getNext() != null) {
                prev = node;
                node = prev.getNext();
                Order order = node.getOrder();
                order.lock();
                if (order.getOrderId().equals(cancelOrder.getCancelId())) {
                    node.lock();
                    prev.setNext(node.getNext());
                    if (node.getNext() == null) {
                        this.tail = prev;
                    }
                    prev.unlock();
                    node.unlock();
                    order.unlock();
                    return order;
                }
                order.unlock();
                node.lock();
                prev.unlock();
            } else {
                node.unlock();
                return null;
            }
        }
    }

    public Boolean removeOrder(Order toRemove) {
        this.head.lock();
        OrderNode node;
        OrderNode prev;
        node = this.head;
        while (true) {
            if (node.getNext() != null) {
                prev = node;
                node = prev.getNext();
                Order order = node.getOrder();
                order.lock();
                if (order == toRemove) {
                    node.lock();
                    prev.setNext(node.getNext());
                    if (node.getNext() == null) {
                        this.tail = prev;
                    }
                    prev.unlock();
                    node.unlock();
                    order.unlock();
                    return true;
                }
                order.unlock();
                node.lock();
                prev.unlock();
            } else {
                node.unlock();
                return false;
            }
        }
    }

    public String toString() {
        OrderNode node = this.head;
        int count = 0;
        StringBuilder str = new StringBuilder();
        while (node.getNext() != null) {
            str.append("Node_");
            str.append(++count);
            str.append(": ");
            str.append(node.getNext().toTestString());
            str.append("\n");
            node = node.getNext();
        }
        return str.toString();
    }

}