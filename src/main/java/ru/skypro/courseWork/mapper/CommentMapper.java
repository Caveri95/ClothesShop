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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = Instant.class)
public interface CommentMapper {

    @Mapping(target = "author", source = "author", qualifiedByName = "authorToInteger")
    @Mapping(target = "authorImage", source = "author", qualifiedByName = "authorImageToString")
    CommentDto toCommentDto(Comment comment);

    @Mapping(target = "author", ignore = true)
    Comment toCommentEntity(CommentDto commentDto);

    List<CommentDto> toCommentsDto(List<Comment> comments);

    @Mapping(target = "createAt", expression = "java(Instant.now().toEpochMilli())")
    Comment toCommentEntityFromCreateOrUpdateComment(CreateOrUpdateCommentDto createOrUpdateCommentDto);

    @Named("authorImageToString")
    default String authorImageToString(User user) {
        if (user.getImage() == null) {
            return "/users/image/null";
        }
        return "/users/image/" + user.getImage().getId();
    }

    @Named("authorToInteger")
    default Integer authorToInteger(User user) {
        return user.getId();
    }
}
