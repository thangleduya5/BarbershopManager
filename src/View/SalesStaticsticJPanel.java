/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BillController;
import Controller.DBConnection;
import Controller.Chart;
import Controller.pdf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class SalesStaticsticJPanel extends javax.swing.JPanel {

    /**
     * Creates new form SalesStaticsticJPanel
     */
    int status = 0;
    DefaultTableModel defaultTableModel = new DefaultTableModel();

    public SalesStaticsticJPanel() {
        initComponents();
        jComboBoxMonth.setVisible(false);
        jLabelMonth.setVisible(false);
        showYear();
        setYear();
        setMonth();
        jComboBoxMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButtonRender.setEnabled(false);
            }
        });
    }

    public void setYear() {
        jComboBoxType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k = jComboBoxType.getSelectedIndex();
                if (k == -1) {
                    return;
                }
                status = k;
                if (status == 0) {
                    jComboBoxMonth.setVisible(false);
                    jLabelMonth.setVisible(false);
                } else {
                    jComboBoxMonth.setVisible(true);
                    jLabelMonth.setVisible(true);
                }
                showYear();
                jButtonRender.setEnabled(false);
            }
        });
    }

    public void setMonth() {
        jComboBoxYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBoxYear.getSelectedIndex() == -1) {
                    return;
                }
                showMonth(Integer.parseInt(jComboBoxYear.getSelectedItem().toString()));
                jButtonRender.setEnabled(false);
            }
        });
    }

    public void showMonth(int year) {
        jComboBoxMonth.removeAllItems();
        for (int k : BillController.getMonthOutBill(year)) {
            jComboBoxMonth.addItem(k + "");
        }
    }

    public void showYear() {
        jComboBoxYear.removeAllItems();
        ArrayList<Integer> list = BillController.getYearOutBill();
        if (status == 0) {
            boolean flag;
            for (int k : BillController.getYearInBill()) {
                flag = true;
                for (int j : list) {
                    if (j == k) {
                        flag = false;
                        System.out.println(flag);
                        break;
                    }
                }
                if (flag == true) {
                    list.add(k);
                    System.out.println(k);
                }
            }
        }
        for (int k : list) {
            jComboBoxYear.addItem(k + "");
        }
    }

    public void showTableSales_Expense() {
        defaultTableModel.setNumRows(0);
        defaultTableModel.setColumnCount(0);
        jTableStatisticTable.setEditingColumn(0);
        jTableStatisticTable.setEditingRow(0);
        defaultTableModel.addColumn("Tháng");
        defaultTableModel.addColumn("Doanh thu");
        defaultTableModel.addColumn("Kinh phí");
        int y = Integer.parseInt(jComboBoxYear.getSelectedItem().toString());
        Vector vt;
        for (int i = 1; i < 13; i++) {
            vt = new Vector();
            vt.add(i);
            vt.add(BillController.getSales(i, y));
            vt.add(BillController.getExpense(i, y));
            defaultTableModel.addRow(vt);
        }
        jTableStatisticTable.setModel(defaultTableModel);
    }

    public void showTableSales_Cosmetic() {
        defaultTableModel.setNumRows(0);
        defaultTableModel.setColumnCount(0);
        jTableStatisticTable.setEditingColumn(0);
        jTableStatisticTable.setEditingRow(0);
        defaultTableModel.addColumn("Mỹ phẩm");
        defaultTableModel.addColumn("Doanh thu");
        int y = Integer.parseInt(jComboBoxYear.getSelectedItem().toString());
        int m = Integer.parseInt(jComboBoxMonth.getSelectedItem().toString());
        Vector vt;
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select c.cosName, S.Sales\n"
                    + "from COSMETICS c, (select dob.cosID, sum(dob.dobCount * cb.cosPrice) as Sales\n"
                    + "from COS_BATCH cb, OUT_BILL ob, DETAIL_OUT_BILL dob\n"
                    + "where ob.obID=dob.obID and dob.cosID=cb.cosID and dob.cosMFG=cb.cosMFG\n"
                    + "and year(ob.obDate)=? and month(ob.obDate)=?\n"
                    + "group by dob.cosID) as S\n"
                    + "where S.cosID=c.cosID");
            pre.setInt(1, y);
            pre.setInt(2, m);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                vt = new Vector();
                vt.add(re.getString(1));
                vt.add(re.getInt(2));
                defaultTableModel.addRow(vt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalesStaticsticJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTableStatisticTable.setModel(defaultTableModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxType = new javax.swing.JComboBox<>();
        jLabelMonth = new javax.swing.JLabel();
        jComboBoxMonth = new javax.swing.JComboBox<>();
        jLabelAge = new javax.swing.JLabel();
        jComboBoxYear = new javax.swing.JComboBox<>();
        btnConfirm = new javax.swing.JButton();
        jButtonRender = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelMonth1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStatisticTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabelMonth2 = new javax.swing.JLabel();
        jPanelChart = new javax.swing.JPanel();

        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(1500, 800));

        jLabel9.setBackground(new java.awt.Color(51, 153, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DOANH THU");
        jLabel9.setOpaque(true);
        jLabel9.setPreferredSize(new java.awt.Dimension(1000, 44));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jComboBoxType.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng doanh thu - kinh phí", "Doanh thu theo sản phẩm" }));
        jComboBoxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTypeActionPerformed(evt);
            }
        });

        jLabelMonth.setBackground(new java.awt.Color(51, 153, 255));
        jLabelMonth.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMonth.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMonth.setText("Tháng");
        jLabelMonth.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelMonth.setOpaque(true);

        jComboBoxMonth.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jComboBoxMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMonthActionPerformed(evt);
            }
        });

        jLabelAge.setBackground(new java.awt.Color(51, 153, 255));
        jLabelAge.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelAge.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAge.setText("Năm");
        jLabelAge.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelAge.setOpaque(true);

        jComboBoxYear.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jComboBoxYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxYearActionPerformed(evt);
            }
        });

        btnConfirm.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnConfirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_update_30px_1.png"))); // NOI18N
        btnConfirm.setText("LỌC");
        btnConfirm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnConfirm.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        jButtonRender.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButtonRender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_outdent_30px.png"))); // NOI18N
        jButtonRender.setText("KẾT XUẤT");
        jButtonRender.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        jButtonRender.setEnabled(false);
        jButtonRender.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonRender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabelMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jLabelAge, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 415, Short.MAX_VALUE)
                .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabelMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabelAge, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabelMonth1.setBackground(new java.awt.Color(51, 153, 255));
        jLabelMonth1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelMonth1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMonth1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMonth1.setText("Bảng chi tiết");
        jLabelMonth1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelMonth1.setOpaque(true);

        jTableStatisticTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tháng", "Doanh thu", "Kinh phí"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableStatisticTable);
        if (jTableStatisticTable.getColumnModel().getColumnCount() > 0) {
            jTableStatisticTable.getColumnModel().getColumn(0).setResizable(false);
            jTableStatisticTable.getColumnModel().getColumn(1).setResizable(false);
            jTableStatisticTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                    .addComponent(jLabelMonth1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabelMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabelMonth2.setBackground(new java.awt.Color(51, 153, 255));
        jLabelMonth2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelMonth2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMonth2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMonth2.setText("Biểu đồ");
        jLabelMonth2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabelMonth2.setOpaque(true);

        javax.swing.GroupLayout jPanelChartLayout = new javax.swing.GroupLayout(jPanelChart);
        jPanelChart.setLayout(jPanelChartLayout);
        jPanelChartLayout.setHorizontalGroup(
            jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelChartLayout.setVerticalGroup(
            jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMonth2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabelMonth2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenderActionPerformed
        if (status == 0) {
            pdf.pdfStatisticSales_Expense(defaultTableModel);
        } else {
            pdf.pdfStatisticSales_Cosmetic(defaultTableModel);
        }
        JOptionPane.showMessageDialog(this, "Kết xuất thành công");
        jButtonRender.setEnabled(false);
    }//GEN-LAST:event_jButtonRenderActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        if (status == 0) {
            showTableSales_Expense();
            Chart.chartMonth(Integer.parseInt(jComboBoxYear.getSelectedItem().toString()), defaultTableModel);
        } else {
            showTableSales_Cosmetic();
            Chart.BarChart(defaultTableModel);
        }
        jPanelChart.removeAll();
        ImageIcon icon = new ImageIcon("Doanhthu-Kinhphi.jpeg");
        icon.getImage().flush();
        JLabel jLabelLineChart = new JLabel(icon);
        jLabelLineChart.setSize(700, 500);
        jLabelLineChart.setVisible(true);
        jPanelChart.add(jLabelLineChart);
        jPanelChart.repaint();
        jButtonRender.setEnabled(true);
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void jComboBoxMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMonthActionPerformed

    private void jComboBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTypeActionPerformed

    private void jComboBoxYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxYearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton jButtonRender;
    private javax.swing.JComboBox<String> jComboBoxMonth;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JComboBox<String> jComboBoxYear;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAge;
    private javax.swing.JLabel jLabelMonth;
    private javax.swing.JLabel jLabelMonth1;
    private javax.swing.JLabel jLabelMonth2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelChart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableStatisticTable;
    // End of variables declaration//GEN-END:variables
}
