/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.CosmeticJPanel;
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
public class ProducerController {

    public static void addProducer(String proID, String proName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into PRODUCERS values(?,?)");
            pre.setString(1, proID);
            pre.setString(2, proName);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> getListProName() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "select * from PRODUCERS";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("proName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean ExistProducer(String proName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from PRODUCERS where PRODUCERS.proName=?");
            pre.setString(1, proName);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean ExistProducerID(String proID) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from PRODUCERS where PRODUCERS.proID=?");
            pre.setString(1, proID);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String getProName(String id) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from PRODUCERS where PRODUCERS.proID=?");
            pre.setString(1, id);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("proName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String getProID(String proName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from PRODUCERS where PRODUCERS.proName=?");
            pre.setString(1, proName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("proID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //-------------new--------------//
    public static ArrayList<Producer> getListProducer(String proName) {
        ArrayList<Producer> producers = new ArrayList<>();
        String sql = "select * from PRODUCERS where proName like '" + proName + "%'";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producer producer = new Producer();
                producer.proID = rs.getString("proID");
                producer.proName = rs.getString("proName");
                producers.add(producer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producers;
    }
    
    //----------new------------//
    public static boolean addProducer(Producer producer) {
        String sql = "insert into PRODUCERS(proID, proName)"
                + "VALUES(?,?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, producer.proID);
            ps.setString(2, producer.proName);

            CosmeticJPanel.producers.add(producer);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
