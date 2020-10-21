package cn.com.cworks.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
            logger.debug(dirPath + " does not exists!");
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

}
