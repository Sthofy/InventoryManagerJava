package inventorymanagerapp.others;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class ItemType {

    private Integer ItemTypeID;
    private String ItemTypeName;

    public ItemType(Integer ItemTypeID, String ItemTypeName) {
        this.ItemTypeID = ItemTypeID;
        this.ItemTypeName = ItemTypeName;
    }

    public String getItemTypeName() {
        return ItemTypeName;
    }

    public void setItemTypeName(String ItemTypeName) {
        this.ItemTypeName = ItemTypeName;
    }

    public Integer getItemTypeID() {
        return ItemTypeID;
    }

    public void setItemTypeID(Integer ItemTypeID) {
        this.ItemTypeID = ItemTypeID;
    }

}
