package com.codex.projeFX.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codex.projeFX.dao.CourseDAO;
import com.codex.projeFX.entity.CourseEntity;

@Repository
@Transactional
public class CourseRepositoryImpl implements CourseRepository {

	private CourseDAO courseDAO;

	@Autowired
	public CourseRepositoryImpl(CourseDAO courseDAO) {
	    this.courseDAO = courseDAO;
	}

	@Override
	public void createCourse(CourseEntity course) {
		courseDAO.createCourse(course);
	}

	@Override
	public CourseEntity getCourseById(long id) {
		return courseDAO.getCourseById(id);
	}

	@Override
	public CourseEntity findCourseByName(String name) {
		return courseDAO.findCourseByName(name);
	}

	@Override
	public void updateCourse(CourseEntity course) {
		courseDAO.updateCourse(course);
	}

	@Override
	public void deleteCourse(CourseEntity course) {
		courseDAO.deleteCourse(course);
	}

	@Override
	public void deleteAllCourses() {
		courseDAO.deleteAllCourses();
	}

	@Override
	public List<CourseEntity> getAllCourse() {
		return courseDAO.getAllCourse();
	}
}