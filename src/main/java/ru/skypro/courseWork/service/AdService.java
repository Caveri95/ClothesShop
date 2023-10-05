package ru.skypro.courseWork.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.AdDto;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.dto.ExtendedAdDto;
import ru.skypro.courseWork.entity.Ad;

import java.io.IOException;
import java.util.List;

public interface AdService {
    List<AdDto> getAllAds();

    AdDto createAd(CreateOrUpdateAdDto properties, MultipartFile image, Authentication authentication) throws IOException;

    void updateImage(Integer id, MultipartFile image) throws IOException;

    List<AdDto> getAllMyAds(Authentication authentication);

    ExtendedAdDto getAdFullInfo(Integer id);

    void deleteById(Integer id);

    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);
}
