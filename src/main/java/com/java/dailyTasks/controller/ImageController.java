package com.java.dailyTasks.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.response.ImageSavedResponse;
import com.java.dailyTasks.response.Response;
import com.java.dailyTasks.response.ResponseMessage;
import com.java.dailyTasks.service.ImageService;

import java.io.IOException;
@RestController
@RequestMapping("/images")
public class ImageController {

	private final ImageService imageService;

	@Autowired
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}

	@PostMapping
	public ResponseEntity<ImageSavedResponse> uploadImage(@RequestParam("imageFile")MultipartFile file) throws IOException { 

		String imageId = imageService.uploadImage(file);

		ImageSavedResponse response =  new ImageSavedResponse(imageId, ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true);

		return  ResponseEntity.ok(response);


	}

		@GetMapping("/image/{id}")
		public ResponseEntity<byte[]> saveImage(@PathVariable String id)throws IOException{
			byte[] imageData=imageService.getImage(id);
			
//			 HttpHeaders header = new HttpHeaders();
//		      header.setContentType(MediaType.IMAGE_PNG);
//		      
		     // return ((BodyBuilder) new ResponseEntity<>(header,HttpStatus.OK)).body(imagData);
			
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);

		}
		
		@DeleteMapping("/{id}")
//		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response> deleteImageFile(@PathVariable String id) {
			imageService.removeById(id);
			
			Response response = new Response(ResponseMessage.IMAGE_DELETE_RESPONSE_MESSAGE,true);
			return ResponseEntity.ok(response);
		}
		
		


		@PostMapping("/file")
		public ResponseEntity<String> uploadImageToFIleSystem(@RequestParam("image")MultipartFile file) throws IOException {
			String uploadImage = imageService.uploadImageToFileSystem(file);
			 new Response(ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE,true);
			return ResponseEntity.ok().body(uploadImage);
		}

		@GetMapping("/{id}")
		public ResponseEntity<byte[]> downloadImageFromFileSystem(@PathVariable Long id) throws IOException {
			byte[] imageData=imageService.downloadImageFromFileSystem(id);
			return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(imageData);

		}
	
}