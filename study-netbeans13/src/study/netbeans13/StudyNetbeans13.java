/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package study.netbeans13;

import javax.swing.UIManager;
import study.netbeans13.test.MainFrame;

/**
 *
 * @author P088454
 */
public class StudyNetbeans13 {

    public static void main(String[] args) {        
        //com.formdev.flatlaf.FlatLightLaf.setup();
        System.out.println("layout = " + UIManager.getLookAndFeel().getClass().getName());
                
        //NumberAddition frame = new NumberAddition();
        //ImageDisplay frame = new ImageDisplay();
        //ContactEditorUI frame = new ContactEditorUI();
        //CelsiusConverter frame = new CelsiusConverter();
        //FramePanelControls frame = new FramePanelControls();
        MainFrame frame = new MainFrame();
        //ListDragAndDrop frame = new ListDragAndDrop();
        //ImageControl frame = new ImageControl();
        //TableControls frame = new TableControls();
        //FileChooserControls frame = new FileChooserControls();
        //TableListMulti frame = new TableListMulti();
        //PanelForm frame = new PanelForm();
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("TEST");
        
        frame.setResizable(false);
        
//        com.formdev.flatlaf.FlatLightLaf.setup(); // LAF 먼저
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            FramePanelControls frame = new FramePanelControls();
//            
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });

    }
    
}
