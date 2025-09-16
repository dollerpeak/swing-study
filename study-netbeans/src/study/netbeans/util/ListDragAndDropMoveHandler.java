/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
import study.netbeans.common.logger.LoggerManager;

/**
 * List 드래그 앤 드롭 기능
 */
//public class ListDragAndDropMoveHandler extends TransferHandler {
//
//    private LoggerManager loggerMgr;
//
//    // 드래그 상태 저장
//    private int[] indices = null; // 드래그된 원본 인덱스들
//    private int addIndex = -1; // 동일 리스트에 삽입이 일어난 시작 인덱스 (동일 리스트인 경우에만 의미)
//    private int addCount = 0; // 동일 리스트에 삽입된 항목 수
//    private JList<?> sourceList = null; // 드래그 시작한 리스트 참조
//    private boolean inSameList = false; // 드래그한 리스트와 드롭 타깃이 동일 리스트인지 여부
//
//    public ListDragAndDropMoveHandler(LoggerManager loggerMgr) {
//        this.loggerMgr = loggerMgr;
//    }
//
//    @Override
//    protected Transferable createTransferable(JComponent component) {
//        // 드래그 시작
//        JList<?> list = (JList<?>) component;
//        sourceList = list; // exportDone에서 동일 리스트 판별에 사용
//        indices = list.getSelectedIndices();
//        List<?> values = list.getSelectedValuesList();
//
//        StringBuilder sb = new StringBuilder();
//        for (Object v : values) {
//            sb.append(v.toString()).append("\n");
//        }
//        
//        // 문자열 기반, StringBuilder 간단하고 호환성 좋음
//        return new StringSelection(sb.toString());
//    }
//
//    @Override
//    public int getSourceActions(JComponent component) {
//        return MOVE;
//    }
//
//    @Override
//    public boolean canImport(TransferSupport support) {
//        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
//    }
//
//    @Override
//    public boolean importData(TransferSupport support) {
//        // 드롭 처리
//        if (!canImport(support)) {
//            return false;
//        }
//
//        try {
//            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
//
//            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
//            @SuppressWarnings("unchecked")
//            JList<String> target = (JList<String>) support.getComponent();
//            DefaultListModel<String> model = (DefaultListModel<String>) target.getModel();
//
//            int index = dl.getIndex();
//            //boolean insert = dl.isInsert();
//
//            // 인덱스 범위 체크
//            if (index < 0 || index > model.getSize()) {
//                index = model.getSize();
//            }
//
//            // 삽입
//            String[] lines = data.split("\n");
//            int added = 0;
//            for (String line : lines) {
//                String item = line.trim();
//                if (!item.isEmpty()) {
//                    model.add(index++, item);
//                    added++;
//                }
//            }
//
//            // 동일 리스트 내 MOVE인지 여부 판단
//            inSameList = (target == sourceList);
//
//            // 동일 리스트인 경우에만 addIndex/addCount를 기록하여 exportDone에서 보정에 사용
//            if (inSameList) {
//                addIndex = index - added; // 삽입이 실제로 시작된 위치
//                addCount = added;
//            } else {
//                addIndex = -1;
//                addCount = 0;
//            }
//
//            return true;
//        } catch (Exception e) {
//            loggerMgr.getLogger().severe(e.toString());
//            return false;
//        }
//    }
//
//    @Override
//    protected void exportDone(JComponent source, Transferable data, int action) {
//        // 드래그 출발지에서 원본 항목 제거
//        try {
//            if (action == MOVE && indices != null) {
//                JList<?> src = (JList<?>) source;
//                DefaultListModel<?> model = (DefaultListModel<?>) src.getModel();
//
//                // 동일 리스트로 MOVE한 경우에만, 삽입으로 인해 원본 인덱스가 밀린 부분을 보정
//                if (inSameList && addCount > 0 && addIndex >= 0) {
//                    for (int i = 0; i < indices.length; i++) {
//                        if (indices[i] >= addIndex) {
//                            indices[i] += addCount;
//                        }
//                    }
//                }
//
//                // 리스트는 제거시 인덱스 문제로 뒤에서부터 제거
//                for (int i = indices.length - 1; i >= 0; i--) {
//                    int idx = indices[i];
//                    // 범위 체크
//                    if (idx >= 0 && idx < model.getSize()) {
//                        model.remove(idx);
//                    } else {
//                        // 범위를 벗어나면 로그만
//                        loggerMgr.getLogger().info("remove out-of-range, idx = " + idx + ", modelSize = " + model.getSize());
//                    }
//                }
//            }
//        } finally {
//            // 상태 초기화 (다음 DnD를 위해)
//            indices = null;
//            addIndex = -1;
//            addCount = 0;
//            sourceList = null;
//            inSameList = false;
//        }
//    }
//}

