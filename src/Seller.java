import java.util.HashMap;
import java.util.UUID;

public class Seller extends Account {
    private HashMap<UUID, Integer> sold_product_count;

    private HashMap<UUID, Integer> inventory;

    private HashMap<UUID, Integer> product_price;
    // TODO maybe add name to seller
    private int balance;

    public Seller(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address);
        this.inventory = new HashMap<>();
        this.product_price = new HashMap<>();
        this.sold_product_count = new HashMap<>();
        this.balance = 0;
    }

    public int get_product_quantity(Product product) {
        UUID uuid = product.getUuid();
        return inventory.get(uuid);
    }

    public HashMap<UUID, Integer> getSold_product_count() {
        return sold_product_count;
    }

    public boolean add_to_inventory(Product product, int quantity) {
        UUID uuid = product.getUuid();
        Integer new_count = inventory.getOrDefault(uuid, 0) + quantity;
        if (new_count >= 0) {
            inventory.put(uuid, new_count);
            return true;
        } else {
            return false;
        }
    }

    public void set_price(Product product, int price) {
        UUID uuid = product.getUuid();
        product_price.put(uuid, price);
    }

    public boolean sell(Product product, int quantity) {
        UUID uuid = product.getUuid();
        if (add_to_inventory(product, -quantity)) {
            Integer new_count = sold_product_count.getOrDefault(uuid, 0) + quantity;
            sold_product_count.put(uuid, new_count);
            balance += 0.9 * product_price.get(uuid);
            return true;
        } else {
            return false;
        }
    }

    public void refund_to_seller(Transaction transaction) {
        add_to_inventory(transaction.getProduct(), transaction.getQuantity());
    }

    public Integer get_sold_count(Product product) {
        return sold_product_count.getOrDefault(product.getUuid(), 0);
    }

    public int getBalance() {
        return balance;
    }

    public HashMap<UUID, Integer> getInventory() {
        return inventory;
    }

    public int selling_price(Product product) {
        UUID uuid = product.getUuid();
        return product_price.get(uuid);
    }
}
