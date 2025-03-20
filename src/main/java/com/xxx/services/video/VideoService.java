package com.xxx.services.video;

public interface VideoService {
    void startStreaming(String url, String hlsOutputPath);
    void stopStreaming();
    boolean isStreaming();
}
