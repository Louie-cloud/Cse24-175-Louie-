package models;

public class Account {
    // Encapsulated attributes
    private String accountNumber;
    private double balance;
    private Customer customer;
    private String branch;

    // Static variable for generating account numbers (demonstrates static concept)
    private static int accountCounter = 1000;

    // Constructor demonstrating method overloading
    public Account(Customer customer, double initialDeposit) {
        this.accountNumber = "ACC" + (++accountCounter);
        this.balance = initialDeposit;
        this.customer = customer;
        this.branch = "Main Branch";
    }

    public Account(Customer customer, double initialDeposit, String branch) {
        this(customer, initialDeposit); // Constructor chaining
        this.branch = branch;
    }

    // Abstract method - enforcing implementation in subclasses (Abstraction)
    public void withdraw(double amount) {

    }

    // Concrete method - common to all accounts
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: BWP " + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Getters and Setters (Encapsulation)
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    void setBalance(double balance) { this.balance = balance; }
    public Customer getCustomer() { return customer; }
    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    // Overriding toString method from Object class (Polymorphism)
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", branch='" + branch + '\'' +
                '}';
    }
}