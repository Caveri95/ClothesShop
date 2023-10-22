package ru.skypro.courseWork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.dto.UpdateUserDto;
import ru.skypro.courseWork.dto.UserDto;
import ru.skypro.courseWork.security.service.SecurityUtils;
import ru.skypro.courseWork.service.ImageService;
import ru.skypro.courseWork.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
/**
 * Класс-контроллер для обработки запросов, связанных с пользователями.
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;
    private final SecurityUtils securityUtils;

    /**
     * Обновление пароля пользователя.
     *
     * @param newPasswordDto новый пароль пользователя.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return ответ с кодом состояния HTTP 200 (OK) после успешного обновления пароля.<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если объявление не найдено - код состояния HTTP 403 (Forbidden).
     */
    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid NewPasswordDto newPasswordDto,
                                               Authentication authentication) {
        securityUtils.updatePassword(newPasswordDto, authentication);
        return ResponseEntity.ok().build();
    }

    /**
     * Получение информации об авторизованном пользователе.
     *
     * @return ответ с информацией о пользователе и кодом состояния HTTP 200 (OK).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).
     */
    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = UserDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getMyInfo(authentication));
    }

    /**
     * Обновление информации об авторизованном пользователе.
     *
     * @param updateUserDto  обновленные данные пользователя.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return ответ с обновленной информацией о пользователе и кодом состояния HTTP 200 (OK).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).
     */
    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = UpdateUserDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto,
                                                    Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(updateUserDto, authentication));
    }

    /**
     * Обновление аватара авторизованного пользователя.
     *
     * @param image          файл для установки изображения пользователя.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return Ответ с кодом состояния HTTP 200 (OK) после успешного обновления аватара.<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> updateUserAvatar(@RequestParam MultipartFile image,
                                                 Authentication authentication) throws IOException {
        userService.updateAvatar(image, authentication);
        return ResponseEntity.ok().build();
    }

    /**
     * Получение изображения пользователя по его идентификатору изображения.
     *
     * @param id идентификатор изображения пользователя.
     * @return массив байтов с изображением и код состояния HTTP 200 (OK).
     */
    @GetMapping("/image/{id}")
    @Operation(summary = "Получение изображения пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        return ResponseEntity.ok(imageService.getImage(id));
    }
}
