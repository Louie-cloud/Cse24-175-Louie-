package CONTROLLERS;

import interfaces.Admin;
import models.Customer;
import services.BankService;

public class AuthController {
    private BankService bankService;
    private Admin currentAdmin;
    private Customer currentCustomer;

    public AuthController(BankService bankService) {
        this.bankService = bankService;
    }

    // Admin authentication
    public boolean loginAdmin(String username, String password) {
        Admin admin = bankService.authenticateAdmin(username, password);
        if (admin != null) {
            this.currentAdmin = admin;
            return true;
        }
        return false;
    }

    // Customer authentication (for demo, any customer works)
    public boolean loginCustomer() {
        var customers = bankService.getCustomers();
        if (!customers.isEmpty()) {
            this.currentCustomer = customers.get(0);
            return true;
        }
        return false;
    }

    public void logout() {
        this.currentAdmin = null;
        this.currentCustomer = null;
    }

    // Getters
    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public boolean isAdminLoggedIn() {
        return currentAdmin != null;
    }

    public boolean isCustomerLoggedIn() {
        return currentCustomer != null;
    }
}