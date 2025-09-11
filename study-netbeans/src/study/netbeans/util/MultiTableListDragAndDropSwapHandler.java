/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import study.netbeans.common.CommonManager;
import study.netbeans.common.logger.LoggerManager;

public class MultiTableListDragAndDropSwapHandler extends TransferHandler {

    // private LoggerManager loggerMgr;
    private static LoggerManager loggerMgr;

    public MultiTableListDragAndDropSwapHandler(LoggerManager loggerMgr) {
        this.loggerMgr = loggerMgr;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent component) {
        if (component instanceof JTable) {
            JTable table = (JTable) component;
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            // 첫 컬럼 제외, row=0 허용
            if (row < 0 || col <= 0) {
                return null;
            }

            Object value = table.getValueAt(row, col);
            return new CellData(value, row, col, table);
        }

        if (component instanceof JList) {
            @SuppressWarnings("unchecked")
            JList<Object> list = (JList<Object>) component;
            int idx = list.getSelectedIndex();

            if (idx < 0) {
                return null;
            }

            Object value = list.getModel().getElementAt(idx);
            return new CellData(value, idx, -1, list);
        }

        return null;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(CellData.CELL_FLAVOR);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            Object transfer = support.getTransferable().getTransferData(CellData.CELL_FLAVOR);
            if (!(transfer instanceof CellData)) {
                return false;
            }
            CellData data = (CellData) transfer;

            JComponent targetComp = (JComponent) support.getComponent();

            // ----------------- TARGET: JTable -----------------
            if (targetComp instanceof JTable) {
                JTable targetTable = (JTable) targetComp;
                JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
                if (dl == null) {
                    return false;
                }

                int targetRow = dl.getRow();
                int targetCol = dl.getColumn();

                if (targetRow < 0 || targetCol <= 0) {
                    return false;
                }

                Object targetValue = targetTable.getValueAt(targetRow, targetCol);

                // 소스가 JTable
                if (data.owner instanceof JTable) {
                    JTable srcTable = (JTable) data.owner;
                    if (srcTable == targetTable && data.row == targetRow && data.col == targetCol) {
                        return false;
                    }

                    // 테이블 ↔ 테이블: null/빈 허용
                    targetTable.setValueAt(data.value, targetRow, targetCol);
                    srcTable.setValueAt(targetValue, data.row, data.col);
                    return true;
                }

                // 소스가 JList → JTable
                if (data.owner instanceof JList) {
                    @SuppressWarnings("unchecked")
                    JList<Object> srcList = (JList<Object>) data.owner;
                    DefaultListModel<Object> srcModel = (DefaultListModel<Object>) srcList.getModel();
                    Object srcVal = srcModel.get(data.row);

                    // 리스트 → 테이블: null/빈 체크 (소스 또는 대상)
                    if (srcVal == null || "".equals(srcVal.toString())
                            || targetValue == null || "".equals(targetValue.toString())) {
                        CommonManager.showMessageDialog(targetTable, "알림", "리스트에는 null 또는 빈 문자열을 넣을 수 없습니다.", JOptionPane.WARNING_MESSAGE);
                        //JOptionPane.showMessageDialog(targetTable,
                        //        "리스트에는 null 또는 빈 문자열을 넣을 수 없습니다.",
                        //        "알림",
                        //        JOptionPane.WARNING_MESSAGE);
                        return false;
                    }

                    targetTable.setValueAt(srcVal, targetRow, targetCol);
                    srcModel.set(data.row, targetValue);
                    return true;
                }

                return false;
            }

            // ----------------- TARGET: JList -----------------
            if (targetComp instanceof JList) {
                @SuppressWarnings("unchecked")
                JList<Object> targetList = (JList<Object>) targetComp;
                DefaultListModel<Object> targetModel = (DefaultListModel<Object>) targetList.getModel();
                JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                if (dl == null) {
                    return false;
                }
                int targetIndex = dl.getIndex();
                if (targetIndex < 0 || targetIndex >= targetModel.getSize()) {
                    return false;
                }

                Object targetValue = targetModel.get(targetIndex);

                // 소스가 JList → JList
                if (data.owner instanceof JList) {
                    @SuppressWarnings("unchecked")
                    JList<Object> srcList = (JList<Object>) data.owner;
                    DefaultListModel<Object> srcModel = (DefaultListModel<Object>) srcList.getModel();
                    Object srcVal = srcModel.get(data.row);

                    // 리스트 ↔ 리스트: null/빈 체크
                    if (srcVal == null || "".equals(srcVal.toString())
                            || targetValue == null || "".equals(targetValue.toString())) {
                        CommonManager.showMessageDialog(targetList, "알림", "리스트에는 null 또는 빈 문자열을 넣을 수 없습니다.", JOptionPane.WARNING_MESSAGE);
                        //JOptionPane.showMessageDialog(targetList,
                        //        "리스트에는 null 또는 빈 문자열을 넣을 수 없습니다.",
                        //        "알림",
                        //        JOptionPane.WARNING_MESSAGE);
                        return false;
                    }

                    targetModel.set(targetIndex, srcVal);
                    srcModel.set(data.row, targetValue);
                    return true;
                }

                // 소스가 JTable → JList
                if (data.owner instanceof JTable) {
                    JTable srcTable = (JTable) data.owner;
                    Object srcVal = srcTable.getValueAt(data.row, data.col);

                    // 테이블 → 리스트: null/빈 체크 (소스 또는 대상)
                    if (srcVal == null || "".equals(srcVal.toString())
                            || targetValue == null || "".equals(targetValue.toString())) {
                        CommonManager.showMessageDialog(targetList, "알림", "리스트에는 null 또는 빈 문자열을 넣을 수 없습니다.", JOptionPane.WARNING_MESSAGE);
                        //JOptionPane.showMessageDialog(targetList,
                        //        "리스트에는 null 또는 빈 문자열을 넣을 수 없습니다.",
                        //        "알림",
                        //        JOptionPane.WARNING_MESSAGE);
                        return false;
                    }

                    targetModel.set(targetIndex, srcVal);
                    srcTable.setValueAt(targetValue, data.row, data.col);
                    return true;
                }

                return false;
            }

            //} catch (UnsupportedFlavorException | IOException e) {
        } catch (Exception e) {
            loggerMgr.getLogger().severe(e.getMessage());
            return false;
        }

        return false;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        // 상태 유지 필요 없음
    }

    // ----------------- CellData -----------------
    public static class CellData implements Transferable {

        static DataFlavor CELL_FLAVOR;

        static {
            try {
                // JVM 내부에서만 사용할 수 있는 객체 타입 CellData 전용 DataFlavor
                CELL_FLAVOR = new DataFlavor(
                        DataFlavor.javaJVMLocalObjectMimeType + ";class=" + CellData.class.getName()
                );
            //} catch (ClassNotFoundException e) {
            } catch (Exception e) {
                //throw new RuntimeException("CellData class not found for DataFlavor", e);
                loggerMgr.getLogger().severe(e.getMessage());
            }
        }

        Object value;
        int row;
        int col;
        JComponent owner;

        public CellData(Object value, int row, int col, JComponent owner) {
            this.value = value;
            this.row = row;
            this.col = col;
            this.owner = owner;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{CELL_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return CELL_FLAVOR.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return this;
        }
    }

}
