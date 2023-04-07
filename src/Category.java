import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void add_product(Product product) {
        this.products.add(product);
    }

    public void remove_product(Product product) {
        this.products.remove(product);
    }
}
