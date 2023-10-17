package ru.skypro.courseWork.security.service;

import ru.skypro.courseWork.dto.Register;
import ru.skypro.courseWork.dto.RegisterDto;
/**
 * Сервис для аутентификации и регистрации пользователей.
 */
public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterDto registerDto);
}
