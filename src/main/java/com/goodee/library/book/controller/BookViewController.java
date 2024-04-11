package com.goodee.library.book.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		String searchBookName = bookDto.getB_name();
		int totalData = bookService.selectBookCount(searchBookName); 
		bookDto.setTotalData(totalData);
		List<BookDto> bookList = bookService.selectBookList(bookDto);
		
		model.addAttribute("resultList", bookList);
		model.addAttribute("paging", bookDto);
		
		return "book/list";
	}
	
	@GetMapping("/book/add")
	public String createBook() {
		return "book/add";
	}
	
	@GetMapping("/book/{b_no}")
	public String editBookDetail(@PathVariable("b_no") int b_no, Model model) {
		BookDto bookDto = bookService.selectBookDetail(b_no);
		model.addAttribute("bookDto", bookDto);
		return "book/edit";
	}
	
}
