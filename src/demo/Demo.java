/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package demo;

import QuanLyKyThi.MainGUI;
import javax.swing.SwingUtilities;

/**
 *
 * @author ad
 */
public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        // Chạy GUI trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainGUI();
        });
    }
    
}
