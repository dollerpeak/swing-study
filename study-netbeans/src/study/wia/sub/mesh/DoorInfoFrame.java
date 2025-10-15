/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package study.wia.sub.mesh;

import study.wia.common.CommonManager;
import study.wia.common.LoggerManager;
import hdwia.hvac.function.ListDragAndDropMoveHandler;
import study.wia.main.MainFrame;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import star.common.Simulation;

@SuppressWarnings("this-escape")
public class DoorInfoFrame extends javax.swing.JFrame {
    private MainFrame mainFrame;
    private Simulation simulation;
    private LoggerManager loggerMgr;
    
    private DefaultListModel<String> bodyIntakeListModel = new DefaultListModel<>(); 
    private DefaultListModel<String> bodyTemperatureListModel = new DefaultListModel<>(); 
    private DefaultListModel<String> bodyModeListModel = new DefaultListModel<>(); 
    private DefaultListModel<String> doorIntakeListModel = new DefaultListModel<>();
    private DefaultListModel<String> doorTemperatureListModel = new DefaultListModel<>();
    private DefaultListModel<String> doorModeListModel = new DefaultListModel<>();
    private DefaultListModel<String> partListModel = new DefaultListModel<>();
    
    

    /**
     * Creates new form DoorInfoFrame
     */
    public DoorInfoFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        simulation = mainFrame.getSimulation();        
        loggerMgr = mainFrame.getLoggerManager();
        
        initComponents();
        initialize();
        
