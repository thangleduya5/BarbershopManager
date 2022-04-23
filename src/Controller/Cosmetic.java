/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.ListIDStorage;
import View.CosmeticJPanel;


/**
 *
 * @author tienn
 */
public class Cosmetic implements ListIDStorage{
    public String cosID;
    public String cosName;
    public String proName;
    public String cosEXP;
    public String cosMFG;
    public int cosCount;
    public int cosPrice;
    public int dcibPay;
    
    public Cosmetic(String cosID, String cosName, String proName, String cosEXP,
            String cosMFG, int cosCount, int cosPrice, int dcibPay) {
        this.cosID = cosID;
        this.cosName = cosName;
        this.proName = proName;
        this.cosEXP = cosEXP;
        this.cosMFG = cosMFG;
        this.cosCount = cosCount;
        this.cosPrice = cosPrice;
        this.dcibPay = dcibPay;
    }
    
    public Cosmetic(){
        
    }
    
    private String convertIntToStringID(int id){
        String sid = "C";
        String tmp = "";
        int pos = 3;
        while(pos > 0){
            int x = id%10;
            id/=10;
            tmp += String.valueOf(x);
            pos--;
        }
        for(int i=2; i>=0; i--) sid += tmp.charAt(i);
        return sid;
    }
    
    
    public void initCosID(){
        for(int i=0; i<LIST_ID_SIZE; i++){
            if(listIDCosmetic[i] != 1){
                this.cosID = convertIntToStringID(i);
                listIDCosmetic[i] = 1;
                break;
            }
        }
    }

    @Override
    public void initListID() {
        for(Cosmetic c : CosmeticJPanel.cosmetics){
            String tmp = c.cosID.substring(1);
            int id = Integer.parseInt(tmp);
            listIDCosmetic[id] = 1;
        }
    }
    
    
    
    public boolean compareCos(Cosmetic cosmetic){
        if(this.cosName.equals(cosmetic.cosName)
                && cosMFG.equals(cosmetic.cosMFG) || 
           this.cosName.equals(cosmetic.cosName)
                && cosEXP.equals(cosmetic.cosEXP)){
            return true;
        }
        return false;
    }

}
