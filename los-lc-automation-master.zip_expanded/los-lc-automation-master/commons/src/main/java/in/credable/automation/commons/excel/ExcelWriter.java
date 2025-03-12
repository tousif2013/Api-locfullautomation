package in.credable.automation.commons.excel;

import in.credable.automation.commons.exception.ColumnNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
public class ExcelWriter {

    private final Workbook workbook;
    private final Sheet sheet;
    private final Map<String, Integer> headerMap;

    public ExcelWriter(String sheetName) {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet(sheetName);
        headerMap = new LinkedHashMap<>();
    }

    public ExcelWriter(ExcelReader excelReader) {
        this.workbook = excelReader.getWorkbook();
        this.sheet = excelReader.getSheet();
        this.headerMap = excelReader.getHeaderMap();
    }

    public void createHeaderRow(String... headerRowCellData) {
        Row header = sheet.createRow(0);
        for (int i = 0; i < headerRowCellData.length; i++) {
            header.createCell(i).setCellValue(headerRowCellData[i]);
            headerMap.put(headerRowCellData[i], i);
        }
    }

    public void writeDataIntoNewRow(Map<String, Object> data) {
        int rowIndex = sheet.getLastRowNum() + 1;
        sheet.createRow(rowIndex);
        ExcelReader excelReader = new ExcelReader(workbook, sheet, headerMap);
        for (String key : data.keySet()) {
            try {
                int columnIndex = excelReader.getColumnIndex(key);
                writeData(rowIndex, columnIndex, data.get(key));
            } catch (ColumnNotFoundException ignored) {
                log.warn("Column " + key + " not found in header row. Not writing value for this key.");
            }
        }
    }

    @SneakyThrows
    public void saveFile(File file) {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    private void writeData(int rowIndex, int columnIndex, Object data) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            cell = row.createCell(columnIndex);
        }
        if (data instanceof String) {
            cell.setCellValue((String) data);
        } else if (data instanceof Integer) {
            cell.setCellValue((Integer) data);
        } else if (data instanceof Double) {
            cell.setCellValue((Double) data);
        } else if (data instanceof Boolean) {
            cell.setCellValue((Boolean) data);
        } else if (data instanceof Long) {
            cell.setCellValue((Long) data);
        } else if (data instanceof Float) {
            cell.setCellValue((Float) data);
        } else if (data instanceof Date) {
            cell.setCellValue((Date) data);
        } else if (data instanceof Calendar) {
            cell.setCellValue((Calendar) data);
        } else if (data instanceof RichTextString) {
            cell.setCellValue((RichTextString) data);
        }
    }
}
