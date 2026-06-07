package com.chad.community.service;

import org.springframework.core.io.Resource;

public interface FileService {
    String getUploadServer();

    String uploadFile(byte[] byteArray);

    Resource getFile(String filename);
}
