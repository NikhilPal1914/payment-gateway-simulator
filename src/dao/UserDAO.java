package dao;

import util.DBConnection;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public void registerUser(String name, String email, String password) {

        String sql = "INSERT INTO users(name,email,password,balance) VALUES(?,?,?,0)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, PasswordUtil.hashPassword(password));

            ps.executeUpdate();

            System.out.println("User registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int loginUser(String email, String password) {

        String sql = "SELECT id, password FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String storedPassword = rs.getString("password");

                    if (passwordMatches(password, storedPassword)) {
                        upgradeLegacyPassword(con, userId, password, storedPassword);
                        return userId;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public double getBalance(int userId) {

        try (Connection con = DBConnection.getConnection()) {
            return getBalance(con, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getBalance(Connection con, int userId) throws SQLException {

        return getBalance(con, userId, false);
    }

    public double getBalanceForUpdate(Connection con, int userId) throws SQLException {

        return getBalance(con, userId, true);
    }

    private double getBalance(Connection con, int userId, boolean forUpdate) throws SQLException {

        String sql = "SELECT balance FROM users WHERE id=?";
        if (forUpdate) {
            sql += " FOR UPDATE";
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        }

        return 0;
    }

    public void updateBalance(int userId, double balance) {

        try (Connection con = DBConnection.getConnection()) {
            updateBalance(con, userId, balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBalance(Connection con, int userId, double balance) throws SQLException {

        String sql = "UPDATE users SET balance=? WHERE id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, balance);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public int getUserIdByEmail(String email) {

        String sql = "SELECT id FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    private boolean passwordMatches(String password, String storedPassword) {

        if (storedPassword == null || storedPassword.trim().isEmpty()) {
            return false;
        }

        if (storedPassword.contains(":")) {
            return PasswordUtil.matches(password, storedPassword);
        }

        return storedPassword.equals(password);
    }

    private void upgradeLegacyPassword(Connection con, int userId, String password, String storedPassword) throws SQLException {

        if (!storedPassword.contains(":")) {
            String passwordHash = PasswordUtil.hashPassword(password);
            String sql = "UPDATE users SET password=? WHERE id=?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, passwordHash);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }
        }
    }
}
