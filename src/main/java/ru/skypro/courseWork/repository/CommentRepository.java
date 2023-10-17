package ru.skypro.courseWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.courseWork.entity.Comment;

import java.util.List;
/**
 * Репозиторий для доступа к данным комментариев (Comment) в базе данных.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Нахождение всех комментариев по идентификатору {@code pk} объявления.
     *
     * @param id идентификатор объявления.
     * @return список объектов класса {@code Comment}.
     */
    List<Comment> findAllByAdPk(Integer id);

    /**
     * Удаление всех комментариев объявления по идентификатору {@code id} объявления.
     * @param id идентификатор объявления.
     */
    void deleteAllByAdPk(Integer id);
}
