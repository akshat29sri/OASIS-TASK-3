import javax.swing.SwingUtilities;

public class ATM {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAccount account = new BankAccount("user123", "1234", 10000.00);
            ATMInterface atmInterface = new ATMInterface(account);
            atmInterface.setVisible(true);
        });
    }
}
