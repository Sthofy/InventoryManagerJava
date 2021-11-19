package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;
import java.sql.*;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.RowFilter;
import javax.swing.table.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class WarehousePanel extends java.awt.Panel {

    private TableRowSorter<TableModel> rowSorter;

    public WarehousePanel() {
        initComponents();
        setTable();
        setComboBox();
        getOutOfStock();
        getLowStock();
    }

    private void setTable() {
        Connection conn = DatabaseManager.getConnection();
        DefaultTableModel model = (DefaultTableModel) tblItems.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        rowSorter = new TableRowSorter<>(tblItems.getModel());
        tblItems.setRowSorter(rowSorter);

        try {
            PreparedStatement getItems = conn.prepareStatement("SELECT * FROM items,itemtype WHERE items.ITEMTYPE_ItemTypeID=itemtype.ItemTypeID ORDER BY ItemID");
            ResultSet rs = getItems.executeQuery();

            while (rs.next()) {

                Vector row = new Vector();
                row.add(rs.getInt("ItemID"));
                row.add(rs.getString("Itemname"));
                row.add(rs.getString("ItemTypeName"));
                row.add(rs.getInt("Stock"));
                row.add(rs.getDouble("Price"));
                model.addRow(row);

                tblItems.setModel(model);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setComboBox() {
        Connection conn = DatabaseManager.getConnection();
        try {         
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
        Integer tempTypeID = 0,updatedItemType = 0, updatedStock = 0;
        double updatedPrice = 0.0;

        try {
            PreparedStatement getItem = conn.prepareStatement("SELECT * FROM items WHERE ItemID=?");
            getItem.setInt(1, Integer.valueOf(txtBxUpdate.getText()));
            ResultSet rsGetItem = getItem.executeQuery();
            while (rsGetItem.next()) {
                tempTypeID = rsGetItem.getInt("ITEMTYPE_ItemTypeID");

                PreparedStatement getItemType = conn.prepareStatement("SELECT * FROM itemtype WHERE ItemTypeID=" + tempTypeID);
                ResultSet rsGetItemType = getItemType.executeQuery();

                if (!txtBxUpdate.getText().isEmpty() && !txtBxUpdate.getText().equals("Enter Item ID")) {

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
            PreparedStatement updateItem = conn.prepareStatement("UPDATE items SET ItemName=?, Stock=?, Price=?, ITEMTYPE_ItemTypeID=? WHERE ItemID=?");
            updateItem.setInt(5,Integer.valueOf(txtBxUpdate.getText()));
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
        rowSorter = new TableRowSorter<>(tblItems.getModel());
        tblItems.setRowSorter(rowSorter);
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void getOutOfStock() {
        Connection conn = DatabaseManager.getConnection();
        int i = 0;
        try {
            PreparedStatement getOutStock = conn.prepareStatement("SELECT ItemName FROM items WHERE Stock=" + 0);
            ResultSet getOutStockRs = getOutStock.executeQuery();
            DefaultListModel model = new DefaultListModel();
            
            while (getOutStockRs.next()) {
                model.add(i, getOutStockRs.getString(1));
                i++;
            }
            
            lstOutStock.setModel(model);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getLowStock() {
        Connection conn = DatabaseManager.getConnection();
        int i = 0;
        try {
            PreparedStatement getLowStock = conn.prepareStatement("SELECT ItemName FROM items WHERE Stock BETWEEN " + 1 + " AND " + 5);
            ResultSet getLowStockRs = getLowStock.executeQuery();
            DefaultListModel model = new DefaultListModel();
            while (getLowStockRs.next()) {
                model.add(i, getLowStockRs.getString(1));
                i++;
            }
            lstLowStock.setModel(model);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        lstOutStock = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstLowStock = new javax.swing.JList<>();

        setBackground(new java.awt.Color(204, 204, 204));
        setMinimumSize(new java.awt.Dimension(970, 670));
        setPreferredSize(new java.awt.Dimension(970, 670));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblItems.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblItems.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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

        cmbBxItemTypesAdd.setMaximumRowCount(10);
        cmbBxItemTypesAdd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbBxItemTypesAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 130, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Add/Delete/Update Item");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 30));
        jPanel1.add(txtBxPriceAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 150, -1));
        jPanel1.add(txtBxNameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 150, -1));
        jPanel1.add(txtBxStockAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 150, -1));

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdd.setText("Add Item");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 110, 30));

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 90, 30));

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
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 50, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Category:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 70, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Stock:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 50, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Price:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 40, -1));

        txtBxUpdate.setText("Enter Item ID");
        txtBxUpdate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBxUpdateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBxUpdateFocusLost(evt);
            }
        });
        jPanel1.add(txtBxUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 110, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Name:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 50, -1));
        jPanel1.add(txtBxNameUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 150, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Category:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 70, -1));

        cmbBxItemTypesUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbBxItemTypesUpdate.setMaximumRowCount(10);
        cmbBxItemTypesUpdate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbBxItemTypesUpdate.setDoubleBuffered(true);
        jPanel1.add(cmbBxItemTypesUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 130, -1));
        cmbBxItemTypesUpdate.setVisible(true);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Stock:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 50, 20));
        jPanel1.add(txtBxStockUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 150, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Price:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 40, -1));
        jPanel1.add(txtBxPriceUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 150, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 455, 330));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Out of Stock/Low Stock");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 30));

        lstOutStock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lstOutStock.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstOutStock);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 200, 110));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Change Selected");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 190, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Delete Selected");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 190, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Add Stock To Selected");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 190, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Change Price");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, 190, -1));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Add Stock To Selected");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 190, -1));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setText("Change Selected");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 190, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Out of Stock");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Low Stock");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        lstLowStock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lstLowStock.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lstLowStock);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 200, 110));

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
            txtBxUpdate.setText("Enter Item ID");
        }
    }//GEN-LAST:event_txtBxUpdateFocusLost

    private void txtBxUpdateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxUpdateFocusGained
        if (txtBxUpdate.getText().equals("Enter Item ID")) {
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
        txtBxUpdate.setText(items[0]);
        txtBxNameUpdate.setText(items[1]);
        cmbBxItemTypesUpdate.setSelectedItem(items[2]);
        txtBxStockUpdate.setText(items[3]);
        txtBxPriceUpdate.setText(items[4]);
    }//GEN-LAST:event_tblItemsMouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement getItemData = conn.prepareStatement("SELECT * FROM items WHERE ItemName=?");
            getItemData.setString(1, lstOutStock.getSelectedValue());
            ResultSet rs = getItemData.executeQuery();

            while (rs.next()) {
                txtBxUpdate.setText(rs.getString("ItemName"));
                txtBxNameUpdate.setText(rs.getString("ItemName"));
                txtBxPriceUpdate.setText(rs.getString("Price"));
                cmbBxItemTypesUpdate.setSelectedIndex(rs.getInt("ITEMTYPE_ItemTypeID"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtBxStockUpdate.grabFocus();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        txtBxNameUpdate.grabFocus();
        txtBxUpdate.setText(lstOutStock.getSelectedValue());
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement deleteItem = conn.prepareCall("DELETE FROM items WHERE ItemName=?");
            deleteItem.setString(1, lstOutStock.getSelectedValue());
            deleteItem.executeUpdate();

            PromptDialog promptDialog = new PromptDialog("Operation successful", "Item Deleted");
            promptDialog.setResizable(false);
            promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
            promptDialog.setVisible(true);

            conn.close();
            setTable();
            getOutOfStock();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        txtBxNameUpdate.grabFocus();
        txtBxUpdate.setText(lstLowStock.getSelectedValue());
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement getItemData = conn.prepareStatement("SELECT * FROM items WHERE ItemName=?");
            getItemData.setString(1, lstLowStock.getSelectedValue());
            ResultSet rs = getItemData.executeQuery();

            while (rs.next()) {
                txtBxUpdate.setText(rs.getString("ItemName"));
                txtBxNameUpdate.setText(rs.getString("ItemName"));
                txtBxStockUpdate.setText(rs.getString("Stock"));
                cmbBxItemTypesUpdate.setSelectedIndex(rs.getInt("ITEMTYPE_ItemTypeID"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtBxPriceUpdate.grabFocus();
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement getItemData = conn.prepareStatement("SELECT * FROM items WHERE ItemName=?");
            getItemData.setString(1, lstLowStock.getSelectedValue());
            ResultSet rs = getItemData.executeQuery();

            while (rs.next()) {
                txtBxUpdate.setText(rs.getString("ItemName"));
                txtBxNameUpdate.setText(rs.getString("ItemName"));
                txtBxPriceUpdate.setText(rs.getString("Price"));
                cmbBxItemTypesUpdate.setSelectedIndex(rs.getInt("ITEMTYPE_ItemTypeID"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtBxStockUpdate.grabFocus();
    }//GEN-LAST:event_jButton5MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbBxItemTypesAdd;
    private javax.swing.JComboBox<String> cmbBxItemTypesUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> lstLowStock;
    private javax.swing.JList<String> lstOutStock;
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
