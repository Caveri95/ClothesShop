package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
        logger.debug("Info about user with id - " + user.getId() + " was update");
        return userMapper.toUpdateUserDto(user);
    }

    @Override
    public void updateAvatar(MultipartFile image, Authentication authentication) throws IOException {

        User user = findUserByEmail(authentication);
        user.setImage(imageService.upload(image));
        userRepository.save(user);
        logger.debug("User avatar with id - " + user.getId() + " was update");
    }

    private User findUserByEmail(Authentication authentication) {
        Optional<User> user = userRepository.findByEmail(authentication.getName());

        if (user.isEmpty()) {
            logger.error("User not found");
            throw new UserNotFoundException();
        } else {
            return user.get();
        }
    }
}
