import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/BankingManagement";
	private static final String USER = "root";
	private static final String PASSWORD = "Root";
	
	public static Connection getconConnection() throws Exception{
		return DriverManager.getConnection(URL,USER,PASSWORD);
		
	}


}
