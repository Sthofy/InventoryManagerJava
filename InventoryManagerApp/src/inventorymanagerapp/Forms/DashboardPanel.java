package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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
        getEmployeeOfMonth();
    }

    private void getOutOfStock() {
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement getOutStock = conn.prepareStatement("SELECT ItemName FROM items WHERE Stock=" + 0);
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
            PreparedStatement getLowStock = conn.prepareStatement("SELECT ItemName FROM items WHERE Stock BETWEEN " + 1 + " AND " + 5);
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

    private void getEmployeeOfMonth() {
        Connection conn = DatabaseManager.getConnection();
        ArrayList<String> names = new ArrayList<>();
        LocalDate date = LocalDate.now();
        String tempName, tempMonth, tempYear, actualMonth = String.valueOf(date.getMonthValue()), actualYear = String.valueOf(date.getYear());

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM purchases");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tempYear = String.valueOf(rs.getDate("PurchaseDate").toString().substring(0, 4));
                tempMonth = String.valueOf(rs.getDate("PurchaseDate").toString().substring(5, 7));

                if (actualYear.equals(tempYear) && actualMonth.equals(tempMonth)) {
                    names.add(rs.getString("USERS_Username"));
                }
            }

            Collections.sort(names);
            tempName = names.get(0);
            Integer max = Collections.frequency(names, tempName);

            for (int i = 1; i < names.size(); ++i) {

                if (!tempName.equals(names.get(i))) {
                    tempName = names.get(i);
                    int tempSells = Collections.frequency(names, tempName);
                    if (tempSells > max) {
                        max = tempSells;
                        EoMName.setText(names.get(i));
                        EoMNumber.setText(max.toString());
                    }
                }

                tempName = names.get(i);
            }
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
        pnlEmployeeOfMonth = new javax.swing.JPanel();
        EoMImage = new javax.swing.JLabel();
        EoMName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        EoMNumber = new javax.swing.JLabel();
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

        pnlEmployeeOfMonth.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EoMImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventorymanagerapp/images/employeeOfMonth.png"))); // NOI18N
        EoMImage.setMaximumSize(new java.awt.Dimension(150, 150));
        EoMImage.setMinimumSize(new java.awt.Dimension(150, 150));
        EoMImage.setPreferredSize(new java.awt.Dimension(150, 150));
        pnlEmployeeOfMonth.add(EoMImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        EoMName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        EoMName.setForeground(new java.awt.Color(204, 0, 0));
        EoMName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlEmployeeOfMonth.add(EoMName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 150, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Name:");
        pnlEmployeeOfMonth.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 150, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Number of Sells:");
        pnlEmployeeOfMonth.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        EoMNumber.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        EoMNumber.setForeground(new java.awt.Color(204, 0, 0));
        EoMNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlEmployeeOfMonth.add(EoMNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 150, 40));

        add(pnlEmployeeOfMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 290, 500));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 30, 290, 500));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EoMImage;
    private javax.swing.JLabel EoMName;
    private javax.swing.JLabel EoMNumber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNumberOfLowStock;
    private javax.swing.JLabel lblNumberOfOutStock;
    private javax.swing.JList<String> lstLowStock;
    private javax.swing.JList<String> lstOutStock;
    private javax.swing.JPanel pnlEmployeeOfMonth;
    private javax.swing.JPanel pnlWarehouse;
    // End of variables declaration//GEN-END:variables
}
