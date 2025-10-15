/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.common;

import java.awt.Component;
import javax.swing.JOptionPane;


public class CommonManager {
    static public int showConfirmDialog(Component parentComponent, String title, String message, int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType);
        //return JOptionPane.showConfirmDialog(
        //        this,
        //        "선택한 항목을 삭제하시겠습니까?",
        //        "삭제",
        //        JOptionPane.YES_NO_OPTION,
        //        JOptionPane.WARNING_MESSAGE
        //);
    }

   static public String showInputDialog(Component parentComponent, String message, Object initialSelectionValue) {
        return JOptionPane.showInputDialog(parentComponent, message, initialSelectionValue);
        //return JOptionPane.showInputDialog(
        //        this,
        //        "수정할 내용을 입력하세요.",
        //        currentValue
        //);
    }

    static public void showMessageDialog(Component parentComponent, String title, String message, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
        //JOptionPane.showMessageDialog(
        //        this,
        //        "빈 값으로 변경할 수 없습니다.",
        //        "알림",
        //        JOptionPane.WARNING_MESSAGE
        //);
    }
}
