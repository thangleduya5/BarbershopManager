/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BillController;
import Controller.DBConnection;
import Controller.InBill;
import Controller.ProducerController;
import Controller.ToolController;
import Controller.UserController;
import Controller.pdf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author huunh
 */
public class ToolJPanel extends javax.swing.JPanel {

    /**
     * Creates new form CosmeticJPanel1
     */
    DefaultTableModel defaultTableModel = new DefaultTableModel();
    String status = "";
    int selectedRow = 0;

    public ToolJPanel() {
        initComponents();
        showTool();
        setProname();
        showTooName();
        FilterTable();
    }

        public void FilterTable() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) jTableTool.getModel()));

        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter(jTextFieldSearch.getText()));
                jTableTool.setRowSorter(sorter);

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter(jTextFieldSearch.getText()));
                jTableTool.setRowSorter(sorter);

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }
    public void showTool() {
        defaultTableModel.setNumRows(0);
        defaultTableModel.setColumnCount(0);
        jTableTool.setEditingColumn(0);
        jTableTool.setEditingRow(0);
        defaultTableModel.addColumn("Tên dụng cụ");
        defaultTableModel.addColumn("Nhà sản xuất");
        defaultTableModel.addColumn("Số lượng");
        Connection con = DBConnection.getConnection();
        Vector vt;
        try {
            PreparedStatement pre = con.prepareStatement("select *from TOOLS");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                vt = new Vector();
                vt.add(re.getString("tooName"));
                vt.add(ProducerController.getProName(re.getString("proID")));
                vt.add(re.getString("tooCount"));
                defaultTableModel.addRow(vt);
            }
            jTableTool.setModel(defaultTableModel);
        } catch (SQLException ex) {
            Logger.getLogger(AccountJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showTooName() {
        for (String k : ToolController.getTooName()) {
            jComboBoxTooName.addItem(k);
        }
    }

    public void resetTool() {
        showTooName();
        jComboBoxTooName.setEnabled(true);
        jTextFieldPay.setEnabled(true);
        jComboBoxTooName.setSelectedIndex(0);
        jTextFieldCount.setText("");
        jTextFieldPay.setText("");
    }

    public void showProName() {
        for (String k : ProducerController.getListProName()) {
            jComboBoxProName.addItem(k);
        }
    }

    public void setProname() {
        jComboBoxTooName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tooName = jComboBoxTooName.getEditor().getItem().toString();
                jComboBoxProName.removeAllItems();
                showProName();
                if (ToolController.existTooName(tooName)) {
                    jComboBoxProName.setEnabled(false);
                    jComboBoxProName.setSelectedItem(ProducerController.getProName(ToolController.getProID(jComboBoxTooName.getSelectedItem().toString())));
                    status = "update";
                } else {
                    jComboBoxProName.setEditable(true);
                    jComboBoxProName.setEnabled(true);
                    status = "add";
                }
            }
        });
    }

    public String randomTooID() {
        String result;
        do {
            result = "T";
            Random r = new Random();
            for (int i = 0; i < 3; i++) {
                result += r.nextInt(9);
            }
        } while (ToolController.ExistTool(result));
        return result;
    }

    public String randomproID() {
        String result;
        do {
            result = "P";
            Random r = new Random();
            for (int i = 0; i < 3; i++) {
                result += r.nextInt(9);
            }
        } while (ProducerController.ExistProducerID(result));
        return result;
    }

    public boolean testData(String tooName, String proName, String dtibCounts, String dtibPays) {
        if (tooName.equals("") || tooName.length() > 40) {
            JOptionPane.showMessageDialog(this, "Tên vật dụng không được rỗng, không vượt quá 40 kí tự");
            return false;
        }
        System.out.println(proName);
        if (proName.equals("") || proName.length() > 70) {
            JOptionPane.showMessageDialog(this, "Nhà sản xuất không được rỗng, không vượt quá 70 kí tự");
            return false;
        }
        String pattern = "[0-9]{1,9}";
        if (!dtibCounts.matches(pattern)) {
            JOptionPane.showMessageDialog(this, "Số lượng không đúng định dạng");
            return false;
        }
        if (Integer.parseInt(dtibCounts) == 0) {
            JOptionPane.showMessageDialog(this, "Số lượng > 0");
            return false;
        }
        if (!dtibPays.matches(pattern)) {
            JOptionPane.showMessageDialog(this, "Thành tiền không đúng định dạng");
            return false;
        }
        if (Integer.parseInt(dtibPays) == 0) {
            JOptionPane.showMessageDialog(this, "Thành tiền > 0");
            return false;
        }
        return true;
    }

    public void displayTool() {
        jComboBoxTooName.setSelectedItem(defaultTableModel.getValueAt(selectedRow, 0));
        jComboBoxProName.setSelectedItem(defaultTableModel.getValueAt(selectedRow, 1));
        jTextFieldCount.setText(defaultTableModel.getValueAt(selectedRow, 2).toString());
        jTextFieldPay.setEnabled(false);
        jComboBoxTooName.setEnabled(false);
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
        jLabel_TieuDeThemMyPham = new javax.swing.JLabel();
        jLabel_TenDungCu = new javax.swing.JLabel();
        jLabel_NhaSanXuat = new javax.swing.JLabel();
        jLabel_SoLuong = new javax.swing.JLabel();
        jLabel_ThanhTien = new javax.swing.JLabel();
        jTextFieldCount = new javax.swing.JTextField();
        jTextFieldPay = new javax.swing.JTextField();
        jButtonAdd = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jComboBoxTooName = new javax.swing.JComboBox<>();
        jComboBoxProName = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel_TieuDeDSMyPham = new javax.swing.JLabel();
        jLabel_TimKiem = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonRender = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTool = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1500, 800));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(java.awt.Color.gray);
        jPanel1.setMaximumSize(new java.awt.Dimension(500, 32767));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 800));

        jLabel_TieuDeThemMyPham.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_TieuDeThemMyPham.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_TieuDeThemMyPham.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TieuDeThemMyPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TieuDeThemMyPham.setText("THÊM DỤNG CỤ");
        jLabel_TieuDeThemMyPham.setOpaque(true);

        jLabel_TenDungCu.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_TenDungCu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_TenDungCu.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TenDungCu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TenDungCu.setText("Tên dụng cụ:");
        jLabel_TenDungCu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_TenDungCu.setOpaque(true);

        jLabel_NhaSanXuat.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_NhaSanXuat.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_NhaSanXuat.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_NhaSanXuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_NhaSanXuat.setText("Nhà sản xuất:");
        jLabel_NhaSanXuat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_NhaSanXuat.setOpaque(true);

        jLabel_SoLuong.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_SoLuong.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_SoLuong.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_SoLuong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_SoLuong.setText("Số lượng:");
        jLabel_SoLuong.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_SoLuong.setOpaque(true);

        jLabel_ThanhTien.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_ThanhTien.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_ThanhTien.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_ThanhTien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_ThanhTien.setText("Thành tiền:");
        jLabel_ThanhTien.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_ThanhTien.setOpaque(true);

        jTextFieldCount.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldCount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jTextFieldCount.setMinimumSize(new java.awt.Dimension(60, 24));

        jTextFieldPay.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldPay.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jButtonAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_add_30px_1.png"))); // NOI18N
        jButtonAdd.setText("Thêm");
        jButtonAdd.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonDelete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_delete_30px.png"))); // NOI18N
        jButtonDelete.setText("Xóa");
        jButtonDelete.setEnabled(false);
        jButtonDelete.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButtonUpdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_update_30px_1.png"))); // NOI18N
        jButtonUpdate.setText("Cập nhật");
        jButtonUpdate.setEnabled(false);
        jButtonUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jComboBoxTooName.setEditable(true);
        jComboBoxTooName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jComboBoxProName.setEditable(true);
        jComboBoxProName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel_TieuDeThemMyPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_TenDungCu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxTooName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_NhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxProName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPay)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel_TieuDeThemMyPham, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_TenDungCu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTooName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_NhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxProName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(338, Short.MAX_VALUE))
        );

        add(jPanel1);

        jPanel2.setBackground(java.awt.Color.lightGray);
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 800));

        jLabel_TieuDeDSMyPham.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_TieuDeDSMyPham.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_TieuDeDSMyPham.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TieuDeDSMyPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TieuDeDSMyPham.setText("DANH SÁCH DỤNG CỤ");
        jLabel_TieuDeDSMyPham.setOpaque(true);

        jLabel_TimKiem.setBackground(new java.awt.Color(51, 153, 255));
        jLabel_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_TimKiem.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_search_30px_2.png"))); // NOI18N
        jLabel_TimKiem.setText("Tìm kiếm:");
        jLabel_TimKiem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel_TimKiem.setOpaque(true);

        jTextFieldSearch.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextFieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jTextFieldSearch.setMaximumSize(new java.awt.Dimension(100, 2147483647));
        jTextFieldSearch.setMinimumSize(new java.awt.Dimension(50, 22));
        jTextFieldSearch.setOpaque(false);

        jButtonRender.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonRender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_outdent_30px.png"))); // NOI18N
        jButtonRender.setText("Kết xuất");
        jButtonRender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRenderActionPerformed(evt);
            }
        });

        jTableTool.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTableTool.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên dụng cụ", "Nhà sản xuất", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableTool.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableToolMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableTool);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
                        .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_TieuDeDSMyPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel_TieuDeDSMyPham, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents
    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        String tooName = jComboBoxTooName.getEditor().getItem().toString().trim();
        String proName = jComboBoxProName.getEditor().getItem().toString().trim();
        String dtibCounts = jTextFieldCount.getText().toString();
        String dtibPays = jTextFieldPay.getText().toString();
        if (!testData(tooName, proName, dtibCounts, dtibPays)) {
            return;
        }
        int dtibCount = Integer.parseInt(dtibCounts);
        int dtibPay = Integer.parseInt(dtibPays);
        String tooID_update = ToolController.findTooID(tooName);
        String tooID_add = randomTooID();
        String proID_add = randomproID();
        int ibID = MainFrame.lastIB.ibID + 1;

        if (!BillController.existInBill(ibID)) {
            BillController.addInBill(new InBill(ibID, UserController.findID(MainFrame.userName), LocalDate.now().toString()));
        }
        if (status.equals("update")) {
            if (!ProducerController.getProName(ToolController.getProID(tooName)).equals(proName)) {
                JOptionPane.showMessageDialog(this, "Nhập lỗi, cùng 1 dụng cụ phải cùng nhà sản xuất");
                return;
            }
            BillController.add_DetailTool_InBill(ibID, tooID_update, dtibCount, dtibPay);
            ToolController.updateCountTool(tooID_update, dtibCount);
        }
        if (status.equals("add")) {
            if (!ProducerController.ExistProducer(proName)) {
                ProducerController.addProducer(proID_add, proName);
                ToolController.addTool(tooID_add, tooName, proID_add, dtibCount);
            } else {
                ToolController.addTool(tooID_add, tooName, ProducerController.getProID(proName), dtibCount);
            }
            BillController.add_DetailTool_InBill(ibID, tooID_add, dtibCount, dtibPay);
        }
        JOptionPane.showMessageDialog(this, "Thêm dụng cụ thành công");
        resetTool();
        showTool();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonRenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenderActionPerformed
        pdf.pdfTool();
        JOptionPane.showMessageDialog(this, "Kết xuất thành công");
    }//GEN-LAST:event_jButtonRenderActionPerformed

    private void jTableToolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableToolMouseClicked
        jButtonAdd.setEnabled(false);
        jButtonUpdate.setEnabled(true);
        selectedRow = jTableTool.getSelectedRow();
        displayTool();
    }//GEN-LAST:event_jTableToolMouseClicked

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        //code here
        String tooName = jComboBoxTooName.getEditor().getItem().toString().trim();
        String tooCount = jTextFieldCount.getText().toString().trim();
        String pattern = "[0-9]{1,9}";
        if (!tooCount.matches(pattern)) {
            JOptionPane.showMessageDialog(this, "Số lượng không đúng định dạng");
            return;
        }
        if (Integer.parseInt(tooCount) == 0) {
            JOptionPane.showMessageDialog(this, "Số lượng > 0");
            return;
        }
        ToolController.modifyTool(ToolController.findTooID(tooName), Integer.parseInt(tooCount));
        jButtonAdd.setEnabled(true);
        jButtonUpdate.setEnabled(false);
        resetTool();
        showTool();
        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
    }//GEN-LAST:event_jButtonUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonRender;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox<String> jComboBoxProName;
    private javax.swing.JComboBox<String> jComboBoxTooName;
    private javax.swing.JLabel jLabel_NhaSanXuat;
    private javax.swing.JLabel jLabel_SoLuong;
    private javax.swing.JLabel jLabel_TenDungCu;
    private javax.swing.JLabel jLabel_ThanhTien;
    private javax.swing.JLabel jLabel_TieuDeDSMyPham;
    private javax.swing.JLabel jLabel_TieuDeThemMyPham;
    private javax.swing.JLabel jLabel_TimKiem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTool;
    private javax.swing.JTextField jTextFieldCount;
    private javax.swing.JTextField jTextFieldPay;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
