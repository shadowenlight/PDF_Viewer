package com.codex.projeFX.entity;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Course_id")
  private Long courseId;

  @Column(name = "Course_name")
  private String courseName;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = CascadeType.ALL)
  private List<BookEntity> books;

  // Default Constructor
  public CourseEntity() {
	  this.books=new ArrayList<>();
  }

  // Parameterized Constructor
  public CourseEntity(String courseName, List<BookEntity> books) {
    this.books = books;
    this.courseName = courseName;
  }

  // Set Functions
  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public void setBooks(List<BookEntity> books) {
    this.books = books;
  }

  // Get functions
  public Long getCourseId() {
    return courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public List<BookEntity> getBooks() {
    return books;
  }

  // Custom methods
  public void addBook(BookEntity book) {
	  book.setCourse(this);
    this.books.add(book);
  }

  public void removeBook(BookEntity book) {
    books.remove(book);
    book.setCourse(null);
  }

  public BookEntity getBook(String bookName) {
    BookEntity target = null;

    for (int i = 0; i < this.books.size(); i++) {
      if (books.get(i).getTitle() == bookName) {
        target = books.get(i);
        break;
      }
    }
    if (target == null) throw new RuntimeException("An error occurred: Error message goes here.");
    return target;
  }
}
