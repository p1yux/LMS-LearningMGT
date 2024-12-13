package com.lms.service;

import com.lms.model.Course;
import com.lms.model.Subject;
import com.lms.model.User;
import com.lms.repository.CourseRepository;
import com.lms.repository.SubjectRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public Course createCourse(Course course, Long instructorId) {
        User instructor = userRepository.findById(instructorId)
            .orElseThrow(() -> new RuntimeException("Instructor not found"));
        
        if (instructor.getRole() != User.UserRole.INSTRUCTOR) {
            throw new RuntimeException("User is not an instructor");
        }
        
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @Transactional
    public void enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (student.getRole() != User.UserRole.STUDENT) {
            throw new RuntimeException("User is not a student");
        }
        
        if (course.getEnrolledStudents().contains(student)) {
            throw new RuntimeException("Student already enrolled in this course");
        }
        
        course.getEnrolledStudents().add(student);
        courseRepository.save(course);
    }

    public List<Course> getInstructorCourses(Long instructorId) {
        User instructor = userRepository.findById(instructorId)
            .orElseThrow(() -> new RuntimeException("Instructor not found"));
        return courseRepository.findByInstructor(instructor);
    }

    public List<Course> getStudentCourses(Long studentId) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        return courseRepository.findByEnrolledStudents(student);
    }

    public List<Subject> getCourseSubjects(Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        return subjectRepository.findByCourse(course);
    }
} 