package com.goodee.library.book.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;

@Controller
public class BookViewController {
	
	private static final Logger LOGGER = 
			LogManager.getLogger(BookViewController.class);
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/book")
	public String bookList(BookDto bookDto, Model model) {
		LOGGER.info("도서 목록 페이지로 이동");
		int totalData = bookService.selectBookCount(bookDto.getB_name()); // searchBookName
		bookDto.setTotalData(totalData);
		List<BookDto> bookList = bookService.selectBookList(bookDto);
		model.addAttribute("resultList", bookList);
		model.addAttribute("paging", bookDto);
		return "book/list";
	}
	
	@GetMapping("/book/add")
	public String createBook() {
		LOGGER.info("도서 등록 화면으로 이동");
		return "book/add";
	}
	
}
