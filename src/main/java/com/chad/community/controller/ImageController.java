package com.chad.community.controller;

import com.chad.community.dto.ImageUrlResponseDto;
import com.chad.community.service.ImageService;
import com.chad.community.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/uploads/images")
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ImageUrlResponseDto>> uploadImage(
            @RequestParam MultipartFile file,
            @RequestParam String type
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(imageService.uploadImage(file, type), "image uploaded successfully"));
    }
}
