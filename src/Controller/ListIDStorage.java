/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author tienn
 */
public interface ListIDStorage {
    public final int LIST_ID_SIZE = 1000;
    public int[] listIDProducer = new int[LIST_ID_SIZE];
    public int[] listIDCosmetic = new int[LIST_ID_SIZE];
    public void initListID();
}
