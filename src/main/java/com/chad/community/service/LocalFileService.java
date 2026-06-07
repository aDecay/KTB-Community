package com.chad.community.service;

import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileService implements FileService {
    private final String uploadServer;
    private final String uploadDir;

    public LocalFileService(
            @Value("${file.upload-server}") String uploadServer,
            @Value("${file.upload-dir}") String uploadDir
    ) {
        this.uploadServer = uploadServer;
        this.uploadDir = uploadDir;
    }

    @Override
    public String getUploadServer() {
        return uploadServer;
    }

    @Override
    public String uploadFile(byte[] byteArray) {
        try {
            Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(directory);

            String filename = UUID.randomUUID().toString();
            Path targetPath = directory.resolve(filename);

            Files.copy(new ByteArrayInputStream(byteArray), targetPath);

            return filename;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
