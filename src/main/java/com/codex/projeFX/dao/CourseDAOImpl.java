package com.codex.projeFX.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codex.projeFX.entity.CourseEntity;

import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class CourseDAOImpl implements CourseDAO {

	private final Session session;

	@Autowired
	public CourseDAOImpl(Session session) {
		this.session = session;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createCourse(CourseEntity course) {
		session.save(course);
	}

	@Override
	public CourseEntity getCourseById(long id) {
		return session.get(CourseEntity.class, id);
	}

	@Override
	public CourseEntity findCourseByName(String name) {
		return session.get(CourseEntity.class, name);
	}

	@Override
	public void updateCourse(CourseEntity course) {
		session.merge(course);
	}

	@Override
	public void deleteCourse(CourseEntity course) {
		session.remove(course);
	}

	@Override
	public void deleteAllCourses() {
		Query<Void> query = session.createQuery("DELETE FROM CourseEntity");
		query.executeUpdate();
	}

	@Override
	public List<CourseEntity> getAllCourse() {
		TypedQuery<CourseEntity> query = session.createQuery("FROM CourseEntity", CourseEntity.class);
		List<CourseEntity> courses = query.getResultList();
		return courses;
	}
}