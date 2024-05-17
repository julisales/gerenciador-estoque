package connection;

import java.sql.*;

public class ConnectionFactory {
    private static final String url = "jdbc:mysql://stockpro.czsw8uie83iv.us-east-1.rds.amazonaws.com/stockpro";
    private static final String user = "admin";
    private static final String password = "admin123";

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


