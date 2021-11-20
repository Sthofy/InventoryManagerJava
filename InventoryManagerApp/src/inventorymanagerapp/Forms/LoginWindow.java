package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;
import inventorymanagerapp.others.ImageEditor;
import inventorymanagerapp.others.PasswordManager;

import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.UIManager;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class LoginWindow extends javax.swing.JFrame {

    public static String loggerUsername = "";
    public static String loggerAccessLevel = "";
    private int startedCount = 0;

    public LoginWindow() {
        initComponents();
        ImageEditor.ScaleImage(lblIcon, "User_Icon.png");
        FillFromUserCredents();
    }

    private void FillFromUserCredents() {

        try {
            try (Connection connection = DatabaseManager.getConnection()) {
                PreparedStatement getCredents = connection.prepareStatement("SELECT * FROM usercredents");
                ResultSet resultSet = getCredents.executeQuery();

                if (resultSet.next()) {
                    TxtBxUsername.setText(resultSet.getString("Username")); //Getting Saved Username
                    TxtBxPassword.setText(resultSet.getString("Password")); //Getting Saved Password
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void userLogger() {

        PromptDialog promptDialog;
        String username, password;

        if (TxtBxPassword.getText().equals("") || TxtBxUsername.getText().equals("")) {
            promptDialog = new PromptDialog("Error", "Missing Username or Password");
            promptDialog.setResizable(false);
            promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
            promptDialog.setVisible(true);
        } else {
            username = TxtBxUsername.getText();
            password = TxtBxPassword.getText();

            try {
                Connection conn = DatabaseManager.getConnection();
                String sql = "SELECT * FROM users WHERE username = ? ";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("Password");
                    String salt = storedPassword.substring(0, 24);

                    if (PasswordManager.isValidPassword(password, salt, storedPassword)) {
                        loggerUsername = rs.getString("Username");
                        loggerAccessLevel = rs.getString("AccessLevel");

                        if (ChkBxSaveCredents.isSelected()) {
                            PreparedStatement delPrevCredents = conn.prepareStatement("DELETE FROM usercredents");
                            delPrevCredents.executeUpdate();

                            PreparedStatement saveCredents = conn.prepareStatement("INSERT INTO usercredents VALUES ('" + username + "'," + "'" + password + "')");
                            saveCredents.executeUpdate();
                        }

                        MainWindow mainWindow = new MainWindow();
                        mainWindow.setResizable(false);
                        mainWindow.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                        mainWindow.setVisible(true);
                        this.dispose();
                    } else {
                        promptDialog = new PromptDialog("Error", "Wrong Username Or Password!");
                        promptDialog.setResizable(false);
                        promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                        promptDialog.setVisible(true);
                    }
                } else {
                    promptDialog = new PromptDialog("Error", "Wrong Username Or Password!");
                    promptDialog.setResizable(false);
                    promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                    promptDialog.setVisible(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        TxtBxUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtBxPassword = new javax.swing.JPasswordField();
        BtnLogin = new javax.swing.JButton();
        BtnClear = new javax.swing.JButton();
        lblIcon = new javax.swing.JLabel();
        chkBxPasswordMask = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ChkBxSaveCredents = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(71, 120, 197));
        setName("LoginFrame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(23, 35, 51));

        jLabel3.setFont(new java.awt.Font("Mongolian Baiti", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Sthofy Inventory Manager");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel3)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 349, -1));

        jPanel2.setBackground(new java.awt.Color(71, 120, 197));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("UserID:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Password:");
        jLabel2.setToolTipText("");

        TxtBxPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtBxPasswordKeyPressed(evt);
            }
        });

        BtnLogin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        BtnLogin.setText("Login");
        BtnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnLoginMouseClicked(evt);
            }
        });

        BtnClear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        BtnClear.setText("Clear");
        BtnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnClearMouseClicked(evt);
            }
        });

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblIcon.setMaximumSize(new java.awt.Dimension(25, 25));
        lblIcon.setMinimumSize(new java.awt.Dimension(25, 25));
        lblIcon.setPreferredSize(new java.awt.Dimension(25, 25));

        chkBxPasswordMask.setBackground(new java.awt.Color(71, 120, 197));
        chkBxPasswordMask.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chkBxPasswordMask.setFocusPainted(false);
        chkBxPasswordMask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBxPasswordMaskAction(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Show Password:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Save Credents:");

        ChkBxSaveCredents.setBackground(new java.awt.Color(71, 120, 197));
        ChkBxSaveCredents.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ChkBxSaveCredents.setFocusPainted(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtBxUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtBxPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(chkBxPasswordMask))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(BtnLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnClear)
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(ChkBxSaveCredents)
                .addGap(102, 102, 102))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtBxUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtBxPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkBxPasswordMask, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnLogin)
                    .addComponent(BtnClear))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ChkBxSaveCredents, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(54, 54, 54))
        );

        lblIcon.getAccessibleContext().setAccessibleName("lblIcon");
        jLabel4.getAccessibleContext().setAccessibleName("Show Password");

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 350, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void chkBxPasswordMaskAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBxPasswordMaskAction
        if (chkBxPasswordMask.isSelected()) {
            TxtBxPassword.setEchoChar('\u0000');
        } else {
            TxtBxPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
        }
    }//GEN-LAST:event_chkBxPasswordMaskAction

    private void BtnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnClearMouseClicked
        TxtBxUsername.setText("");
        TxtBxPassword.setText("");
        startedCount++;
        chkBxPasswordMask.setEnabled(true);
    }//GEN-LAST:event_BtnClearMouseClicked

    private void BtnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnLoginMouseClicked
        userLogger();
    }//GEN-LAST:event_BtnLoginMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        chkBxPasswordMask.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void TxtBxPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtBxPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (TxtBxPassword.getText().equals("") && startedCount == 0) {
                startedCount++;

                if (startedCount != 0) {
                    chkBxPasswordMask.setEnabled(true);
                }
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            userLogger();
        }
    }//GEN-LAST:event_TxtBxPasswordKeyPressed

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
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnClear;
    private javax.swing.JButton BtnLogin;
    private javax.swing.JCheckBox ChkBxSaveCredents;
    private javax.swing.JPasswordField TxtBxPassword;
    private javax.swing.JTextField TxtBxUsername;
    private javax.swing.JCheckBox chkBxPasswordMask;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblIcon;
    // End of variables declaration//GEN-END:variables
}
