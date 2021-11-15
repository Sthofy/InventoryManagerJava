package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;
import java.sql.*;
import javax.swing.DefaultListModel;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class DashboardPanel extends java.awt.Panel {

    private Integer outOfStock = 0;
    private Integer lowStock = 0;
    private int i = 0;

    public DashboardPanel() {
        initComponents();
        getOutOfStock();
        getLowStock();
    }

    private void getOutOfStock() {
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement getOutStock = conn.prepareStatement("SELECT ItemName FROM items,itemtype WHERE ItemTypeID=ITEMTYPE_ItemTypeID AND Stock=" + 0);
            ResultSet getOutStockRs = getOutStock.executeQuery();
            i = 0;
            DefaultListModel model = new DefaultListModel();
            while (getOutStockRs.next()) {
                model.add(i, getOutStockRs.getString(1));
                outOfStock++;
                i++;
            }
            lstOutStock.setModel(model);
            lblNumberOfOutStock.setText(outOfStock.toString());
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getLowStock() {
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement getLowStock = conn.prepareStatement("SELECT ItemName FROM items,itemtype WHERE ItemTypeID=ITEMTYPE_ItemTypeID AND Stock BETWEEN " + 1 + " AND " + 5);
            ResultSet getLowStockRs = getLowStock.executeQuery();
            i = 0;
            DefaultListModel model = new DefaultListModel();
            while (getLowStockRs.next()) {
                model.add(i, getLowStockRs.getString(1));
                lowStock++;
                i++;
            }
            lstLowStock.setModel(model);
            lblNumberOfLowStock.setText(lowStock.toString());
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlWarehouse = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNumberOfLowStock = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstLowStock = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        lblNumberOfOutStock = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstOutStock = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(204, 204, 204));
        setMaximumSize(new java.awt.Dimension(970, 680));
        setMinimumSize(new java.awt.Dimension(970, 680));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(970, 680));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlWarehouse.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Warehouse Overview");
        pnlWarehouse.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 250, 30));
        jLabel1.getAccessibleContext().setAccessibleName("");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 0));
        jLabel2.setText("Low Stock:");
        pnlWarehouse.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 130, 30));

        lblNumberOfLowStock.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pnlWarehouse.add(lblNumberOfLowStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 50, 30));

        lstLowStock.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jScrollPane1.setViewportView(lstLowStock);

        pnlWarehouse.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 250, 140));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Out of Stock:");
        pnlWarehouse.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 130, 30));

        lblNumberOfOutStock.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pnlWarehouse.add(lblNumberOfOutStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 50, 30));

        lstOutStock.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jScrollPane2.setViewportView(lstOutStock);

        pnlWarehouse.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 250, 140));

        add(pnlWarehouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 30, 290, 500));
        pnlWarehouse.getAccessibleContext().setAccessibleName("");

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 290, 500));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 30, 290, 500));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNumberOfLowStock;
    private javax.swing.JLabel lblNumberOfOutStock;
    private javax.swing.JList<String> lstLowStock;
    private javax.swing.JList<String> lstOutStock;
    private javax.swing.JPanel pnlWarehouse;
    // End of variables declaration//GEN-END:variables
}
