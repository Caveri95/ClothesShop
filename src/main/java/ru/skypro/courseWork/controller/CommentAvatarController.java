package ru.skypro.courseWork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.courseWork.service.ImageService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class CommentAvatarController {
    private final ImageService imageService;

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id, Authentication authentication){
        return ResponseEntity.ok(imageService.getImage(id));
    }
}
