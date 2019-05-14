/***********************************************************************
 * Module:  Orderbook.java
 * Author:  gyh
 * Purpose: Defines the Class Orderbook
 ***********************************************************************/

import java.util.*;

/** @pdOid b50fe714-3a4d-4efb-a9e0-a6c423c56b92 */
public class Orderbook {
   /** @pdOid ce10f3ae-11bc-4d84-a2b6-81b3f38e3b9e */
   private Product product;
   /** @pdOid ada82225-fe11-45de-8a92-883c61ed5ea8 */
   private PriceNodeList buyOrders;
   /** @pdOid cae31ea5-70da-41d9-bd8c-b88598f3fccc */
   private PriceNodeList sellOrders;
   /** @pdOid e23127b9-f4ec-4d2a-83e1-2c0a3c9658d6 */
   private WaitingOrders waitingQueue;
   
   /** @pdOid 1d2381fa-3354-4d7e-a3c0-23a99da9508a */
   public Boolean addOrder() {
      // TODO: implement
      return null;
   }
   
   /** @pdOid 57e72597-ab90-4c60-8bf4-3985925d4da8 */
   public Order cancelOrder() {
      // TODO: implement
      return null;
   }
   
   /** @pdOid 766045ff-5f99-4014-b35b-77299f60a553 */
   public Boolean deal() {
      // TODO: implement
      return null;
   }

}