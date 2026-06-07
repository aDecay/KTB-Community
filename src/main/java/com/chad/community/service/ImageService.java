package com.chad.community.service;

import com.chad.community.dto.ImageUrlResponseDto;
import com.chad.community.entity.Image;
import com.chad.community.enumeration.ImageType;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.ImageMapper;
import com.chad.community.repository.ImageRepository;
import com.chad.community.utils.ImageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final ImageProcessor imageProcessor;

    public ImageUrlResponseDto uploadImage(MultipartFile file, String type) {
        ImageType imageType;

        try {
            imageType = ImageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.IMAGE_INVALID_TYPE);
        }

        try {
            var processedImages = imageProcessor.processImage(file, imageType);

            String jpgPath = fileService.uploadFile(processedImages.jpgByteArray());
            String webpPath = fileService.uploadFile(processedImages.webpByteArray());

            Image image = imageRepository.save(ImageMapper.mapNameAndTypeAndPathToImage(file.getOriginalFilename(), imageType, jpgPath, webpPath));

            return ImageMapper.mapServerUrlAndImageToImagePathResponse(fileService.getUploadServer(), image);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public ImageService(ImageRepository imageRepository, FileService fileService, ImageProcessor imageProcessor) {
        this.imageRepository = imageRepository;
        this.fileService = fileService;
        this.imageProcessor = imageProcessor;
    }
}
