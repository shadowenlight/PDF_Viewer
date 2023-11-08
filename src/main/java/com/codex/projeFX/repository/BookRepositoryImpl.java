package com.codex.projeFX.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codex.projeFX.dao.BookDAO;
import com.codex.projeFX.entity.*;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

	private BookDAO bookDAO;

	@Autowired
	public BookRepositoryImpl(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	@Override
	public void createBook(BookEntity book) {
		bookDAO.createBook(book);
	}

	@Override
	public BookEntity getBookById(long id) {
		return bookDAO.getBookById(id);
	}

	@Override
	public void updateBook(BookEntity book) {
		bookDAO.updateBook(book);
	}

	@Override
	public void deleteBook(BookEntity book) {
		bookDAO.deleteBook(book);
	}

	@Override
	public void deleteAllBooks() {
		bookDAO.deleteAllBooks();
	}

	@Override
	public List<BookEntity> getAllBooks() {
		return bookDAO.getAllBooks();
	}

	@Override
	public List<BookEntity> getAllBooksByCourse(CourseEntity course) {
		return bookDAO.getAllBooksByCourse(course);
	}

	@Override
	public List<BookEntity> getAllBooksByType(String bookType) {
		return bookDAO.getAllBooksByType(bookType);
	}

	public List<String> getAllBookTypes() {
		return bookDAO.getAllBookTypes();
	}
}