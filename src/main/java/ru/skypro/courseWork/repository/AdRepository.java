package ru.skypro.courseWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.courseWork.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
}