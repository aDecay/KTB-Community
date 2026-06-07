package com.chad.community.service;

import com.chad.community.dto.ResourceAndMediaTypeDto;
import com.chad.community.dto.ImageUrlResponseDto;
import com.chad.community.entity.Image;
import com.chad.community.enumeration.ImageType;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.ImageMapper;
import com.chad.community.repository.ImageRepository;
import com.chad.community.utils.ImageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
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

    public ResourceAndMediaTypeDto getImage(long imageId, String format) {
        MediaType mediaType;
        Resource resource;

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new CustomException(ErrorCode.IMAGE_NOT_FOUND));

        if (format.equalsIgnoreCase("jpg")) {
            mediaType = MediaType.IMAGE_JPEG;
            resource = fileService.getFile(image.getJpgPath());
        } else if (format.equalsIgnoreCase("webp")) {
            mediaType = MediaType.parseMediaType("image/webp");
            resource = fileService.getFile(image.getWebpPath());
        } else {
            throw new CustomException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        return new ResourceAndMediaTypeDto(resource, mediaType);
    }
}
