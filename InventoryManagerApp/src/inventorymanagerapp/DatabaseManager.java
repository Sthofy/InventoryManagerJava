package inventorymanagerapp;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    
    static private Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagerdb,root,Suhajdak970");
    static private Statement stat=null;
    static private ResultSet result=null;

    public void getConnectin(){
        Class.forName("com.mysql.jdbc.Driver");
    }
}
