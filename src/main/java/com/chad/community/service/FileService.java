package com.chad.community.service;

public interface FileService {
    String getUploadServer();

    String uploadFile(byte[] byteArray);
}
