package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {

    public void recordTransaction(int sender, int receiver, double amount, String status) {

        try (Connection con = DBConnection.getConnection()) {
            recordTransaction(con, sender, receiver, amount, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recordTransaction(Connection con, int sender, int receiver, double amount, String status) throws SQLException {

        String query = "INSERT INTO transactions(sender_id,receiver_id,amount,status) VALUES(?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, sender);
            ps.setInt(2, receiver);
            ps.setDouble(3, amount);
            ps.setString(4, status);
            ps.executeUpdate();
        }
    }

    public void showTransactions(int userId) {

        String query = "SELECT * FROM transactions WHERE sender_id=? OR receiver_id=? ORDER BY date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    System.out.println(
                            "Transaction ID: " + rs.getInt("id") +
                                    " | Sender: " + rs.getInt("sender_id") +
                                    " | Receiver: " + rs.getInt("receiver_id") +
                                    " | Amount: " + rs.getDouble("amount") +
                                    " | Status: " + rs.getString("status") +
                                    " | Date: " + rs.getTimestamp("date")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
