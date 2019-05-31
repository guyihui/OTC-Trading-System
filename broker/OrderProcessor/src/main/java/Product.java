/***********************************************************************
 * Module:  Product.java
 * Author:  gyh
 * Purpose: Defines the Class Product
 ***********************************************************************/

import java.util.*;

public class Product {

   private String productId;
   private String productName;
   private String productPeriod;

   public Product(String pId,String pName,String pPeriod){
      this.productId = pId;
      this.productName = pName;
      this.productPeriod = pPeriod;
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

   public boolean equals(Object obj){
       if(obj instanceof Product){
          return (((Product)obj).getProductId()).equals(this.productId);
       }
       return false;
   }

   @Override
   public int hashCode() {
      return this.productId.hashCode();
   }
}