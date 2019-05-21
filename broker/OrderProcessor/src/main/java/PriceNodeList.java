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
      //this.lock();
      if(head == null){
         PriceNode newHead = new PriceNode(price);
         head = newHead;
         //head.lock();
         if(order.getOrderType().equals("limit")){
            depth = newHead;
         }

         Boolean temp = head.addOrder(order);
         //head.unlock();
         //this.unlock();
         return temp;
      }

      //head.lock();
      PriceNode temp = head;
      int flag = 0;

      if(order.getSellOrBuy().equals("sell")){
         if(price < temp.getPrice()){ //新的订单的价格小于队首第一个节点的价格，于是新订单的价格是当前价格优先级最高
            PriceNode newHead = new PriceNode(price);
            //newHead.lock();
            newHead.setNext(head);
            head = newHead;
            if(order.getOrderType().equals("limit")){
               depth = newHead;
            }
            Boolean tempAddOrder = head.addOrder(order);
            //newHead.unlock();
            //head.unlock();

            return tempAddOrder;
         }

         while(temp.getNext() != null){
            if(depth == temp){
               flag = 1;
            }
            if(price == temp.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               if(flag == 0 && order.getOrderType().equals("limit")){
                  depth = temp;
               }
               return temp.addOrder(order);
            }
            else if(price > temp.getPrice() && price < temp.getNext().getPrice()){ //新的订单的价格介于原price node list中两个节点之间，则插入新节点
                  PriceNode newMedium = new PriceNode(price);
                  newMedium.setNext(temp.getNext());
                  temp.setNext(newMedium);
               if(flag == 0 && order.getOrderType().equals("limit")){
                  depth = newMedium;
               }
                  return newMedium.addOrder(order);
            }
            temp = temp.getNext();
         }

         if(price == temp.getPrice()){ //判断最后一个节点的price值是否与新订单的price相等
            if(flag == 0 && order.getOrderType().equals("limit")){
               depth = temp;
            }
            return temp.addOrder(order);
         }

         PriceNode newTail = new PriceNode(price);
         temp.setNext(newTail);
         if(flag == 0 && order.getOrderType().equals("limit")){
            depth = newTail;
         }
         return newTail.addOrder(order);
      }
      else{

         if(price > temp.getPrice()){ //新的订单的价格大于队首第一个节点的价格，于是新订单的价格是当前价格优先级最高
            PriceNode newHead = new PriceNode(price);
            newHead.setNext(head);
            head = newHead;
            if(order.getOrderType().equals("limit")){
               depth = newHead;
            }
            return head.addOrder(order);
         }

         while(temp.getNext() != null){
            if(depth == temp){
               flag = 1;
            }
            if(price == temp.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               if(flag == 0 && order.getOrderType().equals("limit")){
                  depth = temp;
               }
               return temp.addOrder(order);
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
               return newMedium.addOrder(order);
            }
            temp = temp.getNext();
         }

         if(price == temp.getPrice()){ //判断最后一个节点的price值是否与新订单的price相等
            if(flag == 0 && order.getOrderType().equals("limit")){
               depth = temp;
            }
            return temp.addOrder(order);
         }

         PriceNode newTail = new PriceNode(price);
         temp.setNext(newTail);
         if(flag == 0 && order.getOrderType().equals("limit")){
            depth = newTail;
         }
         return newTail.addOrder(order);

      }

   }

   public Order cancelOrder(Order order) {
      Integer price = order.getPrice();
      if(head == null){
         return null;
      }
      PriceNode temp = head;
      if(temp.getPrice() == price){
         Order canceledOrder = temp.cancelOrder(order);
         if(temp.isEmpty() == 0){
            if(temp != depth){
               head = head.getNext();
            }
            else{
               depth = null;
               head = head.getNext();
               temp = head;
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  temp = temp.getNext();
               }
            }
         }
         else if(temp.isEmpty() == 1){
           if(temp==depth){
               depth = null;
               temp = head.getNext();
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  temp = temp.getNext();
               }
            }
         }
         return canceledOrder;
      }

      while(temp.getNext() != null){
         if(temp.getNext().getPrice() == price){
            Order canceledOrder = temp.getNext().cancelOrder(order);
            if(temp.getNext().isEmpty() == 0){
               if(temp.getNext() != depth){
                  temp.setNext(temp.getNext().getNext());
               }
               else{
                  depth = null;
                  temp.setNext(temp.getNext().getNext());
                  temp = temp.getNext();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     temp = temp.getNext();
                  }
               }
            }
            else if(temp.getNext().isEmpty() == 1){
               if(temp.getNext()==depth){
                  depth = null;
                  temp = temp.getNext().getNext();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     temp = temp.getNext();
                  }
               }
            }
            return canceledOrder;
         }
         temp = temp.getNext();
      }

      return null;
   }

   public Order candidateOrder() {
      if(depth == null){
         return null;
      }
      return depth.candidateOrder();
   }

   public Integer checkStop() {
      // TODO: implement
      return null;
   }

   public Boolean removeOrder(Order order) {
      Integer price = order.getPrice();
      if(head == null){
         return null;
      }
      PriceNode temp = head;
      if(temp.getPrice() == price){
         Boolean removedOrder = temp.removeOrder(order);
         if(temp.isEmpty() == 0){
            if(temp != depth){
               head = head.getNext();
            }
            else{
               depth = null;
               head = head.getNext();
               temp = head;
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  temp = temp.getNext();
               }
            }
         }
         else if(temp.isEmpty() == 1){
            if(temp==depth){
               depth = null;
               temp = head.getNext();
               while(temp != null){
                  if(temp.isEmpty() > 1){
                     depth = temp;
                     break;
                  }
                  temp = temp.getNext();
               }
            }
         }
         return removedOrder;
      }

      while(temp.getNext() != null){
         if(temp.getNext().getPrice() == price){
            Boolean removedOrder = temp.getNext().removeOrder(order);
            if(temp.getNext().isEmpty() == 0){
               if(temp.getNext() != depth){
                  temp.setNext(temp.getNext().getNext());
               }
               else{
                  depth = null;
                  temp.setNext(temp.getNext().getNext());
                  temp = temp.getNext();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     temp = temp.getNext();
                  }
               }
            }
            else if(temp.getNext().isEmpty() == 1){
               if(temp.getNext()==depth){
                  depth = null;
                  temp = temp.getNext().getNext();
                  while(temp != null){
                     if(temp.isEmpty() > 1){
                        depth = temp;
                        break;
                     }
                     temp = temp.getNext();
                  }
               }
            }
            return removedOrder;
         }
         temp = temp.getNext();
      }

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