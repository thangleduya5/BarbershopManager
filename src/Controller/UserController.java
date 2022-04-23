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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserController {

    public static void addUser(String userName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into USERS (userName, userPassword, RoleID) values(?,?,?)");
            pre.setString(1, userName);
            pre.setString(2, "1234");
            pre.setString(3, "2");
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addInfor(String id, String userName, String fullName, int age, String sex, String address) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into INFORS values(?,?,?,?,?,?)");
            pre.setString(1, id);
            pre.setString(2, userName);
            pre.setString(3, fullName);
            pre.setInt(4, age);
            pre.setString(5, sex);
            pre.setString(6, address);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyInfor(String id, String fullName, int age, String sex, String address) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("update INFORS set INFORS.fullName=?, INFORS.age=?, INFORS.sex=?, INFORS.address=? where INFORS.id=?");
            pre.setString(1, fullName);
            pre.setInt(2, age);
            pre.setString(3, sex);
            pre.setString(4, address);
            pre.setString(5, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyInforUserName(String userNameOld, String userNameNew) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("update INFORS set INFORS.userName=? where INFORS.userName=?");
            pre.setString(1, userNameNew);
            pre.setString(2, userNameOld);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modifyLoginUser(String pass, String userName, String userNameOld) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("update USERS set USERS.userPassword=?, USERS.userName=? where USERS.userName=?");
            pre.setString(1, pass);
            pre.setString(2, userName);
            pre.setString(3, userNameOld);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteUser(String userName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("delete from USERS where USERS.userName=?");
            pre.setString(1, userName);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean ExistUser(String userName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from USERS where USERS.userName=?");
            pre.setString(1, userName);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String checkLogin(String userName, String password) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from USERS where USERS.userName=?");
            pre.setString(1, userName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                if (re.getString("userPassword").toString().trim().equals(password)) {
                    return re.getString("roleID");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "0";
    }

    public static boolean ExistInfor(String id) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from INFORS where INFORS.id=?");
            pre.setString(1, id);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean ExistFullName(String fullName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from INFORS where INFORS.fullName=?");
            pre.setString(1, fullName);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void deleteInfor(String id) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("delete from INFORS where INFORS.id=?");
            pre.setString(1, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String findUserName(String id) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from USERS where USERS.id=?");
            pre.setString(1, id);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("userName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String findFullName(String id) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from INFORS where INFORS.id=?");
            pre.setString(1, id);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("fullName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String findID(String userName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from INFORS where INFORS.userName=?");
            pre.setString(1, userName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
