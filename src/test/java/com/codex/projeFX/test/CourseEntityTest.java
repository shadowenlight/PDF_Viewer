package com.codex.projeFX.test;

import com.codex.projeFX.entity.BookEntity;
import com.codex.projeFX.entity.CourseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CourseEntityTest {

    @Test
    public void test_courseId() {
        CourseEntity courseEntity = new CourseEntity();

        courseEntity.setCourseId(1L);

        long expectedId = 1L;
        long actualId = courseEntity.getCourseId();

        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    public void test_courseName() {
        CourseEntity courseEntity = new CourseEntity();

        courseEntity.setCourseName("JTest CourseName");

        String expectedCourseName = "JTest CourseName";
        String actualCourseName = courseEntity.getCourseName();

        Assertions.assertEquals(expectedCourseName, actualCourseName);
    }

    @Test
    public void test_Books() {
        CourseEntity courseEntity = new CourseEntity();

        BookEntity book1 = new BookEntity();
        BookEntity book2 = new BookEntity();
        BookEntity book3 = new BookEntity();
        BookEntity book4 = new BookEntity();

        List<BookEntity> books = new ArrayList<>();

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);

        courseEntity.setBooks(books);

        List<BookEntity> retrievedBooks = courseEntity.getBooks();

        Assertions.assertEquals(books.size(), retrievedBooks.size());
        Assertions.assertTrue(retrievedBooks.contains(book1));
        Assertions.assertTrue(retrievedBooks.contains(book2));
        Assertions.assertTrue(retrievedBooks.contains(book3));
        Assertions.assertTrue(retrievedBooks.contains(book4));
    }

    @Test
    public void test_addBook() {
        CourseEntity course = new CourseEntity();
        course.setCourseId(0L);
        course.setCourseName("JTest Course");

        BookEntity bookToAdd = new BookEntity();
        bookToAdd.setTitle("BookToAdd");

        BookEntity nativeBook1 = new BookEntity();
        nativeBook1.setBookId(0L);
        nativeBook1.setTitle("JTestBook1");

        BookEntity nativeBook2 = new BookEntity();
        nativeBook2.setBookId(1L);
        nativeBook2.setTitle("JTestBook2");

        BookEntity nativeBook3 = new BookEntity();
        nativeBook3.setBookId(2L);
        nativeBook3.setTitle("JTestBook3");

        BookEntity nativeBook4 = new BookEntity();
        nativeBook4.setBookId(3L);
        nativeBook4.setTitle("JTestBook4");

        List<BookEntity> bookList = new ArrayList<>();
        bookList.add(nativeBook1);
        bookList.add(nativeBook2);
        bookList.add(nativeBook3);
        bookList.add(nativeBook4);

        course.setBooks(bookList);
        course.addBook(bookToAdd);

        List<BookEntity> retrievedBooks = course.getBooks();

        Assertions.assertTrue(retrievedBooks.contains(bookToAdd));
    }

    @Test
    public void test_removeBook() {
        CourseEntity course = new CourseEntity();
        course.setCourseId(0L);
        course.setCourseName("JTest Course");

        BookEntity nativeBook1 = new BookEntity();
        nativeBook1.setBookId(0L);
        nativeBook1.setTitle("JTestBook1");

        BookEntity nativeBook2 = new BookEntity();
        nativeBook2.setBookId(1L);
        nativeBook2.setTitle("JTestBook2");

        BookEntity nativeBook3 = new BookEntity();
        nativeBook3.setBookId(2L);
        nativeBook3.setTitle("JTestBook3");

        BookEntity bookToRemove = new BookEntity();
        bookToRemove.setBookId(3L);
        bookToRemove.setTitle("Book To Remove");

        List<BookEntity> bookList = new ArrayList<>();
        bookList.add(nativeBook1);
        bookList.add(nativeBook2);
        bookList.add(nativeBook3);
        bookList.add(bookToRemove);

        course.setBooks(bookList);

        course.removeBook(bookToRemove);

        List<BookEntity> retrievedBooks = course.getBooks();

        Assertions.assertFalse(retrievedBooks.contains(bookToRemove));
    }

    @Test
    public void test_getBook() {

        CourseEntity course = new CourseEntity();
        course.setCourseName("JTest Course");

        BookEntity bookToFind = new BookEntity();
        bookToFind.setTitle("Book To Find");

        BookEntity book1 = new BookEntity();
        book1.setTitle("JTest Book1");

        BookEntity book2 = new BookEntity();
        book2.setTitle("JTest Book2");

        BookEntity book3 = new BookEntity();
        book3.setTitle("JTest Book3");

        List<BookEntity> bookList = new ArrayList<>();
        bookList.add(book3);
        bookList.add(book1);
        bookList.add(bookToFind);
        bookList.add(book2);

        course.setBooks(bookList);

        BookEntity result = course.getBook("Book To Find");

        Assertions.assertEquals(bookToFind, result);
    }
}