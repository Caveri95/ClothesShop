package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
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
import ru.skypro.courseWork.security.service.SecurityUtils;
import ru.skypro.courseWork.service.CommentService;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    @Override
    public List<CommentDto> getCommentByIdAd(Integer id) {
        return commentMapper.toCommentsDto(commentRepository.findAllByAdPk(id));
    }

    @Override
    public CommentDto createAdComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        Comment comment = commentMapper.toCommentEntityFromCreateOrUpdateComment(createOrUpdateCommentDto);

        comment.setAd(ad);
        comment.setAuthor(user);

        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public void deleteCommentById(Integer commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        securityUtils.checkAccessToComment(comment);
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto updateComment(Integer adId,
                                    Integer commentId,
                                    CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                    Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        securityUtils.checkAccessToComment(comment);

        comment.setText(createOrUpdateCommentDto.getText());
        comment.setCreateAt(Instant.now().toEpochMilli());
        comment.setAd(ad);
        comment.setAuthor(user);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }
}
