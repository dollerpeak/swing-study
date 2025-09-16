/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
import study.netbeans.common.logger.LoggerManager;

/**
 * JList 간/내부 항목 SWAP 전용 TransferHandler
 * - 단일 선택(SINGLE_SELECTION) 전제
 * - DropMode.ON 전제 (항목 위로 드롭하여 맞교환)
 * - 같은 리스트: 선택 항목 ↔ 드롭 대상 항목 스왑(순서 변경)
 * - 다른 리스트: 정책(allowPair) 충족 시 소스 항목 ↔ 타깃 항목 스왑
 * - 문자열(stringFlavor) 기반으로 간단 구현
 */
public class ListDragAndDropSwapHandler extends TransferHandler {

    private final LoggerManager loggerMgr;
    private final String listKey; // 이 핸들러가 붙은 리스트의 키

    // === 전역 방향성 정책 (이전 MOVE와 동일한 사용법) ===
    private static final Set<String> allowedPairs = new HashSet<>();
    private static String pair(Object a, Object b){ return String.valueOf(a) + "->" + String.valueOf(b); }

    /** bidirectional=true 이면 양방향( from↔to ), false면 단방향( from→to ) */
    public static void allowPair(String fromKey, String toKey, boolean bidirectional) {
        allowedPairs.add(pair(fromKey, toKey));
        if (bidirectional) allowedPairs.add(pair(toKey, fromKey));
    }

    // 드래그-소스 컨텍스트를 타깃에서 읽기 위한 ThreadLocal (NPE 예방)
    private static final ThreadLocal<SwapContext> TL_CTX = new ThreadLocal<>();

    private static class SwapContext {
        final JList<String> sourceList;
        final int sourceIndex;
        final String sourceValue;
        final String fromKey;
        SwapContext(JList<String> list, int idx, String value, String fromKey) {
            this.sourceList = list; this.sourceIndex = idx; this.sourceValue = value; this.fromKey = fromKey;
        }
    }

    public ListDragAndDropSwapHandler(LoggerManager loggerMgr, String listKey) {
        this.loggerMgr = loggerMgr;
        this.listKey = listKey;
    }

    public String getListKey() { return listKey; }

    @Override
    public int getSourceActions(JComponent c) {
        // 표시상 MOVE면 충분 (실제 remove/add 없음; set으로 스왑)
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        @SuppressWarnings("unchecked")
        JList<String> list = (JList<String>) c;

        int idx = list.getSelectedIndex();
        if (idx < 0) return null; // 단일 선택 전제
        String value = list.getModel().getElementAt(idx);
        if (value == null || value.isEmpty()) return null; // 안전망

        // 소스 컨텍스트 기록(타깃 canImport/importData에서 사용)
        TL_CTX.set(new SwapContext(list, idx, value, this.listKey));

        return new StringSelection(value);
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) return false;
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) return false;
        if (!(support.getComponent() instanceof JList)) return false;

        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        int tIndex = dl.getIndex();
        if (tIndex < 0) return false; // ON 모드에서 유효한 타깃 인덱스 필요

        @SuppressWarnings("unchecked")
        JList<String> target = (JList<String>) support.getComponent();
        if (tIndex >= target.getModel().getSize()) return false; // ON → 0..size-1

        SwapContext ctx = TL_CTX.get();
        if (ctx == null) return false; // 우리 핸들러에서 시작하지 않은 DnD

        // 같은 리스트: 언제나 허용(순서 변경)
        if (ctx.sourceList == target) return true;

        // 다른 리스트: 방향성 정책 검사
        String fromKey = ctx.fromKey;
        String toKey   = this.listKey;
        if (fromKey == null || toKey == null) return false;
        return allowedPairs.contains(pair(fromKey, toKey));
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;

        try {
            @SuppressWarnings("unchecked")
            JList<String> target = (JList<String>) support.getComponent();
            if (!(target.getModel() instanceof DefaultListModel)) return false;

            DefaultListModel<String> tModel = (DefaultListModel<String>) target.getModel();
            int tIndex = ((JList.DropLocation) support.getDropLocation()).getIndex();
            String tValue = tModel.getElementAt(tIndex);

            SwapContext ctx = TL_CTX.get();
            if (ctx == null) return false;

            @SuppressWarnings("unchecked")
            JList<String> source = ctx.sourceList;
            if (!(source.getModel() instanceof DefaultListModel)) return false;

            DefaultListModel<String> sModel = (DefaultListModel<String>) source.getModel();
            int sIndex = ctx.sourceIndex;
            String sValue = ctx.sourceValue;

            if (source == target) {
                // 같은 리스트: 자기 자신 위면 변화 없음
                if (sIndex == tIndex) return true;
                // 같은 모델 내 스왑
                tModel.set(tIndex, sValue);
                sModel.set(sIndex, tValue);
                target.setSelectedIndex(tIndex);
                target.ensureIndexIsVisible(tIndex);
            } else {
                // 서로 다른 리스트: 정책 충족 가정 하에 맞교환
                tModel.set(tIndex, sValue);
                sModel.set(sIndex, tValue);

                target.setSelectedIndex(tIndex);
                target.ensureIndexIsVisible(tIndex);
                source.setSelectedIndex(sIndex);
                source.ensureIndexIsVisible(sIndex);
            }
            return true;

        } catch (Exception e) {
            //Logger log = loggerMgr.getLogger();
            //log.severe(e.toString());
            return false;
        }
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        // 스왑은 remove/add가 없으므로 정리만
        TL_CTX.remove();
    }
}
