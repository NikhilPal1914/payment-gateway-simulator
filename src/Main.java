import Service.PaymentService;
import dao.TransactionDAO;
import dao.UserDAO;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        PaymentService paymentService = new PaymentService();
        TransactionDAO transactionDAO = new TransactionDAO();

        int loggedUser = -1;

        while (true) {

            System.out.println("\n1 Register");
            System.out.println("2 Login");
            System.out.println("3 Add Money");
            System.out.println("4 Send Money");
            System.out.println("5 View Transactions");
            System.out.println("6 Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.println("Enter Name:");
                    String name = sc.nextLine();

                    System.out.println("Enter Email:");
                    String email = sc.nextLine();

                    System.out.println("Enter Password:");
                    String password = sc.nextLine();

                    userDAO.registerUser(name, email, password);
                    break;

                case 2:
                    sc.nextLine();
                    System.out.println("Enter Email:");
                    email = sc.nextLine();

                    System.out.println("Enter Password:");
                    password = sc.nextLine();

                    loggedUser = userDAO.loginUser(email, password);

                    if (loggedUser != -1) {
                        System.out.println("Login Successful");
                    } else {
                        System.out.println("Invalid Credentials");
                    }

                    break;

                case 3:
                    if (loggedUser == -1) {
                        System.out.println("Login First");
                        break;
                    }

                    System.out.println("Enter Amount:");
                    double amount = sc.nextDouble();

                    paymentService.addMoney(loggedUser, amount);
                    break;

                case 4:
                    if (loggedUser == -1) {
                        System.out.println("Login First");
                        break;
                    }

                    sc.nextLine();
                    System.out.println("Enter Receiver Email:");
                    String receiverEmail = sc.nextLine();

                    int receiverId = userDAO.getUserIdByEmail(receiverEmail);

                    if (receiverId == -1) {
                        System.out.println("Receiver Not Found");
                        break;
                    }

                    System.out.println("Enter Amount:");
                    amount = sc.nextDouble();

                    paymentService.sendMoney(loggedUser, receiverId, amount);
                    break;

                case 5:
                    if (loggedUser == -1) {
                        System.out.println("Login First");
                        break;
                    }

                    transactionDAO.showTransactions(loggedUser);
                    break;

                case 6:
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}