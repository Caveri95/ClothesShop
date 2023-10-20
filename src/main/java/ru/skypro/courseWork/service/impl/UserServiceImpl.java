package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.UpdateUserDto;
import ru.skypro.courseWork.dto.UserDto;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.notFoundException.UserNotFoundException;
import ru.skypro.courseWork.mapper.UserMapper;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.service.ImageService;
import ru.skypro.courseWork.service.UserService;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    @Override
    public UserDto getMyInfo(Authentication authentication) {
        return userMapper.toUserDto(findUserByEmail(authentication));
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication) {

        User user = findUserByEmail(authentication);
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return userMapper.toUpdateUserDto(user);
    }

    @Override
    public void updateAvatar(MultipartFile image, Authentication authentication) throws IOException {

        User user = findUserByEmail(authentication);
        user.setImage(imageService.upload(image));
        userRepository.save(user);
    }

    private User findUserByEmail(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
    }
}
