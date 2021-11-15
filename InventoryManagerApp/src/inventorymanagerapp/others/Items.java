package inventorymanagerapp.others;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class Items {

    private Integer ItemID;
    private String ItemName;
    private Integer Stock;
    private double Price;
    private String ItemType;

    public Items(Integer ItemID, String ItemName, Integer Stock, double Price, String ItemType) {
        this.ItemName = ItemName;
        this.Stock = Stock;
        this.ItemID = ItemID;
        this.Price = Price;
        this.ItemType = ItemType;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String ItemType) {
        this.ItemType = ItemType;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public Integer getItemID() {
        return ItemID;
    }

    public void setItemID(Integer ItemID) {
        this.ItemID = ItemID;
    }

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer Stock) {
        this.Stock = Stock;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

}
