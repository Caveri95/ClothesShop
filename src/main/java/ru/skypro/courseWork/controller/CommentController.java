package ru.skypro.courseWork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.courseWork.dto.CommentDto;
import ru.skypro.courseWork.dto.CommentsDto;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.service.CommentService;

import javax.validation.Valid;
import java.util.List;

/**
 * Класс-контроллер для обработки запросов, связанных с комментариями объявлений.
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentController {

    private final CommentService commentService;

    /**
     * Получение комментариев к объявлению по его идентификатору.
     *
     * @param id идентификатор объявления.
     * @return ответ с массивом комментариев в формате JSON и кодом состояния HTTP 200 (OK).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если комментарии не найдены - код состояния HTTP 404 (Not Found).
     */
    @GetMapping("/{id}/comments")
    @Operation(summary = "Получение комментариев объявления", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = CommentsDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentsDto> getCommentsByIdAd(@PathVariable("id") Integer id) {
        List<CommentDto> commentsDto = commentService.getCommentByIdAd(id);
        return ResponseEntity.ok(new CommentsDto(commentsDto.size(), commentsDto));
    }

    /**
     * Добавление нового комментария к объявлению.
     *
     * @param id                       идентификатор объявления.
     * @param createOrUpdateCommentDto содержание нового комментария.
     * @param authentication           объект аутентификации, представляющий текущего пользователя.
     * @return ответ с созданным комментарием в формате JSON и кодом состояния HTTP 201 (Created).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если объявление не найдено - код состояния HTTP 404 (Not Found).
     */
    @PostMapping("/{id}/comments")
    @Operation(summary = "Добавление комментария к объявлению", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = CommentDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentDto> createAdComment(@PathVariable("id") Integer id,
                                                      @RequestBody @Valid CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                      Authentication authentication) {
        return ResponseEntity.ok(commentService.createAdComment(id, createOrUpdateCommentDto, authentication));
    }

    /**
     * Удаление комментария у объявления по его идентификатору.
     *
     * @param id        идентификатор объявления.
     * @param commentId идентификатор комментария.
     * @return ответ с кодом состояния HTTP 200 (OK) после успешного удаления.<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если запрос недопустим - код состояния HTTP 403 (Forbidden).<br>
     * Если комментарий не найден - код состояния HTTP 404 (Not Found).
     */
    @DeleteMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Удаление комментария", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> deleteCommentByIdAd(@PathVariable("adId") Integer id,
                                                    @PathVariable("commentId") Integer commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновление комментария у объявления по его идентификатору.
     *
     * @param adId                     идентификатор объявления.
     * @param commentId                идентификатор комментария.
     * @param createOrUpdateCommentDto обновленные данные комментария.
     * @param authentication           объект аутентификации, представляющий текущего пользователя.
     * @return ответ с обновленным комментарием в формате JSON и кодом состояния HTTP 200 (OK).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если запрос недопустим - код состояния HTTP 403 (Forbidden).<br>
     * Если комментарий не найден - код состояния HTTP 404 (Not Found).
     */
    @PatchMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Обновление комментария", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = CommentDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentDto> updateCommentByIdAd(@PathVariable("adId") Integer adId,
                                                          @PathVariable("commentId") Integer commentId,
                                                          @RequestBody @Valid CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                          Authentication authentication) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, createOrUpdateCommentDto, authentication));
    }
}
