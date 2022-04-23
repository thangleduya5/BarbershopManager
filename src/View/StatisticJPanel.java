/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.*;
import Controller.BillController;
import Controller.InBill;
import Controller.UserController;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class StatisticJPanel extends javax.swing.JPanel {

    /**
     * Creates new form HomJPanel
     */
    public StatisticJPanel() {
        initComponents();
        jLabelDate.setText(LocalDate.now().toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelDate = new javax.swing.JLabel();
        jButtonStatisticBill = new javax.swing.JButton();
        jButtonStatisticExpene = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 153, 255));
        setPreferredSize(new java.awt.Dimension(1500, 800));

        jLabelDate.setBackground(new java.awt.Color(102, 255, 255));
        jLabelDate.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelDate.setOpaque(true);

        jButtonStatisticBill.setBackground(java.awt.Color.lightGray);
        jButtonStatisticBill.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonStatisticBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_bill_30px.png"))); // NOI18N
        jButtonStatisticBill.setText("Thống kê hóa đơn ");
        jButtonStatisticBill.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        jButtonStatisticBill.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonStatisticBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStatisticBillActionPerformed(evt);
            }
        });

        jButtonStatisticExpene.setBackground(java.awt.Color.lightGray);
        jButtonStatisticExpene.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonStatisticExpene.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_update_40px_1.png"))); // NOI18N
        jButtonStatisticExpene.setText("Thống kê doanh thu - kinh phí ");
        jButtonStatisticExpene.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        jButtonStatisticExpene.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonStatisticExpene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStatisticExpeneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(500, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonStatisticExpene, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonStatisticBill, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(500, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jButtonStatisticBill, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonStatisticExpene, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 354, Short.MAX_VALUE)
                .addComponent(jLabelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonStatisticBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStatisticBillActionPerformed
        removeAll();
        setLayout(new BorderLayout());
        add(new StatisticBillJPanel());
        validate();
        repaint();
    }//GEN-LAST:event_jButtonStatisticBillActionPerformed

    private void jButtonStatisticExpeneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStatisticExpeneActionPerformed
        removeAll();
        setLayout(new BorderLayout());
        add(new SalesStaticsticJPanel());
        validate();
        repaint();
    }//GEN-LAST:event_jButtonStatisticExpeneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonStatisticBill;
    private javax.swing.JButton jButtonStatisticExpene;
    private javax.swing.JLabel jLabelDate;
    // End of variables declaration//GEN-END:variables
}