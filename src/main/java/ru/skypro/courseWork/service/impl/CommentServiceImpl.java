package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.CommentDto;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.notFoundException.AdNotFoundException;
import ru.skypro.courseWork.exception.notFoundException.CommentNotFoundException;
import ru.skypro.courseWork.exception.notFoundException.UserNotFoundException;
import ru.skypro.courseWork.mapper.CommentMapper;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.CommentRepository;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.service.CommentService;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса по работе с комментариями объявления
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    @Override
    public List<CommentDto> getCommentByIdAd(Integer id) {
        return commentMapper.toCommentsDto(commentRepository.findAllByAdPk(id));
    }

    @Override
    public CommentDto createAdComment(Integer id,
                                      CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                      Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        Comment comment = commentMapper.toCommentEntityFromCreateOrUpdateComment(createOrUpdateCommentDto);

        comment.setAd(ad);
        comment.setAuthor(user);

        commentRepository.save(comment);
        log.debug("Comment with id - {} was created", id);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or " +
            "@commentServiceImpl.findCommentById(#commentId).getAuthor().getEmail()==authentication.name")
    public void deleteCommentById(Integer commentId) {
        commentRepository.deleteById(commentId);
        log.debug("Comment with id - {} was deleted", commentId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or " +
            "@commentServiceImpl.findCommentById(#commentId).getAuthor().getEmail()==authentication.name")
    public CommentDto updateComment(Integer adId,
                                    Integer commentId,
                                    CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                    Authentication authentication) {


        String email = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new).getAuthor().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        comment.setText(createOrUpdateCommentDto.getText());
        comment.setCreatedAt(Instant.now().toEpochMilli());
        comment.setAd(ad);
        comment.setAuthor(user);
        commentRepository.save(comment);

        log.debug("Comment with id - {} was updated in ad with id - {}", commentId, adId);
        return commentMapper.toCommentDto(comment);
    }

    public Comment findCommentById(Integer id) {

        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isEmpty()) {
            log.error("Comment not found");
            throw new CommentNotFoundException();

        } else {
            return comment.get();
        }

    }
}
