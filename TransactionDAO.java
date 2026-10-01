import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionDAO {
	
	public void showTransactionHistory(String accountNumber) {
		String sql = "SELECT t.transaction_id, " +
	             "t.transaction_type, " +
	             "t.amount, " +
	             "t.description, " +
	             "t.transaction_date " +
	             "FROM transactions t " +
	             "JOIN accounts a " +
	             "ON t.account_id = a.account_id " +
	             "WHERE a.account_number = ? " +
	             "ORDER BY t.transaction_date DESC";
		
		try(Connection con = DBConnection.getconConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			System.out.println("-----------------------------------");
			System.out.println("        TRANSACTION HISTORY        ");
			System.out.println("-----------------------------------");
			
			boolean found = false;
			
			while (rs.next()) {
				System.out.println("Trnsaction ID : "+ rs.getInt("transaction_id"));
				System.out.println(" Type : "+ rs.getString("transaction_type"));
				System.out.println(" Amount : "+ rs.getDouble("amount"));
				System.out.println(" Description : "+ rs.getString("description"));
				System.out.println(" Date : "+ rs.getTimestamp("transaction_date"));
				System.out.println("----------------------------------------------");

			}
			if (!found) {
				System.out.println(" No Trnsaction found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
