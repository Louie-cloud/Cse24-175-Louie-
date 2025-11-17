package models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    // Encapsulated attributes
    private String customerId;
    private String firstname;
    private String surname;
    private String address;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String companyAddress;
    private List<models.Account> accounts; // Composition - accounts cannot exist without customer

    // Static counter for generating customer IDs
    private static int customerCounter = 100;

    // Constructors demonstrating method overloading
    public Customer(String firstname, String surname, String address, String email, String phoneNumber) {
        this.customerId = "CUST" + (++customerCounter);
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accounts = new ArrayList<>();
    }

    // Overloaded constructor for employed customers
    public Customer(String firstname, String surname, String address, String email, String phoneNumber,
                    String companyName, String companyAddress) {
        this(firstname, surname, address, email, phoneNumber);
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    // Business methods
    public void openAccount(models.Account account) {
        accounts.add(account);
        System.out.println("Account " + account.getAccountNumber() + " opened successfully!");
    }

    public void displayAllAccounts() {
        System.out.println("\n--- Accounts for " + firstname + " " + surname + " ---");
        for (models.Account account : accounts) {
            System.out.println(account);
        }
    }

    public double getTotalBalance() {
        return accounts.stream()
                .mapToDouble(models.Account::getBalance)
                .sum();
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public String getFirstname() { return firstname; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCompanyName() { return companyName; }
    public String getCompanyAddress() { return companyAddress; }
    public List<models.Account> getAccounts() { return new ArrayList<>(accounts); }

    public boolean isEmployed() {
        return companyName != null && !companyName.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", numberOfAccounts=" + accounts.size() +
                ", totalBalance=BWP " + String.format("%.2f", getTotalBalance()) +
                '}';
    }
}