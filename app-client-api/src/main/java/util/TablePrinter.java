package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TablePrinter {
    public void printTable(List<List<String>> data) {
        int[] columnWidths = calculateColumnWidths(data);

        printSeparator(columnWidths);
        printRow(data.get(0), columnWidths);
        printSeparator(columnWidths);

        for (int i = 1; i < data.size(); i++) {
            printRow(data.get(i), columnWidths);
        }

        printSeparator(columnWidths);
    }

    private void printSeparator(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+");
            for (int i = 0; i < width; i++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    private void printRow(List<String> row, int[] columnWidths) {
        for (int i = 0; i < row.size(); i++) {
            System.out.print("|");
            System.out.print(String.format("%-" + columnWidths[i] + "s", row.get(i)));
        }
        System.out.println("|");
    }

    private int[] calculateColumnWidths(List<List<String>> data) {
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
