package ru.skypro.courseWork.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.UpdateUserDto;
import ru.skypro.courseWork.dto.UserDto;
import ru.skypro.courseWork.entity.User;

import java.io.IOException;

public interface UserService {
    UserDto getMyInfo(Authentication authentication);

    UpdateUserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication);

    void updateAvatar(MultipartFile image, Authentication authentication) throws IOException;
}
