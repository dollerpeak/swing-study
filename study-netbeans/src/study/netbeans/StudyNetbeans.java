/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package study.netbeans;

import study.netbeans.controls.FramePanelControls;

/**
 *
 * @author P088454
 */
public class StudyNetbeans {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        com.formdev.flatlaf.FlatLightLaf.setup();
                
        //NumberAddition frame = new NumberAddition();
        //ImageDisplay frame = new ImageDisplay();
        //ContactEditorUI frame = new ContactEditorUI();
        //CelsiusConverter frame = new CelsiusConverter();
        FramePanelControls frame = new FramePanelControls();
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        //frame.setTitle("TEST");
        
//        com.formdev.flatlaf.FlatLightLaf.setup(); // LAF 먼저
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            FramePanelControls frame = new FramePanelControls();
//            
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });

    }
    
}
