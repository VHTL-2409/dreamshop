package com.dailycodework.dreamshop.service.image;

import com.dailycodework.dreamshop.Exception.ResourceNotFoundException;
import com.dailycodework.dreamshop.dto.ImageDto;
import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.repository.ImageRepository;
import com.dailycodework.dreamshop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(Math.toIntExact(id)).orElseThrow(()-> new ResourceNotFoundException("Image not found with id" + id));
    }

    @Override
    public void deleteImageById(Long id) {

    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductId(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String biuldDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = biuldDownloadUrl + image.getId();
                image.setUrl(downloadUrl);
                Image savedImage =  imageRepository.save(image);
                savedImage.setUrl(biuldDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto =  new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getUrl());
                savedImageDto.add(imageDto);
            }
            catch (IOException |SQLException e){
                throw new RuntimeException(e.getMessage());

            }
        }
         return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
