package com.selegant.xxljob.util;

public class CronUtil {

    static final String xin = "*";
    static final String wenhao = "?";
    static final String dao = "-";
    static final String mei = "/";
    static final String huo = ",";

    public static String descCorn(String cronExp) {
        String[] tmpCorns = cronExp.split(" ");
        StringBuffer sBuffer = new StringBuffer();
        if (tmpCorns.length != 6) {
            throw new RuntimeException("请补全表达式,必须标准的cron表达式才能解析");
        }
        // 解析月
        descMonth(tmpCorns[4], sBuffer);
        // 解析周
        descWeek(tmpCorns[5], sBuffer);

        // 解析日
        descDay(tmpCorns[3], sBuffer);

        // 解析时
        descHour(tmpCorns[2], sBuffer);

        // 解析分
        descMintue(tmpCorns[1], sBuffer);

        // 解析秒
        descSecond(tmpCorns[0], sBuffer);
        sBuffer.append(" 执行");
        return sBuffer.toString();

    }

    /**
     * 描述
     *
     * @param tmpCorns
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:01:09
     */
    private static void descSecond(String s, StringBuffer sBuffer) {
        String danwei = "秒";
        desc(s, sBuffer, danwei);
    }

    /**
     * 描述
     *
     * @param s
     * @param sBuffer
     * @param danwei
     * @author Norton Lai
     * @created 2019-2-27 下午5:16:19
     */
    private static void desc(String s, StringBuffer sBuffer, String danwei) {
        if (s.equals("1/1")) {
            s = "*";
        }
        if (s.equals("0/0")) {
            s = "0";
        }
        if (xin.equals(s)) {
            sBuffer.append("每" + danwei);
            return;
        }
        if (wenhao.equals(s)) {
            return;
        }
        if (s.contains(huo)) {
            String[] arr = s.split(huo);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length() != 0) {
                    sBuffer.append("第" + arr[i] + danwei + "和");
                }
            }
            sBuffer.deleteCharAt(sBuffer.length() - 1);
            sBuffer.append("的");
            return;
        }
        if (s.contains(dao)) {
            String[] arr = s.split(dao);
            if (arr.length != 2) {
                throw new RuntimeException("表达式错误" + s);
            }
            sBuffer.append("从第" + arr[0] + danwei + "到第" + arr[1] + danwei + "每" + danwei);
            sBuffer.append("的");
            return;
        }

        if (s.contains(mei)) {
            String[] arr = s.split(mei);
            if (arr.length != 2) {
                throw new RuntimeException("表达式错误" + s);
            }
            if (arr[0].equals(arr[1]) || arr[0].equals("0")) {
                sBuffer.append("每" + arr[1] + danwei);
            } else {
                sBuffer.append("每" + arr[1] + danwei + "的第" + arr[0] + danwei);
            }
            return;
        }
        sBuffer.append("第" + s + danwei);
    }

    /**
     * 描述
     *
     * @param tmpCorns
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:01:00
     */
    private static void descMintue(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "分钟");
    }

    /**
     * 描述
     *
     * @param tmpCorns
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:50
     */
    private static void descHour(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "小时");
    }

    /**
     * 描述
     *
     * @param tmpCorns
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:39
     */
    private static void descDay(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "天");
    }

    /**
     * 描述
     *
     * @param tmpCorns
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:30
     */
    private static void descWeek(String s, StringBuffer sBuffer) {
        //不解释 太麻烦
    }
//    private static String turnWeek(String week){
//        switch (week) {
//        case "1":
//            return"星期天";
//        case "2":
//            return"星期一";
//        case "3":
//            return"星期二";
//        case "4":
//            return"星期三";
//        case "5":
//            return"星期四";
//        case "6":
//            return"星期五";
//        case "7":
//            return"星期六";
//        default:
//            return null;
//        }
//    }

    /**
     * 描述
     *
     * @param tmpCorns
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:15
     */
    private static void descMonth(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "月");
    }

    // 测试方法
    public static void main(String[] args) {
//        String CRON_EXPRESSION = "0 0 0/2 * * ?";
//        String CRON_EXPRESSION = "0 0/5 * * * ?";
        String CRON_EXPRESSION = "0 0 5-8 * * ?";

        System.out.println(descCorn(CRON_EXPRESSION));
    }




}
