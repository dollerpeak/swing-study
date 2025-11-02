/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package study.netbeans.controls;

import java.awt.Color;
import java.util.logging.Level;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import study.wia.common.LoggerManager;
import study.wia.function.MultiTableToListCopyDragAndDropHandler;
import study.wia.function.TableCellRenderer;
import study.wia.function.TableToTableMoveDragAndDropHandler;
import study.wia.function.TableToTableSwapDragAndDropHandler;

/**
 *
 * @author P088454
 */
public class TableSwap extends javax.swing.JFrame {
    
    private LoggerManager loggerMgr;
    
    private DefaultTableModel table1Model;
    private DefaultTableModel table2Model;
    private DefaultTableModel table3Model;
    private DefaultTableModel table4Model;
    private DefaultTableModel table5Model;
    
    private DefaultListModel<String> list1Model = new DefaultListModel<>();
    private DefaultListModel<String> list2Model = new DefaultListModel<>();
    private DefaultListModel<String> list3Model = new DefaultListModel<>();

    /**
     * Creates new form TableSwap
     */
    public TableSwap() {
        initLoggerManager();
        initComponents();
        
        init();
    }

    private void initLoggerManager() {
//        loggerMgr = new LoggerManager(this.getClass().getName(), Level.INFO);
//        loggerMgr.getLogger().info("===> initLoggerManager()");  
    }
    
//    private void setTable(JTable table, DefaultTableModel tableModel, String[] columArray, int dataCount, int[] inActiveEditColume) {
//        tableModel = new DefaultTableModel(columArray, dataCount) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                if (inActiveEditColume != null) {
//                    for (int i = 0; i < inActiveEditColume.length; i += 1) {
//                        // edit기능 제거
//                        if (inActiveEditColume[i] == column) {
//                            return false;
//                        }
//                    }
//                    return true;
//                } else {
//                    return true;
//                }
//            }
//        };
//        // 타이틀바 색상변경
//        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
//        // 맨 왼쪽 색상변경
//        table.setDefaultRenderer(Object.class, new TableCellRenderer());
//    }
    
