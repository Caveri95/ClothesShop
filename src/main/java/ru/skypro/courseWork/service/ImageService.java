package ru.skypro.courseWork.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.entity.Image;

import java.io.IOException;

/**
 * Сервис по работе с изображениями
 */
public interface ImageService {

    /**
     * Сохранение изображения в базу данных
     *
     * @param imageFile файл изображения, не может быть {@code null}.
     * @return объект класса {@link Image}.
     * @throws IOException если возникла ошибка во время сохранения файла изображения.
     */
    Image upload(MultipartFile imageFile) throws IOException;

    /**
     * Получение изображения из базы данных
     * @param imageId идентификатор изображения, не может быть {@code null}.
     * @return массив байт.
     */
    byte[] getImage(Integer imageId);
}
