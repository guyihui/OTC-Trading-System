package com.tradehistoryaccess.BrokerService.OrderBook; /***********************************************************************
 * Module:  PriceNodeList.java
 * Author:  gyh
 * Purpose: Defines the Class PriceNodeList
 ***********************************************************************/

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PriceNodeList {

   private final PriceNode limitHead = new PriceNode();
   private final PriceNode stopHead = new PriceNode();
   private String sellOrBuy;
   private PriceNodeList other;
   private Lock lock = new ReentrantLock();

   public void lock() {
      this.lock.lock();
   }

   public void unlock() {
      this.lock.unlock();
   }

   public void setOther(PriceNodeList otherList) {
      this.other = otherList;
   }

   public String getSellOrBuy(){
      return sellOrBuy;
   }

   public Integer getDepth() {
       limitHead.lock();
       PriceNode limitNext = limitHead.getNext();
       if(limitNext!=null){
           Integer price = limitNext.getPrice();
           limitHead.unlock();
           return price;
       }
       else{
           limitHead.unlock();
           return limitHead.getPrice();
       }
   }


   public PriceNodeList(String sellOrBuy) {
      this.sellOrBuy = sellOrBuy;
      if(sellOrBuy.equals("sell")){
          this.limitHead.setPrice(0);
          this.stopHead.setPrice(0);
      }
      else{
          this.limitHead.setPrice(Integer.MAX_VALUE);
          this.stopHead.setPrice(Integer.MAX_VALUE);
      }
   }

   public Boolean addOrder(Order order) {
//      Integer price = order.getPrice();
//      this.lock();
//      if(other.getDepth() != null) {
//         if (order.getSellOrBuy().equals("sell") && order.getOrderType().equals("stop")) {
//            if (order.getPrice() >= other.getDepth().getPrice()) {
//               order.setOrderType("limit");
//            }
//         } else if (order.getSellOrBuy().equals("buy") && order.getOrderType().equals("stop")) {
//            if (order.getPrice() <= other.getDepth().getPrice()) {
//               order.setOrderType("limit");
//            }
//         }
//      }
//      if(head == null){
//         PriceNode newHead = new PriceNode(price);
//         head = newHead;
//         head.lock();
//         if(order.getOrderType().equals("limit")){
//            depth = newHead;
//         }
//
//         Boolean temp = head.addOrder(order);
//         head.unlock();
//         this.unlock();
//         return temp;
//      }
////      this.unlock();
//
//      head.lock();
//      PriceNode temp;
//      temp = head;
//      int flag = 0;
//
//      if(order.getSellOrBuy().equals("sell")){
//         if(depth == temp){
//            flag = 1;
//         }
//         if(price < temp.getPrice()){ //新的订单的价格小于队首第一个节点的价格，于是新订单的价格是当前价格优先级最高
//            PriceNode newHead = new PriceNode(price);
//            newHead.lock();
//            newHead.setNext(head);
//            head = newHead;
//            if(order.getOrderType().equals("limit")){
//               depth = newHead;
//            }
//            Boolean tempAddOrder = head.addOrder(order);
//            temp.unlock();
//            newHead.unlock();
//            this.unlock();
//
//            return tempAddOrder;
//         }
//         this.unlock();
//
//         while(temp.getNext() != null){
//            temp.getNext().lock();
//            PriceNode tempNext = temp.getNext();
//            if(depth == temp){
//               flag = 1;
//            }
//            if(price == temp.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
//               if(flag == 0 && order.getOrderType().equals("limit")){
//                  depth = temp;
//               }
//               Boolean tempAddOrder = temp.addOrder(order);
//               tempNext.unlock();
//               temp.unlock();
//               return tempAddOrder;
//            }
//            else if(price > temp.getPrice() && price < temp.getNext().getPrice()){ //新的订单的价格介于原price node list中两个节点之间，则插入新节点
//               PriceNode newMedium = new PriceNode(price);
//               newMedium.setNext(tempNext);
//               temp.setNext(newMedium);
//               if(flag == 0 && order.getOrderType().equals("limit")){
//                  depth = newMedium;
//               }
//               Boolean tempAddOrder = newMedium.addOrder(order);
//               tempNext.unlock();
//               temp.unlock();
//               return tempAddOrder;
//            }
//            PriceNode tempPrev;
//            tempPrev = temp;
//            temp = temp.getNext();
//            //temp.lock();
//            tempPrev.unlock();
//         }
//
//         if(price == temp.getPrice()){ //判断最后一个节点的price值是否与新订单的price相等
//            if(flag == 0 && order.getOrderType().equals("limit")){
//               depth = temp;
//            }
//            Boolean tempAddOrder = temp.addOrder(order);
//            temp.unlock();
//            return tempAddOrder;
//         }
//
//         PriceNode newTail = new PriceNode(price);
//         newTail.lock();
//         temp.setNext(newTail);
//         if(flag == 0 && order.getOrderType().equals("limit")){
//            depth = newTail;
//         }
//         Boolean tempAddOrder = newTail.addOrder(order);
//         temp.unlock();
//         newTail.unlock();
//         return tempAddOrder;
//      }
//      else{
//         if(depth == temp){
//            flag = 1;
//         }
//         if(price > temp.getPrice()){ //新的订单的价格大于队首第一个节点的价格，于是新订单的价格是当前价格优先级最高
//            PriceNode newHead = new PriceNode(price);
//            newHead.lock();
//            newHead.setNext(head);
//            head = newHead;
//            if(order.getOrderType().equals("limit")){
//               depth = newHead;
//            }
//            Boolean tempAddOrder = head.addOrder(order);
//            temp.unlock();
//            newHead.unlock();
//            this.unlock();
//
//            return tempAddOrder;
//         }
//         this.unlock();
//
//         while(temp.getNext() != null){
//            temp.getNext().lock();
//            PriceNode tempNext = temp.getNext();
//            if(depth == temp){
//               flag = 1;
//            }
//            if(price == temp.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
//               if(flag == 0 && order.getOrderType().equals("limit")){
//                  depth = temp;
//               }
//               Boolean tempAddOrder = temp.addOrder(order);
//               tempNext.unlock();
//               temp.unlock();
//               return tempAddOrder;
//            }
//            else if(price < temp.getPrice() && price > temp.getNext().getPrice()){
//               PriceNode newMedium = new PriceNode(price);
//               newMedium.setNext(temp.getNext());
//               temp.setNext(newMedium);
//               if(flag == 0){
//                  if(order.getOrderType().equals("limit")) {
//                     depth = newMedium;
//                  }
//               }
//               Boolean tempAddOrder = newMedium.addOrder(order);
//               tempNext.unlock();
//               temp.unlock();
//               return tempAddOrder;
//            }
//            PriceNode tempPrev = temp;
//            temp = temp.getNext();
//            //temp.lock();
//            tempPrev.unlock();
//         }
//
//         if(price == temp.getPrice()){ //判断最后一个节点的price值是否与新订单的price相等
//            if(flag == 0 && order.getOrderType().equals("limit")){
//               depth = temp;
//            }
//            Boolean tempAddOrder = temp.addOrder(order);
//            temp.unlock();
//            return tempAddOrder;
//         }
//
//         PriceNode newTail = new PriceNode(price);
//         newTail.lock();
//         temp.setNext(newTail);
//         if(flag == 0 && order.getOrderType().equals("limit")){
//            depth = newTail;
//         }
//         Boolean tempAddOrder = newTail.addOrder(order);
//         temp.unlock();
//         newTail.unlock();
//         return tempAddOrder;
//
//      }

       Integer price = order.getPrice();
       if(other.getDepth() != null) {
         if (order.getSellOrBuy().equals("sell") && order.getOrderType().equals("stop")) {
            if (order.getPrice() >= other.getDepth()) {
               order.setOrderType("limit");
            }
         } else if (order.getSellOrBuy().equals("buy") && order.getOrderType().equals("stop")) {
            if (order.getPrice() <= other.getDepth()) {
               order.setOrderType("limit");
            }
         }
      }
       PriceNode temp;
       if(order.getOrderType().equals("limit")) {
           temp = limitHead;
       }
       else{
           temp = stopHead;
       }
       temp.lock();
       while(temp.getNext() != null){
           temp.getNext().lock();
           PriceNode tempNext = temp.getNext();
           if(price == tempNext.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               Boolean tempAddOrder = tempNext.addOrder(order);
               tempNext.unlock();
               temp.unlock();
               return tempAddOrder;
           }
           //TODO:改判断条件
           else if(price < temp.getPrice() && price > tempNext.getPrice()){
               PriceNode newMedium = new PriceNode(price);
               newMedium.setNext(tempNext);
               temp.setNext(newMedium);
               if(temp == limitHead){
                   //TODO:Depth变化时动作：如 Broadcast 等
               }
               Boolean tempAddOrder = newMedium.addOrder(order);
               tempNext.unlock();
               temp.unlock();
               return tempAddOrder;
           }
           PriceNode tempPrev = temp;
           temp = tempNext;
           tempPrev.unlock();
       }
       PriceNode newTail = new PriceNode(price);
       Boolean tempAddOrder = newTail.addOrder(order);
       temp.setNext(newTail);
       temp.unlock();
       return tempAddOrder;

   }

   public Order cancelOrder(Order order) {
//      Integer price = order.getPrice();
//      Integer oldDepth;
//      Integer newDepth;
//      this.lock();
//      if(head == null){
//         this.unlock();
//         return null;
//      }
//      PriceNode temp = head;
//      temp.lock();
//      if(temp.getPrice() == price){
//         Order canceledOrder = temp.cancelOrder(order);
//         if(temp.isEmpty() == 0){
//            if(temp != depth){
//               head = head.getNext();
//            }
//            else{
//               oldDepth = depth.getPrice();
//               depth = null;
//               if(head.getNext()==null){
//                  head = null;
//                  temp.unlock();
//                  this.unlock();
//                  return canceledOrder;
//               }
//               head = head.getNext();
//               head.lock();
//               temp.unlock();
//               temp = head;
//               while(temp != null){
//                  if(temp.isEmpty() > 1){
//                     depth = temp;
//                     newDepth = depth.getPrice();
//                     other.checkStop(oldDepth,newDepth);
//                     break;
//                  }
//                  PriceNode tempPrev = temp;
//                  temp = temp.getNext();
//                  temp.lock();
//                  tempPrev.unlock();
//               }
//            }
//         }
//         else if(temp.isEmpty() == 1){
//           if(temp==depth){
//              oldDepth = depth.getPrice();
//               depth = null;
//               temp = head.getNext();
//               temp.lock();
//               head.unlock();
//               while(temp != null){
//                  if(temp.isEmpty() > 1){
//                     depth = temp;
//                     newDepth = depth.getPrice();
//                     other.checkStop(oldDepth,newDepth);
//                     break;
//                  }
//                  PriceNode tempPrev = temp;
//                  temp = temp.getNext();
//                  temp.lock();
//                  tempPrev.unlock();
//               }
//            }
//         }
//         temp.unlock();
//         this.unlock();
//         return canceledOrder;
//      }
//      this.unlock();
//
//
//      while(temp.getNext() != null){
//         PriceNode tempNext = temp.getNext();
//         tempNext.lock();
//         if(tempNext.getPrice() == price){
//            Order canceledOrder = tempNext.cancelOrder(order);
//            if(tempNext.isEmpty() == 0){
//               if(tempNext != depth){
//                  temp.setNext(tempNext.getNext());
//                  tempNext.unlock();
//               }
//               else{
//                  this.lock();
//                  oldDepth = depth.getPrice();
//                  depth = null;
//                  temp.setNext(tempNext.getNext());
//                  tempNext.unlock();
//                  PriceNode tempPrev = temp;
//                  temp = temp.getNext();
//                  temp.lock();
//                  tempPrev.unlock();
//                  while(temp != null){
//                     if(temp.isEmpty() > 1){
//                        depth = temp;
//                        newDepth = depth.getPrice();
//                        other.checkStop(oldDepth,newDepth);
//                        break;
//                     }
//                     tempPrev = temp;
//                     temp = temp.getNext();
//                     temp.lock();
//                     tempPrev.unlock();
//                  }
//                  this.unlock();
//               }
//            }
//            else if(tempNext.isEmpty() == 1){
//               if(tempNext==depth){
//                  this.lock();
//                  oldDepth = depth.getPrice();
//                  depth = null;
//                  PriceNode tempPrev = temp;
//                  temp = tempNext.getNext();
//                  temp.lock();
//                  tempNext.unlock();
//                  tempPrev.unlock();
//                  while(temp != null){
//                     if(temp.isEmpty() > 1){
//                        depth = temp;
//                        newDepth = depth.getPrice();
//                        other.checkStop(oldDepth,newDepth);
//                        break;
//                     }
//                     tempPrev = temp;
//                     temp = temp.getNext();
//                     temp.lock();
//                     tempPrev.unlock();
//                  }
//                  this.unlock();
//               }
//            }
//            else{
//               tempNext.unlock();
//            }
//            temp.unlock();
//            return canceledOrder;
//         }
//         PriceNode tempPrev = temp;
//         temp = temp.getNext();
//         //temp.lock();
//         tempPrev.unlock();
//      }
//      temp.unlock();
//
//      return null;

       Integer price = order.getPrice();
       PriceNode temp;
       temp = stopHead;
       temp.lock();
       while(temp.getNext() != null){
           temp.getNext().lock();
           PriceNode tempNext = temp.getNext();
           if(price == tempNext.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               Order tempCanceledOrder = tempNext.cancelOrder(order);
               if(tempNext.isEmpty()==1){
                   if(temp == limitHead){
                       //TODO:Depth变化时动作：如 Broadcast 等
                   }
                   temp.setNext(tempNext.getNext());
               }
               tempNext.unlock();
               temp.unlock();
               return tempCanceledOrder;
           }
           PriceNode tempPrev = temp;
           temp = tempNext;
           tempPrev.unlock();
       }

       temp.unlock();

       temp = limitHead;
       temp.lock();
       while(temp.getNext() != null){
           temp.getNext().lock();
           PriceNode tempNext = temp.getNext();
           if(price == tempNext.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               Order tempCanceledOrder = tempNext.cancelOrder(order);
               if(tempNext.isEmpty()==1){
                   if(temp == limitHead){
                       //TODO:Depth变化时动作：如 Broadcast 等
                   }
                   temp.setNext(tempNext.getNext());
               }
               tempNext.unlock();
               temp.unlock();
               return tempCanceledOrder;
           }
           PriceNode tempPrev = temp;
           temp = tempNext;
           tempPrev.unlock();
       }
       temp.unlock();
       return null;
   }

   public Order candidateOrder() {
       limitHead.lock();
       PriceNode limitNext = limitHead.getNext();
       if (limitNext == null) {
           limitHead.unlock();
           return null;
       }
       limitNext.lock();
       Order ord = limitNext.candidateOrder();
       limitNext.unlock();
       limitHead.unlock();
       return ord;
   }

   public Boolean checkStop(int oldStopPrice,int newStopPrice) {
//      this.lock();
//      if(head == null){
//         this.unlock();
//         return false;
//      }
//      head.lock();
//      if(this.getSellOrBuy().equals("sell")){
//         if(head.getPrice() >= oldStopPrice){
//            head.unlock();
//            this.unlock();
//            return false;
//         }
//         if(head.getPrice() >= newStopPrice){
//            head.checkStop();
//         }
//         PriceNode temp = head.getNext();
//         if(temp==null){
//            head.unlock();
//            this.unlock();
//            return true;
//         }
//         temp.lock();
//         head.unlock();
//         this.unlock();
//         while(temp!=null){
//            if(temp.getPrice() >= newStopPrice && temp.getPrice() < oldStopPrice) {
//               temp.checkStop();
//               PriceNode tempPrev = temp;
//               temp = temp.getNext();
//               if (temp != null) {
//                  temp.lock();
//               }
//               tempPrev.unlock();
//            }
//            else if(temp.getPrice() < newStopPrice){
//               PriceNode tempPrev = temp;
//               temp = temp.getNext();
//               if (temp != null) {
//                  temp.lock();
//               }
//               tempPrev.unlock();
//            }
//            else{
//               temp.unlock();
//               break;
//            }
//         }
//      }
//      else{
//         if(head.getPrice() <= oldStopPrice){
//            head.unlock();
//            this.unlock();
//            return false;
//         }
//         if(head.getPrice() <= newStopPrice){
//            head.checkStop();
//         }
//         PriceNode temp = head.getNext();
//         if(temp==null){
//            head.unlock();
//            this.unlock();
//            return true;
//         }
//         temp.lock();
//         head.unlock();
//         this.unlock();
//         while(temp!=null){
//            if(temp.getPrice() <= newStopPrice && temp.getPrice() > oldStopPrice) {
//               temp.checkStop();
//               PriceNode tempPrev = temp;
//               temp = temp.getNext();
//               if (temp != null) {
//                  temp.lock();
//               }
//               tempPrev.unlock();
//            }
//            else if(temp.getPrice() > newStopPrice){
//               PriceNode tempPrev = temp;
//               temp = temp.getNext();
//               if (temp != null) {
//                  temp.lock();
//               }
//               tempPrev.unlock();
//            }
//            else{
//               temp.unlock();
//               break;
//            }
//         }
//      }
//      return true;


       return null;
   }

   public Boolean removeOrder(Order order) {

//      Integer price = order.getPrice();
//      Integer oldDepth;
//      Integer newDepth;
//      this.lock();
//      if(head == null){
//         this.unlock();
//         return null;
//      }
//      PriceNode temp = head;
////      System.out.println("1");
////      System.out.flush();
////      System.out.println("caocaocao"+temp.getLimitOrders().toString());
////      System.out.flush();
//      temp.lock();
////      System.out.println("2");
////      System.out.flush();
//      if(temp.getPrice() == price){
////         System.out.println("3");
////         System.out.flush();
//         Boolean removedOrder = temp.removeOrder(order);
////         System.out.println("4");
////         System.out.flush();
//         if(temp.isEmpty() == 0){
//            if(temp != depth){
//               head = head.getNext();
//            }
//            else{
//               oldDepth = depth.getPrice();
//               depth = null;
//               if(head.getNext()==null){
//                  head = null;
////                  System.out.println("5");
////                  System.out.flush();
//                  temp.unlock();
////                  System.out.println("6");
////                  System.out.flush();
//                  this.unlock();
//                  return removedOrder;
//               }
//               head = head.getNext();
////               System.out.println("7");
////               System.out.flush();
////               System.out.println(head.getLimitOrders());
////               System.out.flush();
//               head.lock();
////               System.out.println("8");
////               System.out.flush();
//               temp.unlock();
//               temp = head;
//               while(temp != null){
//                  if(temp.isEmpty() > 1){
//                     depth = temp;
//                     newDepth = depth.getPrice();
//                     other.checkStop(oldDepth,newDepth);
//                     break;
//                  }
//                  PriceNode tempPrev = temp;
//                  temp = temp.getNext();
////                  System.out.println("9");
////                  System.out.flush();
//                  temp.lock();
////                  System.out.println("10");
////                  System.out.flush();
//                  tempPrev.unlock();
//               }
//            }
//         }
//         else if(temp.isEmpty() == 1){
//            if(temp==depth){
//               oldDepth = depth.getPrice();
//               depth = null;
//               temp = head.getNext();
////               System.out.println("11");
////               System.out.flush();
//               temp.lock();
////               System.out.println("12");
////               System.out.flush();
//               head.unlock();
//               while(temp != null){
//                  if(temp.isEmpty() > 1){
//                     depth = temp;
//                     newDepth = depth.getPrice();
//                     other.checkStop(oldDepth,newDepth);
//                     break;
//                  }
//                  PriceNode tempPrev = temp;
//                  temp = temp.getNext();
////                  System.out.println("13");
////                  System.out.flush();
//                  temp.lock();
////                  System.out.println("14");
////                  System.out.flush();
//                  tempPrev.unlock();
//               }
//            }
//         }
////         System.out.println("15");
////         System.out.flush();
//         temp.unlock();
//         this.unlock();
//         return removedOrder;
//      }
//      this.unlock();
//
//      while(temp.getNext() != null){
//         PriceNode tempNext = temp.getNext();
////         System.out.println("16");
////         System.out.flush();
//         tempNext.lock();
//         if(tempNext.getPrice() == price){
//            Boolean removedOrder = tempNext.removeOrder(order);
//            if(tempNext.isEmpty() == 0){
//               if(tempNext != depth){
//                  temp.setNext(tempNext.getNext());
////                  System.out.println("17");
////                  System.out.flush();
//                  tempNext.unlock();
//               }
//               else{
//                  this.lock();
//                  oldDepth = depth.getPrice();
//                  depth = null;
//                  temp.setNext(tempNext.getNext());
////                  System.out.println("18");
////                  System.out.flush();
//                  tempNext.unlock();
//                  PriceNode tempPrev = temp;
//                  temp = temp.getNext();
////                  System.out.println("19");
////                  System.out.flush();
//                  temp.lock();
////                  System.out.println("20");
////                  System.out.flush();
//                  tempPrev.unlock();
//                  while(temp != null){
//                     if(temp.isEmpty() > 1){
//                        depth = temp;
//                        newDepth = depth.getPrice();
//                        other.checkStop(oldDepth,newDepth);
//                        break;
//                     }
//                     tempPrev = temp;
//                     temp = temp.getNext();
//                     temp.lock();
//                     tempPrev.unlock();
//                  }
//                  this.unlock();
//               }
//            }
//            else if(tempNext.isEmpty() == 1){
//               if(tempNext==depth){
//                  this.lock();
//                  oldDepth = depth.getPrice();
//                  depth = null;
//                  PriceNode tempPrev = temp;
//                  temp = tempNext.getNext();
//                  temp.lock();
//                  tempNext.unlock();
//                  tempPrev.unlock();
//                  while(temp != null){
//                     if(temp.isEmpty() > 1){
//                        depth = temp;
//                        newDepth = depth.getPrice();
//                        other.checkStop(oldDepth,newDepth);
//                        break;
//                     }
//                     tempPrev = temp;
//                     temp = temp.getNext();
//                     temp.lock();
//                     tempPrev.unlock();
//                  }
//                  this.unlock();
//               }
//            }
//            else{
//               tempNext.unlock();
//            }
//            temp.unlock();
//            return removedOrder;
//         }
//         PriceNode tempPrev = temp;
//         temp = temp.getNext();
//         //temp.lock();
//         tempPrev.unlock();
//      }
//      temp.unlock();
//
//      return null;

       Integer price = order.getPrice();
       PriceNode temp;
       temp = limitHead;
       temp.lock();
       while(temp.getNext() != null){
           temp.getNext().lock();
           PriceNode tempNext = temp.getNext();
           if(price == tempNext.getPrice()){ //新的订单的价格在原price node list中存在，则直接在后面加上节点
               Boolean tempRemovedOrder = tempNext.removeOrder(order);
               if(tempNext.isEmpty()==1){
                   if(temp == limitHead){
                       //TODO:Depth变化时动作：如 Broadcast 等
                   }
                   temp.setNext(tempNext.getNext());
               }
               tempNext.unlock();
               temp.unlock();
               return tempRemovedOrder;
           }
           PriceNode tempPrev = temp;
           temp = tempNext;
           tempPrev.unlock();
       }

       temp.unlock();
       return false;

   }

   public Boolean removeLevel() {
      // TODO: implement
      return null;
   }

   public String toString(){
      PriceNode node = this.limitHead;
      int count = 0;
      StringBuilder str = new StringBuilder();
      str.append("Test:"+Thread.currentThread().toString()+"\n");
      while (node.getNext() != null) {
         if(node!=limitHead) {
            str.append("PriceNode_");
         }
         else{
            str.append("DepthNode_");
         }
         str.append(++count);
         str.append(": ");
         str.append(node.getPrice()+"\n");
         str.append("Limit:\n");
         str.append(node.getOrders().toString());
         str.append("------------------------------\n");
         str.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
         node = node.getNext();
      }
      return str.toString();

   }

   public JsonArray orderBooktoString(){
       PriceNode node = this.limitHead;
       JsonArray nodeList = new JsonArray();
       while (node.getNext() != null) {
           JsonObject priceNode = new JsonObject();
           JsonArray temp = node.getNext().getOrders().orderListToJsonArray();
           if(temp != null){
               priceNode.addProperty("price", node.getNext().getPrice());
               priceNode.add("orderList",temp);
               nodeList.add(priceNode);
           }
           node = node.getNext();
       }
       return nodeList;
    }
}