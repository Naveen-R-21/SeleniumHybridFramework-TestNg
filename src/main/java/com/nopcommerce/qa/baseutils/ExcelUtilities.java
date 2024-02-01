package com.nopcommerce.qa.baseutils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelUtilities {

	public static XSSFWorkbook WBook = null;


	public static String modifyFilePath(String filePath) {
		String os = System.getProperty("os.name").toLowerCase();
		String replacedPath;

		// Check if the operating system is Windows
		if (os.contains("win")) {
			// Modify file path for Windows
			replacedPath = filePath.replace("/", "\\");
			//System.out.println("Executing in Windows OS ");
		} else {
			// Modify file path for other operating systems (assuming Unix-like)
			replacedPath = filePath.replace("\\", "/");
			// System.out.println("Executing in MAC OS ");
		}

		// System.out.println("Modified File Path: " + replacedPath); // Print the modified path only once
		return replacedPath;
	}


	public static String browserDataFromExcel(String sheetName, int rowNum, int colNum) throws IOException {
		// Original file path
		String originalFilePath = "/src/main/java/com/nopcommerce/qa/testdata/NopCommerce.xlsx";

		// Modify file path based on the operating system
		String modifiedFilePath = modifyFilePath(originalFilePath);

		// Create the file object using the modified path
		File excelFile = new File(System.getProperty("user.dir") + modifiedFilePath);

		FileInputStream inputStream = new FileInputStream(excelFile);
		Workbook workbook = new XSSFWorkbook(inputStream);

		try {
			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowNum);
			Cell cell = row.getCell(colNum);
			return getCellValueAsString(cell);
		} finally {
			workbook.close();
			inputStream.close();
		}
	}



	public static String getCellValueAsStringBrowser(Cell cell) {
		if (cell == null || cell.getCellType() == CellType.BLANK) {
			return "";
		} else if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else {
			return "";
		}
	}


	public static List<Object[]> getTestDataFromExcel(String sheetName, String... columnNames) {
		List<Object[]> testData = new ArrayList<>();

		// Original file path
		String filePath = "/src/main/java/com/nopcommerce/qa/testdata/NopCommerce.xlsx";

		// Modify file path based on the operating system
		String modifiedFilePath = modifyFilePath(filePath);

		File excelFile = new File(System.getProperty("user.dir") + modifiedFilePath);

		try (FileInputStream fis = new FileInputStream(excelFile)) {
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetName);

			Iterator<Row> rowIterator = sheet.iterator();
			// Find header row
			Row headerRow = rowIterator.next();

			int[] columnIndex = new int[columnNames.length];
			for (int i = 0; i < columnNames.length; i++) {
				columnIndex[i] = findColumnIndex(headerRow, columnNames[i]);
			}

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Object[] data = new Object[columnNames.length];
				for (int i = 0; i < columnNames.length; i++) {
					int index = columnIndex[i];
					data[i] = getCellValueAsString(row.getCell(index));
				}

				testData.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return testData;
	}

	public static int findColumnIndex(Row headerRow, String columnName) {
		Iterator<Cell> cellIterator = headerRow.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
				return cell.getColumnIndex();
			}
		}
		throw new IllegalArgumentException("Column with name '" + columnName + "' not found.");
	}

	public static String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}

	public static String captureScreenshot(WebDriver driver, String name) {
		// TODO Auto-generated method stub
		return null;
	}


}
