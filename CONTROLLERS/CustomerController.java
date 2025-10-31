package CONTROLLERS;

import models.Customer;
import services.BankService;

public class CustomerController {
    private BankService bankService;

    public CustomerController(BankService bankService) {
        this.bankService = bankService;
    }

    // Customer registration
    public Customer registerCustomer(String firstname, String surname, String address,
                                     String email, String phoneNumber) {
        return bankService.registerCustomer(firstname, surname, address, email, phoneNumber);
    }

    public Customer registerCustomer(String firstname, String surname, String address,
                                     String email, String phoneNumber,
                                     String companyName, String companyAddress) {
        return bankService.registerCustomer(firstname, surname, address, email, phoneNumber,
                companyName, companyAddress);
    }

    // Customer retrieval
    public java.util.List<Customer> getAllCustomers() {
        return bankService.getCustomers();
    }

    public Customer getCustomerById(String customerId) {
        return bankService.getCustomers().stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    // Validation methods
    public boolean validateCustomerData(String firstname, String surname, String email,
                                        String phone, String address) {
        return firstname != null && !firstname.trim().isEmpty() &&
                surname != null && !surname.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                phone != null && !phone.trim().isEmpty() &&
                address != null && !address.trim().isEmpty();
    }

    public boolean validateCustomerData(String firstname, String surname, String email,
                                        String phone, String address,
                                        String companyName, String companyAddress) {
        return validateCustomerData(firstname, surname, email, phone, address) &&
                companyName != null && !companyName.trim().isEmpty() &&
                companyAddress != null && !companyAddress.trim().isEmpty();
    }
}