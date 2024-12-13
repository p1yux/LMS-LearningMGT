package com.lms.service;

import com.lms.model.Assignment;
import com.lms.model.Course;
import com.lms.model.Submission;
import com.lms.repository.AssignmentRepository;
import com.lms.repository.CourseRepository;
import com.lms.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public Assignment createAssignment(Course course, String title, String description, Date dueDate) {
        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setDueDate(dueDate);
        assignment.setCourse(course);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public void gradeSubmission(Long submissionId, double grade, String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
            .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        submissionRepository.save(submission);
    }

    public List<Course> getInstructorCourses(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
    }
} 