package cn.com.cworks.test;

import cn.com.cworks.db.OracleTool;
import cn.com.cworks.file.FileUtils;
import com.mchange.io.impl.EndsWithFilenameFilter;
import org.apache.poi.util.StringUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author: luchao
 * @date: 2021/9/6
 */
public class EntData {

    public static void main(String[] args) {
        File file = new File("/Users/lc_666/Desktop/xml");
        EndsWithFilenameFilter filter = new EndsWithFilenameFilter(".txt", 2);
        String[] filenames = file.list(filter);
        for (int i = 0; i < Objects.requireNonNull(filenames).length; i++) {
            String filename = filenames[i];
            String info = FileUtils.getInfoWithLineNumber("/Users/lc_666/Desktop/xml/" + filename, 34, "UTF-8");
            info = info.replaceAll("<FILENAME>", "").replaceAll("</FILENAME>", "").trim();
            System.out.println(filename + "   " + info);
            File data = new File("/Users/lc_666/Desktop/data" + info);
            String acNo = filename.substring(0, filename.lastIndexOf("."));
            FileUtils.writeStr("--" + acNo + "\n", "/Users/lc_666/Desktop/echecktrs.sql");
            readContent(data, acNo);
        }

    }

    public static void readContent(File data, String acno) {
        try (FileInputStream fis = new FileInputStream(data);
             InputStreamReader isr = new InputStreamReader(fis, "GBK");
             BufferedReader br = new BufferedReader(isr)) {
            String str = "";
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                assembleSQL(str, acno);
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件" + data);
        } catch (IOException e) {
            System.out.println("读取文件失败");
        }
    }

    public static void assembleSQL(String records, String acno) {
        String[] record = records.split("\\|");

        String jnlNo = record[7].replaceAll("#", "");
        String transDate = jnlNo.substring(0, 16);
        String remark = record[13].replaceAll("#", "");
        String transType = record[5].replaceAll("#", "");
        String outAmount = record[3].replaceAll("#", "");
        if ("".equals(outAmount)) outAmount = "0";
        String inAmount = record[4].replaceAll("#", "");
        if ("".equals(inAmount)) inAmount = "0";
        String balance = record[6].replaceAll("#", "");
        if ("".equals(balance)) balance = "0";
        String exAcNo = record[16].replaceAll("#", "");
        String exAcName = record[15].replaceAll("#", "");

        String exBankCode = record[17].replaceAll("#", "");
        String exBankName = OracleTool.oracle(exBankCode);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ECHECKTRS (ACNO,JNLNO,TRANSDATE,TRANSTYPE, VOUCHERTYPE, VOUCHERNO, REMARK, INAMOUNT, OUTAMOUNT, BALANCE, EXACNO, EXACNAME, EXBANKNAME, EXBANKNODE, EXBANKNODENAME, LOADDATE, TRANSCODE, BILLNO, OPERATORNO) \n" +
                        "VALUES('").append(acno).append("','")
                .append(jnlNo)
                .append("',to_date('")
                .append(transDate).append("','yyyyMMddHH24:mi:ss'),'")
                .append(transType).append("',null,null,'")
                .append(remark).append("','")
                .append(inAmount).append("','")
                .append(outAmount).append("','")
                .append(balance).append("','")
                .append(exAcNo).append("','")
                .append(exAcName).append("','")
                .append(exBankName).append("','")
                .append(exBankCode).append("','")
                .append(exBankName).append("',")
                .append("to_date('2021090102:00:00','yyyyMMddHH24:mi:ss'),'MCBatchEcheckTransferEnt',BILLNOSEQ.NEXTVAL,'');\n");

        FileUtils.writeStr(sb.toString(), "/Users/lc_666/Desktop/echecktrs.sql");

    }
}
