package Task4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task4_GUI {
    public static void main(String[] args) {
        // Create a bank account with an initial balance
        BankAccount userAccount = new BankAccount(1000.0);

        // Create an ATM instance and pass the user's bank account
        ATM atm = new ATM(userAccount);

        // Create the GUI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ATM");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            JPanel panel = new JPanel(new GridLayout(4, 1));

            JButton withdrawButton = new JButton("Withdraw");
            withdrawButton.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
                if (input != null) {
                    try {
                        double amount = Double.parseDouble(input);
                        if (amount > 0) {
                            if (atm.checkBalance() >= amount) {
                                atm.withdraw(amount);
                                JOptionPane.showMessageDialog(frame, "Withdrawal successful!");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid amount. Amount must be positive.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
                    }
                }
            });
            panel.add(withdrawButton);

            JButton depositButton = new JButton("Deposit");
            depositButton.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
                if (input != null) {
                    try {
                        double amount = Double.parseDouble(input);
                        if (amount > 0) {
                            atm.deposit(amount);
                            JOptionPane.showMessageDialog(frame, "Deposit successful!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid amount. Amount must be positive.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
                    }
                }
            });
            panel.add(depositButton);

            JButton checkBalanceButton = new JButton("Check Balance");
            checkBalanceButton.addActionListener(e -> {
                double balance = atm.checkBalance();
                JOptionPane.showMessageDialog(frame, "Balance: Rs:" + balance);
            });
            panel.add(checkBalanceButton);

            JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "Thank you for using the ATM. Goodbye!");
                System.exit(0);
            });
            panel.add(exitButton);

            frame.getContentPane().add(panel);
            frame.setVisible(true);
        });
    }
}
