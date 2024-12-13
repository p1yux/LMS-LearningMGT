package com.lms.service;

import com.lms.model.Course;
import com.lms.model.User;
import com.lms.repository.CourseRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(String name, String email, String password, User.UserRole role) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    @Transactional
    public Course createCourse(String title, String description, String syllabus, User instructor) {
        if (instructor.getRole() != User.UserRole.INSTRUCTOR) {
            throw new RuntimeException("Only instructors can be assigned to courses");
        }

        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setSyllabus(syllabus);
        course.setInstructor(instructor);

        return courseRepository.save(course);
    }
} 