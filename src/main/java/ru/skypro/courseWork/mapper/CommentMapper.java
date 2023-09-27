package ru.skypro.courseWork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.skypro.courseWork.dto.CommentDto;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "author", source = "author", qualifiedByName = "authorToInteger")
    @Mapping(target = "authorImage", source = "authorImage", qualifiedByName = "authorImageToString")
    CommentDto toCommentDto(Comment comment);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "authorImage", ignore = true)
    Comment toCommentEntity(CommentDto commentDto);

    Comment toCommentEntityFromCreateOrUpdateComment(CreateOrUpdateCommentDto createOrUpdateCommentDto);

    @Named("authorImageToString")
    default String authorImageToString(Image image) {
        return "/user/image/" + image.getId();
    }

    @Named("authorToInteger")
    default Integer authorToInteger(User user) {
        return user.getId();
    }
}
