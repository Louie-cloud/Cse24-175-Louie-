import java.util.ArrayList;
import java.util.List;

public class Customer {
    // Encapsulated attributes
    private String customerId;
    private String firstname;
    private String surname;
    private String address;
    private String companyName;
    private String companyAddress;
    private List<Account> accounts; // Composition - accounts cannot exist without customer

    // Static counter for generating customer IDs
    private static int customerCounter = 100;

    // Constructors demonstrating method overloading
    public Customer(String firstname, String surname, String address) {
        this.customerId = "CUST" + (++customerCounter);
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    // Overloaded constructor for employed customers
    public Customer(String firstname, String surname, String address,
                    String companyName, String companyAddress) {
        this(firstname, surname, address); // Constructor chaining
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    // Business methods
    public void openAccount(Account account) {
        accounts.add(account);
        System.out.println("Account " + account.getAccountNumber() + " opened successfully!");
    }

    public void displayAllAccounts() {
        System.out.println("\n--- Accounts for " + firstname + " " + surname + " ---");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public String getFirstname() { return firstname; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public String getCompanyName() { return companyName; }
    public String getCompanyAddress() { return companyAddress; }
    public List<Account> getAccounts() { return new ArrayList<>(accounts); } // Defensive copy

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
                ", companyName='" + companyName + '\'' +
                ", numberOfAccounts=" + accounts.size() +
                '}';
    }
}