        //loggerMgr.info("init DoorInfoFrame");
    }

    private DoorInfoFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void initialize() {
        initListData();
        setTestData();
    }
    
    private void initListData() {
        // set list data model
        bodyIntakeList.setModel(bodyIntakeListModel);
        bodyTemperatureList.setModel(bodyTemperatureListModel);        
        bodyModeList.setModel(bodyModeListModel);
        doorIntakeList.setModel(doorIntakeListModel);
        doorTemperatureList.setModel(doorTemperatureListModel);        
        doorModeList.setModel(doorModeListModel);        
        partList.setModel(partListModel);
        
        // set handler
        bodyIntakeList.setTransferHandler(new ListDragAndDropMoveHandler(loggerMgr));
        bodyTemperatureList.setTransferHandler(new ListDragAndDropMoveHandler(loggerMgr));
        bodyModeList.setTransferHandler(new ListDragAndDropMoveHandler(loggerMgr));
        doorIntakeList.setTransferHandler(new ListDragAndDropMoveHandler(loggerMgr));
        doorTemperatureList.setTransferHandler(new ListDragAndDropMoveHandler(loggerMgr));
        doorModeList.setTransferHandler(new ListDragAndDropMoveHandler(loggerMgr));
        partList.setTransferHandler(new ListDragAndDropMoveHandler(loggerMgr));
    }
    
    private void setTestData() {
        partListModel.addElement("body_0");
        partListModel.addElement("body_1");
        partListModel.addElement("body_2");
        partListModel.addElement("body_3");
        partListModel.addElement("body_4");
        partListModel.addElement("body_5");
        partListModel.addElement("body_6");
        partListModel.addElement("body_7");
        partListModel.addElement("body_8");
        partListModel.addElement("door_0");
        partListModel.addElement("door_1");
        partListModel.addElement("door_2");
        partListModel.addElement("door_3");
        partListModel.addElement("door_4");
        partListModel.addElement("door_5");
        partListModel.addElement("door_6");
        partListModel.addElement("door_7");
        partListModel.addElement("door_8");
        
        String temp = "Base size\n"
                + "Minimum size\n"
                + "Maximum size\n"
                + "Prism Layer Thickness";
        doorTextArea1.setText(temp);
        doorTextArea2.setText(temp);
        doorTextArea3.setText(temp);
    }
    
    
    /****************************************************************************
    * funtion
    ****************************************************************************/    
    private void deleteListData(JList<String> list, DefaultListModel<String> model) {
        int[] index;
        int result;

        index = list.getSelectedIndices();
        Arrays.sort(index);

        if (index.length > 0) {
            result = CommonManager.showConfirmDialog(this, "삭제", "선택한 항목을 삭제하시겠습니까?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                for (int i = index.length - 1; i >= 0; i -= 1) {
                    model.remove(index[i]);
                }
            }
        }
    }

    private void renameListData(JList<String> list, DefaultListModel<String> model) {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            String currentValue = model.getElementAt(selectedIndex);

            // 입력 다이얼로그 표시
            String newValue = CommonManager.showInputDialog(this, "수정할 내용을 입력하세요.", currentValue);

            // 취소했거나 빈 문자열이면 변경하지 않음
            if (newValue != null) {
                newValue = newValue.trim(); // 공백 제거
                if (newValue.length() > 0) {
                    model.setElementAt(newValue, selectedIndex);
                } else {
                    CommonManager.showMessageDialog(this, "알림", "빈 값으로 변경할 수 없습니다.", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                // 취소이벤트
            }
        }
    }
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bodyIntakeList = new javax.swing.JList<String>();
        jScrollPane2 = new javax.swing.JScrollPane();
        bodyTemperatureList = new javax.swing.JList<String>();
        jScrollPane3 = new javax.swing.JScrollPane();
        bodyModeList = new javax.swing.JList<String>();
        jScrollPane4 = new javax.swing.JScrollPane();
        doorIntakeList = new javax.swing.JList<String>();
        jScrollPane5 = new javax.swing.JScrollPane();
        doorTemperatureList = new javax.swing.JList<String>();
        jScrollPane6 = new javax.swing.JScrollPane();
        doorModeList = new javax.swing.JList<String>();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        doorTextArea1 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        doorTextArea2 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        doorTextArea3 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        partList = new javax.swing.JList<String>();
        resetButton = new javax.swing.JButton();
        rreviewButton = new javax.swing.JButton();
        doorSettingButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        returnButton = new javax.swing.JButton();

        setTitle("현대위아 HVAC 해석 > Door 정보 확인");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("인테이크 도어");

        jLabel3.setText("온도 도어");

        jLabel4.setText("모드도어");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel1)
                .addGap(101, 101, 101)
                .addComponent(jLabel3)
                .addGap(117, 117, 117)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel2.setText("바디 ");

        jLabel5.setText("도어 ");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(68, 68, 68))
        );

        bodyIntakeList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        bodyIntakeList.setDragEnabled(true);
        bodyIntakeList.setDropMode(javax.swing.DropMode.INSERT);
        bodyIntakeList.setPrototypeCellValue("width");
        bodyIntakeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bodyIntakeListMouseClicked(evt);
            }
        });
        bodyIntakeList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bodyIntakeListKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(bodyIntakeList);

        bodyTemperatureList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        bodyTemperatureList.setDragEnabled(true);
        bodyTemperatureList.setDropMode(javax.swing.DropMode.INSERT);
        bodyTemperatureList.setPrototypeCellValue("width");
        bodyTemperatureList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bodyTemperatureListMouseClicked(evt);
            }
        });
        bodyTemperatureList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bodyTemperatureListKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(bodyTemperatureList);

        bodyModeList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        bodyModeList.setDragEnabled(true);
        bodyModeList.setDropMode(javax.swing.DropMode.INSERT);
        bodyModeList.setPrototypeCellValue("width");
        bodyModeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bodyModeListMouseClicked(evt);
            }
        });
        bodyModeList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bodyModeListKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(bodyModeList);

        doorIntakeList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        doorIntakeList.setDragEnabled(true);
        doorIntakeList.setDropMode(javax.swing.DropMode.INSERT);
        doorIntakeList.setPrototypeCellValue("width");
        doorIntakeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doorIntakeListMouseClicked(evt);
            }
        });
        doorIntakeList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                doorIntakeListKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(doorIntakeList);

        doorTemperatureList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        doorTemperatureList.setDragEnabled(true);
        doorTemperatureList.setDropMode(javax.swing.DropMode.INSERT);
        doorTemperatureList.setPrototypeCellValue("width");
        doorTemperatureList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doorTemperatureListMouseClicked(evt);
            }
        });
        doorTemperatureList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                doorTemperatureListKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(doorTemperatureList);

        doorModeList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        doorModeList.setDragEnabled(true);
        doorModeList.setDropMode(javax.swing.DropMode.INSERT);
        doorModeList.setPrototypeCellValue("width");
        doorModeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doorModeListMouseClicked(evt);
            }
        });
        doorModeList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                doorModeListKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(doorModeList);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        doorTextArea1.setEditable(false);
        doorTextArea1.setColumns(20);
        doorTextArea1.setRows(5);
        jScrollPane7.setViewportView(doorTextArea1);

        doorTextArea2.setEditable(false);
        doorTextArea2.setColumns(20);
        doorTextArea2.setRows(5);
        jScrollPane8.setViewportView(doorTextArea2);

        doorTextArea3.setEditable(false);
        doorTextArea3.setColumns(20);
        doorTextArea3.setRows(5);
        jScrollPane9.setViewportView(doorTextArea3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(jScrollPane8)
                    .addComponent(jScrollPane9))
                .addContainerGap())
        );

        jLabel6.setText("도어 작동각도 선택");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(161, 161, 161))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Part List"));

        partList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        partList.setDragEnabled(true);
        partList.setDropMode(javax.swing.DropMode.INSERT);
        partList.setPrototypeCellValue("width");
        partList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                partListMouseClicked(evt);
            }
        });
        partList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                partListKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(partList);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );

        resetButton.setText("Reset");

        rreviewButton.setText("Preview");

        doorSettingButton.setText("도어 세팅 적용");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(rreviewButton, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(doorSettingButton, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rreviewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(doorSettingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        returnButton.setText("Return");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /****************************************************************************
    * frame
    ****************************************************************************/
    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        this.setVisible(false);
        mainFrame.setVisible(true);
    }//GEN-LAST:event_returnButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.setVisible(false);
        mainFrame.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.setVisible(false);
        mainFrame.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    
    /****************************************************************************
    * delete list data
    ****************************************************************************/
    private void bodyIntakeListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bodyIntakeListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(bodyIntakeList, bodyIntakeListModel);
        }
    }//GEN-LAST:event_bodyIntakeListKeyPressed

    private void bodyTemperatureListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bodyTemperatureListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(bodyTemperatureList, bodyTemperatureListModel);
        }
    }//GEN-LAST:event_bodyTemperatureListKeyPressed

    private void bodyModeListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bodyModeListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(bodyModeList, bodyModeListModel);
        }
    }//GEN-LAST:event_bodyModeListKeyPressed

    private void doorIntakeListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_doorIntakeListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(doorIntakeList, doorIntakeListModel);
        }
    }//GEN-LAST:event_doorIntakeListKeyPressed

    private void doorTemperatureListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_doorTemperatureListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(doorTemperatureList, doorTemperatureListModel);
        }
    }//GEN-LAST:event_doorTemperatureListKeyPressed

    private void doorModeListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_doorModeListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(doorModeList, doorModeListModel);
        }
    }//GEN-LAST:event_doorModeListKeyPressed

    private void partListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_partListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteListData(partList, partListModel);
        }
    }//GEN-LAST:event_partListKeyPressed

    
    /***************************************************************************
    * rename list data
    ****************************************************************************/
    private void bodyIntakeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bodyIntakeListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(bodyIntakeList, bodyIntakeListModel);
        }
    }//GEN-LAST:event_bodyIntakeListMouseClicked

    private void bodyTemperatureListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bodyTemperatureListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(bodyTemperatureList, bodyTemperatureListModel);
        }
    }//GEN-LAST:event_bodyTemperatureListMouseClicked

    private void bodyModeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bodyModeListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(bodyModeList, bodyModeListModel);
        }
    }//GEN-LAST:event_bodyModeListMouseClicked

    private void doorIntakeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doorIntakeListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(doorIntakeList, doorIntakeListModel);
        }
    }//GEN-LAST:event_doorIntakeListMouseClicked

    private void doorTemperatureListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doorTemperatureListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(doorTemperatureList, doorTemperatureListModel);
        }
    }//GEN-LAST:event_doorTemperatureListMouseClicked

    private void doorModeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doorModeListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(doorModeList, doorModeListModel);
        }
    }//GEN-LAST:event_doorModeListMouseClicked

    private void partListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_partListMouseClicked
        // 더블 클릭
        if (evt.getClickCount() == 2) {
            renameListData(partList, partListModel);
        }
    }//GEN-LAST:event_partListMouseClicked
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DoorInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoorInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoorInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoorInfoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DoorInfoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> bodyIntakeList;
    private javax.swing.JList<String> bodyModeList;
    private javax.swing.JList<String> bodyTemperatureList;
    private javax.swing.JList<String> doorIntakeList;
    private javax.swing.JList<String> doorModeList;
    private javax.swing.JButton doorSettingButton;
    private javax.swing.JList<String> doorTemperatureList;
    private javax.swing.JTextArea doorTextArea1;
    private javax.swing.JTextArea doorTextArea2;
    private javax.swing.JTextArea doorTextArea3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JList<String> partList;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton rreviewButton;
    // End of variables declaration//GEN-END:variables
}
