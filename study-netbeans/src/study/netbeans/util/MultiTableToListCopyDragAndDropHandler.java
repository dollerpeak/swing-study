/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;
import study.netbeans.common.LoggerManager;
import study.wia.common.CommonManager;

/**
 * MultiTableToListCopyDragAndDropHandler
 *
 * JTable -> JList 단일 셀 복사(COPY) 전용 핸들러
 *
 * 특징:
 * - 오직 JTable에서 JList로의 복사만 가능
 * - 리스트 → 테이블 복사 불가
 * - 특정 컬럼(excludeColumns)에서 드래그 불가
 *   → 기능은 막되, 팝업은 표시하지 않음
 * - JList에 동일 데이터가 존재할 경우만 팝업 표시
 * - Drop 위치에 따라 리스트 중간에도 삽입 가능
 *   - 항목 사이 → 해당 위치에 삽입
 *   - 빈 공간이나 항목 외부 → 맨 끝에 추가
 * - JList 모델은 DefaultListModel이어야 함
 *
 * 설정 예시:
 * int[] excludeCols = new int[]{0, 2, 7};
 * table1.setDragEnabled(true);
 * table1.setDropMode(DropMode.INSERT); // 중요: INSERT나 ON_OR_INSERT로 설정해야 항목 사이 삽입 가능
 * table1.setTransferHandler(new MultiTableToListCopyDragAndDropHandler(loggerMgr, "t1", excludeCols));
 *
 * list.setDragEnabled(true);
 * list.setDropMode(DropMode.INSERT); // 항목 사이 삽입이 필요하면 반드시 설정
 * list.setTransferHandler(new MultiTableToListCopyDragAndDropHandler(loggerMgr, "l1"));
 *
 * 주의사항:
 * - excludeColumns에 지정된 컬럼은 드래그가 시작되지 않음
 * - 리스트 → 테이블 드래그는 항상 무시됨
 * - 리스트에 중복된 데이터가 있을 경우만 팝업 표시
 * - DropMode가 INSERT/ON_OR_INSERT가 아니면 항목 사이 삽입이 작동하지 않음
 */
public class MultiTableToListCopyDragAndDropHandler extends TransferHandler {

    private final LoggerManager loggerMgr;
    private final String componentKey;
    private final int[] excludeColumns;

    private static final ThreadLocal<CopyContext> TL_CTX = new ThreadLocal<>();

    private static class CopyContext {
        final JComponent sourceComp;
        final Object value;
        final String fromKey;

        CopyContext(JComponent comp, Object value, String fromKey) {
            this.sourceComp = comp;
            this.value = value;
            this.fromKey = fromKey;
        }
    }

    public MultiTableToListCopyDragAndDropHandler(LoggerManager loggerMgr, String componentKey, int[] excludeColumns) {
        this.loggerMgr = loggerMgr;
        this.componentKey = componentKey;
        this.excludeColumns = (excludeColumns != null) ? excludeColumns.clone() : new int[0];
    }

    public MultiTableToListCopyDragAndDropHandler(LoggerManager loggerMgr, String componentKey) {
        this(loggerMgr, componentKey, null);
    }

    private boolean isExcludedColumn(int col) {
        return excludeColumns != null && Arrays.stream(excludeColumns).anyMatch(x -> x == col);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent component) {
        if (!(component instanceof JTable)) return null;

        JTable table = (JTable) component;
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        if (row < 0 || col < 0) return null;

        if (isExcludedColumn(col)) return null;

        if (!(table.getModel() instanceof DefaultTableModel)) return null;

        Object value = ((DefaultTableModel) table.getModel()).getValueAt(row, col);
        if (value == null) return null;

        TL_CTX.set(new CopyContext(table, value, this.componentKey));
        return new StringSelection(value.toString());
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!(support.getComponent() instanceof JList)) return false;
        if (!support.isDrop()) return false;
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!(support.getComponent() instanceof JList)) return false;

        JList<?> targetList = (JList<?>) support.getComponent();
        if (!(targetList.getModel() instanceof DefaultListModel)) return false;
        DefaultListModel<Object> listModel = (DefaultListModel<Object>) targetList.getModel();

        CopyContext ctx = TL_CTX.get();
        if (ctx == null || !(ctx.sourceComp instanceof JTable)) return false;

        Object value = ctx.value;
        if (value == null) return false;

        // 리스트에 동일 데이터 존재 시 팝업 표시
        for (int i = 0; i < listModel.getSize(); i++) {
            if (value.equals(listModel.getElementAt(i))) {
                //JOptionPane.showMessageDialog(targetList,
                //        "동일한 데이터가 이미 존재합니다:\n" + value.toString(),
                //        "복사 취소 - 중복",
                //        JOptionPane.INFORMATION_MESSAGE);
                CommonManager.showMessageDialog(targetList, "알림", "동일한 데이터가 이미 존재합니다.", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        // 드롭 위치 처리: 항목 사이 삽입 또는 맨 끝 추가
        int index = listModel.getSize(); // 기본 맨 끝
        if (support.getDropLocation() != null) {
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            if (dl.isInsert()) {
                index = Math.min(dl.getIndex(), listModel.getSize());
            }
        }

        listModel.add(index, value);
        targetList.setSelectedIndex(index);
        targetList.ensureIndexIsVisible(index);

        TL_CTX.remove();
        return true;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        TL_CTX.remove();
    }
}







