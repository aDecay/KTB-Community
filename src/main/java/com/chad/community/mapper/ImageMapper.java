package com.chad.community.mapper;

import com.chad.community.dto.ImageUrlResponseDto;
import com.chad.community.entity.Image;
import com.chad.community.enumeration.ImageType;

public class ImageMapper {
    public static Image mapNameAndTypeAndPathToImage(String originalFilename, ImageType type, String jpgPath, String webpPath) {
        return Image.builder()
                .originalName(originalFilename)
                .type(type)
                .jpgPath(jpgPath)
                .webpPath(webpPath).build();
    }

    public static ImageUrlResponseDto mapServerUrlAndImageToImagePathResponse(String serverUrl, Image image) {
        return new ImageUrlResponseDto(serverUrl + "/uploads/images/" + image.getId());
    }
}
