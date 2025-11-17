package services;

import interfaces.Admin;
import models.*;
import interfaces.InterestBearing;
import java.util.ArrayList;
import java.util.List;

public class BankService {
    private List<Customer> customers;
    private List<Admin> admins;

    public BankService() {
        this.customers = new ArrayList<>();
        this.admins = new ArrayList<>();
        initializeDefaultAdmins();
    }

    private void initializeDefaultAdmins() {
        // Create default admin accounts
        Admin admin1 = new Admin("admin", "admin123", "System", "Administrator",
                "admin@bank.com", "IT", "System Admin");
        Admin admin2 = new Admin("manager", "manager123", "Branch", "Manager",
                "manager@bank.com", "Operations", "Branch Manager");
        admins.add(admin1);
        admins.add(admin2);
    }

    // Customer management methods
    public Customer registerCustomer(String firstname, String surname, String address,
                                     String email, String phoneNumber) {
        Customer customer = new Customer(firstname, surname, address, email, phoneNumber);
        customers.add(customer);
        System.out.println("Customer registered successfully: " + customer.getCustomerId());
        return customer;
    }

    public Customer registerCustomer(String firstname, String surname, String address,
                                     String email, String phoneNumber, String companyName, String companyAddress) {
        Customer customer = new Customer(firstname, surname, address, email, phoneNumber, companyName, companyAddress);
        customers.add(customer);
        System.out.println("Employed customer registered successfully: " + customer.getCustomerId());
        return customer;
    }

    // Admin authentication
    public Admin authenticateAdmin(String username, String password) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.authenticate(password)) {
                admin.recordLogin();
                return admin;
            }
        }
        return null;
    }

    // Account management methods (demonstrates polymorphism)
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
        int interestAppliedCount = 0;
        double totalInterest = 0;

        for (Customer customer : customers) {
            for (Account account : customer.getAccounts()) {
                if (account instanceof InterestBearing) {
                    double interest = ((InterestBearing) account).calculateMonthlyInterest();
                    ((InterestBearing) account).applyMonthlyInterest();
                    interestAppliedCount++;
                    totalInterest += interest;
                }
            }
        }

        System.out.println("Interest applied to " + interestAppliedCount + " accounts. Total interest: BWP " +
                String.format("%.2f", totalInterest));
    }

    // Utility methods
    public void displayAllCustomers() {
        System.out.println("\n--- All Registered Customers ---");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public void displayAllAdmins() {
        System.out.println("\n--- All System Admins ---");
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Admin> getAdmins() {
        return new ArrayList<>(admins);
    }

    // Statistics methods
    public int getTotalCustomers() {
        return customers.size();
    }

    public long getTotalAccounts() {
        return customers.stream()
                .mapToLong(customer -> customer.getAccounts().size())
                .sum();
    }

    public double getTotalBalance() {
        return customers.stream()
                .flatMap(customer -> customer.getAccounts().stream())
                .mapToDouble(Account::getBalance)
                .sum();
    }
}