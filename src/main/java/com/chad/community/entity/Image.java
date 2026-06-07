package com.chad.community.entity;

import com.chad.community.enumeration.ImageType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "images")
@Getter
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String originalName;
    @Enumerated(value = EnumType.STRING)
    private ImageType type;
    @Column(nullable = false, length = 255)
    private String jpgPath;
    @Column(nullable = false, length = 255)
    private String webpPath;

    @Builder
    public Image(String originalName, ImageType type, String jpgPath, String webpPath) {
        this.originalName = originalName;
        this.type = type;
        this.jpgPath = jpgPath;
        this.webpPath = webpPath;
    }
}
