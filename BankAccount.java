public class BankAccount {
    private final String userId;
    private final String pin;
    private double balance;
    private final TransactionHistory transactionHistory;

    public BankAccount(String userId, String pin, double openingBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = openingBalance;
        this.transactionHistory = new TransactionHistory();
        transactionHistory.addTransaction("Account opened", openingBalance);
    }

    public boolean validateLogin(String enteredUserId, String enteredPin) {
        return userId.equals(enteredUserId) && pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        transactionHistory.addTransaction("Withdraw", amount);
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        transactionHistory.addTransaction("Deposit", amount);
        return true;
    }

    public boolean transfer(String receiverUserId, double amount) {
        if (receiverUserId == null || receiverUserId.isBlank() || amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        transactionHistory.addTransaction("Transfer to " + receiverUserId, amount);
        return true;
    }
}
