package inventorymanagerapp.others;

import java.sql.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class DatabaseManager {

    private static final String url = "";
    private static final String username = "root";
    private static final String password = "Suhajdak970";

    public static Connection getConnectin() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }
}
