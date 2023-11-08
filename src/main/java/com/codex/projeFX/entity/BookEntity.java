package com.codex.projeFX.entity;

import java.net.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Book_id")
	private Long bookId;

	@Column(name = "Title")
	private String title;

	@Column(name = "Cover_image_path")
	private URL imgPath;

	@Column(name = "Pdf_path")
	private String pdfPath;

	@Column(name = "bookType")
	private String bookType;

	@ManyToOne
	@JoinColumn(name = "Course_id")
	private CourseEntity course;

	// Default Constructor
	public BookEntity() {
	}

	// Parameterized Constructor
	BookEntity(String title, URL imgPath, String pdfPath, String bookType) {
		this.title = title;
		this.imgPath = imgPath;
		this.pdfPath = pdfPath;
		this.bookType = bookType;
	}

	// Set Functions
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImgPath(URL imgPath) {
		this.imgPath = imgPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	// Get Functions
	public Long getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public URL getImgPath() {
		return imgPath;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public String getBookType() {
		return bookType;
	}

	public CourseEntity getCourse() {
		return course;
	}
}
