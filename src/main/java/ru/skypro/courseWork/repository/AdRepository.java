package ru.skypro.courseWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.courseWork.entity.Ad;

import java.util.List;
/**
 * Репозиторий для доступа к данным объявлений (Ad) в базе данных.
 */
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

    /**
     * Нахождение всех объявлений по идентификатору {@code id} пользователя.
     *
     * @param id идентификатор пользователя.
     * @return список объектов класса {@code Ad}.
     */
    List<Ad> findAllByAuthorId(Integer id);
}
