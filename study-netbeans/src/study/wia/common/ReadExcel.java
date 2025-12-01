/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.wia.common;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;


public class ReadExcel implements Closeable {

    private final Workbook wb;
    private final DataFormatter formatter;
    private final FormulaEvaluator evaluator;

    private ReadExcel(Workbook wb) {
        this.wb = wb;
        this.formatter = new DataFormatter();
        this.evaluator = wb.getCreationHelper().createFormulaEvaluator();
    }

    // 파일 열기 (.xlsx/.xls 자동 인식)
    public static ReadExcel open(Path excelPath) throws IOException, InvalidFormatException {
        try (InputStream in = Files.newInputStream(excelPath)) {
            Workbook book = WorkbookFactory.create(in);
            return new ReadExcel(book);
        }
    }

    // 모든 시트명 반환
    public List<String> getSheetNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            names.add(wb.getSheetAt(i).getSheetName());
        }
        return names;
    }

    // 시트명, 행, 열
    // ex) String v1 = reader.get("AutomatedMesh", 5, 2);
    // 행과 열이 숫자로 들어가야 함, 1부터 시작, A=1, B=2
    public String get(String sheetName, int row1, int col1) {
        Sheet sheet = wb.getSheet(sheetName);
        if (sheet == null) {
            return null;
        }

        int rowIdx = row1 - 1;
        int colIdx = col1 - 1;
        Row row = sheet.getRow(rowIdx);
        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(colIdx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        return format(cell);
    }

    // 시트명, 열행
    // ex) String v2 = reader.get("AutomatedMesh", "B5");
    // 열과 행을 붙여서 보이는대로 적용
    public String getCell(String sheetName, String a1Address) {
        int[] rc = parseA1(a1Address);
        return get(sheetName, rc[0], rc[1]);
    }

    private String format(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.FORMULA) {
            return formatter.formatCellValue(cell, evaluator);
        }
        return formatter.formatCellValue(cell);
    }

    private static int[] parseA1(String a1) {
        Pattern p = Pattern.compile("^([A-Za-z]+)(\\d+)$");
        Matcher m = p.matcher(a1.trim());
        if (!m.matches()) {
            throw new IllegalArgumentException("잘못된 A1 주소: " + a1);
        }

        String letters = m.group(1).toUpperCase(Locale.ROOT);
        int col1 = 0;
        for (int i = 0; i < letters.length(); i++) {
            col1 = col1 * 26 + (letters.charAt(i) - 'A' + 1);
        }
        int row1 = Integer.parseInt(m.group(2));
        return new int[]{row1, col1};
    }

    @Override
    public void close() throws IOException {
        wb.close();
    }
    
    // ================================
    // 1) 이름상자: 단일 셀 읽기
    // ================================
    public String getName(String definedName) {
        Name name = wb.getName(definedName);
        if (name == null) {
            throw new IllegalArgumentException("정의된 이름이 존재하지 않습니다: " + definedName);
        }

        String refersTo = name.getRefersToFormula();
        if (refersTo == null) {
            return "";
        }

        AreaReference area = new AreaReference(refersTo, wb.getSpreadsheetVersion());
        CellReference firstCell = area.getFirstCell();

        Sheet sheet = wb.getSheet(firstCell.getSheetName());
        if (sheet == null) {
            return "";
        }

        Row row = sheet.getRow(firstCell.getRow());
        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(firstCell.getCol(), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        return format(cell);
    }


    // ================================
    // 2) 이름상자: 범위 전체 읽기
    //    (B2:D20 같이 여러 셀인 경우)
    // ================================
    public List<String> getNameRange(String definedName) {
        Name name = wb.getName(definedName);
        if (name == null) {
            throw new IllegalArgumentException("정의된 이름이 존재하지 않습니다: " + definedName);
        }

        String refersTo = name.getRefersToFormula();
        if (refersTo == null) {
            return List.of();
        }

        AreaReference area = new AreaReference(refersTo, wb.getSpreadsheetVersion());
        CellReference[] refs = area.getAllReferencedCells();

        List<String> result = new ArrayList<>();

        for (CellReference ref : refs) {
            Sheet sheet = wb.getSheet(ref.getSheetName());
            if (sheet == null) {
                result.add("");
                continue;
            }

            Row row = sheet.getRow(ref.getRow());
            if (row == null) {
                result.add("");
                continue;
            }

            Cell cell = row.getCell(ref.getCol(), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            result.add(format(cell));
        }

        return result;
    }
}
