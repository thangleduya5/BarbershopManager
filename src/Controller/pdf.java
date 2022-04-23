/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class pdf {

    public static PdfPCell getCell(String value, Font f) {
        PdfPCell cell = new PdfPCell(new Paragraph(value, f));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public static String getDate() {
        return "Hồ Chí Minh, ngày " + LocalDate.now().getDayOfMonth() + " tháng " + LocalDate.now().getMonth().getValue() + " năm " + LocalDate.now().getYear();
    }

    public static void pdfUser() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("DSNhanVien.pdf"));
            document.open();
            Connection con = DBConnection.getConnection();
            PreparedStatement pre = con.prepareStatement("select *from INFORS");
            ResultSet re = pre.executeQuery();
            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 13, Font.ITALIC);
            //
            Paragraph p = new Paragraph("DANH SÁCH NHÂN VIÊN", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            PdfPTable table;
            table = new PdfPTable(new float[]{3, 8, 10, 3, 3, 15});//Thiết lập tỉ lệ giữa các cột trong bảng
            table.setWidthPercentage(100);//Thiết lập chiều rộng
            table.addCell(getCell("ID", fontCo));
            table.addCell(getCell("Tên đăng nhập", fontCo));
            table.addCell(getCell("Tên đầy đủ", fontCo));
            table.addCell(getCell("Tuổi", fontCo));
            table.addCell(getCell("Giới tính", fontCo));
            table.addCell(getCell("Địa chỉ", fontCo));
            while (re.next()) {
                table.addCell(getCell(re.getString("id"), f));
                table.addCell(getCell(re.getString("userName"), f));
                table.addCell(getCell(re.getString("fullName"), f));
                table.addCell(getCell(re.getInt("age") + "", f));
                table.addCell(getCell(re.getString("sex"), f));
                table.addCell(getCell(re.getString("address"), f));
            }

            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
            document.add(table);
            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pdfTool() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("DSDungcu.pdf"));
            document.open();
            Connection con = DBConnection.getConnection();
            PreparedStatement pre = con.prepareStatement("select *from TOOLS");
            ResultSet re = pre.executeQuery();
            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 13, Font.ITALIC);
            //
            Paragraph p = new Paragraph("DANH SÁCH DỤNG CỤ", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            PdfPTable table;
            table = new PdfPTable(new float[]{3, 8, 15, 5});//Thiết lập tỉ lệ giữa các cột trong bảng
            table.setWidthPercentage(100);//Thiết lập chiều rộng
            table.addCell(getCell("ID", fontCo));
            table.addCell(getCell("Tên dụng cụ", fontCo));
            table.addCell(getCell("Nhà sản xuất", fontCo));
            table.addCell(getCell("Số lượng tồn", fontCo));
            while (re.next()) {
                table.addCell(getCell(re.getString("tooID"), f));
                table.addCell(getCell(re.getString("tooName"), f));
                table.addCell(getCell(ProducerController.getProName(re.getString("proID")), f));
                table.addCell(getCell(re.getInt("tooCount") + "", f));
            }

            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
            document.add(table);
            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pdfCosmetic() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("DSMypham.pdf"));
            document.open();
            Connection con = DBConnection.getConnection();
            PreparedStatement pre = con.prepareStatement("select *from COSMETICS");
            ResultSet re = pre.executeQuery();
            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 13, Font.ITALIC);
            //
            Paragraph p = new Paragraph("DANH SÁCH MỸ PHẨM", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            PdfPTable table, table2;
            table2 = new PdfPTable(new float[]{2, 4, 6, 4, 4, 4, 4});//Thiết lập tỉ lệ giữa các cột trong bảng
            table2.setWidthPercentage(100);//Thiết lập chiều rộng
            table2.addCell(getCell("ID", fontCo));
            table2.addCell(getCell("Tên mỹ phẩm", fontCo));
            table2.addCell(getCell("Nhà sản xuất", fontCo));
            table2.addCell(getCell("Hạn sử dụng", fontCo));
            table2.addCell(getCell("Ngày sản xuất", fontCo));
            table2.addCell(getCell("Số lượng tồn", fontCo));
            table2.addCell(getCell("Đơn giá", fontCo));
            table = new PdfPTable(new float[]{2, 4, 6, 16});//Thiết lập tỉ lệ giữa các cột trong bảng
            table.setWidthPercentage(100);//Thiết lập chiều rộng
            while (re.next()) {
                table.addCell(getCell(re.getString("cosID"), f));
                table.addCell(getCell(re.getString("cosName"), f));
                table.addCell(getCell(ProducerController.getProName(re.getString("proID")), f));
                PreparedStatement pre1 = con.prepareStatement("select *from COS_BATCH where COS_BATCH.cosID=?");
                pre1.setString(1, re.getString("cosID"));
                ResultSet re1 = pre1.executeQuery();
                PdfPTable table1 = new PdfPTable(new float[]{4, 4, 4, 4});
                while (re1.next()) {
                    table1.addCell(getCell(re1.getString("cosEXP"), f));
                    table1.addCell(getCell(re1.getString("cosMFG"), f));
                    table1.addCell(getCell(re1.getString("cosCount"), f));
                    table1.addCell(getCell(re1.getString("cosPrice"), f));
                }
                table.addCell(table1);
            }

            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
            document.add(table2);
            document.add(table);
            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pdfBill(int obID, int obPay, String fullName) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Hoadon.pdf"));
            document.open();
            Connection con = DBConnection.getConnection();
            PreparedStatement pre = con.prepareStatement("select *from OUT_BILL where OUT_BILL.obID=?");
            pre.setInt(1, obID);
            ResultSet re = pre.executeQuery();
            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 11, Font.ITALIC);
            Font fontUser = new Font(b, 11, Font.UNDERLINE);
            //
            Paragraph p = new Paragraph("HÓA ĐƠN", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(10);
            document.add(p);

            while (re.next()) {
                Paragraph pUser = new Paragraph("Nhân viên bán hàng: " + fullName, fontUser);
                pUser.setAlignment(Element.ALIGN_LEFT);
                pUser.setSpacingAfter(15);
                document.add(pUser);
                PdfPTable table;
                table = new PdfPTable(new float[]{8, 10, 8, 5});//Thiết lập tỉ lệ giữa các cột trong bảng
                table.setWidthPercentage(100);//Thiết lập chiều rộng
                table.addCell(getCell("Tên sản phẩm", fontCo));
                table.addCell(getCell("Nhà sản xuất", fontCo));
                table.addCell(getCell("Đơn giá", fontCo));
                table.addCell(getCell("Số lượng", fontCo));
                PreparedStatement pre1 = con.prepareStatement("select *from DETAIL_OUT_BILL where DETAIL_OUT_BILL.obID=?");
                pre1.setString(1, re.getString("obID"));
                ResultSet re1 = pre1.executeQuery();
                while (re1.next()) {
                    table.addCell(getCell(CosmeticController.findCosName(re1.getString("cosID")), f));//Tên mỹ phẩm
                    table.addCell(getCell(ProducerController.getProName(CosmeticController.findproID(re1.getString("cosID"))), f));//Tên nhà sản xuất
                    table.addCell(getCell(CosmeticController.findCosPrice(re1.getString("cosID"), re1.getDate("cosMFG").toString()) + "", f));//Đơn giá
                    table.addCell(getCell(re1.getInt("dobCount") + "", f));//Số lượng
                }
                document.add(table);

            }

            Paragraph pTotal = new Paragraph("Thành tiền: " + obPay + " vnd", f);
            pTotal.setAlignment(Element.ALIGN_RIGHT);
            pTotal.setSpacingAfter(10);
            pTotal.setSpacingBefore(10);

            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);

            document.add(pTotal);

            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //update at 3/5/2021
    public static void pdfStatisticBill(DefaultTableModel d) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Thongkehoadon.pdf"));
            document.open();

            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 11, Font.ITALIC);
            Font fontUser = new Font(b, 11, Font.UNDERLINE);
            //
            Paragraph p = new Paragraph("HÓA ĐƠN", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(10);
            document.add(p);

            PdfPTable table;
            table = new PdfPTable(new float[]{4, 10, 8, 8});//Thiết lập tỉ lệ giữa các cột trong bảng
            table.setWidthPercentage(100);//Thiết lập chiều rộng
            table.addCell(getCell("ID", fontCo));
            table.addCell(getCell("Họ tên nhân viên", fontCo));
            table.addCell(getCell("Ngày lập", fontCo));
            table.addCell(getCell("Thành tiền", fontCo));
            for (int k = 0; k < d.getRowCount(); k++) {
                for (int i = 0; i < d.getColumnCount(); i++) {
                    table.addCell(getCell(d.getValueAt(k, i) + "", f));
                }
            }

            document.add(table);

            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);

            //add image 
