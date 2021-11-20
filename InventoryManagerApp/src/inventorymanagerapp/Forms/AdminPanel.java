package inventorymanagerapp.Forms;

import inventorymanagerapp.others.DatabaseManager;
import inventorymanagerapp.others.PasswordManager;

import java.sql.*;
import java.util.Vector;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.*;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class AdminPanel extends java.awt.Panel {

    private TableRowSorter<TableModel> rowSorter;

    public AdminPanel() {
        initComponents();
        setTable();
    }

    private void setTable() {
        Connection conn = DatabaseManager.getConnection();
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        rowSorter = new TableRowSorter<>(tblUsers.getModel());
        tblUsers.setRowSorter(rowSorter);

        try {
            PreparedStatement getItems = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = getItems.executeQuery();

            while (rs.next()) {

                Vector row = new Vector();
                row.add(rs.getString("Username"));
                row.add(rs.getString("Email"));
                row.add(rs.getString("AccessLevel"));
                model.addRow(row);

                tblUsers.setModel(model);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Filter() {
        String text = txtBxFilter.getText();
        rowSorter = new TableRowSorter<>(tblUsers.getModel());
        tblUsers.setRowSorter(rowSorter);
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void updateUser() {
        Connection conn = DatabaseManager.getConnection();
        try {
            if (!txtBxUsernameChange.getText().isEmpty() && !txtBxEmailChange.getText().isEmpty() && !pwChange.getText().isEmpty()) {
                String tempUsername = txtBxUsernameChange.getText();
                String salt = PasswordManager.getNextSalt();

                PreparedStatement ps = conn.prepareStatement("UPDATE users SET Password=?, Email=?, AccessLevel=? WHERE Username=?");

                ps.setString(4, tempUsername);
                ps.setString(2, txtBxEmailChange.getText());
                if (rdBtnAdminChange.isSelected()) {
                    ps.setString(3, "Admin");
                } else if (rdBtnEmployeeChange.isSelected()) {
                    ps.setString(3, "Employee");
                }
                ps.setString(1, PasswordManager.hash(pwChange.getText(), salt));

                ps.executeUpdate();

                PromptDialog promptDialog = new PromptDialog("Operation Successfull", "User Updated!");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);
            } else {
                PromptDialog promptDialog = new PromptDialog("Error", "Missing Information!");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTable();
    }

    private void addUser() {
        Connection conn = DatabaseManager.getConnection();
        try {
            if (!txtBxUsernameAdd.getText().isEmpty() && !txtBxEmailAdd.getText().isEmpty() && !pwAdd.getText().isEmpty()) {
                String salt = PasswordManager.getNextSalt();

                PreparedStatement ps = conn.prepareStatement("INSERT users (Username,Password,Email,AccessLevel) VALUES (?,?,?,?)");
                PreparedStatement getUsers = conn.prepareStatement("SELECT * FROM users WHERE Username=?");

                getUsers.setString(1, txtBxUsernameAdd.getText());

                ResultSet rs = getUsers.executeQuery();
                while (rs.next()) {
                    PromptDialog promptDialog = new PromptDialog("Error", "Username already exist! Try another Username!");
                    promptDialog.setResizable(false);
                    promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                    promptDialog.setVisible(true);
                }

                ps.setString(1, txtBxUsernameAdd.getText());
                ps.setString(3, txtBxEmailAdd.getText());
                if (rdBtnAdminAdd.isSelected()) {
                    ps.setString(4, "Admin");
                } else if (rdBtnEmployeeAdd.isSelected()) {
                    ps.setString(4, "Employee");
                }
                ps.setString(2, PasswordManager.hash(pwChange.getText(), salt));

                ps.executeUpdate();

                PromptDialog promptDialog = new PromptDialog("Operation Successfull", "User Updated!");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);

            } else {
                PromptDialog promptDialog = new PromptDialog("Error", "Missing Information!");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTable();
    }

    private void userDelete() {
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE Username=?");
            PreparedStatement checkOff=conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            PreparedStatement checkOn=conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1");
            checkOff.execute();
            if (!txtBxDelete.getText().isEmpty()) {
                ps.setString(1, txtBxDelete.getText());
            } else {
                PromptDialog promptDialog = new PromptDialog("Error", "Missing information!");
                promptDialog.setResizable(false);
                promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
                promptDialog.setVisible(true);
            }

            ps.executeUpdate();
            checkOn.execute();

            PromptDialog promptDialog = new PromptDialog("Operation Successfull", "User Deleted!");
            promptDialog.setResizable(false);
            promptDialog.setDefaultCloseOperation(PromptDialog.DISPOSE_ON_CLOSE);
            promptDialog.setVisible(true);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTable();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpAdd = new javax.swing.ButtonGroup();
        btnGrpChange = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBxFilter = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBxUsernameAdd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBxEmailAdd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pwAdd = new javax.swing.JPasswordField();
        rdBtnAdminAdd = new javax.swing.JRadioButton();
        rdBtnEmployeeAdd = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        chckBxAdd = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtBxUsernameChange = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtBxEmailChange = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        rdBtnAdminChange = new javax.swing.JRadioButton();
        rdBtnEmployeeChange = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        pwChange = new javax.swing.JPasswordField();
        chckBxChange = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtBxDelete = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        btnGrpAdd.add(rdBtnAdminAdd);
        btnGrpAdd.add(rdBtnEmployeeAdd);

        btnGrpChange.add(rdBtnAdminChange);
        btnGrpChange.add(rdBtnEmployeeChange);

        setBackground(new java.awt.Color(204, 204, 204));
        setMaximumSize(new java.awt.Dimension(970, 670));
        setMinimumSize(new java.awt.Dimension(970, 670));
        setPreferredSize(new java.awt.Dimension(970, 670));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblUsers.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Email", "Access Level"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsers);
        if (tblUsers.getColumnModel().getColumnCount() > 0) {
            tblUsers.getColumnModel().getColumn(0).setResizable(false);
            tblUsers.getColumnModel().getColumn(1).setResizable(false);
            tblUsers.getColumnModel().getColumn(2).setResizable(false);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 220));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Search:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 70, 20));

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
        add(txtBxFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 750, 20));

        btnFilter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFilter.setText("OK");
        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterMouseClicked(evt);
            }
        });
        add(btnFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 240, 70, 20));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Add");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, -1));
        jPanel1.add(txtBxUsernameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 320, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Email");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 320, -1));
        jPanel1.add(txtBxEmailAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 320, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Password");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 320, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Acces Level");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 320, -1));
        jPanel1.add(pwAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 290, -1));

        rdBtnAdminAdd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdBtnAdminAdd.setText("Admin");
        jPanel1.add(rdBtnAdminAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        rdBtnEmployeeAdd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdBtnEmployeeAdd.setText("Employee");
        jPanel1.add(rdBtnEmployeeAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Add User");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 180, -1));

        chckBxAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckBxAddActionPerformed(evt);
            }
        });
        jPanel1.add(chckBxAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Username");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 320, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 365, 310));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Change Selected");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Username");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 320, -1));
        jPanel2.add(txtBxUsernameChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 320, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Email");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 320, -1));
        jPanel2.add(txtBxEmailChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 320, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Password");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 320, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Acces Level");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 320, -1));

        rdBtnAdminChange.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdBtnAdminChange.setText("Admin");
        jPanel2.add(rdBtnAdminChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        rdBtnEmployeeChange.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdBtnEmployeeChange.setText("Employee");
        jPanel2.add(rdBtnEmployeeChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Change");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 180, -1));
        jPanel2.add(pwChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 290, -1));

        chckBxChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckBxChangeActionPerformed(evt);
            }
        });
        jPanel2.add(chckBxChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(525, 280, 365, 310));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Delete User");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 150, 20));

        txtBxDelete.setText("Enter Username");
        txtBxDelete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBxDeleteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBxDeleteFocusLost(evt);
            }
        });
        jPanel3.add(txtBxDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 500, 20));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Delete");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, -1));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 600, 810, 60));
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

    private void txtBxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBxFilterActionPerformed
        Filter();
    }//GEN-LAST:event_txtBxFilterActionPerformed

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked
        Filter();
    }//GEN-LAST:event_btnFilterMouseClicked

    private void chckBxAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckBxAddActionPerformed
        if (chckBxAdd.isSelected()) {
            pwAdd.setEchoChar('\u0000');
        } else {
            pwAdd.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
        }
    }//GEN-LAST:event_chckBxAddActionPerformed

    private void chckBxChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckBxChangeActionPerformed
        if (chckBxChange.isSelected()) {
            pwChange.setEchoChar('\u0000');
        } else {
            pwChange.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
        }
    }//GEN-LAST:event_chckBxChangeActionPerformed

    private void tblUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsersMouseClicked
        DefaultTableModel tableModel = (DefaultTableModel) tblUsers.getModel();
        Object elementAt = tableModel.getDataVector().elementAt(tblUsers.getSelectedRow());

        String selectedItems = elementAt.toString().substring(1, elementAt.toString().length() - 1);
        String[] items = selectedItems.split(", ");
        txtBxUsernameChange.setText(items[0]);
        txtBxEmailChange.setText(items[1]);
        if (items[2].equals("Admin")) {
            rdBtnAdminChange.setSelected(true);
        } else {
            rdBtnEmployeeChange.setSelected(true);
        }


    }//GEN-LAST:event_tblUsersMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        updateUser();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        addUser();
    }//GEN-LAST:event_jButton1MouseClicked

    private void txtBxDeleteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxDeleteFocusGained
        if (txtBxDelete.getText().equals("Enter Username")) {
            txtBxDelete.setText("");
        }
    }//GEN-LAST:event_txtBxDeleteFocusGained

    private void txtBxDeleteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBxDeleteFocusLost
        if (txtBxDelete.getText().equals("")) {
            txtBxDelete.setText("Enter Username");
        }
    }//GEN-LAST:event_txtBxDeleteFocusLost

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        userDelete();
    }//GEN-LAST:event_jButton3MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.ButtonGroup btnGrpAdd;
    private javax.swing.ButtonGroup btnGrpChange;
    private javax.swing.JCheckBox chckBxAdd;
    private javax.swing.JCheckBox chckBxChange;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField pwAdd;
    private javax.swing.JPasswordField pwChange;
    private javax.swing.JRadioButton rdBtnAdminAdd;
    private javax.swing.JRadioButton rdBtnAdminChange;
    private javax.swing.JRadioButton rdBtnEmployeeAdd;
    private javax.swing.JRadioButton rdBtnEmployeeChange;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtBxDelete;
    private javax.swing.JTextField txtBxEmailAdd;
    private javax.swing.JTextField txtBxEmailChange;
    private javax.swing.JTextField txtBxFilter;
    private javax.swing.JTextField txtBxUsernameAdd;
    private javax.swing.JTextField txtBxUsernameChange;
    // End of variables declaration//GEN-END:variables
}
