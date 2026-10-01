import java.sql.Connection;
import java.sql.PreparedStatement;

public class BankingService {

    // =========================
    // DEPOSIT
    // =========================
    public void deposit(String accountNumber, double amount) {

        Connection con = null;

        try {
            con = DBConnection.getconConnection();

            String sql = "UPDATE accounts "
                       + "SET balance = balance + ? "
                       + "WHERE account_number = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Deposit Successful");
                System.out.println("Amount Deposited : " + amount);
            } else {
                System.out.println("Account Not Found");
            }

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================
    // WITHDRAW
    // =========================
    public void withdraw(String accountNumber, double amount) {

        Connection con = null;

        try {
            con = DBConnection.getconConnection();

            String sql = "UPDATE accounts "
                       + "SET balance = balance - ? "
                       + "WHERE account_number = ? "
                       + "AND balance >= ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);
            ps.setDouble(3, amount);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Withdrawal Successful");
                System.out.println("Amount Withdrawn : " + amount);
            } else {
                System.out.println(
                        "Withdrawal Failed: Account not found or insufficient balance");
            }

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================
    // FUND TRANSFER
    // =========================
    public void transfer(String fromAccount,
                         String toAccount,
                         double amount) {

        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than 0");
            return;
        }

        if (fromAccount.equals(toAccount)) {
            System.out.println(
                    "Sender and Receiver accounts cannot be same");
            return;
        }

        Connection con = null;

        try {
            con = DBConnection.getconConnection();

            // Start transaction
            con.setAutoCommit(false);

            // 1. Deduct money from sender
            String debitSql =
                    "UPDATE accounts "
                  + "SET balance = balance - ? "
                  + "WHERE account_number = ? "
                  + "AND balance >= ?";

            PreparedStatement debitPs =
                    con.prepareStatement(debitSql);

            debitPs.setDouble(1, amount);
            debitPs.setString(2, fromAccount);
            debitPs.setDouble(3, amount);

            int debitRows = debitPs.executeUpdate();

            debitPs.close();

            if (debitRows == 0) {
                System.out.println(
                        "Transfer Failed: Sender account not found or insufficient balance");

                con.rollback();
                return;
            }


            // 2. Add money to receiver
            String creditSql =
                    "UPDATE accounts "
                  + "SET balance = balance + ? "
                  + "WHERE account_number = ?";

            PreparedStatement creditPs =
                    con.prepareStatement(creditSql);

            creditPs.setDouble(1, amount);
            creditPs.setString(2, toAccount);

            int creditRows = creditPs.executeUpdate();

            creditPs.close();

            if (creditRows == 0) {
                System.out.println(
                        "Transfer Failed: Receiver account not found");

                con.rollback();
                return;
            }


            // 3. Record sender transaction
            String senderTransaction =
                    "INSERT INTO transactions "
                  + "(account_id, transaction_type, amount, description) "
                  + "SELECT account_id, ?, ?, ? "
                  + "FROM accounts "
                  + "WHERE account_number = ?";

            PreparedStatement senderPs =
                    con.prepareStatement(senderTransaction);

            senderPs.setString(1, "DEBIT");
            senderPs.setDouble(2, amount);
            senderPs.setString(
                    3,
                    "Money transferred to " + toAccount);
            senderPs.setString(4, fromAccount);

            senderPs.executeUpdate();

            senderPs.close();


            // 4. Record receiver transaction
            String receiverTransaction =
                    "INSERT INTO transactions "
                  + "(account_id, transaction_type, amount, description) "
                  + "SELECT account_id, ?, ?, ? "
                  + "FROM accounts "
                  + "WHERE account_number = ?";

            PreparedStatement receiverPs =
                    con.prepareStatement(receiverTransaction);

            receiverPs.setString(1, "CREDIT");
            receiverPs.setDouble(2, amount);
            receiverPs.setString(
                    3,
                    "Money received from " + fromAccount);
            receiverPs.setString(4, toAccount);

            receiverPs.executeUpdate();

            receiverPs.close();


            // 5. Save everything
            con.commit();

            System.out.println("Transfer Successful");
            System.out.println("Amount Transferred : " + amount);
            System.out.println("From Account : " + fromAccount);
            System.out.println("To Account   : " + toAccount);

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }

            System.out.println(
                    "Transfer Failed. Money deduction rolled back");

            e.printStackTrace();

        } finally {

            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}