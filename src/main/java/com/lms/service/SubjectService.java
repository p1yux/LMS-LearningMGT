package com.lms.service;

import com.lms.model.Course;
import com.lms.model.Semester;
import com.lms.model.Subject;
import com.lms.model.User;
import com.lms.repository.CourseRepository;
import com.lms.repository.SemesterRepository;
import com.lms.repository.SubjectRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final UserRepository userRepository;

    @Transactional
    public Subject createSubject(Subject subject, Long courseId, Long semesterId, Long instructorId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Semester semester = semesterRepository.findById(semesterId)
            .orElseThrow(() -> new RuntimeException("Semester not found"));
        
        User instructor = userRepository.findById(instructorId)
            .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (instructor.getRole() != User.UserRole.INSTRUCTOR) {
            throw new RuntimeException("User is not an instructor");
        }

        subject.setCourse(course);
        subject.setSemester(semester);
        subject.setInstructor(instructor);
        
        return subjectRepository.save(subject);
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subject not found"));
    }

    public List<Subject> getSubjectsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        return subjectRepository.findByCourse(course);
    }

    public List<Subject> getSubjectsBySemester(Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
            .orElseThrow(() -> new RuntimeException("Semester not found"));
        return subjectRepository.findBySemester(semester);
    }

    public List<Subject> getSubjectsByCourseAndSemester(Long courseId, Long semesterId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        Semester semester = semesterRepository.findById(semesterId)
            .orElseThrow(() -> new RuntimeException("Semester not found"));
        return subjectRepository.findByCourseAndSemester(course, semester);
    }

    @Transactional
    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = getSubjectById(id);
        subject.setName(subjectDetails.getName());
        subject.setCode(subjectDetails.getCode());
        subject.setCredits(subjectDetails.getCredits());
        return subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepository.delete(subject);
    }

    @Transactional
    public Subject assignInstructor(Long subjectId, Long instructorId) {
        Subject subject = getSubjectById(subjectId);
        User instructor = userRepository.findById(instructorId)
            .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (instructor.getRole() != User.UserRole.INSTRUCTOR) {
            throw new RuntimeException("User is not an instructor");
        }

        subject.setInstructor(instructor);
        return subjectRepository.save(subject);
    }
} 