package com.duojiala.mikeboot.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 枚举工具类
 */
public class EnumUtil {

    public static final String METHOD_TYPE = "getType";

    /**
     * 值映射为枚举
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param methodStr    取值方法
     * @param <E>       对应枚举
     * @return
     */
    public static <E extends Enum<?>> E valueOf(Class<E> enumClass, Object value, String methodStr) {
        E[] es = enumClass.getEnumConstants();
        for (E e : es) {
            Object evalue;
            try {
                Method method = enumClass.getMethod(methodStr);
                method.setAccessible(true);
                evalue = method.invoke(e);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                throw new RuntimeException("Error: NoSuchMethod " + e);
            }
            if (value instanceof Number && evalue instanceof Number
                    && new BigDecimal(String.valueOf(value)).compareTo(new BigDecimal(String.valueOf(evalue))) == 0) {
                return e;
            }
            if (Objects.equals(evalue, value)) {
                return e;
            }
            if(evalue instanceof Number && value instanceof String) {
                try {
                    if(String.valueOf(evalue).equals(value)) {
                        return e;
                    }
                }catch (Exception ex) {
                    return null;
                }
            }
        }
        return null;
    }

}
