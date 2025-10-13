import interfaces.InterestBearing;

final class InvestmentAccount extends Account implements InterestBearing {
    private static final double MONTHLY_INTEREST_RATE = 0.05; // 5%
    private static final double MIN_INITIAL_DEPOSIT = 500.00;

    public InvestmentAccount(Customer customer, double initialDeposit) {
        super(customer, initialDeposit);
        if (initialDeposit < MIN_INITIAL_DEPOSIT) {
            System.out.println("Warning: Investment Account requires minimum BWP " +
                    MIN_INITIAL_DEPOSIT + " initial deposit");
        }
    }

    public InvestmentAccount(Customer customer, double initialDeposit, String branch) {
        super(customer, initialDeposit, branch);
        if (initialDeposit < MIN_INITIAL_DEPOSIT) {
            System.out.println("Warning: Investment Account requires minimum BWP " +
                    MIN_INITIAL_DEPOSIT + " initial deposit");
        }
    }

    // Overriding withdraw method - withdrawals allowed with sufficient funds
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
                " applied to Investment Account " + getAccountNumber());
    }

    @Override
    public String toString() {
        return super.toString() + " [Type: Investment, Interest Rate: 5% monthly]";
    }
}