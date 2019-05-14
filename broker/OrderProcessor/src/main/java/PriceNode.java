/***********************************************************************
 * Module:  PriceNode.java
 * Author:  gyh
 * Purpose: Defines the Class PriceNode
 ***********************************************************************/

import java.util.*;

/** @pdOid bd961b70-b5fc-4a8b-8c4e-0a964363046c */
public class PriceNode {
   /** @pdOid ffe799d3-68b2-4d15-b70c-a96d91547c30 */
   private int price;
   /** @pdOid 6cc5e51c-eee7-49d2-9864-1aa36b52fb4b */
   private OrderNodeList limitOrders;
   /** @pdOid 2923b27c-bb8d-4576-b6d6-249a2af9abcf */
   private OrderNodeList stopOrders;
   /** @pdOid 5c35cfce-83e4-4495-a01d-e91f807f1c8d */
   private PriceNode next;
   
   /** @pdOid 376fc952-7e86-468e-b27e-42b4f4ad6ddb */
   public Boolean addOrder() {
      // TODO: implement
      return null;
   }
   
   /** @pdOid 714de740-0fc1-4a49-9408-74feef59c63a */
   public Order cancelOrder() {
      // TODO: implement
      return null;
   }
   
   /** @pdOid 05a94448-9f83-4d81-9593-e5767a508a93 */
   public Order candidateOrder() {
      // TODO: implement
      return null;
   }
   
   /** @pdOid fedbfc7f-ec06-4253-8e6d-a2a1aee44be4 */
   public Boolean removeOrder() {
      // TODO: implement
      return null;
   }

}