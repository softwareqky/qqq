package project.edge.common.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter<T> {
	
	/**
	 * 将目标实体数据导出到Excel文件中
	 * @param dataList
	 * @param clazz
	 * @param fields
	 * @param filePath
	 * @param title
	 * @return
	 */
	public File export(List<T> dataList, List<ExcelField> fields, String filePath, String title) {
		
		FileOutputStream output = null;
		File file = new File(filePath);
		
		try {
			
			// 创建Sheet
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			
			// 创建Titler行
			this.createTitle(wb, sheet, title, fields.size(), 0);
			
			// 创建Header行
			this.createHeader(wb, sheet, fields, 1);
			
			// 创建Content行
			this.createContent(wb, sheet, dataList, fields, 2);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			// 冻结第2行
			sheet.createFreezePane(0, 2);
			
			// 写文件
			output = new FileOutputStream(file);
			wb.write(output);
			output.flush();
			return file;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return file;
	}
	
	/**
	 * 设置表格内容
	 * @param wb
	 * @param sheet
	 * @param fields
	 * @param clazz
	 * @param rowNum
	 */
	private void createContent(Workbook wb, Sheet sheet, List<T> dataList, List<ExcelField> fields, int rowNum) {
		
		for (int i = 0; i < dataList.size(); i++) {
			
			T data = dataList.get(i);
			Row row = sheet.createRow(rowNum + i);
			row.setHeight((short) 500);
			
			for (int j = 0; j < fields.size(); j++) {
				
				ExcelField field = fields.get(j);
				String value = this.getCellValue(data, field.getField(), field.getType(), field.getEnumMap());
				if (value == null) return;
				
				row.createCell(j).setCellValue(value);
			}
		}
	}
	
	/**
	 * 取得单元格对应的值
	 * @param data
	 * @param field
	 * @return
	 */
	private String getCellValue(Object data, String fieldName, int fieldType, HashMap<Integer, String> enumMap) {
		
		try {
			
			String[] fieldNames = fieldName.split("\\.");
			
			Class<?> clazz = data.getClass();
			Field field = clazz.getDeclaredField(fieldNames[0]);
			field.setAccessible(true);
			
			Object value = field.get(data);
			if (value == null) {
				return "";
			} else if (fieldNames.length > 1) {
				return this.getCellValue(value, fieldName.substring(fieldNames[0].length() + 1), fieldType, enumMap);
			} else {
				
				switch (fieldType) {
				
				case ExcelField.TYPE_NUMBER:
					return Float.valueOf(value.toString()).intValue() + "";
				case ExcelField.TYPE_ENUM:
					return enumMap.get(Integer.parseInt(value.toString()));
				case ExcelField.TYPE_BOOLEAN:
					return value.toString().equals("0") ? "否" : "是";
				case ExcelField.TYPE_FLOAT:
				case ExcelField.TYPE_TEXT:
				default:
					return value.toString();
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 设置表头
	 * @param wb
	 * @param sheet
	 * @param fields
	 * @param row
	 */
	private void createHeader(Workbook wb, Sheet sheet, List<ExcelField> fields, int rowNum) {
		
		Row row = sheet.createRow(rowNum);
		row.setHeight((short) 800);
		
		// 设置表头单元格的样式
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		Font font = wb.createFont();
		font.setBold(true); // 加粗
		font.setFontHeightInPoints((short) 12); // 设置标题字体大小
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		
		// 设置背景色和边框
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		
		for (int i = 0; i < fields.size(); i++) {
			Cell headerCell = row.createCell(i);
			headerCell.setCellValue(fields.get(i).getTitle());
			headerCell.setCellStyle(cellStyle);
		}
	}
	
	
	/**
	 * 设置标题头部
	 * @param sheet
	 */
	private void createTitle(Workbook wb, Sheet sheet, String title, int colCount, int rowNum) {
		
		Row row = sheet.createRow(rowNum);
		row.setHeight((short) 1000);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, colCount));
		Cell headCell = row.createCell(0);
		headCell.setCellValue(title);
		
		// 设置Header行的样式
		// TDOO: 这个是固定样式，以后可以拓展
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		Font font = wb.createFont();
		font.setBold(true); // 加粗
		font.setFontHeightInPoints((short) 15); // 设置标题字体大小
		cellStyle.setFont(font);
		headCell.setCellStyle(cellStyle);
	}
}
