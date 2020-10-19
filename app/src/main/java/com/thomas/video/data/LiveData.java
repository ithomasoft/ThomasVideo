package com.thomas.video.data;

public class LiveData {
    private String title;
    private String playUrl;

    public LiveData(String title, String playUrl) {
        this.title = title;
        this.playUrl = playUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
