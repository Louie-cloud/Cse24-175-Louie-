package CONTROLLERS;

import models.Account;
import models.Customer;
import models.SavingsAccount;
import models.InvestmentAccount;
import models.ChequeAccount;
import services.BankService;

public class AccountController {
    private BankService bankService;

    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }

    // Account creation
    public boolean openSavingsAccount(Customer customer, double initialDeposit) {
        if (customer == null || initialDeposit < 0) {
            return false;
        }
        try {
            bankService.openSavingsAccount(customer, initialDeposit);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean openInvestmentAccount(Customer customer, double initialDeposit) {
        if (customer == null || initialDeposit < 0) {
            return false;
        }
        try {
            bankService.openInvestmentAccount(customer, initialDeposit);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean openChequeAccount(Customer customer, double initialDeposit) {
        if (customer == null || initialDeposit < 0) {
            return false;
        }
        try {
            bankService.openChequeAccount(customer, initialDeposit);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Account operations
    public boolean deposit(Account account, double amount) {
        if (account == null || amount <= 0) {
            return false;
        }
        try {
            account.deposit(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean withdraw(Account account, double amount) {
        if (account == null || amount <= 0) {
            return false;
        }
        try {
            account.withdraw(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Validation methods
    public boolean validateDepositAmount(double amount) {
        return amount > 0;
    }

    public boolean validateWithdrawalAmount(Account account, double amount) {
        if (account == null || amount <= 0) {
            return false;
        }

        // Check if account allows withdrawals
        if (account instanceof SavingsAccount) {
            return false;
        }

        // Check sufficient funds
        return amount <= account.getBalance();
    }

    public boolean validateInitialDeposit(String accountType, double amount) {
        if (amount < 0) return false;

        if ("Investment Account".equals(accountType)) {
            return amount >= 500.00;
        }
        return true;
    }

    // Interest application
    public void applyMonthlyInterest() {
        bankService.applyMonthlyInterestToAll();
    }
}