public class ListDragAndDropMoveHandler extends TransferHandler {

    private final LoggerManager loggerMgr;
    private final String listKey; // 이 핸들러가 붙은 리스트의 키

    // 전역(클래스 단위) 이동 허용 테이블
    private static final Set<String> allowedPairs = new HashSet<>();
    private static String pair(Object a, Object b) { return String.valueOf(a) + "->" + String.valueOf(b); }

    /** 쌍 등록: bidirectional=true면 양방향, false면 단방향(from->to)만 */
    public static void allowPair(String fromKey, String toKey, boolean bidirectional) {
        allowedPairs.add(pair(fromKey, toKey));
        if (bidirectional) {
            allowedPairs.add(pair(toKey, fromKey));
        }
    }

    // 현재 드래그의 "소스 리스트 키"를 전달하기 위한 스레드 로컬
    private static final ThreadLocal<String> TL_FROM_KEY = new ThreadLocal<>();

    // 드래그 상태
    private int[] indices = null;      // 드래그된 원본 인덱스들
    private int addIndex = -1;         // 동일 리스트 삽입 시작 인덱스
    private int addCount = 0;          // 동일 리스트 삽입된 항목 수
    private JList<?> sourceList = null;// 드래그 시작 리스트
    private boolean inSameList = false;// 동일 리스트 드롭 여부

    public ListDragAndDropMoveHandler(LoggerManager loggerMgr, String listKey) {
        this.loggerMgr = loggerMgr;
        this.listKey = listKey;
    }

    public String getListKey() { return listKey; }

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
        for (Object v : values) sb.append(v.toString()).append("\n");
        return new StringSelection(sb.toString());
    }

    @Override
    public int getSourceActions(JComponent component) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) return false;
        if (!(support.getComponent() instanceof JList)) return false;

        @SuppressWarnings("unchecked")
        JList<?> target = (JList<?>) support.getComponent();

        // 동일 리스트 재정렬: 항상 허용
        if (target == sourceList) return true;

        // 스레드 로컬에서 소스 키를 읽어 정책 판단
        String fromKey = TL_FROM_KEY.get();
        String toKey   = this.listKey;

        // 혹시 동일 리스트인데 sourceList 비교가 못 잡는 경우 대비 (안전망)
        if (fromKey != null && fromKey.equals(toKey)) return true;

        if (fromKey == null || toKey == null) return false;
        return allowedPairs.contains(pair(fromKey, toKey));
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;
        try {
            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);

            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            @SuppressWarnings("unchecked")
            JList<String> target = (JList<String>) support.getComponent();
            @SuppressWarnings("unchecked")
            DefaultListModel<String> model = (DefaultListModel<String>) target.getModel();

            int index = dl.getIndex();
            if (index < 0 || index > model.getSize()) index = model.getSize();

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
                int end   = index - 1;
                target.getSelectionModel().setSelectionInterval(start, end);
            }
            return true;

        } catch (Exception e) {
            loggerMgr.getLogger().severe(e.toString());
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
                        if (indices[i] >= addIndex) indices[i] += addCount;
                    }
                }

                // 뒤에서부터 제거
                for (int i = indices.length - 1; i >= 0; i--) {
                    int idx = indices[i];
                    if (idx >= 0 && idx < model.getSize()) {
                        model.remove(idx);
                    } else {
                        loggerMgr.getLogger().info("remove out-of-range, idx=" + idx + ", size=" + model.getSize());
                    }
                }
            }
        } finally {
            // 상태/컨텍스트 정리
            indices = null;
            addIndex = -1;
            addCount = 0;
            sourceList = null;
            inSameList = false;
            TL_FROM_KEY.remove(); // 스레드 로컬 정리(중요)
        }
    }
}


