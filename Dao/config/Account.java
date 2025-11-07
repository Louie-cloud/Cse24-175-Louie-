package dao;

import models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends BaseDAO {

    // CREATE - Insert new account
    public boolean insertAccount(Account account) {
        String sql = "INSERT INTO accounts (account_number, customer_id, account_type, balance, branch, date_opened, interest_rate, min_balance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            String accountType = getAccountType(account);
            double interestRate = getInterestRate(account);
            double minBalance = getMinBalance(account);

            setParameters(stmt,
                    account.getAccountNumber(),
                    account.getCustomer().getCustomerId(),
                    accountType,
                    account.getBalance(),
                    account.getBranch(),
                    java.time.LocalDate.now(),
                    interestRate,
                    minBalance
            );

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting account: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt);
        }
    }

    // READ - Find account by number
    public Account findAccountByNumber(String accountNumber) {
        String sql = "SELECT a.*, c.* FROM accounts a JOIN customers c ON a.customer_id = c.customer_id WHERE a.account_number = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAccount(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error finding account by number: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return null;
    }

    // READ - Find accounts by customer ID
    public List<Account> findAccountsByCustomerId(String customerId) {
        String sql = "SELECT a.*, c.* FROM accounts a JOIN customers c ON a.customer_id = c.customer_id WHERE a.customer_id = ? AND a.is_active = TRUE";
        List<Account> accounts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding accounts by customer ID: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return accounts;
    }

    // READ - Find all accounts
    public List<Account> findAllAccounts() {
        String sql = "SELECT a.*, c.* FROM accounts a JOIN customers c ON a.customer_id = c.customer_id WHERE a.is_active = TRUE ORDER BY a.date_opened DESC";
        List<Account> accounts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all accounts: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return accounts;
    }

    // UPDATE - Update account balance
    public boolean updateAccountBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating account balance: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt);
        }
    }

    // UPDATE - Deactivate account
    public boolean deactivateAccount(String accountNumber) {
        String sql = "UPDATE accounts SET is_active = FALSE WHERE account_number = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deactivating account: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt);
        }
    }

    // Helper methods for account type mapping
    private String getAccountType(Account account) {
        if (account instanceof SavingsAccount) return "SAVINGS";
        if (account instanceof InvestmentAccount) return "INVESTMENT";
        if (account instanceof ChequeAccount) return "CHEQUE";
        return "UNKNOWN";
    }

    private double getInterestRate(Account account) {
        if (account instanceof SavingsAccount) return 0.0005;
        if (account instanceof InvestmentAccount) return 0.05;
        return 0.0;
    }

    private double getMinBalance(Account account) {
        if (account instanceof InvestmentAccount) return 500.00;
        return 0.00;
    }

    // Map ResultSet to Account object
    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Customer customer = createCustomerFromResultSet(rs);
        String accountNumber = rs.getString("account_number");
        String accountType = rs.getString("account_type");
        double balance = rs.getDouble("balance");
        String branch = rs.getString("branch");

        return switch (accountType) {
            case "SAVINGS" -> new SavingsAccount(customer, balance, branch) {
                @Override public String getAccountNumber() { return accountNumber; }
            };
            case "INVESTMENT" -> new InvestmentAccount(customer, balance, branch) {
                @Override public String getAccountNumber() { return accountNumber; }
            };
            case "CHEQUE" -> new ChequeAccount(customer, balance, branch) {
                @Override public String getAccountNumber() { return accountNumber; }
            };
            default -> throw new IllegalArgumentException("Unknown account type: " + accountType);
        };
    }

    private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
        String customerId = rs.getString("customer_id");
        String firstname = rs.getString("firstname");
        String surname = rs.getString("surname");
        String address = rs.getString("address");
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phone_number");
        String companyName = rs.getString("company_name");
        String companyAddress = rs.getString("company_address");

        if (companyName != null && !companyName.trim().isEmpty()) {
            return new Customer(firstname, surname, address, email, phoneNumber, companyName, companyAddress) {
                @Override public String getCustomerId() { return customerId; }
            };
        } else {
            return new Customer(firstname, surname, address, email, phoneNumber) {
                @Override public String getCustomerId() { return customerId; }
            };
        }
    }

    // Statistics methods
    public double getTotalBalance() {
        String sql = "SELECT SUM(balance) as total FROM accounts WHERE is_active = TRUE";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting total balance: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return 0.0;
    }

    public int countAccounts() {
        String sql = "SELECT COUNT(*) FROM accounts WHERE is_active = TRUE";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error counting accounts: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return 0;
    }
}