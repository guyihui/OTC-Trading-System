package com.tradehistoryaccess.BrokerService.OrderBook; /***********************************************************************
 * Module:  PriceNode.java
 * Author:  gyh
 * Purpose: Defines the Class PriceNode
 ***********************************************************************/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PriceNode {

   private int price;
   private OrderNodeList orders;
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
      orders=new OrderNodeList();
   }

   public PriceNode(){

   }

   public Boolean addOrder(Order order) {
      return orders.add(order);
   }

   public Order cancelOrder(Order order) {
      return orders.cancelOrder(order);
   }

   public Order candidateOrder() {
      return orders.candidateOrder();
   }

   public Boolean removeOrder(Order order) {
      return orders.removeOrder(order);
   }

   public int isEmpty(){
      if(orders.isEmpty()){
         return 1;
      }
      else{
         return 0;
      }
   }

   public Boolean checkStop() {
      return null;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public OrderNodeList getOrders() {
      return orders;
   }

   public void setOrders(OrderNodeList orders) {
      this.orders = orders;
   }


   public PriceNode getNext() {
      return next;
   }

   public void setNext(PriceNode next) {
      this.next = next;
   }

}