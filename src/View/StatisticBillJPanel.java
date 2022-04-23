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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
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
public class StatisticBillJPanel extends javax.swing.JPanel {

    int statusBill = 0;
    String fullName = "All";
    String date = "All";
    /**
     * Creates new form AccountJPanel
     */
    int selectedRow = 0;
    DefaultTableModel defaultTableModel = new DefaultTableModel();

    public StatisticBillJPanel() {
        initComponents();
        showFullName(BillController.getFullNameInBill());
        showDate(BillController.getIBDate());
        resetFullName_Date();
        resetDate();
        showBill();
    }

    public void showBill() {
        defaultTableModel.setNumRows(0);
        defaultTableModel.setColumnCount(0);
        jTableBIll.setEditingColumn(0);
        jTableBIll.setEditingRow(0);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Họ tên nhân viên");
        defaultTableModel.addColumn("Ngày lập");
        defaultTableModel.addColumn("Thành tiền");
        fullName = jComboBoxFullName.getSelectedItem().toString();
        date = jComboBoxDate.getSelectedItem().toString();
        Connection con = DBConnection.getConnection();
        Vector vt;
        try {
            PreparedStatement pre;
            if (statusBill == 0) {
                if (fullName.equals("All")) {
                    if (date.equals("All")) {
                        pre = con.prepareStatement("select *from INFORS, IN_BILL where IN_BILL.id=INFORS.id");
                    } else {
                        pre = con.prepareStatement("select *from INFORS, IN_BILL where IN_BILL.id=INFORS.id and ibDate=?");
                        pre.setDate(1, Date.valueOf(date));
                    }
                } else {
                    if (date.equals("All")) {
                        pre = con.prepareStatement("select *from INFORS, IN_BILL where IN_BILL.id=INFORS.id and fullName=?");
                        pre.setString(1, fullName);
                    } else {
                        pre = con.prepareStatement("select *from INFORS, IN_BILL where IN_BILL.id=INFORS.id and ibDate=? and fullName=?");
                        pre.setDate(1, Date.valueOf(date));
                        pre.setString(2, fullName);
                    }
                }
                ResultSet re = pre.executeQuery();
                while (re.next()) {
                    vt = new Vector();
                    vt.add(re.getString("ibID"));
                    vt.add(re.getString("fullName"));
                    vt.add(re.getString("ibDate"));
                    vt.add(BillController.getPayInBill(re.getString("ibID")));
                    defaultTableModel.addRow(vt);
                }
            } else {
                if (fullName.equals("All")) {
                    if (date.equals("All")) {
                        pre = con.prepareStatement("select *from INFORS, OUT_BILL where OUT_BILL.id=INFORS.id");
                    } else {
                        pre = con.prepareStatement("select *from INFORS, OUT_BILL where OUT_BILL.id=INFORS.id and obDate=?");
                        pre.setDate(1, Date.valueOf(date));
                    }
                } else {
                    if (date.equals("All")) {
                        pre = con.prepareStatement("select *from INFORS, OUT_BILL where OUT_BILL.id=INFORS.id and fullName=?");
                        pre.setString(1, fullName);
                    } else {
                        pre = con.prepareStatement("select *from INFORS, OUT_BILL where OUT_BILL.id=INFORS.id and obDate=? and fullName=?");
                        pre.setDate(1, Date.valueOf(date));
                        pre.setString(2, fullName);
                    }
                }
                ResultSet re = pre.executeQuery();
                while (re.next()) {
                    vt = new Vector();
                    vt.add(re.getString("obID"));
                    vt.add(re.getString("fullName"));
                    vt.add(re.getString("obDate"));
                    vt.add(re.getString("obPay"));
                    defaultTableModel.addRow(vt);
                }
            }

            jTableBIll.setModel(defaultTableModel);
        } catch (SQLException ex) {
            Logger.getLogger(StatisticBillJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showFullName(ArrayList<String> listBill) {
        jComboBoxFullName.removeAllItems();
        jComboBoxFullName.addItem("All");
        for (String k : listBill) {
            jComboBoxFullName.addItem(k);
        }
    }

    public void showDate(ArrayList<String> listDate) {
        jComboBoxDate.removeAllItems();
        jComboBoxDate.addItem("All");
        for (String k : listDate) {
            jComboBoxDate.addItem(k);
        }
    }

    public void resetDate() {
        jComboBoxFullName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBoxFullName.getSelectedIndex() == -1) {
                    System.out.println("helo");
                    return;
                }
                jComboBoxDate.removeAllItems();
                if (jComboBoxFullName.getSelectedItem().equals("All")) {
                    if (statusBill == 0) {
                        showDate(BillController.getIBDate());
                    } else {
                        showDate(BillController.getOBDate());
                    }
                } else {
                    if (statusBill == 0) {
                        showDate(BillController.getIBDateByFullName(jComboBoxFullName.getSelectedItem().toString()));
                    } else {
                        showDate(BillController.getOBDateByFullName(jComboBoxFullName.getSelectedItem().toString()));

                    }
                }
            }
        });
    }

    public void resetFullName_Date() {
        jComboBoxType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBoxType.getSelectedIndex() == 0) {
                    showFullName(BillController.getFullNameInBill());
                    showDate(BillController.getIBDate());
                    statusBill = 0;
                } else {
                    System.out.println("không có lỗi");
                    showFullName(BillController.getFullNameOutBill());
                    showDate(BillController.getOBDate());
                    statusBill = 1;
                }
            }
        });
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
        jLabelAddress = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnFilter = new javax.swing.JButton();
        jComboBoxType = new javax.swing.JComboBox<>();
        jComboBoxDate = new javax.swing.JComboBox<>();
        jComboBoxFullName = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableBIll = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jButtonRender = new javax.swing.JButton();
        jButtonRenderDetail = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1500, 800));

        jPanel2.setBackground(java.awt.Color.gray);
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 800));

        jLabelAge.setBackground(new java.awt.Color(51, 153, 255));
        jLabelAge.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelAge.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAge.setText("Tên nhân viên");
        jLabelAge.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelAge.setOpaque(true);

        jLabelFullName.setBackground(new java.awt.Color(51, 153, 255));
        jLabelFullName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelFullName.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFullName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFullName.setText("Loại hóa đơn");
        jLabelFullName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelFullName.setOpaque(true);

        jLabelAddress.setBackground(new java.awt.Color(51, 153, 255));
        jLabelAddress.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabelAddress.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAddress.setText("Ngày lập");
        jLabelAddress.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelAddress.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(51, 153, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("LỌC HÓA ĐƠN");
        jLabel8.setOpaque(true);

        btnFilter.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_update_30px_1.png"))); // NOI18N
        btnFilter.setText("LỌC");
        btnFilter.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnFilter.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        jComboBoxType.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hóa đơn nhập hàng", "Hóa đơn bán hàng" }));

        jComboBoxDate.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jComboBoxFullName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelAge, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jComboBoxDate, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(java.awt.Color.lightGray);
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 800));

        jTableBIll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTableBIll.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jTableBIll.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Họ tên nhân viên", "Ngày lập", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableBIll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBIllMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableBIll);
        if (jTableBIll.getColumnModel().getColumnCount() > 0) {
            jTableBIll.getColumnModel().getColumn(0).setResizable(false);
            jTableBIll.getColumnModel().getColumn(1).setResizable(false);
            jTableBIll.getColumnModel().getColumn(2).setResizable(false);
            jTableBIll.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel9.setBackground(new java.awt.Color(51, 153, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DANH SÁCH HÓA ĐƠN");
        jLabel9.setOpaque(true);

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

        jButtonRenderDetail.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonRenderDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_outdent_30px.png"))); // NOI18N
        jButtonRenderDetail.setText("KẾT XUẤT CHI TIẾT HÓA ĐƠN");
        jButtonRenderDetail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        jButtonRenderDetail.setEnabled(false);
        jButtonRenderDetail.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonRenderDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRenderDetailActionPerformed(evt);
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
                        .addComponent(jButtonRenderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 457, Short.MAX_VALUE)
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
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRenderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        jButtonRenderDetail.setEnabled(false);
        showBill();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void jButtonRenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenderActionPerformed
        pdf.pdfStatisticBill(defaultTableModel);
        JOptionPane.showMessageDialog(this, "Kết xuất thành công");
    }//GEN-LAST:event_jButtonRenderActionPerformed

    private void jTableBIllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBIllMouseClicked
        jButtonRenderDetail.setEnabled(true);
        selectedRow = jTableBIll.getSelectedRow();
    }//GEN-LAST:event_jTableBIllMouseClicked

    private void jButtonRenderDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenderDetailActionPerformed
        pdf.pdfStatisticDetailInBill(statusBill, defaultTableModel.getValueAt(selectedRow, 0).toString(), defaultTableModel.getValueAt(selectedRow, 1).toString(), defaultTableModel.getValueAt(selectedRow, 2).toString(), Integer.parseInt(defaultTableModel.getValueAt(selectedRow, 3).toString()));
        JOptionPane.showMessageDialog(this, "Kết xuất thành công");
        jButtonRenderDetail.setEnabled(false);
    }//GEN-LAST:event_jButtonRenderDetailActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton jButtonRender;
    private javax.swing.JButton jButtonRenderDetail;
    private javax.swing.JComboBox<String> jComboBoxDate;
    private javax.swing.JComboBox<String> jComboBoxFullName;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelAge;
    private javax.swing.JLabel jLabelFullName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableBIll;
    // End of variables declaration//GEN-END:variables
}
