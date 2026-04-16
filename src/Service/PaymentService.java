package Service;

import dao.TransactionDAO;
import dao.UserDAO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentService {

    private final UserDAO userDAO = new UserDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public void addMoney(int userId, double amount) {

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                double balance = userDAO.getBalanceForUpdate(con, userId);
                userDAO.updateBalance(con, userId, balance + amount);
                con.commit();
                System.out.println("Money added successfully");
            } catch (SQLException e) {
                rollbackQuietly(con);
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMoney(int senderId, int receiverId, double amount) {

        if (senderId == receiverId) {
            System.out.println("You cannot send money to the same account");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int firstUserId = Math.min(senderId, receiverId);
                int secondUserId = Math.max(senderId, receiverId);

                double firstBalance = userDAO.getBalanceForUpdate(con, firstUserId);
                double secondBalance = userDAO.getBalanceForUpdate(con, secondUserId);

                double senderBalance = senderId == firstUserId ? firstBalance : secondBalance;
                double receiverBalance = receiverId == firstUserId ? firstBalance : secondBalance;

                if (senderBalance < amount) {
                    con.rollback();
                    System.out.println("Insufficient balance");
                    return;
                }

                userDAO.updateBalance(con, senderId, senderBalance - amount);
                userDAO.updateBalance(con, receiverId, receiverBalance + amount);
                transactionDAO.recordTransaction(con, senderId, receiverId, amount, "SUCCESS");

                con.commit();
                System.out.println("Transaction successful");
            } catch (SQLException e) {
                rollbackQuietly(con);
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rollbackQuietly(Connection con) {

        try {
            con.rollback();
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace();
        }
    }
}
