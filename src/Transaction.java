public class Transaction {
    private Seller seller;
    private int quantity;
    private Product product;

    public Transaction(Seller seller, int quantity, Product product) {
        this.seller = seller;
        this.quantity = quantity;
        this.product = product;
    }

    public int get_transaction_cost() {
        return seller.selling_price(product) * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public String toString() {
        return quantity + " * " + product.getName() + "from" + seller.getUsername();
    }
}
