/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.function;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.MOVE;
import javax.swing.table.DefaultTableModel;
import study.wia.common.LoggerManager;

/**
 * JTable 간 DragAndDrop 방식으로 셀 단위 데이터 이동(MOVE)
 * - 셀 선택은 한 개만 허용
 * - 동일 테이블 내에서 이동
 * - 다른 테이블 간 이동
 * - 특정 컬럼은 MOVE 대상에서 제외 가능
 *
 * 테이블 설정:
 * - setDragEnabled(true)
 * - setDropMode(DropMode.ON)
 * - selectionMode = ListSelectionModel.SINGLE_SELECTION
 *
 * 사용 예:
 * int[] excludeCols = {0, 2, 7};, ex) new int[]{0, 1}
 * table1.setTransferHandler(new TableToTableMoveDragAndDropHandler(loggerMgr, "t1", excludeCols));
 * table2.setTransferHandler(new TableToTableMoveDragAndDropHandler(loggerMgr, "t2", excludeCols));
 *
 * TableToTableMoveDragAndDropHandler.allowPair("t1","t2", false); // 단방향 t1 -> t2
 */
public class TableToTableMoveDragAndDropHandler extends TransferHandler {

    private final LoggerManager loggerMgr;
    private final String tableKey;
    private final int[] excludeColumns;

    private static final Set<String> allowedPairs = new HashSet<>();

    private static String pair(Object a, Object b) {
        return String.valueOf(a) + "->" + String.valueOf(b);
    }

    public static void allowPair(String fromKey, String toKey, boolean bidirectional) {
        allowedPairs.add(pair(fromKey, toKey));
        if (bidirectional) allowedPairs.add(pair(toKey, fromKey));
    }

    private static final ThreadLocal<MoveContext> TL_CTX = new ThreadLocal<>();

    private static class MoveContext {
        final JTable sourceTable;
        final int row;
        final int col;
        final Object value;
        final String fromKey;

        MoveContext(JTable table, int row, int col, Object value, String fromKey) {
            this.sourceTable = table;
            this.row = row;
            this.col = col;
            this.value = value;
            this.fromKey = fromKey;
        }
    }

    public TableToTableMoveDragAndDropHandler(LoggerManager loggerMgr, String tableKey, int[] excludeColumns) {
        this.loggerMgr = loggerMgr;
        this.tableKey = tableKey;
        this.excludeColumns = excludeColumns != null ? excludeColumns.clone() : new int[0];
    }

    public String getTableKey() {
        return tableKey;
    }

    private boolean isExcludedColumn(int col) {
        return Arrays.stream(excludeColumns).anyMatch(c -> c == col);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent component) {
        JTable table = (JTable) component;
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        if (row < 0 || col < 0) return null;
        if (isExcludedColumn(col)) return null;

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object value = model.getValueAt(row, col);
        if (value == null || "".equals(value)) return null;

        TL_CTX.set(new MoveContext(table, row, col, value, this.tableKey));
        return new StringSelection("MOVE");
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) return false;
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) return false;
        if (!(support.getComponent() instanceof JTable)) return false;

        JTable target = (JTable) support.getComponent();
        JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
        int row = dl.getRow();
        int col = dl.getColumn();
        if (row < 0 || col < 0) return false;
        if (isExcludedColumn(col)) return false;

        MoveContext ctx = TL_CTX.get();
        if (ctx == null) return false;

        if (ctx.sourceTable == target) return true; // 동일 테이블 허용
        return allowedPairs.contains(pair(ctx.fromKey, this.tableKey));
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) return false;

        try {
            JTable target = (JTable) support.getComponent();
            JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
            int tRow = dl.getRow();
            int tCol = dl.getColumn();

            if (!(target.getModel() instanceof DefaultTableModel)) return false;
            if (isExcludedColumn(tCol)) return false;

            DefaultTableModel tModel = (DefaultTableModel) target.getModel();
            MoveContext ctx = TL_CTX.get();
            if (ctx == null) return false;

            JTable source = ctx.sourceTable;
            DefaultTableModel sModel = (DefaultTableModel) source.getModel();

            // 동일 테이블 MOVE: 선택된 셀을 Drop 위치로 이동
            if (source == target) {
                Object temp = sModel.getValueAt(ctx.row, ctx.col);
                sModel.setValueAt(sModel.getValueAt(tRow, tCol), ctx.row, ctx.col);
                sModel.setValueAt(temp, tRow, tCol);
            } else {
                // 다른 테이블 MOVE: 대상 셀과 교환
                Object tValue = tModel.getValueAt(tRow, tCol);
                tModel.setValueAt(ctx.value, tRow, tCol);
                sModel.setValueAt(null, ctx.row, ctx.col); // 소스 값 제거
            }

            // 선택 표시 갱신
            target.changeSelection(tRow, tCol, false, false);
            source.changeSelection(ctx.row, ctx.col, false, false);

            return true;

        } catch (Exception e) {
            //loggerMgr.getLogger().severe(e.toString());
            loggerMgr.error(e.toString());
            return false;
        }
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        TL_CTX.remove();
    }
}


