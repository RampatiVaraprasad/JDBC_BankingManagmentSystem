import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountDAO {

    public int createAccount(Account account) {

        String sql = "INSERT INTO accounts "
                   + "(customer_id, account_number, account_type, balance) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getconConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, account.getCustomerId());
            ps.setString(2, account.getAccountNumber());
            ps.setString(3, account.getAccountType());
            ps.setDouble(4, account.getBalance());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    // View Account
    public void showAccount(String accountNumber) {

        String sql =
                "SELECT a.account_id, " +
                "c.customer_name, " +
                "c.email, " +
                "c.phone, " +
                "a.account_number, " +
                "a.account_type, " +
                "a.balance " +
                "FROM accounts a " +
                "JOIN customers c " +
                "ON a.customer_id = c.customer_id " +
                "WHERE a.account_number = ?";

        try (Connection con = DBConnection.getconConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println();
                System.out.println("------ Account Details ------");

                System.out.println(
                        "Account ID      : " +
                        rs.getInt("account_id"));

                System.out.println(
                        "Customer Name   : " +
                        rs.getString("customer_name"));

                System.out.println(
                        "Email           : " +
                        rs.getString("email"));

                System.out.println(
                        "Phone           : " +
                        rs.getString("phone"));

                System.out.println(
                        "Account Number  : " +
                        rs.getString("account_number"));

                System.out.println(
                        "Account Type    : " +
                        rs.getString("account_type"));

                System.out.println(
                        "Balance         : " +
                        rs.getDouble("balance"));

            } else {

                System.out.println(
                        "Account Not Found");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}