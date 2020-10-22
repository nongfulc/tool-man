package cn.com.cworks.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 返回文件夹下文件按照正序排序的列表
     *
     * @param dirPath 文件夹路径
     * @return 文件夹下文件按照正序排序的文件名称
     */
    public static List<String> getFileNamesInDir(String dirPath) {
        List<String> fileNames = new ArrayList<>();
        File dir = new File(dirPath);
        if (!dir.exists()) {
            logger.debug(dirPath + " does NOT exists!");
            throw new RuntimeException(dirPath + " does not exists!");
        }
        if (!dir.isDirectory()) {
            logger.debug(dirPath + " is NOT directory!");
            throw new RuntimeException(dirPath + " is NOT directory!");
        }
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (!file.isDirectory()) {
                fileNames.add(file.getName());
            }
        }
        fileNames.sort(Comparator.naturalOrder());
        return fileNames;
    }

    /**
     * 获取文件中第几行的数据，行号为0则读取全部
     *
     * @param filePath   文件路径
     * @param lineNumber 行号
     * @return 文本内容
     */
    public static String getInfoWithLineNumber(String filePath, int lineNumber, String charsetName) {
        StringBuilder infoBuilder = new StringBuilder();
        File file = new File(filePath);
        if (!file.exists()) {
            logger.debug("dose NOT exists!");
            throw new RuntimeException(filePath + " does not exists!");
        }
        try (
                FileInputStream in = new FileInputStream(file);
                InputStreamReader reader = new InputStreamReader(in, charsetName);
                BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            String line;
            int i = 0;
            while (null != (line = bufferedReader.readLine())) {
                if (0 != lineNumber) {
                    i++;
                    if (i == lineNumber) {
                        infoBuilder.append(line);
                        break;
                    }
                } else {
                    infoBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infoBuilder.toString();
    }

    public static String getTextFromFile(String path, String encoding) {
        StringBuilder text = new StringBuilder();
        File file = new File(path);
        if (!file.exists())
            throw new RuntimeException(path + " does NOT exists!");

        try (
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader reader = new BufferedReader(read);
        ) {
            String line;
            while (null != (line = reader.readLine())) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

}
