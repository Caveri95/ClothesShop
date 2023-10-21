package ru.skypro.courseWork.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.CommentRepository;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TestUtil {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

    public User createTestUser() {

        Image imageForUser = new Image();
        imageForUser.setData("userImage".getBytes());
        imageRepository.save(imageForUser);

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("$2a$10$j4g2V3qsGCRffvV/bYpE6uJrhEFFyCSKwRFnSK5QXdX8z88Ao6JEu");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhone("+79000000000");
        user.setRole(Role.USER);
        user.setImage(imageForUser);
        userRepository.save(user);

        return user;
    }

    public Ad createTestAd() {

        Image imageForAd = new Image();
        imageForAd.setData("adImage".getBytes());
        imageRepository.save(imageForAd);

        Image imageForUser = new Image();
        imageForUser.setData("userImage".getBytes());
        imageRepository.save(imageForUser);

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("$2a$10$j4g2V3qsGCRffvV/bYpE6uJrhEFFyCSKwRFnSK5QXdX8z88Ao6JEu");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhone("+79000000000");
        user.setRole(Role.USER);
        user.setImage(imageForUser);
        userRepository.save(user);

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setImage(imageForAd);
        ad.setPrice(1234);
        ad.setDescription("description");
        ad.setTitle("titletitle");
        adRepository.save(ad);

        return ad;
    }

    public Comment createTestComment() {

        Image imageForAd = new Image();
        imageForAd.setData("adImage".getBytes());
        imageRepository.save(imageForAd);

        Image imageForUser = new Image();
        imageForUser.setData("userImage".getBytes());
        imageRepository.save(imageForUser);

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("$2a$10$j4g2V3qsGCRffvV/bYpE6uJrhEFFyCSKwRFnSK5QXdX8z88Ao6JEu");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhone("+79000000000");
        user.setRole(Role.USER);
        user.setImage(imageForUser);
        userRepository.save(user);

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setImage(imageForAd);
        ad.setPrice(1234);
        ad.setDescription("description");
        ad.setTitle("titletitle");
        adRepository.save(ad);

        Comment comment = new Comment();
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setAuthor(user);
        comment.setText("TextComment");
        comment.setAd(ad);
        commentRepository.save(comment);

        return comment;
    }
}
