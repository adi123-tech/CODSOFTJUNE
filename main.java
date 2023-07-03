package Task4;

public class main {
	 public static void main(String[] args) {
	        // Create a BankAccount object with an initial balance of 1000
	        BankAccount bankAccount = new BankAccount(1000);

	        // Create an ATM object with the BankAccount object
	        ATM atm = new ATM(bankAccount);

	        // Perform transactions using the ATM
	        System.out.println("Current balance: " + atm.checkBalance());
	        System.out.println(atm.withdraw(500));
	        System.out.println(atm.deposit(200));
	        System.out.println(atm.withdraw(800));
	        System.out.println("Current balance: " + atm.checkBalance());
	    }
}
