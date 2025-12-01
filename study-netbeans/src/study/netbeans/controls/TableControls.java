/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package study.netbeans.controls;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import study.netbeans.common.LoggerManager;
//import study.netbeans.util.TableDragAndDropSwapHandler;
import study.wia.common.CommonManager;

/**
 *
 * @author home
 */
public class TableControls extends javax.swing.JFrame {
    
    private LoggerManager loggerMgr;

    private DefaultTableModel table1Model;
    private DefaultTableModel table2Model;
    private DefaultTableModel table3Model;

    /**
     * Creates new form TableControls
     */
    public TableControls() {
        initLoggerManager();
        initComponents();
        
        //init();
        initTable();
    }

    private void initLoggerManager() {
        loggerMgr = new LoggerManager(this.getClass().getName(), Level.INFO);
        loggerMgr.getLogger().info("===> initLoggerManager()");  
    }
    
    private void initTable() {
//        table1Model = new DefaultTableModel(new String[]{"왼쪽", "컬럼타이틀", "컬럼타이틀"}, 0) {
//            // 맨 왼쪽 수정금지
//            @Override
//            public boolean isCellEditable(int row, int column) {
////                if (column == 0) {
////                    return false;
////                }
////                return true;                
//                return false;
//            }
//        };
//        // 타이틀바 색상변경
//        table1.getTableHeader().setBackground(Color.LIGHT_GRAY);
//        // 맨 왼쪽 색상변경
//        table1.setDefaultRenderer(Object.class, new TableCellRenderer());
//        table1.setTransferHandler(new TableDragAndDropSwapHandler(loggerMgr));
//        
//        table1.setModel(table1Model);
//        table1Model.addRow(new String[]{"왼쪽1", "table1_11", "table1_12"});
//        table1Model.addRow(new String[]{"왼쪽1", "table1_21", "table1_22"});
//        table1Model.addRow(new String[]{"왼쪽1", "table1_31", "table1_32"});
//                
//        table2Model = new DefaultTableModel(new String[]{"왼쪽", "컬럼타이틀", "컬럼타이틀"}, 0) {
//            // 맨 왼쪽 수정금지
//            @Override
//            public boolean isCellEditable(int row, int column) {
////                if (column == 0) {
////                    return false;
////                }
////                return true;
//                
//                return false;
//            }
//        };
//        // 타이틀바 색상변경
//        table2.getTableHeader().setBackground(Color.LIGHT_GRAY);
//        // 맨 왼쪽 색상변경
//        table2.setDefaultRenderer(Object.class, new TableCellRenderer());
//        table2.setTransferHandler(new TableDragAndDropSwapHandler(loggerMgr));
//        
//        table2.setModel(table2Model);
//        table2Model.addRow(new String[]{"왼쪽2", "table2_11", "table2_12"});
//        table2Model.addRow(new String[]{"왼쪽2", "table2_21", "table2_22"});
//        table2Model.addRow(new String[]{"왼쪽2", "table2_31", "table2_32"});
//        
//        table3Model = new DefaultTableModel(new String[]{"왼쪽", "컬럼타이틀", "컬럼타이틀"}, 0) {
//            // 맨 왼쪽 수정금지
//            @Override
//            public boolean isCellEditable(int row, int column) {
////                if (column == 0) {
////                    return false;
////                }
////                return true;
//                
//                return false;
//            }
//        };
//        // 타이틀바 색상변경
//        table3.getTableHeader().setBackground(Color.LIGHT_GRAY);
//        // 맨 왼쪽 색상변경
//        table3.setDefaultRenderer(Object.class, new TableCellRenderer());
//        table3.setTransferHandler(new TableDragAndDropSwapHandler(loggerMgr));
//        
//        table3.setModel(table3Model);
//        table3Model.addRow(new String[]{"왼쪽3", "table3_11", "table3_12"});
//        table3Model.addRow(new String[]{"왼쪽3", "table3_21", "table3_22"});
//        table3Model.addRow(new String[]{"왼쪽3", "table3_31", "table3_32"});
    }
    
//    private void init() {
//        //String[] title = {"이름", "나이", "직업"};
//        //tableModel = new DefaultTableModel(new String[] {"이름", "나이", "직업"}, 5);
//        
//        table1.setModel(tableModel);
//        
//        // 타이틀바 색상변경
//        table1.getTableHeader().setBackground(Color.LIGHT_GRAY);
//        // 맨 왼쪽 색상변경
//        table1.setDefaultRenderer(Object.class, new TableCellRenderer());
//        
//        // 데이터 추가
//        tableModel.addRow(new String[]{"ven", "Ven_int", "Ven_int"});
//        tableModel.addRow(new String[]{"duct", "", "duct"});
//        tableModel.addRow(new String[]{"door", "Door_1", "Door_1"});
//
//        //tableModel.setValueAt("AAA", 0, 0);
////        tableModel.removeRow(1); // row 1인덱스 삭제
////        tableModel.insertRow(1, new String[]{"duct", "111", "111"}); // row 1인덱스에 넣기 나머지는 밀려남
////        
////        tableModel.setRowCount(1); // 행을 강제로 조정
////        tableModel.setRowCount(2);
//        
//        loggerMgr.getLogger().info("getRowCount = " + tableModel.getRowCount());  
//        loggerMgr.getLogger().info("getColumnCount = " + tableModel.getColumnCount());  
//        loggerMgr.getLogger().info("getDataVector = " + tableModel.getDataVector()); 
//        
//        //tableModel.addColumn("추가");
//        //tableModel.addColumn("추가", new String[]{"11", "22", "333"});
//        
//    }

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
        jScrollPane3 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        table3 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();

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
        table1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table1.setDragEnabled(true);
        table1.setDropMode(javax.swing.DropMode.ON);
        table1.setName(""); // NOI18N
        table1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table1.setShowGrid(true);
        table1.getTableHeader().setReorderingAllowed(false);
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        table1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table1KeyPressed(evt);
            }
        });
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
        jScrollPane3.setViewportView(table2);

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
        jScrollPane4.setViewportView(table3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        list.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(list);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        if (evt.getClickCount() == 2) { // 더블클릭 감지
            JTable target = (JTable) evt.getSource();
            int row = target.rowAtPoint(evt.getPoint());
            int col = target.columnAtPoint(evt.getPoint());
            
            loggerMgr.getLogger().info("double click, row = " + row + ", col = " + col);

            // 첫 번째 컬럼은 편집 금지
            if (col != 0) {
                String currentValue = (String) target.getValueAt(row, col);
                // 입력 다이얼로그 표시
//            String newValue = JOptionPane.showInputDialog(
//                    this,
//                    "수정할 내용을 입력하세요.",
//                    currentValue
//            );
                //String newValue = showInputDialog(this, "수정할 내용을 입력하세요.", currentValue);
                String newValue = CommonManager.showInputDialog(this, "수정할 내용을 입력하세요.", currentValue);

                // 취소했거나 빈 문자열이면 변경하지 않음
                if (newValue != null) {
                    newValue = newValue.trim(); // 공백 제거
                    target.setValueAt(newValue, row, col);
                } else {
                    // 취소 이벤트
                }
            }
        }
    }//GEN-LAST:event_table1MouseClicked

    private void table1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            int result;
            int row = table1.getSelectedRow();
            int col = table1.getSelectedColumn();
            
            loggerMgr.getLogger().info("delete press, row = " + row + ", col = " + col);
            
           // 첫 번째 컬럼은 편집 금지
            if (col != 0) {
                result = CommonManager.showConfirmDialog(this, "삭제", "선택한 항목을 삭제하시겠습니까?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    table1.setValueAt("", row, col);
                }
            }
        }
    }//GEN-LAST:event_table1KeyPressed

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
            java.util.logging.Logger.getLogger(TableControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TableControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TableControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableControls().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JList<String> list;
    private javax.swing.JTable table1;
    private javax.swing.JTable table2;
    private javax.swing.JTable table3;
    // End of variables declaration//GEN-END:variables
}
