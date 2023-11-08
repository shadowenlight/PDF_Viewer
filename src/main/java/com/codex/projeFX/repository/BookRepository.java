package com.codex.projeFX.repository;

import java.util.List;

import com.codex.projeFX.entity.BookEntity;
import com.codex.projeFX.entity.CourseEntity;

public interface BookRepository {

	void createBook(BookEntity book);

	BookEntity getBookById(long id);

	void updateBook(BookEntity book);

	void deleteBook(BookEntity book);

	void deleteAllBooks();

	List<BookEntity> getAllBooks();

	List<BookEntity> getAllBooksByCourse(CourseEntity course);

	List<BookEntity> getAllBooksByType(String bookType);

	List<String> getAllBookTypes();
}