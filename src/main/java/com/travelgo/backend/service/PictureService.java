package com.travelgo.backend.service;

import com.travelgo.backend.domain.Item;
import com.travelgo.backend.domain.Location;
import com.travelgo.backend.domain.Picture;
import com.travelgo.backend.repository.PictureRepository;
import com.travelgo.exception.ErrorResponse;
import com.travelgo.exception.GlobalErrorCode;
import com.travelgo.exception.TravelGoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureService {

    private final PictureRepository pictureRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public Picture saveLocationPicture(MultipartFile image, Location location) throws IOException {
        String fileUrl = s3UploadService.upload(image, "images");
        Picture findPicture = pictureRepository.findByImageUrl(fileUrl);

        if (findPicture != null) {
            throw new TravelGoException(GlobalErrorCode.IMAGE_FILE_DUPLICATION);
        }

        Picture picture = new Picture(fileUrl, location);
        return pictureRepository.save(picture);
    }

    @Transactional
    public void saveItemPicture(MultipartFile image, Item item) throws IOException {
        String fileUrl = s3UploadService.upload(image, "images");
        Picture picture = new Picture(fileUrl, item);
        pictureRepository.save(picture);
    }

    public Picture findLocationPicture(Long locationId) {
        return pictureRepository.findByLocation_LocationId(locationId);
    }

    public Picture findItemPicture(Long itemId) {
        return pictureRepository.findByItem_ItemId(itemId);
    }

    @Transactional
    public void delete(Long pictureId) {
        pictureRepository.deleteById(pictureId);
    }

}
