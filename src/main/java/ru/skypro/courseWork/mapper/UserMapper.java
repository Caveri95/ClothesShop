package ru.skypro.courseWork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.skypro.courseWork.dto.UserDto;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.ImageRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "imageToPathString")
    UserDto toUserDto(User user);

    @Mapping(target = "image", source = "image", ignore = true)
    User toUserEntity(UserDto userDto);

    @Named("imageToPathString")
    default String imageToPathString(Image image) {
        return "/users/image/" + image.getId();
    }
}
