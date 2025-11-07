package dao;

import interfaces.Admin;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO extends BaseDAO {

    // READ - Find admin by username
    public Admin findAdminByUsername(String username) {
        String sql = "SELECT * FROM admins WHERE username = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAdmin(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error finding admin by username: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return null;
    }

    // READ - Find all admins
    public List<Admin> findAllAdmins() {
        String sql = "SELECT * FROM admins ORDER BY created_date DESC";
        List<Admin> admins = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error finding all admins: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return admins;
    }

    // UPDATE - Update last login
    public boolean updateLastLogin(String adminId) {
        String sql = "UPDATE admins SET last_login = ? WHERE admin_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, adminId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating last login: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt);
        }
    }

    // Helper method to map ResultSet to Admin object
    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        String adminId = rs.getString("admin_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstname = rs.getString("firstname");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String department = rs.getString("department");
        String position = rs.getString("position");
        Timestamp lastLogin = rs.getTimestamp("last_login");

        return new Admin(username, password, firstname, surname, email, department, position) {
            @Override public String getAdminId() { return adminId; }
            @Override public LocalDateTime getLastLogin() {
                return lastLogin != null ? lastLogin.toLocalDateTime() : null;
            }
        };
    }
}