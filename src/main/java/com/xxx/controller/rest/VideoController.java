package com.xxx.controller.rest;

import com.xxx.services.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/start")
    public String start() {
        String rtspUrl = "rtsp://admin:pass@192.168.1.17:554/onvif1";
        String hlsOutputPath = "src/main/resources/static/hls/stream.m3u8";
        videoService.startStreaming(rtspUrl, hlsOutputPath);
        return "Start video";
    }
}
