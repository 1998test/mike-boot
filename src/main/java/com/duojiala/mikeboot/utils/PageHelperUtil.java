package com.duojiala.mikeboot.utils;

import java.util.List;

/**
 * 分页工具类
 */
public class PageHelperUtil {

    /**
     * 开始分页
     * @param list
     * @param pageNum 页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static <T> List<T> startPage(List<T> list, Integer pageNum,
                                        Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        Integer count = list.size(); // 记录总数
        int pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        return list.subList(fromIndex, toIndex);

    }
}
