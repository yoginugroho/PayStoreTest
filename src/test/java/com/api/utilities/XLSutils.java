package com.api.utilities;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;


public class XLSutils {
	private FileInputStream fi;
	private FileOutputStream fo;
	private HSSFWorkbook wb;
	private HSSFSheet ws;
	private HSSFRow row;
	private HSSFCell cell;
	
	public XLSutils() {
		super();
	}

	public int getRowCount(String xlfile,String xlsheet) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new HSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		int rowcount=ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;		
	}


	public int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new HSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}

	public String getCellData(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new HSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);

		DataFormatter formatter = new DataFormatter();
		Object value = formatter.formatCellValue(cell);

		wb.close();
		fi.close();
		return value.toString();
	}

	public void setCellData(String xlfile,String xlsheet,int rownum,int colnum,String data) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new HSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);		
		wb.close();
		fi.close();
		fo.close();
	}

	public Object[][] getAllCellData(String path, String sheetName, int startRow) throws IOException {
		int rowNum = this.getRowCount(path, sheetName);
		int rowColumn = this.getCellCount(path,sheetName,startRow);
		Object[][] dataXLS=new Object[rowNum-startRow+1][rowColumn];
		int counter=0;
		for(int i=startRow;i<=rowNum;i++) {
			for(int j=0;j<rowColumn;j++) {
				dataXLS[counter][j]=this.getCellData(path,sheetName,i,j);
			}
			counter++;
		}

		return dataXLS;
	}

	public void writeCellData(String path, String sheetName, Object[][] data) throws IOException {
		InputStream inputStream = new FileInputStream(path);
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.createSheet(sheetName);

		int rowCount = 0;
		for (Object[] rowData : data) {
			HSSFRow row = sheet.createRow(++rowCount);
			int columnCount = 0;
			for (Object cellData : rowData) {
				HSSFCell cell = row.createCell(++columnCount);
				cell.setCellValue((String)cellData);
			}
		}

		try (FileOutputStream outputStream = new FileOutputStream(path)) {
			workbook.write(outputStream);
			workbook.close();
		}
	}


}
