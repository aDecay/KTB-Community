package com.chad.community.utils;

import com.chad.community.enumeration.ImageType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageProcessor {
    public record ProcessedImages(byte[] jpgByteArray, byte[] webpByteArray) { }

    public ProcessedImages processImage(MultipartFile file, ImageType type) throws IOException {
        try (InputStream is = file.getInputStream()) {
            BufferedImage image = ImageIO.read(is);
            float quality = getCompressQuality(type);

            byte[] jpgImageBytes = compressToJpg(image, quality);
            byte[] webpImageBytes = compressToWebp(image, quality);

            return new ProcessedImages(jpgImageBytes, webpImageBytes);
        }
    }

    private byte[] compressToJpg(BufferedImage image, float quality) throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }

        return outputStream.toByteArray();
    }

    private byte[] compressToWebp(BufferedImage image, float quality) throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("webp").next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionType("Lossy");
        param.setCompressionQuality(quality);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }

        return outputStream.toByteArray();
    }

    private float getCompressQuality(ImageType type) {
        if (type == ImageType.PROFILE) {
            return 0.4f;
        } else {
            return 0.8f;
        }
    }
}
