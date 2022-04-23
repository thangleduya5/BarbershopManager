/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.MainFrame;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class BillController {

//    public static void addInBill(InBill ib) {
//        String sql = "insert into IN_BILL values(?,?)";
//        Connection conn = DBConnection.getConnection();
//        try {
//            PreparedStatement pre = conn.prepareStatement(sql);
//            pre.setString(1, ib.idUser);
//            pre.setDate(2, Date.valueOf(ib.ibDate));
//            pre.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static boolean existUserBill(String id) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from IN_BILL where id=?");
            pre.setString(1, id);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
            pre=con.prepareStatement("select *from OUT_BILL where id=?");
            pre.setString(1, id);
            re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean existInBill(int ibID) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from IN_BILL where ibID=?");
            pre.setInt(1, ibID);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                System.out.println("tồn tại");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void add_DetailTool_InBill(int ibID, String tooID, int dtibCount, int dtibPay) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into DETAIL_TOOLS_IN_BILL values(?,?,?,?)");
            System.out.println("đây là: " + ibID);
            pre.setInt(1, ibID);
            pre.setString(2, tooID);
            pre.setInt(3, dtibCount);
            pre.setInt(4, dtibPay);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add_DetailCosmetic_InBill(String ibID, String cosID, int dibCount) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into DETAIL_COSMETICS_IN_BILL values(?,?,?)");
            pre.setString(1, ibID);
            pre.setString(2, cosID);
            pre.setInt(3, dibCount);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addOutBill(String id, String obDate, int obPay) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into OUT_BILL (id, obDate, obPay) values(?,?,?)");
            pre.setString(1, id);
            pre.setString(2, obDate);
            pre.setInt(3, obPay);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void add_Detail_OutBill(int obID, String cosMFG, String cosID, int dobCount) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into DETAIL_OUT_BILL values(?,?,?,?)");
            pre.setInt(1, obID);
            pre.setDate(3, Date.valueOf(cosMFG));
            pre.setString(2, cosID);
            pre.setInt(4, dobCount);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int maxOutBill() {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select max(obID) from OUT_BILL");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static int getdcibCount(int ibID, String cosID, String cosMFG) {
        int cnt = 0;
        String sql = "select * "
                + "from DETAIL_COSMETICS_IN_BILL where cosID='"
                + cosID + "' and cosMFG='" + cosMFG + "' and ibID='" + ibID + "'";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cnt = rs.getInt("dcibCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }
    
    public static int getdcibPay(int ibID, String cosID, String cosMFG) {
        int cnt = 0;
        String sql = "select * "
                + "from DETAIL_COSMETICS_IN_BILL where cosID='"
                + cosID + "' and cosMFG='" + cosMFG + "' and ibID='" + ibID + "'";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cnt = rs.getInt("dcibPay");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }

    public static InBill getLastInBill() {
        InBill ib = new InBill();
        String sql = "select * from IN_BILL where ibID = (select Max(ibID) from IN_BILL)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ib.ibID = rs.getInt("ibID");
                ib.idUser = rs.getString("id");
                ib.ibDate = rs.getString("ibDate");
            }
            return ib;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getFullNameOutBill() {
        ArrayList<String> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct fullName from INFORS, OUT_BILL where OUT_BILL.id=INFORS.id");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getString("fullName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<String> getFullNameInBill() {
        ArrayList<String> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct fullName from INFORS, IN_BILL where IN_BILL.id=INFORS.id");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getString("fullName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<String> getIBDate() {
        ArrayList<String> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct ibDate from IN_BILL");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                if (re.getDate("ibDate") != null) {
                    result.add(re.getDate("ibDate").toString());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<String> getIBDateByFullName(String fullName) {
        ArrayList<String> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct ibDate from IN_BILL, INFORS where IN_BILL.id=INFORS.id and fullName=?");
            pre.setString(1, fullName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getDate("ibDate").toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<String> getOBDateByFullName(String fullName) {
        ArrayList<String> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct obDate from OUT_BILL, INFORS where OUT_BILL.id=INFORS.id and fullName=?");
            pre.setString(1, fullName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getDate("obDate").toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<String> getOBDate() {
        ArrayList<String> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct obDate from OUT_BILL");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getDate("obDate").toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //update on 3/5/2021
    public static int getPayInBill(String id) {
        int result = 0;
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select sum(dcibPay) from DETAIL_COSMETICS_IN_BILL where ibID=?");
            pre.setString(1, id);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result += re.getInt(1);
            }
            pre = con.prepareStatement("select sum(dtibPay) from DETAIL_TOOLS_IN_BILL where ibID=?");
            pre.setString(1, id);
            re = pre.executeQuery();
            while (re.next()) {
                result += re.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<Integer> getYearOutBill() {
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct year(obDate) from OUT_BILL");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<Integer> getYearInBill() {
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct year(ibDate) from IN_BILL");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ArrayList<Integer> getMonthOutBill(int year) {
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select distinct month(obDate) from OUT_BILL where year(obDate)=?");
            pre.setInt(1, year);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result.add(re.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static int getSales(int month, int year) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select sum(obPay) from OUT_BILL where month(obDate)=? and year(obDate)=?");
            pre.setInt(1, month);
            pre.setInt(2, year);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static int getExpense(int month, int year) {
        int result = 0;
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select ibID from IN_BILL where month(ibDate)=? and year(ibDate)=?");
            pre.setInt(1, month);
            pre.setInt(2, year);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                result += BillController.getPayInBill(re.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    //update on 4/5/2021

    //-----------new-------------------------//
    public static int getdcibPay(Cosmetic cosmetic) {
        int dcibPay = 0;
        String sql = "select dcibPay from DETAIL_COSMETICS_IN_BILL "
                + "where cosID='" + cosmetic.cosID + "' and cosMFG='" + cosmetic.cosMFG + "'";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dcibPay = rs.getInt("dcibPay");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dcibPay;
    }

    public static void updateCosToInBill(Cosmetic cosmetic) {
        String sql = "update DETAIL_COSMETICS_IN_BILL set dcibCount=?, dcibPay=? "
                + "where cosID=? and ibID=? and cosMFG=?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cosmetic.cosCount);
            ps.setInt(2, cosmetic.dcibPay);
            ps.setString(3, cosmetic.cosID);
            ps.setInt(4, MainFrame.lastIB.ibID +1);
            ps.setString(5, cosmetic.cosMFG);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addCosToInBill(Cosmetic cosmetic, int ibID) {
        String sql = "insert into DETAIL_COSMETICS_IN_BILL(ibID, cosID, cosMFG, dcibCount, dcibPay) values(?,?,?,?,?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ibID);
            ps.setString(2, cosmetic.cosID);
            ps.setString(3, cosmetic.cosMFG);
            ps.setInt(4, cosmetic.cosCount);
            ps.setInt(5, cosmetic.dcibPay);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getCountLastIbID() {
        int cnt = 0;
        String sql = "select Count(ibID) as cnt_ibid "
                + "from DETAIL_COSMETICS_IN_BILL where ibID = '" + MainFrame.lastIB.ibID + "'";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cnt = rs.getInt("cnt_ibid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }

    public static void initInBillWithUserDate(InBill ib) {
        String sql = "update IN_BILL set id=?, ibDate=?"
                + " where ibID=?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ib.idUser);
            ps.setString(2, ib.ibDate);
            ps.setInt(3, MainFrame.lastIB.ibID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addInBill(InBill ib) {
        String sql = "insert into IN_BILL values(?,?,?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, ib.ibID);
            pre.setString(2, ib.idUser);
            pre.setString(3, ib.ibDate);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
