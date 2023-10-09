package ru.skypro.courseWork.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.RegisterDto;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.exception.UserNotFoundException;
import ru.skypro.courseWork.mapper.UserMapper;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.security.service.AuthService;
import ru.skypro.courseWork.security.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ImageRepository imageRepository;



    @Override
    public boolean login(String username, String password) {
        if (userRepository.findByEmail(username).isEmpty()) {
            throw new UserNotFoundException();
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {

        ru.skypro.courseWork.entity.User user = userMapper.toUserEntity(registerDto);
        user.setPassword(encoder.encode(registerDto.getPassword()));

        if (registerDto.getRole() == null) {
            user.setRole(Role.USER);
        }
        // Можно было б и установить изображение, а то фронт ругается на стандартное

        userRepository.save(user);
        return true;
    }

}
