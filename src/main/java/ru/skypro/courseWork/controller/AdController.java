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
import ru.skypro.courseWork.dto.*;
import ru.skypro.courseWork.service.AdService;
import ru.skypro.courseWork.service.ImageService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * Класс-контроллер для обработки запросов, связанных с объявлениями.
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdController {

    private final AdService adService;
    private final ImageService imageService;

    /**
     * Получение всех объявлений.
     *
     * @return ответ в виде массива с объявлениями в формате JSON и кодом состояния HTTP 200 (OK).
     */
    @GetMapping
    @Operation(summary = "Получение всех объявлений", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = AdsDto.class)))})
    })
    public ResponseEntity<AdsDto> getAllAds() {
        List<AdDto> adsDto = adService.getAllAds();

        return ResponseEntity.ok(new AdsDto(adsDto.size(), adsDto));
    }

    /**
     * Добавление нового объявления.
     *
     * @param properties     объект класса {@link CreateOrUpdateAdDto},
     *                       содержащий необходимую информацию о создаваемом объявлении.
     * @param image          файл для установки изображения объявления.
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return ответ с созданным объявлением в формате JSON и кодом состояния HTTP 201 (Created).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добавление объявления", responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = AdDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AdDto> createAd(@RequestPart @Valid CreateOrUpdateAdDto properties,
                                          @RequestPart MultipartFile image,
                                          Authentication authentication) throws IOException {
        return ResponseEntity.ok(adService.createAd(properties, image, authentication));
    }

    /**
     * Получение информации об объявлении по его идентификатору.
     *
     * @param id идентификатор объявления.
     * @return ответ с информацией об объявлении в формате JSON и кодом состояния HTTP 200 (OK).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если объявление не найдено - код состояния HTTP 404 (Not Found).
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об объявлении", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = ExtendedAdDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<ExtendedAdDto> getAdInfo(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getAdFullInfo(id));
    }

    /**
     * Удаление объявления по его идентификатору.
     *
     * @param id идентификатор объявления.
     * @return ответ с кодом состояния HTTP 200 (OK) после успешного удаления.<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если запрос недопустим - код состояния HTTP 403 (Forbidden).<br>
     * Если объявление не найдено - код состояния HTTP 404 (Not Found).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Void> deleteAdById(@PathVariable("id") Integer id) {
        adService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновление информации объявления по его идентификатору.
     *
     * @param id                  идентификатор объявления.
     * @param createOrUpdateAdDto обновленные данные объявления.
     * @return ответ с обновленным объявлением в формате JSON и кодом состояния HTTP 200 (OK).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если запрос недопустим - код состояния HTTP 403 (Forbidden).<br>
     * Если объявление не найдено - код состояния HTTP 404 (Not Found).
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации об объявлении", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = AdDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<AdDto> updateAdInfo(@PathVariable("id") Integer id,
                                              @RequestBody @Valid CreateOrUpdateAdDto createOrUpdateAdDto) {
        return ResponseEntity.ok(adService.updateAd(id, createOrUpdateAdDto));
    }

    /**
     * Получение объявлений текущего пользователем.
     *
     * @param authentication объект аутентификации, представляющий текущего пользователя.
     * @return ответ с массивом объявлений в формате JSON и кодом состояния HTTP 200 (OK).
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).
     */
    @GetMapping("/me")
    @Operation(summary = "Получение объявлений авторизованного пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = AdsDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AdsDto> getAllMeAds(Authentication authentication) {
        List<AdDto> adsDto = adService.getAllMyAds(authentication);
        return ResponseEntity.ok(new AdsDto(adsDto.size(), adsDto));
    }

    /**
     * Обновление изображения объявления.
     *
     * @param id    идентификатор объявления.
     * @param image файл изображения.
     * @return ответ с обновленным объявлением в формате JSON и кодом состояния HTTP 200 (OK).<br>
     * Если пользователь не авторизован - код состояния HTTP 401 (Unauthorized).<br>
     * Если запрос недопустим - код состояния HTTP 403 (Forbidden).<br>
     * Если объявление не найдено - код состояния HTTP 404 (Not Found).
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление картинки объявления", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/octet-stream", array = @ArraySchema(schema =
                    @Schema(implementation = String.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<String> updateImage(@PathVariable("id") Integer id,
                                              @NotNull @RequestParam MultipartFile image) throws IOException {
        adService.updateImage(id, image);
        return ResponseEntity.ok().build();
    }

    /**
     * Получение изображения объявления по его идентификатору изображения.
     *
     * @param id идентификатор изображения пользователя.
     * @return массив байтов с изображением и код состояния HTTP 200 (OK).
     */
    @GetMapping("/image/{id}")
    @Operation(summary = "Получение изображения объявления", responses = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        return ResponseEntity.ok(imageService.getImage(id));
    }
}
