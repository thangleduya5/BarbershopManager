/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BillController;
import Controller.DBConnection;
import Controller.UserController;
import Controller.pdf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Admin
 */
public class AccountJPanel extends javax.swing.JPanel {

    /**
     * Creates new form AccountJPanel
     */
    int selectedRow = 0;
    String fFullName = "";
    DefaultTableModel defaultTableModel = new DefaultTableModel();

    public AccountJPanel() {
        initComponents();
        showUser();
        FilterTable();
    }

    public void FilterTable() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) jTableAccount.getModel()));

        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter(jTextFieldSearch.getText()));
                jTableAccount.setRowSorter(sorter);

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter(jTextFieldSearch.getText()));
                jTableAccount.setRowSorter(sorter);

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public void showUser() {
        defaultTableModel.setNumRows(0);
        defaultTableModel.setColumnCount(0);
        jTableAccount.setEditingColumn(0);
        jTableAccount.setEditingRow(0);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Tên đăng nhập");
        defaultTableModel.addColumn("Tên đầy đủ");
        defaultTableModel.addColumn("Tuổi");
        defaultTableModel.addColumn("Giới tính");
        defaultTableModel.addColumn("Địa chỉ");
        Connection con = DBConnection.getConnection();
        Vector vt;
        try {
            PreparedStatement pre = con.prepareStatement("select *from INFORS");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                vt = new Vector();
                vt.add(re.getString("id"));
                vt.add(re.getString("userName"));
                vt.add(re.getString("fullName"));
                vt.add(re.getString("age"));
                vt.add(re.getString("sex"));
                vt.add(re.getString("address"));
                defaultTableModel.addRow(vt);
            }
            jTableAccount.setModel(defaultTableModel);
        } catch (SQLException ex) {
            Logger.getLogger(AccountJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String ChuanHoaTen(String ten) {
        ten = ten.trim();
        ten = ten.toLowerCase();
        ten = ten.replaceAll("\\s+", " ");
        String temp[] = ten.split(" ");
        ten = "";
        for (int i = 0; i < temp.length; i++) {
            ten += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) {
                ten += " ";
            }
        }
        return ten;
    }

    public boolean testData(String fullName, String age, String sex, String address) {
        if (fullName.equals("") || fullName.length() > 50 || fullName.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Họ tên không hợp lệ, tên không vượt quá 50 ký tự");
            return false;
        }
        String parttern = "[0-9]{2}";
        if (age.equals("") || !age.matches(parttern)) {
            JOptionPane.showMessageDialog(this, "Tuổi không hợp lệ");
            return false;
        }
        if (Integer.parseInt(age) <= 18 || Integer.parseInt(age) >= 40) {
            JOptionPane.showMessageDialog(this, "Ít nhất 19 tuổi, nhiều nhất 39 tuổi");
            return false;
        }
        if (address.equals("") || address.length() > 100) {
            JOptionPane.showMessageDialog(this, "Địa chỉ trống hoặc không hợp lệ, >100 ký tự");
            return false;
        }
        return true;
    }

    public String randomUserName() {
        String result;
        do {
            result = "";
            Random r = new Random();
            for (int i = 0; i < 10; i++) {
                result += (char) (r.nextInt(26) + 'a');
            }
        } while (UserController.ExistUser(result));
        return result;
    }

    public String randomID() {
        String result;
        do {
            result = "U";
            Random r = new Random();
            for (int i = 0; i < 3; i++) {
                result += r.nextInt(9);
            }
        } while (UserController.ExistInfor(result));
        return result;
    }

    public void displayInfor() {
        fFullName = defaultTableModel.getValueAt(selectedRow, 2).toString();
        jTextFieldFullName.setText(fFullName);
        jTextFieldAge.setText(defaultTableModel.getValueAt(selectedRow, 3).toString());
        jTextAreaAddress.setText(defaultTableModel.getValueAt(selectedRow, 5).toString());
        String sex = defaultTableModel.getValueAt(selectedRow, 4).toString().trim();
        if (sex.equals("Nam")) {
            jComboBoxSex.setSelectedIndex(0);
        } else {
            jComboBoxSex.setSelectedIndex(1);
        }
    }

    public void resetInfor() {
        jTextFieldFullName.setText("");
        jTextAreaAddress.setText("");
        jTextFieldAge.setText("");
        jComboBoxSex.setSelectedIndex(0);
    }

    public void resetStatus(boolean x) {
        btnDelete.setEnabled(x);
        btnUpdate.setEnabled(x);
        jButtonAdd.setEnabled(!x);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabelAge = new javax.swing.JLabel();
        jLabelFullName = new javax.swing.JLabel();
        jLabelSex = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jTextFieldFullName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAddress = new javax.swing.JTextArea();
        jComboBoxSex = new javax.swing.JComboBox<>();
        jTextFieldAge = new javax.swing.JTextField();
        jButtonAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAccount = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonRender = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1500, 800));

        jPanel2.setBackground(java.awt.Color.gray);
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 800));

        jLabelAge.setBackground(new java.awt.Color(51, 153, 255));
        jLabelAge.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelAge.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAge.setText("Tuổi");
        jLabelAge.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelAge.setOpaque(true);

        jLabelFullName.setBackground(new java.awt.Color(51, 153, 255));
        jLabelFullName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelFullName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFullName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFullName.setText("Họ và tên");
        jLabelFullName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelFullName.setOpaque(true);

        jLabelSex.setBackground(new java.awt.Color(51, 153, 255));
        jLabelSex.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelSex.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSex.setText("Giới tính");
        jLabelSex.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelSex.setOpaque(true);

        jLabelAddress.setBackground(new java.awt.Color(51, 153, 255));
        jLabelAddress.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelAddress.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAddress.setText("Địa chỉ");
        jLabelAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelAddress.setOpaque(true);

        jTextFieldFullName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldFullName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextFieldFullName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel8.setBackground(new java.awt.Color(51, 153, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("THÔNG TIN NHÂN VIÊN");
        jLabel8.setOpaque(true);

        jTextAreaAddress.setColumns(20);
        jTextAreaAddress.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextAreaAddress.setRows(5);
        jTextAreaAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jScrollPane1.setViewportView(jTextAreaAddress);

        jComboBoxSex.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboBoxSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        jTextFieldAge.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldAge.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jButtonAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_add_30px_1.png"))); // NOI18N
        jButtonAdd.setText("THÊM ");
        jButtonAdd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        jButtonAdd.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_delete_30px.png"))); // NOI18N
        btnDelete.setText("XÓA ");
        btnDelete.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnDelete.setEnabled(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_update_30px_1.png"))); // NOI18N
        btnUpdate.setText("CẬP NHẬT ");
        btnUpdate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnUpdate.setEnabled(false);
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabelAge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextFieldAge, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelSex, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxSex, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSex, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSex, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(java.awt.Color.lightGray);
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 800));

        jTableAccount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTableAccount.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTableAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên đăng nhập", "Tên đầy đủ", "Tuổi", "Giới tính", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAccountMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableAccount);
        if (jTableAccount.getColumnModel().getColumnCount() > 0) {
            jTableAccount.getColumnModel().getColumn(0).setResizable(false);
            jTableAccount.getColumnModel().getColumn(1).setResizable(false);
            jTableAccount.getColumnModel().getColumn(2).setResizable(false);
            jTableAccount.getColumnModel().getColumn(3).setResizable(false);
            jTableAccount.getColumnModel().getColumn(4).setResizable(false);
            jTableAccount.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel9.setBackground(new java.awt.Color(51, 153, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DANH SÁCH NHÂN VIÊN");
        jLabel9.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(51, 153, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_search_30px_2.png"))); // NOI18N
        jLabel11.setText("Tìm kiếm");
        jLabel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel11.setOpaque(true);
        jLabel11.setPreferredSize(new java.awt.Dimension(89, 19));

        jTextFieldSearch.setBackground(java.awt.Color.lightGray);
        jTextFieldSearch.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextFieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jTextFieldSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldSearch.setOpaque(false);
        jTextFieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSearchActionPerformed(evt);
            }
        });

        jButtonRender.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonRender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_outdent_30px.png"))); // NOI18N
        jButtonRender.setText("KẾT XUẤT");
        jButtonRender.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        jButtonRender.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonRender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 346, Short.MAX_VALUE)
                        .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAccountMouseClicked
        selectedRow = jTableAccount.getSelectedRow();
        displayInfor();
        resetStatus(true);
    }//GEN-LAST:event_jTableAccountMouseClicked

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void jButtonRenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenderActionPerformed
        pdf.pdfUser();
        JOptionPane.showMessageDialog(this, "Kết xuất thành công");
    }//GEN-LAST:event_jButtonRenderActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        String fullName = jTextFieldFullName.getText().toString().trim();
        String age = jTextFieldAge.getText().toString();
        String sex = jComboBoxSex.getSelectedItem().toString();
        String address = jTextAreaAddress.getText().toString().trim();
        if (testData(fullName, age, sex, address)) {
            fullName = ChuanHoaTen(fullName);
            if (UserController.ExistFullName(fullName)) {
                JOptionPane.showMessageDialog(this, "Họ tên đã tồn tại");
                return;
            }
            String userName = randomUserName();
            String id = randomID();
            UserController.addUser(userName);
            UserController.addInfor(id, userName, fullName, Integer.parseInt(age), sex, address);
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            showUser();
            resetInfor();
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String userName = defaultTableModel.getValueAt(selectedRow, 1).toString();
        String id = defaultTableModel.getValueAt(selectedRow, 0).toString();
        if (BillController.existUserBill(id)) {
            JOptionPane.showMessageDialog(this, "Không thể xóa");
            resetInfor();
            resetStatus(false);
            return;
        }
        UserController.deleteInfor(id);
        UserController.deleteUser(userName);
        showUser();
        JOptionPane.showMessageDialog(this, "Đá xóa thành công tài khoản: " + userName);
        resetStatus(false);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String fullName = jTextFieldFullName.getText().toString().trim();
        String age = jTextFieldAge.getText().toString();
        String sex = jComboBoxSex.getSelectedItem().toString();
        String address = jTextAreaAddress.getText().toString();
        if (testData(fullName, age, sex, address)) {
            if (!fullName.equals(fullName)) {
                if (UserController.ExistFullName(fullName)) {
                    JOptionPane.showMessageDialog(this, "Họ tên đã tồn tại");
                    return;
                }
            }
            UserController.modifyInfor(defaultTableModel.getValueAt(selectedRow, 0).toString(), fullName, Integer.parseInt(age), sex, address);
            showUser();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            jButtonAdd.setText("THÊM");
            resetInfor();
        }
        resetStatus(false);
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonRender;
    private javax.swing.JComboBox<String> jComboBoxSex;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelAge;
    private javax.swing.JLabel jLabelFullName;
    private javax.swing.JLabel jLabelSex;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableAccount;
    private javax.swing.JTextArea jTextAreaAddress;
    private javax.swing.JTextField jTextFieldAge;
    private javax.swing.JTextField jTextFieldFullName;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
