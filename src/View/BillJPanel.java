/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BillController;
import Controller.CosmeticController;
import Controller.OutBill;
import Controller.ProducerController;
import Controller.UserController;
import Controller.pdf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author huunh
 */
public class BillJPanel extends javax.swing.JPanel {

    /**
     * Creates new form HoaDon
     */
    int selectedRow = 0;
    ArrayList<OutBill> bills = new ArrayList<>();
    DefaultTableModel defaultTableModel = new DefaultTableModel();

    ;

    public BillJPanel() {
        initComponents();
        setMFG_proName();
        setPrice_Count();
        resetBill();
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }

    public void showBill() {
        defaultTableModel.setNumRows(0);
        defaultTableModel.setColumnCount(0);
        tblBill.setEditingColumn(0);
        tblBill.setEditingRow(0);
        defaultTableModel.addColumn("Tên mỹ phẩm");
        defaultTableModel.addColumn("Ngày sản xuất");
        defaultTableModel.addColumn("Nhà sản xuất");
        defaultTableModel.addColumn("Đơn giá");
        defaultTableModel.addColumn("Số lượng");
        for (OutBill k : bills) {
            defaultTableModel.addRow(k.toObject());
        }
        tblBill.setModel(defaultTableModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel_NhapHoaDon = new javax.swing.JLabel();
        jLabel_TenMyPham = new javax.swing.JLabel();
        jLabel_NhaSanXuat = new javax.swing.JLabel();
        jTextFieldProName = new javax.swing.JTextField();
        jLabel_NgaySanXuat = new javax.swing.JLabel();
        jTextFieldPrice = new javax.swing.JTextField();
        jLabel_SoLuong = new javax.swing.JLabel();
        jLabel_DonGia = new javax.swing.JLabel();
        jTextFieldCount = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jComboBoxCosName = new javax.swing.JComboBox<>();
        jComboBoxMFG = new javax.swing.JComboBox<>();
        jLabel_SoLuong1 = new javax.swing.JLabel();
        jTextFieldCount0 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel_ChiTietHoaDon = new javax.swing.JLabel();
        jButtonRender = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBill = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabelPay = new javax.swing.JLabel();
        jButtonNewBill = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1500, 800));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(java.awt.Color.gray);
        jPanel1.setMaximumSize(new java.awt.Dimension(500, 32767));
        jPanel1.setMinimumSize(new java.awt.Dimension(300, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 800));

        jLabel_NhapHoaDon.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_NhapHoaDon.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_NhapHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_NhapHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_NhapHoaDon.setText("NHẬP HÓA ĐƠN");
        jLabel_NhapHoaDon.setOpaque(true);

        jLabel_TenMyPham.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_TenMyPham.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_TenMyPham.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TenMyPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TenMyPham.setText("Tên mỹ phẩm:");
        jLabel_TenMyPham.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_TenMyPham.setOpaque(true);

        jLabel_NhaSanXuat.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_NhaSanXuat.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_NhaSanXuat.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_NhaSanXuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_NhaSanXuat.setText("Nhà sản xuất:");
        jLabel_NhaSanXuat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_NhaSanXuat.setOpaque(true);

        jTextFieldProName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldProName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextFieldProName.setEnabled(false);

        jLabel_NgaySanXuat.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_NgaySanXuat.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_NgaySanXuat.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_NgaySanXuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_NgaySanXuat.setText("Ngày sản xuất:");
        jLabel_NgaySanXuat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_NgaySanXuat.setOpaque(true);

        jTextFieldPrice.setEditable(false);
        jTextFieldPrice.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPrice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextFieldPrice.setMinimumSize(new java.awt.Dimension(62, 23));

        jLabel_SoLuong.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_SoLuong.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_SoLuong.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_SoLuong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_SoLuong.setText("Số lượng:");
        jLabel_SoLuong.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_SoLuong.setOpaque(true);

        jLabel_DonGia.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_DonGia.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_DonGia.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_DonGia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_DonGia.setText("Đơn giá:");
        jLabel_DonGia.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_DonGia.setOpaque(true);

        jTextFieldCount.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldCount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextFieldCount.setMaximumSize(new java.awt.Dimension(62, 2147483647));

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_delete_30px.png"))); // NOI18N
        btnDelete.setText("XÓA  ");
        btnDelete.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_add_30px_1.png"))); // NOI18N
        btnAdd.setText("THÊM ");
        btnAdd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_update_30px_1.png"))); // NOI18N
        btnUpdate.setText("CẬP NHẬT ");
        btnUpdate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jComboBoxCosName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jComboBoxMFG.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel_SoLuong1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_SoLuong1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_SoLuong1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_SoLuong1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_SoLuong1.setText("Số lượng tồn:");
        jLabel_SoLuong1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_SoLuong1.setOpaque(true);

