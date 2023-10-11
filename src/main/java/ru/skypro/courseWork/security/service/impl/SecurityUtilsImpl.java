package ru.skypro.courseWork.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.notFoundException.UserNotFoundException;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.security.service.SecurityUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityUtilsImpl implements SecurityUtils {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void updatePassword(NewPasswordDto newPasswordDto, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
        }
    }
}
