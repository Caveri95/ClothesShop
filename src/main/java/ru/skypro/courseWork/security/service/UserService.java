package ru.skypro.courseWork.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.UserNotFoundException;
import ru.skypro.courseWork.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {   //Задача сервиса - по имени пользователя предоставить пол-ля

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        //Создаем спринговского Юзера, в которого передаем юзернейм, пароль,
        // коллекцию прав доступа(тут проблема, у нас роли -> используем метод)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(List.of(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

}