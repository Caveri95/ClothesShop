package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.exception.notFoundException.ImageNotFoundException;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.service.ImageService;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public Image upload(MultipartFile imageFile) throws IOException {
        Image image = new Image();
        image.setData(imageFile.getBytes());
        logger.debug("Image was saved");
        return imageRepository.save(image);
    }

    @Override
    public byte[] getImage(Integer imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }
}
