package com.goodee.library.book.util;

import java.io.File;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	private static final Logger LOGGER = 
			LogManager.getLogger(UploadFileService.class);
	
	public String upload(MultipartFile file) {
		LOGGER.info("파일을 서버에 저장");
		boolean result = false;
		
		String originalFileName = file.getOriginalFilename();
		String extension = 
				originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		UUID uuid = UUID.randomUUID(); // 중복되지 않은 값 
		String newFileName = uuid.toString().replaceAll("-", ""); // - 있으면 공백으로 수정 
		
		File savedFile = new File("C:\\library\\upload\\" + newFileName + extension); // 해당 경로에 비어있는 파일 객체 생성 
		if(!savedFile.exists()) {
			savedFile.mkdirs(); // make directory 리눅스 명령어
		}
		try {
			file.transferTo(savedFile); // 비어있는 파일 객체에 가져온 파일 넣기
			result = true;
		} catch (Exception e) {
			e.printStackTrace(); // transferto 메서드 동작하지 않으면 exception 발생, 동작하면 result = true 가 된다 
		}	
		
		if(result) {
			return newFileName + extension;
		}
		
		return null;
	}
	
}
