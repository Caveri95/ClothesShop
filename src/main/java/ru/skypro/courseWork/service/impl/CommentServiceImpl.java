package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.AdNotFoundException;
import ru.skypro.courseWork.exception.CommentNotFoundException;
import ru.skypro.courseWork.mapper.CommentMapper;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.CommentRepository;
import ru.skypro.courseWork.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdRepository adRepository;
    @Override
    public List<Comment> getCommentByIdAd(Integer id) {
        return commentRepository.findAllByAdPk(id);
    }

    @Override
    public Comment createAdComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto) {

        //User user = userRepository.findBy()    Ищем юзера по какому то критерию
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        Comment comment = commentMapper.toCommentEntityFromCreateOrUpdateComment(createOrUpdateCommentDto);

        comment.setAd(ad);
        comment.setAuthor(new User()); //Сюда юзер нужен

        commentRepository.save(comment);
        return comment;
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {

        //User user = userRepository.findBy()    Ищем юзера по какому то критерию
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        commentMapper.toCommentEntityFromCreateOrUpdateComment(createOrUpdateCommentDto);
        comment.setAd(ad);
        comment.setAuthor(new User()); //Сюда юзер нужен
        return comment;
    }
}
