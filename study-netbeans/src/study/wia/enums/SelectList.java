/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hdwia.hvac.enums;

public enum SelectList {
    DELETE("DELETE"),
    FLUID("FLUID"),
    SOLID("SOLID"),
    AUTOMATED_ACTIVE_1("AUTOMATED_ACTIVE_1"),
    AUTOMATED_ACTIVE_2("AUTOMATED_ACTIVE_2"),
    AUTOMATED_ACTIVE_3("AUTOMATED_ACTIVE_3"),
    AUTOMATED_ACTIVE_4("AUTOMATED_ACTIVE_4"),
    AUTOMATED_INACTIVE_1("AUTOMATED_INACTIVE_1"),
    AUTOMATED_INACTIVE_2("AUTOMATED_INACTIVE_2"),
    AUTOMATED_INACTIVE_3("AUTOMATED_INACTIVE_3"),
    AUTOMATED_INACTIVE_4("AUTOMATED_INACTIVE_4");

    private String name;

    SelectList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
