package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.exception.notFoundException.ImageNotFoundException;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.service.ImageService;

import java.io.IOException;

/**
 * Реализация сервиса по работе с изображениями
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image upload(MultipartFile imageFile) throws IOException {
        Image image = new Image();
        image.setData(imageFile.getBytes());
        log.debug("Image was saved");
        return imageRepository.save(image);
    }

    @Override
    public byte[] getImage(Integer imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }
}
