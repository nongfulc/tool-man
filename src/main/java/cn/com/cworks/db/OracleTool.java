package cn.com.cworks.db;

import java.sql.*;

/**
 * @author: luchao
 * @date: 2021/9/6
 */
public class OracleTool {

    public static String oracle(String bankCode) {
        switch (bankCode) {
            case "730010":
                return "禹州新民生村镇银行";
            case "730050":
                return "禹州新民生村镇银行鸿畅支行";
            case "730030":
                return "禹州新民生村镇银行范坡支行";
            case "730060":
                return "禹州新民生村镇银行颍河大街支行";
            case "730070":
                return "禹州新民生村镇银行古城支行";
            case "730100":
                return "禹州新民生村镇银行滨河支行";
        }
        String driver = "oracle.jdbc.driver.OracleDriver";
        //2、定义连接URL
        String url = "jdbc:oracle:thin:@//123.56.169.158:15280/EIP";
        String username = "eipdba";//用户名
        String password = "EipPassword1";//密码
        String sql = "SELECT lname from MCAPSRTGSNODE WHERE BANKCODE = ?";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1、加载驱动Oracle的jdbc驱动包
            Class.forName(driver);

            //3、建立连接 ：制定连接到哪里去jdbc:oracle:thin:  ip地址 : 端口号 : <数据库名>
            connection = DriverManager.getConnection(url, username, password);

            //4、创建statement对象，便于执行静态sql语句
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bankCode);
            resultSet = ps.executeQuery();


            while (resultSet.next()) {
                String bankName = resultSet.getString(1);
                System.out.println(bankName);
                return bankName;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //7、关闭连接
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        oracle("103121035884");
    }



}
