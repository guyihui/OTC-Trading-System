

public class Product {

    private String productId;

    public Product(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return obj.hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return productId.hashCode() + "product".hashCode();
    }
}
