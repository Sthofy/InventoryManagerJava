package inventorymanagerapp.others;

import inventorymanagerapp.Forms.PromptDialog;
import java.sql.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/inventorymanagerdb?zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Suhajdak970";

    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 0) { //Error Code 0: adatbázis offline
                new PromptDialog("Error!", "Database server is offline!");
            }
            return null;
        }
        return conn;
    }
}