package com.goodee.library.book.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.goodee.library.book.dao.BookDao;
import com.goodee.library.book.dto.BookDto;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	
	public Map<String, String> createBook(BookDto bookDto) {
		Map<String, String> map = new HashMap<>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 등록 중 오류가 발생했습니다.");
		
		if(bookDao.createBook(bookDto) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "도서 등록에 성공했습니다.");
		}
		return map;
	}

	public int selectBookCount(String searchBookName) {
		return bookDao.selectBookCount(searchBookName);
	}

	public List<BookDto> selectBookList(BookDto bookDto) {
		return bookDao.selectBookList(bookDto);
	}

	public List<BookDto> selectBookListToday() {
		return bookDao.selectBookListToday();
	}
	
}
