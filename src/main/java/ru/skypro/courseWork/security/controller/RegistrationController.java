package ru.skypro.courseWork.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.courseWork.dto.RegisterDto;
import ru.skypro.courseWork.security.service.AuthService;
/**
 * Контроллер для регистрации пользователей.
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Регистрация")
public class RegistrationController {

    private final AuthService authService;

    /**
     * Регистрирует нового пользователя с предоставленной информацией.
     *
     * @param registerDto объект запроса, содержащий регистрационные данные пользователя.
     * @return {@link ResponseEntity} с кодом состояния HTTP, указывающим на успешную регистрацию.<br>
     * Если регистрация прошла успешно, возвращает код состояния HTTP 201 (Created). <br>
     * Если регистрация не удалась - код состояния HTTP 400 (Bad Request).
     */
    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) {
        if (authService.register(registerDto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
