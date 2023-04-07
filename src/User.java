import java.util.ArrayList;
import java.util.List;

public class User extends Account {
    private int balance;
    private List<Order> request_order;
    private List<Order> finish_order;
    private Order cart;

    public User(String username, String password, String email, String phone, String address, int balance) {
        super(username, password, email, phone, address);
        this.request_order = new ArrayList<>();
        this.finish_order = new ArrayList<>();
        this.cart = new Order();
        this.balance = balance;
    }

    public User(String username, String password, String email, String phone, String address) {
        super(username, password, email, phone, address);
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void add_product_to_cart(Seller seller, int quantity, Product product){
        Transaction transaction = new Transaction(seller,quantity,product);
        this.cart.add_transaction(transaction);
    }

    public List<Order> getRequest_order() {
        return request_order;
    }

    public void add_to_request_order() {
        request_order.add(cart);
        clear_cart();
    }


    public void clear_cart() {
        this.cart = new Order();
    }


    public int getBalance() {
        return balance;
    }

    public List<Order> getFinish_order() {
        return finish_order;
    }

    public void refund_order(Order order){
        request_order.remove(order);
    }

    @Override
    public String toString() {
        return "User{" +
                "request_order=" + request_order +
                '}';
    }
}
