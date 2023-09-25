package ru.skypro.courseWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.courseWork.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
