package com.example.a111111.diancan.ctd.inside;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class timeCal {
    public timeCal() {
    }

    public int subtract_date(String DATE1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentTime = df.format(date);
        System.out.println(currentTime);

        try {

            Date begin = df.parse(DATE1);
            Date end = df.parse(currentTime);
            // 得到微秒级别的差值
            long diff = end.getTime() - begin.getTime();
            // 将级别提升到天
            long minutes = diff / (1000 * 60);
            return (int) minutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
