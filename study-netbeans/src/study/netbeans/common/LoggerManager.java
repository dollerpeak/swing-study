/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.common;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Level;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author a
 */
public class LoggerManager {

    private Logger logger;

    public LoggerManager(String className, Level level) {
        logger = Logger.getLogger(className);
        // 루트 핸들러 비활성화
        logger.setUseParentHandlers(false);

        //콘솔 핸들러
        ConsoleHandler handler = new ConsoleHandler();
        
        // 포맷 설정
        handler.setFormatter(new Formatter() {
            @Override
            public synchronized String format(LogRecord record) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy:HH:mm:ss");
                String dateTime = sdf.format(new Date(record.getMillis()));

                String loggerName = record.getLoggerName();

                String lineNum = "NON";
                if (record.getSourceClassName() != null && record.getSourceMethodName() != null) {
                    lineNum = getSourceLine(record);
                }

                return String.format("%s %s:%s %s: %s%n",
                        dateTime, // 날짜 시간
                        loggerName, // 
                        lineNum, // 라인
                        record.getLevel().getName(), // 로그 레벨
                        record.getMessage() // 메시지
                );
            }
        });
        
        // 한글설정
        try {
            handler.setEncoding("UTF-8");
        } catch (Exception e) {
            //
        }

        handler.setLevel(level); //Level.ALL
        logger.setLevel(level); //Level.ALL
        logger.addHandler(handler);
    }

    String getSourceLine(LogRecord record) {
        try {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stack) {
                if (element.getClassName().equals(record.getSourceClassName())) {
                    return Integer.toString(element.getLineNumber());
                }
            }
        } catch (Exception e) {
            //
        }

        return "NON";
    }

    public Logger getLogger() {
        return logger;
    }
}
