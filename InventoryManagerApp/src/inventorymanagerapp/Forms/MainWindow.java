package inventorymanagerapp.Forms;

import inventorymanagerapp.others.ImageEditor;
import inventorymanagerapp.others.RoundedPanel;
import java.awt.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class MainWindow extends javax.swing.JFrame {

    private Object prevPanel;

    public MainWindow() {
        initComponents();
        firstOpened();
        setLabels();
    }

    private void setLabels() {
        ImageEditor.ScaleImage(lblIcon, "User_Icon.png");

        lblUsername.setText(LoginWindow.loggerUsername);
        if (LoginWindow.loggerAccessLevel.equals("Admin")) {
            lblAccessLevel.setForeground(Color.red);
            lblAccessLevel.setText(LoginWindow.loggerAccessLevel);
        } else {
            lblAccessLevel.setText(LoginWindow.loggerAccessLevel);
            btnAdmin.hide();
        }
    }

    private void firstOpened() {
        DashboardPanel dashboardPanel = new DashboardPanel();
        dashboardPanel.setSize(970, 670);
        dashboardPanel.setLocation(15, 15);
        ContentPanel.add(dashboardPanel);
        dashboardPanel.setVisible(true);
        prevPanel = dashboardPanel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LeftPanel = new javax.swing.JPanel();
        pnlLoggedInfo = new RoundedPanel(30,new Color(255,255,255));
        lblIcon = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblAccessLevel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnAdmin = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnWarehouse = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        Header = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();
        ContentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1280, 780));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LeftPanel.setBackground(new java.awt.Color(23, 35, 51));
        LeftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setText("jLabel1");

        lblAccessLevel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAccessLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAccessLevel.setText("jLabel1");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Logged in as:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Access Level:");

        javax.swing.GroupLayout pnlLoggedInfoLayout = new javax.swing.GroupLayout(pnlLoggedInfo);
        pnlLoggedInfo.setLayout(pnlLoggedInfoLayout);
        pnlLoggedInfoLayout.setHorizontalGroup(
            pnlLoggedInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoggedInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlLoggedInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAccessLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlLoggedInfoLayout.setVerticalGroup(
            pnlLoggedInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoggedInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoggedInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoggedInfoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsername)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAccessLevel)
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlLoggedInfo.setBackground(new Color(23,35,51));
        pnlLoggedInfo.setOpaque(false);

        LeftPanel.add(pnlLoggedInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 260, 140));

        jPanel3.setBackground(new java.awt.Color(71, 120, 197));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        LeftPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 280, 10));

        btnAdmin.setText("Administrative");
        LeftPanel.add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, 260, 70));

        btnDashboard.setText("Dashboard");
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
        });
        LeftPanel.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 260, 70));

        jButton2.setText("jButton1");
        LeftPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 260, 70));

        btnWarehouse.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnWarehouse.setText("Warehouse");
        btnWarehouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWarehouseMouseClicked(evt);
            }
        });
        LeftPanel.add(btnWarehouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 260, 70));

        jButton4.setText("jButton1");
        LeftPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 260, 70));

        getContentPane().add(LeftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 780));

        Header.setBackground(new java.awt.Color(71, 120, 197));
        Header.setAlignmentX(0.0F);
        Header.setAlignmentY(0.0F);

        lblHeader.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader.setText("Dashboard");

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(572, 572, 572))
        );

        getContentPane().add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 1000, 70));

        ContentPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ContentPanelLayout = new javax.swing.GroupLayout(ContentPanel);
        ContentPanel.setLayout(ContentPanelLayout);
        ContentPanelLayout.setHorizontalGroup(
            ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        ContentPanelLayout.setVerticalGroup(
            ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
        );

        getContentPane().add(ContentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 1000, 710));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseClicked
        ContentPanel.removeAll();
        DashboardPanel dashboardPanel = new DashboardPanel();
        dashboardPanel.setSize(970, 670);
        dashboardPanel.setLocation(15, 15);
        ContentPanel.add(dashboardPanel);
        dashboardPanel.setVisible(true);
        ContentPanel.updateUI();

    }//GEN-LAST:event_btnDashboardMouseClicked

    private void btnWarehouseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWarehouseMouseClicked
        ContentPanel.removeAll();
        WarehousePanel warehousePanel = new WarehousePanel();
        warehousePanel.setSize(970, 670);
        warehousePanel.setLocation(15, 15);
        ContentPanel.add(warehousePanel);
        warehousePanel.setVisible(true);
        ContentPanel.updateUI();
    }//GEN-LAST:event_btnWarehouseMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ContentPanel;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnWarehouse;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAccessLevel;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlLoggedInfo;
    // End of variables declaration//GEN-END:variables
}
