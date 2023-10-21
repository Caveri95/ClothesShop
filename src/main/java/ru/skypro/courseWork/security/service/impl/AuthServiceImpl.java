package ru.skypro.courseWork.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.RegisterDto;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.invalidParameters.InvalidLoginPasswordException;
import ru.skypro.courseWork.exception.invalidParameters.InvalidUsernameException;
import ru.skypro.courseWork.mapper.UserMapper;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.security.service.AuthService;
import ru.skypro.courseWork.service.impl.AdServiceImpl;

import javax.transaction.Transactional;
/**
 * Реализация сервиса для аутентификации и регистрации пользователей.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final SecurityUserService securityUserService;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    @Transactional
    public boolean login(String username, String password) {
        UserDetails userDetails = securityUserService.loadUserByUsername(username);
        if (userRepository.findByEmail(username).isEmpty() || !encoder.matches(password, userDetails.getPassword())) {
            throw new InvalidLoginPasswordException();
        }
        logger.debug("User with username - " + username + " was login");
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    @Transactional
    public boolean register(RegisterDto registerDto) {

        if (userRepository.findByEmail(registerDto.getUsername()).isPresent()) {
            throw new InvalidUsernameException();
        }

        User user = userMapper.toUserEntity(registerDto);
        user.setPassword(encoder.encode(registerDto.getPassword()));

        if (registerDto.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
        logger.debug("New user was created");
        return true;
    }
}