    private void init() {
        
        ////////////////////////////////////////////////////////////////////////////////////////////////
        //table1Model = new DefaultTableModel(new String[]{"col0", "col1", "col2"}, 0);
        //setTable(table1, table1Model, new String[]{"col0", "col1", "col2"}, 0, new int[]{0});
        table1Model = new DefaultTableModel(new String[]{"col0", "col1", "col2"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                int inActiveEditColume[] = {0};
                
                if (inActiveEditColume != null) {
                    for (int i = 0; i < inActiveEditColume.length; i += 1) {
                        // edit기능 제거
                        if (inActiveEditColume[i] == column) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return true;
                }
            }
        };
        // 타이틀바 색상변경
        //table1.getTableHeader().setBackground(Color.LIGHT_GRAY);
        // 맨 왼쪽 색상변경
        table1.setDefaultRenderer(Object.class, new TableCellRenderer());
        table1.setModel(table1Model);
        table1Model.addRow(new String[]{"왼쪽1", "table1_11", "table1_12"});
        table1Model.addRow(new String[]{"왼쪽1", "table1_21", "table1_22"});
        table1.setTransferHandler(new TableToTableSwapDragAndDropHandler(loggerMgr, "table1", new int[]{0}));
        
        table2Model = new DefaultTableModel(new String[]{"col0", "col1", "col2"}, 0);
        table2.setModel(table2Model);
        table2Model.addRow(new String[]{"왼쪽2", "table2_11", "table2_12"});
        table2Model.addRow(new String[]{"왼쪽2", "table2_21", "table2_22"});
        table2.setTransferHandler(new TableToTableSwapDragAndDropHandler(loggerMgr, "table2", null));
        
        TableToTableSwapDragAndDropHandler.allowPair("table1", "table2", true);
        
        table3Model = new DefaultTableModel(new String[]{"col0", "col1", "col2"}, 0);
        table3.setModel(table3Model);
        table3Model.addRow(new String[]{"왼쪽3", "table3_11", "table3_12"});
        table3Model.addRow(new String[]{"왼쪽3", "table3_21", "table3_22"});
        table3.setTransferHandler(new TableToTableMoveDragAndDropHandler(loggerMgr, "table3", null));
        
        table4Model = new DefaultTableModel(new String[]{"col0", "col1", "col2"}, 0);
        table4.setModel(table4Model);
        table4Model.addRow(new String[]{"왼쪽4", "table4_11", "table4_12"});
        table4Model.addRow(new String[]{"왼쪽4", "table4_21", "table4_22"});
        table4.setTransferHandler(new TableToTableMoveDragAndDropHandler(loggerMgr, "table4", null));
        
        TableToTableMoveDragAndDropHandler.allowPair("table3", "table4", true);
        
        table5Model = new DefaultTableModel(new String[]{"col0", "col1", "col2"}, 0);
        table5.setModel(table5Model);
        table5Model.addRow(new String[]{"왼쪽5", "table5_11", "table5_12"});
        table5Model.addRow(new String[]{"왼쪽5", "table5_21", "table5_22"});
        table5.setTransferHandler(new MultiTableToListCopyDragAndDropHandler(loggerMgr, "table5", new int[]{0}));
        
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////
        list1.setModel(list1Model);
        list1Model.addElement("list1_0");
        list1Model.addElement("list1_1");
        list1.setTransferHandler(new MultiTableToListCopyDragAndDropHandler(loggerMgr, "list1", null));
        
        list2.setModel(list2Model);
        list2Model.addElement("list2_0");
        list2Model.addElement("list2_1");
        
        list3.setModel(list3Model);
        list3Model.addElement("list3_0");
        list3Model.addElement("list3_1");
                
//        TableToListSwapDragAndDropHandler.allowPair("table5", "list1", true);
        
        
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        table3 = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        table4 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        table5 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        list1 = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        list2 = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        list3 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table1.setCellSelectionEnabled(true);
        table1.setDragEnabled(true);
        table1.setDropMode(javax.swing.DropMode.ON);
        table1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table1.setShowGrid(true);
        table1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table1);

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table2.setCellSelectionEnabled(true);
        table2.setDragEnabled(true);
        table2.setDropMode(javax.swing.DropMode.ON);
        table2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table2.setShowGrid(true);
        table2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(table2);

        table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table3.setCellSelectionEnabled(true);
        table3.setDragEnabled(true);
        table3.setDropMode(javax.swing.DropMode.ON);
        table3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table3.setShowGrid(true);
        table3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(table3);

        table4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table4.setCellSelectionEnabled(true);
        table4.setDragEnabled(true);
        table4.setDropMode(javax.swing.DropMode.ON);
        table4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table4.setShowGrid(true);
        table4.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(table4);

        table5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table5.setCellSelectionEnabled(true);
        table5.setDragEnabled(true);
        table5.setDropMode(javax.swing.DropMode.ON);
        table5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table5.setShowGrid(true);
        table5.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(table5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        list1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        list1.setDropMode(javax.swing.DropMode.INSERT);
        jScrollPane4.setViewportView(list1);

        list2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        list2.setDropMode(javax.swing.DropMode.INSERT);
        jScrollPane5.setViewportView(list2);

        list3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        list3.setDropMode(javax.swing.DropMode.INSERT);
        jScrollPane6.setViewportView(list3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            //logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TableSwap().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JList<String> list1;
    private javax.swing.JList<String> list2;
    private javax.swing.JList<String> list3;
    private javax.swing.JTable table1;
    private javax.swing.JTable table2;
    private javax.swing.JTable table3;
    private javax.swing.JTable table4;
    private javax.swing.JTable table5;
    // End of variables declaration//GEN-END:variables
}
