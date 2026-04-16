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

            if (!sc.hasNextInt()) {
                System.out.println("Enter a valid menu number.");
                sc.nextLine();
                continue;
            }

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
                        System.out.println("Login successful");
                        System.out.println("Welcome!");
                    } else {
                        System.out.println("Invalid credentials");
                    }

                    break;

                case 3:
                    if (loggedUser == -1) {
                        System.out.println("Login first");
                        break;
                    }

                    System.out.println("Enter Amount:");
                    if (!sc.hasNextDouble()) {
                        System.out.println("Enter a valid amount.");
                        sc.nextLine();
                        break;
                    }

                    double addAmount = sc.nextDouble();
                    paymentService.addMoney(loggedUser, addAmount);
                    break;

                case 4:
                    if (loggedUser == -1) {
                        System.out.println("Login first");
                        break;
                    }

                    sc.nextLine();
                    System.out.println("Enter Receiver Email:");
                    String receiverEmail = sc.nextLine();

                    int receiverId = userDAO.getUserIdByEmail(receiverEmail);

                    if (receiverId == -1) {
                        System.out.println("Receiver not found");
                        break;
                    }

                    System.out.println("Enter Amount:");
                    if (!sc.hasNextDouble()) {
                        System.out.println("Enter a valid amount.");
                        sc.nextLine();
                        break;
                    }

                    double transferAmount = sc.nextDouble();
                    paymentService.sendMoney(loggedUser, receiverId, transferAmount);
                    break;

                case 5:
                    if (loggedUser == -1) {
                        System.out.println("Login first");
                        break;
                    }

                    transactionDAO.showTransactions(loggedUser);
                    break;

                case 6:
                    sc.close();
                    System.out.println("Goodbye");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
