package dao;

import config.DatabaseConfig;
import java.sql.*;

public abstract class BaseDAO {

    protected Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }

    protected void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing database resources: " + e.getMessage());
        }
    }

    protected void closeResources(Connection conn, PreparedStatement stmt) {
        closeResources(conn, stmt, null);
    }

    protected void setParameters(PreparedStatement stmt, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof java.time.LocalDate) {
                stmt.setDate(i + 1, Date.valueOf((java.time.LocalDate) parameters[i]));
            } else {
                stmt.setObject(i + 1, parameters[i]);
            }
        }
    }
}