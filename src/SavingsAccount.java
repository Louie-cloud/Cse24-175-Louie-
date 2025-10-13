import interfaces.InterestBearing;

public class SavingsAccount extends Account implements InterestBearing {
    private static final double MONTHLY_INTEREST_RATE = 0.0005; // 0.05%

    public SavingsAccount(Customer customer, double initialDeposit) {
        super(customer, initialDeposit);
    }

    public SavingsAccount(Customer customer, double initialDeposit, String branch) {
        super(customer, initialDeposit, branch);
    }

    // Overriding withdraw method - no withdrawals allowed (Polymorphism)
    @Override
    public void withdraw(double amount) {
        System.out.println("Operation failed: Withdrawals are not allowed from Savings Account!");
    }

    // Implementing InterestBearing interface
    @Override
    public double calculateMonthlyInterest() {
        return getBalance() * MONTHLY_INTEREST_RATE;
    }

    @Override
    public void applyMonthlyInterest() {
        double interest = calculateMonthlyInterest();
        setBalance(getBalance() + interest);
        System.out.println("Interest of BWP " + String.format("%.2f", interest) +
                " applied to Savings Account " + getAccountNumber());
    }

    @Override
    public String toString() {
        return super.toString() + " [Type: Savings, Interest Rate: 0.05% monthly]";
    }
}
