package com.duojiala.mikeboot.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 时间工具类
 */
public class DateUtil {

    public static final String HM = "HH:mm";
    public static final String YM = "yyyyMM";
    public static final String Y_M = "yyyy-MM";
    public static final String YMD = "yyyyMMdd";
    public static final String MD_CN = "MM月dd日";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String _YMDHMS = "yyyyMMddHHmmss";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";
    public static final String YMDHMSS = "yyyyMMddHHmmssSSS";
    public static final String HMS = "HH:mm:ss";
    public static final String START_CRITICAL_POINT = " 00:00:00";
    public static final String END_CRITICAL_POINT = " 23:59:59";
    public static final String LINK_HMS = "HHmmss";
    public static final String ZERO_SECOND = ":00";
    public static final String MS = "mm:ss";
    public static final String CYM = "yyyy年MM月";
    public static final String MDHM = "MM-dd HH:mm";
    public static final String YEAR = "yyyy";
    public static final String CYMD = "yyyy年MM月dd日";
    public static final String CMD = "MM月dd日";
    public static final String CYMDMS = "yyyy年MM月dd日 HH:mm";
    public static final String CYMDMSS = "yyyy年MM月dd日 HH:mm:ss";
    public static final String _YMDHMSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static Date parse(String dateStr, String pattern) {

        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String format(Date date, String pattern) {

        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 一段时间范围的所有日期
     *
     * @param dBegin 开始
     * @param dEnd   结束
     * @return 所有日期
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 判断此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 一段时间范围的所有日期
     * @param startDate 开始
     * @param endDate   结束
     * @return 所有日期
     */
    public static List<String> findDates(String startDate, String endDate, String formatter) {
        List<String> lDate = new ArrayList<>();
        Date dBegin = StringTool.formatDate(startDate, formatter);
        Date dEnd = StringTool.formatDate(endDate, formatter);
        lDate.add(startDate);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 判断此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(StringTool.formatDate(calBegin.getTime(), formatter));
        }
        return lDate;
    }

    /**
     * 某一个日期是星期几["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"] = [0-6]
     * @param date 日期
     */
    public static int dateToWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dateOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dateOfWeek == 0) {
            dateOfWeek = 7;
        }
        return dateOfWeek;
    }

    /**
     * 获取某个日期所在月份的第一天和最后一天
     * @param date 日期
     * @return [第一天, 最后一天]
     */
    public static Date[] dateMonthFirstAndEnd(Date date) {
        Date[] dates = new Date[2];

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        dates[0] = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
        dates[1] = cal2.getTime();

        return dates;
    }

    /**
     * 获取某个时间所在日期的临界
     * @param date 日期
     * @return [改天开始时刻, 改天结束时刻]
     */
    public static Date[] dateDayFirstAndEnd(Date date) {
        Date[] dates = new Date[2];
        String ymd = format(date, DateUtil.Y_M_D);
        dates[0] = parse(ymd + DateUtil.START_CRITICAL_POINT, DateUtil.YMDHMS);
        dates[1] = parse(ymd + DateUtil.END_CRITICAL_POINT, DateUtil.YMDHMS);
        return dates;
    }

    /**
     * 获得指定日期的前一天
     */
    public static Date getLastDay(Date date) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        return c.getTime();
    }

    /**
     * 获得指定日期的后一天
     */
    public static Date getAddDay(Date date) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        return c.getTime();
    }


    /**
     * 获取某个时间的前min分钟
     * @param min 前多少分钟
     */
    public static Date getDateBeforeMin(Date date, int min) {
        return new Date(date.getTime() - (min * 60 * 1000));
    }

    /**
     * 获得指定日期的前一月
     */
    public static Date getLastMonth(Date date) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month - 1);
        return c.getTime();
    }

    /**
     * 获得指定日期的后一月
     */
    public static Date getAddMonth(Date date) {//可以用new Date().toLocalString()传递参数
        return DateUtil.add(date, Calendar.MONTH, 1);
    }

    /**
     * 获得指定日期的前一年
     */
    public static Date getLastYear(Date date) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.YEAR);
        c.set(Calendar.YEAR, day - 1);

        return c.getTime();
    }

    /**
     * 比较某个时间 是否在timeArray 范围内
     * @param timeRange 开始时间，结束时间
     */
    public static boolean isInDateRange(Date time, String[] timeRange, String pattern) {
        Date start = StringTool.formatDate(timeRange[0], pattern);
        Date end = StringTool.formatDate(timeRange[1], pattern);

        assert start != null;
        assert end != null;
        if (start.after(end) || start.equals(end)) {
            /**
             * 直接用时间判断
             */
            return time.getTime() >= start.getTime() || time.getTime() <= end.getTime();

        } else {
            return time.getTime() >= start.getTime() && time.getTime() <= end.getTime();
        }
    }

    /**
     * 比较某个时间 是否在timeArray 范围内
     * @param timeRange 开始时间，结束时间
     */
    public static boolean isInDateRange(Date time, List<String> timeRange, String pattern) {
        Date start = StringTool.formatDate(timeRange.get(0), pattern);
        Date end = StringTool.formatDate(timeRange.get(1), pattern);

        assert start != null;
        assert end != null;
        if (start.after(end) || start.equals(end)) {
            /**
             * 直接用时间判断
             */
            return time.getTime() >= start.getTime() || time.getTime() <= end.getTime();

        } else {
            return time.getTime() >= start.getTime() && time.getTime() <= end.getTime();
        }
    }

    /**
     * 比较某个时间 是否在timeArray 范围内
     * @param timeRange 开始时间，结束时间
     */
    public static boolean isInDateRange(Date time, Date[] timeRange) {
        Date start = timeRange[0];
        Date end = timeRange[1];

        if (start.after(end) || start.equals(end)) {
            /**
             * 直接用时间判断
             */
            return time.getTime() >= start.getTime() || time.getTime() <= end.getTime();

        } else {
            return time.getTime() >= start.getTime() && time.getTime() <= end.getTime();
        }
    }

    /**
     * 判断日期是否在日期范围内
     */
    public static boolean isInDayRange(Date time, Date[] timeRange) {
        String formatter = "yyyyMMdd";
        String today = StringTool.formatDate(time, formatter);
        String start = StringTool.formatDate(timeRange[0], formatter);
        String end = StringTool.formatDate(timeRange[1], formatter);
        return (Integer.parseInt(today) >= Integer.parseInt(start) && Integer.parseInt(today) <= Integer.parseInt(end));
    }

    /**
     * 获取两个日期相差的年月日
     */
    public static long[] dateDiff(String startTime, String endTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate startDate = LocalDate.parse(startTime, formatter);
        LocalDate endDate = LocalDate.parse(endTime, formatter);
        long y = ChronoUnit.YEARS.between(startDate, endDate);
        long m = ChronoUnit.MONTHS.between(startDate, endDate);
        long d = ChronoUnit.DAYS.between(startDate, endDate);
        return new long[]{y, m, d};
    }

    /**
     * 获取两个日期相差的年月日
     */
    public static long[] dateDiff(Date startTime, Date endTime) {
        LocalDate startDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long y = ChronoUnit.YEARS.between(startDate, endDate);
        long m = ChronoUnit.MONTHS.between(startDate, endDate);
        long d = ChronoUnit.DAYS.between(startDate, endDate);
        return new long[]{y, m, d};
    }

    /**
     * 获取两个两个时间相差的时间
     */
    public static long[] timeDiff(String startTime, String endTime, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
            return new long[]{day, hour, min, sec};

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new long[]{};
    }

    /**
     * 获取两个两个时间相差的时间
     */
    public static long[] timeDiff(Date startTime, Date endTime) {
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        diff = endTime.getTime() - startTime.getTime();
        day = diff / nd;// 计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少小时
        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        sec = diff % nd % nh % nm / ns;// 计算差多少秒
        return new long[]{day, hour, min, sec};
    }

    /**
     * 获取某个月份一共有多少天
     * @param month 某月
     */
    public static int getDaysOfMonth(Date month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 取出一个月内的完整周（周日在这个月内的）
     * @param month 月份 yyyyMM
     */
    public static List<Date[]> listMonthWeek(String month) {
        List<Date[]> weeks = new ArrayList<>();
        // 找出本月的所有周日
        Calendar c = Calendar.getInstance();
        Date date = StringTool.formatDate(month, "yyyyMM");
        if (date != null) {
            c.setTime(date);
            int m = c.get(Calendar.MONTH);
            do {
                int day = c.get(Calendar.DAY_OF_WEEK);
                if (day == Calendar.SUNDAY) {
                    Date sundayDate = c.getTime();
                    Calendar weekC = Calendar.getInstance();
                    weekC.setTime(sundayDate);
                    weekC.add(Calendar.DAY_OF_YEAR, -6);
                    Date mondayDate = weekC.getTime();
                    weeks.add(new Date[]{mondayDate, sundayDate});
//                    log.info("每周：{} ~ {}", StringTool.formatDate(mondayDate, "yyyyMMdd"), StringTool.formatDate(sundayDate, "yyyyMMdd"));
                }
                c.add(Calendar.DAY_OF_YEAR, 1);
            } while (c.get(Calendar.MONTH) == m);
        }

        return weeks;
    }

    /**
     * 根据星期天的日期 获取那一周星期一的日期
     */
    public static String getMondayBySunday(String sundayDate, String formatter) {
        Calendar weekC = Calendar.getInstance();
        weekC.setTime(Objects.requireNonNull(StringTool.formatDate(sundayDate, formatter)));
        weekC.add(Calendar.DAY_OF_YEAR, -6);
        Date mondayDate = weekC.getTime();
        return StringTool.formatDate(mondayDate, formatter);
    }

    /**
     * 判断日期1是否在日期2前(或相同)
     * @param date1     日期1
     * @param date2     日期2
     * @param formatter 日期格式
     */
    public static boolean isBeforeDate(String date1, String date2, String formatter) {
        DateFormat df = new SimpleDateFormat(formatter);
        Date dt1 = null;
        try {
            dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() < dt2.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

    /**
     * 判断日期1是否等于日期2
     * @param date1     日期1
     * @param date2     日期2
     * @param formatter 日期格式
     */
    public static boolean isEqualsDate(String date1, String date2, String formatter) {
        DateFormat df = new SimpleDateFormat(formatter);
        Date dt1 = null;
        try {
            dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() == dt2.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

    /**
     * 校验开始时间是否大于结束时间
     * @param startTime 开始时间字符串
     * @param endTime   结束时间字符串
     * @param format    格式
     */
    public static boolean checkTimeRange(String startTime, String endTime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date startDate = null;

            Date endDate = null;

            startDate = sdf.parse(startTime);

            endDate = sdf.parse(endTime);

            //startDate是否在endTime之后，为true 表示 startTime>endTime
            return startDate.after(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获得指定日期的前七天
     *
     * @param date
     * @return
     */
    public static Date getLastSevenDay(Date date) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 7);

        return c.getTime();
    }

    /**
     * 判断时间点是否在两个时间点的范围内
     * @param time       时间点
     * @param startTime  开始时间点
     * @param endTime    结束时间点
     * @param formatters 日期格式
     */
    public static boolean timeInRange(String time, String startTime, String endTime, String... formatters) {
        // 判断开始和结束的两个时间点是否是同一天的，如果结束的时间点比开始的时间点更小，则视为第二天
        String formatter = "HH:mm:ss";
        if (formatters.length > 0) {
            formatter = formatters[0];
        }
        try {
            Date startD = StringTool.formatDate(startTime, formatter);
            Date endD = StringTool.formatDate(endTime, formatter);
            Date timeD = StringTool.formatDate(time, formatter);
            assert endD != null;
            if (Objects.requireNonNull(endD).before(startD)) {
                // 如果结束的时间点比开始的时间点更小，则视为第二天,分为两段 => [start,00:00:00] || [00:00:00,end]
                Date zeroD_1 = StringTool.formatDate("23:59:59", formatter);
                Date zeroD = StringTool.formatDate("00:00:00", formatter);
                assert timeD != null;
                return (timeD.compareTo(startD) >= 0 && timeD.before(zeroD_1)) || (timeD.compareTo(zeroD) >= 0 && timeD.before(endD));
            } else {
                assert timeD != null;
                return timeD.compareTo(startD) >= 0 && timeD.before(endD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 工具类，实现公农历互转
     */
    public static class LunarCalendar {

        /**
         * 支持转换的最小农历年份
         */
        public static final int MIN_YEAR = 1900;
        /**
         * 支持转换的最大农历年份
         */
        public static final int MAX_YEAR = 2099;

        /**
         * 公历每月前的天数
         */
        private static final int[] DAYS_BEFORE_MONTH = {0, 31, 59, 90, 120, 151, 181,
                212, 243, 273, 304, 334, 365};

        /**
         * 用来表示1900年到2099年间农历年份的相关信息，共24位bit的16进制表示，其中：
         * 1. 前4位表示该年闰哪个月；
         * 2. 5-17位表示农历年份13个月的大小月分布，0表示小，1表示大；
         * 3. 最后7位表示农历年首（正月初一）对应的公历日期。
         * <p>
         * 以2014年的数据0x955ABF为例说明：
         * 1001 0101 0101 1010 1011 1111
         * 闰九月                                  农历正月初一对应公历1月31号
         */
        private static final int[] LUNAR_INFO = {
                0x84B6BF,/*1900*/
                0x04AE53, 0x0A5748, 0x5526BD, 0x0D2650, 0x0D9544, 0x46AAB9, 0x056A4D, 0x09AD42, 0x24AEB6, 0x04AE4A,/*1901-1910*/
                0x6A4DBE, 0x0A4D52, 0x0D2546, 0x5D52BA, 0x0B544E, 0x0D6A43, 0x296D37, 0x095B4B, 0x749BC1, 0x049754,/*1911-1920*/
                0x0A4B48, 0x5B25BC, 0x06A550, 0x06D445, 0x4ADAB8, 0x02B64D, 0x095742, 0x2497B7, 0x04974A, 0x664B3E,/*1921-1930*/
                0x0D4A51, 0x0EA546, 0x56D4BA, 0x05AD4E, 0x02B644, 0x393738, 0x092E4B, 0x7C96BF, 0x0C9553, 0x0D4A48,/*1931-1940*/
                0x6DA53B, 0x0B554F, 0x056A45, 0x4AADB9, 0x025D4D, 0x092D42, 0x2C95B6, 0x0A954A, 0x7B4ABD, 0x06CA51,/*1941-1950*/
                0x0B5546, 0x555ABB, 0x04DA4E, 0x0A5B43, 0x352BB8, 0x052B4C, 0x8A953F, 0x0E9552, 0x06AA48, 0x6AD53C,/*1951-1960*/
                0x0AB54F, 0x04B645, 0x4A5739, 0x0A574D, 0x052642, 0x3E9335, 0x0D9549, 0x75AABE, 0x056A51, 0x096D46,/*1961-1970*/
                0x54AEBB, 0x04AD4F, 0x0A4D43, 0x4D26B7, 0x0D254B, 0x8D52BF, 0x0B5452, 0x0B6A47, 0x696D3C, 0x095B50,/*1971-1980*/
                0x049B45, 0x4A4BB9, 0x0A4B4D, 0xAB25C2, 0x06A554, 0x06D449, 0x6ADA3D, 0x0AB651, 0x095746, 0x5497BB,/*1981-1990*/
                0x04974F, 0x064B44, 0x36A537, 0x0EA54A, 0x86B2BF, 0x05AC53, 0x0AB647, 0x5936BC, 0x092E50, 0x0C9645,/*1991-2000*/
                0x4D4AB8, 0x0D4A4C, 0x0DA541, 0x25AAB6, 0x056A49, 0x7AADBD, 0x025D52, 0x092D47, 0x5C95BA, 0x0A954E,/*2001-2010*/
                0x0B4A43, 0x4B5537, 0x0AD54A, 0x955ABF, 0x04BA53, 0x0A5B48, 0x652BBC, 0x052B50, 0x0A9345, 0x474AB9,/*2011-2020*/
                0x06AA4C, 0x0AD541, 0x24DAB6, 0x04B64A, 0x6a573D, 0x0A4E51, 0x0D2646, 0x5E933A, 0x0D534D, 0x05AA43,/*2021-2030*/
                0x36B537, 0x096D4B, 0xB4AEBF, 0x04AD53, 0x0A4D48, 0x6D25BC, 0x0D254F, 0x0D5244, 0x5DAA38, 0x0B5A4C,/*2031-2040*/
                0x056D41, 0x24ADB6, 0x049B4A, 0x7A4BBE, 0x0A4B51, 0x0AA546, 0x5B52BA, 0x06D24E, 0x0ADA42, 0x355B37,/*2041-2050*/
                0x09374B, 0x8497C1, 0x049753, 0x064B48, 0x66A53C, 0x0EA54F, 0x06AA44, 0x4AB638, 0x0AAE4C, 0x092E42,/*2051-2060*/
                0x3C9735, 0x0C9649, 0x7D4ABD, 0x0D4A51, 0x0DA545, 0x55AABA, 0x056A4E, 0x0A6D43, 0x452EB7, 0x052D4B,/*2061-2070*/
                0x8A95BF, 0x0A9553, 0x0B4A47, 0x6B553B, 0x0AD54F, 0x055A45, 0x4A5D38, 0x0A5B4C, 0x052B42, 0x3A93B6,/*2071-2080*/
                0x069349, 0x7729BD, 0x06AA51, 0x0AD546, 0x54DABA, 0x04B64E, 0x0A5743, 0x452738, 0x0D264A, 0x8E933E,/*2081-2090*/
                0x0D5252, 0x0DAA47, 0x66B53B, 0x056D4F, 0x04AE45, 0x4A4EB9, 0x0A4D4C, 0x0D1541, 0x2D92B5          /*2091-2099*/
        };

        /**
         * 将农历日期转换为公历日期
         *
         * @param year        农历年份
         * @param month       农历月
         * @param monthDay    农历日
         * @param isLeapMonth 该月是否是闰月
         * @return 农历日期对应的公历日期，year0, month1, day2.
         */
        public static int[] lunarToSolar(int year, int month, int monthDay,
                                         boolean isLeapMonth) {
            int dayOffset;
            int leapMonth;
            int i;

            if (year < MIN_YEAR || year > MAX_YEAR || month < 1 || month > 12
                    || monthDay < 1 || monthDay > 30) {
                throw new IllegalArgumentException(
                        "Illegal lunar date, must be like that:\n\t" +
                                "year : 1900~2099\n\t" +
                                "month : 1~12\n\t" +
                                "day : 1~30");
            }

            dayOffset = (LUNAR_INFO[year - MIN_YEAR] & 0x001F) - 1;

            if (((LUNAR_INFO[year - MIN_YEAR] & 0x0060) >> 5) == 2)
                dayOffset += 31;

            for (i = 1; i < month; i++) {
                if ((LUNAR_INFO[year - MIN_YEAR] & (0x80000 >> (i - 1))) == 0)
                    dayOffset += 29;
                else
                    dayOffset += 30;
            }

            dayOffset += monthDay;
            leapMonth = (LUNAR_INFO[year - MIN_YEAR] & 0xf00000) >> 20;

            // 这一年有闰月
            if (leapMonth != 0) {
                if (month > leapMonth || (month == leapMonth && isLeapMonth)) {
                    if ((LUNAR_INFO[year - MIN_YEAR] & (0x80000 >> (month - 1))) == 0)
                        dayOffset += 29;
                    else
                        dayOffset += 30;
                }
            }

            if (dayOffset > 366 || (year % 4 != 0 && dayOffset > 365)) {
                year += 1;
                if (year % 4 == 1)
                    dayOffset -= 366;
                else
                    dayOffset -= 365;
            }

            int[] solarInfo = new int[3];
            for (i = 1; i < 13; i++) {
                int iPos = DAYS_BEFORE_MONTH[i];
                if (year % 4 == 0 && i > 2) {
                    iPos += 1;
                }

                if (year % 4 == 0 && i == 2 && iPos + 1 == dayOffset) {
                    solarInfo[1] = i;
                    solarInfo[2] = dayOffset - 31;
                    break;
                }

                if (iPos >= dayOffset) {
                    solarInfo[1] = i;
                    iPos = DAYS_BEFORE_MONTH[i - 1];
                    if (year % 4 == 0 && i > 2) {
                        iPos += 1;
                    }
                    if (dayOffset > iPos)
                        solarInfo[2] = dayOffset - iPos;
                    else if (dayOffset == iPos) {
                        if (year % 4 == 0 && i == 2)
                            solarInfo[2] = DAYS_BEFORE_MONTH[i] - DAYS_BEFORE_MONTH[i - 1] + 1;
                        else
                            solarInfo[2] = DAYS_BEFORE_MONTH[i] - DAYS_BEFORE_MONTH[i - 1];

                    } else
                        solarInfo[2] = dayOffset;
                    break;
                }
            }
            solarInfo[0] = year;

            return solarInfo;
        }

        /**
         * 将公历日期转换为农历日期，且标识是否是闰月
         *
         * @param year
         * @param month
         * @param monthDay
         * @return 返回公历日期对应的农历日期，year0，month1，day2，leap3
         */
        public static int[] solarToLunar(int year, int month, int monthDay) {
            int[] lunarDate = new int[4];
            Date baseDate = new GregorianCalendar(1900, 0, 31).getTime();
            Date objDate = new GregorianCalendar(year, month - 1, monthDay).getTime();
            int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000L);

            // 用offset减去每农历年的天数计算当天是农历第几天
            // iYear最终结果是农历的年份, offset是当年的第几天
            int iYear, daysOfYear = 0;
            for (iYear = MIN_YEAR; iYear <= MAX_YEAR && offset > 0; iYear++) {
                daysOfYear = daysInLunarYear(iYear);
                offset -= daysOfYear;
            }
            if (offset < 0) {
                offset += daysOfYear;
                iYear--;
            }

            // 农历年份
            lunarDate[0] = iYear;

            int leapMonth = leapMonth(iYear); // 闰哪个月,1-12
            boolean isLeap = false;
            // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
            int iMonth, daysOfMonth = 0;
            for (iMonth = 1; iMonth <= 13 && offset > 0; iMonth++) {
                daysOfMonth = daysInLunarMonth(iYear, iMonth);
                offset -= daysOfMonth;
            }
            // 当前月超过闰月，要校正
            if (leapMonth != 0 && iMonth > leapMonth) {
                --iMonth;

                if (iMonth == leapMonth) {
                    isLeap = true;
                }
            }
            // offset小于0时，也要校正
            if (offset < 0) {
                offset += daysOfMonth;
                --iMonth;
            }

            lunarDate[1] = iMonth;
            lunarDate[2] = offset + 1;
            lunarDate[3] = isLeap ? 1 : 0;

            return lunarDate;
        }

        /**
         * 传回农历year年month月的总天数
         *
         * @param year  要计算的年份
         * @param month 要计算的月
         * @return 传回天数
         */
        public static int daysInMonth(int year, int month) {
            return daysInMonth(year, month, false);
        }

        /**
         * 传回农历year年month月的总天数
         *
         * @param year  要计算的年份
         * @param month 要计算的月
         * @param leap  当月是否是闰月
         * @return 传回天数，如果闰月是错误的，返回0.
         */
        public static int daysInMonth(int year, int month, boolean leap) {
            int leapMonth = leapMonth(year);
            int offset = 0;

            // 如果本年有闰月且month大于闰月时，需要校正
            if (leapMonth != 0 && month > leapMonth) {
                offset = 1;
            }

            // 不考虑闰月
            if (!leap) {
                return daysInLunarMonth(year, month + offset);
            } else {
                // 传入的闰月是正确的月份
                if (leapMonth != 0 && leapMonth == month) {
                    return daysInLunarMonth(year, month + 1);
                }
            }

            return 0;
        }

        /**
         * 传回农历 year年的总天数
         *
         * @param year 将要计算的年份
         * @return 返回传入年份的总天数
         */
        private static int daysInLunarYear(int year) {
            int i, sum = 348;
            if (leapMonth(year) != 0) {
                sum = 377;
            }
            int monthInfo = LUNAR_INFO[year - MIN_YEAR] & 0x0FFF80;
            for (i = 0x80000; i > 0x7; i >>= 1) {
                if ((monthInfo & i) != 0)
                    sum += 1;
            }
            return sum;
        }

        /**
         * 传回农历 year年month月的总天数，总共有13个月包括闰月
         *
         * @param year  将要计算的年份
         * @param month 将要计算的月份
         * @return 传回农历 year年month月的总天数
         */
        private static int daysInLunarMonth(int year, int month) {
            if ((LUNAR_INFO[year - MIN_YEAR] & (0x100000 >> month)) == 0)
                return 29;
            else
                return 30;
        }

        /**
         * 传回农历 year年闰哪个月 1-12 , 没闰传回 0
         *
         * @param year 将要计算的年份
         * @return 传回农历 year年闰哪个月1-12, 没闰传回 0
         */
        private static int leapMonth(int year) {
            return (int) ((LUNAR_INFO[year - MIN_YEAR] & 0xF00000)) >> 20;
        }

    }

    /**
     * 补全给定起止时间区间内的所有日期
     *
     * @param startTime          开始时间(yyyy-MM-dd)
     * @param endTime            结束时间(yyyy-MM-dd)
     * @param isIncludeStartTime 是否包含开始时间
     * @return
     */
    public static Map<String, String> getBetweenDates(String startTime, String endTime, boolean isIncludeStartTime) {
        List<String> result = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);//定义起始日期
            Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);//定义结束日期  可以去当前月也可以手动写日期。
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(d1);//设置日期起始时间
            if (isIncludeStartTime) {
                result.add(format.format(d1));
                map.put(StringTool.formatDayOfWeek(dateToWeek(d1)), StringTool.formatDate(d1, DateUtil.Y_M_D));
            }
            while (dd.getTime().before(d2)) {//判断是否到结束日期
                dd.add(Calendar.DATE, 1);//进行当前日期加1
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = sdf.format(dd.getTime());
                result.add(str);
                map.put(StringTool.formatDayOfWeek(dateToWeek(StringTool.formatDate(str, DateUtil.Y_M_D))), str);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 补全给定起止时间区间内的所有日期
     *
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param isIncludeStartTime 是否包含开始时间
     * @return
     */
    public static Map<String, String> getBetweenDates(Date startTime, Date endTime, boolean isIncludeStartTime) {
        List<String> result = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(startTime);//设置日期起始时间
            if (isIncludeStartTime) {
                result.add(format.format(startTime));
                map.put(StringTool.formatDayOfWeek(dateToWeek(startTime)), StringTool.formatDate(startTime, DateUtil.Y_M_D));
            }
            while (dd.getTime().before(endTime)) {//判断是否到结束日期
                dd.add(Calendar.DATE, 1);//进行当前日期加1
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = sdf.format(dd.getTime());
                result.add(str);
                map.put(StringTool.formatDayOfWeek(dateToWeek(StringTool.formatDate(str, DateUtil.Y_M_D))), str);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 将 秒 转成时间格式(HH:mm:ss)
     * 比如：18   --> 00:00:18
     */
    public static String formatDateTime(long mss) {
        String DateTimes = null;

        long hours = mss / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;

        DateTimes = (hours > 0 ? hours : "00") + ":"
                + (minutes > 0 ? (minutes < 10 ? "0" + minutes : minutes) : "00") + ":"
                + (seconds > 0 ? (seconds < 10 ? "0" + seconds : seconds) : "00");

        return DateTimes;
    }

    public static String formatDateTimeToChinese(String time) {
        System.out.println("s = " + time);
        String b = "";
        String[] split = time.split(":");
        if (!split[0].equals("00")) {
            b += Integer.parseInt(split[0]) + "时";
        }
        if (!split[1].equals("00") || !split[0].equals("00")) {
            b += Integer.parseInt(split[1]) + "分";
        }
        if (!split[2].equals("00")) {
            b += Integer.parseInt(split[2]) + "秒";
        }
        return b;
    }

    /**
     * 补全给定起止时间区间内的所有日期
     *
     * @param startTime          开始时间
     * @param endTime            结束时间
     * @param isIncludeStartTime 是否包含开始时间
     */
    public static List<String> getBetweenDateList(Date startTime, Date endTime, boolean isIncludeStartTime) {
        List<String> result = new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(startTime);//设置日期起始时间
            if (isIncludeStartTime) {
                result.add(format.format(startTime));
            }

            while (!isEqualsDate(StringTool.formatDate(dd.getTime()), StringTool.formatDate(endTime), DateUtil.Y_M_D)) {//判断是否到结束日期
                dd.add(Calendar.DATE, 1);//进行当前日期加1
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = sdf.format(dd.getTime());
                result.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    /**
     * 获得某月的第一天的开始时刻
     */
    public static String getFirstDayOfMonth(String yearMonth) {
        int month = Integer.parseInt(yearMonth.substring(4, 6));
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, Integer.parseInt(yearMonth.substring(0, 4)));
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        return format(cal.getTime(), DateUtil.Y_M_D) + DateUtil.START_CRITICAL_POINT;
    }

    /**
     * 获得某月的最后一天的最后时刻
     */
    public static String getLatDayOfMonth(String yearMonth) {
        int month = Integer.parseInt(yearMonth.substring(4, 6));
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, Integer.parseInt(yearMonth.substring(0, 4)));
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        return format(cal.getTime(), DateUtil.Y_M_D) + DateUtil.END_CRITICAL_POINT;
    }

    /**
     * 查看某个时间点 time 是否在时段 timeRange 内
     * 比如：当前时间 new Date() 是否在 【08:00~12:00】内
     */
    public static boolean inDate(Date time, List<String> timeRange, String pattern) {
        String dateFormat = DateUtil.format(time, pattern);
        Date newDate = StringTool.formatDate(dateFormat, pattern);
        return DateUtil.isInDateRange(newDate, timeRange, pattern);
    }

    /**
     * 查看某个时间点 time 是否是 timeRange
     */
    public static boolean sameDate(Date time, String timeRange, String pattern) {
        String dateFormat = DateUtil.format(time, pattern);
        Date newDate = StringTool.formatDate(dateFormat, pattern);
        Date timeRangeDate = StringTool.formatDate(timeRange, pattern);
        if (newDate == null || timeRangeDate == null) {
            return false;
        }
        return newDate.getTime() == timeRangeDate.getTime();
    }

    /**
     * 查看某个时间点 time 是超过指定时段 timeRange 多少
     */
    public static long beyondTimeStamp(Date time, String timeRange, String pattern) {
        String dateFormat = DateUtil.format(time, pattern);
        Date newDate = StringTool.formatDate(dateFormat, pattern);
        Date timePeriodDate = StringTool.formatDate(timeRange, pattern);
        assert timePeriodDate != null;
        assert newDate != null;
        return newDate.getTime() - timePeriodDate.getTime();
    }

    /**
     * date2比date1多的天数
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }

    /**
     * 计算两个时间相差的天、时、分（带小数）
     */
    public static BigDecimal[] getDatePoor(Date endDate, Date nowDate) {

        BigDecimal nd = new BigDecimal(1000 * 24 * 60 * 60);
        BigDecimal nh = new BigDecimal(1000 * 60 * 60);
        BigDecimal nm = new BigDecimal(1000 * 60);
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        BigDecimal diff = new BigDecimal(endDate.getTime() - nowDate.getTime());
        // 计算差多少天
        BigDecimal day = CommonUtil.divide(diff, nd, 2, BigDecimal.ROUND_HALF_UP);
        // 计算差多少小时
        BigDecimal hour = CommonUtil.divide(diff, nh, 2, BigDecimal.ROUND_HALF_UP);
        // 计算差多少分钟
        BigDecimal min = CommonUtil.divide(diff, nm, 0, BigDecimal.ROUND_DOWN);
        return new BigDecimal[]{day, hour, min};
    }

    /**
     * 转换时间格式
     */
    public static String changeDateFormat(String date, String oldPattern, String newPattern) {
        Date time = parse(date, oldPattern);
        return format(time, newPattern);
    }

    /**
     * 查询当天是星期几
     */
    public static String dateToWeek(String date, String pattern) {
        Date time = parse(date, pattern);
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        return weeks[dateToWeek(time)];
    }

}
