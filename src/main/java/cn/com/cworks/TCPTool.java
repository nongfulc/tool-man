package cn.com.cworks;

import java.io.*;
import java.net.Socket;

public class TCPTool {

    public static String send(String ip, Integer port, String msg) {
        try (
                Socket socket = new Socket(ip, port);
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ) {
            bw.write(msg);
            bw.flush();
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
