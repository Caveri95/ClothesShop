package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.service.UserService;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User getMyInfo() {
        //userRepository.findBy()   Как то ищем самого себя
        return new User();
    }
}
