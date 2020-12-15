package cn.com.cworks.excel;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExcelUtil {

    public static List<Map<String, Object>> readExcel(String path) {
        try (FileInputStream input = new FileInputStream(path)){
            Workbook book = null;
            if (path.toLowerCase(Locale.ROOT).endsWith("xlsx")){
                book = new XSSFWorkbook(input);
            }else if (path.toLowerCase(Locale.ROOT).endsWith("xlsx")){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
