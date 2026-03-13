import Service.PaymentService;
import dao.TransactionDAO;
import dao.UserDAO;

void main() {

    Scanner sc = new Scanner(System.in);

    UserDAO userDAO = new UserDAO();
    PaymentService paymentService = new PaymentService();
    TransactionDAO transactionDAO = new TransactionDAO();

    int loggedUser = -1;

    while (true) {

        IO.println("\n1 Register");
        IO.println("2 Login");
        IO.println("3 Add Money");
        IO.println("4 Send Money");
        IO.println("5 View Transactions");
        IO.println("6 Exit");

        int choice = sc.nextInt();

        switch (choice) {

            case 1:
                sc.nextLine();
                IO.println("Enter Name:");
                String name = sc.nextLine();

                IO.println("Enter Email:");
                String email = sc.nextLine();

                IO.println("Enter Password:");
                String password = sc.nextLine();

                userDAO.registerUser(name, email, password);
                break;

            case 2:
                sc.nextLine();
                IO.println("Enter Email:");
                email = sc.nextLine();

                IO.println("Enter Password:");
                password = sc.nextLine();

                loggedUser = userDAO.loginUser(email, password);

                if (loggedUser != -1) {
                    IO.println("Login Successful");
                    System.out.println("Welcome!!");
                } else {
                    IO.println("Invalid Credentials");
                }

                break;

            case 3:
                if (loggedUser == -1) {
                    IO.println("Login First");
                    break;
                }

                IO.println("Enter Amount:");
                double amount = sc.nextDouble();

                paymentService.addMoney(loggedUser, amount);
                break;

            case 4:
                if (loggedUser == -1) {
                    IO.println("Login First");
                    break;
                }

                sc.nextLine();
                IO.println("Enter Receiver Email:");
                String receiverEmail = sc.nextLine();

                int receiverId = userDAO.getUserIdByEmail(receiverEmail);

                if (receiverId == -1) {
                    IO.println("Receiver Not Found");
                    break;
                }

                IO.println("Enter Amount:");
                amount = sc.nextDouble();

                paymentService.sendMoney(loggedUser, receiverId, amount);
                break;

            case 5:
                if (loggedUser == -1) {
                    IO.println("Login First");
                    break;
                }

                transactionDAO.showTransactions(loggedUser);
                break;

            case 6:
                System.exit(0);

            default:
                IO.println("Invalid Choice");
        }
    }
}