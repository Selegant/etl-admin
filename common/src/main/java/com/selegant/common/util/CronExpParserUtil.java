package com.selegant.common.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @author 像我这样的人本该
 */
public class CronExpParserUtil {
    public final static String[] CRON_TIME_CN = new String[] { "秒", "分", "点", "天", "月", "周", "年" };
    private final static Integer HOURS = 24;
    private final static Integer TIMESCALE = 60;

    /**
     * cron转中文.
     * @param cronExp 表达式
     * @return 中文
     */
    public static String translateToChinese(String cronExp) {
        return translateToChinese(cronExp, CRON_TIME_CN);
    }

    /**
     * 主要解析 斜杆 /：表示起始时间开始触发，然后每隔固定时间触发一次。 范围值+开始值 - 间隔值 = 范围内最后执行的值；
     * 例如在Hourss域使用3/4,则意味着从第3小时到24+3-4：23小时范围内，第3小时开始触发第一次，然后每隔4小时，即7，11，15，19，23小时等分别触发一次。
     * 例如在Minutes域使用5/20,则意味着从第5分钟到60+5-20：45分范围内，第5分钟开始触发第一次，然后每隔20分钟，即25，45分钟等分别触发一次。
     * 例如在Seconds域使用8/10,则意味着从第8秒到60+8-10：58秒范围内，第8秒开始触发第一次，然后每隔10秒，即18，28，38，48，58秒等分别触发一次。
     * * 对于 *：表示匹配该域的任意值。例如在Minutes域使用*, 即表示每分钟都会触发事件。 对于问号
     * ?：只能用在DayofMonth和DayofWeek两个域，其作用为不指定 对于
     * -：表示范围。例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次。直接进行拼接 对于逗号
     * ,：表示列出枚举值。例如在Minutes域使用5,20 ， 则意味着在5和20分每分钟触发一次。
     * 对于L：表示最后，只能出现在DayofWeek和DayofMonth域。 如果出现在DayofMonth域，只能使用L，表示当月最后一天
     * 如果在DayofWeek域 使用数字（1-7）或L（和7的作用一样表示每周的最后一天周六），比如数字"5"表示每周4， "7"或"L"表示每周六
     * 使用数字（1-7）结合L，表示当月最后一周的周几，比如"5L" 表示在最后的一周的周四； "3L" 表示最后一周的周二 对于#:
     * 用于确定每个月第几个星期几，只能出现在DayofMonth域。 例如 "4#2" 表示某月的第二个星期三（4表示星期三，2表示第二周）;
     * “6#3”表示本月第三周的星期五（6表示星期五，3表示第三周）; “2#1”表示本月第一周的星期一; “4#5”表示第五周的星期三。
     *
     * @param cronExp
     * @param cronTimeArr
     * @return
     */
    public static String translateToChinese(String cronExp, String[] cronTimeArr) {
        if (cronExp == null || cronExp.length() < 1) {
            return "cron表达式为空";
        }

        String[] tmpCorns = cronExp.split(" ");
        StringBuffer sBuffer = new StringBuffer();
        if (tmpCorns.length == 6 || tmpCorns.length == 7) {
            if (tmpCorns.length == 7) {
                // 解析年 Year
                String year = tmpCorns[6];
                if ((!year.equals("*") && !year.equals("?"))) {
                    sBuffer.append(year).append(cronTimeArr[6]);
                }
            }
            // 解析月 Month 主要解析 /
            String months = tmpCorns[4];
            if (!months.equals("*") && !months.equals("?")) {
                if (months.contains("/")) {
                    sBuffer.append("从").append(months.split("/")[0]).append("号开始").append(",每")
                            .append(months.split("/")[1]).append(cronTimeArr[4]);
                } else {
                    if(sBuffer.toString().contains("-")) {
                        sBuffer.append("每年");
                    }
                    sBuffer.append(months).append(cronTimeArr[4]);
                }
            }

            // 解析周 DayofWeek 主要解析 , - 1~7/L 1L~7L
            String dayofWeek = tmpCorns[5];
            if (!dayofWeek.equals("*") && !dayofWeek.equals("?")) {
                if (dayofWeek.contains(",")) {// 多个数字，逗号隔开
                    sBuffer.append("每周的第").append(dayofWeek).append(cronTimeArr[3]);
                } else if (dayofWeek.contains("L") && dayofWeek.length() > 1) {// 1L-7L
                    String weekNum = dayofWeek.split("L")[0];
                    String weekName = judgeWeek(weekNum);
                    sBuffer.append("每月的最后一周的");
                    sBuffer.append(weekName);
                } else if (dayofWeek.contains("-")) {
                    String[] splitWeek = dayofWeek.split("-");
                    String weekOne = judgeWeek(splitWeek[0]);
                    String weekTwo = judgeWeek(splitWeek[1]);
                    sBuffer.append("每周的").append(weekOne).append("到").append(weekTwo);
                } else { // 1-7/L
                    if ("L".equals(dayofWeek)) { // L 转换为7，便于识别
                        dayofWeek = "7";
//						dayofWeek = "SAT";
                    }
//					int weekNums = Integer.parseInt(dayofWeek);
//					if (weekNums < 0 || weekNums > 7) {
//						return "cron表达式有误，dayofWeek数字应为1-7";
//					}
                    sBuffer.append("每周的");
                    String weekName = judgeWeek(dayofWeek);
                    sBuffer.append(weekName);
                }
            }
            // 解析日 days -- DayofMonth 需要解析的 / # L W
            // * “6#3”表示本月第三周的星期五（6表示星期五，3表示第三周）;
            // * “2#1”表示本月第一周的星期一;
            // * “4#5”表示第五周的星期三。
            String days = tmpCorns[3];
            if (!days.equals("?") && !days.equals("*")) {
                if (days.contains("/")) {
                    sBuffer.append("每周从第").append(days.split("/")[0]).append("天开始").append(",每")
                            .append(days.split("/")[1]).append(cronTimeArr[3]);
                } else if ("L".equals(days)) { // 处理L 每月的最后一天
                    sBuffer.append("每月最后一天");
                } else if ("W".equals(days)) { // 处理W 暂时不懂怎么处理
                    sBuffer.append(days);
                } else if (days.contains("#")) {
                    String[] splitDays = days.split("#");
                    String weekNum = splitDays[0]; // 前面数字表示周几
                    String weekOfMonth = splitDays[1]; // 后面的数字表示第几周 范围1-4 一个月最多4周
                    String weekName = judgeWeek(weekNum);
                    sBuffer.append("每月第").append(weekOfMonth).append(cronTimeArr[5]).append(weekName);
                } else {
//					sBuffer.append("每月").append(days).append(cronTimeArr[3]);
                    sBuffer.append("每月").append(days).append("号");
                }
            } else {
                if ((sBuffer.toString().length() == 0 || tmpCorns.length == 7) && !sBuffer.toString().contains("星期")) { // 前面没有内容的话，拼接下
                    sBuffer.append("每").append(cronTimeArr[3]);
                }
            }
            // 解析时 Hours 主要解析 /
            String hours = tmpCorns[2];
            if (!hours.equals("*")) {
                if (hours.contains("/")) {
                    // sBuffer.append("内，从").append(hours.split("/")[0]).append("时开始").append(",每")
                    // .append(hours.split("/")[1]).append(cronTimeArr[2]);
                    sBuffer.append(appendGapInfo(hours, HOURS, 2));
                } else {
                    if (!(sBuffer.toString().length() > 0)) { // 对于 , 的情况，直接拼接
                        sBuffer.append("每天").append(hours).append(cronTimeArr[2]);
                    } else {
                        sBuffer.append(hours).append(cronTimeArr[2]);
                    }
                }
            }

            // 解析分 Minutes 主要解析 /
            String minutes = tmpCorns[1];
            if (!minutes.equals("*")) {
                if (minutes.contains("/")) {
                    // sBuffer.append("内，从第").append(minutes.split("/")[0]).append("分开始").append(",每")
                    // .append(minutes.split("/")[1]).append(cronTimeArr[1]);
                    sBuffer.append(appendGapInfo(minutes, TIMESCALE, 1));
                } else if (minutes.equals("0")) {
                } else if (minutes.contains("-")) {
                    String[] splitMinute = minutes.split("-");
                    sBuffer.append(splitMinute[0]).append(cronTimeArr[1]).append("到").append(splitMinute[1])
                            .append(cronTimeArr[1]).append("每分钟");
                } else {
                    sBuffer.append(minutes).append(cronTimeArr[1]);
                }
            }

            // 解析秒 Seconds 主要解析 /
            String seconds = tmpCorns[0];
            if (!seconds.equals("*")) {
                if (seconds.contains("/")) {
                    // sBuffer.append("内，从第").append(seconds.split("/")[0]).append("秒开始").append(",每")
                    // .append(seconds.split("/")[0]).append(cronTimeArr[0]);
                    sBuffer.append(appendGapInfo(seconds, TIMESCALE, 0));
                } else if (!seconds.equals("0")) {
                    sBuffer.append(seconds).append(cronTimeArr[0]);
                }
            }

            if (sBuffer.toString().length() > 0) {
                sBuffer.append("执行");
            } else {
                sBuffer.append("表达式中文转换异常");
            }
        }
        return sBuffer.toString();
    }

