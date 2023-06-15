package com.java.dailyTasks.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.dailyTasks.domain.FileData;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.exceptions.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
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

	   private final ImageRepository imageRepository;

		@Autowired
		public ImageService(ImageRepository imageRepository) {

			this.imageRepository = imageRepository;
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

    public byte[] getImage(Long id) {
        Optional<Image> dbImageData = imageRepository.findById(id);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getData());
        return images;
    }
//--------------------------------------------------------------------------------------------
    public void removeById(Long id) {
		Image imageData = findImageById(id);
		imageRepository.delete(imageData);

	}

	public Image findImageById(Long id) {
		return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,true)));

	}


//-----------------------------------------------------------------------------------------------

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=folder_path+file.getOriginalFilename();

        FileData fileData=imageRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }
//------------------------------------------------------------------------------------------------
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = imageRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }



}