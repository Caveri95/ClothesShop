package ru.skypro.courseWork.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.UpdateUserDto;
import ru.skypro.courseWork.dto.UserDto;
import ru.skypro.courseWork.exception.notFoundException.UserNotFoundException;

import java.io.IOException;

/**
 * Сервис по работе с пользователями
 */
public interface UserService {

    /**
     * Получение информации об авторизованном пользователе из базы данных.
     *
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return объект класса {@link UserDto}, содержащий информацию о пользователе.
     * @throws UserNotFoundException если пользователь не найден.
     */
    UserDto getMyInfo(Authentication authentication);

    /**
     * Обновление информации авторизованного пользователя и сохранение ее в базу данных.
     *
     * @param updateUserDto  объект класса {@link UpdateUserDto},
     *                       содержащий информацию для обновления пользователя, не может быть {@code null}.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return объект класса {@link UpdateUserDto}, содержащий обновленную информацию о пользователе.
     * @throws UserNotFoundException если пользователь не найден.
     * @see JpaRepository#save(Object)
     */
    UpdateUserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication);

    /**
     * Обновление изображения пользователя.
     *
     * @param image          файл изображения, не может быть {@code null}.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @throws IOException           если возникла ошибка во время сохранения файла изображения.
     * @throws UserNotFoundException если пользователь не найден.
     * @see JpaRepository#save(Object)
     */
    void updateAvatar(MultipartFile image, Authentication authentication) throws IOException;
}
