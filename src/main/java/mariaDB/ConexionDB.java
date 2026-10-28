package mariaDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

	private static final String URL = "jdbc:mariadb://10.10.32.8:3306/academia";
	private static final String USER = "root";
	private static final String PASS = "abc123.";
	
	
	    public static Connection conectaDB() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASS);
	    }
	}
	

