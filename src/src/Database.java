import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:sqlserver://JOJO\\SQLEXPRESS:1433;databaseName=GYM;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sui";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}