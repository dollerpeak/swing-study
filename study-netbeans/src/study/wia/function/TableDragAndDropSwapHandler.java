/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.function;

import study.wia.common.LoggerManager;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

/**
 * Table 드래그 앤 드롭 기능
 */
public class TableDragAndDropSwapHandler extends TransferHandler {

    private static LoggerManager loggerMgr;

    public TableDragAndDropSwapHandler(LoggerManager loggerMgr) {
        loggerMgr = loggerMgr;
    }

    @Override
    public int getSourceActions(JComponent component) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent component) {
        JTable table = (JTable) component;
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();

        // 첫번째 컬럼 불가
        if (row < 0 || col <= 0) {
            return null; 
        }
        Object value = table.getValueAt(row, col);
        return new CellData(table, row, col, value);
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

        JTable targetTable = (JTable) support.getComponent();
        JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
        int targetRow = dl.getRow();
        int targetCol = dl.getColumn();

        // 첫번째 컬럼 불가
        if (targetRow < 0 || targetCol <= 0) {
            return false;
        }
        try {
            CellData data = (CellData) support.getTransferable()
                    .getTransferData(CellData.CELL_FLAVOR);

            Object targetValue = targetTable.getValueAt(targetRow, targetCol);

            // 교환
            data.table.setValueAt(targetValue, data.row, data.col);
            targetTable.setValueAt(data.value, targetRow, targetCol);

            return true;
        //} catch (UnsupportedFlavorException | IOException e) {
        } catch (Exception e) {
            loggerMgr.error(e.getMessage());
            return false;
        }
    }

    // Transferable 내부에 담을 클래스
    static class CellData implements Transferable {

        static DataFlavor CELL_FLAVOR;

        static {
            try {
                // JVM 내부에서만 사용할 수 있는 객체 타입 CellData 전용 DataFlavor
                CELL_FLAVOR = new DataFlavor(
                        DataFlavor.javaJVMLocalObjectMimeType + ";class=" + CellData.class.getName());
            //} catch (ClassNotFoundException e) {
            } catch (Exception e) {
                //e.printStackTrace();
                loggerMgr.error(e.getMessage());
            }
        }

        JTable table;
        int row, col;
        Object value;

        public CellData(JTable table, int row, int col, Object value) {
            this.table = table;
            this.row = row;
            this.col = col;
            this.value = value;
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
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return this;
        }
    }

}
