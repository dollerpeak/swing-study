/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.function;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.MOVE;
import study.wia.common.LoggerManager;

/**
 * 리스트간 DragAndDrop방식으로 데이터 이동(MOVE) 
 * - 리스트 데이터는 ""(공란)이거나 null데이터는 없음 
 * - 동일 리스트내에서 이동(MOVE) : 결과적으로 순서가 변경됨 
 * - 다른 리스트간 이동(MOVE) : Drop 위치에 따라 순서가 변경됨 
 * - 한개 혹은 여러개의 데이터 이동 가능
 *
 * list설정 
 * - selectionMode : MULTIPLE_INTERVAL 
 * - dragEnabled : True 
 * - dropMode : INSERT
 *
 * 사용 
 * 리스트별 고유 key설정 
 * - alist.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "a")); // 리스트 key설정
 * - blist.setTransferHandler(new ListToListMoveDragAndDropHandler(loggerMgr, "b")); // 리스트 key설정 
 * 리스트별 방향성 설정 -
 * - ListToListMoveDragAndDropHandler.allowPair("1", "2", true); // 1 <-> 2 (true=양방향) 
 * - ListToListMoveDragAndDropHandler.allowPair("2", "1", false); //2 -> 1 (false=단방향)
 *
 */

public class ListToListMoveDragAndDropHandler extends TransferHandler {

    private final LoggerManager loggerMgr;
    private final String listKey; // 고유 key

    // 이동 허용 테이블
    private static final Set<String> allowedPairs = new HashSet<>();

    private static String pair(Object a, Object b) {
        return String.valueOf(a) + "->" + String.valueOf(b);
    }

    // from에서 to로 이동(MOVE), bidirectional=true면 양방향, false면 단방향
    public static void allowPair(String fromKey, String toKey, boolean bidirectional) {
        allowedPairs.add(pair(fromKey, toKey));
        if (bidirectional) {
            allowedPairs.add(pair(toKey, fromKey));
        }
    }

    // 현재 드래그의 "소스 리스트 키"를 전달하기 위한 스레드 로컬
    private static final ThreadLocal<String> TL_FROM_KEY = new ThreadLocal<>();

    // 드래그 상태
    private int[] indices = null; // 드래그된 원본 인덱스들
    private int addIndex = -1; // 동일 리스트 삽입 시작 인덱스
    private int addCount = 0; // 동일 리스트 삽입된 항목 수
    private JList<?> sourceList = null; // 드래그 시작 리스트
    private boolean inSameList = false; // 동일 리스트 드롭 여부

    public ListToListMoveDragAndDropHandler(LoggerManager loggerMgr, String listKey) {
        this.loggerMgr = loggerMgr;
        this.listKey = listKey;
    }

    public String getListKey() {
        return listKey;
    }

    @Override
    protected Transferable createTransferable(JComponent component) {
        // 드래그 시작(소스 쪽)
        JList<?> list = (JList<?>) component;
        sourceList = list;
        indices = list.getSelectedIndices();
        List<?> values = list.getSelectedValuesList();

        // 현재 드래그의 fromKey를 기록 (타깃 쪽 canImport에서 읽음)
        TL_FROM_KEY.set(this.listKey);

        StringBuilder sb = new StringBuilder();
        for (Object v : values) {
            sb.append(v.toString()).append("\n");
        }
        return new StringSelection(sb.toString());
    }

    @Override
    public int getSourceActions(JComponent component) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        
        if (!(support.getComponent() instanceof JList)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        JList<?> target = (JList<?>) support.getComponent();

        // 동일 리스트 재정렬: 항상 허용
        if (target == sourceList) {
            return true;
        }

        // 스레드 로컬에서 소스 키를 읽어 정책 판단
        String fromKey = TL_FROM_KEY.get();
        String toKey = this.listKey;

        // 혹시 동일 리스트인데 sourceList 비교가 못 잡는 경우 대비 (안전망)
        if (fromKey != null && fromKey.equals(toKey)) {
            return true;
        }

        if (fromKey == null || toKey == null) {
            return false;
        }
        
        return allowedPairs.contains(pair(fromKey, toKey));
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }
        
        try {
            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);

            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            @SuppressWarnings("unchecked")
            JList<String> target = (JList<String>) support.getComponent();
            @SuppressWarnings("unchecked")
            DefaultListModel<String> model = (DefaultListModel<String>) target.getModel();

            int index = dl.getIndex();
            if (index < 0 || index > model.getSize()) {
                index = model.getSize();
            }

            String[] lines = data.split("\n");
            int added = 0;
            for (String line : lines) {
                String item = line.trim();
                if (!item.isEmpty()) {
                    model.add(index++, item);
                    added++;
                }
            }

            inSameList = (target == sourceList);
            if (inSameList) {
                addIndex = index - added;
                addCount = added;
            } else {
                addIndex = -1;
                addCount = 0;
            }

            if (added > 0) {
                int start = inSameList ? addIndex : (index - added);
                int end = index - 1;
                target.getSelectionModel().setSelectionInterval(start, end);
            }
            return true;

        } catch (Exception e) {
            //loggerMgr.getLogger().severe(e.toString());
            loggerMgr.error(e.toString());
            return false;
        }
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        try {
            if (action == MOVE && indices != null) {
                JList<?> src = (JList<?>) source;
                @SuppressWarnings("unchecked")
                DefaultListModel<?> model = (DefaultListModel<?>) src.getModel();

                // 동일 리스트 MOVE 시 삽입으로 밀린 인덱스 보정
                if (inSameList && addCount > 0 && addIndex >= 0) {
                    for (int i = 0; i < indices.length; i++) {
                        if (indices[i] >= addIndex) {
                            indices[i] += addCount;
                        }
                    }
                }

                // 뒤에서부터 제거
                for (int i = indices.length - 1; i >= 0; i--) {
                    int idx = indices[i];
                    if (idx >= 0 && idx < model.getSize()) {
                        model.remove(idx);
                    } else {
                        //loggerMgr.getLogger().severe("remove out-of-range, idx=" + idx + ", size=" + model.getSize());
                        loggerMgr.error("remove out-of-range, idx=" + idx + ", size=" + model.getSize());
                    }
                }
            }
        } finally {
            // 상태, 컨텍스트 정리
            indices = null;
            addIndex = -1;
            addCount = 0;
            sourceList = null;
            inSameList = false;
            // 스레드 로컬 정리(중요)
            TL_FROM_KEY.remove(); 
        }
    }
}




