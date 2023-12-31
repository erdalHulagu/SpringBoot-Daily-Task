package com.java.dailyTasks.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.java.dailyTasks.domain.FileData;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.exception.message.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.repository.FileDataRepository;
import com.java.dailyTasks.repository.ImageRepository;
import com.java.dailyTasks.response.Response;
import com.java.dailyTasks.response.ResponseMessage;
import com.java.dailyTasks.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageService {

	   private  ImageRepository imageRepository;

	   private   FileDataRepository fileDataRepository;

		@Autowired
		public ImageService(ImageRepository imageRepository,  FileDataRepository fileDataRepository) {

			this.imageRepository = imageRepository;
			this.fileDataRepository=fileDataRepository;
		}

		private final String folder_path="C:\\Users\\user\\Pictures\\Saved Pictures";
		
		public String uploadImage(MultipartFile file) throws IOException {
			String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

		Image image=	Image.builder()
	           .name(fileName)
	           .type(file.getContentType())
	           .data(ImageUtils.compressImage(file.getBytes()))
	           .build();
			Image imageData = imageRepository.save(image);
			if(imageData!=null){


				return new Response(ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true) + file.getOriginalFilename();

		  }
	     return image.getId();
	   }
 
//--------------------------------------------------------------------------------------------

    public byte[] getImage(String id) {
        Image dbImageData = imageRepository.findImageById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
       byte[] images = ImageUtils.decompressImage(dbImageData.getData());
        return images;
    }
//--------------------------------------------------------------------------------------------
    public void removeById(String id) {
		Image imageData = findImageByImageId(id);
		imageRepository.delete(imageData);

	}

	public Image findImageByImageId(String id) {
		return imageRepository.findImageById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

	}


//-----------------------------------------------------------------------------------------------

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=folder_path+file.getOriginalFilename();

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return new Response(ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true) + filePath;
        }
        return null;
    }
//------------------------------------------------------------------------------------------------
    public byte[] downloadImageFromFileSystem(Long id) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findById(id);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

	



}