//public class ListDragAndDropMoveHandler extends TransferHandler {
//
//    private LoggerManager loggerMgr;
//
//    // 리스트 식별자 (생성자에서 부여)
//    private String listId;
//
//    // 허용된 이동 쌍 (from -> set of to)
//    private static Map<String, Set<String>> allowedPairs = new HashMap<>();
//
//    // 드래그 상태 저장
//    private int[] indices = null;
//    private JList<?> sourceList = null;
//
//    // 생성자: 리스트별 id 부여
//    public ListDragAndDropMoveHandler(LoggerManager loggerMgr, String listId) {
//        this.loggerMgr = loggerMgr;
//        this.listId = listId;
//    }
//
//    // 허용 쌍 등록 (bidirectional = true면 양방향, false면 단방향)
//    public static void allowPair(String from, String to, boolean bidirectional) {
//        allowedPairs.computeIfAbsent(from, k -> new HashSet<>()).add(to);
//        if (bidirectional) {
//            allowedPairs.computeIfAbsent(to, k -> new HashSet<>()).add(from);
//        }
//    }
//
//    @Override
//    protected Transferable createTransferable(JComponent c) {
//        JList<?> list = (JList<?>) c;
//        sourceList = list;
//        indices = list.getSelectedIndices();
//
//        StringBuilder sb = new StringBuilder();
//        for (Object v : list.getSelectedValuesList()) {
//            sb.append(v.toString()).append("\n");
//        }
//
//        return new StringSelection(sb.toString());
//    }
//
//    @Override
//    public int getSourceActions(JComponent c) {
//        return MOVE;
//    }
//
//    @Override
//    public boolean canImport(TransferSupport support) {
//        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) return false;
//        JList<?> targetList = (JList<?>) support.getComponent();
//
//        // 자기 자신 리스트는 항상 허용
//        if (targetList == sourceList) return true;
//
//        // 다른 리스트 MOVE 허용 여부 확인
//        Set<String> allowed = allowedPairs.getOrDefault(listId, Collections.emptySet());
//        return allowed.contains(getListId(targetList));
//    }
//
//    // 리스트 id 확인 (생성자에서 부여한 id 사용)
//    private String getListId(JList<?> list) {
//        TransferHandler th = list.getTransferHandler();
//        if (th instanceof ListDragAndDropMoveHandler) {
//            return ((ListDragAndDropMoveHandler) th).listId;
//        }
//        return "";
//    }
//
//    @Override
//    public boolean importData(TransferSupport support) {
//        if (!canImport(support)) return false;
//
//        try {
//            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
//            String[] lines = data.split("\n");
//
//            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
//            @SuppressWarnings("unchecked")
//            JList<String> target = (JList<String>) support.getComponent();
//            DefaultListModel<String> targetModel = (DefaultListModel<String>) target.getModel();
//
//            int index = dl.getIndex();
//            if (index < 0 || index > targetModel.getSize()) index = targetModel.getSize();
//
//            if (target == sourceList) {
//                // 자기 자신 MOVE: 순서 변경
//                List<String> temp = new ArrayList<>();
//                for (String line : lines) temp.add(line);
//
//                Arrays.sort(indices);
//                for (int i = indices.length - 1; i >= 0; i--) {
//                    targetModel.remove(indices[i]);
//                    if (indices[i] < index) index--;
//                }
//
//                for (String item : temp) targetModel.add(index++, item);
//
//            } else {
//                // 다른 리스트 MOVE
//                for (String line : lines) targetModel.add(index++, line);
//            }
//
//            return true;
//        } catch (Exception e) {
//            loggerMgr.getLogger().severe("importData error: " + e.toString());
//            return false;
//        }
//    }
//
//    @Override
//    protected void exportDone(JComponent c, Transferable data, int action) {
//        if (action != MOVE || sourceList == null) return;
//
//        try {
//            // 다른 리스트 MOVE일 때만 삭제
//            JList<?> src = sourceList;
//            if (c != src) {
//                DefaultListModel<?> srcModel = (DefaultListModel<?>) src.getModel();
//                Arrays.sort(indices);
//                for (int i = indices.length - 1; i >= 0; i--) {
//                    srcModel.remove(indices[i]);
//                }
//            }
//        } catch (Exception e) {
//            loggerMgr.getLogger().severe("exportDone error: " + e.toString());
//        } finally {
//            indices = null;
//            sourceList = null;
//        }
//    }
//}