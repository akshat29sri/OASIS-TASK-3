import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ATMInterface extends JFrame {
    private static final Color ATM_DARK = new Color(15, 23, 42);
    private static final Color ATM_PANEL = new Color(30, 41, 59);
    private static final Color ATM_SCREEN = new Color(218, 252, 231);
    private static final Color ATM_GREEN = new Color(34, 197, 94);
    private static final Color ATM_BLUE = new Color(37, 99, 235);
    private static final Color ATM_RED = new Color(220, 38, 38);

    private final BankAccount account;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    private JTextField userIdField;
    private JPasswordField pinField;
    private JLabel balanceLabel;
    private JLabel statusLabel;
    private JTextArea screenArea;
    private JTextField amountField;
    private JTextField receiverField;

    public ATMInterface(BankAccount account) {
        this.account = account;
        setTitle("OASIS ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 600);
        setMinimumSize(new Dimension(760, 540));
        setLocationRelativeTo(null);

        cards.add(buildLoginPanel(), "Login");
        cards.add(buildAtmPanel(), "ATM");
        setContentPane(cards);
    }

    private JPanel buildLoginPanel() {
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(48, 70, 48, 70));

        JLabel title = new JLabel("OASIS ATM", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        panel.add(title, BorderLayout.NORTH);

        JPanel loginBox = new RoundedPanel(new Color(255, 255, 255, 235));
        loginBox.setLayout(new GridLayout(6, 1, 10, 10));
        loginBox.setBorder(BorderFactory.createEmptyBorder(32, 44, 32, 44));

        JLabel prompt = new JLabel("Insert credentials to continue", SwingConstants.CENTER);
        prompt.setFont(new Font("Segoe UI", Font.BOLD, 18));
        prompt.setForeground(ATM_DARK);

        userIdField = createInputField("User ID");
        pinField = new JPasswordField();
        pinField.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pinField.setHorizontalAlignment(SwingConstants.CENTER);
        pinField.setBorder(BorderFactory.createTitledBorder("PIN"));

        JButton loginButton = createButton("Login", ATM_GREEN);
        loginButton.addActionListener(event -> login());

        JLabel hint = new JLabel("Demo login: user123 / 1234", SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hint.setForeground(new Color(71, 85, 105));

        loginBox.add(prompt);
        loginBox.add(userIdField);
        loginBox.add(pinField);
        loginBox.add(loginButton);
        loginBox.add(hint);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(loginBox);
        panel.add(wrapper, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildAtmPanel() {
        JPanel outer = new JPanel(new BorderLayout(18, 18));
        outer.setBackground(ATM_DARK);
        outer.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("OASIS ATM Banking", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        outer.add(title, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(18, 18));
        body.setOpaque(false);

        JPanel screen = new RoundedPanel(ATM_SCREEN);
        screen.setLayout(new BorderLayout(12, 12));
        screen.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        balanceLabel = new JLabel("", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        balanceLabel.setForeground(new Color(20, 83, 45));

        statusLabel = new JLabel("Choose a transaction", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setForeground(new Color(22, 101, 52));

        screenArea = new JTextArea();
        screenArea.setEditable(false);
        screenArea.setLineWrap(true);
        screenArea.setWrapStyleWord(true);
        screenArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        screenArea.setBackground(new Color(240, 253, 244));
        screenArea.setForeground(new Color(20, 83, 45));
        screenArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        screen.add(balanceLabel, BorderLayout.NORTH);
        screen.add(new JScrollPane(screenArea), BorderLayout.CENTER);
        screen.add(statusLabel, BorderLayout.SOUTH);

        JPanel controls = new JPanel(new BorderLayout(12, 12));
        controls.setOpaque(false);

        JPanel fields = new JPanel(new GridLayout(2, 1, 8, 8));
        fields.setOpaque(false);
        amountField = createInputField("Amount");
        receiverField = createInputField("Receiver User ID");
        fields.add(amountField);
        fields.add(receiverField);

        JPanel actions = new JPanel(new GridLayout(5, 1, 10, 10));
        actions.setOpaque(false);

        JButton historyButton = createButton("Transaction History", ATM_BLUE);
        historyButton.addActionListener(event -> showHistory());

        JButton withdrawButton = createButton("Withdraw", new Color(245, 158, 11));
        withdrawButton.addActionListener(event -> withdraw());

        JButton depositButton = createButton("Deposit", ATM_GREEN);
        depositButton.addActionListener(event -> deposit());

        JButton transferButton = createButton("Transfer", new Color(99, 102, 241));
        transferButton.addActionListener(event -> transfer());

        JButton quitButton = createButton("Quit", ATM_RED);
        quitButton.addActionListener(event -> System.exit(0));

        actions.add(historyButton);
        actions.add(withdrawButton);
        actions.add(depositButton);
        actions.add(transferButton);
        actions.add(quitButton);

        controls.add(fields, BorderLayout.NORTH);
        controls.add(actions, BorderLayout.CENTER);

        body.add(screen, BorderLayout.CENTER);
        body.add(controls, BorderLayout.EAST);
        outer.add(body, BorderLayout.CENTER);

        return outer;
    }

    private JTextField createInputField(String title) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(260, 54));
        field.setFont(new Font("Segoe UI", Font.BOLD, 18));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        return button;
    }

    private void login() {
        String userId = userIdField.getText().trim();
        String pin = new String(pinField.getPassword()).trim();

        if (account.validateLogin(userId, pin)) {
            cardLayout.show(cards, "ATM");
            updateBalance();
            screenArea.setText("Welcome, " + userId + ".\nSelect a service from the right side panel.");
            statusLabel.setText("Login successful");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid user id or pin.", "Login Failed", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showHistory() {
        screenArea.setText(account.getTransactionHistory().toDisplayText());
        statusLabel.setText("Transaction history loaded");
    }

    private void withdraw() {
        Double amount = readAmount();
        if (amount == null) {
            return;
        }

        if (account.withdraw(amount)) {
            showSuccess("Withdrawal successful", "Withdrawn Rs. " + format(amount));
        } else {
            showError("Withdrawal failed. Check amount and available balance.");
        }
    }

    private void deposit() {
        Double amount = readAmount();
        if (amount == null) {
            return;
        }

        if (account.deposit(amount)) {
            showSuccess("Deposit successful", "Deposited Rs. " + format(amount));
        } else {
            showError("Deposit failed. Amount must be greater than zero.");
        }
    }

    private void transfer() {
        String receiverUserId = receiverField.getText().trim();
        Double amount = readAmount();
        if (amount == null) {
            return;
        }

        if (account.transfer(receiverUserId, amount)) {
            showSuccess("Transfer successful", "Sent Rs. " + format(amount) + " to " + receiverUserId);
            receiverField.setText("");
        } else {
            showError("Transfer failed. Check receiver id, amount, and balance.");
        }
    }

    private Double readAmount() {
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            return amount;
        } catch (NumberFormatException ex) {
            showError("Please enter a valid amount.");
            return null;
        }
    }

    private void showSuccess(String status, String details) {
        updateBalance();
        screenArea.setText(details + "\n\nUpdated balance: Rs. " + format(account.getBalance()));
        statusLabel.setText(status);
        amountField.setText("");
    }

    private void showError(String message) {
        screenArea.setText(message);
        statusLabel.setText("Action failed");
    }

    private void updateBalance() {
        balanceLabel.setText("Available Balance: Rs. " + format(account.getBalance()));
    }

    private String format(double amount) {
        return String.format("%.2f", amount);
    }

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0, 0, new Color(2, 6, 23), getWidth(), getHeight(), new Color(37, 99, 235)));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    private static class RoundedPanel extends JPanel {
        private final Color color;

        RoundedPanel(Color color) {
            this.color = color;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
            g2.dispose();
            super.paintComponent(graphics);
        }
    }
}