    public static String judgeWeek(String weekNum) {
        String weekName = WeekEnum.matchNameCn(String.valueOf(weekNum));
//		int weekNums = Integer.parseInt(weekNum);
//		if (weekNums < 0 || weekNums > 7) {
//			return "cron表达式有误，dayofWeek 数字应为1-7";
//		}
        return StringUtils.isNotBlank(weekName) ? weekName : String.valueOf(weekNum);
    }

    private static String appendGapInfo(String time, int rangeNum, int index) {
        StringBuffer sBufferTemp = new StringBuffer();
        String[] splitTime = time.split("/");
        String startNum = splitTime[0];
        String gapNum = splitTime[1];
        int endNum = rangeNum + Integer.parseInt(startNum) - Integer.parseInt(gapNum);
        String endStr = String.valueOf(endNum);
        String timeUnit = CRON_TIME_CN[index];
        String splitTimeUnit = CRON_TIME_CN[index];
        if (index == 1) {
            splitTimeUnit = "分钟";
        } else if (index == 2) {
            splitTimeUnit = "小时";
        }
        sBufferTemp.append("从").append(startNum).append(timeUnit).append("开始").append("到").append(endStr)
                .append(timeUnit).append("范围内").append(",每隔").append(gapNum).append(splitTimeUnit);
        return sBufferTemp.toString();
    }

