import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID uuid;
    private Date date;
    private boolean confirmed;
    private List<Transaction> transactions;

    public Order(Transaction transaction) {
        this.uuid = UUID.randomUUID();
        this.date = new Date();
        this.confirmed = false;
        this.transactions = new ArrayList<>();
        add_transaction(transaction);
    }

    public Order() {
        this.uuid = UUID.randomUUID();
        this.date = new Date();
        this.confirmed = false;
        this.transactions = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public boolean is_confirmed() {
        return confirmed;
    }

    public void confirm() {
        confirmed = true;
    }

    public int get_total_price() {
        int total_price = 0;
        for (Transaction transaction : transactions) {
            total_price += transaction.get_transaction_cost();
        }
        return total_price;
    }

    public void add_transaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void display_summary(int num_transactions) {
        System.out.println("Order Summary - " + date + " - " + (confirmed ? "" : "Not ") + "Confirmed");
        System.out.println("Total Price: " + get_total_price());
        // print first few transactions
        num_transactions = Math.min(num_transactions, transactions.size());
        for (int i = 0; i < num_transactions; i++) {
            System.out.println(transactions.get(i));
        }

        // print number of transactions not shown
        if (num_transactions < transactions.size()) {
            System.out.println("... and " + (transactions.size() - num_transactions) + " more");
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
