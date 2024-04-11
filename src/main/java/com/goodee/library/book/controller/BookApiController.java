package com.goodee.library.book.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;
import com.goodee.library.book.util.UploadFileService;

@Controller
public class BookApiController {
	
	private static final Logger LOGGER = 
			LogManager.getLogger(BookApiController.class);
	
	@Autowired
	UploadFileService uploadFileService;
	@Autowired
	BookService bookService;
	
	@PostMapping("/book")
	@ResponseBody
	public Map<String, String> createBook(BookDto bookDto, 
			@RequestParam("file") MultipartFile file){
		LOGGER.info("도서 등록 기능");
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 등록 중 오류가 발생했습니다.");
		
		String savedFileName = uploadFileService.upload(file);
		if(savedFileName != null) {
			map.put("res_msg", "파일 업로드에 성공했습니다.");
			bookDto.setB_thumbnail(savedFileName);
			map = bookService.createBook(bookDto);
		}else {
			map.put("res_msg", "파일 업로드 중 오류가 발생했습니다.");
		}
		
		return map;
	}
	
	@PostMapping("/book/{b_no}")
	@ResponseBody
	public Map<String, String> updateBook(BookDto bookDto, Model model,
			@RequestParam("file") MultipartFile file){
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 수정 중 오류가 발생했습니다.");
		
		if("".equals(file.getOriginalFilename())==false) {
			// 새로운 파일을 서버에 업로드
			String savedFileName = uploadFileService.upload(file);
			if(savedFileName != null) {
				// 기존 파일 삭제
				if(uploadFileService.deleteOriginalFile(bookDto.getB_thumbnail())) {
					// 정상 삭제되면 thumbnail 바꾸기 
					bookDto.setB_thumbnail(savedFileName);
				}else {
					map.put("res_msg", "기존 파일 삭제에 실패하였습니다.");
				} 
			}else {
				map.put("res_msg", "파일 업로드 중 오류가 발생했습니다.");
			}
		}
		
		// 도서 정보 수정 (b_thumbnail 여부에 따라 쿼리가 달라진다.)		
		if(bookService.editBookDetail(bookDto)>0) {
			LOGGER.info("수정 완료! ");
			BookDto updateBookDto = bookService.selectBookDetail(bookDto.getB_no());
			model.addAttribute("bookDto", updateBookDto);
			map.put("res_code", "200");
			map.put("res_msg", "도서 정보 수정이 완료되었습니다.");
		}
		
		return map;
	}
	
	
	@DeleteMapping("/book/{b_no}")
	@ResponseBody
	public Map<String, String> deleteBook(@PathVariable("b_no") int b_no, 
			@RequestBody String deleteThumbnail) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 삭제 중 오류가 발생했습니다.");
		
		if(deleteThumbnail != null) {
			if(bookService.deleteBook(b_no) > 0) {
				uploadFileService.deleteOriginalFile(deleteThumbnail);
				map.put("res_code", "200");
				map.put("res_msg", "도서 삭제가 완료되었습니다.");
			}
		}
		return map;
	}
	
}
