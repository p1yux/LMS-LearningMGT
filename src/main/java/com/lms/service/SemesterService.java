package com.lms.service;

import com.lms.model.Course;
import com.lms.model.Semester;
import com.lms.model.Subject;
import com.lms.repository.CourseRepository;
import com.lms.repository.SemesterRepository;
import com.lms.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterService {
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public Semester createSemester(Semester semester, Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        semester.setCourse(course);
        return semesterRepository.save(semester);
    }

    @Transactional
    public Subject addSubjectToSemester(Long semesterId, Subject subject) {
        Semester semester = semesterRepository.findById(semesterId)
            .orElseThrow(() -> new RuntimeException("Semester not found"));
        subject.setSemester(semester);
        subject.setCourse(semester.getCourse());
        return subjectRepository.save(subject);
    }

    public List<Subject> getSemesterSubjects(Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
            .orElseThrow(() -> new RuntimeException("Semester not found"));
        return subjectRepository.findBySemester(semester);
    }

    public List<Semester> getCourseSemesters(Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        return semesterRepository.findByCourse(course);
    }
} 