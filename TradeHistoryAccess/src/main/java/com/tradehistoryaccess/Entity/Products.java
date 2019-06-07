package com.tradehistoryaccess.Entity;

import com.tradehistoryaccess.BrokerService.OrderBook.Product;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class Products{
    public static Map<String, Product> Products=new HashMap<>();


    public static Product get(String id){
        return Products.get(id);
    }

    static {
        Products.put("01",new Product("01","gold","2019/07/01"));
        Products.put("02",new Product("02","gold","2019/08/02"));
        Products.put("03",new Product("03","oil","2019/06/12"));
        Products.put("04",new Product("04","oil","2019/08/22"));
        Products.put("05",new Product("05","oil","2019/08/23"));
        Products.put("06",new Product("06","gas","2019/07/02"));
        Products.put("07",new Product("07","gold","2019/08/05"));
        Products.put("08",new Product("08","wheat","2019/07/01"));
    }
}
