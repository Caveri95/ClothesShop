package ru.skypro.courseWork.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.entity.Image;

import java.io.IOException;

public interface ImageService {
    Image upload(MultipartFile imageFile) throws IOException;

    void deleteImage(Image image1);

    byte[] getImage(Integer imageId);
}
