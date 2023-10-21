package ru.skypro.courseWork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.skypro.courseWork.dto.*;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.entity.User;

import java.time.Instant;
import java.util.List;
/**
 * Класс {@code CommentMapper} является компонентом, отвечающим за преобразование сущностей комментарий (Comment) и DTO
 * (Data Transfer Objects) в различные форматы и обратно.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = Instant.class)
public interface CommentMapper {

    @Mapping(target = "author", source = "author", qualifiedByName = "authorToInteger")
    @Mapping(target = "authorFirstName", source = "author", qualifiedByName = "authorFirstNameFromAuthor")
    @Mapping(target = "authorImage", source = "author", qualifiedByName = "authorImageToString")
    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentsDto(List<Comment> comments);

    @Mapping(target = "createdAt", expression = "java(Instant.now().toEpochMilli())")
    Comment toCommentEntityFromCreateOrUpdateComment(CreateOrUpdateCommentDto createOrUpdateCommentDto);

    @Named("authorImageToString")
    default String authorImageToString(User user) {
        if (user.getImage() == null) {
            return null;
        }
        return "/users/image/" + user.getImage().getId();
    }

    @Named("authorToInteger")
    default Integer authorToInteger(User user) {
        return user.getId();
    }

    @Named("authorFirstNameFromAuthor")
    default String authorFirstNameFromAuthor(User author) {
        return author.getFirstName();
    }
}
