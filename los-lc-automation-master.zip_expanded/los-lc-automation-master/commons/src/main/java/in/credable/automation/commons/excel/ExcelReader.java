package in.credable.automation.commons.excel;

import in.credable.automation.commons.exception.ColumnNotFoundException;
import in.credable.automation.commons.exception.ExcelInitializationException;
import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ExcelReader {
    private final Workbook workbook;
    private final Sheet sheet;
    private final Map<String, Integer> headerMap;

    public ExcelReader(File file, int sheetIndex) {
        try {
            this.workbook = new XSSFWorkbook(file);
            this.sheet = workbook.getSheetAt(sheetIndex);
            headerMap = new LinkedHashMap<>();
        } catch (Exception e) {
            throw new ExcelInitializationException("Exception while opening excel: " + file.getName(), e);
        }
    }

    ExcelReader(Workbook workbook, Sheet sheet, Map<String, Integer> headerMap) {
        this.workbook = workbook;
        this.sheet = sheet;
        this.headerMap = headerMap;
    }

    public List<String> getHeaderRowCellData() {
        if (getRowCount() > 0) {
            Row headerRow = sheet.getRow(0);
            return getRowData(headerRow);
        }
        return new ArrayList<>();
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public boolean isCellEmpty(Cell cell) {
        return (cell == null || cell.getCellType() == CellType.BLANK);
    }

    private List<String> getRowData(Row row) {
        List<String> rowData = new ArrayList<>();
        for (Cell cell : row) {
            rowData.add(getCellData(cell));
        }
        return rowData;
    }

    private String getCellData(Cell cell) {
        if (isCellEmpty(cell)) {
            return "";
        }
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell);
    }

    public int getColumnIndex(String columnName) {
        if (headerMap.isEmpty()) {
            createHeaderMap();
        }
        if (this.headerMap.containsKey(columnName)) {
            return this.headerMap.get(columnName);
        }
        throw new ColumnNotFoundException("Column " + columnName + " not found in header row");
    }

    private void createHeaderMap() {
        List<String> headerRowData = getHeaderRowCellData();
        for (int i = 0; i < headerRowData.size(); i++) {
            String headerName = headerRowData.get(i);
            headerMap.put(headerName, i);
        }
    }
}
