package ru.skypro.courseWork.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import ru.skypro.courseWork.dto.CommentDto;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.dto.ExtendedAdDto;
import ru.skypro.courseWork.exception.notFoundException.AdNotFoundException;
import ru.skypro.courseWork.exception.notFoundException.CommentNotFoundException;
import ru.skypro.courseWork.repository.CommentRepository;

import java.util.List;

public interface CommentService {

    /**
     * Получение всех комментариев объявления по его идентификатору из базы данных.
     *
     * @param id идентификатор объявления, не может быть {@code null}.
     * @return список объектов класса {@link ExtendedAdDto}.
     * @see CommentRepository#findAllByAdPk(Integer)
     */
    List<CommentDto> getCommentByIdAd(Integer id);

    /**
     * Создание комментария к объявлению и сохранение его в базу данных.
     *
     * @param id                       идентификатор объявления, не может быть {@code null}.
     * @param createOrUpdateCommentDto объект класса {@link CreateOrUpdateCommentDto},
     *                                 содержащий текст комментария, не может быть {@code null}.
     * @param authentication           объект аутентификации, представляющий текущего пользователя.
     * @return объект класса {@link CommentDto}, содержащий информацию о комментарии.
     * @throws AdNotFoundException если объявление по данному {@code id} не было найдено.
     * @see JpaRepository#save(Object)
     */
    CommentDto createAdComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

    /**
     * Удаление комментария по его идентификатору из базы данных.
     *
     * @param commentId идентификатор комментария, не может быть {@code null}.
     * @throws CommentNotFoundException если комментарий по данному {@code id} не был найден.
     * @see JpaRepository#deleteById(Object)
     */
    void deleteCommentById(Integer commentId);

    /**
     * Обновление комментария у объявления и сохранение его в базу данных.
     *
     * @param adId                     идентификатор объявления, не может быть {@code null}.
     * @param commentId                идентификатор комментария, не может быть {@code null}.
     * @param createOrUpdateCommentDto объект класса {@link CreateOrUpdateCommentDto},
     *                                 содержащий текст комментария, не может быть {@code null}.
     * @param authentication           объект аутентификации, представляющий текущего пользователя.
     * @return объект класса {@link CommentDto}, содержащий обновленную информацию комментария.
     * @see JpaRepository#save(Object)
     */
    CommentDto updateComment(Integer adId,
                             Integer commentId,
                             CreateOrUpdateCommentDto createOrUpdateCommentDto,
                             Authentication authentication);
}
