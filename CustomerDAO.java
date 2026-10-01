import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {
	public int addCustomer(Customer customer) {
		
		String sql = "Insert into customers (customer_name, email, phone) values (?,?,?)";
		
	try	(Connection con = DBConnection.getconConnection();
		PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)){
		
		ps.setString(1, customer.getCustomerName());
		ps.setString(2, customer.getEmail());
		ps.setString(3, customer.getPhone());
		
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys(); // This gets the automatically generated ID from MySQL.
		if (rs.next()) {
			return rs.getInt(1);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return -1;	
	}
	public boolean customerExists(int customerId) {
		String sql = "select customer_id from customers where customer_id = ?";
		try	(Connection con = DBConnection.getconConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
				
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
}
