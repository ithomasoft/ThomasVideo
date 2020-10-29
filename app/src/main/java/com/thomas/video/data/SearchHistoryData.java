package com.thomas.video.data;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 搜索记录
 */
public class SearchHistoryData extends LitePalSupport {

    @Column(unique = true)
    private String key;

    private String type;

    @Column(ignore = true)
    private boolean showDelete;

    public SearchHistoryData() {
    }

    public SearchHistoryData(String key, String type) {
        this.key = key;
        this.type = type;
        showDelete = false;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }
}
