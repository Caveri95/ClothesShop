package ru.skypro.courseWork.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.AdDto;
import ru.skypro.courseWork.dto.AdsDto;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.dto.ExtendedAdDto;
import ru.skypro.courseWork.exception.notFoundException.AdNotFoundException;
import ru.skypro.courseWork.repository.AdRepository;

import java.io.IOException;
import java.util.List;

/**
 * Сервис по работе с объявлениями
 */
public interface AdService {

    /**
     * Получение всех объявлений из базы данных.
     *
     * @return объект, содержащий в себе список объектов класса {@link AdDto} и количество таких объектов
     * @see JpaRepository#findAll()
     */
    AdsDto getAllAds();

    /**
     * Создание нового объявления и сохранение его в базу данных.
     *
     * @param properties     объект класса {@link CreateOrUpdateAdDto},
     *                       содержащий необходимую информацию о создаваемом объявлении, не может быть {@code null}.
     * @param image          файл для установки изображения объявления, не может быть {@code null}.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return объект класса {@link AdDto}, содержащий информацию о созданном объявлении.
     * @throws IOException если возникла ошибка во время сохранения файла изображения.
     * @see JpaRepository#save(Object)
     */
    AdDto createAd(CreateOrUpdateAdDto properties, MultipartFile image, Authentication authentication) throws IOException;

    /**
     * Обновление изображения объявления и сохранение его в базу данных.
     *
     * @param id    идентификатор объявления у которого необходимо обновить изображение, не может быть {@code null}.
     * @param image файл для установки изображения объявления, не может быть {@code null}.
     * @throws IOException         если возникла ошибка во время сохранения файла изображения.
     * @throws AdNotFoundException если объявление по данному {@code id} не было найдено.
     * @see JpaRepository#save(Object)
     */
    void updateImage(Integer id, MultipartFile image) throws IOException;

    /**
     * Получение объявлений аутентифицированного пользователя из базы данных.
     *
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return список объектов класса {@link AdDto}.
     * @see AdRepository#findAllByAuthorId(Integer)
     */
    List<AdDto> getAllMyAds(Authentication authentication);

    /**
     * Получение расширенной информации об объявлении по его идентификатору из базы данных.
     *
     * @param id идентификатор объявления, не может быть {@code null}.
     * @return объекта класса {@link ExtendedAdDto}, содержащего полную информацию об объявлении.
     * @throws AdNotFoundException если объявление по данному {@code id} не было найдено.
     * @see JpaRepository#findById(Object)
     */
    ExtendedAdDto getAdFullInfo(Integer id);

    /**
     * Удаление объявления по его идентификатору из базы данных.
     *
     * @param id идентификатор объявления, не может быть {@code null}.
     * @throws AdNotFoundException если объявление по данному {@code id} не было найдено.
     * @see JpaRepository#deleteById(Object)
     */
    void deleteById(Integer id);

    /**
     * Обновление информации объявления по его идентификатору и сохранение в базу данных.
     *
     * @param id                  идентификатор объявления, не может быть {@code null}.
     * @param createOrUpdateAdDto объект класса {@link CreateOrUpdateAdDto},
     *                            содержащий необходимую информацию для обновления объявления, не может быть {@code null}.
     * @return объект класса {@link AdDto}, содержащий обновленную информацию об объявлении.
     * @throws AdNotFoundException если объявление по данному {@code id} не было найдено.
     * @see JpaRepository#save(Object)
     */
    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);

}
