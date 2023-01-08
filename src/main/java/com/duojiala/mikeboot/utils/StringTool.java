package com.duojiala.mikeboot.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 字符串工具类
 */
public class StringTool {

    public static final String COMMA = ",";

    /**
     * 字节数组转为Hex字符串
     */
    public static String bytesToHexString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder("");
        if(bytes != null && bytes.length > 0) {
            for (byte aByte : bytes) {
                int v = aByte & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            return stringBuilder.toString();
        } else {
            return "error";
        }
    }

    /**
     * Hex字符串转为字节数组
     */
    public static byte[] hexStringToBytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();

        for(int i = 0; i < src.length() / 2; ++i) {
            byte _b0 = Byte.decode("0x" + new String(new byte[]{tmp[i * 2]}));
            _b0 = (byte)(_b0 << 4);
            byte _b1 = Byte.decode("0x" + new String(new byte[]{tmp[i * 2 + 1]}));
            ret[i] = (byte)(_b0 ^ _b1);
        }

        return ret;
    }

    /**
     * 获取指定位数的随机字符串,一般这里用作订单号或者流水号
     */
    public static String getRandomString(int length,boolean... withTimeStamp){
        StringBuilder str = (withTimeStamp.length > 0 && withTimeStamp[0]) ? new StringBuilder(getNowTimeStr("yyyyMMddHHmmssSSS")) : new StringBuilder();
        for (int i = 0; i < length; i++) {
            int sj = new Random().nextInt(9);
            str.append(sj);
        }
        return str.toString();
    }

    /**
     * 获取当前时间戳
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis()/1000;
        return String.valueOf(time);
    }

    /**
     * 返回格式化当前时间
     * @param formatter  格式yyyyMMddHHmmssSSS
     */
    public static String getNowTimeStr(String formatter){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 格式化时间字符串
     * @param format1 time的格式例如20170101112233 就是yyyyMMddHHmmss
     * @param format2 这里是想要获取的类型，yyyy-MM-dd HH:mm:ss 就转成了2017-01-01 11:22:33   yyyy年MM月dd日 HH时mm分ss 就转成了2017年01月01日 11时22分33秒
     */
    public static String formatTimeStr(String time, String format1, String format2){
        String sjc = date2TimeStamp(time,format1);
        return timeStamp2Date(sjc,format2);
    }

    /**
     * 时间戳转换成日期格式字符串  yyyy-MM-dd HH:mm:ss
     * @param seconds 精确到秒的字符串
     */
    public static String timeStamp2Date(String seconds, String format) {
        try {
            if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(Long.parseLong(seconds + "000")));
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 如：2016-03-13 03:29:00
     * @param format 如：yyyy-MM-dd HH:mm:ss
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Base64转字符串
     */
    public static String base64Encode(byte[] data) {
        final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        int start = 0;
        int len = data.length;
        StringBuilder buf = new StringBuilder(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    /**
     * 字符串转Base64
     */
    public static byte[] base64Decode(String s) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodedBytes;
    }

    /**
     * 元转分
     */
    public static int changeY2F(int price) {
        DecimalFormat df = new DecimalFormat("#.00");
        price = Integer.parseInt(df.format(price));
        return (int)(price * 100);
    }

    /**
     * 分转元
     */
    public static String changeF2Y(long price) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(BigDecimal.valueOf(price).divide(new BigDecimal(100)).doubleValue());
    }

    /**
     * 分转元
     */
    public static String changeF2Y(float price) {
        try {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(BigDecimal.valueOf(price).divide(new BigDecimal(100)).doubleValue());
        }catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 字符串的日期转为星期几
     */
    public static String formatDayOfWeek(String date, String formatter) {
        Date formatDate = formatDate(date, formatter);
        Calendar cal = Calendar.getInstance();
        if (formatDate != null) {
            cal.setTime(formatDate);
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return formatDayOfWeek(w);
    }

    /**
     * 数字换成文字的周几
     * @param dayOfWeek 周几
     */
    public static String formatDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek){
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
            case 0:
                return "星期日";
        }
        return "";
    }

    /**
     * 格式化时间
     */
    public static String formatDate(Date date){
        return formatDate(date,"yyyy-MM-dd");
    }

    /**
     * 格式化时间
     */
    public static String formatDate(Date date,String formatter){
        if(date == null) {
            return "";
        }
        DateFormat format = new SimpleDateFormat(formatter);
        return format.format(date);
    }

    /**
     * 格式化时间
     */
    public static Date formatDate(String date) {
        return formatDate(date,"yyyy-MM-dd");
    }

    /**
     * 格式化时间
     */
    public static Date formatDate(String date,String formatter) {
        DateFormat format = new SimpleDateFormat(formatter);
        try {
            return format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从一个字符串时间转换成另一个格式的时间字符串
     */
    public static String formatDate(String date, String formatter, String newFormatter) {
        DateFormat format = new SimpleDateFormat(formatter);
        try {
            Date parseDate = format.parse(date);
            return formatDate(parseDate,newFormatter);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 补成100的倍数
     * @param num 原数
     */
    public static long fillZero(long num){
        long remainder = num % 100;
        return num + (100 - remainder);
    }

    /**
     * 补成1000的倍数
     * @param num 原数
     */
    public static long fillZero2(long num){
        long remainder = num % 1000;
        return num + (1000 - remainder);
    }

    /**
     * 计算成万分数
     */
    public static int calPercent(long a, long b){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(4);
        String format = numberFormat.format((float) a / (float) b);
        return (int)(Float.parseFloat(format) * 10000);
    }

    /**
     * 万分数处理成百分之多少的字符串
     */
    public static String formatPercent(int percent, boolean withSymbol){
        return String.format("%.2f", (float) percent / (float) 100) + (withSymbol ? "%" : "");
    }

    /**
     * 万分数处理成小数
     */
    public static String formatPercent(int percent){
        return String.format("%.2f", (float) percent / (float) 10000);
    }

    public static int getIndex(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;                  //字符串时，换为 equals
            }
        }
        return -1;//如果未找到返回-1
    }

    public static int getIndex(String[] arr, String value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(value)) {
                return i;                  //字符串时，换为 equals
            }
        }
        return -1;//如果未找到返回-1
    }

    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;

        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ') {
                i++;
            }

            if (i == len) {
                break;
            }

            int tri = (decode(s.charAt(i)) << 18)
                    + (decode(s.charAt(i + 1)) << 12)
                    + (decode(s.charAt(i + 2)) << 6)
                    + (decode(s.charAt(i + 3)));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=') {
                break;
            }
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=') {
                break;
            }
            os.write(tri & 255);

            i += 4;
        }
    }

    private static int decode(char c) {
        if (c >= 'A' && c <= 'Z') {
            return ((int) c) - 65;
        } else if (c >= 'a' && c <= 'z') {
            return ((int) c) - 97 + 26;
        } else if (c >= '0' && c <= '9') {
            return ((int) c) - 48 + 26 + 26;
        } else {
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
        }
    }

    /**
     * 下划线命名转成驼峰命名
     * @param str 下划线命名
     * @return 驼峰命名
     */
    public static String namedConvert(String str){
        final StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("_(\\w)");
        Matcher m = p.matcher(str);
        while (m.find()){
            m.appendReplacement(sb,m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String distinct(String value, String regex) {
        if(StringUtils.isEmpty(value)) {
            return "";
        }

        return StringUtils.join(Stream.of(value.split(regex)).distinct().toArray(), regex);
    }

    /**
     * 验证身份证号码
     * @param IDNumber 身份证号码
     */
    public static boolean checkIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    return idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase());

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }
        return matches;
    }

    /**
     * 判断String是否可以转为BigDecimal类型
     */
    public static boolean isBigDecimal(String str){
        try {
            new BigDecimal(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 左补字符
     * @param c 要补的字符
     * @param str 原字符串
     * @param length 目标长度
     */
    public static String flushLeft(String c, String str, int length) {
        if(str == null) {
            str = "";
        }
        int str_length = str.length();
        StringBuilder strBuilder = new StringBuilder(str);
        for(int i = 0; i < length - str_length; ++i) {
            strBuilder.insert(0, c);
        }
        str = strBuilder.toString();
        return str;
    }

    /**
     * 右补字符
     * @param c 要补的字符
     * @param str 原字符串
     * @param length 目标长度
     */
    public static String flushRight(String c, String str, int length) {
        if(str == null) {
            str = "";
        }
        int str_length = str.length();
        StringBuilder strBuilder = new StringBuilder(str);
        for(int i = 0; i < length - str_length; ++i) {
            strBuilder.append(c);
        }
        str = strBuilder.toString();
        return str;
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     * @param sourceDate 数值
     * @param formatLength 总长度
     */
    public static String frontCompWithZore(int sourceDate, int formatLength) {
        return String.format("%0" + formatLength + "d", sourceDate);
    }
}