    // 测试方法

    public static void main(String[] args) {
        testCronExpression();
    }

    private static void testCronExpression() {
        String cronExpression = "";
        // 0 0 2 1 * ? * 表示在每月的1日的凌晨2点
        cronExpression = "0 0 2 1 * ? *";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 0 15 10 ? * 2-6 表示周一到周五每天上午10:15执行作业
        cronExpression = "0 15 10 ? * 2-6";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 每天上午10点，下午2点，4点
        cronExpression = "0 0 10,14,16 * * ?";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 在每天下午2点到下午2:05期间的每1分钟触发

        cronExpression = "0 5 14-16 * * ?";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 每月最后一日的上午10:15触发
        cronExpression = "0 15 10 L * ? ";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 每月的第三个星期五上午10:15触发
        cronExpression = "0 15 10 ? * 6";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 2002 每天中午12点触发
        cronExpression = "0 0 12 * * ? 2002";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 2002-2005年 每天中午12点触发
        cronExpression = "0 15 10 ? * ? 2002-2005";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 0 15 10 ? * 6L 每月的最后一个星期五上午10:15触发
        cronExpression = "0 15 10 ? * 6L";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        cronExpression = "0 15 10 6#4 * ?";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 0 15 10 * * ? * 每天上午10:15触发
        cronExpression = "0 15 10 * * ? *";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 0 15 10 * * ? 2005 2005年的每天上午10:15触发
        cronExpression = "0 15 10 * * ? 2005";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 0 * 14 * * ? 在每天下午2点到下午2:59期间的每1分钟触发
        cronExpression = "0 * 14 * * ?";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // 0 0/5 14 * * ? 在每天下午2点到下午2:55期间的每5分钟触发
        cronExpression = "0 0/5 14 * * ?";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        // "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
        cronExpression = "0 0/5 14,18 * * ?";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        cronExpression = "8/10 0/5 3/4 * * ?";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

        cronExpression = "0 0 7 ? 1-5 WED 2021";
        System.out.println(cronExpression);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));
    }

    public enum WeekEnum {
        SECONDS("1", "星期天", "Sunday "),
        MONDAY("2", "星期一", "Monday "),
        TUESDAY("3", "星期二", "Tuesday "),
        WEDNESDAY("4","星期三", "Wednesday "),
        THURSDAY("5", "星期四","Thursday "),
        FRIDAY("6", "星期五", "Friday "),
        SATURDAY("7", "星期六", "Saturday "),

        SECONDS_EN("SUN", "星期天", "Sunday "),
        MONDAY_EN("MON", "星期一", "Monday "),
        TUESDAY_EN("TUE", "星期二", "Tuesday "),
        WEDNESDAY_EN("WED","星期三", "Wednesday "),
        THURSDAY_EN("THU", "星期四","Thursday "),
        FRIDAY_EN("FRI", "星期五", "Friday "),
        SATURDAY_EN("SAT", "星期六", "Saturday ");

        private String key;
        private String nameCn;
        private String nameEn;

        WeekEnum(String key, String nameCn, String nameEn) {
            this.key = key;
            this.nameCn = nameCn;
            this.nameEn = nameEn;
        }

        public static String matchNameCn(String code) {
            for (WeekEnum m : WeekEnum.values()) {
                if (m.getKey().equals(code)) {
                    return m.getNameCn();
                }
            }
            return "";
        }

        public static String matchNameEn(String code) {
            for (WeekEnum m : WeekEnum.values()) {
                if (m.getKey().equals(code)) {
                    return m.getNameEn();
                }
            }
            return "";
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {

            this.nameEn = nameEn;
        }
    }
}
