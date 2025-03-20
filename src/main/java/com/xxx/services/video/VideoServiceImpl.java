package com.xxx.services.video;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {
    private FFmpegFrameGrabber grabber;
    private FFmpegFrameRecorder recorder;
    private Thread streamingThread;
    private volatile boolean isStreaming = false;

    @Override
    public void startStreaming(String url, String hlsOutputPath) {
        if (isStreaming) {
            log.info("Process already started");
            return;
        }
        try {
            File hlsFile = new File(hlsOutputPath);
            if (!hlsFile.exists()) {
                hlsFile.mkdir();
            }

            grabber = new FFmpegFrameGrabber(url);
            grabber.setOption("rtsp_transport", "tcp");
            grabber.start();

            // Khởi tạo recorder cho HLS
            recorder = new FFmpegFrameRecorder(hlsOutputPath, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
            recorder.setVideoCodec(grabber.getVideoCodec()); // -c:v copy
            recorder.setAudioCodecName("aac");
            recorder.setAudioBitrate(128000);
            recorder.setFormat("hls"); // Định dạng HLS
            recorder.setOption("hls_time", "2"); // Mỗi đoạn 2 giây
            recorder.setOption("hls_list_size", "3"); // Giữ 3 đoạn trong danh sách
            recorder.setOption("hls_flags", "delete_segments"); // Xóa đoạn cũ
            recorder.setPixelFormat(grabber.getPixelFormat()); // Giữ nguyên pixel format

            recorder.start();

            isStreaming = true;
            streamingThread = new Thread(() -> {
                try {
                    Frame frame;
                    while (isStreaming && (frame = grabber.grab()) != null) {
                        recorder.record(frame);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            streamingThread.start();
            log.info("Bắt đầu phát luồng RTSP sang HLS...");
        } catch (FFmpegFrameGrabber.Exception e) {
            throw new RuntimeException(e);
        } catch (FFmpegFrameRecorder.Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void stopStreaming() {
        isStreaming = false;
        try {
            if (recorder != null) {
                recorder.stop();
                recorder.release();
            }
            if (grabber != null) {
                grabber.stop();
                grabber.release();
            }
            if (streamingThread != null) {
                streamingThread.interrupt();
            }
            log.info("Đã dừng luồng.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isStreaming() {
        return isStreaming;
    }
}
