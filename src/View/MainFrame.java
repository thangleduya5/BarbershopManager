
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BillController;
import Controller.ConvertEvent;
import Controller.InBill;
import Controller.TaskItem;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class MainFrame extends javax.swing.JFrame {

    public static String x;
    public static String userName;
    public static InBill lastIB;

    /**
     * Creates new form HomeFrame
     */
    public MainFrame() {
        initComponents();
        jLabelThongKe.setVisible(false);
        ArrayList<TaskItem> listTaskItems = new ArrayList<>();
        HomJPanel.k = x;
        listTaskItems.add(new TaskItem("TrangChu", jLabelTrangChu));
        listTaskItems.add(new TaskItem("MyPham", jLabelMyPham));
        listTaskItems.add(new TaskItem("DungCu", jLabelDungCu));
        listTaskItems.add(new TaskItem("HoaDon", jLabelHoaDon));
        if (x.equals("1")) {
            jLabelThongKe.setVisible(true);
            listTaskItems.add(new TaskItem("ThongKe", jLabelThongKe));
        }
        ConvertEvent convertEvent = new ConvertEvent(jPanelView);
        convertEvent.setView(jLabelTrangChu);
        convertEvent.setEvent(listTaskItems);

        //
        //khởi tạo ibID
        lastIB = BillController.getLastInBill();
//        if (lastIB.ibID == 0 || lastIB.idUser!=null) {
//            new BillController().addInBill(new InBill());
//            lastIB = BillController.getLastInBill();
//        } else {
//            System.out.println(lastIB.ibID);
//        }
        
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(CosmeticJPanel.timerCos != null){
                    CosmeticJPanel.timerCos.cancel();
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

        jPanelNavBar = new javax.swing.JPanel();
        jLabelTrangChu = new javax.swing.JLabel();
        jLabelMyPham = new javax.swing.JLabel();
        jLabelDungCu = new javax.swing.JLabel();
        jLabelHoaDon = new javax.swing.JLabel();
        jLabelThongKe = new javax.swing.JLabel();
        jPanelView = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÍ TIỆM TÓC");
        setLocation(new java.awt.Point(200, 50));
        setMinimumSize(new java.awt.Dimension(1500, 890));
        setPreferredSize(new java.awt.Dimension(1500, 800));

        jPanelNavBar.setToolTipText("");
        jPanelNavBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabelTrangChu.setBackground(new java.awt.Color(51, 51, 255));
        jLabelTrangChu.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTrangChu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/baseline_home_black_24dp_1.png"))); // NOI18N
        jLabelTrangChu.setText("TRANG CHỦ");
        jLabelTrangChu.setToolTipText("");
        jLabelTrangChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jLabelTrangChu.setOpaque(true);

        jLabelMyPham.setBackground(new java.awt.Color(51, 51, 255));
        jLabelMyPham.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelMyPham.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMyPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMyPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/baseline_invert_colors_black_24dp_1.png"))); // NOI18N
        jLabelMyPham.setText("MỸ PHẨM");
        jLabelMyPham.setToolTipText("");
        jLabelMyPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jLabelMyPham.setOpaque(true);

        jLabelDungCu.setBackground(new java.awt.Color(51, 51, 255));
        jLabelDungCu.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelDungCu.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDungCu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDungCu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/baseline_construction_black_24dp_1.png"))); // NOI18N
        jLabelDungCu.setText("DỤNG CỤ");
        jLabelDungCu.setToolTipText("");
        jLabelDungCu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jLabelDungCu.setOpaque(true);

        jLabelHoaDon.setBackground(new java.awt.Color(51, 51, 255));
        jLabelHoaDon.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/baseline_receipt_long_black_24dp_1.png"))); // NOI18N
        jLabelHoaDon.setText("HÓA ĐƠN");
        jLabelHoaDon.setToolTipText("");
        jLabelHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jLabelHoaDon.setOpaque(true);

        jLabelThongKe.setBackground(new java.awt.Color(51, 51, 255));
        jLabelThongKe.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelThongKe.setForeground(new java.awt.Color(255, 255, 255));
        jLabelThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/baseline_insert_chart_black_24dp_1.png"))); // NOI18N
        jLabelThongKe.setText("THỐNG KÊ");
        jLabelThongKe.setToolTipText("");
        jLabelThongKe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jLabelThongKe.setOpaque(true);

        javax.swing.GroupLayout jPanelNavBarLayout = new javax.swing.GroupLayout(jPanelNavBar);
        jPanelNavBar.setLayout(jPanelNavBarLayout);
        jPanelNavBarLayout.setHorizontalGroup(
            jPanelNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNavBarLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabelTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabelMyPham, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabelDungCu, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabelHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabelThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(400, Short.MAX_VALUE))
        );
        jPanelNavBarLayout.setVerticalGroup(
            jPanelNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNavBarLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabelMyPham, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabelDungCu, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabelHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabelThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanelViewLayout = new javax.swing.GroupLayout(jPanelView);
        jPanelView.setLayout(jPanelViewLayout);
        jPanelViewLayout.setHorizontalGroup(
            jPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1500, Short.MAX_VALUE)
        );
        jPanelViewLayout.setVerticalGroup(
            jPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 80));

        jLabel1.setBackground(new java.awt.Color(255, 102, 102));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÍ TIỆM TÓC");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelNavBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelNavBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDungCu;
    private javax.swing.JLabel jLabelHoaDon;
    private javax.swing.JLabel jLabelMyPham;
    private javax.swing.JLabel jLabelThongKe;
    private javax.swing.JLabel jLabelTrangChu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelNavBar;
    private javax.swing.JPanel jPanelView;
    // End of variables declaration//GEN-END:variables
}
