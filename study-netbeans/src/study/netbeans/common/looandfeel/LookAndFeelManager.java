/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.common.looandfeel;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import study.netbeans.common.logger.LoggerManager;

/**
 *
 * @author home
 */
public class LookAndFeelManager {

    LoggerManager loggerMgr;

    // 개별 프레임용
    public LookAndFeelManager(LoggerManager loggerMgr) {
        try {
            loggerMgr = loggerMgr;

            // default : javax.swing.plaf.metal.MetalLookAndFeel
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(new FlatLightLaf());

            loggerMgr.getLogger().info(UIManager.getLookAndFeel().getClass().getName());
        } catch (Exception e) {
            loggerMgr.getLogger().info("e = " + e.toString());
        }
    }

    // 전체 프레임용
    public LookAndFeelManager() {
        try {
            // default : javax.swing.plaf.metal.MetalLookAndFeel
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            //
        }
    }

}
