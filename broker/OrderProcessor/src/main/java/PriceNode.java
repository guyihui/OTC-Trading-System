/***********************************************************************
 * Module:  PriceNode.java
 * Author:  gyh
 * Purpose: Defines the Class PriceNode
 ***********************************************************************/

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PriceNode {

   private int price;
   private OrderNodeList limitOrders;
   private OrderNodeList stopOrders;
   private PriceNode next;
   private Lock lock = new ReentrantLock();

   public void lock() {
      this.lock.lock();
   }

   public void unlock() {
      this.lock.unlock();
   }

   public PriceNode(int price){
      this.price=price;
      limitOrders=new OrderNodeList();
      stopOrders=new OrderNodeList();
   }

   public Boolean addOrder(Order order) {
      if(order.getOrderType().equals("stop")){
         return stopOrders.add(order);
      }
      else{
         return limitOrders.add(order);
      }
   }

   public Order cancelOrder(Order order) {
      if(order.getOrderType().equals("stop")){
         return stopOrders.cancelOrder(order);
      }
      else{
         return limitOrders.cancelOrder(order);
      }
   }

   public Order candidateOrder() {
      return limitOrders.candidateOrder();
   }

   public Boolean removeOrder(Order order) {
      if(order.getOrderType().equals("stop")){
         return stopOrders.removeOrder(order);
      }
      else{
         return limitOrders.removeOrder(order);
      }
   }

   public int isEmpty(){
      if(limitOrders.isEmpty() == true && stopOrders.isEmpty() == true){
         return 0;
      }
      if(limitOrders.isEmpty() == true){
         return 1;
      }
      else if(stopOrders.isEmpty() == true){
         return 2;
      }
      else{
         return 3;
      }
   }

   public Boolean checkStop() {
      if(stopOrders.isEmpty() == true){
         return false;
      }
      limitOrders.concat(stopOrders.activateStop());
      stopOrders = null;
      return true;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public OrderNodeList getLimitOrders() {
      return limitOrders;
   }

   public void setLimitOrders(OrderNodeList limitOrders) {
      this.limitOrders = limitOrders;
   }

   public OrderNodeList getStopOrders() {
      return stopOrders;
   }

   public void setStopOrders(OrderNodeList stopOrders) {
      this.stopOrders = stopOrders;
   }

   public PriceNode getNext() {
      return next;
   }

   public void setNext(PriceNode next) {
      this.next = next;
   }

}