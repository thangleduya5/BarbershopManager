/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ToolController {

    public static void addTool(String tooID, String tooName, String proID, int tooCount) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into TOOLS values(?,?,?,?)");
            pre.setString(1, tooID);
            pre.setString(2, tooName);
            pre.setString(3, proID);
            pre.setInt(4, tooCount);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> getTooName() {
        Connection con = DBConnection.getConnection();
        ArrayList<String> listName = new ArrayList<>();
        try {
            PreparedStatement pre = con.prepareStatement("select *from TOOLS");
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                listName.add(re.getString("tooName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ToolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listName;
    }

    public static String getProID(String tooName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from TOOLS where tooName=?");
            pre.setString(1, tooName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("proID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ToolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static boolean existTooName(String tooName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from TOOLS where tooName=?");
            pre.setString(1, tooName);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ToolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String findTooID(String tooName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from TOOLS where tooName=?");
            pre.setString(1, tooName);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return re.getString("tooID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ToolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    //update at 3/5/2021
    public static String findTooName(String tooID) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from TOOLS where tooID=?");
            pre.setString(1, tooID);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("tooName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public static boolean insert(Tool tol) {
        String sql = "insert into TOOLS (tooID, tooName, proID, tooCount) values (?, ?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, tol.getIdTol());
            pst.setString(2, tol.getTenTol());
            pst.setString(3, tol.getNhaSanXuat()); //SAI o day!!!!!!
            pst.setInt(4, tol.getSoLuong());
            if (pst.executeUpdate() > 0) {
                System.out.println("New Tool Added");
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ToolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean ExistTool(String tooID) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from TOOLS where tooID=?");
            pre.setString(1, tooID);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void modifyTool(String tooID, int tooCount) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("update TOOLS set TOOLS.tooCount=? where TOOLS.tooID=?");
            pre.setInt(1, tooCount);
            pre.setString(2, tooID);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateCountTool(String tooID, int tooCount) {
        Connection con = DBConnection.getConnection();
        try {
            System.out.println("co chay k");
            PreparedStatement pre0 = con.prepareStatement("select *from TOOLS where tooID=?");
            pre0.setString(1, tooID);
            ResultSet re0 = pre0.executeQuery();
            while (re0.next()) {
                PreparedStatement pre = con.prepareStatement("update TOOLS set TOOLS.tooCount=? where TOOLS.tooID=?");
                System.out.println(tooCount+re0.getInt("tooCount"));
                pre.setInt(1, tooCount+re0.getInt("tooCount"));
                pre.setString(2, tooID);
                pre.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
