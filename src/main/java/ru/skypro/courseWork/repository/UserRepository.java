package ru.skypro.courseWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.courseWork.entity.User;

import java.util.Optional;
/**
 * Репозиторий для доступа к данным пользователей (User) в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Нахождение пользователя по его {@code email} (username).
     *
     * @param email представляется как username, по которому производится поиск.
     * @return {@code Optional}, содержащий найденного пользователя или пустой, если пользователь не найден.
     */
    Optional<User> findByEmail(String email);
}
