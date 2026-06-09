package com.chad.community.controller;

import com.chad.community.dto.ResourceAndMediaTypeDto;
import com.chad.community.dto.ImageUrlResponseDto;
import com.chad.community.service.ImageService;
import com.chad.community.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/uploads/images")
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ImageUrlResponseDto>> uploadImage(
            @RequestParam MultipartFile file,
            @RequestParam String type
    ) {
        ImageUrlResponseDto imageUrl = imageService.uploadImage(file, type);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(imageUrl, "image uploaded successfully"));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImage(
            @PathVariable long imageId,
            @RequestParam(defaultValue = "jpg") String format
    ) {
        ResourceAndMediaTypeDto resourceAndMediaType = imageService.getImage(imageId, format);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(resourceAndMediaType.mediaType())
                .body(resourceAndMediaType.resource());
    }
}
