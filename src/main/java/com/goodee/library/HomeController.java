package com.goodee.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;

@Controller
public class HomeController {
	
	private static final Logger LOGGER = 
			LogManager.getLogger(HomeController.class);
	
	@Autowired 
	BookService bookService;
	
	@GetMapping({"","/"})
	public String home() {
		LOGGER.info("도서관 관리 시스템");
		// 오늘 날짜의 도서 목록 조회
		List<BookDto> bookList = new ArrayList<BookDto>();
		bookList = bookService.selectBookListToday();
		
		for(BookDto dto : bookList) {
			LOGGER.info(dto.getB_name());
		}

		return "home";
	}
	
}
