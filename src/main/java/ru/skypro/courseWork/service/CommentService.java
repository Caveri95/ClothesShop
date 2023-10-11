package ru.skypro.courseWork.service;

import org.springframework.security.core.Authentication;
import ru.skypro.courseWork.dto.CommentDto;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getCommentByIdAd(Integer id);

    CommentDto createAdComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication);

    void deleteCommentById(Integer commentId);

    CommentDto updateComment(Integer adId,
                          Integer commentId,
                          CreateOrUpdateCommentDto createOrUpdateCommentDto,
                          Authentication authentication);
}
