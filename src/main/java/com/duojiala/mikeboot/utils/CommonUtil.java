package com.duojiala.mikeboot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.SerializationUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class CommonUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 正整数正则表达式
     */
    private static final String REGEX_POSIINT = "^[1-9]\\d*$";

    private static final Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    /**
     * 是否为正整数,注意当input为0开头的数字字符串,也返回true,例如:true = isPossInt("00123");
     * @param input 需要做判断的字符串
     * @return boolean 是否为正整数。
     */
    public static boolean isPossInt(Object input) {
        if (input == null) {
            return false;
        }
        String s = input.toString();
        if (isEmpty(s)) {
            return false;
        }
        // 替换掉字符串中开始的"0",从而当input="00123"时也返回true.
        s = s.replaceFirst("^0+", "");
        return s.matches(REGEX_POSIINT);
    }

    /**
     * 是否为手机号码.
     * @param mobile 需要做判断的字符串
     * @return boolean 是否为手机号码。
     */
    public static boolean isMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        if (mobile.trim().length() != 11) {
            return false;
        }
        if (!mobile.trim().startsWith("1")) {
            return false;
        }
        return isPossInt(mobile);
    }

    /**
     * 检查只能是字母和数字
     * @param str the str
     * @return true:符合结果
     */
    public static boolean isNumberAndString(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * 获取字符串字节长度,一个汉字按照两个字节计算.
     * @return int
     */
    public static int strLength(String string) {
        int length = 0;
        if (isEmpty(string)) {
            return length;
        }
        for (int i = 0; i < string.length(); i++) {
            int ascii = Character.codePointAt(string, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }
        return length;
    }

    /**
     * 获取请求IP.
     * @return String ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        Enumeration<?> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            if ("cdn-src-ip".equalsIgnoreCase(name)
                    || "X-Forwarded-For".equalsIgnoreCase(name)
                    || "Proxy-Client-IP".equalsIgnoreCase(name)
                    || "WL-Proxy-Client-IP".equalsIgnoreCase(name)
                    || "X-Real-IP".equalsIgnoreCase(name)) {
                ip = request.getHeader(name);
            }
            if (StringUtils.isNotBlank(ip)){
                break;
            }
        }
        if (StringUtils.isBlank(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 对一个或多的对象进行是否为空的验证,当任一元素为null,返回true. 当元素为字符串时,为空null或trim后长度为0,返回true.
     * 当元素为容器对象时(Collection或Map),为空null或容器中无元素,返回true.
     * 当元素为JSONObject,为空null或无键值对,返回true. 当元素为JSONObject,通过new
     * JSONObject(false)产生,返回true. 当元素为JSONArray,为空null或无元素,返回true.
     * 当元素为JSONNull,返回true.
     * @param objects 对象数组.
     * @return boolean 当数组中有任意一个元素为空即返回true.
     */
    public static boolean isEmpty(Object... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
            if (object instanceof String && object.toString().trim().isEmpty()) {
                return true;
            }
            if (object instanceof Collection && ((Collection<?>) object).isEmpty()) {
                return true;
            }
            if (object instanceof Map && ((Map<?, ?>) object).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取isEmpty()的反,具体实现规则参见isEmpty().
     * @param objects 对象数组.
     * @return boolean 当数组中所有元素不为空即返回true.
     */
    public static boolean isNotEmpty(Object... objects) {
        if (objects == null || objects.length == 0) {
            return false;
        }
        return !isEmpty(objects);
    }

    public static boolean contains(Object[] list, Object target) {
        if (list == null && target != null) {
            return false;
        }
        if (list == null) {
            return true;
        }
        for (Object o : list) {
            if (o == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将对象转换为JSON格式的字符串.日期属性按照"yyyy-MM-dd HH:mm:ss"返回
     */
    public static String toJSONString(Object object) {
        if (CommonUtil.isEmpty(object)) {
            return null;
        }
        return JSON.toJSONStringWithDateFormat(object, DATE_FORMAT, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 以String类型返回Throwable的异常详情，其返回形式类似：e.printStackTrace()
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * 返回18位长度的随机数字
     */
    public static Long getRandomNumber() {
        return Long.parseLong(String.valueOf((int) ((Math.random() * 9 + 1) * 10000000)));
    }

    /**
     * 获取请求IP.
     * @param request request
     * @return String ip
     */
    public static String getIp(HttpServletRequest request) {
        String ip = null;
        Enumeration<?> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            if ("X-Forwarded-For".equalsIgnoreCase(name)
                    || "Proxy-Client-IP".equalsIgnoreCase(name)
                    || "WL-Proxy-Client-IP".equalsIgnoreCase(name)
                    || "X-Real-IP".equalsIgnoreCase(name)) {
                ip = request.getHeader(name);
            }
            if (StringUtils.isNotBlank(ip)){
                break;
            }
        }
        if (StringUtils.isBlank(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static byte[] serialize(Object object) {
        return SerializationUtils.serialize(object);
    }

    @SuppressWarnings("unchecked")
    public static <T> T unSerialize(byte[] bytes, T t) {
        return (T) SerializationUtils.deserialize(bytes);
    }

    /**
     * unicode转中文
     */
    public static String unicodeToString(String str) {

        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch+"" );
        }
        return str;
    }

    /**
     * 判断Object是否可以转化为json对象
     */
    public static boolean isJsonObject(Object object) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断Object是否可以转化为jsonArray对象
     */
    public static boolean isJsonArray(Object object) {
        try {
            JSONArray jsonArray = JSONArray.parseArray(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * BigDecimal 进行除法运算，防止除零异常
     */
    public static BigDecimal divide(BigDecimal decimal1, BigDecimal decimal2, int scale, int roundingMode){
        if(decimal2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }else {
            return decimal1.divide(decimal2, scale, roundingMode);
        }
    }
}
