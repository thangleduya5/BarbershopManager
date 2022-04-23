/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BillController;
import Controller.Cosmetic;
import Controller.CosmeticController;
import Controller.InBill;
import Controller.Producer;
import Controller.ProducerController;
import Controller.UserController;
import Controller.pdf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author tienn
 */
public class CosmeticJPanel extends javax.swing.JPanel {

    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel modelTemp;
    String status = "";
    int selectedRow = 0;
    int choose = 0;
    public static Timer timerCos;

    public static ArrayList<Producer> producers;
    public static ArrayList<Cosmetic> cosmetics;
    public static ArrayList<Cosmetic> listCosTemp;

    private ArrayList<Cosmetic> searchListCosmetic;

    private String cosSearch = "";

    Date dateEXP, dateMFG; // 3 biến này dùng để fix lỗi null
    String cosName;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Cosmetic selectedCos = new Cosmetic(); // cosmetic đang được chọn trong bảng
    Cosmetic inputCos = new Cosmetic(); // 1 cosmetic tạm để lưu các thành phần của cos mà người dùng nhập
    private int selectedIndex = -1; // vị trí của cosmetic trong danh sách nếu tồn tại
    private boolean isExisted;  // dùng để kiểm tra xem cos đã tồn tại hay chưa
    private boolean cl = false; // dùng để lặp đúng 1 lần trong cái timer chạy liên tục

    public CosmeticJPanel() {
        initComponents();
        model = (DefaultTableModel) tblCosmetic.getModel();
        modelTemp = (DefaultTableModel) tblCosmeticTemp.getModel();

        setProname();
        showCosName();

        setTimer();
        settingDateChooser(); // không cho người dùng edit ngày tháng, chỉ đc click chọn

        producers = ProducerController.getListProducer("");
        cosmetics = CosmeticController.getListCosmetic("");
        searchListCosmetic = CosmeticController.getListCosmetic(cosSearch);
        listCosTemp = new ArrayList<>();

        new Producer().initListID(); // khởi tạo id của các pro đã tồn tại để khi tạo mới thì nó biết id tiếp theo là gì
        new Cosmetic().initListID(); // tương tự
        showCosmetics(cosmetics); // show bảng cosmetic

        jTextfieldSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT
                        || e.getKeyCode() == KeyEvent.VK_DOWN
                        || e.getKeyCode() == KeyEvent.VK_UP
                        || e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cosSearch = jTextfieldSearch.getText();
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (jTextfieldSearch.getText().length() > 0) {
                        cosSearch = cosSearch.substring(0, cosSearch.length() - 1);
                    }
                } else {
                    cosSearch += e.getKeyChar();
                }
                searchListCosmetic = CosmeticController.getListCosmetic(cosSearch);
                showCosmetics(searchListCosmetic);
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void showCosName() {
        for (String k : CosmeticController.getCosName()) {
            jComboboxCosName.addItem(k);
        }
        AutoCompleteDecorator.decorate(jComboboxCosName);
    }

    public void showProName() {
        for (String k : ProducerController.getListProName()) {
            jComboboxProName.addItem(k);
        }
        AutoCompleteDecorator.decorate(jComboboxProName);
    }

