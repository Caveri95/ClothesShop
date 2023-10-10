package ru.skypro.courseWork.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityUserService extends UserDetailsService {   //Задача сервиса - по имени пользователя предоставить пол-ля

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
