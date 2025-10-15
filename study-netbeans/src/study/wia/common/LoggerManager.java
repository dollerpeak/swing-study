/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import star.common.Simulation;

public class LoggerManager {
    private Simulation simulation;

    public LoggerManager(Simulation simulation) {
        this.simulation = simulation;
    }

    public void info(String message) {
        //System.out.print("[INFO] " + getMakeInfo() + " : " + message + "\n");
        simulation.print("[INFO] " + getMakeInfo() + " : " + message + "\n");
    }

    public void warning(String message) {
        //System.out.print("[wARN] " + getMakeInfo() + " : " + message + "\n");
        simulation.print("[wARN] " + getMakeInfo() + " : " + message + "\n");
    }

    public void error(String message) {
        //System.out.print("[ERROR] " + getMakeInfo() + " : " + message + "\n");
        simulation.print("[ERROR] " + getMakeInfo() + " : " + message + "\n");
    }

    String getMakeInfo() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = now.format(format);
        
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement ste : stack) {
            String cls = ste.getClassName();
            String method = ste.getMethodName();

            // 건너뛸 프레임
            if (cls.equals(Thread.class.getName()) && "getStackTrace".equals(method)) continue;
            if (cls.equals(LoggerManager.class.getName())) continue;
            if (cls.startsWith("java.") || cls.startsWith("javax.") ||
                cls.startsWith("sun.") || cls.startsWith("jdk.") ||
                cls.startsWith("org.slf4j.") || cls.startsWith("org.apache.")) continue;

            return time + " " + cls + "." + method + "():" + ste.getLineNumber();
        }
        
        return time + " UnKnown";
    }
}
