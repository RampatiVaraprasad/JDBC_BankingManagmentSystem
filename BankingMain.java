import java.util.Scanner;

public class BankingMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        BankingService bankingService = new BankingService();

        int choice;

        do {

            System.out.println();
            System.out.println("=============================");
            System.out.println("  Banking Management System  ");
            System.out.println("=============================");
            System.out.println(" 1. Account Creation ");
            System.out.println(" 2. Deposit ");
            System.out.println(" 3. Withdrawal ");
            System.out.println(" 4. Fund Transfer ");
            System.out.println(" 5. Transaction History ");
            System.out.println(" 6. View Account ");
            System.out.println(" 7. Exit ");
            System.out.print(" Enter Your Choice : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

            case 1:

                System.out.println();
                System.out.println("------ Account Creation ------");

                System.out.print("Enter Customer Name : ");
                String name = sc.nextLine();

                System.out.print("Enter Customer Email : ");
                String email = sc.nextLine();

                System.out.print("Enter Customer Phone : ");
                String phone = sc.nextLine();

                // Create Customer object
                Customer customer =
                        new Customer(name, email, phone);

                // Save customer
                int customerId =
                        customerDAO.addCustomer(customer);

                if (customerId == -1) {

                    System.out.println(
                            "Customer Creation Failed");

                    break;
                }

                System.out.println(
                        "Customer Created Successfully.");

                System.out.println(
                        "Customer ID : " + customerId);

                // Account details
                System.out.print(
                        "Enter Account Number : ");

                String accountNumber = sc.nextLine();

                System.out.print(
                        "Enter Account Type (SAVINGS/CURRENT) : ");

                String accountType = sc.nextLine();

                // Validate account type
                if (!accountType.equalsIgnoreCase("SAVINGS")
                        && !accountType.equalsIgnoreCase("CURRENT")) {

                    System.out.println(
                            "Invalid Account Type");

                    break;
                }

                accountType = accountType.toUpperCase();

                System.out.print(
                        "Enter Initial Deposit : ");

                double initialDeposit = sc.nextDouble();
                sc.nextLine();

                // Validate deposit
                if (initialDeposit < 0) {

                    System.out.println(
                            "Initial Deposit Cannot be Negative");

                    break;
                }

                // Create Account object
                Account account =
                        new Account(
                                customerId,
                                accountNumber,
                                accountType,
                                initialDeposit);

                // Save account
                int accountId =
                        accountDAO.createAccount(account);

                if (accountId != -1) {

                    System.out.println(
                            "Account Created Successfully");

                    System.out.println(
                            "Account ID : " + accountId);

                } else {

                    System.out.println(
                            "Account Creation Failed");
                }

                break;

            case 2:

                System.out.println();
                System.out.println("------- Deposit --------");

                System.out.print(
                        "Enter Account Number : ");

                String depositAccount = sc.nextLine();

                System.out.print(
                        "Enter Deposit Amount : ");

                double depositAmount = sc.nextDouble();
                sc.nextLine();

                if (depositAmount <= 0) {

                    System.out.println(
                            "Deposit Amount Must Be Greater Than 0");

                    break;
                }

                bankingService.deposit(
                        depositAccount,
                        depositAmount);

                break;

            case 3:

                System.out.println();
                System.out.println("------- Withdrawal --------");

                System.out.print(
                        "Enter Account Number : ");

                String withdrawAccount = sc.nextLine();

                System.out.print(
                        "Enter Withdrawal Amount : ");

                double withdrawAmount = sc.nextDouble();
                sc.nextLine();

                if (withdrawAmount <= 0) {

                    System.out.println(
                            "Withdrawal Amount Must Be Greater Than 0");

                    break;
                }

                bankingService.withdraw(
                        withdrawAccount,
                        withdrawAmount);

                break;

            case 4:

                System.out.println();
                System.out.println("------- Fund Transfer --------");

                System.out.print(
                        "Enter Sender Account Number : ");

                String fromAccount = sc.nextLine();

                System.out.print(
                        "Enter Receiver Account Number : ");

                String toAccount = sc.nextLine();

                System.out.print(
                        "Enter Transfer Amount : ");

                double transferAmount = sc.nextDouble();
                sc.nextLine();

                if (transferAmount <= 0) {

                    System.out.println(
                            "Transfer Amount Must Be Greater Than 0");

                    break;
                }

                bankingService.transfer(
                        fromAccount,
                        toAccount,
                        transferAmount);

                break;

            case 5:

                System.out.println();
                System.out.println(
                        "------- Transaction History --------");

                System.out.print(
                        "Enter Account Number : ");

                String historyAccount = sc.nextLine();

                transactionDAO.showTransactionHistory(
                        historyAccount);

                break;

            case 6:

                System.out.println();
                System.out.println(
                        "------- Account Details --------");

                System.out.print(
                        "Enter Account Number : ");

                String viewAccount = sc.nextLine();

                accountDAO.showAccount(viewAccount);

                break;

            case 7:

                System.out.println(
                        "Thank you for using Banking Management System.");

                break;

            default:

                System.out.println(
                        "Invalid Choice");
            }

        } while (choice != 7);

        sc.close();
    }
}