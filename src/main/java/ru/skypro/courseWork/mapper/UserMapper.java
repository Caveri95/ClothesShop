package ru.skypro.courseWork.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.courseWork.dto.Register;
import ru.skypro.courseWork.dto.RegisterDto;
import ru.skypro.courseWork.dto.UpdateUserDto;
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

    @Mapping(target = "email", source = "username")
    User toUserEntity(RegisterDto registerDto);

    UpdateUserDto toUpdateUserDto(User user);

    @Named("imageToPathString")
    default String imageToPathString(Image image) {
        if (image == null) {
            return "/users/image/null";
        }
        return "/users/image/" + image.getId();
    }


}