//            Image image = Image.getInstance("Doanhthu-Kinhphi.jpeg");
//            image.setAlignment(Element.ALIGN_CENTER);
//            image.scaleAbsolute(500, 500);
//            document.add(image);
            //
            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pdfStatisticDetailInBill(int status, String id, String fullName, String date, int Pay) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Thongkechitiethoadon.pdf"));
            document.open();

            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 11, Font.ITALIC);
            Font fontUser = new Font(b, 11, Font.UNDERLINE);
            //
            Paragraph p = new Paragraph("CHI TIẾT HÓA ĐƠN", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(10);
            document.add(p);

            document.add(new Paragraph("ID: "+id, f));
            document.add(new Paragraph("Họ và tên: "+fullName, f));
            document.add(new Paragraph("Ngày lập: "+date, f));
            document.add(new Paragraph("Tổng tiền: "+Pay, f));

            PdfPTable table;
            table = new PdfPTable(new float[]{10, 8, 5, 8});//Thiết lập tỉ lệ giữa các cột trong bảng
            table.setWidthPercentage(100);//Thiết lập chiều rộng
            table.setSpacingBefore(10);
            table.addCell(getCell("Tên mỹ phẩm/dụng cụ", fontCo));
            table.addCell(getCell("Ngày sản xuất", fontCo));
            table.addCell(getCell("Số lượng", fontCo));
            table.addCell(getCell("Thành tiền", fontCo));

            Connection con = DBConnection.getConnection();
            try {
                if (status == 0) {
                    PreparedStatement pre = con.prepareStatement("select *from DETAIL_COSMETICS_IN_BILL where ibID=?");
                    pre.setString(1, id);
                    ResultSet re = pre.executeQuery();
                    while (re.next()) {
                        table.addCell(getCell(CosmeticController.findCosName(re.getString("cosID")), f));
                        table.addCell(getCell(re.getDate("cosMFG").toString(), f));
                        table.addCell(getCell(re.getInt("dcibCount") + "", f));
                        table.addCell(getCell(re.getInt("dcibPay") + "", f));
                    }
                    pre = con.prepareStatement("select *from DETAIL_TOOLS_IN_BILL where ibID=?");
                    pre.setString(1, id);
                    re = pre.executeQuery();
                    while (re.next()) {
                        table.addCell(getCell(ToolController.findTooName(re.getString("tooID")), f));
                        table.addCell(getCell(null, f));
                        table.addCell(getCell(re.getInt("dtibCount") + "", f));
                        table.addCell(getCell(re.getInt("dtibPay") + "", f));
                    }
                } else{
                    PreparedStatement pre = con.prepareStatement("select *from DETAIL_OUT_BILL where obID=?");
                    pre.setString(1, id);
                    ResultSet re = pre.executeQuery();
                    while (re.next()) {
                        table.addCell(getCell(CosmeticController.findCosName(re.getString("cosID")), f));
                        table.addCell(getCell(re.getDate("cosMFG").toString(), f));
                        table.addCell(getCell(re.getInt("dobCount") + "", f));
                        table.addCell(getCell(re.getInt("dobCount")*CosmeticController.findCosPrice(re.getString("cosID"), re.getDate("cosMFG").toString()) + "", f));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
            }

            document.add(table);

            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);
            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pdfStatisticSales_Expense(DefaultTableModel d) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Thongkedoanhthu-kinhphi.pdf"));
            document.open();

            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 11, Font.ITALIC);
            Font fontUser = new Font(b, 11, Font.UNDERLINE);
            //
            Paragraph p = new Paragraph("Doanh thu - Kinh phí", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(10);
            document.add(p);

            PdfPTable table;
            table = new PdfPTable(new float[]{6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});//Thiết lập tỉ lệ giữa các cột trong bảng
            table.setWidthPercentage(100);//Thiết lập chiều rộng
            table.addCell(getCell("Tháng", fontCo));
            for (int i = 1; i < 13; i++) {
                table.addCell(getCell(i + "", f));
            }
            table.addCell(getCell("Doanh thu", fontCo));
            for (int i = 1; i < 13; i++) {
                table.addCell(getCell(d.getValueAt(i - 1, 1).toString(), f));
            }
            table.addCell(getCell("Kinh phí", fontCo));
            for (int i = 1; i < 13; i++) {
                table.addCell(getCell(d.getValueAt(i - 1, 2).toString(), f));
            }

            document.add(table);

            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);

            //add image 
            Image image = Image.getInstance("Doanhthu-Kinhphi.jpeg");
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleAbsolute(500, 500);
            document.add(image);
            //
            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pdfStatisticSales_Cosmetic(DefaultTableModel d) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Thongkedoanhthu-mypham.pdf"));
            document.open();

            //Thiết lập font chữ
            BaseFont b = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font f = new Font(b, 13, Font.NORMAL);
            Font fontTitle = new Font(b, 20, Font.BOLD, BaseColor.BLUE);
            Font fontCo = new Font(b, 13, Font.BOLD);
            Font fontDate = new Font(b, 11, Font.ITALIC);
            Font fontUser = new Font(b, 11, Font.UNDERLINE);
            //
            Paragraph p = new Paragraph("Doanh thu - Mỹ phẩm", fontTitle);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(10);
            document.add(p);

            PdfPTable table;
            table = new PdfPTable(d.getRowCount() + 1);//Thiết lập tỉ lệ giữa các cột trong bảng
            table.setWidthPercentage(100);//Thiết lập chiều rộng
            table.addCell(getCell("Tên mỹ phẩm", fontCo));
            for (int i = 0; i < d.getRowCount(); i++) {
                table.addCell(getCell(d.getValueAt(i, 0) + "", f));
            }
            table.addCell(getCell("Doanh thu", fontCo));
            for (int i = 0; i < d.getRowCount(); i++) {
                table.addCell(getCell(d.getValueAt(i, 1).toString(), f));
            }

            document.add(table);
            Paragraph pDate = new Paragraph(getDate(), fontDate);
            pDate.setAlignment(Element.ALIGN_RIGHT);

            //add image 
            Image image = Image.getInstance("Doanhthu-Kinhphi.jpeg");
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleAbsolute(500, 500);
            document.add(image);
            //
            
            document.add(pDate);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
