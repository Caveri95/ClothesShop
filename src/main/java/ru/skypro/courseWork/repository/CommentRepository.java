package ru.skypro.courseWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.courseWork.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
