package util.reports;

import java.util.List;

public class TablePrinter {
	public static String printTable(List<List<String>> data) {
		StringBuilder tableString = new StringBuilder();

		int[] columnWidths = calculateColumnWidths(data);

		tableString.append(printSeparator(columnWidths));
		tableString.append(printRow(data.get(0), columnWidths));
		tableString.append(printSeparator(columnWidths));

		for (int i = 1; i < data.size(); i++) {
			tableString.append(printRow(data.get(i), columnWidths));
		}

		tableString.append(printSeparator(columnWidths));

		return tableString.toString();
	}

	private static String printSeparator(int[] columnWidths) {
		StringBuilder separator = new StringBuilder();

		for (int width : columnWidths) {
			separator.append("+");
			for (int i = 0; i < width; i++) {
				separator.append("-");
			}
		}
		separator.append("+\n");

		return separator.toString();
	}

	private static String printRow(List<String> row, int[] columnWidths) {
		StringBuilder rowString = new StringBuilder();

		for (int i = 0; i < row.size(); i++) {
			rowString.append("|");
			rowString.append(String.format("%-" + columnWidths[i] + "s", row.get(i)));
		}
		rowString.append("|\n");

		return rowString.toString();
	}

	private static int[] calculateColumnWidths(List<List<String>> data) {
		int numColumns = data.get(0).size();
		int[] columnWidths = new int[numColumns];
		for (List<String> row : data) {
			for (int i = 0; i < numColumns; i++) {
				if (row.get(i).length() > columnWidths[i]) {
					columnWidths[i] = row.get(i).length();
				}
			}
		}
		return columnWidths;
	}
}
