package com.goodee.library.book.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.goodee.library.book.dto.BookDto;

@Repository
public class BookDao {
	
	private static final Logger LOGGER =
			LogManager.getLogger(BookDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String NAMESPACE = "com.goodee.library.bookMapper.";

	public int createBook(BookDto bookDto) {
		LOGGER.info("도서 등록중");
		int result = 0;
		try {
			result = sqlSession.insert(NAMESPACE+"createBook", bookDto);
		} catch (Exception e) {
	        StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        LOGGER.error(errors.toString());
		}
		return result;
	}

	public int selectBookCount(String searchBookName) {
		int result = 0;
		try {
			result = sqlSession.selectOne(NAMESPACE+"selectBookCount", searchBookName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<BookDto> selectBookList(BookDto bookDto) {
		LOGGER.info("도서 목록 조회");
		List<BookDto> bookList = new ArrayList<BookDto>();
		try {
			bookList = sqlSession.selectList(NAMESPACE+"selectBookList", bookDto);
		} catch (Exception e) {
	        e.printStackTrace();			
		}
		return bookList;
	}

	public List<BookDto> selectBookListToday() {
		LOGGER.info("오늘 등록한 도서 목록 조회");
		List<BookDto> bookList = new ArrayList<BookDto>();
		try {
			bookList = sqlSession.selectList(NAMESPACE+"selectBookListToday");
		} catch (Exception e) {
	        e.printStackTrace();			
		}
		return bookList;
	}
	
}
