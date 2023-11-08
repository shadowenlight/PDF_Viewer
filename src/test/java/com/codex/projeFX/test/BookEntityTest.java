package com.codex.projeFX.test;

import com.codex.projeFX.entity.BookEntity;

import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookEntityTest {

	@Test
	public void test_bookId() {
		BookEntity bookEntity = new BookEntity();

		bookEntity.setBookId(1L);

		Long expectedId = 1L;
		Long actualId = bookEntity.getBookId();

		Assertions.assertEquals(expectedId, actualId);
	}

	@Test
	public void test_title() {
		BookEntity bookEntity = new BookEntity();

		bookEntity.setTitle("JTest Book");

		String expectedTitle = "JTest Book";
		String actualTitle = bookEntity.getTitle();

		Assertions.assertEquals(expectedTitle, actualTitle);
	}

	@Test
	public void test_imgPath() {
		BookEntity bookEntity = new BookEntity();

		bookEntity.setImgPath(getClass().getResource("/view/No-Image-Placeholder.svg.png"));

		String expectedImgPath = getClass().getResource("/view/No-Image-Placeholder.svg.png").getPath();
		URL actualImgPath = bookEntity.getImgPath();

		Assertions.assertEquals(expectedImgPath, actualImgPath);
	}

	@Test
	public void test_pdfPath() {
		BookEntity bookEntity = new BookEntity();

		bookEntity.setPdfPath(getClass()
				.getResource("/books/ConferencePaper_AnalyzingtheMentalFatigueFindingsviaPhysiologicalSignals_YA.pdf")
				.getPath());

		String expectedPdfPath = getClass()
				.getResource("/books/ConferencePaper_AnalyzingtheMentalFatigueFindingsviaPhysiologicalSignals_YA.pdf")
				.getPath();
		String actualPdfPath = bookEntity.getPdfPath();

		Assertions.assertEquals(expectedPdfPath, actualPdfPath);
	}
}
