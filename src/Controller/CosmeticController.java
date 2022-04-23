/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.CosmeticJPanel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class CosmeticController {

    public static ArrayList<String> getListCosName(String cosName) {
        ArrayList<String> cosNames = new ArrayList<>();
        String sql = "select * from COSMETICS where cosName like '" + cosName + "%'";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cosNames.add(rs.getString("cosName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cosNames;
    }

    public static ArrayList<String> getCosName() {
        ArrayList<String> cosNames = new ArrayList<>();
        String sql = "select * from COSMETICS";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cosNames.add(rs.getString("cosName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cosNames;
    }

    public static String getProID(String cosName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COSMETICS where cosName=?");
            pre.setString(1, cosName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("proID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ToolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static void updateCosCount(String cosID, String cosMFG, int cosCount) {
        String sql = "update COS_BATCH set cosCount=? where cosID=? and cosMFG=?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cosCount);
            ps.setString(2, cosID);
            ps.setDate(3, Date.valueOf(cosMFG));
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ExistCosmetic(String cosName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COSMETICS where COSMETICS.cosName=?");
            pre.setString(1, cosName);
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void addCosmetic(String cosID, String cosName, String proID) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into COSMETICS values(?,?,?)");
            pre.setString(1, cosID);
            pre.setString(2, cosName);
            pre.setString(3, proID);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addCosBatch(String cosID, String cosEXP, String cosMFG, int cosCount, int cosPrice) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("insert into COS_BATCH values(?,?,?,?,?)");
            pre.setString(1, cosID);
            pre.setDate(2, Date.valueOf(cosEXP));
            pre.setDate(3, Date.valueOf(cosMFG));
            pre.setInt(4, cosCount);
            pre.setInt(5, cosPrice);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String ExistCosBatchMFG(String cosID, String cosEXP) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COS_BATCH where COS_BATCH.cosID=? and COS_BATCH.cosEXP=?");
            pre.setString(1, cosID);
            pre.setDate(2, Date.valueOf(cosEXP));
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return re.getDate("cosMFG").toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static boolean ExistCosBatchEXP(String cosID, String cosEXP) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COS_BATCH where COS_BATCH.cosID=? and COS_BATCH.cosEXP=?");
            pre.setString(1, cosID);
            pre.setDate(2, Date.valueOf(cosEXP));
            ResultSet re = pre.executeQuery();
            if (re.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ArrayList<String> getMFG(String cosID) {
        ArrayList<String> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COS_BATCH where cosID=?");
            pre.setString(1, cosID);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                if (!re.getDate("cosEXP").before(Date.valueOf(LocalDate.now()))) {
                    list.add(re.getDate("cosMFG").toString());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static int findCosPrice(String cosID, String cosMFG) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COS_BATCH where COS_BATCH.cosID=? and COS_BATCH.cosMFG=?");
            pre.setString(1, cosID);
            pre.setDate(2, Date.valueOf(cosMFG));
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getInt("cosPrice");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static int findCosCount(String cosID, String cosMFG) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COS_BATCH where COS_BATCH.cosID=? and COS_BATCH.cosMFG=?");
            pre.setString(1, cosID);
            pre.setDate(2, Date.valueOf(cosMFG));
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getInt("cosCount");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static String findCosName(String cosID) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COSMETICS where COSMETICS.cosID=?");
            pre.setString(1, cosID);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("cosName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String findCosID(String cosName) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COSMETICS where cosName=?");
            pre.setString(1, cosName);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("cosID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String findproID(String cosID) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement pre = con.prepareStatement("select *from COSMETICS where COSMETICS.cosID=?");
            pre.setString(1, cosID);
            ResultSet re = pre.executeQuery();
            while (re.next()) {
                return re.getString("proID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CosmeticController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static int getCountDetailCosIB(int ibID, String cosID, String cosMFG) {
        int cnt = 0;
        String sql = "select Count(ibID) as cnt_ibid "
                + "from DETAIL_COSMETICS_IN_BILL where cosID='"
                + cosID + "' and cosMFG='" + cosMFG + "' and ibID='" + ibID + "'";
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
    
    
    //-------------new----------------//
    public static ArrayList<Cosmetic> getListCosmetic(String cosName) {
        ArrayList<Cosmetic> cosmetics = new ArrayList<>();
        String sql = "select * from COSMETICS, COS_BATCH where COSMETICS.cosID "
                + "= COS_BATCH.cosID "
                + "and cosName like N'" + cosName + "%'";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cosmetic cosmetic = new Cosmetic();
                cosmetic.cosID = rs.getString("cosID");
                cosmetic.cosName = rs.getString("cosName");
                cosmetic.proName = ProducerController.getProName(rs.getString("proID"));
                cosmetic.cosEXP = rs.getString("cosEXP");
                cosmetic.cosMFG = rs.getString("cosMFG");
                cosmetic.cosCount = rs.getInt("cosCount");
                cosmetic.cosPrice = rs.getInt("cosPrice");

                cosmetics.add(cosmetic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cosmetics;
    }
    
    public static void updateCosmetic(Cosmetic cosmetic) {
        String sql = "update COS_BATCH set cosCount=? where cosID=? and cosMFG=?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cosmetic.cosCount);
            ps.setString(2, cosmetic.cosID);
            ps.setString(3, cosmetic.cosMFG);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean addExistCosmetic(Cosmetic cosmetic) {
        String sql = "insert into COS_BATCH(cosID, cosEXP, cosMFG, cosCount, "
                + "cosPrice) values(?,?,?,?,?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cosmetic.cosID);
            ps.setString(2, cosmetic.cosEXP);
            ps.setString(3, cosmetic.cosMFG);
            ps.setInt(4, cosmetic.cosCount);
            ps.setInt(5, cosmetic.cosPrice);

            CosmeticJPanel.cosmetics.add(cosmetic);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean addCosmetic(Cosmetic cosmetic) {
        String sql = "insert into COSMETICS(cosID, cosName, proID) values(?,?,?)"
                + " insert into COS_BATCH(cosID, cosEXP, cosMFG, cosCount, "
                + "cosPrice) values(?,?,?,?,?)";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cosmetic.cosID);
            ps.setString(2, cosmetic.cosName);
            ps.setString(3, ProducerController.getProID(cosmetic.proName));
            ps.setString(4, cosmetic.cosID);
            ps.setString(5, cosmetic.cosEXP);
            ps.setString(6, cosmetic.cosMFG);
            ps.setInt(7, cosmetic.cosCount);
            ps.setInt(8, cosmetic.cosPrice);

            CosmeticJPanel.cosmetics.add(cosmetic);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
