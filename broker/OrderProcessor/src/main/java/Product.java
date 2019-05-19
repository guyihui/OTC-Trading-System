/***********************************************************************
 * Module:  Product.java
 * Author:  gyh
 * Purpose: Defines the Class Product
 ***********************************************************************/

import java.util.*;

/** @pdOid f9244257-ad1f-43f1-b98e-cbefb047bd16 */
public class Product {
   /** @pdOid 1cf68cd4-dc07-479e-97b5-f63e08014601 */
   private String productId;
   /** @pdOid ae1a1dfd-0e30-4460-a721-a74959221f39 */
   private String productName;
   /** @pdOid d7ecbf62-83b4-424b-b265-a12596492f50 */
   private String productPeriod;

   public Product(String productId,String productName,String productPeriod){
      this.setProductId(productId);
      this.setProductName(productName);
      this.setProductPeriod(productPeriod);
   }

   public String getProductId() {
      return productId;
   }

   public void setProductId(String productId) {
      this.productId = productId;
   }

   public String getProductName() {
      return productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public String getProductPeriod() {
      return productPeriod;
   }

   public void setProductPeriod(String productPeriod) {
      this.productPeriod = productPeriod;
   }
}