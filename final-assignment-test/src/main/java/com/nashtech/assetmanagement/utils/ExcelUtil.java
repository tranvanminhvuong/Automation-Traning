package com.nashtech.assetmanagement.utils;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    private static FileInputStream fis;
    private FileOutputStream fileOut;
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFCell cell;
    private static XSSFRow row;
    private CellStyle cellstyle;
    private Color mycolor;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    public static String getCellData(int rownum, int colnum) throws Exception {
        try {
            cell = sheet.getRow(rownum).getCell(colnum);
            String CellData = null;
            switch (cell.getCellType()) {
            case STRING:
                CellData = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    CellData = String.valueOf(cell.getDateCellValue());
                } else {
                    CellData = String.valueOf((long) cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                CellData = Boolean.toString(cell.getBooleanCellValue());
                break;
            case BLANK:
                CellData = "";
                break;
            }
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    public static Object[][] getExcelDataContainValue(String fileName, String sheetName, boolean value)
            throws IOException {
        Object[][] data = null;
        try {
            fis = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(0);
            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = row.getLastCellNum();
            JsonObject dataJson;

            int total = 0, temp = 0;

            for (int i = 1; i < noOfRows; i++) {
                if (sheet.getRow(i).getCell(noOfCols - 1).getBooleanCellValue() == value)
                    temp++;
            }
            data = new Object[temp][1];

            for (int i = 1; i < noOfRows; i++) {
                dataJson = new JsonObject();
                if (sheet.getRow(i).getCell(noOfCols - 1).getBooleanCellValue() == value) {
                    total++;
                    for (int j = 0; j < noOfCols; j++) {
                        row = sheet.getRow(i);
                        cell = row.getCell(j);
                        dataJson.addProperty(getCellData(0, j), getCellData(i, j));
                    }
                    data[total - 1][0] = dataJson;
                }
            }
        } catch (Exception e) {
            System.out.println("The exception is: " + e.getMessage());
        }
        return data;
    }
}
