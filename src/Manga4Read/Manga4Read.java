/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manga4Read;

/**
 *
 * @author tenshi
 */
public class Manga4Read {

    /**
     * Version 1.0 
     * Servidores Soportados 
     * Submanga
     * Fakku
     * Hakihome
     * Mcanime
     */
    public static void main(String[] args) {
        database.crear();
        new Principal().setVisible(true);
    }
}
