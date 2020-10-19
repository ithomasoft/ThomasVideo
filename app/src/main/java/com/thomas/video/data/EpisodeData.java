package com.thomas.video.data;

public class EpisodeData {
    private String videoName;
    private String videoUrl;
    private String downloadUrl;
    private boolean isPlay = false;

    public EpisodeData(String videoName, String videoUrl, String downloadUrl) {
        this.videoName = videoName;
        this.videoUrl = videoUrl;
        this.downloadUrl = downloadUrl;
    }

    public EpisodeData(String videoName, String videoUrl) {
        this.videoName = videoName;
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
