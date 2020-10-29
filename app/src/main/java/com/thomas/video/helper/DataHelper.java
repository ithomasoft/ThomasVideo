package com.thomas.video.helper;

import com.thomas.video.data.SearchHistoryData;

import org.litepal.LitePal;

import java.util.List;

public class DataHelper {

    /**
     * 保存搜索记录
     *
     * @param key
     * @param type
     */
    public static void saveSearchKey(String key, String type) {
        SearchHistoryData data = new SearchHistoryData(key, type);
        data.save();
    }

    /**
     * 查询所有的搜索记录
     *
     * @return
     */
    public static List<SearchHistoryData> getSearchKeyList() {
        return LitePal.findAll(SearchHistoryData.class);
    }

    /**
     * 删除某条搜索记录
     *
     * @param key
     */
    public static void deleteSearchKey(String key) {
        LitePal.deleteAll(SearchHistoryData.class, "key = ?", key);
    }

    /**
     * 删除所有的搜索记录
     */
    public static void deleteAllSearchKey() {
        LitePal.deleteAll(SearchHistoryData.class);
    }
}
