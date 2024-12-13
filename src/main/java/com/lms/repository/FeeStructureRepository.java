package com.lms.repository;

import com.lms.model.Course;
import com.lms.model.FeeStructure;
import com.lms.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {
    Optional<FeeStructure> findByCourseAndSemesterAndAcademicYear(
        Course course, Semester semester, String academicYear);
} 