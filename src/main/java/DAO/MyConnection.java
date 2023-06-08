package DAO;

import java.sql.DriverManager;

public class MyConnection {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/manage?useSSL=false";

    private static final String jdbcUsername = "root";

    private static final String jdbcPassword = "12345678";

    private static java.sql.Connection connection;

    public static java.sql.Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
