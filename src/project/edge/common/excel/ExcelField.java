package project.edge.common.excel;

import java.util.HashMap;

public class ExcelField {
	
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_NUMBER = 1;
	public static final int TYPE_FLOAT = 2;
	public static final int TYPE_BOOLEAN = 3;
	public static final int TYPE_ENUM = 4;

	private String title;
	
	private String field;
	
	private int type = ExcelField.TYPE_TEXT;
	
	private String format;
	
	private HashMap<Integer, String> enumMap;
	
	public ExcelField(String title, String field) {
		this.title = title;
		this.field = field;
	}
	
	public ExcelField(String title, String field, int type) {
		this.title = title;
		this.field = field;
		this.type = type;
	}
	
	public ExcelField(String title, String field, int type, String format) {
		this.title = title;
		this.field = field;
		this.type = type;
		this.format = format;
	}
	
	public ExcelField(String title, String field, HashMap<Integer, String> enumMap) {
		this.title = title;
		this.field = field;
		this.type = ExcelField.TYPE_ENUM;
		this.enumMap = enumMap;
	} 

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public HashMap<Integer, String> getEnumMap() {
		return enumMap;
	}

	public void setEnumMap(HashMap<Integer, String> enumMap) {
		this.enumMap = enumMap;
	}
	
}
