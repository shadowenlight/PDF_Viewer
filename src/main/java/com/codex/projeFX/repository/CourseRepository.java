package com.codex.projeFX.repository;

import java.util.List;

import com.codex.projeFX.entity.CourseEntity;

public interface CourseRepository {

	void createCourse(CourseEntity course);

	CourseEntity getCourseById(long id);

	CourseEntity findCourseByName(String name);

	void updateCourse(CourseEntity course);

	void deleteCourse(CourseEntity course);

	void deleteAllCourses();

	List<CourseEntity> getAllCourse();
}