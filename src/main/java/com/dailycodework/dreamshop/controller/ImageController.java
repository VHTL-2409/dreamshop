package com.dailycodework.dreamshop.controller;

import ch.qos.logback.core.joran.action.AppenderAction;
import com.dailycodework.dreamshop.Exception.ResourceNotFoundException;
import com.dailycodework.dreamshop.dto.ImageDto;
import com.dailycodework.dreamshop.model.Image;
import com.dailycodework.dreamshop.response.ApiResponse;
import com.dailycodework.dreamshop.service.image.ImageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Internal;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@AllArgsConstructor
@RestController
@RequestMapping("${/api.prefix}/image")
public class ImageController {
    private final ImageService imageService;



    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files , Long productId){
        try{
            List<ImageDto> imageDto = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("success", imageDto));
        }
       catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed " , e.getMessage()));
       }
    }

    @SneakyThrows
    @GetMapping("/image/download/{imageId}")
    public  ResponseEntity<Resource> downloadImage(@PathVariable Long imageId){
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachmemt ;filename=\""+image.getFileName()+ "\"")
                .body(resource);
    }
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile files , Long productId){
        Image image = imageService.getImageById(imageId);
        try {
            if(image != null) {
                    imageService.updateImage(files , imageId);
                    return ResponseEntity.ok(new ApiResponse("success",  null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse( e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("image not found", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        Image image = imageService.getImageById(imageId);
        try {
            if(image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("success",  null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse( e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("image not found", INTERNAL_SERVER_ERROR));
    }
}















