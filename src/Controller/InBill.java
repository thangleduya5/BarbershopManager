/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.ListIDStorage;

/**
 *
 * @author tienn
 */
public class InBill implements ListIDStorage{
    public int ibID;
    public String idUser;
    public String ibDate;

    public InBill() {
    }

    public InBill(int ibID, String idUser, String ibDate) {
        this.ibID = ibID;
        this.idUser = idUser;
        this.ibDate = ibDate;
    }

    @Override
    public void initListID() {
        
    }
    
    
}
