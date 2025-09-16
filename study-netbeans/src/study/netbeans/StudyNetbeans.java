/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package study.netbeans;

import study.netbeans.controls.TableListMulti;
import java.awt.image.ImageConsumer;
import study.netbeans.controls.ListDragAndDrop;
import study.netbeans.controls.FileChooserControls;
import study.netbeans.controls.FramePanelControls;
import study.netbeans.controls.ImageControls;
import study.netbeans.controls.PanelForm;
import study.netbeans.controls.TableControls;
import study.netbeans.newframe.MainFrame;

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
        //FramePanelControls frame = new FramePanelControls();
        //MainFrame frame = new MainFrame();
        ListDragAndDrop frame = new ListDragAndDrop();
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
