package cn.com.cworks.excel;


import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static List<Map<String, Object>> readExcel(String path) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            DataFormatter formatter = new DataFormatter();
            Workbook sheets = WorkbookFactory.create(new File(path));
            for (Sheet sheet : sheets) {
                for (Row row : sheet) {
                    Map<String, Object> temp = new HashMap<>();
                    for (Cell cell : row) {
                        temp.put(String.valueOf(cell.getColumnIndex()), formatter.formatCellValue(cell));
                    }
                    result.add(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void writeExcel(String path) {

    }

    public static void main(String[] args) {
    }


}
