package com.codex.projeFX.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.codex.projeFX.dao.BookDAO;
import com.codex.projeFX.repository.BookRepositoryImpl;
import com.codex.projeFX.entity.*;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryTest {

	@InjectMocks
	private BookRepositoryImpl bookRepos;

	@Mock
	private BookDAO bookDAO;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// ----------------BOOKREPOSITORY-TESTS
	@Test
	public void test_CreateBook() {
		BookEntity book = new BookEntity();
		book.setTitle("JTest Book");

		bookRepos.createBook(book);

		Mockito.verify(bookDAO).createBook(book);
	}

	@Test
	public void test_getBookById() {
		long bookId = 1;
		BookEntity expectedBook = new BookEntity();

		expectedBook.setBookId(bookId);
		expectedBook.setTitle("JTest Book");

		Mockito.when(bookDAO.getBookById(bookId)).thenReturn(expectedBook);

		BookEntity actualBook = bookRepos.getBookById(bookId);

		Assertions.assertEquals(expectedBook, actualBook);
	}

	@Test
	public void test_updateBook() {
		BookEntity book = new BookEntity();
		book.setBookId(1L);
		book.setTitle("JTest Book");

		Mockito.doNothing().when(bookDAO).updateBook(book);

		bookRepos.updateBook(book);

		Mockito.verify(bookDAO).updateBook(book);
	}

	@Test
	public void test_deleteBook() {
		BookEntity book = new BookEntity();
		book.setBookId(1L);
		book.setTitle("JTest Book");

		Mockito.doNothing().when(bookDAO).deleteBook(book);

		bookRepos.deleteBook(book);

		Mockito.verify(bookDAO).deleteBook(book);
	}

	@Test
	public void test_getAllBooks() {
		BookEntity book1 = new BookEntity();
		BookEntity book2 = new BookEntity();
		BookEntity book3 = new BookEntity();
		BookEntity book4 = new BookEntity();

		book1.setBookId(0L);
		book1.setTitle("JTest Book1");

		book2.setBookId(1L);
		book2.setTitle("JTest Book2");

		book3.setBookId(2L);
		book3.setTitle("JTest Book3");

		book4.setBookId(3L);
		book4.setTitle("JTest Book4");

		List<BookEntity> expectedBooks = new ArrayList<>();

		expectedBooks.add(book1);
		expectedBooks.add(book2);
		expectedBooks.add(book3);
		expectedBooks.add(book4);

		Mockito.when(bookDAO.getAllBooks()).thenReturn(expectedBooks);

		List<BookEntity> result = bookRepos.getAllBooks();

		Mockito.verify(bookDAO).getAllBooks();

		Assertions.assertEquals(expectedBooks, result);
	}

	@Test
	public void test_getAllBooksByCourse() {
		CourseEntity course1 = new CourseEntity();
		CourseEntity course2 = new CourseEntity();

		BookEntity book1 = new BookEntity();
		BookEntity book2 = new BookEntity();
		BookEntity book3 = new BookEntity();
		BookEntity book4 = new BookEntity();

		book1.setBookId(0L);
		book1.setTitle("JTest Book1");

		book2.setBookId(1L);
		book2.setTitle("JTest Book2");

		book3.setBookId(2L);
		book3.setTitle("JTest Book3");

		book4.setBookId(3L);
		book4.setTitle("JTest Book4");

		course1.setCourseId(0L);
		course1.setCourseName("Course 1");

		course2.setCourseId(1L);
		course2.setCourseName("Course 2");

		List<BookEntity> expectedBooks = new ArrayList<>();

		expectedBooks.add(book1);
		expectedBooks.add(book2);

		Mockito.when(bookDAO.getAllBooksByCourse(course1)).thenReturn(expectedBooks);

		List<BookEntity> result = bookRepos.getAllBooksByCourse(course1);

		Mockito.verify(bookDAO).getAllBooksByCourse(course1);

		Assertions.assertEquals(expectedBooks, result);
	}
}
