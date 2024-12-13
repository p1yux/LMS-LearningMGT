package com.lms.repository;

import com.lms.model.Course;
import com.lms.model.Semester;
import com.lms.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByCourse(Course course);
    List<Subject> findBySemester(Semester semester);
    List<Subject> findByCourseAndSemester(Course course, Semester semester);
} 