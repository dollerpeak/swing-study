/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package study.netbeans;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLookAndFeel;
import study.netbeans.common.looandfeel.LookAndFeelManager;
import study.netbeans.controls.FramePanelControls;
import study.netbeans.etc.CelsiusConverter;

/**
 *
 * @author P088454
 */
public class StudyNetbeans {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        LookAndFeelManager lookAndFeelMgr = new LookAndFeelManager();
                
        //NumberAddition frame = new NumberAddition();
        //ImageDisplay frame = new ImageDisplay();
        //ContactEditorUI frame = new ContactEditorUI();
        //CelsiusConverter frame = new CelsiusConverter();
        FramePanelControls frame = new FramePanelControls();
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        //frame.setTitle("TEST");
    }
    
}
