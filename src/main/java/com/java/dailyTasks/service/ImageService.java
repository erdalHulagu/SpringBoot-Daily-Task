package com.java.dailyTasks.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.dailyTasks.domain.FileData;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.exceptions.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.repository.FileDataRepository;
import com.java.dailyTasks.repository.ImageRepository;
import com.java.dailyTasks.response.Response;
import com.java.dailyTasks.response.ResponseMessage;
import com.java.dailyTasks.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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


			Image imageData = imageRepository.save(Image.builder()
					           .name(file.getOriginalFilename())
					           .type(file.getContentType())
					           .data(ImageUtils.compressImage(file.getBytes()))
					           .build());
			if(imageData!=null){

				return new Response(ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true) + file.getOriginalFilename();

		  }
	     return null;
	   }
 
//--------------------------------------------------------------------------------------------

    public byte[] getImage(String id) {
        Optional<Image> dbImageData = Optional.of(imageRepository.findImageById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,true))));
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getData());
        return images;
    }
//--------------------------------------------------------------------------------------------
    public void removeById(String id) {
		Image imageData = findImageByImageId(id);
		imageRepository.delete(imageData);

	}

	public Image findImageByImageId(String id) {
		return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,true)));

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
