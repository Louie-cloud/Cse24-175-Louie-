public class Main {
    public static void main(String[] args) {
        BankService bankService = new BankService();

        System.out.println("=== BANKING SYSTEM DEMONSTRATION ===\n");

        // Register customers (demonstrating method overloading)
        Customer customer1 = bankService.registerCustomer("John", "Doe", "123 Main St");
        Customer customer2 = bankService.registerCustomer("Jane", "Smith", "456 Elm St",
                "Tech Corp", "789 Innovation Blvd");

        System.out.println("\n--- Opening Accounts ---");

        // Customer 1 opens multiple accounts
        bankService.openSavingsAccount(customer1, 1000.00);
        bankService.openInvestmentAccount(customer1, 600.00); // Valid - meets minimum

        // Customer 2 opens multiple accounts
        bankService.openInvestmentAccount(customer2, 450.00); // Warning - below minimum
        bankService.openChequeAccount(customer2, 100.00);

        System.out.println("\n--- Transaction Demonstrations ---");

        // Get accounts and demonstrate transactions
        Account johnSavings = customer1.getAccounts().get(0);
        Account johnInvestment = customer1.getAccounts().get(1);

        // Deposits
        johnSavings.deposit(500.00);
        johnInvestment.deposit(200.00);

        // Withdrawals
        johnSavings.withdraw(100.00); // Should fail - no withdrawals from savings
        johnInvestment.withdraw(150.00); // Should succeed

        System.out.println("\n--- Monthly Interest Application ---");

        // Apply monthly interest (demonstrates interface implementation)
        bankService.applyMonthlyInterestToAll();

        System.out.println("\n--- Final Account Status ---");

        // Display all customer information
        bankService.displayAllCustomers();
        customer1.displayAllAccounts();
        customer2.displayAllAccounts();

        // Demonstrate polymorphism
        System.out.println("\n--- Polymorphism Demonstration ---");
        for (Customer customer : bankService.getCustomers()) {
            for (Account account : customer.getAccounts()) {
                System.out.println("Account: " + account.getAccountNumber() +
                        ", Type: " + account.getClass().getSimpleName() +
                        ", Balance: BWP " + account.getBalance());

                // This will call the appropriate withdraw method for each account type
                account.withdraw(50.00); // Polymorphic call
            }
        }
    }
}
