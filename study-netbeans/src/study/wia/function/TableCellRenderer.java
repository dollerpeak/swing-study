/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.function;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import study.wia.common.CommonManager;

/**
 * table
 * - 첫번째 컬럼 색상적용
 * - 활성화, 비활성화시 색상적용
 */
public class TableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == 0) {
            // 음영을 적용하고 싶은 컬럼
            //component.setBackground(Color.LIGHT_GRAY);
            component.setBackground(CommonManager.setColorGray());
            component.setForeground(Color.BLACK);
        } else {
            // 기존 그대로 유지
            if (isSelected) {
                component.setBackground(table.getSelectionBackground());
                component.setForeground(table.getSelectionForeground());
            } else {
                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLACK);
            }
        }

        return component;
    }

}
