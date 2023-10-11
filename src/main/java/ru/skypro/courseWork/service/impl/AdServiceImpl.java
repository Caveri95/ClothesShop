package ru.skypro.courseWork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.AdDto;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.dto.ExtendedAdDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.exception.notFoundException.AdNotFoundException;
import ru.skypro.courseWork.exception.notFoundException.UserNotFoundException;
import ru.skypro.courseWork.mapper.AdMapper;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.CommentRepository;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.service.AdService;
import ru.skypro.courseWork.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;


    @Override
    public List<AdDto> getAllAds() {
        return adMapper.toAdsDto(adRepository.findAll());
    }

    @Override
    public AdDto createAd(CreateOrUpdateAdDto properties, MultipartFile image, Authentication authentication) throws IOException {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        Ad ad = adMapper.toAdEntity(properties);
        ad.setImage(imageService.upload(image));
        ad.setAuthor(user);
        adRepository.save(ad);

        return adMapper.toAdDto(ad);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.findAdById(#id).getAuthor().getEmail()==authentication.name")
    public void updateImage(Integer id, MultipartFile image) throws IOException {

        Ad ad = findAdById(id);
        imageRepository.delete(ad.getImage());
        ad.setImage(imageService.upload(image));
        adRepository.save(ad);
    }

    @Override
    public List<AdDto> getAllMyAds(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);

        return adMapper.toAdsDto(adRepository.findAllByAuthorId(user.getId()));
    }

    @Override
    public ExtendedAdDto getAdFullInfo(Integer id) {
        return adMapper.toExtendAdDto(findAdById(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.findAdById(#id).getAuthor().getEmail()==authentication.name")
    public void deleteById(Integer id) {

        Ad ad = findAdById(id);

        commentRepository.deleteAllByAdPk(ad.getPk());
        imageRepository.deleteById(ad.getImage().getId());
        adRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.findAdById(#id).getAuthor().getEmail()==authentication.name")
    public AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {

        Ad ad = findAdById(id);

        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        adRepository.save(ad);

        return adMapper.toAdDto(ad);
    }

    public Ad findAdById(Integer id) {
        return adRepository.findById(id).orElseThrow(AdNotFoundException::new);
    }
}
