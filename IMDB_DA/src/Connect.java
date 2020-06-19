import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	public Connection connection;

	public Connect() {
        this.connection = null;
}


public Connection create_connection() throws SQLException, ClassNotFoundException {
DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
   

String host = "localhost"; 
String port = "1521";
String dbName = "xe"; 
String userName = "scott"; 
String password = "tiger"; 

String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + "/" + dbName; 
return DriverManager.getConnection(dbURL, userName, password); 
}

public void close_connection(Connection con) {
try {
	con.close();
} catch (SQLException e) {
	System.err.println("Error! Database connection close failed." + e.getMessage());
}
}
	      
}
