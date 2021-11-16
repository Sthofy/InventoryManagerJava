package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;
import inventorymanagerapp.others.Items;
import java.sql.*;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.RowFilter;
import javax.swing.table.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class WarehousePanel extends java.awt.Panel {

    private final ArrayList<Items> itemList = new ArrayList<>();

    public WarehousePanel() {
        initComponents();
        setTable();
        setComboBox();
    }
    private TableRowSorter<TableModel> rowSorter;

    private void setTable() {
        Connection conn = DatabaseManager.getConnection();
        DefaultTableModel model = (DefaultTableModel) tblItems.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        rowSorter = new TableRowSorter<TableModel>(tblItems.getModel());
        tblItems.setRowSorter(rowSorter);

        try {
            PreparedStatement getItems = conn.prepareStatement("SELECT * FROM items,itemtype WHERE items.ITEMTYPE_ItemTypeID=itemtype.ItemTypeID ORDER BY ItemID");
            ResultSet rs = getItems.executeQuery();

            while (rs.next()) {
                Items item = new Items(rs.getInt("ItemID"),
                        rs.getString("Itemname"),
                        rs.getInt("Stock"),
                        rs.getDouble("Price"),
                        rs.getString("ITEMTYPE_ItemTypeID"));
                rs.getString(1);

                Vector row = new Vector();
                row.add(rs.getInt("ItemID"));
                row.add(rs.getString("Itemname"));
                row.add(rs.getString("ItemTypeName"));
                row.add(rs.getInt("Stock"));
                row.add(rs.getDouble("Price"));
                model.addRow(row);

                tblItems.setModel(model);
                itemList.add(item);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setComboBox() {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement getItemTypeNames = conn.prepareStatement("SELECT ItemTypeName FROM itemtype");
            ResultSet rs = getItemTypeNames.executeQuery();

            DefaultComboBoxModel modelAdd = new DefaultComboBoxModel();
            DefaultComboBoxModel modelUpdate = new DefaultComboBoxModel();

            while (rs.next()) {
                modelAdd.addElement(rs.getString(1));
                modelUpdate.addElement(rs.getString(1));
                cmbBxItemTypesAdd.setModel(modelAdd);
                cmbBxItemTypesUpdate.setModel(modelUpdate);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addNewItem() {
        Connection conn = DatabaseManager.getConnection();
        int itemTypeID = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO items (ItemName,Stock,Price,ITEMTYPE_ItemTypeID) VALUES (?,?,?,?)");
            PreparedStatement getItemTypeID = conn.prepareStatement("SELECT ItemTypeID FROM itemtype WHERE itemtype.ItemTypeName=?");
            getItemTypeID.setString(1, (String) cmbBxItemTypesAdd.getSelectedItem());

            ResultSet rsItemTypeID = getItemTypeID.executeQuery();

            while (rsItemTypeID.next()) {
                itemTypeID = rsItemTypeID.getInt(1);
            }

            if (txtBxNameAdd.getText().isEmpty() || txtBxPriceAdd.getText().isEmpty() || txtBxStockAdd.getText().isEmpty()) {
                PromptDialog promptDialog = new PromptDialog("Error", "Missing information");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);
            } else {
                ps.setString(1, txtBxNameAdd.getText());
                ps.setInt(2, Integer.valueOf(txtBxStockAdd.getText()));
                ps.setDouble(3, Double.valueOf(txtBxPriceAdd.getText()));
                ps.setInt(4, itemTypeID);
                ps.executeUpdate();

                PromptDialog promptDialog = new PromptDialog("Operation Succesful", "Item Added");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);

            }
            conn.close();
            setTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateItem() {
        Connection conn = DatabaseManager.getConnection();
        String updatedName = "";
        Integer tempTypeID = 0, tempItemID = 0, updatedItemType = 0, updatedStock = 0;
        double updatedPrice = 0.0;

        try {
            PreparedStatement getItem = conn.prepareStatement("SELECT * FROM items WHERE ItemName=?");
            getItem.setString(1, txtBxUpdate.getText());
            ResultSet rsGetItem = getItem.executeQuery();
            while (rsGetItem.next()) {
                tempItemID = rsGetItem.getInt("ItemID");
                tempTypeID = rsGetItem.getInt("ITEMTYPE_ItemTypeID");

                PreparedStatement getItemType = conn.prepareStatement("SELECT * FROM itemtype WHERE ItemTypeID=" + tempTypeID);
                ResultSet rsGetItemType = getItemType.executeQuery();

                if (!txtBxUpdate.getText().isEmpty() && !txtBxUpdate.getText().equals("Enter Item Name")) {
                    if (rsGetItem == null) {
                        System.out.println("Nincs elem");
                    }

                    if (txtBxNameUpdate.getText().equals(rsGetItem.getString("ItemName"))) {
                        updatedName = rsGetItem.getString("ItemName");
                    } else {
                        updatedName = txtBxNameUpdate.getText();
                    }

                    if (txtBxPriceUpdate.getText().equals(rsGetItem.getString("Price"))) {
                        updatedPrice = rsGetItem.getDouble("Price");
                    } else {
                        updatedPrice = Double.valueOf(txtBxPriceUpdate.getText());
                    }

                    if (txtBxStockUpdate.getText().equals(rsGetItem.getString("Stock"))) {
                        updatedStock = rsGetItem.getInt("Stock");
                    } else {
                        updatedStock = Integer.valueOf(txtBxStockUpdate.getText());
                    }

                    while (rsGetItemType.next()) {
                        if (cmbBxItemTypesUpdate.getSelectedItem().equals(rsGetItemType.getString("ItemTypeName"))) {
                            updatedItemType = rsGetItemType.getInt("ItemTypeID");
                        } else {
                            PreparedStatement getItemTypeUpdated = conn.prepareStatement("SELECT * FROM itemtype WHERE ItemTypeName=?");
                            getItemTypeUpdated.setString(1, cmbBxItemTypesUpdate.getSelectedItem().toString());
                            ResultSet rsGetItemTypeUpdated = getItemTypeUpdated.executeQuery();

                            while (rsGetItemTypeUpdated.next()) {
                                updatedItemType = rsGetItemTypeUpdated.getInt(1);
                            }
                        }
                    }
                } else {
                    PromptDialog promptDialog = new PromptDialog("Error", "Missing information");
                    promptDialog.setResizable(false);
                    promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                    promptDialog.setVisible(true);
                }
            }
            PreparedStatement updateItem = conn.prepareStatement("UPDATE items SET ItemName=?, Stock=?, Price=?, ITEMTYPE_ItemTypeID=? WHERE ItemID=" + tempItemID);
            updateItem.setString(1, updatedName);
            updateItem.setInt(2, updatedStock);
            updateItem.setDouble(3, updatedPrice);
            updateItem.setInt(4, updatedItemType);
            updateItem.executeUpdate();

            PromptDialog promptDialog = new PromptDialog("Operation Succesful", "Item Updated");
            promptDialog.setResizable(false);
            promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
            promptDialog.setVisible(true);
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTable();
    }

    private void deleteItem() {
        Connection conn = DatabaseManager.getConnection();
        try {
            Integer itemID = 0;
            if (txtBxDelete.getText().equals("") || txtBxDelete.getText().equals("Enter Item Name")) {
                PromptDialog promptDialog = new PromptDialog("Error", "Missing information");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);
            } else {
                PreparedStatement getItemID = conn.prepareStatement("SELECT ItemID FROM items WHERE ItemName=?");
                getItemID.setString(1, txtBxDelete.getText());
                ResultSet rsGetItemID = getItemID.executeQuery();
                while (rsGetItemID.next()) {
                    itemID = rsGetItemID.getInt(1);
                }

                PreparedStatement deleteItem = conn.prepareCall("DELETE FROM items WHERE ItemID=" + itemID);
                deleteItem.executeUpdate();

                PromptDialog promptDialog = new PromptDialog("Operation successful", "Item Deleted");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);

            }
            conn.close();
            txtBxDelete.setText("Enter Item Name");
            setTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Filter() {
        String text = txtBxFilter.getText();
        rowSorter = new TableRowSorter<TableModel>(tblItems.getModel());
        tblItems.setRowSorter(rowSorter);
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        btnFilter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtBxFilter = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        cmbBxItemTypesAdd = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtBxPriceAdd = new javax.swing.JTextField();
        txtBxNameAdd = new javax.swing.JTextField();
        txtBxStockAdd = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtBxDelete = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtBxUpdate = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtBxNameUpdate = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbBxItemTypesUpdate = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtBxStockUpdate = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtBxPriceUpdate = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setMinimumSize(new java.awt.Dimension(970, 670));
        setPreferredSize(new java.awt.Dimension(970, 670));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblItems.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Stock", "Price (USD)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblItems.setGridColor(new java.awt.Color(255, 255, 255));
        tblItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblItems);
        if (tblItems.getColumnModel().getColumnCount() > 0) {
            tblItems.getColumnModel().getColumn(0).setResizable(false);
            tblItems.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblItems.getColumnModel().getColumn(1).setResizable(false);
            tblItems.getColumnModel().getColumn(2).setResizable(false);
            tblItems.getColumnModel().getColumn(3).setResizable(false);
            tblItems.getColumnModel().getColumn(4).setResizable(false);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 930, 270));

        btnFilter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFilter.setText("OK");
        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterMouseClicked(evt);
            }
        });
        add(btnFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 290, 70, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Search:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 70, 20));

        txtBxFilter.setText("Search by Name");
        txtBxFilter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBxFilterFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBxFilterFocusLost(evt);
            }
        });
        txtBxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBxFilterActionPerformed(evt);
            }
        });
        add(txtBxFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 750, 20));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbBxItemTypesAdd.setMaximumRowCount(20);
        cmbBxItemTypesAdd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbBxItemTypesAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 130, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Add/Delete/Update Item");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 30));
        jPanel1.add(txtBxPriceAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 150, -1));
        jPanel1.add(txtBxNameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 150, -1));
        jPanel1.add(txtBxStockAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 150, -1));

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdd.setText("Add Item");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 110, 30));

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 90, 30));

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 110, 30));

        txtBxDelete.setText("Enter Item Name");
        txtBxDelete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBxDeleteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBxDeleteFocusLost(evt);
            }
        });
        jPanel1.add(txtBxDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 300, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Name:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Category:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 70, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Stock:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 50, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Price:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 40, -1));

        txtBxUpdate.setText("Enter Item Name");
        txtBxUpdate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBxUpdateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBxUpdateFocusLost(evt);
            }
        });
        jPanel1.add(txtBxUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 110, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Name:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 50, -1));
        jPanel1.add(txtBxNameUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 150, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Category:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 70, -1));

        cmbBxItemTypesUpdate.setMaximumRowCount(20);
        cmbBxItemTypesUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbBxItemTypesUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 130, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Stock:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 50, 20));
        jPanel1.add(txtBxStockUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 150, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Price:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 40, -1));
        jPanel1.add(txtBxPriceUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 150, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 455, 330));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Out of Stock/Low Stock");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 30));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 330, 455, 330));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBxFilterActionPerformed
        Filter();
    }//GEN-LAST:event_txtBxFilterActionPerformed

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked
        Filter();
    }//GEN-LAST:event_btnFilterMouseClicked

    private void txtBxFilterFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxFilterFocusGained
        if (txtBxFilter.getText().equals("Search by Name")) {
            txtBxFilter.setText("");
        }
    }//GEN-LAST:event_txtBxFilterFocusGained

    private void txtBxFilterFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxFilterFocusLost
        if (txtBxFilter.getText().equals("")) {
            txtBxFilter.setText("Search by Name");
        }
    }//GEN-LAST:event_txtBxFilterFocusLost

    private void txtBxDeleteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxDeleteFocusLost
        if (txtBxDelete.getText().equals("")) {
            txtBxDelete.setText("Enter Item Name");
        }
    }//GEN-LAST:event_txtBxDeleteFocusLost

    private void txtBxDeleteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxDeleteFocusGained
        if (txtBxDelete.getText().equals("Enter Item Name")) {
            txtBxDelete.setText("");
        }
    }//GEN-LAST:event_txtBxDeleteFocusGained

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        deleteItem();
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void txtBxUpdateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxUpdateFocusLost
        if (txtBxUpdate.getText().equals("")) {
            txtBxUpdate.setText("Enter Item Name");
        }
    }//GEN-LAST:event_txtBxUpdateFocusLost

    private void txtBxUpdateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxUpdateFocusGained
        if (txtBxUpdate.getText().equals("Enter Item Name")) {
            txtBxUpdate.setText("");
        }
    }//GEN-LAST:event_txtBxUpdateFocusGained

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        updateItem();
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        addNewItem();
    }//GEN-LAST:event_btnAddMouseClicked

    private void tblItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemsMouseClicked
        DefaultTableModel tableModel = (DefaultTableModel) tblItems.getModel();
        Object elementAt = tableModel.getDataVector().elementAt(tblItems.getSelectedRow());

        String selectedItems = elementAt.toString().substring(1, elementAt.toString().length() - 1);
        String[] items = selectedItems.split(", ");
        txtBxUpdate.setText(items[1]);
        txtBxNameUpdate.setText(items[1]);
        cmbBxItemTypesUpdate.setSelectedItem(items[2]);
        txtBxStockUpdate.setText(items[3]);
        txtBxPriceUpdate.setText(items[4]);
    }//GEN-LAST:event_tblItemsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbBxItemTypesAdd;
    private javax.swing.JComboBox<String> cmbBxItemTypesUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtBxDelete;
    private javax.swing.JTextField txtBxFilter;
    private javax.swing.JTextField txtBxNameAdd;
    private javax.swing.JTextField txtBxNameUpdate;
    private javax.swing.JTextField txtBxPriceAdd;
    private javax.swing.JTextField txtBxPriceUpdate;
    private javax.swing.JTextField txtBxStockAdd;
    private javax.swing.JTextField txtBxStockUpdate;
    private javax.swing.JTextField txtBxUpdate;
    // End of variables declaration//GEN-END:variables
}
