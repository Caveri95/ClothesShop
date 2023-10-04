package ru.skypro.courseWork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.courseWork.dto.CommentDto;
import ru.skypro.courseWork.dto.CommentsDto;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.mapper.CommentMapper;
import ru.skypro.courseWork.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получение комментариев объявления", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentsDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentsDto> getCommentsByIdAd(@PathVariable("id") Integer id) {
        List<CommentDto> commentsDto = commentMapper.toCommentsDto(commentService.getCommentByIdAd(id));
        return ResponseEntity.ok(new CommentsDto(commentsDto.size(), commentsDto));
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Добавление комментария к объявлению", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentDto> createAdComment(@PathVariable("id") Integer id,
                                                      @RequestBody @Valid CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return ResponseEntity.ok(commentMapper.toCommentDto(commentService.createAdComment(id, createOrUpdateCommentDto)));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Удаление комментария", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> deleteCommentByIdAd(@PathVariable("adId") Integer id,          //Зачем id объявления
                                                    @PathVariable("commentId") Integer commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Обновление комментария", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<CommentDto> updateCommentByIdAd(@PathVariable("adId") Integer adId,
                                                    @PathVariable("commentId") Integer commentId,
                                                    @RequestBody @Valid CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return ResponseEntity.ok(commentMapper.toCommentDto(commentService.updateComment(adId,commentId, createOrUpdateCommentDto)));
    }
}
