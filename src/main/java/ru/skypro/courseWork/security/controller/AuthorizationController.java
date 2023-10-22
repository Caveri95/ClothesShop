package ru.skypro.courseWork.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.courseWork.dto.Login;
import ru.skypro.courseWork.security.service.AuthService;

import javax.validation.Valid;

/**
 * Контроллер для аутентификации пользователей.
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Авторизация")
public class AuthorizationController {

    private final AuthService authService;

    /**
     * Авторизует пользователя с предоставленными учетными данными.
     *
     * @param login запрос на вход, содержащий имя пользователя и пароль.
     * @return {@link ResponseEntity} с кодом состояния HTTP, указывающим на успешную авторизацию.<br>
     * Если авторизация прошла успешно, возвращает код состояния HTTP 200 (OK). <br>
     * Если авторизация не удалась - код состояния HTTP 401 (Unauthorized).
     */
    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> login(@RequestBody @Valid Login login) {
        if (authService.login(login.getUsername(), login.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
