package elethu.ikamva.utils;

import elethu.ikamva.domain.enums.ReportType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public void writeHeaders(ReportType reportType, XSSFWorkbook workbook, XSSFSheet sheet, Map<String,String> headers, int rowCount) {
        switch (reportType) {
            case INDIVIDUAL:
                writeIndividualHeaders(workbook, sheet, headers, rowCount);
                break;
            case ALL:
                writeAllHeaders(workbook, sheet, headers, rowCount);
                break;
            case MONTHLY:
                writeMonthlyHeaders(workbook, sheet, headers, rowCount);
                break;
            case YEARLY:
                writeYearlyHeaders(workbook, sheet, headers, rowCount);
                break;
            case SUMMARY:
                writeSummaryHeaders(workbook, sheet, headers, rowCount);
                break;
            default:
                writeCustomHeaders(workbook, sheet, headers, rowCount);
                break;
        }
    }

    public void createCell(Row row, int colCount, Object value, CellStyle style, Sheet sheet) {
        sheet.autoSizeColumn(colCount);

        Cell cell = row.createCell(colCount);
        if(value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public void writeReportXAndYAxis(XSSFWorkbook workbook, XSSFSheet sheet, List<String> x, List<String> y, int rowCount) {
        Row row = sheet.createRow(rowCount);
        int colCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(12);
        font.setColor(IndexedColors.BLUE.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);

        for(String xValue : x) {
            createCell(row, colCount, xValue, style, sheet);
            colCount++;
        }

        for(String yValue : y) {
            row = sheet.createRow(rowCount++);
            createCell(row, colCount, yValue, style, sheet);
            colCount++;
        }
    }

    private void writeIndividualHeaders(XSSFWorkbook workbook, XSSFSheet sheet, Map<String,String> headers, int rowCount) {
        sheet  = workbook.createSheet("Individual");
        Row row = sheet.createRow(rowCount);
        int colCount = 0;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        for(Map.Entry<String, String> header : headers.entrySet()) {
            createCell(row, colCount, String.format("%s%s",header.getKey(), ":"), style, sheet);
            createCell(row, colCount++, header.getValue(), style, sheet);
            row = sheet.createRow(rowCount++);
            colCount++;
        }
    }

    private void writeCustomHeaders(XSSFWorkbook workbook, XSSFSheet sheet, Map<?,?> headers, int rowCount) {
    }

    private void writeYearlyHeaders(XSSFWorkbook workbook, XSSFSheet sheet, Map<?,?> headers, int rowCount) {
    }

    private void writeMonthlyHeaders(XSSFWorkbook workbook, XSSFSheet sheet, Map<?,?> headers, int rowCount) {
    }

    private void writeAllHeaders(XSSFWorkbook workbook, XSSFSheet sheet, Map<?,?> headers, int rowCount) {
    }

    private void writeSummaryHeaders(XSSFWorkbook workbook, XSSFSheet sheet, Map<String, String> headers, int rowCount) {
    }




}
