/***********************************************************************
 * Module:  Trader.java
 * Author:  gyh
 * Purpose: Defines the Class Trader
 ***********************************************************************/

import java.util.*;

/** @pdOid 7373a996-c0c5-4f35-b296-50322d629344 */
public class Trader {
   /** @pdOid 4567beab-9ee8-454a-813a-7fdfa09f0ee9 */
   private String traderId;
   /** @pdOid c28f8105-015f-4587-81ca-39a3afca4896 */
   private String traderCompany;
   /** @pdOid 270a91ec-d4a6-4e39-9ff6-add624d25523 */
   private Object connection;


   public Trader(String traderId,String traderCompany){

   }

   public String getTraderId() {
      return traderId;
   }

   public void setTraderId(String traderId) {
      this.traderId = traderId;
   }

   public String getTraderCompany() {
      return traderCompany;
   }

   public void setTraderCompany(String traderCompany) {
      this.traderCompany = traderCompany;
   }

   public Object getConnection() {
      return connection;
   }

   public void setConnection(Object connection) {
      this.connection = connection;
   }
}