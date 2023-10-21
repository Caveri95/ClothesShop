package ru.skypro.courseWork.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.notFoundException.UserNotFoundException;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.security.service.SecurityUtils;

import javax.transaction.Transactional;
/**
 * Реализация сервиса для обновления пароля пользователя
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityUtilsImpl implements SecurityUtils {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(SecurityUtilsImpl.class);

    /**
     * Обновление пароля авторизованного пользователя
     * @param newPasswordDto объект, содержащий в себе новый пароль от пользователя.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     */
    @Override
    @Transactional
    public void updatePassword(NewPasswordDto newPasswordDto, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
        }
        logger.debug("User with id - " + user.getId() + " updated password");
    }
}