    public void setProname() {
        jComboboxCosName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cosName = ((JTextField) jComboboxCosName.getEditor().getEditorComponent()).getText();
                jComboboxProName.removeAllItems();
                showProName();
                if (CosmeticController.ExistCosmetic(cosName)) {
                    jComboboxProName.setEnabled(false);
                    jComboboxProName.setSelectedItem(ProducerController.getProName(CosmeticController.getProID(jComboboxCosName.getSelectedItem().toString())));
                    status = "update";
                } else {
                    jComboboxProName.setEditable(true);
                    jComboboxProName.setEnabled(true);
                    status = "add";
                }
                System.out.println(status);
            }
        });
    }

    public void setTimer() {
        timerCos = new Timer();
        timerCos.schedule(new TimerTask() {
            public void run() {
                dateEXP = dateCosEXP.getDate();
                dateMFG = dateCosMFG.getDate();
                cosName = ((JTextField) jComboboxCosName.getEditor().getEditorComponent()).getText();

                if (CosmeticController.ExistCosmetic(cosName)) {
                    String proName = ProducerController.getProName(CosmeticController.getProID(cosName));
                    jComboboxProName.setSelectedItem(proName);
                    jComboboxProName.setEnabled(false);
                } else {
                    jComboboxProName.setEnabled(true);
                }

                if ((!(cosName.equals("")) && dateEXP != null)
                        || (!(cosName.equals("")) && dateMFG != null)) {

                    inputCos.cosName = cosName;
                    SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
                    inputCos.cosEXP = (dateEXP != null) ? s.format(dateEXP) : "2000-10-02";
                    inputCos.cosMFG = (dateMFG != null) ? s.format(dateMFG) : "";
                    selectedIndex = checkIfCosExisted(inputCos); // trả về vị trí của cosmetic nếu tồn tại, ngược lại trả về -1
                    if (selectedIndex != -1) {
                        isExisted = true;
                    } else {
                        isExisted = false;
                    }
                } else {
                    isExisted = false;
                }
                System.out.println("đã tồn tại" + isExisted);
                printCos(selectedCos);
                printCos(inputCos);
                if (isExisted) {
                    selectedCos = cosmetics.get(selectedIndex);
                    selectedCos.dcibPay = BillController.getdcibPay(selectedCos);
                    if (!selectedCos.cosEXP.equals(inputCos.cosEXP)) {
                        cl = true;
                    }

                    if (cl) {
                        jTextfieldCosCount.setText(selectedCos.cosCount + "");
                        try {
                            dateCosMFG.setDate(sdf.parse(selectedCos.cosMFG));
                            dateCosEXP.setDate(sdf.parse(selectedCos.cosEXP));
                        } catch (ParseException ex) {
                            Logger.getLogger(CosmeticJPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jComboboxProName.setSelectedItem(selectedCos.proName);
                        jTextfieldCosPrice.setText(selectedCos.cosPrice + "");
                        cl = false;
                    }    
                    jTextfieldCosPrice.setEnabled(false);
                } else {
                    jTextfieldCosPrice.setEnabled(true);
                    cl = true;
                }
                inputCos = new Cosmetic();
            }
        }, 0, 100);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboboxProName = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextfieldCosCount = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextfieldCosPrice = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextfieldPay = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        dateCosEXP = new com.toedter.calendar.JDateChooser();
        dateCosMFG = new com.toedter.calendar.JDateChooser();
        jComboboxCosName = new javax.swing.JComboBox<>();
        btnSaveToDB = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTextfieldSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCosmetic = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jButtonRender = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCosmeticTemp = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1500, 800));

        jPanel1.setBackground(java.awt.Color.gray);
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 800));

        jLabel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tên");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(89, 19));

        jLabel2.setBackground(new java.awt.Color(51, 153, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Nhà sản xuất");
        jLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel2.setOpaque(true);

        jComboboxProName.setEditable(true);
        jComboboxProName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboboxProName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jComboboxProName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboboxProNameActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(51, 153, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hạn sử dụng");
        jLabel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel3.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(51, 153, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Ngày sản xuất");
        jLabel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel4.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(51, 153, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Số lượng");
        jLabel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel5.setOpaque(true);

        jTextfieldCosCount.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextfieldCosCount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jLabel6.setBackground(new java.awt.Color(51, 153, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Đơn giá");
        jLabel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel6.setOpaque(true);

        jTextfieldCosPrice.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextfieldCosPrice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

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
        btnUpdate.setEnabled(false);
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(51, 153, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("THÊM MỸ PHẨM");
        jLabel7.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(51, 153, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Thành tiền");
        jLabel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jLabel9.setOpaque(true);

        jTextfieldPay.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextfieldPay.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

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

        jComboboxCosName.setEditable(true);
        jComboboxCosName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        btnSaveToDB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSaveToDB.setText("LƯU VÀO CSDL");
        btnSaveToDB.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnSaveToDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveToDBActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_sync_30px.png"))); // NOI18N
        btnReset.setText("RESET");
        btnReset.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 5, 0, new java.awt.Color(51, 51, 51)));
        btnReset.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 22, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateCosEXP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextfieldCosCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jComboboxProName, javax.swing.GroupLayout.Alignment.TRAILING, 0, 300, Short.MAX_VALUE)
                            .addComponent(jTextfieldCosPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jComboboxCosName, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateCosMFG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextfieldPay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveToDB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboboxCosName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboboxProName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateCosEXP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateCosMFG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextfieldCosCount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextfieldCosPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextfieldPay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSaveToDB, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );

        jPanel2.setBackground(java.awt.Color.lightGray);
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 800));

        jTextfieldSearch.setBackground(java.awt.Color.lightGray);
        jTextfieldSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextfieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jTextfieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextfieldSearchActionPerformed(evt);
            }
        });

        tblCosmetic.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblCosmetic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Tên mỹ phẩm", "Nhà sản xuất", "Hạn sử dụng", "Ngày sản xuất", "Số lượng tồn", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCosmetic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCosmeticMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCosmetic);

        jLabel10.setBackground(new java.awt.Color(51, 153, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("DANH SÁCH MỸ PHẨM");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setOpaque(true);

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

        tblCosmeticTemp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblCosmeticTemp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Tên mỹ phẩm", "Nhà sản xuất", "Hạn sử dụng", "Ngày sản xuất", "Số lượng tồn", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCosmeticTemp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCosmeticTempMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCosmeticTemp);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextfieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextfieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!testData()) {
            return;
        }
        //-----------new----------//

        addCosToTableTemp();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        Cosmetic c = new Cosmetic();
        if(!testData()){
            return;
        }
        getInfoCosFromComponents(c);
        listCosTemp.set(tblCosmeticTemp.getSelectedRow(), c);
        printListCos(listCosTemp);
        showCosmeticsTemp(listCosTemp);
//        btnUpdate.setEnabled(false);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblCosmeticMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCosmeticMouseClicked
//        getInfoCosFromTable(selectedCos, tblCosmetic.getSelectedRow());
//        selectedIndex = Integer.parseInt(model.getValueAt(tblCosmetic.getSelectedRow(), 0).toString());
//
//        jComboboxCosName.setSelectedItem(selectedCos.cosName);
//        jComboboxProName.setSelectedItem(selectedCos.proName);
//        try {
//            dateCosEXP.setDate(sdf.parse(selectedCos.cosEXP));
//            dateCosMFG.setDate(sdf.parse(selectedCos.cosMFG));
//        } catch (ParseException ex) {
//            Logger.getLogger(CosmeticJPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        jTextfieldCosCount.setText(selectedCos.cosCount + "");
//        jTextfieldCosPrice.setText(selectedCos.cosPrice + "");
//        jTextfieldPay.setText(selectedCos.dcibPay + "");
//        // btnUpdate.setEnabled(true);
//        choose = 1;
        Cosmetic c = searchListCosmetic.get(tblCosmetic.getSelectedRow());
        c.dcibPay = BillController.getdcibPay(c);
        jComboboxCosName.setSelectedItem(c.cosName);
        jComboboxProName.setSelectedItem(c.proName);
        try {
            dateCosEXP.setDate(sdf.parse(c.cosEXP));
            dateCosMFG.setDate(sdf.parse(c.cosMFG));
        } catch (ParseException ex) {
            Logger.getLogger(CosmeticJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextfieldCosCount.setText(c.cosCount + "");
        jTextfieldCosPrice.setText(c.cosPrice + "");
        jTextfieldPay.setText(c.dcibPay + "");
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }//GEN-LAST:event_tblCosmeticMouseClicked

    private void jButtonRenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenderActionPerformed
        pdf.pdfCosmetic();
        JOptionPane.showMessageDialog(this, "Kết xuất thành công");
    }//GEN-LAST:event_jButtonRenderActionPerformed

    private void jTextfieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextfieldSearchActionPerformed

    }//GEN-LAST:event_jTextfieldSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        listCosTemp.remove(tblCosmeticTemp.getSelectedRow());
        showCosmeticsTemp(listCosTemp);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblCosmeticTempMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCosmeticTempMouseClicked
        Cosmetic c = listCosTemp.get(tblCosmeticTemp.getSelectedRow());
        jComboboxCosName.setSelectedItem(c.cosName);
        jComboboxProName.setSelectedItem(c.proName);
        try {
            dateCosEXP.setDate(sdf.parse(c.cosEXP));
            dateCosMFG.setDate(sdf.parse(c.cosMFG));
        } catch (ParseException ex) {
            Logger.getLogger(CosmeticJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextfieldCosCount.setText(c.cosCount + "");
        jTextfieldCosPrice.setText(c.cosPrice + "");
        jTextfieldPay.setText(c.dcibPay + "");
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
    }//GEN-LAST:event_tblCosmeticTempMouseClicked

    private void btnSaveToDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveToDBActionPerformed
        jTextfieldSearch.setText("");
        cosSearch = "";
        searchListCosmetic = CosmeticController.getListCosmetic(cosSearch);
        showCosmetics(searchListCosmetic);
        if(listCosTemp.isEmpty()){
            JOptionPane.showMessageDialog(this, "Lưu thất bại");
            return;
        }
        int ibID = MainFrame.lastIB.ibID + 1;
        if (!BillController.existInBill(ibID)) {
            System.out.println("vao day");
            BillController.addInBill(new InBill(ibID, UserController.findID(MainFrame.userName), LocalDate.now().toString()));
        }
        System.out.println("đây là: "+ibID);
        for (Cosmetic c : listCosTemp) {
            int i = checkIfCosExisted(c);
            printCos(c);
            System.out.println("cos exíted?" + i);
            if (i != -1) {
                if (CosmeticController.getCountDetailCosIB(ibID, c.cosID, c.cosMFG) > 0) {
                    int tmpCount = c.cosCount;
                    c.cosCount += BillController.getdcibCount(ibID, c.cosID, c.cosMFG);
                    int tmpIbPay = c.dcibPay;
                    c.dcibPay += BillController.getdcibPay(ibID, c.cosID, c.cosMFG);
                    BillController.updateCosToInBill(c);
                    c.cosCount = tmpCount;
                    c.dcibPay = tmpIbPay;
                } else {
                    System.out.println(c.dcibPay);
                    BillController.addCosToInBill(c,ibID);
                }
                c.cosCount += (int) model.getValueAt(i, 6);
                CosmeticController.updateCosmetic(c);
                model.setValueAt(c.cosName, i, 2);
                model.setValueAt(c.proName, i, 3);
                model.setValueAt(c.cosEXP, i, 4);
                model.setValueAt(c.cosMFG, i, 5);
                model.setValueAt(c.cosCount, i, 6);
                model.setValueAt(c.cosPrice, i, 7);
            } else {
                if ((c.cosID = getIDFromCosName(c)).equals("")) {
                    c.initCosID();
                    CosmeticController.addCosmetic(c);
                } else {
                    CosmeticController.addExistCosmetic(c);
                }

                BillController.addCosToInBill(c,ibID);
                model.setRowCount(0);
                showCosmetics(cosmetics);
            }
            
        }
        if(listCosTemp.size() > 0){
            JOptionPane.showMessageDialog(this, "Lưu thành công!");
        }
        listCosTemp.clear();
        listCosTemp = new ArrayList<>();
        searchListCosmetic = CosmeticController.getListCosmetic(cosSearch);
        showCosmetics(searchListCosmetic);
        modelTemp.setRowCount(0);
    }//GEN-LAST:event_btnSaveToDBActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        jComboboxCosName.setSelectedIndex(0);
        dateCosEXP.setDate(null);
        dateCosMFG.setDate(null);
        jTextfieldCosCount.setText("");
        jTextfieldCosPrice.setText("");
        jTextfieldPay.setText("");
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }//GEN-LAST:event_btnResetActionPerformed

    private void jComboboxProNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboboxProNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboboxProNameActionPerformed

    // <editor-fold defaultstate="collapsed" desc="Variables declaration - do not modify  ">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSaveToDB;
    private javax.swing.JButton btnUpdate;
    private com.toedter.calendar.JDateChooser dateCosEXP;
    private com.toedter.calendar.JDateChooser dateCosMFG;
    private javax.swing.JButton jButtonRender;
    private javax.swing.JComboBox<String> jComboboxCosName;
    private javax.swing.JComboBox<String> jComboboxProName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextfieldCosCount;
    private javax.swing.JTextField jTextfieldCosPrice;
    private javax.swing.JTextField jTextfieldPay;
    private javax.swing.JTextField jTextfieldSearch;
    private javax.swing.JTable tblCosmetic;
    private javax.swing.JTable tblCosmeticTemp;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>

    private boolean checkNull1() {
        if (jComboboxCosName.getSelectedItem().toString().equals("")) {
            return true;
        }
        if (jComboboxProName.getSelectedItem().toString().equals("")) {
            return true;
        }
        if (dateCosEXP.getDate() == null) {
            return true;
        }
        if (dateCosMFG.getDate() == null) {
            return true;
        }
        if (jTextfieldCosCount.getText().equals("")) {
            return true;
        }
        if (jTextfieldCosPrice.getText().equals("")) {
            return true;
        }
        if (jTextfieldPay.getText().equals("")) {
            return true;
        }
        return false;
    }

    private boolean testData() {
        if (selectedCos == null) {
            JOptionPane.showMessageDialog(this, "No cosmetics have been selected!");
            return false;
        }
        if (tblCosmetic.getSelectedRowCount() > 1) {
            return false;
        }
        if (checkNull()) {
            return false;
        }
        if (!getInfoCosFromComponents(selectedCos)) {
            return false;
        }
        if (!checkDate(selectedCos)) {
            JOptionPane.showMessageDialog(this, "HSD hoặc NSX không hợp lệ!");
            return false;
        }
//        if(selectedCos.proName.matches(".*\\d.*")){
//            JOptionPane.showMessageDialog(this, "Tên NSX không được chứa chữ số!");
//            return false;
//        }
        if (checkProName(selectedCos.proName) && !selectedCos.proName.equals("")) {
            int option = JOptionPane.showConfirmDialog(this, "This producer "
                    + "not exists. Add new one?");
            if (option == JOptionPane.YES_OPTION) {
                Producer producer = new Producer();
                producer.initProID();
                producer.proName = selectedCos.proName;
                jComboboxProName.addItem(producer.proName);
                jComboboxProName.setSelectedItem(selectedCos.proName);

                ProducerController.addProducer(producer);
            } else {
                return false;
            }
        }
        return true;
    }

    private void showCosmetics(ArrayList<Cosmetic> cosmetics) {
        if (tblCosmetic.getRowCount() > 0) {
            ((DefaultTableModel) tblCosmetic.getModel()).setNumRows(0);
        }
        DefaultTableModel model = (DefaultTableModel) tblCosmetic.getModel();
        Object[] row = new Object[8];
        int stt = 1;
        for (Cosmetic c : cosmetics) {
            row[0] = stt++;
            row[1] = c.cosID;
            row[2] = c.cosName;
            row[3] = c.proName;
            row[4] = c.cosEXP;
            row[5] = c.cosMFG;
            row[6] = c.cosCount;
            row[7] = c.cosPrice;
            model.addRow(row);
        }
    }

    private void showCosmeticsTemp(ArrayList<Cosmetic> cosmetics) {
        if (tblCosmeticTemp.getRowCount() > 0) {
            ((DefaultTableModel) tblCosmeticTemp.getModel()).setNumRows(0);
        }
        DefaultTableModel model = (DefaultTableModel) tblCosmeticTemp.getModel();
        Object[] row = new Object[8];
        int stt = 1;
        for (Cosmetic c : cosmetics) {
            row[0] = stt++;
            row[1] = c.cosID;
            row[2] = c.cosName;
            row[3] = c.proName;
            row[4] = c.cosEXP;
            row[5] = c.cosMFG;
            row[6] = c.cosCount;
            row[7] = c.cosPrice;
            model.addRow(row);
        }
    }

    private boolean checkProName(String proName) {
        for (Producer p : producers) {
            if (p.proName.equals(proName)) {
                return false;
            }
        }
        return true;
    }

    private void settingDateChooser() {
        JTextField EXPeditor = (JTextField) dateCosEXP.getDateEditor();
        JTextField MFGeditor = (JTextField) dateCosMFG.getDateEditor();
        EXPeditor.setEditable(false);
        MFGeditor.setEditable(false);
    }

    private boolean getInfoCosFromComponents(Cosmetic cosmetic) {
        try {

            cosmetic.cosName = jComboboxCosName.getSelectedItem().toString();
            cosmetic.cosID = CosmeticController.findCosID(cosmetic.cosName);
            cosmetic.proName = jComboboxProName.getSelectedItem().toString();
            SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
            cosmetic.cosEXP = s.format(dateCosEXP.getDate());
            cosmetic.cosMFG = s.format(dateCosMFG.getDate());
            cosmetic.cosCount = Integer.parseInt(jTextfieldCosCount.getText());
            cosmetic.cosPrice = Integer.parseInt(jTextfieldCosPrice.getText());
            cosmetic.dcibPay = Integer.parseInt(jTextfieldPay.getText());
            if(cosmetic.cosCount <= 0 || cosmetic.cosPrice <= 0 || cosmetic.dcibPay <= 0){
                JOptionPane.showMessageDialog(this, "Số lượng hoặc giá tiền phải > 0!");
                return false;
            }
            cosmetic.cosName = chuanHoa(cosmetic.cosName);
            jComboboxCosName.setSelectedItem(cosmetic.cosName);
            cosmetic.proName = chuanHoa(cosmetic.proName);
            jComboboxProName.setSelectedItem(cosmetic.proName);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng hoặc giá tiền nhập sai!");
            return false;
        }
    }

    private void getInfoCosFromTable(Cosmetic cosmetic, int index) {
        cosmetic.cosID = model.getValueAt(index, 1).toString();
        cosmetic.cosName = model.getValueAt(index, 2).toString();
        cosmetic.proName = model.getValueAt(index, 3).toString();
        cosmetic.cosEXP = model.getValueAt(index, 4).toString();
        cosmetic.cosMFG = model.getValueAt(index, 5).toString();
        cosmetic.cosCount = Integer.parseInt(model.getValueAt(index, 6).toString());
        cosmetic.cosPrice = Integer.parseInt(model.getValueAt(index, 7).toString());
        cosmetic.dcibPay = BillController.getdcibPay(cosmetic);
    }

    private int checkIfCosExisted(Cosmetic cosmetic) {
        cosmetics = CosmeticController.getListCosmetic("");
        for (int i = cosmetics.size() - 1; i >= 0; i--) {
            if (cosmetics.get(i).compareCos(cosmetic) && i != selectedIndex - 1) {
                return i;
            }
        }
        return -1;
    }

    private boolean checkIfCosTempExisted() {
        if (checkNull1()) {
            return false;
        }
        if (listCosTemp.size() <= 0) {
            return false;
        }
        Cosmetic cosmetic = new Cosmetic();
        try {
            cosmetic.cosName = jComboboxCosName.getSelectedItem().toString();
            cosmetic.proName = jComboboxProName.getSelectedItem().toString();
            SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
            cosmetic.cosEXP = s.format(dateCosEXP.getDate());
            cosmetic.cosMFG = s.format(dateCosMFG.getDate());
            cosmetic.cosCount = Integer.parseInt(jTextfieldCosCount.getText());
            cosmetic.cosPrice = Integer.parseInt(jTextfieldCosPrice.getText());
            cosmetic.dcibPay = Integer.parseInt(jTextfieldPay.getText());
//            System.out.println("COSMETIC");
//            printCos(cosmetic);
//            System.out.println("LIST COSMETIC");
//            printListCos(listCosTemp);

            for (int i = listCosTemp.size() - 1; i >= 0; i--) {
                if (listCosTemp.get(i).cosName.equals(cosmetic.cosName)
                        && listCosTemp.get(i).cosMFG.equals(cosmetic.cosMFG)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkDate(Cosmetic cosmetic) {
        Date EXP, MFG;
        try {
            EXP = sdf.parse(cosmetic.cosEXP);
            MFG = sdf.parse(cosmetic.cosMFG);
            if (MFG.after(EXP) || EXP.before(new Date()) || MFG.after(new Date())) {
                return false;
            }
        } catch (ParseException ex) {
            Logger.getLogger(CosmeticJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private boolean checkNull() {
        if (jComboboxCosName.getSelectedItem().toString().trim().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Tên mỹ phẩm không được bỏ trống!");
            return true;
        }
        if (jComboboxProName.getSelectedItem().toString().trim().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Nhà sản xuất không được bỏ trống!");
            return true;
        }
        if (dateCosEXP.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                    "HSD không được bỏ trống!");
            return true;
        }
        if (dateCosMFG.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                    "Ngày sản xuất không được bỏ trống!");
            return true;
        }
        if (jTextfieldCosCount.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng mỹ phẩm không được bỏ trống!");
            return true;
        }
        if (jTextfieldCosPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Giá mỹ phẩm không được bỏ trống!");
            return true;
        }
        if (jTextfieldPay.getText().equals("") && jTextfieldPay.isEnabled()) {
            JOptionPane.showMessageDialog(this,
                    "Thành tiền không được bỏ trống!");
            return true;
        }
        return false;
    }

    public void printListCos(ArrayList<Cosmetic> cosmetics) {
        for (int i = 0; i < cosmetics.size(); i++) {
            System.out.println(i);
            printCos(cosmetics.get(i));
        }
    }

    public void printCos(Cosmetic cosmetic) {
        System.out.println(cosmetic.cosID);
        System.out.println(cosmetic.cosName);
        System.out.println(cosmetic.proName);
        System.out.println(cosmetic.cosEXP);
        System.out.println(cosmetic.cosMFG);
        System.out.println(cosmetic.cosCount);
        System.out.println(cosmetic.cosPrice);
        System.out.println(cosmetic.dcibPay);
    }

    private String getIDFromCosName(Cosmetic cosmetic) {
        for (Cosmetic c : cosmetics) {
            if (cosmetic.cosName.equals(c.cosName)) {
                return c.cosID;
            }
        }
        return "";
    }

    private void addCosToTableTemp() {
        Cosmetic c = new Cosmetic();
        getInfoCosFromComponents(c);
//        c.cosID = selectedCos.cosID;
//        c.cosName = selectedCos.cosName;
//        c.proName = selectedCos.proName;
//        c.cosEXP = selectedCos.cosEXP;
//        c.cosMFG = selectedCos.cosMFG;
//        c.cosCount = selectedCos.cosCount;
//        c.cosPrice = selectedCos.cosPrice;
//        c.dcibPay = selectedCos.dcibPay;
        if(isCosTempExisted(c) != -1){

        }else{
            listCosTemp.add(c);
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        }
        
        showCosmeticsTemp(listCosTemp);
    }
    
    private int isCosTempExisted(Cosmetic c){
        for(int i=0; i<listCosTemp.size(); i++){
            if(listCosTemp.get(i).cosName.equals(c.cosName) && listCosTemp.get(i).cosMFG.equals(c.cosMFG)){
                listCosTemp.get(i).cosCount += c.cosCount;
                listCosTemp.get(i).dcibPay += c.dcibPay;
                if(listCosTemp.get(i).cosPrice != c.cosPrice){
                    JOptionPane.showMessageDialog(this, "Giá bán đã bị thay đổi!");
                }
                listCosTemp.get(i).cosPrice = c.cosPrice;
                return i;
            }
        }
        return -1;
    }
    private String chuanHoa(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        String temp[] = str.split(" ");
        str = "";
        for (int i = 0; i < temp.length; i++) {
            if(i==0){
                str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            }else{
                str += temp[i];
            }
            if (i < temp.length - 1)
                str += " ";
        }
        return str;
    }
}
