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
public class Producer implements ListIDStorage {

    public String proID;
    public String proName;

    public Producer(String proID, String proName) {
        this.proID = proID;
        this.proName = proName;
    }

    public Producer() {
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public void nhapProducer(String proID, String proName) {
        this.proID = proID;
        this.proName = proName;
    }

    private String convertIntToStringID(int id) {
        String sid = "P";
        String tmp = "";
        int pos = 3;
        while (pos > 0) {
            int x = id % 10;
            id /= 10;
            tmp += String.valueOf(x);
            pos--;
        }
        for (int i = 2; i >= 0; i--) {
            sid += tmp.charAt(i);
        }
        return sid;
    }

    public void initProID() {
        for (int i = 0; i < LIST_ID_SIZE; i++) {
            if (listIDProducer[i] != 1) {
                this.proID = convertIntToStringID(i);
                listIDProducer[i] = 1;
                break;
            }
        }
    }

    @Override
    public void initListID() {
        for (Producer p : CosmeticJPanel.producers) {
            System.out.println(p.proID);
            String tmp = p.proID.substring(1);
            int id = Integer.parseInt(tmp);
            listIDProducer[id] = 1;
        }
    }
}
