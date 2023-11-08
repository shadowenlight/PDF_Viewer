package com.codex.projeFX.dao;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codex.projeFX.entity.BookEntity;
import com.codex.projeFX.entity.CourseEntity;

@Repository
@Transactional
public class BookDAOImpl implements BookDAO {

	private final Session session;

	@Autowired
	public BookDAOImpl(Session session) {
		this.session = session;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createBook(BookEntity book) {
		session.save(book);
	}

	@Override
	public BookEntity getBookById(long id) {
		return session.get(BookEntity.class, id);
	}

	@Override
	public void updateBook(BookEntity book) {
		session.merge(book);
	}

	@Override
	public void deleteBook(BookEntity book) {
		session.remove(book);
	}

	@Override
	public void deleteAllBooks() {
		Query<Void> query = session.createQuery("DELETE FROM BookEntity");
		query.executeUpdate();
	}

	@Override
	public List<BookEntity> getAllBooks() {

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);

		Root<BookEntity> root = criteriaQuery.from(BookEntity.class);
		criteriaQuery.select(root);
		Query<BookEntity> query = session.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public List<BookEntity> getAllBooksByCourse(CourseEntity course) {

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);

		Root<BookEntity> root = criteriaQuery.from(BookEntity.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("course"), course));
		Query<BookEntity> query = session.createQuery(criteriaQuery);

		return query.getResultList();
	}

	@Override
	public List<BookEntity> getAllBooksByType(String bookType) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<BookEntity> query = builder.createQuery(BookEntity.class);
		Root<BookEntity> root = query.from(BookEntity.class);

		query.select(root);
		query.where(builder.equal(root.get("bookType"), bookType));

		TypedQuery<BookEntity> typedQuery = session.createQuery(query);
		return typedQuery.getResultList();
	}

	@Override
	public List<String> getAllBookTypes() {
		String hql = "SELECT DISTINCT b.bookType FROM BookEntity b";
		Query<String> query = session.createQuery(hql, String.class);
		return query.getResultList();
	}
}