package inventorymanagerapp;

import inventorymanagerapp.conrollers.LoginWindow;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class Main {

    public static void main(String[] args) {
        LoginWindow Login = new LoginWindow();
        Login.setTitle("Sthofy Inventory Manager");
        Login.setDefaultCloseOperation(LoginWindow.DISPOSE_ON_CLOSE);
        Login.setResizable(false);
        Login.setSize(360, 520);
        Login.setVisible(true);
    }

}
