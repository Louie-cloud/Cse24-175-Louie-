package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends BaseDAO {

    // CREATE - Record transaction
    public boolean recordTransaction(String accountNumber, String transactionType,
                                     double amount, double balanceAfter, String description) {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, balance_after, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            setParameters(stmt,
                    accountNumber,
                    transactionType,
                    amount,
                    balanceAfter,
                    description
            );

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error recording transaction: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt);
        }
    }

    // READ - Get transactions for account
    public List<TransactionRecord> getTransactionsByAccount(String accountNumber, int limit) {
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC LIMIT ?";
        List<TransactionRecord> transactions = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            stmt.setInt(2, limit);
            rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting transactions by account: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return transactions;
    }

    // READ - Get all transactions
    public List<TransactionRecord> getAllTransactions(int limit) {
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT ?";
        List<TransactionRecord> transactions = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all transactions: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return transactions;
    }

    // Helper method to map ResultSet to TransactionRecord
    private TransactionRecord mapResultSetToTransaction(ResultSet rs) throws SQLException {
        return new TransactionRecord(
                rs.getLong("transaction_id"),
                rs.getString("account_number"),
                rs.getString("transaction_type"),
                rs.getDouble("amount"),
                rs.getDouble("balance_after"),
                rs.getString("description"),
                rs.getTimestamp("transaction_date").toLocalDateTime()
        );
    }

    // Data class for transaction records
    public static class TransactionRecord {
        private final long transactionId;
        private final String accountNumber;
        private final String transactionType;
        private final double amount;
        private final double balanceAfter;
        private final String description;
        private final LocalDateTime transactionDate;

        public TransactionRecord(long transactionId, String accountNumber, String transactionType,
                                 double amount, double balanceAfter, String description,
                                 LocalDateTime transactionDate) {
            this.transactionId = transactionId;
            this.accountNumber = accountNumber;
            this.transactionType = transactionType;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.description = description;
            this.transactionDate = transactionDate;
        }

        // Getters
        public long getTransactionId() { return transactionId; }
        public String getAccountNumber() { return accountNumber; }
        public String getTransactionType() { return transactionType; }
        public double getAmount() { return amount; }
        public double getBalanceAfter() { return balanceAfter; }
        public String getDescription() { return description; }
        public LocalDateTime getTransactionDate() { return transactionDate; }

        @Override
        public String toString() {
            return String.format("%s: %s - BWP %.2f (Balance: BWP %.2f)",
                    transactionDate.toLocalDate(),
                    transactionType,
                    amount,
                    balanceAfter);
        }
    }
}