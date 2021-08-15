package support;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

public class DataReader {
    private String filePath = Settings.getInstance().getDataSource();
    private String fileName;
    private String sheetName;
    private String referData;
    Sheet sheet;

    public DataReader(String referData, String fileName, String sheetName) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.referData = referData;
    }

    public DataReader(String fileName, String sheetName) {
        this.fileName = fileName;
        this.sheetName = sheetName;
    }

    public String get(String dataName, int referenceColumn) {
        InputStream inputStream;
        Workbook wb;
        DataFormatter formatter = new DataFormatter(Locale.US);
        try {
            if (sheet == null) {
                String fullPath = filePath + fileName + ".xlsx";
                if (fileName.contains(".xlsx")) {
                    fullPath = fileName;
                }
                inputStream = new FileInputStream(new File(fullPath));
                wb = WorkbookFactory.create(inputStream);
                sheet = wb.getSheet(sheetName);
            }
            boolean firstRow = true;
            Row dtColumnNames = null;
            for (Row row : sheet) {
                if (firstRow == true) {
                    dtColumnNames = row;
                    firstRow = false;
                    continue;
                }
                Cell cell = row.getCell(referenceColumn);
                String referenceData = formatter.formatCellValue(cell).trim();
                //Reference is to point the row corresponding to the test which is being executed
                if (referenceData.equalsIgnoreCase(referData)) {
                    for (int column = 0; column < row.getLastCellNum(); column++) {
                        if (dtColumnNames.getCell(column).getStringCellValue().trim().equalsIgnoreCase(dataName.trim())) {
                            return formatter.formatCellValue(row.getCell(column)).toString().trim();
                        }
                    }
                }
            }
            return "NOT_FOUND";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT_FOUND";
        }
    }

    public String get(String dataName, int referenceColumn, int headerInd) {
        InputStream inputStream;
        Workbook wb;
        String fullPath = null;
        DataFormatter formatter = new DataFormatter(Locale.US);
        try {
            if (sheet == null) {
                fullPath = filePath + fileName + ".xlsx";
                if (fileName.contains(".xlsx")) {
                    fullPath = fileName;
                }
                inputStream = new FileInputStream(new File(fullPath));
                wb = WorkbookFactory.create(inputStream);
                sheet = wb.getSheet(sheetName);
            }
            int rowNum = -1;
            Row clRws = sheet.getRow(headerInd - 1);
            for (Row row : sheet) {
                rowNum = row.getRowNum() + 1;
                if (rowNum <= headerInd) {
                    continue;
                }
                Cell cell = row.getCell(referenceColumn);
                String referenceData = formatter.formatCellValue(cell).trim();
                //Reference is to point the row corresponding to the test which is beong executed
                if (referenceData.equalsIgnoreCase(referData)) {
                    for (int column = 0; column < row.getLastCellNum(); column++) {
                        if (clRws.getCell(column).getStringCellValue().trim().equalsIgnoreCase(dataName.trim())) {
                            return formatter.formatCellValue(row.getCell(column)).toString().trim();
                        }
                    }
                }
            }
            return "NOT_FOUND";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT_FOUND";
        }
    }

    public String get(int rowIndex, int columnIndex) {
        InputStream inputStream;
        Workbook wb;
        String fullPath;
        DataFormatter formatter = new DataFormatter(Locale.US);
        try {
            if (sheet == null) {
                fullPath = filePath + fileName + ".xlsx";
                if (fileName.contains(".xlsx")) {
                    fullPath = fileName;
                }
                inputStream = new FileInputStream(new File(fullPath));
                wb = WorkbookFactory.create(inputStream);
                sheet = wb.getSheet(sheetName);
            }
            for (Row row : sheet) {
                if (row.getRowNum() == (rowIndex - 1)) {
                    return formatter.formatCellValue(row.getCell(columnIndex - 1)).toString().trim();
                }
            }
            return "NOT_FOUND";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT_FOUND";
        }
    }
}