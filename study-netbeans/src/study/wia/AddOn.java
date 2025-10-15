/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia;

//import com.formdev.flatlaf.FlatLightLaf;
//import study.wia.main.MainFrame;
//import hdwia.hvac.starccm.FrameRegistry;
//import java.awt.BorderLayout;
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.Dialog;
//import java.awt.Dimension;
//import java.awt.EventQueue;
//import java.awt.Frame;
//import java.awt.Window;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.net.URL;
//import javax.swing.Action;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JDialog;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
//import javax.swing.LookAndFeel;
//import javax.swing.PopupFactory;
//import javax.swing.SwingUtilities;
//import javax.swing.ToolTipManager;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//import org.openide.util.Exceptions;
//import star.common.Simulation;
//import star.coremodule.SimulationProcessObject;
//import star.coremodule.actions.ActiveSimulationAction;
//
//@star.locale.annotation.StarAction(display = "HDWIA_HVAC", hint = "HDWIA_HVAC")
//public class AddOn extends ActiveSimulationAction {
//    private MainFrame mainFrame;
//
//    // 중복실행되지 않음
//    // starccm이 종료될때 ui도 종료
//    // layout 적용
//    // ui가 활성화 되면 starccm에서 마우스 우클릭이 되지 않음    
//    @Override
//    protected Component getToolbarPresenter(Action action) {
//        URL url = getClass().getResource("/hdwia/hvac/resources/icon_1_32.png");
//        JButton button = new JButton(url != null ? new ImageIcon(url) : null);
//        button.setToolTipText("HDWIA_HVAC");
//
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SimulationProcessObject simulationProcessObject = getSimulationProcessObject();
//                if (simulationProcessObject == null || simulationProcessObject.getSimulation() == null) {
//                    JOptionPane.showMessageDialog(null,
//                            "활성 Simulation이 없습니다.\n프로젝트(.sim)를 열고 다시 시도하십시오.");
//                    return;
//                }
//                Simulation simulation = simulationProcessObject.getSimulation();
//                
//                simulation.print("******************************************* \n");
//                simulation.print("     start HDWIA_HVAC \n");
//                simulation.print("******************************************* \n");
//                
//                java.awt.EventQueue.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mainFrame == null) {
//                            simulation.print("[INFO] new Frame \n");
//                            simulation.println("layout = " + UIManager.getLookAndFeel().getClass().getName());
//                            
//                            // starccm ui와 충돌 발생
//                            //com.formdev.flatlaf.FlatLightLaf.setup();
//                            //simulation.print("[INFO] layout flatlaf \n");                            
//                            
//                            // 최초 생성
//                            mainFrame = new MainFrame(simulation);
//                            //mainFrame.setVisible(true);
//                            mainFrame.setLocationRelativeTo(null);
//                            mainFrame.setResizable(false);
//                            
//                            // 레지스트리에 등록
//                            FrameRegistry.register(mainFrame);
//                            
//                            // Frame 닫힐 때 참조 정리
//                            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
//                                @Override
//                                public void windowClosed(java.awt.event.WindowEvent e) {
//                                    // 레지스트리 비움
//                                    FrameRegistry.clear(); 
//                                    // 기존 참조 정리
//                                    mainFrame = null;
//                                    
//                                    simulation.print("[INFO] close mainFrame \n");
//                                }
//                            });
//                            mainFrame.getStartFrame().addWindowListener(new java.awt.event.WindowAdapter() {
//                                @Override
//                                public void windowClosed(java.awt.event.WindowEvent e) {
//                                    // 레지스트리 비움
//                                    FrameRegistry.clear(); 
//                                    // 기존 참조 정리
//                                    mainFrame = null;
//                                    
//                                    simulation.print("[INFO] close startFrame \n");
//                                }
//                            });
//                        } else {
//                            simulation.print("[INFO] already Frame \n");
//                            // 생성되어 있는 경우 맨앞으로
//                            // - .setVisible(false)인 경우 적용이 안됨
//                            // - main, sub frame 가 교차되므로 모든 frame이 다 나오면 isVisible()로 적용
//                            // - 추후 수정
//                            //mainFrame.setVisible(true);
//                            //mainFrame.toFront();
//                        }
//                    }
//                });
//            }
//        });
//        
//        return button;
//    }
//
//    @Override
//    public void performAction() {
//        // 메뉴에서 호출될 때 진입점. 필요 없으면 비워두어도 무방.
//    }
//
//}
