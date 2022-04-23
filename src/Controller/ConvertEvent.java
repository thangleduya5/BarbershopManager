/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.BillJPanel;
import View.CosmeticJPanel;
import View.HomJPanel;
import View.StatisticJPanel;
import View.StatisticBillJPanel;
import View.ToolJPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class ConvertEvent {

    private String statusSelected = "";
    private JPanel jpnMain;

    public ConvertEvent(JPanel jpnMain) {
        this.jpnMain = jpnMain;
    }

    public void setView(JLabel jlbItem) {
        statusSelected = "TrangChu";
        jlbItem.setBackground(new Color(26, 198, 255));
        jpnMain.removeAll();
        jpnMain.setLayout(new BorderLayout());
        jpnMain.add(new HomJPanel());
        jpnMain.validate();
        jpnMain.repaint();
    }

    public void setEvent(ArrayList<TaskItem> listTask) {
        for (TaskItem taskItem : listTask) {
            taskItem.getJlb().addMouseListener(new LabelEvent(taskItem.getStatus(), taskItem.getJlb(), listTask));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel temp;
        private String status;
        private JLabel jlb;
        private ArrayList<TaskItem> listTask;

        public LabelEvent(String status, JLabel jlb, ArrayList<TaskItem> listTask) {
            this.status = status;
            this.jlb = jlb;
            this.listTask = listTask;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // cái này là khi click sang các tab khác thì nó kiểm tra xem danh sách các
            //mỹ phẩm đã nhập nhưng chưa lưu vào CSDL và hỏi người dùng xem có muốn lưu trước khi sang tab khác không?
            //nhưng chưa biết làm
//            if(CosmeticJPanel.listCosTemp!=null && CosmeticJPanel.listCosTemp.size()>0){
//                if(status.equals("MyPham")) return;
//                int option = JOptionPane.showConfirmDialog(null, 
//                        "Danh sách các mỹ phẩm vừa thêm chưa được lưu. Thoát và không lưu?");
//                if(option == JOptionPane.YES_OPTION){
//                    CosmeticJPanel.listCosTemp = null;
//                }else{
//                    status = "MyPham";
//                    return;
//                }
//            }
            System.out.println("click");
//            switch (status) {
//                case "TrangChu":
//                    temp = new HomJPanel();
//                    break;
//                case "MyPham":
//                    temp = new CosmeticJPanel();
//                    break;
//                case "DungCu":
//                    temp = new ToolJPanel();
//                    break;
//                case "HoaDon":
//                    temp = new BillJPanel();
//                    break;
//                case "ThongKe":
//                    temp = new StatisticJPanel();
//                    break;
//            }
//            jpnMain.removeAll();
//            jpnMain.setLayout(new BorderLayout());
//            jpnMain.add(temp);
//            jpnMain.validate();
//            jpnMain.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (CosmeticJPanel.timerCos != null) {
                CosmeticJPanel.timerCos.cancel();
            }
            statusSelected = status;
            System.out.println("press");
            jlb.setBackground(new Color(26, 198, 255));
            for (TaskItem taskItem : listTask) {
                if (!taskItem.getStatus().equalsIgnoreCase(statusSelected)) {
                    taskItem.getJlb().setBackground(new Color(0, 0, 255));
                }
            }
            switch (status) {
                case "TrangChu":
                    temp = new HomJPanel();
                    break;
                case "MyPham":
                    temp = new CosmeticJPanel();
                    break;
                case "DungCu":
                    temp = new ToolJPanel();
                    break;
                case "HoaDon":
                    temp = new BillJPanel();
                    break;
                case "ThongKe":
                    temp = new StatisticJPanel();
                    break;
            }
            jpnMain.removeAll();
            jpnMain.setLayout(new BorderLayout());
            jpnMain.add(temp);
            jpnMain.validate();
            jpnMain.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jlb.setBackground(new Color(26, 198, 255));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!statusSelected.equalsIgnoreCase(status)) {
                jlb.setBackground(new Color(0, 0, 255));
            }
        }
    }

}
