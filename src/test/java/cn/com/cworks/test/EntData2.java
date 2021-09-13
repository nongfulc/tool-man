package cn.com.cworks.test;

import cn.com.cworks.file.FileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: luchao
 * @date: 2021/9/6
 */
public class EntData2 {

    private static List<Map<String, Object>> entMap;

    static {
        String entInfo = FileUtils.getInfoWithLineNumber("/Users/lc_666/Desktop/prod_ent_user_data.json", 0, StandardCharsets.UTF_8.name());
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionLikeType collectionLikeType = objectMapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, Map.class);
        try {
            entMap = objectMapper.readValue(entInfo, collectionLikeType);
            System.out.println(entMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        readData(new File("/Users/lc_666/Desktop/ECHECKTRS_202109081503.csv"));

    }

    public static void readData(File data) {
        try (FileInputStream fis = new FileInputStream(data);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            String str = "";
            while ((str = br.readLine()) != null) {
                assemble(str);
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件" + data);
        } catch (IOException e) {
            System.out.println("读取文件失败");
        }
    }

    //667301056880500010,2021083111:33:02731100000071,2021-08-31 11:33:02,TR,,,转账,0.0000,0.0000,6217000130055079221,田来云,中国建设银行股份有限公司河北省分行,105121000016,中国建设银行股份有限公司河北省分行,2021-09-01 02:00:00,MCBatchEcheckTransferEnt,2825,,49321
    public static void assemble(String records) {
        String[] record = records.split(",");
        for (int i = 0; i < record.length; i++) {
            System.out.println(i + "  " + record[i]);
        }
        String acNo = record[0].replaceAll("\"", "").trim();
        String transDate = record[2].replaceAll("\"", "").trim();
        String remark = record[6].replaceAll("\"", "").trim();

        String balance = record[8].replaceAll("\"", "").trim();
        if ("".equals(balance)) balance = "0";

        String acName = "";
        String deptSeq = "";
        String billNo = record[16].replaceAll("\"", "").trim();
        ;
        for (Map map : entMap) {
            if (acNo.equals(map.get("AcNo"))) {
                acName = (String) map.get("ACNAME");
                deptSeq = (String) map.get("DEPTSEQ");
                break;
            }
        }
        StringBuilder echeck = new StringBuilder();
        echeck.append("INSERT INTO ECHECK (ACNO, BILLNO, ACNAME, CURRENCYCODE, DEPTSEQ, BANKACTYPECHINESE, BALANCE, STATE, CREATEDATE,\n" +
                        "                    REMARK, CHECKRESULT, TRANSDATE, PROCESSTIME, DOWNDATE, PARENTACNO, PRINTCOUNT)\n" +
                        "VALUES ('").append(acNo).append("','")
                .append(billNo).append("','")
                .append(acName).append("','CNY','")
                .append(deptSeq).append("',NULL,'")
                .append(balance).append("','00',TO_DATE('2021-09-01 02:00:00', 'yyyy-mm-dd hh24:mi:ss'),'")
                .append(remark).append("',NULL,TO_DATE('")
                .append(transDate).append("', 'yyyy-mm-dd hh24:mi:ss'),TO_DATE('2021-09-01 02:00:00', 'yyyy-mm-dd hh24:mi:ss'),NULL,NULL,'0');\n");
        StringBuilder echeckBalance = new StringBuilder();
        echeckBalance.append("INSERT INTO ECHECKBALANCE (BILLNO, ACNO, ENDDATE, ENDBALANCE, CHECKSTATE, CHECKTYPE, CHECKDATE, CHECKRESULT, AMOUNT,\n" +
                        "                           USERID, LOADDATE)\n" +
                        "VALUES ('").append(billNo).append("','")
                .append(acNo).append("',TO_DATE('")
                .append(transDate).append("', 'yyyy-mm-dd hh24:mi:ss'),'")
                .append(balance).append("','00',NULL,NULL, NULL, NULL, NULL, TO_DATE('2021-09-01 02:00:00', 'yyyy-mm-dd hh24:mi:ss'));\n\n");
        System.out.println(echeck);
        System.out.println(echeckBalance);

        FileUtils.writeStr("--" + acNo + "\n", "/Users/lc_666/Desktop/echeck.sql");
        FileUtils.writeStr(echeck.toString(), "/Users/lc_666/Desktop/echeck.sql");
        FileUtils.writeStr(echeckBalance.toString(), "/Users/lc_666/Desktop/echeck.sql");
    }
}
