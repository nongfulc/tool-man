package cn.com.cworks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @description:
 * @author: luchao
 * @date: 2020/12/17
 */
public class TimeTransfer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        while (true) {
            System.out.println("Please choose: (1 date to timestamp===2 timestamp to date ===q/Q to quit)");
            String input = scanner.nextLine();
            if ("1".equalsIgnoreCase(input)) {
                System.out.println("Please enter your timestamp: (q/Q for quit)");
                input = scanner.nextLine();
                if ("q".equalsIgnoreCase(input)) {
                    System.out.println("Thanks for using. Have a nice day, Sir. :)");
                    break;
                }
                try {
                    long timestamp = Long.parseLong(input);
                    date.setTime(timestamp);
                    System.out.println(sdf.format(date));
                } catch (NumberFormatException e) {
                    System.out.println("WTF? Please check your input...");
                }
            } else if ("2".equalsIgnoreCase(input)) {
                System.out.println("Please enter your date (like: 2020/12/12): (q/Q for quit)");
                input = scanner.nextLine();
                if ("q".equalsIgnoreCase(input)) {
                    System.out.println("Thanks for using. Have a nice day, Sir. :)");
                    break;
                }
                try {
                    date = sdf1.parse(input);
                    System.out.println(date.getTime());
                } catch (ParseException e) {
                    System.out.println("WTF? Please check your input...");
                }
            } else if ("q".equalsIgnoreCase(input)) {
                System.out.println("Thanks for using. Have a nice day, Sir. :)");
                break;
            } else {
                System.out.println("Sir, please be serious...");
            }

        }
    }

}
