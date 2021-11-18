package inventorymanagerapp.others;

import java.util.Date;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class Purchases {

    private Integer purchaseID;
    private Date purchaseDate;
    private Integer quantity;
    private double amountDue;
    private String accountName;
    private String username;
    private String itemName;

    public Purchases(Integer purchaseID, Date purchaseDate, Integer quantity, double amountDue, String accountName, String username, String itemName) {
        this.purchaseID = purchaseID;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
        this.amountDue = amountDue;
        this.accountName = accountName;
        this.username = username;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(Integer purchaseID) {
        this.purchaseID = purchaseID;
    }

}
