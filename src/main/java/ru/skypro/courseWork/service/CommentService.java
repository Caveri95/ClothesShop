package ru.skypro.courseWork.service;

import ru.skypro.courseWork.dto.CommentDto;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentByIdAd(Integer id);

    Comment createAdComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto);

    void deleteCommentById(Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto);
}
