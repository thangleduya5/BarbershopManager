/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

public class Tool {
    private String idTol, tenTol, nhaSanXuat, ngaySanXuat;
    private int soLuong, donGia;

    public Tool() {
    }

    public Tool(String idTol, String tenTool, String nhaSanXuat, String ngaySanXuat, int soLuong, int donGia) {
        this.idTol = idTol;
        this.tenTol = tenTool;
        this.nhaSanXuat = nhaSanXuat;
        this.ngaySanXuat = ngaySanXuat;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getIdTol() {
        return idTol;
    }

    public void setIdTol(String idTol) {
        this.idTol = idTol;
    }

    public String getTenTol() {
        return tenTol;
    }

    public void setTenTol(String tenTol) {
        this.tenTol = tenTol;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(String ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
    
    public void nhapTool(String idTol, String tenTol, String nhaSanXuat, String ngaySanXuat, int soLuong, int donGia) {
        this.idTol = idTol;
        this.tenTol = tenTol;
        this.nhaSanXuat = nhaSanXuat;
        this.ngaySanXuat = ngaySanXuat;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
}
