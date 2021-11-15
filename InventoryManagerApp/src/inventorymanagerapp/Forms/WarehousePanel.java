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

    private ArrayList<Items> itemList = new ArrayList<Items>();

    public WarehousePanel() {
        initComponents();
        setTable();
        setComboBox();
    }
    private TableRowSorter<TableModel> rowSorter;

    private void setTable() {
        Connection conn = DatabaseManager.getConnection();
        DefaultTableModel model = (DefaultTableModel) tblItems.getModel();

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
            DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
            while (rs.next()) {
                defaultComboBoxModel.addElement(rs.getString(1));
                cmbBxItemTypesAdd.setModel(defaultComboBoxModel);
            }

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
            }

            new PromptDialog("Operation Succesful", "Item Added");

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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbBxItemTypesAdd = new javax.swing.JComboBox<>();
        txtBxPriceAdd = new javax.swing.JTextField();
        txtBxNameAdd = new javax.swing.JTextField();
        txtBxStockAdd = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setMinimumSize(new java.awt.Dimension(970, 670));
        setPreferredSize(new java.awt.Dimension(970, 670));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblItems.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 0, 0)));
        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Category", "Stock", "Price (USD)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblItems.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblItems);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 930, 290));

        btnFilter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFilter.setText("OK");
        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterMouseClicked(evt);
            }
        });
        add(btnFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 320, -1, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Search:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 70, 20));

        txtBxFilter.setText("Search by Name");
        txtBxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBxFilterActionPerformed(evt);
            }
        });
        add(txtBxFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 750, 20));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Add/Delete/Update Item");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Price:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 50, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Name:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 50, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Category:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Stock:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 50, -1));

        cmbBxItemTypesAdd.setMaximumRowCount(20);
        cmbBxItemTypesAdd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbBxItemTypesAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 170, -1));
        jPanel1.add(txtBxPriceAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 170, -1));
        jPanel1.add(txtBxNameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 170, -1));
        jPanel1.add(txtBxStockAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 170, -1));

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdd.setText("Add Item");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 110, 110));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 455, 310));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Out of Stock/Low Stock");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 30));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 350, 455, 310));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBxFilterActionPerformed
        Filter();
    }//GEN-LAST:event_txtBxFilterActionPerformed

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked
        Filter();
    }//GEN-LAST:event_btnFilterMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        addNewItem();
    }//GEN-LAST:event_btnAddMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnFilter;
    private javax.swing.JComboBox<String> cmbBxItemTypesAdd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtBxFilter;
    private javax.swing.JTextField txtBxNameAdd;
    private javax.swing.JTextField txtBxPriceAdd;
    private javax.swing.JTextField txtBxStockAdd;
    // End of variables declaration//GEN-END:variables
}
