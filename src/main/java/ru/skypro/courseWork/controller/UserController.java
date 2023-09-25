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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.dto.UpdateUserDto;
import ru.skypro.courseWork.dto.UserDto;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid NewPasswordDto newPasswordDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(new UserDto());
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UpdateUserDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(new UpdateUserDto());
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> updateUserAvatar(@RequestParam MultipartFile image) {
        return ResponseEntity.ok().build();
    }

}
