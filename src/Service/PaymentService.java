package Service;

import dao.TransactionDAO;
import dao.UserDAO;

public class PaymentService {

    UserDAO userDAO = new UserDAO();
    TransactionDAO transactionDAO = new TransactionDAO();

    public void addMoney(int userId, double amount) {

        double balance = userDAO.getBalance(userId);

        balance += amount;

        userDAO.updateBalance(userId, balance);

        System.out.println("Money Added Successfully");
    }

    public void sendMoney(int senderId, int receiverId, double amount) {

        double senderBalance = userDAO.getBalance(senderId);

        if (senderBalance < amount) {
            System.out.println("Insufficient Balance");
            return;
        }

        double receiverBalance = userDAO.getBalance(receiverId);

        senderBalance -= amount;
        receiverBalance += amount;

        userDAO.updateBalance(senderId, senderBalance);
        userDAO.updateBalance(receiverId, receiverBalance);

        transactionDAO.recordTransaction(senderId, receiverId, amount, "SUCCESS");

        System.out.println("Transaction Successful");
    }
}
