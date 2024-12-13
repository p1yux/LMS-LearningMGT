package com.lms.controller;

import com.lms.model.*;
import com.lms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorController {
    private final CourseService courseService;
    private final AttendanceService attendanceService;
    private final SubjectService subjectService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getMyCourses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(courseService.getInstructorCourses(user.getId()));
    }

    @PostMapping("/attendance")
    public ResponseEntity<Attendance> markAttendance(
            @RequestParam Long studentId,
            @RequestParam Long subjectId,
            @RequestParam boolean present,
            @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(
            attendanceService.markAttendance(studentId, subjectId, present, remarks)
        );
    }

    @GetMapping("/subjects/{courseId}")
    public ResponseEntity<List<Subject>> getCourseSubjects(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseSubjects(courseId));
    }
} 