/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;
import study.netbeans.common.LoggerManager;

/**
 * JTable 간 DragAndDrop 방식으로 셀 단위 데이터 교환(SWAP)
 * - 셀 선택은 한 개만 허용
 * - 동일 테이블 내에서 교환(SWAP)
 * - 다른 테이블 간 교환(SWAP)
 * - 특정 컬럼은 SWAP 대상에서 제외 가능, 사용하지 않으면 null
 *
 * 테이블 설정:
 * - setDragEnabled(true)
 * - setDropMode(DropMode.ON)
 * - selectionMode = ListSelectionModel.SINGLE_SELECTION
 *
 * 사용 예:
 * int[] excludeCols = {0, 2, 7};, ex) new int[]{0, 1}
 * table1.setTransferHandler(new TableToTableSwapDragAndDropHandler(loggerMgr, "t1", excludeCols));
 * table2.setTransferHandler(new TableToTableSwapDragAndDropHandler(loggerMgr, "t2", excludeCols));
 *
 * TableToTableSwapDragAndDropHandler.allowPair("t1","t2", false); // 단방향 t1 -> t2
 */
public class TableToTableSwapDragAndDropHandler extends TransferHandler {

    private final LoggerManager loggerMgr;
    private final String tableKey;
    private final int[] excludeColumns; // SWAP 제외 컬럼

    private static final Set<String> allowedPairs = new HashSet<>();

    private static String pair(Object a, Object b) {
        return String.valueOf(a) + "->" + String.valueOf(b);
    }

    public static void allowPair(String fromKey, String toKey, boolean bidirectional) {
        allowedPairs.add(pair(fromKey, toKey));
        if (bidirectional) allowedPairs.add(pair(toKey, fromKey));
    }

    private static final ThreadLocal<SwapContext> TL_CTX = new ThreadLocal<>();

    private static class SwapContext {
        final JTable sourceTable;
        final int row;
        final int col;
        final Object value;
        final String fromKey;

        SwapContext(JTable table, int row, int col, Object value, String fromKey) {
            this.sourceTable = table;
            this.row = row;
            this.col = col;
            this.value = value;
            this.fromKey = fromKey;
        }
    }

    /**
     * @param loggerMgr 로거
     * @param tableKey 테이블 고유 키
     * @param excludeColumns SWAP 제외 컬럼 배열, null이면 모두 허용
     */
    public TableToTableSwapDragAndDropHandler(LoggerManager loggerMgr, String tableKey, int[] excludeColumns) {
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

        TL_CTX.set(new SwapContext(table, row, col, value, this.tableKey));
        return new StringSelection("SWAP"); // 실제 내용은 필요 없음
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) return false;
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) return false;
        if (!(support.getComponent() instanceof JTable)) return false;

        JTable target = (JTable) support.getComponent();
        JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
        int row = dl.getRow();
        int col = dl.getColumn();
        if (row < 0 || col < 0) return false;
        if (isExcludedColumn(col)) return false;

        SwapContext ctx = TL_CTX.get();
        if (ctx == null) return false;

        if (ctx.sourceTable == target) return true; // 동일 테이블 허용
        return allowedPairs.contains(pair(ctx.fromKey, this.tableKey));
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;

        try {
            JTable target = (JTable) support.getComponent();
            JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
            int tRow = dl.getRow();
            int tCol = dl.getColumn();

            if (!(target.getModel() instanceof DefaultTableModel)) return false;
            if (isExcludedColumn(tCol)) return false;

            DefaultTableModel tModel = (DefaultTableModel) target.getModel();
            SwapContext ctx = TL_CTX.get();
            if (ctx == null) return false;

            JTable source = ctx.sourceTable;
            DefaultTableModel sModel = (DefaultTableModel) source.getModel();

            // 안전 체크
            if (tRow >= tModel.getRowCount() || tCol >= tModel.getColumnCount()) return false;

            Object tValue = tModel.getValueAt(tRow, tCol);

            // SWAP
            sModel.setValueAt(tValue, ctx.row, ctx.col);
            tModel.setValueAt(ctx.value, tRow, tCol);

            // 선택 표시 갱신
            target.changeSelection(tRow, tCol, false, false);
            source.changeSelection(ctx.row, ctx.col, false, false);

            return true;

        } catch (Exception e) {
            loggerMgr.getLogger().severe(e.toString());
            return false;
        }
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        TL_CTX.remove();
    }
}

