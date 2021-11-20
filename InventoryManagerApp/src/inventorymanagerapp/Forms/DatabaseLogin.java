package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class DatabaseLogin extends javax.swing.JFrame {

    public DatabaseLogin() {
        initComponents();
    }

    private void login() {
        if (!dbUsername.getText().isEmpty() && !dbPassword.getText().isEmpty() && !dbIP.getText().isEmpty() && !dbPort.getText().isEmpty()) {
            DatabaseManager.set(dbUsername.getText(), dbPassword.getText(),dbIP.getText(),dbPort.getText());
            if (DatabaseManager.getConnection() == null) {
                PromptDialog promptDialog = new PromptDialog("Error", "Wrong Username or Password!");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);
            } else {
                LoginWindow Login = new LoginWindow();
                Login.setTitle("Sthofy Inventory Manager");
                Login.setDefaultCloseOperation(LoginWindow.DISPOSE_ON_CLOSE);
                Login.setResizable(false);
                Login.setSize(360, 520);
                Login.setVisible(true);

                this.dispose();
            }
        } else {
            PromptDialog promptDialog = new PromptDialog("Error", "Missing Information!");
            promptDialog.setResizable(false);
            promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
            promptDialog.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dbUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        dbPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        dbPort = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dbIP = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(390, 225));
        setMinimumSize(new java.awt.Dimension(390, 225));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setPreferredSize(new java.awt.Dimension(390, 225));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Database Login Page");
        jLabel1.setToolTipText("");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 380, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Port");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, 20));
        getContentPane().add(dbUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 200, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Database Password:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Login");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, -1, -1));

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, -1));

        dbPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dbPasswordKeyPressed(evt);
            }
        });
        getContentPane().add(dbPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 170, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Database Username:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));
        getContentPane().add(dbPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 110, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("IP");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));
        getContentPane().add(dbIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 110, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        login();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            dbPassword.setEchoChar('\u0000');
        } else {
            dbPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void dbPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dbPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login();
        }
    }//GEN-LAST:event_dbPasswordKeyPressed

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
            java.util.logging.Logger.getLogger(DatabaseLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatabaseLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatabaseLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatabaseLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatabaseLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dbIP;
    private javax.swing.JPasswordField dbPassword;
    private javax.swing.JTextField dbPort;
    private javax.swing.JTextField dbUsername;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
