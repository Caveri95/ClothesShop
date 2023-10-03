package ru.skypro.courseWork.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.courseWork.dto.AdDto;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.dto.ExtendedAdDto;
import ru.skypro.courseWork.entity.Ad;

import java.io.IOException;
import java.util.List;

public interface AdService {
    List<Ad> getAllAds();

    Ad createAd(CreateOrUpdateAdDto properties, MultipartFile image) throws IOException;

    void updateImage(Integer id, MultipartFile image) throws IOException;

    List<Ad> getAllMyAds();

    ExtendedAdDto getAdFullInfo(Integer id);

    void deleteById(Integer id);

    AdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);
}
