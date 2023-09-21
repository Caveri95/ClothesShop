package ru.skypro.courseWork.service;

import ru.skypro.courseWork.dto.Register;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);
}
