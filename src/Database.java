package src;
import java.sql.*;

public class Database {
    private Connection connection;

    public Database() {
        try {
            String url = "jdbc:sqlserver://localhost:PORT;" // replace "PORT" with your SQL Server port number
                        + "databaseName=GYM;"
                        + "integratedSecurity=true;"
                        + "encrypt=false;";
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
    
}