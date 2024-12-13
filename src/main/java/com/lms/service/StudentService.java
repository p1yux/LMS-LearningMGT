package com.lms.service;

import com.lms.model.Assignment;
import com.lms.model.Course;
import com.lms.model.Submission;
import com.lms.model.User;
import com.lms.repository.AssignmentRepository;
import com.lms.repository.CourseRepository;
import com.lms.repository.SubmissionRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Current user not found"));
    }

    @Transactional
    public void enrollInCourse(Long courseId) {
        User student = getCurrentUser();
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
            
        if (course.getEnrolledStudents().contains(student)) {
            throw new RuntimeException("Student already enrolled in this course");
        }
        
        course.getEnrolledStudents().add(student);
        courseRepository.save(course);
    }

    @Transactional
    public Submission submitAssignment(Long assignmentId, String content) {
        User student = getCurrentUser();
        Assignment assignment = assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setContent(content);
        
        return submissionRepository.save(submission);
    }

    public List<Course> getEnrolledCourses() {
        User student = getCurrentUser();
        return courseRepository.findByEnrolledStudents(student);
    }

    public List<Assignment> getCourseAssignments(Long courseId) {
        return assignmentRepository.findByCourse_Id(courseId);
    }

    public List<Submission> getSubmissions() {
        User student = getCurrentUser();
        return submissionRepository.findByStudent(student);
    }

    public Submission getSubmissionById(Long submissionId) {
        User student = getCurrentUser();
        return submissionRepository.findById(submissionId)
            .map(submission -> {
                if (!submission.getStudent().getId().equals(student.getId())) {
                    throw new RuntimeException("Access denied");
                }
                return submission;
            })
            .orElseThrow(() -> new RuntimeException("Submission not found"));
    }
} 