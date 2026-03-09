package dao;

import com.sun.source.tree.BreakTree;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public void registerUser(String name, String email, String password) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO users(name,email,password,balance) VALUES(?,?,?,0)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            System.out.println("User registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int loginUser(String email, String password) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT id FROM users WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public double getBalance(int userId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT balance FROM users WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void updateBalance(int userId, double balance) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE users SET balance=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, balance);
            ps.setInt(2, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUserIdByEmail(String email) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT id FROM users WHERE email=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    }
