package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;
import inventorymanagerapp.others.Purchases;
import inventorymanagerapp.others.pdfCreator;
import java.io.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.table.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class PurchasesPanel extends java.awt.Panel {

    private TableRowSorter<TableModel> rowSorter;

    public PurchasesPanel() {
        initComponents();
        setTable();
    }

    private void setTable() {
        Connection conn = DatabaseManager.getConnection();
        DefaultTableModel model = (DefaultTableModel) tblPurchases.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        rowSorter = new TableRowSorter<>(tblPurchases.getModel());
        tblPurchases.setRowSorter(rowSorter);

        try {
            PreparedStatement getItems = conn.prepareStatement("SELECT * FROM purchases,users,items,accounts WHERE purchases.ITEMS_ItemID=items.ItemID AND purchases.USERS_Username=USERS.username "
                    + "AND purchases.ACCOUNTS_AccountID=ACCOUNTS.AccountID ORDER BY PurchaseDate DESC");
            ResultSet rs = getItems.executeQuery();

            while (rs.next()) {

                Purchases purchases = new Purchases(rs.getInt("PurchaseID"),
                        rs.getDate("PurchaseDate"),
                        rs.getInt("Quantity"),
                        rs.getDouble("AmountDue"),
                        rs.getString("AccountName"),
                        rs.getString("Username"),
                        rs.getString("ItemName"));

                Vector row = new Vector();
                row.add(purchases.getPurchaseID());
                row.add(purchases.getPurchaseDate().toString());
                row.add(purchases.getQuantity());
                row.add(purchases.getAmountDue() + " $");
                row.add(purchases.getAccountName());
                row.add(purchases.getUsername());
                row.add(purchases.getItemName());
                model.addRow(row);

                tblPurchases.setModel(model);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Filter() {
        String text = txtBxFilter.getText();
        rowSorter = new TableRowSorter<>(tblPurchases.getModel());
        tblPurchases.setRowSorter(rowSorter);
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private Purchases getSelectedRow() {
        DefaultTableModel tableModel = (DefaultTableModel) tblPurchases.getModel();
        Object elementAt = tableModel.getDataVector().elementAt(tblPurchases.getSelectedRow());

        String selectedItems = elementAt.toString().substring(1, elementAt.toString().length() - 1);
        String[] items = selectedItems.split(", ");
        Purchases purchase = new Purchases(Integer.valueOf(items[0]), Date.valueOf(items[1]), Integer.valueOf(items[2]), Double.valueOf(items[3].replace(" $", "")), items[4], items[5], items[6]);

        return purchase;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPurchases = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBxFilter = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtBxItemNameAdd = new javax.swing.JTextField();
        txtBxQuantityAdd = new javax.swing.JTextField();
        txtBxAmountAdd = new javax.swing.JTextField();
        txtBxUsernameAdd = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtBxAmountChange = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBxDateChange = new javax.swing.JTextField();
        txtBxItemNameChange = new javax.swing.JTextField();
        txtBxQuantityChange = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtBxUsernameChange = new javax.swing.JTextField();
        btnChange = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));
        setMaximumSize(new java.awt.Dimension(970, 670));
        setMinimumSize(new java.awt.Dimension(970, 670));
        setPreferredSize(new java.awt.Dimension(970, 670));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPurchases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Purchase Date", "Quantity", "Amount", "Account Name", "Username", "Item Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPurchases);
        if (tblPurchases.getColumnModel().getColumnCount() > 0) {
            tblPurchases.getColumnModel().getColumn(0).setResizable(false);
            tblPurchases.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblPurchases.getColumnModel().getColumn(1).setResizable(false);
            tblPurchases.getColumnModel().getColumn(1).setPreferredWidth(5);
            tblPurchases.getColumnModel().getColumn(2).setResizable(false);
            tblPurchases.getColumnModel().getColumn(2).setPreferredWidth(5);
            tblPurchases.getColumnModel().getColumn(3).setResizable(false);
            tblPurchases.getColumnModel().getColumn(3).setPreferredWidth(5);
            tblPurchases.getColumnModel().getColumn(4).setResizable(false);
            tblPurchases.getColumnModel().getColumn(5).setResizable(false);
            tblPurchases.getColumnModel().getColumn(6).setResizable(false);
            tblPurchases.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 360));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Search:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 70, 20));

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
        add(txtBxFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 750, 20));

        btnFilter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFilter.setText("OK");
        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterMouseClicked(evt);
            }
        });
        add(btnFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 380, 70, 20));

        btnPrint.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrint.setText("Print Selected");
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });
        add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 500, 130, 140));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Add New");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 5, 930, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("ItemName");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, -1, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Quantity");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Amount");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, -1, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("UserName");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, -1, 20));

        txtBxItemNameAdd.setText("jTextField1");
        jPanel1.add(txtBxItemNameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 100, -1));

        txtBxQuantityAdd.setText("jTextField1");
        jPanel1.add(txtBxQuantityAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 100, -1));

        txtBxAmountAdd.setText("jTextField1");
        jPanel1.add(txtBxAmountAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 100, -1));

        txtBxUsernameAdd.setText("jTextField1");
        jPanel1.add(txtBxUsernameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 100, -1));

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAdd.setText("Add");
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, 80, 60));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 940, 80));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBxAmountChange.setText("jTextField1");
        jPanel2.add(txtBxAmountChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 100, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("UserName");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Purchase Date");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Amount");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, -1, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("ItemName");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, -1, 20));

        txtBxDateChange.setText("jTextField1");
        jPanel2.add(txtBxDateChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 100, -1));

        txtBxItemNameChange.setText("jTextField1");
        jPanel2.add(txtBxItemNameChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 100, -1));

        txtBxQuantityChange.setText("jTextField1");
        jPanel2.add(txtBxQuantityChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 100, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Change");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 5, 790, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Quantity");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        txtBxUsernameChange.setText("jTextField1");
        jPanel2.add(txtBxUsernameChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 100, -1));

        btnChange.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChange.setText("Change");
        jPanel2.add(btnChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 100, 80));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 800, 140));
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked
        Filter();
    }//GEN-LAST:event_btnFilterMouseClicked

    private void txtBxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBxFilterActionPerformed
        Filter();
    }//GEN-LAST:event_txtBxFilterActionPerformed

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked

        Purchases purchase = getSelectedRow();

        JFileChooser saveChooser = new JFileChooser();
        saveChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        saveChooser.setFont(new java.awt.Font("Arial", 0, 10));
        saveChooser.setForeground(new java.awt.Color(0, 0, 0));
        saveChooser.setSelectedFile(new File(purchase.getAccountName() + ".pdf"));
        saveChooser.showSaveDialog(this);

        pdfCreator.CreatePDF(purchase, saveChooser.getSelectedFile());
    }//GEN-LAST:event_btnPrintMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPurchases;
    private javax.swing.JTextField txtBxAmountAdd;
    private javax.swing.JTextField txtBxAmountChange;
    private javax.swing.JTextField txtBxDateChange;
    private javax.swing.JTextField txtBxFilter;
    private javax.swing.JTextField txtBxItemNameAdd;
    private javax.swing.JTextField txtBxItemNameChange;
    private javax.swing.JTextField txtBxQuantityAdd;
    private javax.swing.JTextField txtBxQuantityChange;
    private javax.swing.JTextField txtBxUsernameAdd;
    private javax.swing.JTextField txtBxUsernameChange;
    // End of variables declaration//GEN-END:variables

}
