import interfaces.InterestBearing;

import java.util.ArrayList;
import java.util.List;

public class BankService {
    private List<Customer> customers;

    public BankService() {
        this.customers = new ArrayList<>();
    }

    // Method overloading for customer registration
    public Customer registerCustomer(String firstname, String surname, String address) {
        Customer customer = new Customer(firstname, surname, address);
        customers.add(customer);
        System.out.println("Customer registered: " + customer.getCustomerId());
        return customer;
    }

    public Customer registerCustomer(String firstname, String surname, String address,
                                     String companyName, String companyAddress) {
        Customer customer = new Customer(firstname, surname, address, companyName, companyAddress);
        customers.add(customer);
        System.out.println("Employed customer registered: " + customer.getCustomerId());
        return customer;
    }

    // Method to open different account types (demonstrates polymorphism)
    public void openSavingsAccount(Customer customer, double initialDeposit) {
        SavingsAccount account = new SavingsAccount(customer, initialDeposit);
        customer.openAccount(account);
    }

    public void openInvestmentAccount(Customer customer, double initialDeposit) {
        InvestmentAccount account = new InvestmentAccount(customer, initialDeposit);
        customer.openAccount(account);
    }

    public void openChequeAccount(Customer customer, double initialDeposit) {
        ChequeAccount account = new ChequeAccount(customer, initialDeposit);
        customer.openAccount(account);
    }

    // Method to apply monthly interest to all interest-bearing accounts
    public void applyMonthlyInterestToAll() {
        System.out.println("\n--- Applying Monthly Interest to All Accounts ---");
        for (Customer customer : customers) {
            for (Account account : customer.getAccounts()) {
                if (account instanceof InterestBearing) {
                    ((InterestBearing) account).applyMonthlyInterest();
                }
            }
        }
    }

    // Utility methods
    public void displayAllCustomers() {
        System.out.println("\n--- All Registered Customers ---");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }
}
