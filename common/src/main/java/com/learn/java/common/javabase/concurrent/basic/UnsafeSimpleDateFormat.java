package com.learn.java.common.javabase.concurrent.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * simpleDateFormat非线程安全测试
 */
public class UnsafeSimpleDateFormat {

    public static class DateFormatThread implements Runnable {

        private SimpleDateFormat sdf;

        private String dateStr;

        private DateFormatThread(SimpleDateFormat sdf, String dateStr) {
            this.sdf = sdf;
            this.dateStr = dateStr;
        }


        @Override
        public void run() {
            try {
                Date date = sdf.parse(dateStr);
                String newDateStr = sdf.format(date);
                System.out.println(Thread.currentThread().getName() + ",parseDate:" + newDateStr);
                if (!dateStr.equals(newDateStr)) {
                    System.out.println(Thread.currentThread().getName() + ",日期转换异常，dateStr:" + dateStr + ",newDateStr:" + newDateStr);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String[] dateStringArray = new String[]{"2000-01-01", "2000-01-02",
                "2000-01-03", "2000-01-04", "2000-01-05", "2000-01-06",
                "2000-01-07", "2000-01-08", "2000-01-09", "2000-01-10"};

        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(new DateFormatThread(sdf, dateStringArray[i]));
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }

    }
}
