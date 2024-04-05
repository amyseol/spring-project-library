package com.goodee.library.book.dao;

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
			e.printStackTrace();
		}
		return result;
	}
	
}
