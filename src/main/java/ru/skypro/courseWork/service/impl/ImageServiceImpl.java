package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.exception.ImageNotFoundException;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image upload(MultipartFile imageFile) throws IOException {
        Image image = new Image();
        image.setData(imageFile.getBytes());
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    @Override
    public byte[] getImage(Integer imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }


}
