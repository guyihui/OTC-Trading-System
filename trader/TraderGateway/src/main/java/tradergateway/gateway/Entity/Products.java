package tradergateway.gateway.Entity;

import java.util.HashMap;
import java.util.Map;

public class Products {

    private static Map<String, Product> products = new HashMap<>();

    public static Product get(String id) {
        return products.get(id);
    }

    static {
        products.put("01", new Product("01", "gold", "2019/07/01"));
        products.put("02", new Product("02", "gold", "2019/08/02"));
        products.put("03", new Product("03", "oil", "2019/06/12"));
        products.put("04", new Product("04", "oil", "2019/08/22"));
        products.put("05", new Product("05", "oil", "2019/08/23"));
        products.put("06", new Product("06", "gas", "2019/07/02"));
        products.put("07", new Product("07", "gold", "2019/08/05"));
        products.put("08", new Product("08", "wheat", "2019/07/01"));
    }
}
