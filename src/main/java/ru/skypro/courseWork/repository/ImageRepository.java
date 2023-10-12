package ru.skypro.courseWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.courseWork.entity.Image;
/**
 * Репозиторий для доступа к данным изображений (Image) в базе данных.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
