/***********************************************************************
 * Module:  PriceNodeList.java
 * Author:  gyh
 * Purpose: Defines the Class PriceNodeList
 ***********************************************************************/

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PriceNodeList {

   private PriceNode head;
   private PriceNode depth;
   private Lock lock = new ReentrantLock();

   public void lock() {
      this.lock.lock();
   }

   public void unlock() {
      this.lock.unlock();
   }

   public PriceNode getHead() {
      return head;
   }

   public void setHead(PriceNode head) {
      this.head = head;
   }

   public PriceNode getDepth() {
      return depth;
   }

   public void setDepth(PriceNode depth) {
      this.depth = depth;
   }

   public PriceNodeList() {}

   public Boolean addOrder(Order order) {
      Integer price = order.getPrice();
      this.lock();
      if(head == null){
         PriceNode newHead = new PriceNode(price);
         head = newHead;
         head.lock();
         if(order.getOrderType().equals("limit")){
            depth = newHead;
         }

         Boolean temp = head.addOrder(order);
         head.unlock();
         this.unlock();
         return temp;
      }

      head.lock();
      PriceNode temp = head;
      int flag = 0;

      if(order.getSellOrBuy().equals("sell")){
         if(depth == temp){
            flag = 1;
         }
         if(price < temp.getPrice()){ //新的订单的价格小于队首第一个节点的价格，于是新订单的价格是当前价格优先级最高
            PriceNode newHead = new PriceNode(price);
            newHead.lock();
            newHead.setNext(head);
            head = newHead;
            if(order.getOrderType().equals("limit")){
               depth = newHead;
            }
            Boolean tempAddOrder = head.addOrder(order);
            newHead.unlock();
            temp.unlock();

            return tempAddOrder;
         }

         while(temp.getNext() != null){
            temp.getNext().lock();
            PriceNode tempNext = temp.getNext();
            if(depth == temp){
               flag = 1;
            }
            if(price == temp.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               if(flag == 0 && order.getOrderType().equals("limit")){
                  depth = temp;
               }
               Boolean tempAddOrder = temp.addOrder(order);
               temp.unlock();
               tempNext.unlock();
               return tempAddOrder;
            }
            else if(price > temp.getPrice() && price < temp.getNext().getPrice()){ //新的订单的价格介于原price node list中两个节点之间，则插入新节点
               PriceNode newMedium = new PriceNode(price);
               newMedium.setNext(temp.getNext());
               temp.setNext(newMedium);
               if(flag == 0 && order.getOrderType().equals("limit")){
                  depth = newMedium;
               }
               Boolean tempAddOrder = newMedium.addOrder(order);
               temp.unlock();
               tempNext.unlock();
               return tempAddOrder;
            }
            PriceNode tempPrev = temp;
            temp = temp.getNext();
            tempPrev.unlock();
            temp.lock();
         }

         if(price == temp.getPrice()){ //判断最后一个节点的price值是否与新订单的price相等
            if(flag == 0 && order.getOrderType().equals("limit")){
               depth = temp;
            }
            Boolean tempAddOrder = temp.addOrder(order);
            temp.unlock();
            return tempAddOrder;
         }

         PriceNode newTail = new PriceNode(price);
         newTail.lock();
         temp.setNext(newTail);
         if(flag == 0 && order.getOrderType().equals("limit")){
            depth = newTail;
         }
         Boolean tempAddOrder = newTail.addOrder(order);
         temp.unlock();
         newTail.unlock();
         return tempAddOrder;
      }
      else{
         if(depth == temp){
            flag = 1;
         }
         if(price > temp.getPrice()){ //新的订单的价格大于队首第一个节点的价格，于是新订单的价格是当前价格优先级最高
            PriceNode newHead = new PriceNode(price);
            newHead.lock();
            newHead.setNext(head);
            head = newHead;
            if(order.getOrderType().equals("limit")){
               depth = newHead;
            }
            Boolean tempAddOrder = head.addOrder(order);
            newHead.unlock();
            temp.unlock();

            return tempAddOrder;
         }

         while(temp.getNext() != null){
            temp.getNext().lock();
            PriceNode tempNext = temp.getNext();
            if(depth == temp){
               flag = 1;
            }
            if(price == temp.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               if(flag == 0 && order.getOrderType().equals("limit")){
                  depth = temp;
               }
               Boolean tempAddOrder = temp.addOrder(order);
               temp.unlock();
               tempNext.unlock();
               return tempAddOrder;
            }
            else if(price < temp.getPrice() && price > temp.getNext().getPrice()){
               PriceNode newMedium = new PriceNode(price);
               newMedium.setNext(temp.getNext());
               temp.setNext(newMedium);
               if(flag == 0){
                  if(order.getOrderType().equals("limit")) {
                     depth = newMedium;
                  }
               }
               Boolean tempAddOrder = newMedium.addOrder(order);
               temp.unlock();
               tempNext.unlock();
               return tempAddOrder;
            }
            PriceNode tempPrev = temp;
            temp = temp.getNext();
            tempPrev.unlock();
            temp.lock();
         }

         if(price == temp.getPrice()){ //判断最后一个节点的price值是否与新订单的price相等
            if(flag == 0 && order.getOrderType().equals("limit")){
               depth = temp;
            }
            Boolean tempAddOrder = temp.addOrder(order);
            temp.unlock();
            return tempAddOrder;
         }

         PriceNode newTail = new PriceNode(price);
         newTail.lock();
         temp.setNext(newTail);
         if(flag == 0 && order.getOrderType().equals("limit")){
            depth = newTail;
         }
         Boolean tempAddOrder = newTail.addOrder(order);
         temp.unlock();
         newTail.unlock();
         return tempAddOrder;

      }

   }

   public Order cancelOrder(Order order) {
      Integer price = order.getPrice();
      if(head == null){
         return null;
      }
      PriceNode temp = head;
      temp.lock();
      if(temp.getPrice() == price){
         Order canceledOrder = temp.cancelOrder(order);
         if(temp.isEmpty() == 0){
            if(temp != depth){
               head = head.getNext();
            }
            else{
               depth = null;
               if(head.getNext()==null){
                  head = null;
                  temp.unlock();
                  return canceledOrder;
               }
               head = head.getNext();
               head.lock();
               temp.unlock();
               temp = head;
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  PriceNode tempPrev = temp;
                  temp = temp.getNext();
                  temp.lock();
                  tempPrev.unlock();
               }
            }
         }
         else if(temp.isEmpty() == 1){
           if(temp==depth){
               depth = null;
               temp = head.getNext();
               temp.lock();
               head.unlock();
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  PriceNode tempPrev = temp;
                  temp = temp.getNext();
                  temp.lock();
                  tempPrev.unlock();
               }
            }
         }
         temp.unlock();
         return canceledOrder;
      }

      while(temp.getNext() != null){
         PriceNode tempNext = temp.getNext();
         tempNext.lock();
         if(tempNext.getPrice() == price){
            Order canceledOrder = tempNext.cancelOrder(order);
            if(tempNext.isEmpty() == 0){
               if(tempNext != depth){
                  temp.setNext(tempNext.getNext());
                  tempNext.unlock();
               }
               else{
                  depth = null;
                  temp.setNext(tempNext.getNext());
                  tempNext.unlock();
                  PriceNode tempPrev = temp;
                  temp = temp.getNext();
                  temp.lock();
                  tempPrev.unlock();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     tempPrev = temp;
                     temp = temp.getNext();
                     temp.lock();
                     tempPrev.unlock();
                  }
               }
            }
            else if(tempNext.isEmpty() == 1){
               if(tempNext==depth){
                  depth = null;
                  PriceNode tempPrev = temp;
                  temp = tempNext.getNext();
                  temp.lock();
                  tempNext.unlock();
                  tempPrev.unlock();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     tempPrev = temp;
                     temp = temp.getNext();
                     temp.lock();
                     tempPrev.unlock();
                  }
               }
            }
            temp.unlock();
            return canceledOrder;
         }
         PriceNode tempPrev = temp;
         temp = temp.getNext();
         temp.lock();
         tempPrev.unlock();
      }
      temp.unlock();

      return null;
   }

   public Order candidateOrder() {
      if(depth == null){
         return null;
      }
      return depth.candidateOrder();
   }

   public Integer checkStop(Integer stopPrice) {
      // TODO: implement
      return null;
   }

   public Boolean removeOrder(Order order) {

      Integer price = order.getPrice();
      if(head == null){
         return null;
      }
      PriceNode temp = head;
      temp.lock();
      if(temp.getPrice() == price){
         Boolean removedOrder = temp.removeOrder(order);
         if(temp.isEmpty() == 0){
            if(temp != depth){
               head = head.getNext();
            }
            else{
               depth = null;
               if(head.getNext()==null){
                  head = null;
                  temp.unlock();
                  return removedOrder;
               }
               head = head.getNext();
               head.lock();
               temp.unlock();
               temp = head;
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  PriceNode tempPrev = temp;
                  temp = temp.getNext();
                  temp.lock();
                  tempPrev.unlock();
               }
            }
         }
         else if(temp.isEmpty() == 1){
            if(temp==depth){
               depth = null;
               temp = head.getNext();
               temp.lock();
               head.unlock();
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  PriceNode tempPrev = temp;
                  temp = temp.getNext();
                  temp.lock();
                  tempPrev.unlock();
               }
            }
         }
         temp.unlock();
         return removedOrder;
      }

      while(temp.getNext() != null){
         PriceNode tempNext = temp.getNext();
         tempNext.lock();
         if(tempNext.getPrice() == price){
            Boolean removedOrder = tempNext.removeOrder(order);
            if(tempNext.isEmpty() == 0){
               if(tempNext != depth){
                  temp.setNext(tempNext.getNext());
                  tempNext.unlock();
               }
               else{
                  depth = null;
                  temp.setNext(tempNext.getNext());
                  tempNext.unlock();
                  PriceNode tempPrev = temp;
                  temp = temp.getNext();
                  temp.lock();
                  tempPrev.unlock();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     tempPrev = temp;
                     temp = temp.getNext();
                     temp.lock();
                     tempPrev.unlock();
                  }
               }
            }
            else if(tempNext.isEmpty() == 1){
               if(tempNext==depth){
                  depth = null;
                  PriceNode tempPrev = temp;
                  temp = tempNext.getNext();
                  temp.lock();
                  tempNext.unlock();
                  tempPrev.unlock();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     tempPrev = temp;
                     temp = temp.getNext();
                     temp.lock();
                     tempPrev.unlock();
                  }
               }
            }
            temp.unlock();
            return removedOrder;
         }
         PriceNode tempPrev = temp;
         temp = temp.getNext();
         temp.lock();
         tempPrev.unlock();
      }
      temp.unlock();

      return null;
   }

   public Boolean removeLevel() {
      // TODO: implement
      return null;
   }

   public String toString(){
      PriceNode node = this.head;
      int count = 0;
      StringBuilder str = new StringBuilder();
      str.append("Test:\n");
      while (node != null) {
         if(node!=depth) {
            str.append("PriceNode_");
         }
         else{
            str.append("DepthNode_");
         }
         str.append(++count);
         str.append(": ");
         str.append(node.getPrice()+" ");
         str.append(node.getLimitOrders().toString());
         str.append("------------------------------");
         str.append(node.getStopOrders().toString());
         str.append("\n");
         node = node.getNext();
      }
      return str.toString();
   }

}