        jTextFieldCount0.setEditable(false);
        jTextFieldCount0.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldCount0.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextFieldCount0.setMaximumSize(new java.awt.Dimension(62, 2147483647));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_SoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldCount0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_TenMyPham, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxCosName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel_NhapHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_NhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldProName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel_NgaySanXuat, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .addComponent(jLabel_DonGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxMFG, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel_NhapHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_TenMyPham, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCosName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_NhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldProName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_NgaySanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMFG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_DonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_SoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCount0, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(257, Short.MAX_VALUE))
        );

        add(jPanel1);

        jPanel2.setBackground(java.awt.Color.lightGray);
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 800));

        jLabel_ChiTietHoaDon.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_ChiTietHoaDon.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_ChiTietHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_ChiTietHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_ChiTietHoaDon.setText("CHI TIẾT HÓA ĐƠN");
        jLabel_ChiTietHoaDon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel_ChiTietHoaDon.setOpaque(true);

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

        tblBill.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên mỹ phẩm", "Nhà sản xuất", "Ngày sản xuất", "Đơn giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBill);
        if (tblBill.getColumnModel().getColumnCount() > 0) {
            tblBill.getColumnModel().getColumn(0).setResizable(false);
            tblBill.getColumnModel().getColumn(1).setResizable(false);
            tblBill.getColumnModel().getColumn(2).setResizable(false);
            tblBill.getColumnModel().getColumn(3).setResizable(false);
            tblBill.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel2.setBackground(new java.awt.Color(51, 153, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(" Thành tiền: ");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel2.setOpaque(true);

        jLabelPay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPay.setText("0 vnd");

        jButtonNewBill.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonNewBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_bill_30px.png"))); // NOI18N
        jButtonNewBill.setText("HÓA ĐƠN MỚI");
        jButtonNewBill.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(0, 0, 0)));
        jButtonNewBill.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonNewBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewBillActionPerformed(evt);
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
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(jLabelPay, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonNewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_ChiTietHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel_ChiTietHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelPay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonNewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents
    public void resetBill() {
        jComboBoxCosName.removeAllItems();
        ArrayList<String> list = CosmeticController.getCosName();
        for (String k : list) {
            jComboBoxCosName.addItem(k);
        }
        jComboBoxCosName.setSelectedIndex(0);
        jComboBoxCosName.setEnabled(true);
        jComboBoxMFG.setEnabled(true);
        jTextFieldCount.setText("");
        jLabelPay.setText(getPay() + " vnd");
        jComboBoxMFG.removeAllItems();
        jTextFieldCount.setText("");
        jTextFieldCount0.setText("");
        jTextFieldPrice.setText("");
        jComboBoxCosName.setSelectedIndex(0);
        btnAdd.setEnabled(true);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }

    public void setMFG_proName() {
        jComboBoxCosName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jComboBoxMFG.removeAllItems();
                if (jComboBoxCosName.getSelectedIndex() != -1) {
                    String cosID = CosmeticController.findCosID(jComboBoxCosName.getSelectedItem().toString());
                    jTextFieldProName.setText(ProducerController.getProName(CosmeticController.findproID(cosID)));
                    ArrayList<String> list = CosmeticController.getMFG(cosID);
                    for (String k : list) {
                        jComboBoxMFG.addItem(k);
                    }
                }
            }
        });
    }

    public void setPrice_Count() {
        jComboBoxMFG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBoxMFG.getSelectedIndex() != -1) {
                    String cosID = CosmeticController.findCosID(jComboBoxCosName.getSelectedItem().toString());
                    String cosMFG = jComboBoxMFG.getSelectedItem().toString();
                    jTextFieldPrice.setText(CosmeticController.findCosPrice(cosID, cosMFG) + "");
                    for (OutBill k : bills) {
                        if (k.cosName.equals(jComboBoxCosName.getSelectedItem().toString()) && k.cosMFG.equals(cosMFG)) {
                            jTextFieldCount0.setText(k.cosCount + "");
                            return;
                        }
                    }
                    jTextFieldCount0.setText(CosmeticController.findCosCount(cosID, cosMFG) + "");
                }
            }
        });
    }

    public int getPay() {
        int obPay = 0;
        for (OutBill k : bills) {
            obPay += k.cosPrice * k.dobCount;
        }
        return obPay;
    }

    public void displayBill() {
        jComboBoxCosName.setSelectedItem(defaultTableModel.getValueAt(selectedRow, 0));
        jComboBoxMFG.setSelectedItem(defaultTableModel.getValueAt(selectedRow, 2));
        jTextFieldProName.setText(defaultTableModel.getValueAt(selectedRow, 1).toString());
        jTextFieldPrice.setText(defaultTableModel.getValueAt(selectedRow, 3).toString());
        jTextFieldCount.setText(defaultTableModel.getValueAt(selectedRow, 4).toString());

        jComboBoxCosName.setEnabled(false);
        jComboBoxMFG.setEnabled(false);
    }

    public boolean updateCount() {
        String cosName = jComboBoxCosName.getSelectedItem().toString();
        String cosMFG = jComboBoxMFG.getSelectedItem().toString();
        int count = Integer.parseInt(jTextFieldCount.getText().toString());
        for (OutBill k : bills) {
            if (k.cosName.equals(cosName) && k.cosMFG.equals(cosMFG)) {
                k.dobCount += count;
                k.cosCount -= count;
                return true;
            }
        }
        return false;
    }

    public void update() {
        String cosName = jComboBoxCosName.getSelectedItem().toString();
        String cosMFG = jComboBoxMFG.getSelectedItem().toString();
        int count = Integer.parseInt(jTextFieldCount.getText().toString());
        for (OutBill k : bills) {
            if (k.cosName.equals(cosName) && k.cosMFG.equals(cosMFG)) {
                k.cosCount += k.dobCount - count;
                k.dobCount = count;
                System.out.println("Hàng tồn: " + k.cosCount);
            }
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String cosName = jComboBoxCosName.getSelectedItem().toString();
        String cosMFG = jComboBoxMFG.getSelectedItem().toString();
        //code here
        for (OutBill k : bills) {
            if (k.cosName.equals(cosName) && k.cosMFG.equals(cosMFG)) {
                bills.remove(k);
                break;
            }
        }
        //
        resetBill();
        showBill();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String counts = jTextFieldCount.getText().toString();
        String cosCounts = jTextFieldCount0.getText().toString();
        String Price = jTextFieldPrice.getText().toString();
        if (jComboBoxMFG.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Hết hàng");
            return;
        }
        String pattern = "[0-9]{1,9}";
        if (!counts.matches(pattern)) {
            JOptionPane.showMessageDialog(this, "Số lượng không đúng định dạng");
            return;
        }
        int count = Integer.parseInt(counts);
        int cosCount = Integer.parseInt(cosCounts);
        if (count > cosCount) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ");
            return;
        }
        if (!updateCount()) {
            bills.add(new OutBill(jComboBoxCosName.getSelectedItem().toString(), jTextFieldProName.getText().toString(), jComboBoxMFG.getSelectedItem().toString(), Integer.parseInt(Price), count, cosCount - count));
        }
        resetBill();
        showBill();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String counts = jTextFieldCount.getText().toString();
        int cosCount = Integer.parseInt(jTextFieldCount0.getText().toString());
        String pattern = "[0-9]{1,9}";
        if (!counts.matches(pattern)) {
            JOptionPane.showMessageDialog(this, "Số lượng không đúng định dạng");
            return;
        }
        int count = Integer.parseInt(counts);
        if (count > cosCount) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ");
        } else {
            update();

        }
        resetBill();
        showBill();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void jButtonRenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenderActionPerformed
        if (bills.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kết xuất thất bại");
            return;
        }
        String id = UserController.findID(MainFrame.userName);
        BillController.addOutBill(id, LocalDate.now().toString(), getPay());
        int obID = BillController.maxOutBill();
        String cosID;
        for (OutBill k : bills) {
            cosID = CosmeticController.findCosID(k.cosName);
            CosmeticController.updateCosCount(cosID, k.cosMFG, k.cosCount);
            BillController.add_Detail_OutBill(obID, k.cosMFG, cosID, k.dobCount);
        }
        pdf.pdfBill(obID, getPay(), UserController.findFullName(id));
        JOptionPane.showMessageDialog(this, "Kết xuất thành công");
        bills.removeAll(bills);
        resetBill();
        showBill();
    }//GEN-LAST:event_jButtonRenderActionPerformed

    private void tblBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillMouseClicked
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
        selectedRow = tblBill.getSelectedRow();
        displayBill();
        String cosID = CosmeticController.findCosID(jComboBoxCosName.getSelectedItem().toString());
        String cosMFG = jComboBoxMFG.getSelectedItem().toString();
        jTextFieldCount0.setText(CosmeticController.findCosCount(cosID, cosMFG) + "");
    }//GEN-LAST:event_tblBillMouseClicked

    private void jButtonNewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewBillActionPerformed
        bills.removeAll(bills);
        resetBill();
        showBill();
    }//GEN-LAST:event_jButtonNewBillActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButtonNewBill;
    private javax.swing.JButton jButtonRender;
    private javax.swing.JComboBox<String> jComboBoxCosName;
    private javax.swing.JComboBox<String> jComboBoxMFG;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelPay;
    private javax.swing.JLabel jLabel_ChiTietHoaDon;
    private javax.swing.JLabel jLabel_DonGia;
    private javax.swing.JLabel jLabel_NgaySanXuat;
    private javax.swing.JLabel jLabel_NhaSanXuat;
    private javax.swing.JLabel jLabel_NhapHoaDon;
    private javax.swing.JLabel jLabel_SoLuong;
    private javax.swing.JLabel jLabel_SoLuong1;
    private javax.swing.JLabel jLabel_TenMyPham;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldCount;
    private javax.swing.JTextField jTextFieldCount0;
    private javax.swing.JTextField jTextFieldPrice;
    private javax.swing.JTextField jTextFieldProName;
    private javax.swing.JTable tblBill;
    // End of variables declaration//GEN-END:variables
}
