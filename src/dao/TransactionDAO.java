package dao;

import util.DBConnection;

import java.sql.*;

public class TransactionDAO {

    public void recordTransaction(int sender, int receiver, double amount, String status) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO transactions(sender_id,receiver_id,amount,status) VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, sender);
            ps.setInt(2, receiver);
            ps.setDouble(3, amount);
            ps.setString(4, status);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTransactions(int userId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM transactions WHERE sender_id=? OR receiver_id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
