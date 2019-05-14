/***********************************************************************
 * Module:  Order.java
 * Author:  gyh
 * Purpose: Defines the Class Order
 ***********************************************************************/

import java.util.*;

/** @pdOid 3b0f9e55-9f29-4cf1-bb90-fd07ba10f30b */
public class Order {
   /** @pdOid 11ca45aa-366b-4205-b00e-b2e8257eae0a */
   private String orderId;
   /** @pdOid f7933100-5fd3-4c3f-90db-9e4dd9316e46 */
   private String orderType;
   /** @pdOid 288adf00-0a13-41ed-b37f-6e1ef65096bb */
   private Product product;
   /** @pdOid d56c204a-245c-4219-ad33-1bb75cca7bb5 */
   private Integer sellOrBuy;
   /** @pdOid b924c707-e5b8-407d-85d1-8a78ad24777c */
   private String broker;
   /** @pdOid 14b95b3f-e52f-4d90-8c34-551b8565d72b */
   private Trader trader;
   /** @pdOid 07a2e586-998d-400d-bb88-ba5059ce9d31 */
   private Integer totalQuantity;
   /** @pdOid ff4782df-10d7-4fe3-8a67-d09098ece077 */
   private Integer remainingQuantity;
   /** @pdOid a66db590-b051-47f8-881a-5c01b9451924 */
   private Integer price;
   /** @pdOid 1a972284-57e7-4f6b-aeff-1828984dbc0c */
   private String cancelId;
   /** @pdOid e2266f5e-970c-4dd7-bc55-b9dc8afa90d0 */
   private Long time;
   
   /** @pdOid b3f570db-0b65-4095-88ef-906a6255bbbb */
   public Integer stopToLimit() {
      // TODO: implement
      return null;
   }
   
   /** @pdOid 1716121e-3334-4c45-a2b7-427c79853fff */
   public Integer fill() {
      // TODO: implement
      return null;
   }

}