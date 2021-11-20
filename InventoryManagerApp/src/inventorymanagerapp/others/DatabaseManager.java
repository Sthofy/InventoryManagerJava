package inventorymanagerapp.others;

import inventorymanagerapp.Forms.PromptDialog;
import java.sql.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class DatabaseManager {

    private static String USERNAME,PASSWORD,URL;
    

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
    public static void set(String username,String password,String ip,String port){
        USERNAME=username;
        PASSWORD=password;      
        URL = "jdbc:mysql://"+ip+":"+port+"/inventorymanagerdb?zeroDateTimeBehavior=convertToNull";
    }
}