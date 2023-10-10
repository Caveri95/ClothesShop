package ru.skypro.courseWork.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.forbiddenException.AdForbiddenException;
import ru.skypro.courseWork.exception.forbiddenException.CommentForbiddenException;
import ru.skypro.courseWork.exception.notFoundException.UserNotFoundException;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.security.service.SecurityUtils;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityUtilsImpl implements SecurityUtils {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkAccessToComment(Comment comment) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        if (!Objects.equals(comment.getAuthor().getId(), user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new CommentForbiddenException();
        }
    }

    @Override
    public void checkAccessToAd(Ad ad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        if (!Objects.equals(ad.getAuthor().getId(), user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new AdForbiddenException();
        }
    }

    @Override
    @Transactional
    public void updatePassword(NewPasswordDto newPasswordDto) { //   Зачем в ДТО поле с текущим паролем?
                                                                // (Обычно новый и просят повторить новый)

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
    }
}
