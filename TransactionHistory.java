import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount));
    }

    public void displayHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n---------- Transaction History ----------");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("-----------------------------------------");
    }

    public String toDisplayText() {
        if (transactions.isEmpty()) {
            return "No transactions found.";
        }

        StringBuilder builder = new StringBuilder();
        for (Transaction transaction : transactions) {
            builder.append(transaction).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
