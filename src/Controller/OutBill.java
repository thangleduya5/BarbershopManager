/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Admin
 */
public class OutBill {
    public String cosName;
    public String proName;
    public String cosMFG;
    public int cosPrice;
    public int dobCount;
    public int cosCount;

    public OutBill(String cosName, String proName, String cosMFG, int cosPrice, int dobCount, int cosCount) {
        this.cosName = cosName;
        this.proName = proName;
        this.cosMFG = cosMFG;
        this.cosPrice = cosPrice;
        this.dobCount = dobCount;
        this.cosCount = cosCount;
    }
    
    public OutBill(){
        this.cosName="";
        this.proName="";
        this.cosMFG="";
        this.cosPrice=0;
        this.dobCount=0;
        this.cosCount=0;
    }

    public String getCosName() {
        return cosName;
    }

    public void setCosName(String cosName) {
        this.cosName = cosName;
    }

    public int getCosCount() {
        return cosCount;
    }

    public void setCosCount(int cosCount) {
        this.cosCount = cosCount;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCosMFG() {
        return cosMFG;
    }

    public void setCosMFG(String cosMFG) {
        this.cosMFG = cosMFG;
    }

    public int getCosPrice() {
        return cosPrice;
    }

    public void setCosPrice(int cosPrice) {
        this.cosPrice = cosPrice;
    }

    public int getDobCount() {
        return dobCount;
    }

    public void setDobCount(int dobCount) {
        this.dobCount = dobCount;
    }
    
    public Object[] toObject(){
        return new Object[]{cosName, proName, cosMFG, cosPrice, dobCount};
    }
}
