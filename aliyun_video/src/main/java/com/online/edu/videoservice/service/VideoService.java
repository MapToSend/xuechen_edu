package com.online.edu.videoservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    public String uploadVideo(MultipartFile file);

    Boolean deleteVideo(String videoId);
}
