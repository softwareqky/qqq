package project.edge.common.exception;

import java.util.ArrayList;
import java.util.List;

public class ExcelParseException extends Exception {
	
	private static final long serialVersionUID = 5205989405618797795L;
	
	private List<Detail> details = new ArrayList<>();
	
	public void add(Detail detail) {
		this.details.add(detail);
	}
	
	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
	}

	public static class Detail extends Exception {
		
		public Detail(int column, int row, String message) {
			this.column = column;
			this.row = row;
			this.message = message;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -3805311478147297171L;

		private int column;
		
		private int row;
		
		private String message;

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		public String toString() {
			return "第" + (row+1) + "行" + " 第" + (column+1) + "列数据错误，原因：" + message;
		}
	}
}
