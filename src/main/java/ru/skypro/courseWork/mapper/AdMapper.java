package ru.skypro.courseWork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.skypro.courseWork.dto.AdDto;
import ru.skypro.courseWork.dto.ExtendedAdDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.AdRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "author", source = "author", qualifiedByName = "authorToInt")
    AdDto toAdDto(Ad ad);

    @Named("authorToInt")
    default Integer authorToInt(User user) {
        return user.getId();
    }
}
