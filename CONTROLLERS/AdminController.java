package CONTROLLERS;

import models.Customer;
import services.BankService;

public class AdminController {
    private BankService bankService;

    public AdminController(BankService bankService) {
        this.bankService = bankService;
    }

    // System statistics
    public SystemStatistics getSystemStatistics() {
        int totalCustomers = bankService.getTotalCustomers();
        long totalAccounts = bankService.getTotalAccounts();
        double totalBalance = bankService.getTotalBalance();
        int totalAdmins = bankService.getAdmins().size();

        return new SystemStatistics(totalCustomers, totalAccounts, totalBalance, totalAdmins);
    }

    // Customer reports
    public String generateCustomerReport(Customer customer) {
        if (customer == null) {
            return "No customer selected";
        }

        StringBuilder report = new StringBuilder();
        report.append("=== CUSTOMER REPORT ===\n");
        report.append("Customer: ").append(customer.getFirstname()).append(" ").append(customer.getSurname()).append("\n");
        report.append("Customer ID: ").append(customer.getCustomerId()).append("\n");
        report.append("Email: ").append(customer.getEmail()).append("\n");
        report.append("Phone: ").append(customer.getPhoneNumber()).append("\n");
        report.append("Address: ").append(customer.getAddress()).append("\n");
        report.append("Employment Status: ").append(customer.isEmployed() ? "Employed" : "Not Employed").append("\n");

        if (customer.isEmployed()) {
            report.append("Company: ").append(customer.getCompanyName()).append("\n");
            report.append("Company Address: ").append(customer.getCompanyAddress()).append("\n");
        }

        report.append("Number of Accounts: ").append(customer.getAccounts().size()).append("\n");
        report.append("Total Balance: BWP ").append(String.format("%.2f", customer.getTotalBalance())).append("\n");
        report.append("\nAccounts:\n");

        for (models.Account account : customer.getAccounts()) {
            report.append("  - ").append(account.getAccountNumber())
                    .append(" (").append(account.getClass().getSimpleName().replace("Account", ""))
                    .append("): BWP ").append(String.format("%.2f", account.getBalance())).append("\n");
        }

        return report.toString();
    }

    // Data class for system statistics
    public static class SystemStatistics {
        public final int totalCustomers;
        public final long totalAccounts;
        public final double totalBalance;
        public final int totalAdmins;

        public SystemStatistics(int totalCustomers, long totalAccounts, double totalBalance, int totalAdmins) {
            this.totalCustomers = totalCustomers;
            this.totalAccounts = totalAccounts;
            this.totalBalance = totalBalance;
            this.totalAdmins = totalAdmins;
        }
    }
}