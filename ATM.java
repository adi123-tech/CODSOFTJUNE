package Task4;

public class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String withdraw(double amount) {
        if (bankAccount.getBalance() >= amount) {
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            return "Withdrew " + amount + " from your account. New balance: " + bankAccount.getBalance();
        } else {
            return "Insufficient balance.";
        }
    }

    public String deposit(double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        return "Deposited " + amount + " into your account. New balance: " + bankAccount.getBalance();
    }

    public double checkBalance() {
        return bankAccount.getBalance();
    }

}

