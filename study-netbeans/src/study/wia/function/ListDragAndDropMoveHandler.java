/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hdwia.hvac.function;

import study.wia.common.LoggerManager;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.MOVE;

/**
 * List 드래그 앤 드롭 기능
 */
public class ListDragAndDropMoveHandler extends TransferHandler {
    private final LoggerManager loggerMgr;

    private int[] indices = null; // 드래그된 원본 인덱스들
    private int addIndex = -1; // 동일 리스트에 삽입이 일어난 시작 인덱스, 동일 리스트만
    private int addCount = 0; // 동일 리스트에 삽입된 항목 수
    private JList<?> sourceList = null; // 드래그 시작한 리스트 참조
    private boolean inSameList = false; // 드래그한 리스트와 드롭 타깃이 동일 리스트인지 여부

    public ListDragAndDropMoveHandler(LoggerManager loggerMgr) {
        this.loggerMgr = loggerMgr;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        // 드래그 시작, 선택된 인덱스/아이템
        JList<?> list = (JList<?>) c;
        sourceList = list; // 나중에 exportDone에서 동일 리스트 판별에 사용
        indices = list.getSelectedIndices();
        List<?> values = list.getSelectedValuesList();

        StringBuilder sb = new StringBuilder();
        for (Object v : values) {
            sb.append(v.toString()).append("\n");
        }
        // 문자열 기반 Transferable, 다른 리스트와 호환성 좋음
        return new StringSelection(sb.toString());
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        // 드롭 처리
        if (!canImport(support)) {
            return false;
        }

        try {
            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);

            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            @SuppressWarnings("unchecked")
            JList<String> target = (JList<String>) support.getComponent();
            DefaultListModel<String> model = (DefaultListModel<String>) target.getModel();

            int index = dl.getIndex();
            //boolean insert = dl.isInsert();

            // 인덱스 범위 체크
            if (index < 0 || index > model.getSize()) {
                index = model.getSize();
            }

            // 항상 add
            String[] lines = data.split("\n");
            int added = 0;
            for (String line : lines) {
                String item = line.trim();
                if (!item.isEmpty()) {
                    model.add(index++, item);
                    added++;
                }
            }

            // 동일 리스트 내 MOVE인지 여부 판단
            inSameList = (target == sourceList);

            // 동일 리스트인 경우에만 addIndex/addCount 기록 exportDone에서 보정에 사용
            if (inSameList) {
                addIndex = index - added; // 삽입이 실제로 시작된 위치
                addCount = added;
            } else {
                addIndex = -1;
                addCount = 0;
            }

            return true;
        } catch (Exception e) {
            loggerMgr.error(e.toString());
            return false;
        }
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        // 드래그 출발지에서 원본 항목 제거
        try {
            if (action == MOVE && indices != null) {
                JList<?> src = (JList<?>) source;
                DefaultListModel<?> model = (DefaultListModel<?>) src.getModel();

                // 동일 리스트로 MOVE한 경우에만, 삽입으로 인해 원본 인덱스가 밀린 부분을 보정
                if (inSameList && addCount > 0 && addIndex >= 0) {
                    for (int i = 0; i < indices.length; i++) {
                        if (indices[i] >= addIndex) {
                            indices[i] += addCount;
                        }
                    }
                }

                // 리스트는 제거시 인덱스 문제로 뒤에서부터 제거
                for (int i = indices.length - 1; i >= 0; i--) {
                    int idx = indices[i];
                    // 범위 체크
                    if (idx >= 0 && idx < model.getSize()) {
                        model.remove(idx);
                    } else {
                        // 범위를 벗어난 경우 로그만 출력
                        loggerMgr.error("remove out-of-range, idx = " + idx + ", model.getSize() = " + model.getSize());
                    }
                }
            }
        } finally {
            // 상태 초기화
            indices = null;
            addIndex = -1;
            addCount = 0;
            sourceList = null;
            inSameList = false;
        }
    }
}
