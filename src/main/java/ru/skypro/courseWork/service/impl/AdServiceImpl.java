package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.AdDto;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.dto.ExtendedAdDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.AdNotFoundException;
import ru.skypro.courseWork.mapper.AdMapper;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.CommentRepository;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.service.AdService;
import ru.skypro.courseWork.service.ImageService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;


    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    @Override
    public Ad createAd(CreateOrUpdateAdDto properties, MultipartFile image) throws IOException {

        //User user = userRepository.findBy()    Ищем юзера по какому то критерию

        Ad adEntity = adMapper.toAdEntity(properties);
        adEntity.setImage(imageService.upload(image));
        adEntity.setAuthor(new User());   // сюда нужно подставить юзера, который выше

        return adRepository.save(adEntity);
    }

    @Override
    public void updateImage(Integer id, MultipartFile image) throws IOException {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        imageRepository.delete(ad.getImage());
        ad.setImage(imageService.upload(image));
        adRepository.save(ad);

    }

    @Override
    public List<Ad> getAllMyAds() {

        Integer id = 1;//Откуда то вытаскиваем id пользователя

        return adRepository.findAllByAuthorId(id);
    }

    @Override
    public ExtendedAdDto getAdFullInfo(Integer id) {
        return adMapper.toExtendAdDto(adRepository.findById(id).orElseThrow(AdNotFoundException::new));
    }

    @Override
    public void deleteById(Integer id) {

        //commentRepository.deleteAll(); По хорошему удалить и комментарии этого объявления (не понятно откуда вытащить)
        adRepository.deleteById(id);
    }

    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {

        return adMapper.toAdDto(adRepository.findById(id).orElseThrow(AdNotFoundException::new));
    }
}
