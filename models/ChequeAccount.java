package models;

public class ChequeAccount extends models.Account {

    public ChequeAccount(models.Customer customer, double initialDeposit) {
        super(customer, initialDeposit);
        if (!customer.isEmployed()) {
            System.out.println("Warning: Cheque Account typically requires employment information");
        }
    }

    public ChequeAccount(models.Customer customer, double initialDeposit, String branch) {
        super(customer, initialDeposit, branch);
        if (!customer.isEmployed()) {
            System.out.println("Warning: Cheque Account typically requires employment information");
        }
    }

    // Overriding withdraw method - unlimited withdrawals allowed
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        } else if (amount > getBalance()) {
            System.out.println("Insufficient funds! Available balance: BWP " + getBalance());
        } else {
            setBalance(getBalance() - amount);
            System.out.println("Successfully withdrew: BWP " + amount +
                    ". New balance: BWP " + getBalance());
        }
    }

    @Override
    public String toString() {
        return super.toString() + " [Type: Cheque, No Interest]";
    }
}
