package com.lms.controller;

import com.lms.model.*;
import com.lms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final CourseService courseService;
    private final AttendanceService attendanceService;
    private final FeeService feeService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getMyCourses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(courseService.getStudentCourses(user.getId()));
    }

    @GetMapping("/attendance/{subjectId}")
    public ResponseEntity<Double> getAttendancePercentage(
            @AuthenticationPrincipal User user,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(
            attendanceService.calculateAttendancePercentage(user.getId(), subjectId)
        );
    }

    @GetMapping("/fees/pending/{feeStructureId}")
    public ResponseEntity<BigDecimal> getPendingFees(
            @AuthenticationPrincipal User user,
            @PathVariable Long feeStructureId) {
        return ResponseEntity.ok(
            feeService.calculatePendingFees(user.getId(), feeStructureId)
        );
    }

    @GetMapping("/fees/history")
    public ResponseEntity<List<FeePayment>> getFeeHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(feeService.getPaymentHistory(user.getId()));
    }
} 