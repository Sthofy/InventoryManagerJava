package inventorymanagerapp;

import inventorymanagerapp.Forms.DatabaseLogin;
import inventorymanagerapp.Forms.LoginWindow;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class Main {

    public static void main(String[] args) {
        DatabaseLogin dbLogin = new DatabaseLogin();
        dbLogin.setTitle("Sthofy Inventory Manager");
        dbLogin.setDefaultCloseOperation(LoginWindow.DISPOSE_ON_CLOSE);
        dbLogin.setResizable(false);
        dbLogin.setVisible(true);
    }

}
