package com.chad.community.dto;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public record ResourceAndMediaTypeDto(
        Resource resource,
        MediaType mediaType
) {
}
